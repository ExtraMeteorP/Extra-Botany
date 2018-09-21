package com.meteor.extrabotany.client;

import java.time.LocalDateTime;
import java.time.Month;

import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.client.core.handler.ColorHandler;
import com.meteor.extrabotany.client.core.handler.EventHandlerClient;
import com.meteor.extrabotany.client.core.handler.MiscellaneousIcons;
import com.meteor.extrabotany.client.render.entity.RenderDarkPixie;
import com.meteor.extrabotany.client.render.entity.RenderEntityDomain;
import com.meteor.extrabotany.client.render.entity.RenderFlowerWeapon;
import com.meteor.extrabotany.client.render.entity.RenderFlyCutter;
import com.meteor.extrabotany.client.render.entity.RenderFlyingBoat;
import com.meteor.extrabotany.client.render.entity.RenderGaiaIII;
import com.meteor.extrabotany.client.render.entity.RenderSkullLandmine;
import com.meteor.extrabotany.client.render.entity.RenderSkullMinion;
import com.meteor.extrabotany.client.render.entity.RenderSkullMissile;
import com.meteor.extrabotany.client.render.entity.RenderSplashGrenade;
import com.meteor.extrabotany.client.render.entity.RenderSwordDomain;
import com.meteor.extrabotany.client.render.tile.RenderElfJar;
import com.meteor.extrabotany.client.render.tile.RenderTileCocoonDesire;
import com.meteor.extrabotany.client.render.tile.RenderTilePedestal;
import com.meteor.extrabotany.common.CommonProxy;
import com.meteor.extrabotany.common.block.tile.TileCocoonDesire;
import com.meteor.extrabotany.common.block.tile.TileElfJar;
import com.meteor.extrabotany.common.block.tile.TilePedestal;
import com.meteor.extrabotany.common.core.version.VersionChecker;
import com.meteor.extrabotany.common.entity.EntityDarkPixie;
import com.meteor.extrabotany.common.entity.EntityFlowerWeapon;
import com.meteor.extrabotany.common.entity.EntityFlyCutter;
import com.meteor.extrabotany.common.entity.EntityFlyingBoat;
import com.meteor.extrabotany.common.entity.EntitySplashGrenade;
import com.meteor.extrabotany.common.entity.gaia.EntityDomain;
import com.meteor.extrabotany.common.entity.gaia.EntityGaiaIII;
import com.meteor.extrabotany.common.entity.gaia.EntitySkullLandmine;
import com.meteor.extrabotany.common.entity.gaia.EntitySkullMinion;
import com.meteor.extrabotany.common.entity.gaia.EntitySkullMissile;
import com.meteor.extrabotany.common.entity.gaia.EntitySwordDomain;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{
	
	public static boolean christmas = false;
	public static boolean halloween = false;
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		MinecraftForge.EVENT_BUS.register(MiscellaneousIcons.INSTANCE);
		MinecraftForge.EVENT_BUS.register(EventHandlerClient.INSTANCE);
		initRenderers();
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		VersionChecker.init();
		ColorHandler.init();
		LocalDateTime now = LocalDateTime.now();
		if (now.getMonth() == Month.DECEMBER && now.getDayOfMonth() >= 16 || now.getMonth() == Month.JANUARY && now.getDayOfMonth() <= 2)
			christmas = true;
		if(now.getMonth() == Month.OCTOBER)
			halloween = true;
		if(halloween)
			ExtraBotany.logger.info("Trick or treat?");
		if(christmas)
			ExtraBotany.logger.info("Happy Christmas!");
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
	
	private void initRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(TilePedestal.class, new RenderTilePedestal());
		ClientRegistry.bindTileEntitySpecialRenderer(TileCocoonDesire.class, new RenderTileCocoonDesire());
		ClientRegistry.bindTileEntitySpecialRenderer(TileElfJar.class, new RenderElfJar());
		RenderingRegistry.registerEntityRenderingHandler(EntityDarkPixie.class, RenderDarkPixie::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityFlyingBoat.class, RenderFlyingBoat::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityFlowerWeapon.class, RenderFlowerWeapon::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySkullMissile.class, RenderSkullMissile::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySkullLandmine.class, RenderSkullLandmine::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySkullMinion.class, RenderSkullMinion::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityGaiaIII.class, RenderGaiaIII::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySplashGrenade.class, RenderSplashGrenade::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySwordDomain.class, RenderSwordDomain::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDomain.class, RenderEntityDomain::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityFlyCutter.class, RenderFlyCutter::new);
	}
	
    @Override
    public void setTinkersRenderColor(slimeknights.tconstruct.library.materials.Material material, int color) {
        material.setRenderInfo(color);
    }
	
}
