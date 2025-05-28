package net.eightyseven.simpleshulkers.block;

import com.mojang.serialization.MapCodec;
import net.eightyseven.simpleshulkers.block.entity.ModBlockEntities;
import net.eightyseven.simpleshulkers.block.entity.SpruceShulkerBoxBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;

public class SpruceShulkerBoxBlock extends BaseShulkerBoxBlock {
    public static final MapCodec<SpruceShulkerBoxBlock> CODEC = simpleCodec(SpruceShulkerBoxBlock::new);

    public SpruceShulkerBoxBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SpruceShulkerBoxBlockEntity(pos, state);
    }

    @Override
    protected BlockEntityType<SpruceShulkerBoxBlockEntity> getBlockEntityType() {
        return ModBlockEntities.SPRUCE_SHULKER_BOX_BLOCK_ENTITY.get();
    }
}