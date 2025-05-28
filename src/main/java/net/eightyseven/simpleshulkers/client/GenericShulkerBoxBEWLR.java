package net.eightyseven.simpleshulkers.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.eightyseven.simpleshulkers.block.BaseShulkerBoxBlock;
import net.eightyseven.simpleshulkers.block.entity.BaseShulkerBoxBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GenericShulkerBoxBEWLR extends BlockEntityWithoutLevelRenderer {
    // Cache for dummy block entities to avoid creating them every frame
    private final Map<Block, BlockEntity> dummyCache = new ConcurrentHashMap<>();

    public GenericShulkerBoxBEWLR() {
        super(null, null); // Parameters are not directly used by us in modern versions
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemDisplayContext displayContext, PoseStack poseStack,
                             MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (itemStack.getItem() instanceof BlockItem blockItem) {
            Block block = blockItem.getBlock();
            if (block instanceof BaseShulkerBoxBlock) { // Check if it's one of our shulker blocks
                BlockEntity dummyBE = dummyCache.computeIfAbsent(block, k -> {
                    // Create a new dummy BE instance for this block type
                    BlockState defaultState = k.defaultBlockState();
                    // Attempt to create the correct BlockEntity type associated with the block.
                    // This assumes BaseShulkerBoxBlock subclasses correctly provide their BlockEntity.
                    // The BaseShulkerBoxBlock does not directly create the BE, its subclasses do.
                    // So, we rely on the block's newBlockEntity method.
                    return ((BaseShulkerBoxBlock) k).newBlockEntity(BlockPos.ZERO, defaultState);
                });

                if (dummyBE instanceof BaseShulkerBoxBlockEntity) {
                    // Load potential custom name or other relevant data from itemStack to dummyBE if needed for rendering
                    // For now, we assume the renderer for BaseShulkerBoxBlockEntity handles texture based on its type.
                    // If not, the BEWLR or renderer would need more info from the ItemStack.
                    ((BaseShulkerBoxBlockEntity) dummyBE).loadFromItem(itemStack, Minecraft.getInstance().level != null ? Minecraft.getInstance().level.registryAccess() : null);


                    Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(dummyBE, poseStack, bufferSource, packedLight, packedOverlay);
                }
            }
        }
    }
}