package net.eightyseven.simpleshulkers.inventory;

import net.eightyseven.simpleshulkers.SimpleShulkersMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(Registries.MENU, SimpleShulkersMod.MODID);

    // The factory now calls the GenericShulkerBoxMenu constructor that takes (int, Inventory, FriendlyByteBuf)
    // The MenuType is set correctly by the system when this factory is invoked.
    public static final DeferredHolder<MenuType<?>, MenuType<GenericShulkerBoxMenu>> OAK_SHULKER_BOX_MENU =
            MENU_TYPES.register("oak_shulker_box_menu",
                    () -> IMenuTypeExtension.create((windowId, playerInventory, friendlyByteBuf) ->
                            new GenericShulkerBoxMenu(windowId, playerInventory, friendlyByteBuf) // No self-reference here
                    ));

    public static final DeferredHolder<MenuType<?>, MenuType<GenericShulkerBoxMenu>> SPRUCE_SHULKER_BOX_MENU =
            MENU_TYPES.register("spruce_shulker_box_menu",
                    () -> IMenuTypeExtension.create((windowId, playerInventory, friendlyByteBuf) ->
                            new GenericShulkerBoxMenu(windowId, playerInventory, friendlyByteBuf)
                    ));

    public static final DeferredHolder<MenuType<?>, MenuType<GenericShulkerBoxMenu>> BIRCH_SHULKER_BOX_MENU =
            MENU_TYPES.register("birch_shulker_box_menu",
                    () -> IMenuTypeExtension.create((windowId, playerInventory, friendlyByteBuf) ->
                            new GenericShulkerBoxMenu(windowId, playerInventory, friendlyByteBuf)
                    ));

    // Example for a new shulker type (e.g., Jungle)
    // public static final DeferredHolder<MenuType<?>, MenuType<GenericShulkerBoxMenu>> JUNGLE_SHULKER_BOX_MENU =
    //         MENU_TYPES.register("jungle_shulker_box_menu",
    //                 () -> IMenuTypeExtension.create((windowId, playerInventory, friendlyByteBuf) ->
    //                         new GenericShulkerBoxMenu(windowId, playerInventory, friendlyByteBuf)
    //                 ));

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
