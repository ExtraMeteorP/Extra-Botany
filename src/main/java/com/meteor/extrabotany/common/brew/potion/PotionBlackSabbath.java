package com.meteor.extrabotany.common.brew.potion;

import com.google.common.collect.Lists;
import com.meteor.extrabotany.common.lib.LibPotionsName;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public class PotionBlackSabbath extends PotionMod{

	public PotionBlackSabbath() {
		super(LibPotionsName.BLACKSABBATH, false, 0X000000, 3);
		setBeneficial();
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration < 5;
	}

	@Override
	public void performEffect(@Nonnull EntityLivingBase living, int amplified) {
		living.onKillCommand();
	}
	
	@Override
	public List<ItemStack> getCurativeItems(){
		List<ItemStack> list = Lists.newArrayList();
		return list;
	}

}
