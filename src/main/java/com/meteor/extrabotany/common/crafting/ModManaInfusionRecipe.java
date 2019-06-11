package com.meteor.extrabotany.common.crafting;

import com.meteor.extrabotany.common.item.ModItems;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeManaInfusion;

public class ModManaInfusionRecipe {
	
	public static RecipeManaInfusion nightmarefuelRecipe;
	public static RecipeManaInfusion friedchickenRecipe;
	
	public static void init() {
		nightmarefuelRecipe = BotaniaAPI.registerManaInfusionRecipe(new ItemStack(ModItems.nightmareFuel), new ItemStack(Items.COAL), 2000);
		friedchickenRecipe = BotaniaAPI.registerManaInfusionRecipe(new ItemStack(ModItems.friedchicken), new ItemStack(Items.COOKED_CHICKEN), 600);
	}

}
