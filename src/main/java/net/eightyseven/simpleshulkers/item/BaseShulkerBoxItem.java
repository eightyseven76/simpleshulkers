package net.eightyseven.simpleshulkers.item;

import net.eightyseven.simpleshulkers.client.GenericShulkerBoxBEWLR; // Use the new generic BEWLR
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class BaseShulkerBoxItem extends BlockItem {

    public BaseShulkerBoxItem(Supplier<? extends Block> blockSupplier, Properties properties) {
        super(blockSupplier.get(), properties.stacksTo(1)); // Shulkers should stack to 1
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false; // Prevents nesting for all shulkers extending this
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private BlockEntityWithoutLevelRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    this.renderer = new GenericShulkerBoxBEWLR();
                }
                return this.renderer;
            }
        });
    }
}