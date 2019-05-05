package com.meteor.extrabotany.common.item.equipment.armor.cosmeticmaid;

import com.meteor.extrabotany.common.lib.LibItemsName;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;
import thaumcraft.api.items.IGoggles;
import thaumcraft.api.items.IRevealer;

@Optional.InterfaceList({
	@Optional.Interface(modid = "thaumcraft", iface = "thaumcraft.api.items.IGoggles", striprefs = true),
	@Optional.Interface(modid = "thaumcraft", iface = "thaumcraft.api.items.IRevealer", striprefs = true)})
public class ItemCosmeticMaidHelmRevealing extends ItemCosmeticMaidHelm implements IGoggles, IRevealer {

	public ItemCosmeticMaidHelmRevealing(){
		super(LibItemsName.COSMHELMREVEALING);
	}

	@Override
	public boolean showNodes(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	public boolean showIngamePopups(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}
}
