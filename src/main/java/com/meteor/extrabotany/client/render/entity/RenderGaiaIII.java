package com.meteor.extrabotany.client.render.entity;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.client.ClientProxy;
import com.meteor.extrabotany.client.lib.LibResource;
import com.meteor.extrabotany.common.entity.gaia.EntityGaiaIII;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.client.lib.LibResources;

@SideOnly(Side.CLIENT)
public class RenderGaiaIII extends RenderBiped<EntityGaiaIII> {
	
	private static final ResourceLocation textureHalo = new ResourceLocation(LibResources.MISC_HALO);
	private static final ResourceLocation GAIA_TEXTURES = new ResourceLocation("extrabotany:textures/entity/gaia3.png");

	public RenderGaiaIII(RenderManager renderManager) {
		super(renderManager, new ModelPlayer(0.0F, false), 0F);
	}

	@Override
	public void doRender(@Nonnull EntityGaiaIII dopple, double par2, double par4, double par6, float par8, float par9) {
		super.doRender(dopple, par2, par4, par6, par8, par9);
		/*
		if(dopple.getRankII()){
			TextureAtlasSprite icon = MiscellaneousIcons.INSTANCE.tiaraWingIcons[3];
			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
	
			float rz = 120F;
			float rx = 20F + (float) ((Math.sin((double) (dopple.ticksExisted) * (0.4F)) + 0.5F) * (30F));
			float ry = 0F;
			float h = 0.2F;
			float i = 0.15F;
			float s = 1F;
	
			GlStateManager.pushMatrix();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GlStateManager.color(1F, 1F, 1F, 1F);
	
			int light = 15728880;
			int lightmapX = light % 65536;
			int lightmapY = light / 65536;
	
			float lbx = OpenGlHelper.lastBrightnessX;
			float lby = OpenGlHelper.lastBrightnessY;
			
			rz = 180F;
			h = 0.5F;
			rx = 20F;
			ry = -(float) ((Math.sin((double) (dopple.ticksExisted) * (0.4F)) + 0.6F) * (30F));
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapX, lightmapY);
			
			float mul = 32F / 20F;
			s *= mul;
	
			float f = icon.getMinU();
			float f1 = icon.getMaxU();
			float f2 = icon.getMinV();
			float f3 = icon.getMaxV();
			float sr = 1F / s;
	
			GlStateManager.translate(0F, h, i);
	
			GlStateManager.rotate(rz, 0F, 0F, 1F);
			GlStateManager.rotate(rx, 1F, 0F, 0F);
			GlStateManager.rotate(ry, 0F, 1F, 0F);
			GlStateManager.scale(s, s, s);
			IconHelper.renderIconIn3D(Tessellator.getInstance(), f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 1F / 32F);
			GlStateManager.scale(sr, sr, sr);
			GlStateManager.rotate(-ry, 0F, 1F, 0F);
			GlStateManager.rotate(-rx, 1F, 0F, 0F);
			GlStateManager.rotate(-rz, 0F, 0F, 1F);
			
			GlStateManager.color(1F, 1F, 1F);
			GlStateManager.popMatrix();
	
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbx, lby);
		}
		if(dopple.getRankIII()){
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GlStateManager.shadeModel(GL11.GL_SMOOTH);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
			GlStateManager.disableLighting();
			GlStateManager.disableCull();
			GlStateManager.color(1F, 1F, 1F, 1F);
	
			Minecraft.getMinecraft().renderEngine.bindTexture(textureHalo);
	
			if(dopple != null)
				GlStateManager.translate(0, -dopple.getEyeHeight(), 0);
			GlStateManager.translate(0, 1.5F, 0);
			GlStateManager.rotate(30, 1, 0, -1);
			GlStateManager.translate(-0.1F, -0.5F, -0.1F);
			GlStateManager.rotate(dopple.ticksExisted, 0, 1, 0);
	
			Tessellator tes = Tessellator.getInstance();
			ShaderHelper.useShader(ShaderHelper.halo);
			tes.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			tes.getBuffer().pos(-0.75, 0, -0.75).tex(0, 0).endVertex();
			tes.getBuffer().pos(-0.75, 0, 0.75).tex(0, 1).endVertex();
			tes.getBuffer().pos(0.75, 0, 0.75).tex(1, 1).endVertex();
			tes.getBuffer().pos(0.75, 0, -0.75).tex(1, 0).endVertex();
			tes.draw();
			ShaderHelper.releaseShader();
	
			GlStateManager.enableLighting();
			GlStateManager.shadeModel(GL11.GL_FLAT);
			GlStateManager.enableCull();
		}
		*/
	}

	@Nonnull
	@Override
	protected ResourceLocation getEntityTexture(@Nonnull EntityGaiaIII entity) {
		return ClientProxy.halloween ? new ResourceLocation(LibResource.GAIAIII_PUMPKIN) : GAIA_TEXTURES;
	}
}
