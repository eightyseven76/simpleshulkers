package net.eightyseven.simpleshulkers;

import com.mojang.logging.LogUtils;
import net.eightyseven.simpleshulkers.block.ModBlocks;
import net.eightyseven.simpleshulkers.block.entity.ModBlockEntities;
import net.eightyseven.simpleshulkers.item.ModItems;
import net.eightyseven.simpleshulkers.creativemodetab.ModCreativeTabs;
import net.eightyseven.simpleshulkers.inventory.ModMenuTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(SimpleShulkersMod.MODID)
public class SimpleShulkersMod {
    public static final String MODID = "simpleshulkers";
    private static final Logger LOGGER = LogUtils.getLogger();

    public SimpleShulkersMod(IEventBus modEventBus) {
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus); // Corrected from BLOCK_ENTITIES.register(eventBus);
        ModItems.ITEMS.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        ModMenuTypes.MENU_TYPES.register(modEventBus);

        // Client-side events are handled by @EventBusSubscriber in ClientModEvents.java
        // Common setup events are handled by @EventBusSubscriber in ModEvents.java

        LOGGER.info("SimpleShulkersMod loaded and registers initiated!");
    }
}