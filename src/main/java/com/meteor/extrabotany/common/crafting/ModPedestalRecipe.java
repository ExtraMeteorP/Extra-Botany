package com.meteor.extrabotany.common.crafting;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.crafting.recipe.RecipePedestal;
import com.meteor.extrabotany.common.item.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModPedestalRecipe {
	
	public static RecipePedestal mashedPotato;
	
	public static RecipePedestal fragment;
	
	public static RecipePedestal gravel;
	public static RecipePedestal flint;
	public static RecipePedestal gunpowder;
	
	public static void init() {
		mashedPotato = ExtraBotanyAPI.registerPedestalRecipe(new ItemStack(ModItems.gildedmashedpotato), new ItemStack(ModItems.material, 1, 2));
		
		fragment = ExtraBotanyAPI.registerPedestalRecipe(new ItemStack(ModItems.material), new ItemStack(ModItems.spiritFuel));
		
		gravel = ExtraBotanyAPI.registerPedestalRecipe(new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.COBBLESTONE));
		flint = ExtraBotanyAPI.registerPedestalRecipe(new ItemStack(Items.FLINT), new ItemStack(Blocks.GRAVEL));
		gunpowder = ExtraBotanyAPI.registerPedestalRecipe(new ItemStack(Items.GUNPOWDER), new ItemStack(Items.FLINT));
	}
}
