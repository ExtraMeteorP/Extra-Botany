package com.meteor.extrabotany.common.core.config;

import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler {
	
	public static final ConfigHandler INSTANCE = new ConfigHandler();
	public static Configuration CONFIG;

	public static int CRAFTING_STRIKES;
	
	public static int EFF_OMNIVIOLET;
	public static int BOOK_BURNTIME;
	public static int WRITTENBOOK_BURNTIME;
	
	public static int EFF_BELLFLOWER;
	public static int BASEY;
	
	public static int EFF_BLOODYENCHANTRESS;
	public static int BLOOD_BURNTIME;
	
	public static int EFF_MOONBLESS;
	public static int EFF_SUNBLESS;
	public static int EFF_TINKLE;
	public static int EFF_STONESIA;
	
	public static double EFF_GEMINIORCHID;
	public static double EFF_ELDELWEISS;
	
	public static int ANNOYINGFLOWER_COST;
	public static int TICKS;
	public static int TIMES;
	
	public static int BASECOST;
	public static int PERCOST;
	public static int CONSUMESPEED;
	
	public static int SPEED;
	
	public static boolean LP_STONESIA;
	public static boolean LP_BELLFLOWER;
	public static boolean LP_OMNIVIOLET;
	public static boolean LP_TINKLE;
	
	public static int RL_BASEGEN;
	public static int RL_BURNTIME;
	public static int RL_EFF;
	public static int RL_COOLDOWN;
	public static int RL_CD;
	public static int RL_BASEY;
	
	public static int EO_RANGE;
	public static int EO_COST;
	public static int EO_SPEED;
	
	public static int MB_SPEED;
	
	public static boolean GAIA_ENABLE = true;
	public static boolean GAIA_DISARM = true;
	public static boolean GAIA_SMASH = false;
	public static boolean GAIA_DIVINEJUDGE = true;
	
	public static boolean ENABLE_AF;
	public static boolean ENABLE_EO;
	public static boolean ENABLE_ML;
	public static boolean ENABLE_SL;
	public static boolean ENABLE_BF;
	public static boolean ENABLE_BE;
	public static boolean ENABLE_MB;
	public static boolean ENABLE_OV;
	public static boolean ENABLE_RL;
	public static boolean ENABLE_SS;
	public static boolean ENABLE_SB;
	public static boolean ENABLE_TK;
	public static boolean ENABLE_EW;
	public static boolean ENABLE_MT;
	public static boolean ENABLE_GO;
	
	public static boolean ENABLE_TOOLTIP;
	public static boolean ENABLE_SHIELD;
	
	public static int MG_MAXENERGY;
	public static int MG_CONVERT;
	public static int MG_TRANSFERSPEED;
	
	public static boolean DISABLE_MANAGENERATOR = true;
	public static boolean DISABLE_MANALIQUEFICATION = true;
	
	public static boolean ENABLE_WAILAPOOL = true;
	public static boolean ENABLE_WAILAMANAGEN = true;
	public static boolean ENABLE_WAILAMANABUFFER = true;
	public static boolean ENABLE_WAILAMANALIQUE = true;
	
	public static boolean ENABLE_CASUPPORT = false;
	
	public static boolean ENABLE_TOP = true;
	
	public static boolean ENABLE_FEATURES = true;
	
	public static boolean ENABLE_CANDYBAGDROP = true;
	public static double CANDYBAG_DROPCHANCE = 0.02F;
	public static double PARTICLE = 1F;
	
	public void loadConfig(FMLPreInitializationEvent event) {
		CONFIG = new Configuration(event.getSuggestedConfigurationFile());
		CONFIG.load();
		syncConfigs();
	}
	
	private void syncConfigs() {
		String desc;
		
		desc = "Whether to enable Waila Support for Mana Pool. Default is true. Require MC restart.";
		ENABLE_WAILAPOOL = loadPropBool("waila.pool", desc, ENABLE_WAILAPOOL);
		desc = "Whether to enable Waila Support for Flux Manafield. Default is true. Require MC restart.";
		ENABLE_WAILAMANAGEN = loadPropBool("waila.managenerator", desc, ENABLE_WAILAMANAGEN);
		desc = "Whether to enable Waila Support for Mana Buffer. Default is true. Require MC restart.";
		ENABLE_WAILAMANABUFFER = loadPropBool("waila.manabuffer", desc, ENABLE_WAILAMANABUFFER);
		desc = "Whether to enable Waila Support for Mana Liquefaction Device. Default is true. Require MC restart.";
		ENABLE_WAILAMANALIQUE = loadPropBool("waila.manaliquefaction", desc, ENABLE_WAILAMANALIQUE);
		
		desc = "Whether to enable The One Probe Support. Default is true. Require MC restart.";
		ENABLE_TOP = loadPropBool("enable.topsupport", desc, ENABLE_TOP);
		
		desc = "Whether to enable Flux Manafield. Default is true.";
		DISABLE_MANAGENERATOR = loadPropBool("enable.managenerator", desc, DISABLE_MANAGENERATOR);
		desc = "Whether to enable Mana Liquefaction Device. Default is true.";
		DISABLE_MANALIQUEFICATION = loadPropBool("enable.manaliquefaction", desc, DISABLE_MANALIQUEFICATION);
		desc = "Whether to enable the Summoning of Gaia Guardian III. Default is true.";
		GAIA_ENABLE = loadPropBool("enable.gaiaspawning", desc, GAIA_ENABLE);
		desc = "Whether Gaia Guardian III will disarm or not. Default is true.";
		GAIA_DISARM = loadPropBool("enable.gaiadisarm", desc, GAIA_DISARM);
		desc = "Whether Gaia Guardian III will destroy other mod's blocks. Default is false.";
		GAIA_SMASH = loadPropBool("enable.gaiasmash", desc, GAIA_SMASH);
		desc = "Whether Gaia Guardian III will release Divine Judge. Default is true.";
		GAIA_DIVINEJUDGE = loadPropBool("enable.divinejudge", desc, GAIA_DIVINEJUDGE);
		desc = "Amount of Particles during Gaia Guardian III fight. Default is 100%.";
		PARTICLE = loadPropDouble("gaia.particle", desc, PARTICLE);
		desc = "Whether Monster will have the chance to hold a Halloween Candy Night when spawning. Default is true.";
		ENABLE_CANDYBAGDROP = loadPropBool("enable.candybag", desc, ENABLE_CANDYBAGDROP);
		desc = "The Chance a monster will hold a Halloween Candy Night. Default is 2%.";
		CANDYBAG_DROPCHANCE = loadPropDouble("chance.candybag", desc, CANDYBAG_DROPCHANCE);
		desc = "Whether to enable Construct's Armory Compat. Default is false.";
		ENABLE_CASUPPORT = loadPropBool("enable.ca", desc, ENABLE_CASUPPORT);
		
		ENABLE_AF = CONFIG.get("enable flowers", "annoying flower", true).getBoolean(true);
		ENABLE_EO = CONFIG.get("enable flowers", "enchanted orchid", true).getBoolean(true);
		ENABLE_ML = CONFIG.get("enable flowers", "manalinkium", true).getBoolean(true);
		ENABLE_SL = CONFIG.get("enable flowers", "stardust lotus", true).getBoolean(true);
		ENABLE_BF = CONFIG.get("enable flowers", "bell flower", true).getBoolean(true);
		ENABLE_BE = CONFIG.get("enable flowers", "bloody enchantress", true).getBoolean(true);
		ENABLE_MB = CONFIG.get("enable flowers", "moonlight lily", true).getBoolean(true);
		ENABLE_OV = CONFIG.get("enable flowers", "omniviolet", true).getBoolean(true);
		ENABLE_RL = CONFIG.get("enable flowers", "reikar lily", true).getBoolean(true);
		ENABLE_SS = CONFIG.get("enable flowers", "stonesia", true).getBoolean(true);
		ENABLE_SB = CONFIG.get("enable flowers", "sunshine lily", true).getBoolean(true);
		ENABLE_TK = CONFIG.get("enable flowers", "tinkle flower", true).getBoolean(true);
		ENABLE_EW = CONFIG.get("enable flowers", "edelweiss", true).getBoolean(true);
		ENABLE_MT = CONFIG.get("enable flowers", "mirrowtunia", true).getBoolean(true);
		ENABLE_GO = CONFIG.get("enable flowers", "geminiorchid", true).getBoolean(true);
		
		ENABLE_FEATURES = CONFIG.get("enable features", "easter eggs", true).getBoolean(true);
		
		ENABLE_SHIELD = CONFIG.get("enable shields", "enable non-relic shields", true).getBoolean(true);
		
		MG_MAXENERGY = CONFIG.getInt("max energy stored", "flux manafield", 40000, 1, Integer.MAX_VALUE, "");
		MG_CONVERT = CONFIG.getInt("1000 FE converts to how much. Default is 99", "flux manafield", 99, 1, Integer.MAX_VALUE, "");
		MG_TRANSFERSPEED = CONFIG.getInt("the speed it transfer mana to spreader", "flux manafield", 200, 1, Integer.MAX_VALUE, "");
		
		CRAFTING_STRIKES = CONFIG.getInt("pedestal", "strike times", 5, 1, Integer.MAX_VALUE, "");
		
		EFF_OMNIVIOLET = CONFIG.getInt("mana per tick", "omniviolet", 8, 1, Integer.MAX_VALUE, "");
		BOOK_BURNTIME = CONFIG.getInt("book burn time", "omniviolet", 50, 1, Integer.MAX_VALUE, "");
		WRITTENBOOK_BURNTIME = CONFIG.getInt("written book burn time", "omniviolet", 65, 1, Integer.MAX_VALUE, "");
		LP_OMNIVIOLET = CONFIG.get("omniviolet", "enable catalysis", true).getBoolean(true);
		
		EFF_BELLFLOWER = CONFIG.getInt("efficiency", "bell flower", 8, 1, Integer.MAX_VALUE, "");
		BASEY = CONFIG.getInt("base Y to work", "bell flower", 64, 1, Integer.MAX_VALUE, "");
		LP_BELLFLOWER = CONFIG.get("bell flower", "enable catalysis", true).getBoolean(true);
		
		EFF_BLOODYENCHANTRESS = CONFIG.getInt("mana per tick", "bloodyenchantress", 20, 1, Integer.MAX_VALUE, "");
		BLOOD_BURNTIME = CONFIG.getInt("blood burn time", "bloodyenchantress", 20, 1, Integer.MAX_VALUE, "");
		
		EFF_MOONBLESS = CONFIG.getInt("mana per tick", "moonbless", 1, 1, Integer.MAX_VALUE, "");
		
		EFF_SUNBLESS = CONFIG.getInt("mana per tick", "sunbless", 1, 1, Integer.MAX_VALUE, "");
		
		EFF_TINKLE = CONFIG.getInt("efficiency", "tinkle flower", 45, 1, Integer.MAX_VALUE, "");
		LP_TINKLE = CONFIG.get("tinkle flower", "enable catalysis", true).getBoolean(true);
		
		EFF_STONESIA = CONFIG.getInt("mana per tick", "stonesia", 5, 1, Integer.MAX_VALUE, "");	
		LP_STONESIA = CONFIG.get("stonesia", "enable catalysis", true).getBoolean(true);
		
		ANNOYINGFLOWER_COST = CONFIG.getInt("mana cost for fishing", "annoying flower", 100, 1, Integer.MAX_VALUE, "");	
		TICKS = CONFIG.getInt("interval bewteen fishing", "annoying flower", 260, 1, Integer.MAX_VALUE, "");	
		TIMES = CONFIG.getInt("times fried chicken leg add", "annoying flower", 3, 1, Integer.MAX_VALUE, "");	
		
		BASECOST = CONFIG.getInt("mana cost at least", "stardust lotus", 20000, 1, Integer.MAX_VALUE, "");	
		PERCOST = CONFIG.getInt("mana cost per meter", "stardust lotus", 150, 1, Integer.MAX_VALUE, "");	
		CONSUMESPEED = CONFIG.getInt("mana consumed per tick", "stardust lotus", 800, 1, 100000, "");	
		
		SPEED = CONFIG.getInt("mana transfered per tick", "manalinkium", 400, 1, Integer.MAX_VALUE, "");	
		
		RL_BASEGEN = CONFIG.getInt("mana based generated per lightning", "reikarlily", 40000, 1, Integer.MAX_VALUE, "");	
		RL_BURNTIME = CONFIG.getInt("burntime for each lightning", "reikarlily", 2000, 1, Integer.MAX_VALUE, "");
		RL_EFF = CONFIG.getInt("mana pertick", "reikarlily", 30, 1, Integer.MAX_VALUE, "");
		RL_COOLDOWN = CONFIG.getInt("cooldown after consuming lightning", "reikarlily", 3600, 1, Integer.MAX_VALUE, "");
		RL_CD = CONFIG.getInt("cooldown after a lightning summoned by itself", "reikarlily", 3600, 1, Integer.MAX_VALUE, "");
		RL_BASEY = CONFIG.getInt("the lower base y, the higher chance it will summon lightning", "reikarlily", 64, 1, Integer.MAX_VALUE, "");
		
		EO_COST = CONFIG.getInt("mana cost for each enchanted soil", "enchantedorchid", 500000, 1, Integer.MAX_VALUE, "");
		EO_SPEED = CONFIG.getInt("the speed of mana consuming", "enchantedorchid", 200, 1, 10000, "");
		EO_RANGE = CONFIG.getInt("transform range", "enchantedorchid", 4, 1, Integer.MAX_VALUE, "");
		
		MB_SPEED = CONFIG.getInt("mana transfer speed", "mana buffer", 400, 1, Integer.MAX_VALUE, "");
		
		ENABLE_TOOLTIP = CONFIG.get("Tooltips", "enable mana visualization", true).getBoolean(true);
		
		EFF_GEMINIORCHID = CONFIG.getFloat("efficiency", "geminiorchid", 1F, 0, Integer.MAX_VALUE, "");
		EFF_ELDELWEISS = CONFIG.getFloat("efficiency", "edelweiss", 1F, 0, Integer.MAX_VALUE, "");
		
		if (CONFIG.hasChanged())
			CONFIG.save();
	}
	
	public static int loadPropInt(String propName, String desc, int default_) {
		Property prop = CONFIG.get(Configuration.CATEGORY_GENERAL, "extrabotany.config."+propName, default_);
		prop.setComment(desc);

		return prop.getInt(default_);
	}

	public static double loadPropDouble(String propName, String desc, double default_) {
		Property prop = CONFIG.get(Configuration.CATEGORY_GENERAL, "extrabotany.config."+propName, default_);
		prop.setComment(desc);

		return prop.getDouble(default_);
	}

	public static boolean loadPropBool(String propName, String desc, boolean default_) {
		Property prop = CONFIG.get(Configuration.CATEGORY_GENERAL, "extrabotany.config."+propName, default_);
		prop.setComment(desc);

		return prop.getBoolean(default_);
	}
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(LibMisc.MOD_ID))
			syncConfigs();
	}

}
