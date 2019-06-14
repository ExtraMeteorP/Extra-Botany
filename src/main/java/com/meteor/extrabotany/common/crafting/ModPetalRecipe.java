package com.meteor.extrabotany.common.crafting;

import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.lib.LibBlocksName;

import net.minecraft.item.ItemStack;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipePetals;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;
import vazkii.botania.common.lib.LibOreDict;

public class ModPetalRecipe {
	public static final String white = LibOreDict.PETAL[0], orange = LibOreDict.PETAL[1], magenta = LibOreDict.PETAL[2], lightBlue = LibOreDict.PETAL[3], yellow = LibOreDict.PETAL[4], lime = LibOreDict.PETAL[5], pink = LibOreDict.PETAL[6], gray = LibOreDict.PETAL[7], lightGray = LibOreDict.PETAL[8], cyan = LibOreDict.PETAL[9], purple = LibOreDict.PETAL[10], blue = LibOreDict.PETAL[11], brown = LibOreDict.PETAL[12], green = LibOreDict.PETAL[13], red = LibOreDict.PETAL[14], black = LibOreDict.PETAL[15];
	public static final String runeWater = LibOreDict.RUNE[0], runeFire = LibOreDict.RUNE[1], runeEarth = LibOreDict.RUNE[2], runeAir = LibOreDict.RUNE[3], runeSpring = LibOreDict.RUNE[4], runeSummer = LibOreDict.RUNE[5], runeAutumn = LibOreDict.RUNE[6], runeWinter = LibOreDict.RUNE[7], runeMana = LibOreDict.RUNE[8], runeLust = LibOreDict.RUNE[9], runeGluttony = LibOreDict.RUNE[10], runeGreed = LibOreDict.RUNE[11], runeSloth = LibOreDict.RUNE[12], runeWrath = LibOreDict.RUNE[13], runeEnvy = LibOreDict.RUNE[14], runePride = LibOreDict.RUNE[15];
	public static final String redstoneRoot = LibOreDict.REDSTONE_ROOT;
	public static final String pixieDust = LibOreDict.PIXIE_DUST;
	public static final String gaiaSpirit = LibOreDict.LIFE_ESSENCE;
	public static final String manaPowder = LibOreDict.MANA_POWDER;
	
	public static RecipePetals bloodyenchantressRecipe;
	public static RecipePetals moonblessRecipe;
	public static RecipePetals sunblessRecipe;
	public static RecipePetals omnivioletRecipe;
	public static RecipePetals stonesiaRecipe;
	public static RecipePetals tinkleRecipe;
	public static RecipePetals bellflowerRecipe;
	public static RecipePetals reikarlilyRecipe;
	public static RecipePetals edelweissRecipe;
	public static RecipePetals geminiorchidRecipe;
	
	public static RecipePetals annoyingflowerRecipe;
	public static RecipePetals stardustlotusRecipe;
	public static RecipePetals manalinkiumRecipe;
	public static RecipePetals enchantedorchidRecipe;
	public static RecipePetals mirrortuniaRecipe;
	public static RecipePetals necrofleurRecipe;
	
