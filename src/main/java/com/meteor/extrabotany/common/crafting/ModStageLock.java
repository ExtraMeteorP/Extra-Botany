package com.meteor.extrabotany.common.crafting;

import com.meteor.extrabotany.api.item.IAdvancementRequired;
import com.meteor.extrabotany.common.core.handler.StatHandler;

import net.minecraft.advancements.Advancement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModStageLock {

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.isCancelable() && !event.getEntityPlayer().isCreative()) {
			if (event.getItemStack().getItem() instanceof IAdvancementRequired) {
				IAdvancementRequired r = (IAdvancementRequired) event.getItemStack().getItem();
				if (!StatHandler.hasStat(event.getEntityPlayer(), r.getAdvancementName(event.getItemStack())))
					event.setCanceled(true);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onTooltip(ItemTooltipEvent event) {
		if (event.getItemStack().getItem() instanceof IAdvancementRequired) {
			IAdvancementRequired r = (IAdvancementRequired) event.getItemStack().getItem();
			EntityPlayerSP playerSP = Minecraft.getMinecraft().player;
			if (playerSP != null) {
				Advancement adv = StatHandler.getSideAdvancement(r.getAdvancementName(event.getItemStack()));
				if (!StatHandler.hasAdvancement(r.getAdvancementName(event.getItemStack())))
					event.getToolTip().add(TextFormatting.RED + "" + TextFormatting.ITALIC + I18n.format(
							"tooltip.extrabotany.description",
							I18n.format("advancement.extrabotany:" + r.getAdvancementName(event.getItemStack()))));
			}
		}
	}

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event) {
		if (event.getEntity() instanceof EntityPlayer && !event.getEntityLiving().world.isRemote) {
			final EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (player.isCreative()) {
				return;
			}
			for (final EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {
				final ItemStack stack = player.getItemStackFromSlot(slot);
				if (stack.getItem() instanceof IAdvancementRequired) {
					IAdvancementRequired r = (IAdvancementRequired) stack.getItem();
					if (!StatHandler.hasStat(player, r.getAdvancementName(stack))) {
						player.setItemStackToSlot(slot, ItemStack.EMPTY);
						player.dropItem(stack, false);
					}
				}
			}
		}
	}

}
