package net.eightyseven.simpleshulkers.inventory;

import com.mojang.logging.LogUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;

public class GenericShulkerBoxMenu extends AbstractContainerMenu {
    private static final int CONTAINER_SIZE = 9;
    private final Container container;
    private static final Logger LOGGER = LogUtils.getLogger();

    // Client constructor - Called by the factory in ModMenuTypes
    // The MenuType is set by the system when this factory is invoked.
    public GenericShulkerBoxMenu(int windowId, Inventory playerInventory, FriendlyByteBuf extraData) {
        // Pass null for MenuType; it will be set by the MenuType instance that calls this factory.
        // We still need a Container instance for the client side.
        this(null, windowId, playerInventory, new SimpleContainer(CONTAINER_SIZE));
        // LOGGER.info("CLIENT: GenericShulkerBoxMenu (via FriendlyByteBuf) constructor called, windowId: {}", windowId);
    }

    // Server constructor - Used by BlockEntity's createMenu
    // This constructor receives the actual MenuType from the BlockEntity.
    public GenericShulkerBoxMenu(@org.jetbrains.annotations.Nullable MenuType<?> menuType, int windowId, Inventory playerInventory, Container container) {
        super(menuType, windowId);
        checkContainerSize(container, CONTAINER_SIZE);
        this.container = container;
        container.startOpen(playerInventory.player);
        // LOGGER.info("SERVER: GenericShulkerBoxMenu (via Container) constructor called, windowId: {}, MenuType: {}", windowId, menuType);

        int containerRows = 1;
        int containerCols = 9;

        for (int row = 0; row < containerRows; ++row) {
            for (int col = 0; col < containerCols; ++col) {
                this.addSlot(new WoodenShulkerSlot(container, col + row * containerCols, 8 + col * 18, 20 + row * 18));
            }
        }

        int playerInventoryY = 18 + containerRows * 18 + 15;
        int hotbarY = playerInventoryY + 58;

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, playerInventoryY + row * 18));
            }
        }

        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, hotbarY));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < CONTAINER_SIZE) {
                if (!this.moveItemStackTo(itemstack1, CONTAINER_SIZE, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, CONTAINER_SIZE, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemstack;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }

    private static class WoodenShulkerSlot extends Slot {
        private static final TagKey<Item> DISALLOWED_IN_SHULKERS_TAG =
                ItemTags.create(ResourceLocation.fromNamespaceAndPath("simpleshulkers", "disallowed_in_shulker_boxes"));

        public WoodenShulkerSlot(Container container, int index, int x, int y) {
            super(container, index, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            if (stack.is(DISALLOWED_IN_SHULKERS_TAG)) {
                return false;
            }
            return super.mayPlace(stack);
        }
    }
}
