package com.meteor.extrabotany.common.core.handler;

import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.api.mana.IManaItem;

public class ToolTipHandler {
	
	@SubscribeEvent
	public void onItemTooltip(ItemTooltipEvent e){
		Item item = e.getItemStack().getItem();
		if(item instanceof IManaItem && ConfigHandler.ENABLE_TOOLTIP){
			IManaItem manaitem = (IManaItem)item;
			e.getToolTip().add("Mana:" + manaitem.getMana(e.getItemStack()) + "/" + manaitem.getMaxMana(e.getItemStack()));
		}
	}

}