	public static void init() {
		if(ConfigHandler.ENABLE_BE)
			bloodyenchantressRecipe = BotaniaAPI.registerPetalRecipe(ItemBlockSpecialFlower.ofType(LibBlocksName.SUBTILE_BLOODYENCHANTRESS), red, red, red, red, runeFire, runeSummer, runeWrath);
		if(ConfigHandler.ENABLE_MB)
			moonblessRecipe = BotaniaAPI.registerPetalRecipe(ItemBlockSpecialFlower.ofType(LibBlocksName.SUBTILE_MOONBLESS), red, red, red, white);
		if(ConfigHandler.ENABLE_SB)
			sunblessRecipe = BotaniaAPI.registerPetalRecipe(ItemBlockSpecialFlower.ofType(LibBlocksName.SUBTILE_SUNBLESS), yellow, yellow, yellow, white);
		if(ConfigHandler.ENABLE_OV)
			omnivioletRecipe = BotaniaAPI.registerPetalRecipe(ItemBlockSpecialFlower.ofType(LibBlocksName.SUBTILE_OMINIVIOLET), blue, blue, purple, purple, runeSpring, runeMana, runeLust);
		if(ConfigHandler.ENABLE_SS)
			stonesiaRecipe = BotaniaAPI.registerPetalRecipe(ItemBlockSpecialFlower.ofType(LibBlocksName.SUBTILE_STONESIA), gray, gray, black, gaiaSpirit, runeAutumn, runeGluttony);
		if(ConfigHandler.ENABLE_TK)
			tinkleRecipe = BotaniaAPI.registerPetalRecipe(ItemBlockSpecialFlower.ofType(LibBlocksName.SUBTILE_TINKLE), yellow, yellow, green, lime, runeEarth, new ItemStack(ModItems.material), new ItemStack(ModItems.material), manaPowder);
		if(ConfigHandler.ENABLE_BF)
			bellflowerRecipe = BotaniaAPI.registerPetalRecipe(ItemBlockSpecialFlower.ofType(LibBlocksName.SUBTILE_BELLFLOWER), yellow, yellow, lime, lime, new ItemStack(ModItems.material));
		if(ConfigHandler.ENABLE_AF)
			annoyingflowerRecipe = BotaniaAPI.registerPetalRecipe(ItemBlockSpecialFlower.ofType(LibBlocksName.SUBTILE_ANNOYINGFLOWER), white, white, pink, pink, green, runeMana, new ItemStack(ModItems.material));
		if(ConfigHandler.ENABLE_SL)
			stardustlotusRecipe = BotaniaAPI.registerPetalRecipe(ItemBlockSpecialFlower.ofType(LibBlocksName.SUBTILE_STARDUSTLOTUS), purple, purple, purple, magenta, magenta, runeEnvy, runePride, gaiaSpirit);
		if(ConfigHandler.ENABLE_ML)
			manalinkiumRecipe = BotaniaAPI.registerPetalRecipe(ItemBlockSpecialFlower.ofType(LibBlocksName.SUBTILE_MANALINKIUM), lightBlue, lightBlue, cyan, cyan, cyan, runeLust, runeSloth, gaiaSpirit);
		if(ConfigHandler.ENABLE_RL)
			reikarlilyRecipe = BotaniaAPI.registerPetalRecipe(ItemBlockSpecialFlower.ofType(LibBlocksName.SUBTILE_REIKARLILY), lightBlue, lightBlue, cyan, cyan, blue, runePride, runeSloth, runeEnvy, gaiaSpirit);
		if(ConfigHandler.ENABLE_EO)
			enchantedorchidRecipe = BotaniaAPI.registerPetalRecipe(ItemBlockSpecialFlower.ofType(LibBlocksName.SUBTILE_ENCHANTEDORCHID), purple, purple, magenta, lime, lime, runePride, runeGreed, runeGluttony, gaiaSpirit);
		if(ConfigHandler.ENABLE_EW)
			edelweissRecipe = BotaniaAPI.registerPetalRecipe(ItemBlockSpecialFlower.ofType(LibBlocksName.SUBTILE_EDELWEISS), runeWinter, runeMana, white, white, white, lightBlue, lightBlue, manaPowder);
		if(ConfigHandler.ENABLE_MT)
			mirrortuniaRecipe = BotaniaAPI.registerPetalRecipe(ItemBlockSpecialFlower.ofType(LibBlocksName.SUBTILE_MIRROWTUNIA), runeAir, runePride, runeWrath, cyan, cyan, lightBlue, blue, manaPowder);
		if(ConfigHandler.ENABLE_GO)
			geminiorchidRecipe = BotaniaAPI.registerPetalRecipe(ItemBlockSpecialFlower.ofType(LibBlocksName.SUBTILE_GEMINIORCHID), orange, orange, yellow, yellow, orange, yellow, manaPowder, manaPowder);
		if(ConfigHandler.ENABLE_NF)
			necrofleurRecipe = BotaniaAPI.registerPetalRecipe(ItemBlockSpecialFlower.ofType(LibBlocksName.SUBTILE_NECROFLEUR), gray, gray, red, pink, pink, manaPowder, runeWrath);
	}

}
