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
	
	public void loadConfig(FMLPreInitializationEvent event) {
		CONFIG = new Configuration(event.getSuggestedConfigurationFile());
		CONFIG.load();
		syncConfigs();
	}
	
	private void syncConfigs() {
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
		
		BASECOST = CONFIG.getInt("mana cost at least", "stardust lotus", 10000, 1, Integer.MAX_VALUE, "");	
		PERCOST = CONFIG.getInt("mana cost per meter", "stardust lotus", 100, 1, Integer.MAX_VALUE, "");	
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
		EO_RANGE = CONFIG.getInt("range", "enchantedorchid", 4, 1, Integer.MAX_VALUE, "");
		
		MB_SPEED = CONFIG.getInt("mana transfer speed", "mana buffer", 400, 1, Integer.MAX_VALUE, "");
		
		if (CONFIG.hasChanged())
			CONFIG.save();
	}
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(LibMisc.MOD_ID))
			syncConfigs();
	}

}
