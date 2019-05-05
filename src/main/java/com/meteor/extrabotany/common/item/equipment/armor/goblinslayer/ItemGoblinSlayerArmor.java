package com.meteor.extrabotany.common.item.equipment.armor.goblinslayer;

import com.google.common.collect.Multimap;
import com.meteor.extrabotany.client.model.ModelArmorGoblinSlayerNew;
import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.item.equipment.armor.cosmeticmaid.ItemCosmeticMaidArmor;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ItemGoblinSlayerArmor  extends ItemCosmeticMaidArmor{
	
	public static final String TAG_DAY = "isday";

	public ItemGoblinSlayerArmor(EntityEquipmentSlot type, String name) {
		super(type, name, BotaniaAPI.elementiumArmorMaterial);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped provideArmorModelForSlot(ItemStack stack, EntityEquipmentSlot slot) {
		models.put(slot, new ModelArmorGoblinSlayerNew(slot));
		return models.get(slot);
	}
	
	@Override
	public float getDiscount(ItemStack stack, int slot, EntityPlayer player, @Nullable ItemStack tool) {
		return hasArmorSet(player) ? 0.1F : 0F;
	}
	
	@Override
	public String getArmorTextureAfterInk(ItemStack stack, EntityEquipmentSlot slot) {
		return "extrabotany:textures/model/armor_goblinslayernew.png";
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		super.onArmorTick(world, player, stack);
		if(hasArmorSet(player) && world.isDaytime()) {
			
			ItemNBTHelper.setBoolean(stack, TAG_DAY, true);
		}else
			ItemNBTHelper.setBoolean(stack, TAG_DAY, false);
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> attrib = super.getAttributeModifiers(slot, stack);
		UUID uuid = new UUID((getUnlocalizedName() + slot.toString()).hashCode(), 0);
		boolean night = ItemNBTHelper.getBoolean(stack, TAG_DAY, false);
		if (slot == armorType) {
			attrib.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(uuid, "GoblinSlayer modifier " + type, night ? 0.25F : 0,  1));
			attrib.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(uuid, "GoblinSlayer modifier " + type, night ? 0.04F : 0, 1));
		}
		return attrib;
	}
	
	private static ItemStack[] armorset;

	@Override
	public ItemStack[] getArmorSetStacks() {
		if(armorset == null)
			armorset = new ItemStack[] {
					new ItemStack(ModItems.gshelm),
					new ItemStack(ModItems.gschest),
					new ItemStack(ModItems.gsleg),
					new ItemStack(ModItems.gsboot)
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
		case 0: return stack.getItem() == ModItems.gshelm;
		case 1: return stack.getItem() == ModItems.gschest;
		case 2: return stack.getItem() == ModItems.gsleg;
		case 3: return stack.getItem() == ModItems.gsboot;
		}

		return false;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addArmorSetDescription(ItemStack stack, List<String> list) {
		addStringToTooltip(I18n.format("extrabotany.armorset.goblinslayer.desc0"), list);
		addStringToTooltip(I18n.format("extrabotany.armorset.goblinslayer.desc1"), list);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public String getArmorSetName() {
		return I18n.format("extrabotany.armorset.goblinslayer.name");
	}

}
