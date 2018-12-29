package com.meteor.extrabotany.common.item.bonus;

import java.util.ArrayList;
import java.util.List;

import com.meteor.extrabotany.api.item.Bonus;
import com.meteor.extrabotany.api.item.WeightCategory;
import com.meteor.extrabotany.common.lib.LibItemsName;

import net.minecraft.item.ItemStack;
import vazkii.botania.common.item.ModItems;

public class ItemRewardBag943 extends ItemBonusBase{
	
	private static List<WeightCategory> categorysE = new ArrayList<WeightCategory>();

	public ItemRewardBag943() {
		super(LibItemsName.REWARD_BAG943);
		
		Bonus.addItem(new ItemStack(com.meteor.extrabotany.common.item.ModItems.material, 1, 3), 1, categorysE);
		Bonus.addItem(new ItemStack(com.meteor.extrabotany.common.item.ModItems.rewardbag, 16), 30, categorysE);
		Bonus.addItem(new ItemStack(com.meteor.extrabotany.common.item.ModItems.rewardbag, 10, 1), 20, categorysE);
		Bonus.addItem(new ItemStack(com.meteor.extrabotany.common.item.ModItems.rewardbag, 6, 2), 10, categorysE);
		Bonus.addItem(new ItemStack(com.meteor.extrabotany.common.item.ModItems.rewardbag, 6, 3), 10, categorysE);
		Bonus.addItem(new ItemStack(ModItems.manaResource, 1, 14), 14, categorysE);
		Bonus.addItem(new ItemStack(ModItems.manaResource, 4, 5), 20, categorysE);
		Bonus.addItem(new ItemStack(com.meteor.extrabotany.common.item.ModItems.material, 1, 6), 45, categorysE);
	}
	
	@Override
	public List<WeightCategory> getWeightCategory(ItemStack stack){
		return categorysE;
	}

}
