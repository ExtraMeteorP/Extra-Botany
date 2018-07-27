package com.meteor.extrabotany.common.lexicon;

import com.meteor.extrabotany.common.block.subtile.functional.SubTileManalinkium;
import com.meteor.extrabotany.common.block.subtile.functional.SubTileStardustLotus;
import com.meteor.extrabotany.common.crafting.ModManaInfusionRecipe;
import com.meteor.extrabotany.common.crafting.ModPedestalRecipe;
import com.meteor.extrabotany.common.crafting.ModPetalRecipe;
import com.meteor.extrabotany.common.crafting.ModRuneRecipe;
import com.meteor.extrabotany.common.lexicon.page.PagePedestalRecipe;
import com.meteor.extrabotany.common.lib.LibLexicon;
import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.util.ResourceLocation;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.lexicon.BasicLexiconEntry;
import vazkii.botania.common.lexicon.page.PageCraftingRecipe;
import vazkii.botania.common.lexicon.page.PageManaInfusionRecipe;
import vazkii.botania.common.lexicon.page.PageMultiblock;
import vazkii.botania.common.lexicon.page.PagePetalRecipe;
import vazkii.botania.common.lexicon.page.PageRuneRecipe;
import vazkii.botania.common.lexicon.page.PageText;

public class LexiconData {
	
	public static LexiconEntry bloodyenchantress;
	public static LexiconEntry sunbless;
	public static LexiconEntry moonbless;
	public static LexiconEntry omniviolet;
	public static LexiconEntry stonesia;
	public static LexiconEntry tinkle;
	public static LexiconEntry bellflower;
	
	public static LexiconEntry annoyingflower;
	public static LexiconEntry stardustlotus;
	public static LexiconEntry manalinkium;
	
	public static LexiconEntry pedestal;
	public static LexiconEntry nightmarefuel;
	public static LexiconEntry spiritfuel;
	public static LexiconEntry manareader;
	public static LexiconEntry binder;
	public static LexiconEntry hammer;
	public static LexiconEntry goldpotato;
	
	public static LexiconEntry froststar;
	public static LexiconEntry deathring;
	
