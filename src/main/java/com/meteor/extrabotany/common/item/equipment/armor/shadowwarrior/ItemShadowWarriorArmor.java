package com.meteor.extrabotany.common.item.equipment.armor.shadowwarrior;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.Multimap;
import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.api.item.IDarkElfSpawner;
import com.meteor.extrabotany.client.model.ModelArmorShadowWarrior;
import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.item.equipment.armor.cosmeticmaid.ItemCosmeticMaidArmor;
import com.meteor.extrabotany.common.lib.LibAdvancements;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class ItemShadowWarriorArmor extends ItemCosmeticMaidArmor implements IDarkElfSpawner{
	
	public static final String TAG_NIGHT = "isnight";
	
	public ItemShadowWarriorArmor(EntityEquipmentSlot type, String name) {
		super(type, name, BotaniaAPI.elementiumArmorMaterial);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped provideArmorModelForSlot(ItemStack stack, EntityEquipmentSlot slot) {
		models.put(slot, new ModelArmorShadowWarrior(slot));
		return models.get(slot);
	}
	
	@Override
	public float getDiscount(ItemStack stack, int slot, EntityPlayer player, @Nullable ItemStack tool) {
		return hasArmorSet(player) ? 0.1F : 0F;
	}
	
	@Override
	public String getArmorTextureAfterInk(ItemStack stack, EntityEquipmentSlot slot) {
		return "extrabotany:textures/model/armor_shadowwarrior.png";
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		super.onArmorTick(world, player, stack);
		if(hasArmorSet(player) && !world.isDaytime()) {
			ExtraBotanyAPI.unlockAdvancement(player, LibAdvancements.ARMORSET_SW);
			ItemNBTHelper.setBoolean(stack, TAG_NIGHT, true);
			player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 300, 1));
		}else
			ItemNBTHelper.setBoolean(stack, TAG_NIGHT, false);
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> attrib = super.getAttributeModifiers(slot, stack);
		UUID uuid = new UUID((getUnlocalizedName() + slot.toString()).hashCode(), 0);
		boolean night = ItemNBTHelper.getBoolean(stack, TAG_NIGHT, false);
		if (slot == armorType) {
			attrib.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(uuid, "ShadowWarrior modifier " + type, night ? 0.25F : 0,  1));
			attrib.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(uuid, "ShadowWarrior modifier " + type, night ? 0.08F : 0, 1));
			attrib.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(uuid, "ShadowWarrior modifier " + type, night ? 0.25F : 0, 1));
			attrib.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(uuid, "ShadowWarrior modifier " + type, night ? 0.05F : 0, 1));
		}
		return attrib;
	}
	
	private static ItemStack[] armorset;

	@Override
	public ItemStack[] getArmorSetStacks() {
		if(armorset == null)
			armorset = new ItemStack[] {
					new ItemStack(ModItems.swhelm),
					new ItemStack(ModItems.swchest),
					new ItemStack(ModItems.swleg),
					new ItemStack(ModItems.swboot)
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
		case 0: return stack.getItem() == ModItems.swhelm;
		case 1: return stack.getItem() == ModItems.swchest;
		case 2: return stack.getItem() == ModItems.swleg;
		case 3: return stack.getItem() == ModItems.swboot;
		}

		return false;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addArmorSetDescription(ItemStack stack, List<String> list) {
		addStringToTooltip(I18n.format("extrabotany.armorset.shadowwarrior.desc0"), list);
		addStringToTooltip(I18n.format("extrabotany.armorset.shadowwarrior.desc1"), list);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public String getArmorSetName() {
		return I18n.format("extrabotany.armorset.shadowwarrior.name");
	}

	@Override
	public float getSpawnChance(ItemStack stack) {
		return 0.1F;
	}
}
