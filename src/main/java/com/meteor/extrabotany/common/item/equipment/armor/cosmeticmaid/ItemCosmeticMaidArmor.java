package com.meteor.extrabotany.common.item.equipment.armor.cosmeticmaid;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Multimap;
import com.meteor.extrabotany.client.model.ModelArmorCosmeticMaid;
import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.item.equipment.armor.combatmaid.ItemCombatMaidArmor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.mana.IManaDiscountArmor;

public class ItemCosmeticMaidArmor extends ItemCombatMaidArmor implements IManaDiscountArmor{
	
	public ItemCosmeticMaidArmor(EntityEquipmentSlot type, String name) {
		super(type, name, BotaniaAPI.manasteelArmorMaterial);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped provideArmorModelForSlot(ItemStack stack, EntityEquipmentSlot slot) {
		models.put(slot, new ModelArmorCosmeticMaid(slot));
		return models.get(slot);
	}
	
	@Override
	public float getDiscount(ItemStack stack, int slot, EntityPlayer player, @Nullable ItemStack tool) {
		return hasArmorSet(player) ? 0.6F : 0F;
	}
	
	@Override
	public String getArmorTextureAfterInk(ItemStack stack, EntityEquipmentSlot slot) {
		return "extrabotany:textures/model/armor_cosmeticmaid.png";
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> attrib = super.getAttributeModifiers(slot, stack);
		attrib.removeAll(SharedMonsterAttributes.ARMOR.getName());
		attrib.removeAll(SharedMonsterAttributes.ARMOR_TOUGHNESS.getName());
		attrib.removeAll(SharedMonsterAttributes.MAX_HEALTH.getName());
		attrib.removeAll(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName());
		return attrib;
	}
	
	private static ItemStack[] armorset;

	@Override
	public ItemStack[] getArmorSetStacks() {
		if(armorset == null)
			armorset = new ItemStack[] {
					new ItemStack(ModItems.cosmhelm),
					new ItemStack(ModItems.cosmchest),
					new ItemStack(ModItems.cosmleg),
					new ItemStack(ModItems.cosmboot)
		};

		return armorset;
	}

	@Override
	public boolean hasArmorSetItem(EntityPlayer player, int i) {
		if(player == null || player.inventory == null || player.inventory.armorInventory == null)
			return false;
		
		ItemStack stack = player.inventory.armorInventory.get(3 - i);
		if(stack.isEmpty())
			return false;

		switch(i) {
		case 0: return stack.getItem() == ModItems.cosmhelm;
		case 1: return stack.getItem() == ModItems.cosmchest;
		case 2: return stack.getItem() == ModItems.cosmleg;
		case 3: return stack.getItem() == ModItems.cosmboot;
		}

		return false;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addArmorSetDescription(ItemStack stack, List<String> list) {
		addStringToTooltip(I18n.format("extrabotany.armorset.cosmeticmaid.desc0"), list);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public String getArmorSetName() {
		return I18n.format("extrabotany.armorset.cosmeticmaid.name");
	}
	
}
