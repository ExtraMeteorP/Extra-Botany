package com.meteor.extrabotany.common.brew.potion;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.brew.ModPotions;
import com.meteor.extrabotany.common.lib.LibPotionsName;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionReflect extends PotionMod{

	public PotionReflect() {
		super(LibPotionsName.REFLECT, false, 0X4169E1, 2);
		MinecraftForge.EVENT_BUS.register(this);
		setBeneficial();
	}
	
	@SubscribeEvent
	public void onLivingAttacked(LivingAttackEvent event) {
		EntityLivingBase attacked = event.getEntityLiving();
		DamageSource source = event.getSource();
		if(attacked.isPotionActive(ModPotions.reflect) && source.getImmediateSource() != null){
			int level = attacked.getActivePotionEffect(ModPotions.reflect).getAmplifier();
			float dmg = event.getAmount() / Math.max(1, 6-level);
			source.getImmediateSource().attackEntityFrom(DamageSource.CACTUS, dmg);
			if(source.getImmediateSource() instanceof EntityLivingBase)
				ExtraBotanyAPI.dealTrueDamage((EntityLivingBase) source.getImmediateSource(), level);
		}
	}

}
