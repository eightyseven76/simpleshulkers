package net.eightyseven.simpleshulkers.client;

import com.mojang.logging.LogUtils;
import net.eightyseven.simpleshulkers.SimpleShulkersMod;
import net.eightyseven.simpleshulkers.block.entity.ModBlockEntities;
import net.eightyseven.simpleshulkers.inventory.ModMenuTypes;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import org.slf4j.Logger;

@EventBusSubscriber(modid = SimpleShulkersMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    private static final Logger LOGGER = LogUtils.getLogger();

    public static final ResourceLocation OAK_SHULKER_BOX_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(SimpleShulkersMod.MODID, "entity/shulker/oak_shulker_box");
    public static final ResourceLocation SPRUCE_SHULKER_BOX_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(SimpleShulkersMod.MODID, "entity/shulker/spruce_shulker_box");
    public static final ResourceLocation BIRCH_SHULKER_BOX_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(SimpleShulkersMod.MODID, "entity/shulker/birch_shulker_box");

    @SubscribeEvent
    public static void registerBER(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.OAK_SHULKER_BOX_BLOCK_ENTITY.get(),
                context -> new BaseShulkerBoxRenderer<>(context, OAK_SHULKER_BOX_TEXTURE));
        event.registerBlockEntityRenderer(ModBlockEntities.SPRUCE_SHULKER_BOX_BLOCK_ENTITY.get(),
                context -> new BaseShulkerBoxRenderer<>(context, SPRUCE_SHULKER_BOX_TEXTURE));
        event.registerBlockEntityRenderer(ModBlockEntities.BIRCH_SHULKER_BOX_BLOCK_ENTITY.get(),
                context -> new BaseShulkerBoxRenderer<>(context, BIRCH_SHULKER_BOX_TEXTURE));
    }

    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.OAK_SHULKER_BOX_MENU.get(), ShulkerBoxScreen::new);
        event.register(ModMenuTypes.SPRUCE_SHULKER_BOX_MENU.get(), ShulkerBoxScreen::new);
        event.register(ModMenuTypes.BIRCH_SHULKER_BOX_MENU.get(), ShulkerBoxScreen::new);
    }
}
