package com.meteor.extrabotany.common.item.bonus;

import com.meteor.extrabotany.api.item.WeightCategory;
import com.meteor.extrabotany.common.lib.LibItemsName;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemRewardBag943 extends ItemBonusBase{
	
	public static List<WeightCategory> categorysE = new ArrayList<WeightCategory>();

	public ItemRewardBag943() {
		super(LibItemsName.REWARD_BAG943);
	}
	
	@Override
	public List<WeightCategory> getWeightCategory(ItemStack stack){
		return categorysE;
	}

}
