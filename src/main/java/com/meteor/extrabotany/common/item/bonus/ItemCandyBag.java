package com.meteor.extrabotany.common.item.bonus;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.api.item.Bonus;
import com.meteor.extrabotany.api.item.WeightCategory;
import com.meteor.extrabotany.client.ClientProxy;
import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.lib.LibItemsName;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCandyBag extends ItemBonusBase{
	
	private static List<WeightCategory> category = new ArrayList<WeightCategory>();

	public ItemCandyBag() {
		super(LibItemsName.CANDYBAG);
		
		for(int i = 0; i < 3; i++)
			Bonus.addItem(new ItemStack(ModItems.candy, 3, i), 25, category);
		Bonus.addItem(new ItemStack(Items.PUMPKIN_PIE), 15, category);
		Bonus.addItem(new ItemStack(Items.GOLD_INGOT), 10, category);
		Bonus.addItem(new ItemStack(Items.GOLDEN_APPLE, 1, 1), 1, category);
	}
	
	@Nonnull
	@Override
	@SideOnly(Side.CLIENT)
	public String getUnlocalizedName(ItemStack stack) {
		String name = super.getUnlocalizedName(stack);
		if(ClientProxy.christmas)
			name = name.replaceAll("candybag", "candybagchris");
		return name;
	}
	
	@Override
	public List<WeightCategory> getWeightCategory(ItemStack stack){
		return category;
	}

}
