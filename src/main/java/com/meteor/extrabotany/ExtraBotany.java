package com.meteor.extrabotany;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.Logger;

import com.meteor.extrabotany.common.CommonProxy;
import com.meteor.extrabotany.common.core.handler.ConfigHandler;
import com.meteor.extrabotany.common.crafting.ModRecipe;
import com.meteor.extrabotany.common.entity.ModEntities;
import com.meteor.extrabotany.common.lexicon.LexiconData;
import com.meteor.extrabotany.common.lib.LibMisc;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = LibMisc.MOD_ID, name = ExtraBotany.NAME, version = ExtraBotany.VERSION, dependencies = "required-after:botania;after:Baubles")
public class ExtraBotany{
    public static final String MODID = "extrabotany";
    public static final String NAME = "extrabotany";
    public static final String VERSION = "1.1";

    private static Logger logger;
    
    public static final List<IAction> LATE_REMOVALS = new LinkedList<>();
    public static final List<IAction> LATE_ADDITIONS = new LinkedList<>();
    
    public static final ExtraBotanyCreativeTab tabExtraBotany = new ExtraBotanyCreativeTab(); 
	public static Set<String> subtilesForCreativeMenu = new LinkedHashSet();
	
	@SidedProxy(serverSide = "com.meteor.extrabotany.common.CommonProxy", clientSide = "com.meteor.extrabotany.client.ClientProxy")
	public static CommonProxy proxy;
	
	@Instance(LibMisc.MOD_ID)
	public static ExtraBotany instance;
	
	public static void addSubTileToCreativeMenu(String key) {
		subtilesForCreativeMenu.add(key);
	}

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
    	proxy.preInit(event);
    	ConfigHandler.INSTANCE.loadConfig(event);
    }

    @EventHandler
	public void Init(FMLInitializationEvent event){
    	MinecraftForge.EVENT_BUS.register(ConfigHandler.INSTANCE);
    	ModEntities.init();
		ModRecipe.init();
		LexiconData.init();
		proxy.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){

	}
	
    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
    	if(Loader.isModLoaded("mtlib") && Loader.isModLoaded("crafttweaker")){
	        try {
	            LATE_REMOVALS.forEach(CraftTweakerAPI::apply);
	            LATE_ADDITIONS.forEach(CraftTweakerAPI::apply);
	        } catch(Exception e) {
	            e.printStackTrace();
	            CraftTweakerAPI.logError("Error while applying actions", e);
	        }
    	}
    }
}
