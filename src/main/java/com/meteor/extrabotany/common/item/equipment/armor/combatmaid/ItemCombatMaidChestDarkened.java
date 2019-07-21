package com.meteor.extrabotany.common.item.equipment.armor.combatmaid;

import com.meteor.extrabotany.common.lib.LibItemsName;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class ItemCombatMaidChestDarkened extends ItemCombatMaidChest {

	public static final String TAG_NIGHT = "isnight";

	public ItemCombatMaidChestDarkened() {
		super(LibItemsName.CMCHESTDARKENED);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		super.onArmorTick(world, player, stack);
		if (hasArmorSet(player) && !world.isDaytime()) {
			ItemNBTHelper.setBoolean(stack, TAG_NIGHT, true);
			player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 300, 1));
		} else
			ItemNBTHelper.setBoolean(stack, TAG_NIGHT, false);
	}

}
