package net.eightyseven.simpleshulkers.block.entity;

import net.eightyseven.simpleshulkers.inventory.GenericShulkerBoxMenu;
import net.eightyseven.simpleshulkers.inventory.ModMenuTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public class OakShulkerBoxBlockEntity extends BaseShulkerBoxBlockEntity {

    public OakShulkerBoxBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.OAK_SHULKER_BOX_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.simpleshulkers.oak_shulker_box");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory playerInventory) {
        // Pass its own menu type to the GenericShulkerBoxMenu constructor
        return new GenericShulkerBoxMenu(ModMenuTypes.OAK_SHULKER_BOX_MENU.get(), id, playerInventory, this);
    }
}