package com.meteor.extrabotany.common.item.relic;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.item.equipment.bauble.ItemBaubleRelic;
import com.meteor.extrabotany.common.lib.LibAdvancements;
import com.meteor.extrabotany.common.lib.LibItemsName;

import baubles.api.BaubleType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import vazkii.botania.api.mana.IManaItem;
import vazkii.botania.api.mana.IManaTooltipDisplay;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class ItemMasterManaRing extends ItemBaubleRelic implements IManaItem, IManaTooltipDisplay {

	protected static final int MAX_MANA = Integer.MAX_VALUE;

	private static final String TAG_MANA = "mana";

	public ItemMasterManaRing() {
		this(LibItemsName.BAUBLE_MASTERMANARING);
	}

	public ItemMasterManaRing(String name) {
		super(name);
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.RING;
	}

	@Override
	public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> stacks) {
		if (isInCreativeTab(tab)) {
			stacks.add(new ItemStack(this));

			ItemStack full = new ItemStack(this);
			setMana(full, getMaxMana(full));
			stacks.add(full);
		}
	}

	public static void setMana(ItemStack stack, int mana) {
		ItemNBTHelper.setInt(stack, TAG_MANA, mana);
	}

	@Override
	public int getMana(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_MANA, 0);
	}

	@Override
	public int getMaxMana(ItemStack stack) {
		return MAX_MANA - 1;
	}

	@Override
	public void addMana(ItemStack stack, int mana) {
		setMana(stack, Math.min(getMana(stack) + mana, getMaxMana(stack)));
	}

	@Override
	public boolean canReceiveManaFromPool(ItemStack stack, TileEntity pool) {
		return true;
	}

	@Override
	public boolean canReceiveManaFromItem(ItemStack stack, ItemStack otherStack) {
		return true;
	}

	@Override
	public boolean canExportManaToPool(ItemStack stack, TileEntity pool) {
		return true;
	}

	@Override
	public boolean canExportManaToItem(ItemStack stack, ItemStack otherStack) {
		return true;
	}

	@Override
	public boolean isNoExport(ItemStack stack) {
		return false;
	}

	@Override
	public float getManaFractionForDisplay(ItemStack stack) {
		return (float) getMana(stack) / (float) getMaxMana(stack);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return 1.0 - getManaFractionForDisplay(stack);
	}

	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack) {
		return MathHelper.hsvToRGB(1.0F, 1.0F, 1.0F);
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (!world.isRemote && entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			updateRelic(stack, player);
			if (getMana(stack) == Integer.MAX_VALUE - 1 && !world.isRemote)
				ExtraBotanyAPI.unlockAdvancement(player, LibAdvancements.MASTERMANARING_FILL);
		}
	}

}
