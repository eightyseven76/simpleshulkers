package net.eightyseven.simpleshulkers.block;

import com.mojang.serialization.MapCodec;
import net.eightyseven.simpleshulkers.block.entity.BaseShulkerBoxBlockEntity; // This will be created in the next step
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class BaseShulkerBoxBlock extends BaseEntityBlock {
    public static final EnumProperty<Direction> FACING = DirectionalBlock.FACING;

    // Common AABBs for open state, can be kept here or moved to specific implementations if they differ
    private static final VoxelShape UP_OPEN_AABB = Block.box(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape DOWN_OPEN_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
    private static final VoxelShape WEST_OPEN_AABB = Block.box(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
    private static final VoxelShape EAST_OPEN_AABB = Block.box(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape NORTH_OPEN_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
    private static final VoxelShape SOUTH_OPEN_AABB = Block.box(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);

    protected BaseShulkerBoxBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP));
    }

    // This will be implemented by subclasses to provide their specific codec
    @Override
    protected abstract MapCodec<? extends BaseEntityBlock> codec();

    // This will be implemented by subclasses to provide their specific BlockEntityType
    protected abstract BlockEntityType<? extends BaseShulkerBoxBlockEntity> getBlockEntityType();

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        // The ticker method reference (e.g., OakShulkerBoxBlockEntity::tick) needs to be from a common base or interface
        // For now, we assume BaseShulkerBoxBlockEntity will have a static tick method or an instance method suitable for this.
        // This might need adjustment once BaseShulkerBoxBlockEntity is created.
        return createTickerHelper(blockEntityType, getBlockEntityType(), BaseShulkerBoxBlockEntity::tick);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        if (player.isSpectator()) {
            return InteractionResult.CONSUME;
        }

        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof BaseShulkerBoxBlockEntity shulkerBE) { // Use BaseShulkerBoxBlockEntity
            if (canOpen(state, level, pos, shulkerBE)) {
                player.openMenu(shulkerBE);
                player.awardStat(Stats.OPEN_SHULKER_BOX);
                PiglinAi.angerNearbyPiglins(player, true);
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    private static boolean canOpen(BlockState state, Level level, BlockPos pos, BaseShulkerBoxBlockEntity be) {
        if (be.getAnimationStatus() != BaseShulkerBoxBlockEntity.AnimationStatus.CLOSED) {
            return true;
        } else {
            AABB aabb = Shulker.getProgressDeltaAabb(1.0F, state.getValue(FACING), 0.0F, 0.5F).move(pos).deflate(1.0E-6);
            return level.noCollision(aabb);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getClickedFace());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof BaseShulkerBoxBlockEntity shulkerBE) {
            shulkerBE.unpackLootTable(player);
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        BlockEntity blockentity = params.getParameter(LootContextParams.BLOCK_ENTITY);
        if (blockentity instanceof BaseShulkerBoxBlockEntity shulkerBE) {
            if (params.getOptionalParameter(LootContextParams.THIS_ENTITY) instanceof Player player && player.isCreative()) {
                return List.of();
            }
            ItemStack itemStack = new ItemStack(this);
            shulkerBE.saveToItem(itemStack); // Assumes saveToItem is in BaseShulkerBoxBlockEntity
            return List.of(itemStack);
        }
        return super.getDrops(state, params);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        BlockEntity blockentity = world.getBlockEntity(pos);
        if (blockentity instanceof BaseShulkerBoxBlockEntity shulkerBE) {
            return Shapes.create(shulkerBE.getBoundingBox(state)); // Assumes getBoundingBox is in BaseShulkerBoxBlockEntity
        }
        return Shapes.block();
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState state, BlockGetter world, BlockPos pos) {
        BlockEntity blockentity = world.getBlockEntity(pos);
        if (blockentity instanceof BaseShulkerBoxBlockEntity shulkerBE &&
                shulkerBE.getAnimationStatus() != BaseShulkerBoxBlockEntity.AnimationStatus.CLOSED) {
            Direction direction = state.getValue(FACING).getOpposite();
            return switch (direction) {
                case DOWN -> DOWN_OPEN_AABB;
                case UP -> UP_OPEN_AABB;
                case NORTH -> NORTH_OPEN_AABB;
                case SOUTH -> SOUTH_OPEN_AABB;
                case WEST -> WEST_OPEN_AABB;
                case EAST -> EAST_OPEN_AABB;
            };
        }
        return Shapes.block();
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof BaseShulkerBoxBlockEntity shulkerBE) {
                shulkerBE.loadFromItem(stack, level.registryAccess()); // Assumes loadFromItem is in BaseShulkerBoxBlockEntity
            }
        }
    }
}