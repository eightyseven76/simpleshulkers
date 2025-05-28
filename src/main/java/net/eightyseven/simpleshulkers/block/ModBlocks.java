package net.eightyseven.simpleshulkers.block;

import net.eightyseven.simpleshulkers.SimpleShulkersMod;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
// import net.neoforged.neoforge.registries.DeferredBlock; // Not used directly, DeferredHolder is used
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.world.level.material.PushReaction;


import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(SimpleShulkersMod.MODID);

    private static final Supplier<BlockBehaviour.Properties> WOOD_SHULKER_PROPERTIES = () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .strength(2.0F, 2.0F)
            .requiresCorrectToolForDrops()
            .sound(SoundType.WOOD)
            .noOcclusion()
            .pushReaction(PushReaction.DESTROY);


    public static final DeferredHolder<Block, Block> OAK_SHULKER_BOX = registerBlock("oak_shulker_box",
            () -> new OakShulkerBoxBlock(WOOD_SHULKER_PROPERTIES.get()));

    public static final DeferredHolder<Block, Block> SPRUCE_SHULKER_BOX = registerBlock("spruce_shulker_box",
            () -> new SpruceShulkerBoxBlock(WOOD_SHULKER_PROPERTIES.get()));

    public static final DeferredHolder<Block, Block> BIRCH_SHULKER_BOX = registerBlock("birch_shulker_box",
            () -> new BirchShulkerBoxBlock(WOOD_SHULKER_PROPERTIES.get()));

    // This helper ONLY registers the block. NO item registration here!
    private static <T extends Block> DeferredHolder<Block, T> registerBlock(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    public static void register(IEventBus eventBus) { // This method isn't strictly needed if BLOCKS is public static final
        BLOCKS.register(eventBus); // and registered directly in the main mod constructor.
    }
}