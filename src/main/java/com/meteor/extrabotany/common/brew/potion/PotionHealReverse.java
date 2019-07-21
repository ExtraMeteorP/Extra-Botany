package com.meteor.extrabotany.common.brew.potion;

import java.util.List;

import com.google.common.collect.Lists;
import com.meteor.extrabotany.common.brew.ModPotions;
import com.meteor.extrabotany.common.lib.LibPotionsName;

import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionHealReverse extends PotionMod{

	public PotionHealReverse() {
		super(LibPotionsName.HEALREVERSE, true, 0X4B0082, 10);
		MinecraftForge.EVENT_BUS.register(this);
		setBeneficial();
	}
	
	
	@SubscribeEvent
	public void onUpdate(LivingHealEvent event) {
		if(event.getEntityLiving().isPotionActive(ModPotions.healreverse)) {
			event.getEntityLiving().attackEntityFrom(DamageSource.causeIndirectMagicDamage(event.getEntityLiving(), event.getEntityLiving()), event.getAmount());
			event.setAmount(0);
		}
			
	}
	
	@Override
	public List<ItemStack> getCurativeItems(){
		List<ItemStack> list = Lists.newArrayList();
		return list;
	}

}
