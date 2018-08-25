package com.meteor.extrabotany.common.lexicon;

import com.meteor.extrabotany.common.block.subtile.functional.SubTileManalinkium;
import com.meteor.extrabotany.common.block.subtile.functional.SubTileStardustLotus;
import com.meteor.extrabotany.common.block.tile.TilePedestal;
import com.meteor.extrabotany.common.core.handler.ConfigHandler;
import com.meteor.extrabotany.common.crafting.ModManaInfusionRecipe;
import com.meteor.extrabotany.common.crafting.ModPedestalRecipe;
import com.meteor.extrabotany.common.crafting.ModPetalRecipe;
import com.meteor.extrabotany.common.crafting.ModRuneRecipe;
import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.lexicon.page.PagePedestalRecipe;
import com.meteor.extrabotany.common.lib.LibLexicon;
import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.entity.EntityDoppleganger;
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
	public static LexiconEntry reikarlily;
	
	public static LexiconEntry annoyingflower;
	public static LexiconEntry stardustlotus;
	public static LexiconEntry manalinkium;
	public static LexiconEntry enchantedorchid;
	
	public static LexiconEntry pedestal;
	public static LexiconEntry nightmarefuel;
	public static LexiconEntry spiritfuel;
	public static LexiconEntry manareader;
	public static LexiconEntry binder;
	public static LexiconEntry hammer;
	public static LexiconEntry goldpotato;
	public static LexiconEntry kinggarden;
	public static LexiconEntry manabarrel;
	
	public static LexiconEntry froststar;
	public static LexiconEntry deathring;
	
	public static LexiconEntry gaia3;
	public static LexiconEntry natureorb;
	public static LexiconEntry failnaught;
	public static LexiconEntry camera;
	public static LexiconEntry relicshield;
	public static LexiconEntry mastermanaring;
	public static LexiconEntry cocoondesire;
	public static LexiconEntry managenerator;
	
	public static LexiconEntry cm;
	public static LexiconEntry cosm;
	
	public static void init() {	
		gaia3 = new DreamLexiconEntry(LibLexicon.GAIA_III,  BotaniaAPI.categoryAlfhomancy);
		gaia3.setLexiconPages(new PageText("0"),
					new PageText("1"),
					new PageMultiblock("2", EntityDoppleganger.makeMultiblockSet()));
		gaia3.setIcon(new ItemStack(ModItems.material, 1, 3));
		
		failnaught = new DreamLexiconEntry(LibLexicon.RELIC_FAILNAUGHT,  BotaniaAPI.categoryTools);
		failnaught.setLexiconPages(new PageText("0"),
					new PageCraftingRecipe("1", getResource("recipe_failnaught")));
		
		camera = new DreamLexiconEntry(LibLexicon.RELIC_CAMERA,  BotaniaAPI.categoryTools);
		camera.setLexiconPages(new PageText("0"),
					new PageCraftingRecipe("1", getResource("recipe_camera")));
		
		managenerator = new DreamLexiconEntry(LibLexicon.MANAGENERATOR,  BotaniaAPI.categoryMana);
		managenerator.setLexiconPages(new PageText("0"),
					new PageCraftingRecipe("1", getResource("recipe_managenerator")));
		
		relicshield = new DreamLexiconEntry(LibLexicon.RELIC_RELICSHIELD,  BotaniaAPI.categoryTools);
		relicshield.setLexiconPages(new PageText("0"),
					new PageCraftingRecipe("1", getResource("recipe_relicshield")));
		
		mastermanaring = new DreamLexiconEntry(LibLexicon.RELIC_MASTERMANARING,  BotaniaAPI.categoryTools);
		mastermanaring.setLexiconPages(new PageText("0"),
					new PageCraftingRecipe("1", getResource("recipe_mastermanaring")));
		
		natureorb = new DreamLexiconEntry(LibLexicon.NATUREORB,  BotaniaAPI.categoryTools);
		natureorb.setLexiconPages(new PageText("0"),
					new PageCraftingRecipe("1", getResource("recipe_natureorb")),
					new PageText("4"),
					new PageMultiblock("2", TilePedestal.makeMultiblockSet()),
					new PageMultiblock("3", TilePedestal.makeMultiblockSet2()));
		
		cm = new DreamLexiconEntry(LibLexicon.ARMOR_COMBAT,  BotaniaAPI.categoryTools);
		cm.setLexiconPages(new PageText("0"),
					new PageCraftingRecipe("1", getResource("recipe_cmhelm")),
					new PageCraftingRecipe("2", getResource("recipe_cmchest")),
					new PageCraftingRecipe("3", getResource("recipe_cmlegs")),
					new PageCraftingRecipe("4", getResource("recipe_cmboots")));
		
		cosm = new BasicLexiconEntry(LibLexicon.ARMOR_COSMETIC,  BotaniaAPI.categoryTools);
		cosm.setLexiconPages(new PageText("0"),
					new PageCraftingRecipe("1", getResource("recipe_cosmhelm")),
					new PageCraftingRecipe("2", getResource("recipe_cosmchest")),
					new PageCraftingRecipe("3", getResource("recipe_cosmlegs")),
					new PageCraftingRecipe("4", getResource("recipe_cosmboots")));
		
		
		if(ConfigHandler.ENABLE_BE){
			bloodyenchantress = new BasicLexiconEntry(LibLexicon.GFLOWER_BLOODYENCHANTRESS,  BotaniaAPI.categoryGenerationFlowers);
			bloodyenchantress.setLexiconPages(new PageText("0"),
						new PagePetalRecipe<>("1", ModPetalRecipe.bloodyenchantressRecipe));
		}
		
		if(ConfigHandler.ENABLE_SB){
			sunbless = new BasicLexiconEntry(LibLexicon.GFLOWER_SUNBLESS,  BotaniaAPI.categoryGenerationFlowers);
			sunbless.setLexiconPages(new PageText("0"),
						new PagePetalRecipe<>("1", ModPetalRecipe.sunblessRecipe));
		}
		
		if(ConfigHandler.ENABLE_MB){
			moonbless = new BasicLexiconEntry(LibLexicon.GFLOWER_MOONBLESS,  BotaniaAPI.categoryGenerationFlowers);
			moonbless.setLexiconPages(new PageText("0"),
						new PagePetalRecipe<>("1", ModPetalRecipe.moonblessRecipe));
		}
		
		if(ConfigHandler.ENABLE_OV){
			omniviolet = new BasicLexiconEntry(LibLexicon.GFLOWER_OMNIVIOLET,  BotaniaAPI.categoryGenerationFlowers);
			omniviolet.setLexiconPages(new PageText("0"),
						new PagePetalRecipe<>("1", ModPetalRecipe.omnivioletRecipe));
		}
		
		if(ConfigHandler.ENABLE_SS){
			stonesia = new BasicLexiconEntry(LibLexicon.GFLOWER_STONESIA,  BotaniaAPI.categoryGenerationFlowers);
			stonesia.setLexiconPages(new PageText("0"),
						new PagePetalRecipe<>("1", ModPetalRecipe.stonesiaRecipe));
		}
		
		if(ConfigHandler.ENABLE_TK){
			tinkle = new DreamLexiconEntry(LibLexicon.GFLOWER_TINKLE,  BotaniaAPI.categoryGenerationFlowers);
			tinkle.setLexiconPages(new PageText("0"),
						new PagePetalRecipe<>("1", ModPetalRecipe.tinkleRecipe));
		}
		
		if(ConfigHandler.ENABLE_BF){
			bellflower = new DreamLexiconEntry(LibLexicon.GFLOWER_BELLFLOWER,  BotaniaAPI.categoryGenerationFlowers);
			bellflower.setLexiconPages(new PageText("0"),
						new PagePetalRecipe<>("1", ModPetalRecipe.bellflowerRecipe));
		}
		
		if(ConfigHandler.ENABLE_RL){
			reikarlily = new DreamLexiconEntry(LibLexicon.GFLOWER_REIKARLILY,  BotaniaAPI.categoryGenerationFlowers);
			reikarlily.setLexiconPages(new PageText("0"),
						new PagePetalRecipe<>("1", ModPetalRecipe.reikarlilyRecipe));
		}
		
		if(ConfigHandler.ENABLE_AF){
			annoyingflower = new BasicLexiconEntry(LibLexicon.FFLOWER_ANNOYINGFLOWER,  BotaniaAPI.categoryFunctionalFlowers);
			annoyingflower.setLexiconPages(new PageText("0"),
						new PagePetalRecipe<>("1", ModPetalRecipe.annoyingflowerRecipe),
						new PageText("2"), new PageManaInfusionRecipe("3", ModManaInfusionRecipe.friedchickenRecipe));
		}
		
		if(ConfigHandler.ENABLE_ML){
			manalinkium = new DreamLexiconEntry(LibLexicon.FFLOWER_MANALINKIUM,  BotaniaAPI.categoryFunctionalFlowers);
			manalinkium.setLexiconPages(new PageText("0"),
						new PagePetalRecipe<>("1", ModPetalRecipe.manalinkiumRecipe),
						new PageText("2"), new PageMultiblock("3", SubTileManalinkium.makeMultiblockSet()));
		}
		
		if(ConfigHandler.ENABLE_SL){
			stardustlotus = new DreamLexiconEntry(LibLexicon.FFLOWER_STARDUSTLOTUS,  BotaniaAPI.categoryFunctionalFlowers);
			stardustlotus.setLexiconPages(new PageText("0"),
						new PagePetalRecipe<>("1", ModPetalRecipe.stardustlotusRecipe),
						new PageText("2"), new PageMultiblock("3", SubTileStardustLotus.makeMultiblockSet()));
		}
		
		if(ConfigHandler.ENABLE_EO){
			enchantedorchid = new DreamLexiconEntry(LibLexicon.FFLOWER_ENCHANTEDORCHID,  BotaniaAPI.categoryFunctionalFlowers);
			enchantedorchid.setLexiconPages(new PageText("0"),
						new PagePetalRecipe<>("1", ModPetalRecipe.enchantedorchidRecipe));
		}
		
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
		
		kinggarden = new DreamLexiconEntry(LibLexicon.KINGGARDEN,  BotaniaAPI.categoryTools);
		kinggarden.setLexiconPages(new PageText("0"),
					new PageText("1"),
					new PageCraftingRecipe("2", getResource("recipe_kinggarden2")),
					new PageCraftingRecipe("3", getResource("recipe_kinggarden")));
		
		manabarrel = new DreamLexiconEntry(LibLexicon.MANABARREL,  BotaniaAPI.categoryMana);
		manabarrel.setLexiconPages(new PageText("0"),
					new PageCraftingRecipe("1", getResource("recipe_manabarrel")));
		
		froststar = new BasicLexiconEntry(LibLexicon.BAUBLE_FROSTSTAR,  BotaniaAPI.categoryBaubles);
		froststar.setLexiconPages(new PageText("0"),
					new PageRuneRecipe("1", ModRuneRecipe.recipeFroststar));
		
		deathring = new DreamLexiconEntry(LibLexicon.BAUBLE_DEATHRING,  BotaniaAPI.categoryBaubles);
		deathring.setLexiconPages(new PageText("0"),
					new PageRuneRecipe("1", ModRuneRecipe.recipeDeathring));
		
		cocoondesire = new DreamLexiconEntry(LibLexicon.RELIC_COCOONDESIRE,  BotaniaAPI.categoryDevices);
		cocoondesire.setLexiconPages(new PageText("0"),
					new PageText("1"),
					new PageCraftingRecipe("2", getResource("recipe_cocoondesire")));
	}
	
	private static ResourceLocation getResource(String inName) {
		return new ResourceLocation(LibMisc.MOD_ID, inName);
	}

}
