package com.meteor.extrabotany.common.brew.potion;

import java.util.List;

import com.google.common.collect.Lists;
import com.meteor.extrabotany.common.brew.ModPotions;
import com.meteor.extrabotany.common.lib.LibPotionsName;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionWitchCurse extends PotionMod{

	public PotionWitchCurse() {
		super(LibPotionsName.WITCHCURSE, true, 0X000000, 8);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onUpdate(LivingHealEvent event) {
		if(event.getEntityLiving().isPotionActive(ModPotions.witchcurse))
			event.setAmount(event.getAmount() / event.getEntityLiving().getActivePotionEffect(ModPotions.witchcurse).getAmplifier());
	}
	
	@Override
	public List<ItemStack> getCurativeItems(){
		List<ItemStack> list = Lists.newArrayList();
		return list;
	}

}
