package net.eightyseven.simpleshulkers.event;

import net.eightyseven.simpleshulkers.SimpleShulkersMod;
import net.eightyseven.simpleshulkers.item.BaseShulkerBoxItem; // Import the base item
import net.eightyseven.simpleshulkers.item.ModItems;
import net.minecraft.core.dispenser.ShulkerBoxDispenseBehavior;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredHolder;

@EventBusSubscriber(modid = SimpleShulkersMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // This behavior is for vanilla ShulkerBoxBlock, but also works for blocks behaving similarly.
            // If your BaseShulkerBoxBlock behaves sufficiently like ShulkerBoxBlock for dispenser purposes, this is fine.
            // Otherwise, you might need a custom dispense behavior.
            ShulkerBoxDispenseBehavior shulkerDispenseBehavior = new ShulkerBoxDispenseBehavior();

            for (DeferredHolder<Item, ? extends Item> itemHolder : ModItems.ITEMS.getEntries()) {
                Item item = itemHolder.get(); // .get() is correct here to get the Item instance
                // Check if the item is an instance of your BaseShulkerBoxItem
                if (item instanceof BaseShulkerBoxItem) {
                    DispenserBlock.registerBehavior(item, shulkerDispenseBehavior);
                }
            }
        });
    }
}