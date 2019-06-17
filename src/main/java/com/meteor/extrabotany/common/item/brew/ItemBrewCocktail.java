package com.meteor.extrabotany.common.item.brew;

import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.lib.LibItemsName;

import net.minecraft.item.ItemStack;

public class ItemBrewCocktail extends ItemBrewBase {

	public ItemBrewCocktail() {
		super(LibItemsName.BREW_COCKTAIL, 8, 20, 0, 1.3F, new ItemStack(ModItems.material, 1, 4));
	}

}
