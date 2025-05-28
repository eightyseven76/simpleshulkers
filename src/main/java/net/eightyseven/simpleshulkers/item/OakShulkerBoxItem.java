package net.eightyseven.simpleshulkers.item;

import net.minecraft.world.level.block.Block;
import java.util.function.Supplier;

public class OakShulkerBoxItem extends BaseShulkerBoxItem {
    public OakShulkerBoxItem(Supplier<? extends Block> blockSupplier, Properties properties) {
        super(blockSupplier, properties);
    }
}