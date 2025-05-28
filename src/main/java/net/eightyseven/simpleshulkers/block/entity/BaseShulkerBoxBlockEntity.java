package net.eightyseven.simpleshulkers.block.entity;

import com.mojang.logging.LogUtils;
import net.eightyseven.simpleshulkers.block.BaseShulkerBoxBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity; // Ensure this is extended
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.List;
import java.util.stream.IntStream;

public abstract class BaseShulkerBoxBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
    public static final int CONTAINER_SIZE = 9;
    private static final int[] SLOTS = IntStream.range(0, CONTAINER_SIZE).toArray();
    private static final Logger LOGGER = LogUtils.getLogger();

    private NonNullList<ItemStack> items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
    private int openCount;
    private AnimationStatus animationStatus = AnimationStatus.CLOSED;
    private float progress;
    private float progressOld;

    private Component customName;

    private static final TagKey<Item> DISALLOWED_IN_SHULKERS_TAG =
            ItemTags.create(ResourceLocation.fromNamespaceAndPath("simpleshulkers", "disallowed_in_shulker_boxes"));

    public BaseShulkerBoxBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BaseShulkerBoxBlockEntity blockEntity) {
        blockEntity.updateAnimation(level, pos, state);
    }

    private void updateAnimation(Level level, BlockPos pos, BlockState state) {
        this.progressOld = this.progress;
        switch (this.animationStatus) {
            case CLOSED:
                this.progress = 0.0F;
                break;
            case OPENING:
                this.progress += 0.1F;
                if (this.progressOld == 0.0F) {
                    playSound(level, pos, SoundEvents.SHULKER_BOX_OPEN);
                    doNeighborUpdates(level, pos, state);
                }
                if (this.progress >= 1.0F) {
                    this.animationStatus = AnimationStatus.OPENED;
                    this.progress = 1.0F;
                    doNeighborUpdates(level, pos, state);
                }
                this.moveCollidedEntities(level, pos, state);
                break;
            case OPENED:
                this.progress = 1.0F;
                break;
            case CLOSING:
                this.progress -= 0.1F;
                if (this.progressOld == 1.0F) {
                    playSound(level, pos, SoundEvents.SHULKER_BOX_CLOSE);
                    doNeighborUpdates(level, pos, state);
                }
                if (this.progress <= 0.0F) {
                    this.animationStatus = AnimationStatus.CLOSED;
                    this.progress = 0.0F;
                    doNeighborUpdates(level, pos, state);
                }
                break;
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (this.animationStatus == AnimationStatus.OPENED || this.animationStatus == AnimationStatus.OPENING) {
            this.animationStatus = AnimationStatus.CLOSED;
            this.progress = 0.0F;
            this.progressOld = 0.0F;
        }
    }

    private void playSound(Level level, BlockPos pos, net.minecraft.sounds.SoundEvent soundEvent) {
        level.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
    }

    public AABB getBoundingBox(BlockState state) {
        Direction facing = state.getValue(BaseShulkerBoxBlock.FACING);
        return Shulker.getProgressAabb(1.0F, facing, 0.5F * this.getProgress(1.0F));
    }

    private void moveCollidedEntities(Level level, BlockPos pos, BlockState state) {
        Direction direction = state.getValue(BaseShulkerBoxBlock.FACING);
        AABB pushAabb = Shulker.getProgressDeltaAabb(1.0F, direction, this.progressOld, this.progress).move(pos);
        List<Entity> list = level.getEntities(null, pushAabb);

        if (!list.isEmpty()) {
            for (Entity entity : list) {
                if (entity.getPistonPushReaction() != PushReaction.IGNORE) {
                    entity.move(
                            MoverType.SHULKER_BOX,
                            new Vec3(
                                    (pushAabb.getXsize() + 0.01) * direction.getStepX(),
                                    (pushAabb.getYsize() + 0.01) * direction.getStepY(),
                                    (pushAabb.getZsize() + 0.01) * direction.getStepZ()
                            )
                    );
                }
            }
        }
    }

    @Override
    public void startOpen(Player player) {
        if (!player.isSpectator() && this.level != null) {
            if (this.openCount == 0) {
                this.animationStatus = AnimationStatus.OPENING;
                this.level.blockEvent(this.worldPosition, this.getBlockState().getBlock(), 1, 1);
                this.level.gameEvent(player, GameEvent.CONTAINER_OPEN, this.worldPosition);
            }
            this.openCount++;
        }
    }

    @Override
    public void stopOpen(Player player) {
        if (!player.isSpectator() && this.level != null) {
            this.openCount--;
            if (this.openCount <= 0) {
                this.animationStatus = AnimationStatus.CLOSING;
                this.level.blockEvent(this.worldPosition, this.getBlockState().getBlock(), 1, 0);
                this.level.gameEvent(player, GameEvent.CONTAINER_CLOSE, this.worldPosition);
                this.openCount = 0;
            }
        }
    }

    @Override
    public boolean triggerEvent(int id, int param) {
        if (id == 1) {
            if (param == 1 && this.animationStatus != AnimationStatus.OPENING) {
                this.animationStatus = AnimationStatus.OPENING;
            } else if (param == 0 && this.animationStatus != AnimationStatus.CLOSING) {
                this.animationStatus = AnimationStatus.CLOSING;
            }
            return true;
        }
        return super.triggerEvent(id, param);
    }

    private static void doNeighborUpdates(Level level, BlockPos pos, BlockState state) {
        state.updateNeighbourShapes(level, pos, 3);
        level.updateNeighborsAt(pos, state.getBlock());
    }

    @Override
    protected abstract Component getDefaultName();

    @Override
    protected abstract AbstractContainerMenu createMenu(int id, Inventory playerInventory);

    @Override
    public int getContainerSize() {
        return CONTAINER_SIZE;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
        this.sanitizeItems();
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        return SLOTS;
    }

    private boolean isItemDisallowed(ItemStack stack) {
        return stack.is(DISALLOWED_IN_SHULKERS_TAG);
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction direction) {
        return !isItemDisallowed(stack);
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return !isItemDisallowed(stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction direction) {
        return true;
    }

    public float getProgress(float partialTicks) {
        return Mth.lerp(partialTicks, this.progressOld, this.progress);
    }

    public AnimationStatus getAnimationStatus() {
        return this.animationStatus;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        tag.putByte("Animation", (byte)this.animationStatus.ordinal());
        tag.putFloat("Progress", this.progress);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries) {
        super.handleUpdateTag(tag, registries);
        if (tag.contains("Animation", CompoundTag.TAG_BYTE)) {
            this.animationStatus = AnimationStatus.values()[tag.getByte("Animation")];
        }
        if (tag.contains("Progress", CompoundTag.TAG_FLOAT)) {
            this.progressOld = this.progress;
            this.progress = tag.getFloat("Progress");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (!trySaveLootTable(tag)) {
            ContainerHelper.saveAllItems(tag, this.items, true, registries);
        }
        tag.putByte("Animation", (byte)this.animationStatus.ordinal());
        tag.putFloat("Progress", this.progress);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!tryLoadLootTable(tag)) {
            ContainerHelper.loadAllItems(tag, this.items, registries);
        }
        this.sanitizeItems();

        if (tag.contains("Animation", CompoundTag.TAG_BYTE)) {
            this.animationStatus = AnimationStatus.values()[tag.getByte("Animation")];
        }
        if (tag.contains("Progress", CompoundTag.TAG_FLOAT)) {
            this.progress = tag.getFloat("Progress");
            this.progressOld = this.progress;
        }
    }

    public void saveToItem(ItemStack stack) {
        HolderLookup.Provider registries = this.level != null ? this.level.registryAccess() : null;
        CompoundTag blockEntityDataTag = new CompoundTag();

        if (this.hasCustomName()) {
            stack.set(DataComponents.CUSTOM_NAME, this.getName());
        }

        if (!this.trySaveLootTable(blockEntityDataTag)) {
            if (registries != null) {
                ContainerHelper.saveAllItems(blockEntityDataTag, this.items, true, registries);
            } else {
                LOGGER.warn("BaseShulkerBoxBlockEntity at {}: Cannot save items to ItemStack for dropping - HolderLookup.Provider is null!", this.worldPosition);
            }
        }
        if (!blockEntityDataTag.isEmpty()) {
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(blockEntityDataTag));
        }
    }

    // This method is inherited from RandomizableContainerBlockEntity and should be available.
    // public void setCustomName(@Nullable Component pName) {
    //     super.setCustomName(pName);
    // }

    public void setCustomName(Component name) {
        this.customName = name;
    }
    
    public void loadFromItem(ItemStack stack, HolderLookup.Provider registries) {
        Component customNameComponent = stack.get(DataComponents.CUSTOM_NAME);
        // Use the inherited setCustomName method
        this.setCustomName(customNameComponent); // This should work as setCustomName(Component) is public in superclass

        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if (customData != null) {
            CompoundTag blockEntityDataTag = customData.getUnsafe();
            if (!this.tryLoadLootTable(blockEntityDataTag)) {
                ContainerHelper.loadAllItems(blockEntityDataTag, this.items, registries);
            }
            this.sanitizeItems();
        }
    }

    private void sanitizeItems() {
        for (int i = 0; i < this.items.size(); i++) {
            ItemStack localStack = this.items.get(i);
            if (!localStack.isEmpty() && isItemDisallowed(localStack)) {
                this.items.set(i, ItemStack.EMPTY);
                LOGGER.warn("Removed a disallowed item ({}) from a shulker box at {} during sanitization.", localStack.getItem(), this.worldPosition);
            }
        }
    }

    public enum AnimationStatus {
        CLOSED,
        OPENING,
        OPENED,
        CLOSING
    }
}
