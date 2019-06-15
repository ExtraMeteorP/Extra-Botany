package com.meteor.extrabotany.common;

import com.meteor.extrabotany.client.core.handler.ContributorHandler;
import com.meteor.extrabotany.common.block.dispenser.DispenserBehaviors;
import com.meteor.extrabotany.common.block.fluid.ModFluid;
import com.meteor.extrabotany.common.brew.ModBrew;
import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.core.handler.FakePlayerHandler;
import com.meteor.extrabotany.common.core.handler.ToolTipHandler;
import com.meteor.extrabotany.common.crafting.ModRecipe;
import com.meteor.extrabotany.common.crafting.ModStageLock;
import com.meteor.extrabotany.common.entity.ModEntities;
import com.meteor.extrabotany.common.integration.Integration;
import com.meteor.extrabotany.common.item.equipment.shield.ItemShieldCopy;
import com.meteor.extrabotany.common.lexicon.LexiconData;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.INSTANCE.loadConfig(event);
		ModFluid.init();
		Integration.preInit();
	}

	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(ConfigHandler.INSTANCE);
		if (event.getSide().isClient()) {
			MinecraftForge.EVENT_BUS.register(new ToolTipHandler());
		}
		MinecraftForge.EVENT_BUS.register(ItemShieldCopy.EventHandler.INSTANCE);
		ModEntities.init();
		ModBrew.init();
		ModRecipe.init();
		LexiconData.init();
		// ModWorld.init();
		Integration.init();
		DispenserBehaviors.init();
		MinecraftForge.EVENT_BUS.register(new ModStageLock());
		MinecraftForge.EVENT_BUS.register(new ContributorHandler());
		new FakePlayerHandler();
	}

	public void postInit(FMLPostInitializationEvent event) {

	}

	public void serverStarting(FMLServerStartingEvent event) {

	}

	public void setTinkersRenderColor(slimeknights.tconstruct.library.materials.Material material, int color) {

	}
}
