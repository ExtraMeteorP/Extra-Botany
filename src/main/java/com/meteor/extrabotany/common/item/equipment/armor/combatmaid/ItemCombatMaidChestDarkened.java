package com.meteor.extrabotany.common.item.equipment.armor.combatmaid;

import java.util.UUID;

import com.google.common.collect.Multimap;
import com.meteor.extrabotany.common.lib.LibItemsName;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class ItemCombatMaidChestDarkened extends ItemCombatMaidChest{
	
	public static final String TAG_NIGHT = "isnight";
	
	public ItemCombatMaidChestDarkened() {
		super(LibItemsName.CMCHESTDARKENED);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		super.onArmorTick(world, player, stack);
		if(hasArmorSet(player) && !world.isDaytime()) {
			ItemNBTHelper.setBoolean(stack, TAG_NIGHT, true);
		}else
			ItemNBTHelper.setBoolean(stack, TAG_NIGHT, false);
	}

}