	public static void init() {	
		bloodyenchantress = new BasicLexiconEntry(LibLexicon.GFLOWER_BLOODYENCHANTRESS,  BotaniaAPI.categoryGenerationFlowers);
		bloodyenchantress.setLexiconPages(new PageText("0"),
					new PagePetalRecipe<>("1", ModPetalRecipe.bloodyenchantressRecipe));
		
		sunbless = new BasicLexiconEntry(LibLexicon.GFLOWER_SUNBLESS,  BotaniaAPI.categoryGenerationFlowers);
		sunbless.setLexiconPages(new PageText("0"),
					new PagePetalRecipe<>("1", ModPetalRecipe.sunblessRecipe));
		
		moonbless = new BasicLexiconEntry(LibLexicon.GFLOWER_MOONBLESS,  BotaniaAPI.categoryGenerationFlowers);
		moonbless.setLexiconPages(new PageText("0"),
					new PagePetalRecipe<>("1", ModPetalRecipe.moonblessRecipe));
		
		omniviolet = new BasicLexiconEntry(LibLexicon.GFLOWER_OMNIVIOLET,  BotaniaAPI.categoryGenerationFlowers);
		omniviolet.setLexiconPages(new PageText("0"),
					new PagePetalRecipe<>("1", ModPetalRecipe.omnivioletRecipe));
		
		stonesia = new BasicLexiconEntry(LibLexicon.GFLOWER_STONESIA,  BotaniaAPI.categoryGenerationFlowers);
		stonesia.setLexiconPages(new PageText("0"),
					new PagePetalRecipe<>("1", ModPetalRecipe.stonesiaRecipe));
		
		tinkle = new DreamLexiconEntry(LibLexicon.GFLOWER_TINKLE,  BotaniaAPI.categoryGenerationFlowers);
		tinkle.setLexiconPages(new PageText("0"),
					new PagePetalRecipe<>("1", ModPetalRecipe.tinkleRecipe));
		
		bellflower = new DreamLexiconEntry(LibLexicon.GFLOWER_BELLFLOWER,  BotaniaAPI.categoryGenerationFlowers);
		bellflower.setLexiconPages(new PageText("0"),
					new PagePetalRecipe<>("1", ModPetalRecipe.bellflowerRecipe));
		
		annoyingflower = new BasicLexiconEntry(LibLexicon.FFLOWER_ANNOYINGFLOWER,  BotaniaAPI.categoryFunctionalFlowers);
		annoyingflower.setLexiconPages(new PageText("0"),
					new PagePetalRecipe<>("1", ModPetalRecipe.annoyingflowerRecipe),
					new PageText("2"), new PageManaInfusionRecipe("3", ModManaInfusionRecipe.friedchickenRecipe));
		
		manalinkium = new DreamLexiconEntry(LibLexicon.FFLOWER_MANALINKIUM,  BotaniaAPI.categoryFunctionalFlowers);
		manalinkium.setLexiconPages(new PageText("0"),
					new PagePetalRecipe<>("1", ModPetalRecipe.manalinkiumRecipe),
					new PageText("2"), new PageMultiblock("3", SubTileManalinkium.makeMultiblockSet()));
		
		stardustlotus = new DreamLexiconEntry(LibLexicon.FFLOWER_STARDUSTLOTUS,  BotaniaAPI.categoryFunctionalFlowers);
		stardustlotus.setLexiconPages(new PageText("0"),
					new PagePetalRecipe<>("1", ModPetalRecipe.stardustlotusRecipe),
					new PageText("2"), new PageMultiblock("3", SubTileStardustLotus.makeMultiblockSet()));
		
		pedestal = new BasicLexiconEntry(LibLexicon.PEDESTAL,  BotaniaAPI.categoryBasics);
		pedestal.setLexiconPages(new PageText("0"),
					new PageCraftingRecipe("1", getResource("recipe_pedestal")),
					new PagePedestalRecipe("2", ModPedestalRecipe.mashedPotato),
					new PagePedestalRecipe("3", ModPedestalRecipe.gravel),
					new PagePedestalRecipe("4", ModPedestalRecipe.flint),
					new PagePedestalRecipe("5", ModPedestalRecipe.gunpowder));
		
		hammer = new DreamLexiconEntry(LibLexicon.HAMMER,  BotaniaAPI.categoryTools);
		hammer.setLexiconPages(new PageText("0"),
					new PageCraftingRecipe("1", getResource("recipe_manasteelhammer")),
					new PageCraftingRecipe("2", getResource("recipe_elementiumhammer")),
					new PageCraftingRecipe("3", getResource("recipe_terrasteelhammer")),
					new PageText("4"),
					new PageRuneRecipe("5", ModRuneRecipe.recipeUltimatehammer));

		goldpotato = new BasicLexiconEntry(LibLexicon.GOLDPOTATO,  BotaniaAPI.categoryBasics);
		goldpotato.setLexiconPages(new PageText("0"),
					new PageRuneRecipe("1", ModRuneRecipe.recipeGoldpotato));
		
		
		nightmarefuel = new BasicLexiconEntry(LibLexicon.NIGHTMAREFUEL,  BotaniaAPI.categoryBasics);
		nightmarefuel.setLexiconPages(new PageText("0"),
					new PageManaInfusionRecipe("1", ModManaInfusionRecipe.nightmarefuelRecipe));
		
		spiritfuel = new BasicLexiconEntry(LibLexicon.SPIRITEFUEL,  BotaniaAPI.categoryBasics);
		spiritfuel.setLexiconPages(new PageText("0"),
					new PagePedestalRecipe("1", ModPedestalRecipe.fragment));
		
		manareader = new BasicLexiconEntry(LibLexicon.MANAREADER,  BotaniaAPI.categoryTools);
		manareader.setLexiconPages(new PageText("0"),
					new PageCraftingRecipe("1", getResource("recipe_manareader")));
		
		binder = new DreamLexiconEntry(LibLexicon.BINDER,  BotaniaAPI.categoryTools);
		binder.setLexiconPages(new PageText("0"),
					new PageCraftingRecipe("1", getResource("recipe_binder")));
		
		froststar = new BasicLexiconEntry(LibLexicon.BAUBLE_FROSTSTAR,  BotaniaAPI.categoryBaubles);
		froststar.setLexiconPages(new PageText("0"),
					new PageRuneRecipe("1", ModRuneRecipe.recipeFroststar));
		
		deathring = new DreamLexiconEntry(LibLexicon.BAUBLE_DEATHRING,  BotaniaAPI.categoryBaubles);
		deathring.setLexiconPages(new PageText("0"),
					new PageRuneRecipe("1", ModRuneRecipe.recipeDeathring));
	}
	
	private static ResourceLocation getResource(String inName) {
		return new ResourceLocation(LibMisc.MOD_ID, inName);
	}

}
