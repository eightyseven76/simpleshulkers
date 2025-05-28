package net.eightyseven.simpleshulkers.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
 
public class BirchShulkerShellItem extends Item {
    public BirchShulkerShellItem() {
        super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
    }
} 