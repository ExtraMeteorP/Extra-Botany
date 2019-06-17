package com.meteor.extrabotany.common.item.bonus;

import java.util.ArrayList;
import java.util.List;

import com.meteor.extrabotany.api.item.Bonus;
import com.meteor.extrabotany.api.item.WeightCategory;
import com.meteor.extrabotany.common.lib.LibItemsName;

import net.minecraft.item.ItemStack;
import vazkii.botania.common.item.ModItems;

public class ItemRewardBag943 extends ItemBonusBase {

	public static List<WeightCategory> categorysE = new ArrayList<WeightCategory>();

	public ItemRewardBag943() {
		super(LibItemsName.REWARD_BAG943);
	}

	@Override
	public List<WeightCategory> getWeightCategory(ItemStack stack) {
		return categorysE;
	}

}
