package com.meteor.extrabotany.api.item;

import net.minecraft.item.ItemStack;

public interface INatureOrb {
	
	public void addXP(ItemStack stack, int xp);
	
	public void setXP(ItemStack stack, int xp);

	public int getXP(ItemStack stack);

}
