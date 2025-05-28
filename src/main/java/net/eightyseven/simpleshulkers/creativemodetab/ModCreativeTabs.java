package net.eightyseven.simpleshulkers.creativemodetab;

import net.eightyseven.simpleshulkers.SimpleShulkersMod;
import net.eightyseven.simpleshulkers.item.ModItems; // Import ModItems for shell items
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SimpleShulkersMod.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SIMPLE_SHULKERS_TAB = CREATIVE_MODE_TABS.register("simple_shulkers_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("creativetab.simpleshulkers.simple_shulkers_tab"))
                    .icon(() -> new ItemStack(ModItems.OAK_SHULKER_BOX_ITEM.get())) // Use ModItems for consistency
                    .displayItems((parameters, output) -> {
                        // Add Shells
                        output.accept(ModItems.OAK_SHULKER_SHELL.get());
                        output.accept(ModItems.SPRUCE_SHULKER_SHELL.get());
                        output.accept(ModItems.BIRCH_SHULKER_SHELL.get());

                        // Add Shulker Box Items
                        output.accept(ModItems.OAK_SHULKER_BOX_ITEM.get());
                        output.accept(ModItems.SPRUCE_SHULKER_BOX_ITEM.get());
                        output.accept(ModItems.BIRCH_SHULKER_BOX_ITEM.get());
                        // When adding new shulkers, add their items here:
                        // output.accept(ModItems.JUNGLE_SHULKER_SHELL.get());
                        // output.accept(ModItems.JUNGLE_SHULKER_BOX.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}