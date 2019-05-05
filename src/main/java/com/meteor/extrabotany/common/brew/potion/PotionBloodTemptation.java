package com.meteor.extrabotany.common.brew.potion;

import com.google.common.collect.Lists;
import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.brew.ModPotions;
import com.meteor.extrabotany.common.lib.LibPotionsName;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import java.util.List;

public class PotionBloodTemptation extends PotionMod{

	public PotionBloodTemptation() {
		super(LibPotionsName.BLOODTEMPTATION, true, 0XDC143C, 6);
		registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "8107BD5F-4CF8-4030-441D-534C1F140890", 0.05F, 1);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onTakenDamage(LivingHurtEvent event) {
		Entity attacker = event.getSource().getImmediateSource();
		if(attacker instanceof EntityLivingBase) {
			EntityLivingBase living = (EntityLivingBase) attacker;
			if(living.isPotionActive(ModPotions.bloodtemptation)){
				int level = living.getActivePotionEffect(ModPotions.bloodtemptation).getAmplifier();
				living.heal(event.getAmount() * 0.1F * level);
				ExtraBotanyAPI.addPotionEffect(event.getEntityLiving(), ModPotions.bloodtemptation, 100, 10, true);
			}
		}
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration % 20 == 0;
	}
	
	@Override
	public void performEffect(@Nonnull EntityLivingBase living, int amplified) {
		if(living instanceof EntityPlayer)
			((EntityPlayer)living).addExhaustion(0.1F * amplified);
	}
	
	@Override
	public List<ItemStack> getCurativeItems(){
		List<ItemStack> list = Lists.newArrayList();
		return list;
	}

}
