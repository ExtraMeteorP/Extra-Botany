package com.meteor.extrabotany.common.item;

import com.meteor.extrabotany.common.lib.LibItemsName;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemSpiritFuel extends ItemFoodMod implements IFuelHandler{

	public ItemSpiritFuel() {
		super(0, 6, false, LibItemsName.SPIRITFUEL);
		GameRegistry.registerFuelHandler(this);
	}
	
	public void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player){
		player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 1200, 2));
		player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 1200, 2));
		player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1200, 2));
		player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 1200, 2));
    }
	
	@Override
	public int getBurnTime(ItemStack fuel) {
		return fuel.getItem() == ModItems.nightmareFuel ? 2400 : 0;
	}

}
