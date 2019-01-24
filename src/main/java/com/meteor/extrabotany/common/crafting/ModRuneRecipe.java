package com.meteor.extrabotany.common.crafting;

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
	public static RecipeRuneAltar recipeShadowium;
	public static RecipeRuneAltar recipeDarkened;
	public static RecipeRuneAltar recipeOrichalcos;
	public static RecipeRuneAltar recipeWallruning;
	public static RecipeRuneAltar recipeWalljumping;
	public static RecipeRuneAltar recipeElvenking;
	public static RecipeRuneAltar recipeAllforone;
	public static RecipeRuneAltar recipePhotonium;
	
	public static void init() {
		recipeIngotOrichalcos = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.material, 1, 2), 100000, LibOreDict.MANA_STEEL, LibOreDict.TERRA_STEEL, LibOreDict.GAIA_INGOT, LibOreDict.ELEMENTIUM, LibOreDict.MANA_DIAMOND, LibOreDict.DRAGONSTONE);
		recipeFroststar = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.froststar), 2000, LibOreDict.MANA_STEEL, LibOreDict.MANA_STEEL, new ItemStack(Blocks.ICE), new ItemStack(Blocks.ICE), LibOreDict.RUNE[8]);
		recipeDeathring = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.deathring), 2000, LibOreDict.MANA_STEEL, LibOreDict.MANA_STEEL, LibOreDict.MANA_DIAMOND, new ItemStack(Items.SKULL, 1, 1), LibOreDict.RUNE[14]);
		recipeGoldpotato = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.material, 1, 2), 800, new ItemStack(Items.POTATO), new ItemStack(Items.GOLD_NUGGET));
		recipeUltimatehammer = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.hammerultimate), 100000, new ItemStack(ModItems.hammerterrasteel), new ItemStack(ModItems.gildedmashedpotato), new ItemStack(ModItems.gildedmashedpotato), new ItemStack(ModItems.gildedmashedpotato), new ItemStack(Blocks.GOLD_BLOCK));
		recipeShadowium = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.material, 1, 5), 2200, LibOreDict.ELEMENTIUM, new ItemStack(ModItems.nightmareFuel), new ItemStack(ModItems.nightmareFuel), new ItemStack(ModItems.gildedmashedpotato), new ItemStack(ModItems.nightmareFuel));
		recipeDarkened = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.cmchestdarkened), 50000, new ItemStack(ModItems.cmchest), new ItemStack(ModItems.swhelm), new ItemStack(ModItems.swchest), new ItemStack(ModItems.swleg), new ItemStack(ModItems.swboot));
		recipeOrichalcos = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.material, 1, 1), 150000, LibOreDict.GAIA_INGOT, LibOreDict.GAIA_INGOT, new ItemStack(ModItems.material, 1, 3), new ItemStack(ModItems.gildedmashedpotato), new ItemStack(vazkii.botania.common.item.ModItems.manaResource, 1, 5), new ItemStack(vazkii.botania.common.item.ModItems.manaResource, 1, 5), new ItemStack(vazkii.botania.common.item.ModItems.manaResource, 1, 5), new ItemStack(vazkii.botania.common.item.ModItems.manaResource, 1, 5));
		recipeWalljumping = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.walljumping), 2000, LibOreDict.MANA_STEEL, LibOreDict.MANA_STEEL, new ItemStack(Items.WHEAT_SEEDS), LibOreDict.RUNE[2], new ItemStack(Blocks.STICKY_PISTON));
		recipeWallruning = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.wallrunning), 2000, LibOreDict.MANA_STEEL, LibOreDict.MANA_STEEL, new ItemStack(Items.WHEAT_SEEDS), LibOreDict.RUNE[2], "stone");
		recipeElvenking = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.elvenking), 4000, LibOreDict.ELEMENTIUM, LibOreDict.ELEMENTIUM, LibOreDict.QUARTZ[5], LibOreDict.QUARTZ[5], LibOreDict.RUNE[4]);
		recipeAllforone = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.allforone), 50000, new ItemStack(ModItems.elvenking), new ItemStack(ModItems.material, 1, 3), LibOreDict.RUNE[9], LibOreDict.RUNE[10], LibOreDict.RUNE[11], LibOreDict.RUNE[12], LibOreDict.RUNE[13], LibOreDict.RUNE[14], LibOreDict.RUNE[15]);
		recipePhotonium = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.material, 1, 8), 2200, LibOreDict.ELEMENTIUM, new ItemStack(ModItems.material), new ItemStack(ModItems.material), new ItemStack(ModItems.gildedmashedpotato), new ItemStack(ModItems.material));
	}
	
}
