package com.meteor.extrabotany.client;

import com.meteor.extrabotany.client.render.RenderTilePedestal;
import com.meteor.extrabotany.common.CommonProxy;
import com.meteor.extrabotany.common.block.tile.TilePedestal;
import com.meteor.extrabotany.common.core.version.VersionChecker;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		initRenderers();
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		VersionChecker.init();
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
	
	private void initRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(TilePedestal.class, new RenderTilePedestal());
	}
	
}
