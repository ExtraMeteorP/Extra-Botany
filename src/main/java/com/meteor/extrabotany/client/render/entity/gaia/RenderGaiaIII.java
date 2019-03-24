package com.meteor.extrabotany.client.render.entity.gaia;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.client.ClientProxy;
import com.meteor.extrabotany.client.lib.LibResource;
import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.entity.gaia.EntityGaiaIII;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGaiaIII extends RenderBiped<EntityGaiaIII> {

	private static final ResourceLocation GAIA_TEXTURES = new ResourceLocation("extrabotany:textures/entity/gaia3.png");

	public RenderGaiaIII(RenderManager renderManager) {
		super(renderManager, new ModelPlayer(0.0F, false), 0F);
	}

	@Override
	public void doRender(@Nonnull EntityGaiaIII dopple, double par2, double par4, double par6, float par8, float par9) {
		super.doRender(dopple, par2, par4, par6, par8, par9);
	}

	@Nonnull
	@Override
	public ResourceLocation getEntityTexture(@Nonnull EntityGaiaIII entity) {
		ResourceLocation skin = Minecraft.getMinecraft().getConnection().getPlayerInfo(entity.getCustomNameTag()).getLocationSkin();
		if(ClientProxy.halloween && ConfigHandler.ENABLE_FEATURES)
			return new ResourceLocation(LibResource.GAIAIII_PUMPKIN);
		if(Minecraft.getMinecraft().getConnection() != null)
			return skin;
		return GAIA_TEXTURES;
	}
}
