/****************************************
 ************WORK IN PROGRESS************
 ****************************************/

package net.eightyseven.simpleshulkers;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = SimpleShulkersMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE_OAK_SHULKER_BOX;
    public static final ModConfigSpec.BooleanValue ENABLE_SPRUCE_SHULKER_BOX;
    public static final ModConfigSpec.BooleanValue ENABLE_BIRCH_SHULKER_BOX;
    // Add more here as you create new types, e.g.:
    // public static final ModConfigSpec.BooleanValue ENABLE_JUNGLE_SHULKER;

    static {
        BUILDER.push("Shulker Type Settings");

        ENABLE_OAK_SHULKER_BOX = BUILDER
                .comment("Enable the Oak Shulker Box and its components.")
                .define("enableOakShulkerBox", true);

        ENABLE_SPRUCE_SHULKER_BOX = BUILDER
                .comment("Enable the Spruce Shulker Box and its components.")
                .define("enableSpruceShulkerBox", true);

        ENABLE_BIRCH_SHULKER_BOX = BUILDER
                .comment("Enable the Birch Shulker Box and its components.")
                .define("enableBirchShulkerBox", true);

        // Example for a new type:
        // ENABLE_JUNGLE_SHULKER = BUILDER
        //        .comment("Enable the Jungle Shulker Box and its components.")
        //        .define("enableJungleShulker", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    static final ModConfigSpec SPEC;

    // Public static fields to hold the loaded config values
    public static boolean enableOakShulker;
    public static boolean enableSpruceShulker;
    public static boolean enableBirchShulker;
    // public static boolean enableJungleShulker;


    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == Config.SPEC) {
            enableOakShulker = ENABLE_OAK_SHULKER_BOX.get();
            enableSpruceShulker = ENABLE_SPRUCE_SHULKER_BOX.get();
            enableBirchShulker = ENABLE_BIRCH_SHULKER_BOX.get();
            // enableJungleShulker = ENABLE_JUNGLE_SHULKER.get();

            // You can log these values if needed
            // SimpleShulkersMod.LOGGER.info("Config loaded: Oak Shulker Enabled: {}", enableOakShulker);
        }
    }
}