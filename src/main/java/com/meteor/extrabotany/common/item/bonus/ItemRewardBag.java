package com.meteor.extrabotany.common.item.bonus;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.api.item.Bonus;
import com.meteor.extrabotany.api.item.WeightCategory;
import com.meteor.extrabotany.common.lib.LibItemsName;
import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.common.item.ModItems;

public class ItemRewardBag extends ItemBonusBase{
	
	final int types = 4;
	private static List<WeightCategory> categorysA = new ArrayList<WeightCategory>();
	private static List<WeightCategory> categorysB = new ArrayList<WeightCategory>();
	private static List<WeightCategory> categorysC = new ArrayList<WeightCategory>();
	private static List<WeightCategory> categorysD = new ArrayList<WeightCategory>();

	public ItemRewardBag() {
		super(LibItemsName.REWARD_BAG);
		setHasSubtypes(true);
		//A
		for(int i = 0; i < 16; i++)
			Bonus.addItem(new ItemStack(ModItems.petal, 6, i), 1, categorysA);
		//B
		for(int i = 0; i < 4; i++)
			Bonus.addItem(new ItemStack(ModItems.rune, 2, i), 5, categorysB);
		for(int i = 4; i < 8; i++)
			Bonus.addItem(new ItemStack(ModItems.rune, 1, i), 3, categorysB);
		for(int i = 8; i < 16; i++)
			Bonus.addItem(new ItemStack(ModItems.rune, 1, i), 1, categorysB);
		//C
		for(int i = 0; i < 3; i++)
			Bonus.addItem(new ItemStack(ModItems.manaResource, 5, i), 12, categorysC);
		Bonus.addItem(new ItemStack(ModItems.manaResource, 1, 4), 9, categorysC);
		Bonus.addItem(new ItemStack(ModItems.manaResource, 12, 23), 10, categorysC);
		Bonus.addItem(new ItemStack(ModItems.manaResource, 1, 14), 7, categorysC);
		Bonus.addItem(new ItemStack(ModItems.manaResource, 4, 5), 8, categorysC);
		for(int i = 7; i < 10; i++)
			Bonus.addItem(new ItemStack(ModItems.manaResource, 3, i), 11, categorysC);
		Bonus.addItem(new ItemStack(com.meteor.extrabotany.common.item.ModItems.material, 1, 3), 1, categorysC);
		//D
		Bonus.addItem(new ItemStack(Items.IRON_INGOT, 6), 24, categorysD);
		Bonus.addItem(new ItemStack(Items.DIAMOND, 2), 13, categorysD);
		Bonus.addItem(new ItemStack(Items.COAL, 8), 28, categorysD);
		Bonus.addItem(new ItemStack(Items.GOLD_INGOT, 5), 17, categorysD);
		Bonus.addItem(new ItemStack(ModItems.overgrowthSeed, 2), 10, categorysD);
		Bonus.addItem(new ItemStack(ModItems.blackLotus, 3), 15, categorysD);
		Bonus.addItem(new ItemStack(Items.ENDER_PEARL, 4), 18, categorysD);
		Bonus.addItem(new ItemStack(Items.REDSTONE, 12), 18, categorysD);
		Bonus.addItem(new ItemStack(com.meteor.extrabotany.common.item.ModItems.relics, 1), 1, categorysD);
	}
	
	@Nonnull
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return "item." + LibItemsName.REWARD_BAGS_NAMES[Math.min(types - 1, par1ItemStack.getItemDamage())];
	}
	
	@Override
	public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> stacks) {
		if(isInCreativeTab(tab)) {
			for(int i = 0; i < types; i++) {
				stacks.add(new ItemStack(this, 1, i));
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		for (int i = 0; i < LibItemsName.REWARD_BAGS_NAMES.length; i++) {
			if (!"UNUSED".equals(LibItemsName.REWARD_BAGS_NAMES[i])) {
				ModelLoader.setCustomModelResourceLocation(
					this, i,
					new ModelResourceLocation(LibMisc.MOD_ID + ":" + LibItemsName.REWARD_BAGS_NAMES[i], "inventory")
				);
			}
		}
	}
	
	@Override
	public List<WeightCategory> getWeightCategory(ItemStack stack){
		switch(stack.getMetadata()){
			case 0:
				return categorysA;
			case 1:
				return categorysB;
			case 2:
				return categorysC;
			case 3:
				return categorysD;
		}
		return null;
	}

}
