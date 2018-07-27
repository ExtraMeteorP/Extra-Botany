package com.meteor.extrabotany.common.crafting;

import com.meteor.extrabotany.common.block.ModBlocks;
import com.meteor.extrabotany.common.item.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeRuneAltar;
import vazkii.botania.common.lib.LibOreDict;

public class ModRuneRecipe {

	public static RecipeRuneAltar recipeIngotOrichalcos;
	public static RecipeRuneAltar recipeFroststar;
	public static RecipeRuneAltar recipeDeathring;
	public static RecipeRuneAltar recipeGoldpotato;
	public static RecipeRuneAltar recipeUltimatehammer;
	
	public static void init() {
		recipeIngotOrichalcos = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.material, 1, 2), 100000, LibOreDict.MANA_STEEL, LibOreDict.TERRA_STEEL, LibOreDict.GAIA_INGOT, LibOreDict.ELEMENTIUM, LibOreDict.MANA_DIAMOND, LibOreDict.DRAGONSTONE);
		recipeFroststar = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.froststar), 2000, LibOreDict.MANA_STEEL, LibOreDict.MANA_STEEL, new ItemStack(Blocks.ICE), new ItemStack(Blocks.ICE), LibOreDict.RUNE[8]);
		recipeDeathring = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.deathring), 2000, LibOreDict.MANA_STEEL, LibOreDict.MANA_STEEL, LibOreDict.MANA_DIAMOND, new ItemStack(Items.SKULL, 1, 1), LibOreDict.RUNE[14]);
		recipeGoldpotato = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.material, 1, 2), 800, new ItemStack(Items.POTATO), new ItemStack(Items.GOLD_NUGGET));
		recipeUltimatehammer = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.hammerultimate), 200000, new ItemStack(ModItems.hammerterrasteel), new ItemStack(ModItems.gildedmashedpotato), new ItemStack(ModItems.gildedmashedpotato), new ItemStack(ModItems.gildedmashedpotato), new ItemStack(Blocks.GOLD_BLOCK));
	}
	
}
