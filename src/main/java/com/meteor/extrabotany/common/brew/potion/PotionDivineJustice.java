package com.meteor.extrabotany.common.brew.potion;

import com.google.common.collect.Lists;
import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.lib.LibPotionsName;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public class PotionDivineJustice extends PotionMod{

	public PotionDivineJustice() {
		super(LibPotionsName.DIVINEJUSTICE, false, 0XFF7F50, 5);
		registerPotionAttributeModifier(SharedMonsterAttributes.MAX_HEALTH, "FB353E1C-4180-4865-B01B-BCCE9785ACA3", -0.05F, 1);
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration == 5;
	}

	@Override
	public void performEffect(@Nonnull EntityLivingBase living, int amplified) {
		ExtraBotanyAPI.dealTrueDamage(living, amplified*2);
	}
	
	@Override
	public List<ItemStack> getCurativeItems(){
		List<ItemStack> list = Lists.newArrayList();
		return list;
	}

}
