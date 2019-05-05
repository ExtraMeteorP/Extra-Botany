package com.meteor.extrabotany.common.item.equipment.armor.cosmeticmaid;

import com.google.common.collect.Multimap;
import com.meteor.extrabotany.ExtraBotanyCreativeTab;
import com.meteor.extrabotany.client.model.ModelArmorCosmeticMaid;
import com.meteor.extrabotany.client.render.IModelReg;
import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.lib.LibMisc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.item.IPhantomInkable;
import vazkii.botania.api.mana.IManaDiscountArmor;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.client.lib.LibResources;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ItemCosmeticMaidArmor extends ItemArmor implements ISpecialArmor, IManaUsingItem, IModelReg, IPhantomInkable, IManaDiscountArmor{

	private static final int MANA_PER_DAMAGE = 70;

	private static final String TAG_PHANTOM_INK = "phantomInk";

	protected Map<EntityEquipmentSlot, ModelBiped> models = null;
	public final EntityEquipmentSlot type;

	public ItemCosmeticMaidArmor(EntityEquipmentSlot type, String name) {
		this(type, name, BotaniaAPI.manasteelArmorMaterial);
	}

	public ItemCosmeticMaidArmor(EntityEquipmentSlot type, String name, ArmorMaterial mat) {
		super(mat, 0, type);
		this.type = type;
		setCreativeTab(ExtraBotanyCreativeTab.INSTANCE);
		setRegistryName(new ResourceLocation(LibMisc.MOD_ID, name));
		setUnlocalizedName(name);
	}

	@Nonnull
	@Override
	public String getUnlocalizedNameInefficiently(@Nonnull ItemStack par1ItemStack) {
		return super.getUnlocalizedNameInefficiently(par1ItemStack).replaceAll("item\\.", "item." + LibMisc.MOD_ID + ":");
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, @Nonnull ItemStack armor, DamageSource source, double damage, int slot) {
		if(source.isUnblockable())
			return new ArmorProperties(0, 0, 0);
		return new ArmorProperties(0, damageReduceAmount / 25D, armor.getMaxDamage() + 1 - armor.getItemDamage());
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, @Nonnull ItemStack armor, int slot) {
		return damageReduceAmount;
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> attrib = super.getAttributeModifiers(slot, stack);
		// Remove these or else vanilla will double count it and ISpecialArmor
		attrib.removeAll(SharedMonsterAttributes.ARMOR.getName());
		attrib.removeAll(SharedMonsterAttributes.ARMOR_TOUGHNESS.getName());
		return attrib;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5) {
		if(player instanceof EntityPlayer)
			onArmorTick(world, (EntityPlayer) player, stack);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		if(!world.isRemote && stack.getItemDamage() > 0 && ManaItemHandler.requestManaExact(stack, player, MANA_PER_DAMAGE * 2, true))
			stack.setItemDamage(stack.getItemDamage() - 1);
	}

	@Override
	public void damageArmor(EntityLivingBase entity, @Nonnull ItemStack stack, DamageSource source, int damage, int slot) {
		ToolCommons.damageItem(stack, damage, entity, MANA_PER_DAMAGE);
	}

	@Nonnull
	@Override
	public final String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return hasPhantomInk(stack) ? LibResources.MODEL_INVISIBLE_ARMOR : getArmorTextureAfterInk(stack, slot);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped original) {
			ModelBiped model = getArmorModelForSlot(entityLiving, itemStack, armorSlot);
			if(model == null)
				model = provideArmorModelForSlot(itemStack, armorSlot);

			if(model != null) {
				model.setModelAttributes(original);
			}
			return model;
	}

	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModelForSlot(EntityLivingBase entity, ItemStack stack, EntityEquipmentSlot slot) {
		if(models == null)
			models = new EnumMap<>(EntityEquipmentSlot.class);

		return models.get(slot);
	}
	
	@Override
	public boolean usesMana(ItemStack stack) {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flags) {
		if(GuiScreen.isShiftKeyDown())
			addInformationAfterShift(stack, world, list, flags);
		else addStringToTooltip(I18n.format("botaniamisc.shiftinfo"), list);
	}

	@SideOnly(Side.CLIENT)
	public void addInformationAfterShift(ItemStack stack, World world, List<String> list, ITooltipFlag flags) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		addStringToTooltip(getArmorSetTitle(player), list);
		addArmorSetDescription(stack, list);
		ItemStack[] stacks = getArmorSetStacks();
		for(int i = 0; i < stacks.length; i++)
			addStringToTooltip((hasArmorSetItem(player, i) ? TextFormatting.GREEN : "") + " - " + stacks[i].getDisplayName(), list);
	}

	public void addStringToTooltip(String s, List<String> tooltip) {
		tooltip.add(s.replaceAll("&", "\u00a7"));
	}

	public boolean hasArmorSet(EntityPlayer player) {
		return hasArmorSetItem(player, 0) && hasArmorSetItem(player, 1) && hasArmorSetItem(player, 2) && hasArmorSetItem(player, 3);
	}

	private int getSetPiecesEquipped(EntityPlayer player) {
		int pieces = 0;
		for(int i = 0; i < 4; i++)
			if(hasArmorSetItem(player, i))
				pieces++;

		return pieces;
	}

	@SideOnly(Side.CLIENT)
	private String getArmorSetTitle(EntityPlayer player) {
		return I18n.format("botaniamisc.armorset") + " " + getArmorSetName() + " (" + getSetPiecesEquipped(player) + "/" + getArmorSetStacks().length + ")";
	}

	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, @Nonnull ItemStack par2ItemStack) {
		return par2ItemStack.getItem() == vazkii.botania.common.item.ModItems.manaResource && par2ItemStack.getItemDamage() == 0 ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
	}

	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}

	@Override
	public boolean hasPhantomInk(ItemStack stack) {
		return ItemNBTHelper.getBoolean(stack, TAG_PHANTOM_INK, false);
	}

	@Override
	public void setPhantomInk(ItemStack stack, boolean ink) {
		ItemNBTHelper.setBoolean(stack, TAG_PHANTOM_INK, ink);
	}
	
	@SideOnly(Side.CLIENT)
	public ModelBiped provideArmorModelForSlot(ItemStack stack, EntityEquipmentSlot slot) {
		models.put(slot, new ModelArmorCosmeticMaid(slot));
		return models.get(slot);
	}
	
	public String getArmorTextureAfterInk(ItemStack stack, EntityEquipmentSlot slot) {
		return "extrabotany:textures/model/armor_cosmeticmaid.png";
	}
	
	private static ItemStack[] armorset;

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

	public boolean hasArmorSetItem(EntityPlayer player, int i) {
		if(player == null || player.inventory == null || player.inventory.armorInventory == null)
			return false;
		
		ItemStack stack = player.inventory.armorInventory.get(3 - i);
		if(stack.isEmpty())
			return false;

		switch(i) {
		case 0: return stack.getItem() == ModItems.cosmhelm || stack.getItem() == ModItems.coshelmrevealing;
		case 1: return stack.getItem() == ModItems.cosmchest;
		case 2: return stack.getItem() == ModItems.cosmleg;
		case 3: return stack.getItem() == ModItems.cosmboot;
		}

		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public void addArmorSetDescription(ItemStack stack, List<String> list) {
		addStringToTooltip(I18n.format("extrabotany.armorset.cosmeticmaid.desc0"), list);
	}

	@SideOnly(Side.CLIENT)
	public String getArmorSetName() {
		return I18n.format("extrabotany.armorset.cosmeticmaid.name");
	}
	
}
