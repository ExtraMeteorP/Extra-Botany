package com.meteor.extrabotany.common;

import com.meteor.extrabotany.common.block.fluid.ModFluid;
import com.meteor.extrabotany.common.brew.ModBrew;
import com.meteor.extrabotany.common.core.handler.ConfigHandler;
import com.meteor.extrabotany.common.core.handler.ToolTipHandler;
import com.meteor.extrabotany.common.crafting.ModRecipe;
import com.meteor.extrabotany.common.entity.ModEntities;
import com.meteor.extrabotany.common.item.equipment.shield.ItemShieldCopy;
import com.meteor.extrabotany.common.lexicon.LexiconData;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	public void preInit(FMLPreInitializationEvent event) {
		ModFluid.init();	
	}
	
	public void init(FMLInitializationEvent event) {
    	MinecraftForge.EVENT_BUS.register(ConfigHandler.INSTANCE);
    	if(event.getSide().isClient())
    		MinecraftForge.EVENT_BUS.register(new ToolTipHandler());
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
	}
	
	public void postInit(FMLPostInitializationEvent event) {

	}
}
