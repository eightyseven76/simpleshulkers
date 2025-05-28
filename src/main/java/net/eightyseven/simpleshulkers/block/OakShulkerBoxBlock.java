package net.eightyseven.simpleshulkers.block;

import com.mojang.serialization.MapCodec;
import net.eightyseven.simpleshulkers.block.entity.ModBlockEntities;
import net.eightyseven.simpleshulkers.block.entity.OakShulkerBoxBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;

public class OakShulkerBoxBlock extends BaseShulkerBoxBlock {
    public static final MapCodec<OakShulkerBoxBlock> CODEC = simpleCodec(OakShulkerBoxBlock::new);

    public OakShulkerBoxBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new OakShulkerBoxBlockEntity(pos, state);
    }

    @Override
    protected BlockEntityType<OakShulkerBoxBlockEntity> getBlockEntityType() {
        return ModBlockEntities.OAK_SHULKER_BOX_BLOCK_ENTITY.get();
    }
}