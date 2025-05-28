package net.eightyseven.simpleshulkers.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
 
public class SpruceShulkerShellItem extends Item {
    public SpruceShulkerShellItem() {
        super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
    }
} 