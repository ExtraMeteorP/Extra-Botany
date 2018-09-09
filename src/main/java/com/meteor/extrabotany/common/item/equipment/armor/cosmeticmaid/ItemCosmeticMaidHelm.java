package com.meteor.extrabotany.common.item.equipment.armor.cosmeticmaid;

import javax.annotation.Nullable;

import com.meteor.extrabotany.common.lib.LibItemsName;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.mana.IManaDiscountArmor;

public class ItemCosmeticMaidHelm  extends ItemCosmeticMaidArmor implements IManaDiscountArmor{
	
	public ItemCosmeticMaidHelm(){
		this(LibItemsName.COSMHELM);
	}
	
	public ItemCosmeticMaidHelm(String name) {
		super(EntityEquipmentSlot.HEAD, name);
	}
	
	@Override
	public float getDiscount(ItemStack stack, int slot, EntityPlayer player, @Nullable ItemStack tool) {
		return hasArmorSet(player) ? 0.6F : 0F;
	}
	
}
