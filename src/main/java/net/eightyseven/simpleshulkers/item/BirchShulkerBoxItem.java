package net.eightyseven.simpleshulkers.item;

import net.minecraft.world.level.block.Block;
import java.util.function.Supplier;

public class BirchShulkerBoxItem extends BaseShulkerBoxItem {
    public BirchShulkerBoxItem(Supplier<? extends Block> blockSupplier, Properties properties) {
        super(blockSupplier, properties);
    }
}