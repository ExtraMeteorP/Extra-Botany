package com.meteor.extrabotany.common.brew.potion;

import java.util.List;

import com.google.common.collect.Lists;
import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.brew.ModPotions;
import com.meteor.extrabotany.common.core.handler.PersistentVariableHandler;
import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.lib.LibPotionsName;

import baubles.api.BaublesApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionVegetable extends PotionMod{

	public PotionVegetable() {
		super(LibPotionsName.VEGETABLE, false, 0X00FA9A, 9);
		MinecraftForge.EVENT_BUS.register(this);
		setBeneficial();
	}
	
	@SubscribeEvent
	public void onPlayerHurt(LivingHurtEvent event) {
		if(!(event.getEntityLiving() instanceof EntityPlayer))
			return;
		if(event.getSource().getImmediateSource() == null || !(event.getSource().getImmediateSource() instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) event.getEntityLiving();
		EntityPlayer attacker = (EntityPlayer) event.getSource().getImmediateSource();
		boolean p = PersistentVariableHandler.contributors.contains(player.getGameProfile().getName()) && BaublesApi.isBaubleEquipped(player, ModItems.mask) == -1;
		boolean a = PersistentVariableHandler.contributors.contains(attacker.getGameProfile().getName()) && BaublesApi.isBaubleEquipped(attacker, ModItems.mask) == -1;
		if(p && !a)
			ExtraBotanyAPI.addPotionEffect(attacker, ModPotions.vegetable, 200, 10, false);
		if(a && !p)
			ExtraBotanyAPI.addPotionEffect(player, ModPotions.vegetable, 200, 10, false);
		if(!a && !p) {
			if(attacker.isPotionActive(ModPotions.vegetable)) {
				int attackerAmp = attacker.getActivePotionEffect(ModPotions.vegetable).getAmplifier();
				int playerAmp = player.isPotionActive(ModPotions.vegetable) ? player.getActivePotionEffect(ModPotions.vegetable).getAmplifier() : 0;
				int hatsToGive = Math.min(10-playerAmp, attackerAmp);
				player.addPotionEffect(new PotionEffect(ModPotions.vegetable, 200, playerAmp + hatsToGive));
				attacker.removePotionEffect(ModPotions.vegetable);
				if(attackerAmp > hatsToGive)
					player.addPotionEffect(new PotionEffect(ModPotions.vegetable, 200, attackerAmp - hatsToGive));
			}
		}
	}
	
	@Override
	public List<ItemStack> getCurativeItems(){
		List<ItemStack> list = Lists.newArrayList();
		return list;
	}

}
