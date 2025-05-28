package net.eightyseven.simpleshulkers.item;

import net.eightyseven.simpleshulkers.SimpleShulkersMod;
import net.eightyseven.simpleshulkers.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(BuiltInRegistries.ITEM, SimpleShulkersMod.MODID);

    // Register shell items
    public static final DeferredHolder<Item, OakShulkerShellItem> OAK_SHULKER_SHELL =
            ITEMS.register("oak_shulker_shell", OakShulkerShellItem::new);

    public static final DeferredHolder<Item, SpruceShulkerShellItem> SPRUCE_SHULKER_SHELL =
            ITEMS.register("spruce_shulker_shell", SpruceShulkerShellItem::new);

    public static final DeferredHolder<Item, BirchShulkerShellItem> BIRCH_SHULKER_SHELL =
            ITEMS.register("birch_shulker_shell", BirchShulkerShellItem::new);

    // Register shulker items - using the new specific item classes extending BaseShulkerBoxItem
    public static final DeferredHolder<Item, Item> OAK_SHULKER_BOX_ITEM = ITEMS.register("oak_shulker_box",
            () -> new OakShulkerBoxItem(ModBlocks.OAK_SHULKER_BOX, new Item.Properties())); // .stacksTo(1) is now in BaseShulkerBoxItem

    public static final DeferredHolder<Item, Item> SPRUCE_SHULKER_BOX_ITEM = ITEMS.register("spruce_shulker_box",
            () -> new SpruceShulkerBoxItem(ModBlocks.SPRUCE_SHULKER_BOX, new Item.Properties()));

    public static final DeferredHolder<Item, Item> BIRCH_SHULKER_BOX_ITEM = ITEMS.register("birch_shulker_box",
            () -> new BirchShulkerBoxItem(ModBlocks.BIRCH_SHULKER_BOX, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}