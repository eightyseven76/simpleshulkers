package net.eightyseven.simpleshulkers.item;

import net.minecraft.world.level.block.Block;
import java.util.function.Supplier;

public class SpruceShulkerBoxItem extends BaseShulkerBoxItem {
    public SpruceShulkerBoxItem(Supplier<? extends Block> blockSupplier, Properties properties) {
        super(blockSupplier, properties);
    }
}