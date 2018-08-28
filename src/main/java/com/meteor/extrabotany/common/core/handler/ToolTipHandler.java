package com.meteor.extrabotany.common.core.handler;

import com.meteor.extrabotany.common.block.ModBlocks;
import com.meteor.extrabotany.common.item.block.ItemBlockMod;
import com.meteor.extrabotany.common.lib.LibBlocksName;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.mana.IManaItem;

public class ToolTipHandler {
	
	@SubscribeEvent
	public void onItemTooltip(ItemTooltipEvent e){
		Item item = e.getItemStack().getItem();
		if(ConfigHandler.ENABLE_TOOLTIP){
			if(item instanceof IManaItem){
				IManaItem manaitem = (IManaItem)item;
				e.getToolTip().add("Mana:" + manaitem.getMana(e.getItemStack()) + "/" + manaitem.getMaxMana(e.getItemStack()));
			}
		}
		if(item.getUnlocalizedNameInefficiently(e.getItemStack()).indexOf(LibBlocksName.TILE_MANAGENERATOR) != -1 && !ConfigHandler.DISABLE_MANAGENERATOR)
			e.getToolTip().add(I18n.format("extrabotanymisc.disabledblock"));
		else if(item.getUnlocalizedNameInefficiently(e.getItemStack()).indexOf(LibBlocksName.TILE_MANALIQUEFYING) != -1 && !ConfigHandler.DISABLE_MANALIQUEFICATION)
			e.getToolTip().add(I18n.format("extrabotanymisc.disabledblock"));	
	}

}
