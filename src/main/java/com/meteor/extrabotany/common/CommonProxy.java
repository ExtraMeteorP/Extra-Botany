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
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
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
	}
	
	public void postInit(FMLPostInitializationEvent event) {

	}
}
