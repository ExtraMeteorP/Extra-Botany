package com.meteor.extrabotany.common.crafting;

import java.util.List;

import com.meteor.extrabotany.api.item.IAdvancementRequired;
import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.core.handler.StatHandler;
import com.meteor.extrabotany.common.entity.gaia.EntityGaiaIII;
import com.meteor.extrabotany.common.entity.gaia.EntityVoidHerrscher;
import net.minecraft.advancements.Advancement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
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
		if(ConfigHandler.DISABLE_ADVANCEMENTREQUIREMENT)
			return;
		if (event.isCancelable() && !event.getEntityPlayer().isCreative()) {
			if (event.getItemStack().getItem() instanceof IAdvancementRequired) {
				IAdvancementRequired r = (IAdvancementRequired) event.getItemStack().getItem();
				if (!StatHandler.hasStat(event.getEntityPlayer(), r.getRequiredAdvancementId(event.getItemStack())))
					event.setCanceled(true);
			}
			
			BlockPos source = event.getEntityPlayer().getPosition();
			float range = 12F;
			List<EntityLivingBase> livings = event.getEntityPlayer().world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range, source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));		
			if(ConfigHandler.GAIA_DISARM)
				for(EntityLivingBase living : livings) {
					if(living instanceof EntityGaiaIII || living instanceof EntityVoidHerrscher)
						if(!EntityGaiaIII.match(event.getItemStack()))
							event.setCanceled(true);
				}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onTooltip(ItemTooltipEvent event) {
		if(ConfigHandler.DISABLE_ADVANCEMENTREQUIREMENT)
			return;
		if (event.getItemStack().getItem() instanceof IAdvancementRequired) {
			IAdvancementRequired r = (IAdvancementRequired) event.getItemStack().getItem();
			EntityPlayerSP playerSP = Minecraft.getMinecraft().player;
			if (playerSP != null) {
				Advancement adv = StatHandler.getSideAdvancement(r.getRequiredAdvancementId(event.getItemStack()));
				if (!StatHandler.hasAdvancement(r.getRequiredAdvancementId(event.getItemStack())))
					event.getToolTip().add(TextFormatting.RED + "" + TextFormatting.ITALIC + I18n.format(
							"tooltip.extrabotany.description",
							I18n.format("advancement.extrabotany:" + r.getAdvancementName(event.getItemStack()))));
			}
		}
	}

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event) {
		if(ConfigHandler.DISABLE_ADVANCEMENTREQUIREMENT)
			return;
		if (event.getEntity() instanceof EntityPlayer && !event.getEntityLiving().world.isRemote) {
			final EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (player.isCreative()) {
				return;
			}
			for (final EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {
				final ItemStack stack = player.getItemStackFromSlot(slot);
				if (stack.getItem() instanceof IAdvancementRequired) {
					IAdvancementRequired r = (IAdvancementRequired) stack.getItem();
					if (!StatHandler.hasStat(player, r.getRequiredAdvancementId(stack))) {
						player.setItemStackToSlot(slot, ItemStack.EMPTY);
						player.dropItem(stack, false);
					}
				}
			}
		}
	}

}
