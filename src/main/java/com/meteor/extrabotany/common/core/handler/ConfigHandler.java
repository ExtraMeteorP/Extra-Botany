package com.meteor.extrabotany.common.core.handler;

import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler {
	
	public static final ConfigHandler INSTANCE = new ConfigHandler();
	public Configuration CONFIG;
	
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
	
	public static boolean GAIA_ENABLE;
	public static boolean GAIA_DISARM;
	public static boolean GAIA_SMASH;
	
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
	
	public static boolean ENABLE_TOOLTIP;
	
	public void loadConfig(FMLPreInitializationEvent event) {
		CONFIG = new Configuration(event.getSuggestedConfigurationFile());
		CONFIG.load();
		syncConfigs();
	}
	
	private void syncConfigs() {
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
		
		
		CRAFTING_STRIKES = CONFIG.getInt("pedestal", "strike times", 5, 1, Integer.MAX_VALUE, "");
		
		EFF_OMNIVIOLET = CONFIG.getInt("mana per tick", "omniviolet", 8, 1, Integer.MAX_VALUE, "");
		BOOK_BURNTIME = CONFIG.getInt("book burn time", "omniviolet", 50, 1, Integer.MAX_VALUE, "");
		WRITTENBOOK_BURNTIME = CONFIG.getInt("written book burn time", "omniviolet", 65, 1, Integer.MAX_VALUE, "");
		LP_OMNIVIOLET = CONFIG.get("omniviolet", "enable catalysis", true).getBoolean(true);
		
		EFF_BELLFLOWER = CONFIG.getInt("efficiency", "bell flower", 8, 1, Integer.MAX_VALUE, "");
		BASEY = CONFIG.getInt("base Y to work", "bell flower", 64, 1, Integer.MAX_VALUE, "");
		LP_BELLFLOWER = CONFIG.get("bell flower", "enable catalysis", true).getBoolean(true);
		
		EFF_BLOODYENCHANTRESS = CONFIG.getInt("mana per tick", "bloodyenchantress", 5, 1, Integer.MAX_VALUE, "");
		BLOOD_BURNTIME = CONFIG.getInt("blood burn time", "bloodyenchantress", 60, 1, Integer.MAX_VALUE, "");
		
		EFF_MOONBLESS = CONFIG.getInt("mana per tick", "moonbless", 1, 1, Integer.MAX_VALUE, "");
		
		EFF_SUNBLESS = CONFIG.getInt("mana per tick", "sunbless", 1, 1, Integer.MAX_VALUE, "");
		
		EFF_TINKLE = CONFIG.getInt("efficiency", "tinkle flower", 45, 1, Integer.MAX_VALUE, "");
		LP_TINKLE = CONFIG.get("tinkle flower", "enable catalysis", true).getBoolean(true);
		
		EFF_STONESIA = CONFIG.getInt("mana per tick", "stonesia", 6, 1, Integer.MAX_VALUE, "");	
		LP_STONESIA = CONFIG.get("stonesia", "enable catalysis", true).getBoolean(true);
		
		ANNOYINGFLOWER_COST = CONFIG.getInt("mana cost for fishing", "annoying flower", 50, 1, Integer.MAX_VALUE, "");	
		TICKS = CONFIG.getInt("interval bewteen fishing", "annoying flower", 200, 1, Integer.MAX_VALUE, "");	
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
		
		GAIA_ENABLE = CONFIG.get("gaia guardian III", "enable spawning", true).getBoolean(true);
		GAIA_DISARM = CONFIG.get("gaia guardian III", "enable disarm", true).getBoolean(true);
		GAIA_SMASH = CONFIG.get("gaia guardian III", "enable destroy blocks", true).getBoolean(true);
		
		ENABLE_TOOLTIP = CONFIG.get("Tooltips", "enable mana visualization", true).getBoolean(true);
		
		if (CONFIG.hasChanged())
			CONFIG.save();
	}
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(LibMisc.MOD_ID))
			syncConfigs();
	}

}
