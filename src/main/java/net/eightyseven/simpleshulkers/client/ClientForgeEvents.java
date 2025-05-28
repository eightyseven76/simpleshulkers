package net.eightyseven.simpleshulkers.client;

import net.eightyseven.simpleshulkers.SimpleShulkersMod;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Block;
import net.eightyseven.simpleshulkers.block.BaseShulkerBoxBlock;
import net.eightyseven.simpleshulkers.block.entity.BaseShulkerBoxBlockEntity;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import java.util.List;

@EventBusSubscriber(modid = SimpleShulkersMod.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ClientForgeEvents {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final int MAX_DISPLAY_ITEMS = 5;

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Block block = Block.byItem(stack.getItem());

        if (block instanceof BaseShulkerBoxBlock) {
            List<Component> tooltip = event.getToolTip();
            CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
            boolean hasItemsInNBT = false;

            if (customData != null) {
                CompoundTag blockEntityTag = customData.getUnsafe();
                if (blockEntityTag.contains("Items", CompoundTag.TAG_LIST)) {
                    NonNullList<ItemStack> items = NonNullList.withSize(BaseShulkerBoxBlockEntity.CONTAINER_SIZE, ItemStack.EMPTY);

                    HolderLookup.Provider registries = event.getEntity() != null && event.getEntity().level() != null ?
                            event.getEntity().level().registryAccess() : null;
                    if (registries != null) {
                        ContainerHelper.loadAllItems(blockEntityTag, items, registries);
                    } else {
                        LOGGER.warn("Could not get HolderLookup.Provider for shulker tooltip at item: {}", stack);
                    }


                    int displayedItems = 0;
                    int remainingItems = 0;

                    for (ItemStack itemEntry : items) {
                        if (!itemEntry.isEmpty()) {
                            hasItemsInNBT = true;
                            if (displayedItems < MAX_DISPLAY_ITEMS) {
                                tooltip.add(Component.translatable("container.shulkerBox.itemCount",
                                        itemEntry.getHoverName(),
                                        itemEntry.getCount()
                                ).withStyle(ChatFormatting.WHITE));
                                displayedItems++;
                            } else {
                                remainingItems++;
                            }
                        }
                    }

                    if (remainingItems > 0) {
                        tooltip.add(Component.translatable("container.shulkerBox.more", remainingItems)
                                .withStyle(ChatFormatting.WHITE, ChatFormatting.ITALIC));
                    }
                }
            }

            if (!hasItemsInNBT) {
                boolean hasCustomName = stack.has(DataComponents.CUSTOM_NAME);
                boolean customDataIsEmptyOrNull = (customData == null || customData.getUnsafe().isEmpty());
                boolean noItemsTagInCustomData = (customData != null && !customData.getUnsafe().contains("Items", CompoundTag.TAG_LIST));

                /*if (!hasCustomName && (customDataIsEmptyOrNull || noItemsTagInCustomData) ) {
                    tooltip.add(Component.translatable("tooltip.simpleshulkers.empty")
                            .withStyle(ChatFormatting.WHITE, ChatFormatting.ITALIC));
                }
                 */
            }
        }
    }
}