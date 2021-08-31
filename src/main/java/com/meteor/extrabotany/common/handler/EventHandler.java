package com.meteor.extrabotany.common.handler;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vazkii.botania.api.mana.IManaItem;

@Mod.EventBusSubscriber
public final class EventHandler {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void tooltipEvent(ItemTooltipEvent event){
        ItemStack stack = event.getItemStack();
        if(stack.getItem() instanceof IManaItem){
            IManaItem item = (IManaItem) stack.getItem();
            event.getToolTip().add(new TranslationTextComponent("Mana:" + item.getMana(stack) + "/" + item.getMaxMana(stack)));
        }
    }

}
