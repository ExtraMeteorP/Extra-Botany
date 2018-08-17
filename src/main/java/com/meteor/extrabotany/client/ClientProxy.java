package com.meteor.extrabotany.client;

import com.meteor.extrabotany.client.core.handler.MiscellaneousIcons;
import com.meteor.extrabotany.client.render.entity.RenderFlowerWeapon;
import com.meteor.extrabotany.client.render.entity.RenderGaiaIII;
import com.meteor.extrabotany.client.render.entity.RenderSkullLandmine;
import com.meteor.extrabotany.client.render.entity.RenderSkullMinion;
import com.meteor.extrabotany.client.render.entity.RenderSkullMissile;
import com.meteor.extrabotany.client.render.tile.RenderTilePedestal;
import com.meteor.extrabotany.common.CommonProxy;
import com.meteor.extrabotany.common.block.tile.TilePedestal;
import com.meteor.extrabotany.common.core.version.VersionChecker;
import com.meteor.extrabotany.common.entity.EntityFlowerWeapon;
import com.meteor.extrabotany.common.entity.EntityGaiaIII;
import com.meteor.extrabotany.common.entity.EntitySkullLandmine;
import com.meteor.extrabotany.common.entity.EntitySkullMinion;
import com.meteor.extrabotany.common.entity.EntitySkullMissile;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		MinecraftForge.EVENT_BUS.register(MiscellaneousIcons.INSTANCE);
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
		RenderingRegistry.registerEntityRenderingHandler(EntityFlowerWeapon.class, RenderFlowerWeapon::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySkullMissile.class, RenderSkullMissile::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySkullLandmine.class, RenderSkullLandmine::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySkullMinion.class, RenderSkullMinion::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityGaiaIII.class, RenderGaiaIII::new);
	}
	
}
