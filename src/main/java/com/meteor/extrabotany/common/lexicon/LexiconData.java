package com.meteor.extrabotany.common.lexicon;

import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.client.lib.LibResource;
import com.meteor.extrabotany.common.block.subtile.functional.SubTileManalinkium;
import com.meteor.extrabotany.common.block.subtile.functional.SubTileStardustLotus;
import com.meteor.extrabotany.common.block.tile.TilePedestal;
import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.crafting.ModBrewRecipe;
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
import vazkii.botania.common.lexicon.AlfheimLexiconEntry;
import vazkii.botania.common.lexicon.BasicLexiconEntry;
import vazkii.botania.common.lexicon.page.PageBrew;
import vazkii.botania.common.lexicon.page.PageCraftingRecipe;
import vazkii.botania.common.lexicon.page.PageImage;
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
	public static LexiconEntry edelweiss;
	public static LexiconEntry geminiorchid;
	
	public static LexiconEntry annoyingflower;
	public static LexiconEntry stardustlotus;
	public static LexiconEntry manalinkium;
	public static LexiconEntry enchantedorchid;
	public static LexiconEntry mirrortunia;
	
	public static LexiconEntry pedestal;
	public static LexiconEntry nightmarefuel;
	public static LexiconEntry spiritfuel;
	public static LexiconEntry manareader;
	public static LexiconEntry binder;
	public static LexiconEntry hammer;
	public static LexiconEntry goldpotato;
	public static LexiconEntry kinggarden;
	public static LexiconEntry manabarrel;
	public static LexiconEntry elfjar;
	
	public static LexiconEntry froststar;
	public static LexiconEntry deathring;
	public static LexiconEntry puredaisypendant;
	public static LexiconEntry supercrown;
	public static LexiconEntry redscarf;
	
	public static LexiconEntry gaia3;
	public static LexiconEntry natureorb;
	public static LexiconEntry failnaught;
	public static LexiconEntry camera;
	public static LexiconEntry relicshield;
	public static LexiconEntry mastermanaring;
	public static LexiconEntry cocoondesire;
	public static LexiconEntry managenerator;
	public static LexiconEntry manadrink;
	public static LexiconEntry manaliquefication;
	public static LexiconEntry splashgrenade;
	public static LexiconEntry cocktail;
	public static LexiconEntry infinitewine;
	public static LexiconEntry relics;
	public static LexiconEntry spearsubspace;
	public static LexiconEntry godcore;
	
	public static LexiconEntry parkour;
	public static LexiconEntry elvenking;
	public static LexiconEntry afo;
	public static LexiconEntry walkingcane;
	public static LexiconEntry godweave;
	public static LexiconEntry ticket;
	
	public static LexiconEntry cm;
	public static LexiconEntry cosm;
	public static LexiconEntry sw;
	public static LexiconEntry gs;
	
	public static LexiconEntry judahoath;
	public static LexiconEntry lens;
	
	public static void init() {	
		
		parkour = new BasicLexiconEntry(LibLexicon.BAUBLE_PARKOUR,  BotaniaAPI.categoryBaubles);
		parkour.setLexiconPages(new PageText("0"),
					new PageRuneRecipe("1", ModRuneRecipe.recipeWalljumping),
					new PageRuneRecipe("1", ModRuneRecipe.recipeWallruning),
					new PageText("3"),
					new PageCraftingRecipe("4", getResource("recipe_parkour")));
		
		elvenking = new AlfheimLexiconEntry(LibLexicon.BAUBLE_ELVENKING,  BotaniaAPI.categoryBaubles);
		elvenking.setLexiconPages(new PageText("0"),
					new PageRuneRecipe("1", ModRuneRecipe.recipeElvenking));
		
		walkingcane = new BasicLexiconEntry(LibLexicon.WALKINGCANE,  BotaniaAPI.categoryTools);
		walkingcane.setLexiconPages(new PageText("0"),
					new PageCraftingRecipe("1", getResource("recipe_walkingcane")));
		
		afo = new DreamLexiconEntry(LibLexicon.RELIC_AFO,  BotaniaAPI.categoryBaubles);
		afo.setLexiconPages(new PageText("0"),
				new PageRuneRecipe("1", ModRuneRecipe.recipeAllforone));
		
		godweave = new DreamLexiconEntry(LibLexicon.GODWEAVE,  BotaniaAPI.categoryTools);
		godweave.setLexiconPages(new PageText("0"),
					new PageCraftingRecipe("1", getResource("recipe_godweave")));
		
		ticket = new DreamLexiconEntry(LibLexicon.TICKET,  BotaniaAPI.categoryTools);
		ticket.setLexiconPages(new PageText("0"),
				new PageCraftingRecipe("1", getResource("recipe_ticket")));
		
		gs = new DreamLexiconEntry(LibLexicon.ARMOR_GOBLINSLAYER,  BotaniaAPI.categoryTools);
		gs.setLexiconPages(new PageText("0"),
					new PageRuneRecipe("1", ModRuneRecipe.recipePhotonium),
					new PageText("2"),
					new PageCraftingRecipe("3", getResource("recipe_gshelm")),
					new PageCraftingRecipe("4", getResource("recipe_gschest")),
					new PageCraftingRecipe("5", getResource("recipe_gslegs")),
					new PageCraftingRecipe("6", getResource("recipe_gsboots")));
		
		
		cocktail = new BasicLexiconEntry(LibLexicon.COCKTAIL,  BotaniaAPI.categoryDevices);
		cocktail.setLexiconPages(new PageText("0"),
					new PageBrew(ModBrewRecipe.revolution, "1", "2"),
					new PageBrew(ModBrewRecipe.shell, "3", "4"),
					new PageBrew(ModBrewRecipe.deadpool, "5", "6"),
					new PageBrew(ModBrewRecipe.oneforall, "7", "8"),
					new PageBrew(ModBrewRecipe.floating, "9", "10"));
		cocktail.setIcon(new ItemStack(ModItems.cocktail));
		
		splashgrenade = new BasicLexiconEntry(LibLexicon.GRENADE,  BotaniaAPI.categoryDevices);
		splashgrenade.setLexiconPages(new PageText("0"));
		splashgrenade.setIcon(new ItemStack(ModItems.splashgrenade));
		
		infinitewine = new DreamLexiconEntry(LibLexicon.INFINITEWINE,  BotaniaAPI.categoryDevices);
		infinitewine.setLexiconPages(new PageText("0"));
		infinitewine.setIcon(new ItemStack(ModItems.infinitewine));

		gaia3 = new DreamLexiconEntry(ExtraBotany.naturalpledgeLoaded ? LibLexicon.GAIA_IV : LibLexicon.GAIA_III,  BotaniaAPI.categoryAlfhomancy);
		gaia3.setLexiconPages(new PageText("0"),
					new PageText("1"),
					new PageMultiblock("2", EntityDoppleganger.makeMultiblockSet()));
		gaia3.setIcon(new ItemStack(ModItems.material, 1, 3));
		
		failnaught = new DreamLexiconEntry(LibLexicon.RELIC_FAILNAUGHT,  BotaniaAPI.categoryTools);
		failnaught.setLexiconPages(new PageText("0"),
					new PageCraftingRecipe("1", getResource("recipe_failnaught")));
		
		spearsubspace = new DreamLexiconEntry(LibLexicon.RELIC_SPEARSUBSPACE,  BotaniaAPI.categoryTools);
		spearsubspace.setLexiconPages(new PageText("0"),
					new PageCraftingRecipe("1", getResource("recipe_spearsubspace")),
					new PageRuneRecipe("3", ModRuneRecipe.recipeOrichalcos));
		
		camera = new DreamLexiconEntry(LibLexicon.RELIC_CAMERA,  BotaniaAPI.categoryTools);
		camera.setLexiconPages(new PageText("0"),
					new PageCraftingRecipe("1", getResource("recipe_camera")));
		
		godcore = new DreamLexiconEntry(LibLexicon.RELIC_GODCORE,  BotaniaAPI.categoryTools);
		godcore.setLexiconPages(new PageText("0"),
					new PageCraftingRecipe("1", getResource("recipe_coregod")));
		
		if(ConfigHandler.DISABLE_MANAGENERATOR){
			managenerator = new DreamLexiconEntry(LibLexicon.MANAGENERATOR,  BotaniaAPI.categoryMana);
			managenerator.setLexiconPages(new PageText("0"),
						new PageCraftingRecipe("1", getResource("recipe_managenerator")));
		}
		
		if(ConfigHandler.DISABLE_MANALIQUEFICATION){
			manaliquefication = new BasicLexiconEntry(LibLexicon.MANALIQUEFICATION,  BotaniaAPI.categoryMana);
			manaliquefication.setLexiconPages(new PageText("0"),
						new PageCraftingRecipe("1", getResource("recipe_manaliquefaction")),
						new PageImage("2", LibResource.ENTRY_MANAFLUID));
		}
		
		manadrink = new BasicLexiconEntry(LibLexicon.MANADRINK,  BotaniaAPI.categoryTools);
		manadrink.setLexiconPages(new PageText("0"),
					new PageCraftingRecipe("1", getResource("recipe_emptybottle")));
		manadrink.setIcon(new ItemStack(ModItems.manadrink));
		
		supercrown = new BasicLexiconEntry(LibLexicon.BAUBLE_SUPERCROWN,  BotaniaAPI.categoryBaubles);
		supercrown.setLexiconPages(new PageText("0"),
					new PageCraftingRecipe("1", getResource("recipe_supercrown")));
		
		puredaisypendant = new BasicLexiconEntry(LibLexicon.BAUBLE_PUREDAISYPENDANT,  BotaniaAPI.categoryBaubles);
		puredaisypendant.setLexiconPages(new PageText("0"),
					new PageCraftingRecipe("1", getResource("recipe_puredaisypendant")));
		
		elfjar = new BasicLexiconEntry(LibLexicon.ELFJAR,  BotaniaAPI.categoryDevices);
		elfjar.setLexiconPages(new PageText("0"),
					new PageCraftingRecipe("1", getResource("recipe_elfjar")));
		
		sw = new DreamLexiconEntry(LibLexicon.ARMOR_SHADOW,  BotaniaAPI.categoryTools);
		sw.setLexiconPages(new PageText("0"),
					new PageRuneRecipe("1", ModRuneRecipe.recipeShadowium),
					new PageText("2"),
					new PageCraftingRecipe("3", getResource("recipe_swhelm")),
					new PageCraftingRecipe("4", getResource("recipe_swchest")),
					new PageCraftingRecipe("5", getResource("recipe_swlegs")),
					new PageCraftingRecipe("6", getResource("recipe_swboots")),
					new PageCraftingRecipe("7", getResource("recipe_shadowkatana")));
		
		relicshield = new DreamLexiconEntry(LibLexicon.RELIC_RELICSHIELD,  BotaniaAPI.categoryTools);
		relicshield.setLexiconPages(new PageText("0"),
					new PageCraftingRecipe("1", getResource("recipe_relicshield")));
		
		relics = new DreamLexiconEntry(LibLexicon.RELIC_RELICS,  BotaniaAPI.categoryTools);
		relics.setLexiconPages(new PageText("0"));
		relics.setIcon(new ItemStack(ModItems.relics));
		
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
		
		if(ConfigHandler.ENABLE_GO){
			geminiorchid = new DreamLexiconEntry(LibLexicon.GFLOWER_GEMINIORCHID,  BotaniaAPI.categoryGenerationFlowers);
			geminiorchid.setLexiconPages(new PageText("0"),
						new PagePetalRecipe<>("1", ModPetalRecipe.geminiorchidRecipe),
						new PageImage("2", LibResource.ENTRY_GEMINIORCHID));
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
						new PageText("2"), new PageMultiblock("3", SubTileManalinkium.makeMultiblockSet()),
						new PageImage("4", LibResource.ENTRY_MANALINKIUM));
		}
		
		if(ConfigHandler.ENABLE_SL){
			stardustlotus = new DreamLexiconEntry(LibLexicon.FFLOWER_STARDUSTLOTUS,  BotaniaAPI.categoryFunctionalFlowers);
			stardustlotus.setLexiconPages(new PageText("0"),
						new PagePetalRecipe<>("1", ModPetalRecipe.stardustlotusRecipe),
						new PageText("2"), new PageMultiblock("3", SubTileStardustLotus.makeMultiblockSet()),
						new PageImage("4", LibResource.ENTRY_STARDUSTLOTUS));
		}
		
		if(ConfigHandler.ENABLE_EO){
			enchantedorchid = new DreamLexiconEntry(LibLexicon.FFLOWER_ENCHANTEDORCHID,  BotaniaAPI.categoryFunctionalFlowers);
			enchantedorchid.setLexiconPages(new PageText("0"),
						new PagePetalRecipe<>("1", ModPetalRecipe.enchantedorchidRecipe));
		}
		
		if(ConfigHandler.ENABLE_MT){
			mirrortunia = new DreamLexiconEntry(LibLexicon.FFLOWER_MIRROWTUNIA,  BotaniaAPI.categoryFunctionalFlowers);
			mirrortunia.setLexiconPages(new PageText("0"),
						new PagePetalRecipe<>("1", ModPetalRecipe.mirrortuniaRecipe));
		}
		
		if(ConfigHandler.ENABLE_EW){
			edelweiss = new DreamLexiconEntry(LibLexicon.GFLOWER_EDELWEISS,  BotaniaAPI.categoryGenerationFlowers);
			edelweiss.setLexiconPages(new PageText("0"),
						new PagePetalRecipe<>("1", ModPetalRecipe.edelweissRecipe));
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
		
		lens = new DreamLexiconEntry(LibLexicon.LENS,  BotaniaAPI.categoryMana);
		lens.setLexiconPages(new PageText("0"),
					new PageCraftingRecipe("1", getResource("recipe_lenspush")),
					new PageText("2"),
					new PageCraftingRecipe("3", getResource("recipe_lenssmelt")),
					new PageText("4"),
					new PageCraftingRecipe("5", getResource("recipe_lensmana")),
					new PageText("6"),
					new PageCraftingRecipe("7", getResource("recipe_lenspotion")),
					new PageText("8"),
					new PageCraftingRecipe("1", getResource("recipe_lenscloud")),
					new PageText("10"),
					new PageCraftingRecipe("11", getResource("recipe_lenstrack")));
		
		judahoath = new DreamLexiconEntry(LibLexicon.RELIC_JUDAH,  BotaniaAPI.categoryTools);
		judahoath.setLexiconPages(new PageText("0"),
					new PageCraftingRecipe("1", getResource("recipe_judahoath")),
					new PageCraftingRecipe("2", getResource("recipe_judahoathkira")));
	}
	
	private static ResourceLocation getResource(String inName) {
		return new ResourceLocation(LibMisc.MOD_ID, inName);
	}

}
