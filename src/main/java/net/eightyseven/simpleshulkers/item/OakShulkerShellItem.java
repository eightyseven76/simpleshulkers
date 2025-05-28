
package net.eightyseven.simpleshulkers.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class OakShulkerShellItem extends Item {
    public OakShulkerShellItem() {
        super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
    }
}
