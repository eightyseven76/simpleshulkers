package net.eightyseven.simpleshulkers.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.eightyseven.simpleshulkers.block.BaseShulkerBoxBlock; // Your base block class
import net.eightyseven.simpleshulkers.block.entity.BaseShulkerBoxBlockEntity; // Your base block entity class
import net.minecraft.client.model.ShulkerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB; // Import AABB
import net.minecraft.core.BlockPos; // Import BlockPos
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BaseShulkerBoxRenderer<T extends BaseShulkerBoxBlockEntity> implements BlockEntityRenderer<T> {
    private final ShulkerModel<?> model;
    private final ResourceLocation textureLocation;

    public BaseShulkerBoxRenderer(BlockEntityRendererProvider.Context context, ResourceLocation textureLocation) {
        this.model = new ShulkerModel<>(context.bakeLayer(ModelLayers.SHULKER));
        this.textureLocation = textureLocation;
    }

    @Override
    public void render(T blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Direction direction = Direction.UP; // Default direction
        if (blockEntity.hasLevel()) {
            BlockState blockstate = blockEntity.getBlockState();
            // Ensure the block is an instance of your BaseShulkerBoxBlock or a specific shulker block
            if (blockstate.getBlock() instanceof BaseShulkerBoxBlock) {
                direction = blockstate.getValue(BaseShulkerBoxBlock.FACING);
            }
        }

        Material material = new Material(Sheets.SHULKER_SHEET, this.textureLocation);

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.5F, 0.5F);
        float scale = 0.9995F; // Standard shulker scale
        poseStack.scale(scale, scale, scale);
        poseStack.mulPose(direction.getRotation());
        poseStack.scale(1.0F, -1.0F, -1.0F); // Standard shulker model orientation
        poseStack.translate(0.0F, -1.0F, 0.0F);

        ModelPart lid = this.model.getLid();
        float progress = blockEntity.getProgress(partialTicks); // getProgress should be in BaseShulkerBoxBlockEntity

        // Lid animation (same as vanilla)
        lid.setPos(0.0F, 24.0F - progress * 0.5F * 16.0F, 0.0F);
        lid.yRot = 270.0F * progress * ((float)Math.PI / 180.0F);

        VertexConsumer vertexconsumer = material.buffer(bufferSource, RenderType::entityCutoutNoCull);
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, packedOverlay);
        poseStack.popPose();
    }

    // Common rendering bounding box, adjust if shulkers have different visual bounds
    @Override
    public AABB getRenderBoundingBox(T blockEntity) {
        BlockPos pos = blockEntity.getBlockPos();
        // Extend bounds slightly to prevent culling when the lid is open
        return new AABB(
                pos.getX() - 0.5, pos.getY() - 0.5, pos.getZ() - 0.5,
                pos.getX() + 1.5, pos.getY() + 1.5, pos.getZ() + 1.5
        );
    }
}