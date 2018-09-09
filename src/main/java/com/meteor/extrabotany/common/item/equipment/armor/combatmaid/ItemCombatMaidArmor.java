package com.meteor.extrabotany.common.item.equipment.armor.combatmaid;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.Multimap;
import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.client.model.ModelArmorCombatMaid;
import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.item.equipment.armor.cosmeticmaid.ItemCosmeticMaidArmor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class ItemCombatMaidArmor extends ItemCosmeticMaidArmor{

	public ItemCombatMaidArmor(EntityEquipmentSlot type, String name) {
		super(type, name, ExtraBotanyAPI.orichalcosArmorMaterial);
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> attrib = super.getAttributeModifiers(slot, stack);
		UUID uuid = new UUID((getUnlocalizedName() + slot.toString()).hashCode(), 0);
		boolean night = ItemNBTHelper.getBoolean(stack, "isnight", false);
		if (slot == armorType) {
			attrib.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), new AttributeModifier(uuid, "Combatmaid modifier " + type, (double) getArmorDisplay(null, new ItemStack(this), type.getIndex()) / 20, 0));
			attrib.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(uuid, "Combatmaid modifier " + type, night ? 25 : 5, 0));
			attrib.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(uuid, "Combatmaid modifier " + type, night ? 0.3F : 0, 1));
			attrib.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(uuid, "Combatmaid modifier " + type, night ? 1F : 0, 1));
			attrib.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(uuid, "Combatmaid modifier " + type, night ? 0.2F : 0, 1));
		}
		return attrib;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped provideArmorModelForSlot(ItemStack stack, EntityEquipmentSlot slot) {
		models.put(slot, new ModelArmorCombatMaid(slot));
		return models.get(slot);
	}
	
	public String getArmorTextureAfterInk(ItemStack stack, EntityEquipmentSlot slot) {
		return "extrabotany:textures/model/armor_combatmaid.png";
	}

	static ItemStack[] armorset;

	@Override
	public ItemStack[] getArmorSetStacks() {
		if(armorset == null)
			armorset = new ItemStack[] {
					new ItemStack(ModItems.cmhelm),
					new ItemStack(ModItems.cmchest),
					new ItemStack(ModItems.cmleg),
					new ItemStack(ModItems.cmboot)
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
		case 0: return stack.getItem() == ModItems.cmhelm || stack.getItem() == ModItems.cmhelmrevealing;
		case 1: return stack.getItem() == ModItems.cmchest || stack.getItem() == ModItems.cmchestdarkened;
		case 2: return stack.getItem() == ModItems.cmleg;
		case 3: return stack.getItem() == ModItems.cmboot;
		}

		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public String getArmorSetName() {
		return I18n.format("extrabotany.armorset.combatmaid.name");
	}


	@SideOnly(Side.CLIENT)
	@Override
	public void addArmorSetDescription(ItemStack stack, List<String> list) {
		addStringToTooltip(I18n.format("extrabotany.armorset.combatmaid.desc0"), list);
		addStringToTooltip(I18n.format("extrabotany.armorset.combatmaid.desc1"), list);
		addStringToTooltip(I18n.format("extrabotany.armorset.combatmaid.desc2"), list);
	}

}
