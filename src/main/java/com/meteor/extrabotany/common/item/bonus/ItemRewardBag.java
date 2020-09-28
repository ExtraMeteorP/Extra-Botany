package com.meteor.extrabotany.common.item.bonus;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.api.item.Bonus;
import com.meteor.extrabotany.api.item.WeightCategory;
import com.meteor.extrabotany.common.lib.LibItemsName;
import com.meteor.extrabotany.common.lib.Reference;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRewardBag extends ItemBonusBase {

	final int types = 4;
	public static List<WeightCategory> categoryA = new ArrayList<WeightCategory>();
	public static List<WeightCategory> categoryB = new ArrayList<WeightCategory>();
	public static List<WeightCategory> categoryC = new ArrayList<WeightCategory>();
	public static List<WeightCategory> categoryD = new ArrayList<WeightCategory>();

	public ItemRewardBag() {
		super(LibItemsName.REWARD_BAG);
		setHasSubtypes(true);
	}

	public static void parseItems(String str, List<WeightCategory> category) {
		String[] entry = str.replace(" ", "").split(":");
		int meta = entry.length > 3 ? Integer.valueOf(entry[3]) : 0;
		int size = entry.length > 4 ? Integer.valueOf(entry[4]) : 1;
		ItemStack stack = new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(entry[0], entry[1])), size, meta);
		if (entry.length > 2)
			Bonus.addItem(stack, Integer.valueOf(entry[2]), category);
	}

	@Nonnull
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return "item." + LibItemsName.REWARD_BAGS_NAMES[Math.min(types - 1, par1ItemStack.getItemDamage())];
	}

	@Override
	public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> stacks) {
		if (isInCreativeTab(tab)) {
			for (int i = 0; i < types; i++) {
				stacks.add(new ItemStack(this, 1, i));
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		for (int i = 0; i < LibItemsName.REWARD_BAGS_NAMES.length; i++) {
			if (!"UNUSED".equals(LibItemsName.REWARD_BAGS_NAMES[i])) {
				ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(
						Reference.MOD_ID + ":" + LibItemsName.REWARD_BAGS_NAMES[i], "inventory"));
			}
		}
	}

	@Override
	public List<WeightCategory> getWeightCategory(ItemStack stack) {
		switch (stack.getMetadata()) {
		case 0:
			return categoryA;
		case 1:
			return categoryB;
		case 2:
			return categoryC;
		case 3:
			return categoryD;
		}
		return null;
	}

}
