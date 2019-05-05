package com.meteor.extrabotany.common.item;

import com.meteor.extrabotany.common.lib.LibItemsName;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemGildedMashedPotato extends ItemFoodMod{

	public ItemGildedMashedPotato() {
		super(4, 0.2F, false, LibItemsName.GILDEDMASHEDPOTATO);
	}
	
	@Override
	public void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player){
		super.onFoodEaten(stack, worldIn, player);
		player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 600, 3));
		player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 600, 3));
		player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 600, 1));
	}

}
