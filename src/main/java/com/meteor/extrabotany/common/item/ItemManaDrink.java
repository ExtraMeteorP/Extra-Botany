package com.meteor.extrabotany.common.item;

import com.meteor.extrabotany.common.lib.LibItemsName;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import vazkii.botania.api.mana.ManaItemHandler;

public class ItemManaDrink extends ItemFoodMod{

	public ItemManaDrink() {
		super(1, 1F, false, LibItemsName.MANADRINK);
	}
	
	@Override
	public void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player){
		super.onFoodEaten(stack, worldIn, player);	
		if(player.shouldHeal())
			player.heal(5F);
		player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 1200, 0));
		player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 1200, 0));
		player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1200, 0));
		player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 1200, 0));
		ManaItemHandler.dispatchManaExact(stack, player, 10000, true);
	}

}
