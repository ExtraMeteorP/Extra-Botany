package com.meteor.extrabotany.common;

import com.meteor.extrabotany.common.block.fluid.ModFluid;
import com.meteor.extrabotany.common.brew.ModBrew;
import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.core.handler.ToolTipHandler;
import com.meteor.extrabotany.common.crafting.ModRecipe;
import com.meteor.extrabotany.common.entity.ModEntities;
import com.meteor.extrabotany.common.integration.tinkerconstruct.TConstructCompat;
import com.meteor.extrabotany.common.item.equipment.shield.ItemShieldCopy;
import com.meteor.extrabotany.common.lexicon.LexiconData;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommonProxy {
	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.INSTANCE.loadConfig(event);
		ModFluid.init();	
		if (Loader.isModLoaded("tconstruct"))
			TConstructCompat.preInit();
	}
	
	public void init(FMLInitializationEvent event) {
    	MinecraftForge.EVENT_BUS.register(ConfigHandler.INSTANCE);
    	if(event.getSide().isClient()){
    		MinecraftForge.EVENT_BUS.register(new ToolTipHandler());
    		//MinecraftForge.EVENT_BUS.register(new ChatEvent());
    	}
    	MinecraftForge.EVENT_BUS.register(ItemShieldCopy.EventHandler.INSTANCE);
    	ModEntities.init();
    	ModBrew.init();
		ModRecipe.init();
		LexiconData.init();
		if (Loader.isModLoaded("waila")){
			if(ConfigHandler.ENABLE_WAILAPOOL)
				FMLInterModComms.sendMessage("waila", "register", "com.meteor.extrabotany.client.integration.waila.WailaPool.register");
			if(ConfigHandler.ENABLE_WAILAMANAGEN)
				FMLInterModComms.sendMessage("waila", "register", "com.meteor.extrabotany.client.integration.waila.WailaManaGenerator.register");
			if(ConfigHandler.ENABLE_WAILAMANALIQUE)
				FMLInterModComms.sendMessage("waila", "register", "com.meteor.extrabotany.client.integration.waila.WailaManaLiquefaction.register");
			if(ConfigHandler.ENABLE_WAILAMANABUFFER)
				FMLInterModComms.sendMessage("waila", "register", "com.meteor.extrabotany.client.integration.waila.WailaManaBuffer.register");
		}
		if(ConfigHandler.ENABLE_TOP)
			FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "com.meteor.extrabotany.client.integration.theoneprobe.TOPHandler");
	}
	
	public void postInit(FMLPostInitializationEvent event) {

	}
	
    public void serverStarting(FMLServerStartingEvent event){
    	//event.registerServerCommand(new CommandEmoji());
    }
    
    public void setTinkersRenderColor(slimeknights.tconstruct.library.materials.Material material, int color) {
    	
    }
}
