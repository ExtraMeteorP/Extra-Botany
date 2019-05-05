package com.meteor.extrabotany.common.brew.potion;

import com.google.common.collect.Lists;
import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.brew.ModPotions;
import com.meteor.extrabotany.common.lib.LibPotionsName;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class PotionConstantPain extends PotionMod{

	public PotionConstantPain() {
		super(LibPotionsName.CONSTANTPATIN, false, 0X808080, 4);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onTakenDamage(LivingHurtEvent event) {
		if(event.getEntityLiving().isPotionActive(ModPotions.constantpain)){
			int level = event.getEntityLiving().getActivePotionEffect(ModPotions.constantpain).getAmplifier();
			ExtraBotanyAPI.dealTrueDamage(event.getEntityLiving(), level);
		}
	}
	
	@Override
	public List<ItemStack> getCurativeItems(){
		List<ItemStack> list = Lists.newArrayList();
		return list;
	}

}
