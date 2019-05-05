package com.meteor.extrabotany.common.item.equipment.armor.cosmeticmaid;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.lib.LibAdvancements;
import com.meteor.extrabotany.common.lib.LibItemsName;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import vazkii.botania.api.mana.IManaDiscountArmor;

import javax.annotation.Nullable;

public class ItemCosmeticMaidHelm  extends ItemCosmeticMaidArmor implements IManaDiscountArmor{
	
	public ItemCosmeticMaidHelm(){
		this(LibItemsName.COSMHELM);
	}
	
	public ItemCosmeticMaidHelm(String name) {
		super(EntityEquipmentSlot.HEAD, name);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		super.onArmorTick(world, player, stack);
		if(hasArmorSet(player)) {
			ExtraBotanyAPI.unlockAdvancement(player, LibAdvancements.ARMORSET_COS);
		}
	}
	
	@Override
	public float getDiscount(ItemStack stack, int slot, EntityPlayer player, @Nullable ItemStack tool) {
		return hasArmorSet(player) ? 0.6F : 0F;
	}
	
}
