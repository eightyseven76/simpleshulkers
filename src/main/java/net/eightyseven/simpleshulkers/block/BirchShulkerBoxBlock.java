package net.eightyseven.simpleshulkers.block;

import com.mojang.serialization.MapCodec;
import net.eightyseven.simpleshulkers.block.entity.BirchShulkerBoxBlockEntity;
import net.eightyseven.simpleshulkers.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;

public class BirchShulkerBoxBlock extends BaseShulkerBoxBlock {
    public static final MapCodec<BirchShulkerBoxBlock> CODEC = simpleCodec(BirchShulkerBoxBlock::new);

    public BirchShulkerBoxBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BirchShulkerBoxBlockEntity(pos, state);
    }

    @Override
    protected BlockEntityType<BirchShulkerBoxBlockEntity> getBlockEntityType() {
        return ModBlockEntities.BIRCH_SHULKER_BOX_BLOCK_ENTITY.get();
    }
}