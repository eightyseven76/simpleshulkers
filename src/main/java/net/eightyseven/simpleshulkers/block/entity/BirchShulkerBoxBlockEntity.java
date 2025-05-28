package net.eightyseven.simpleshulkers.block.entity;

import net.eightyseven.simpleshulkers.inventory.GenericShulkerBoxMenu;
import net.eightyseven.simpleshulkers.inventory.ModMenuTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public class BirchShulkerBoxBlockEntity extends BaseShulkerBoxBlockEntity {

    public BirchShulkerBoxBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BIRCH_SHULKER_BOX_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.simpleshulkers.birch_shulker_box");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory playerInventory) {
        return new GenericShulkerBoxMenu(ModMenuTypes.BIRCH_SHULKER_BOX_MENU.get(), id, playerInventory, this);
    }
}