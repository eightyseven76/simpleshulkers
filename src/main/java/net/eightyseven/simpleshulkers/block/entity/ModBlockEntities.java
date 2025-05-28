package net.eightyseven.simpleshulkers.block.entity;

import net.eightyseven.simpleshulkers.SimpleShulkersMod;
import net.eightyseven.simpleshulkers.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, SimpleShulkersMod.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<OakShulkerBoxBlockEntity>> OAK_SHULKER_BOX_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("oak_shulker_block_entity",
                    () -> BlockEntityType.Builder.of(OakShulkerBoxBlockEntity::new,
                            ModBlocks.OAK_SHULKER_BOX.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SpruceShulkerBoxBlockEntity>> SPRUCE_SHULKER_BOX_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("spruce_shulker_block_entity",
                    () -> BlockEntityType.Builder.of(SpruceShulkerBoxBlockEntity::new,
                            ModBlocks.SPRUCE_SHULKER_BOX.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BirchShulkerBoxBlockEntity>> BIRCH_SHULKER_BOX_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("birch_shulker_block_entity",
                    () -> BlockEntityType.Builder.of(BirchShulkerBoxBlockEntity::new,
                            ModBlocks.BIRCH_SHULKER_BOX.get()).build(null));

    public static void register(IEventBus eventBus) { // This method isn't strictly needed if BLOCK_ENTITIES is public static final
        BLOCK_ENTITIES.register(eventBus); // and registered directly in the main mod constructor.
    }
}