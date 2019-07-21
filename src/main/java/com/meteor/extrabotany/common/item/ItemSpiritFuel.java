package com.meteor.extrabotany.common.item;

import com.meteor.extrabotany.common.lib.LibItemsName;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemSpiritFuel extends ItemFoodMod {

	public ItemSpiritFuel() {
		super(0, 0, false, LibItemsName.SPIRITFUEL);
	}

	public void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 500, 1));
		player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 500, 1));
		player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 500, 1));
		player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 500, 1));
	}

	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		return 1400;
	}

}
