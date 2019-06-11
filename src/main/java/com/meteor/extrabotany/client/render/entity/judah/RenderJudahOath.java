package com.meteor.extrabotany.client.render.entity.judah;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import com.meteor.extrabotany.client.core.handler.MiscellaneousIcons;
import com.meteor.extrabotany.common.entity.judah.EntityJudahOath;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import vazkii.botania.client.core.helper.IconHelper;
import vazkii.botania.client.core.helper.ShaderHelper;

public class RenderJudahOath extends Render<EntityJudahOath> {
	
	private static final ResourceLocation texture = new ResourceLocation("extrabotany:textures/model/base.png");
	
	public RenderJudahOath(RenderManager renderManager) {
		super(renderManager);
		shadowSize = 0.0F;
	}

	@Override
	public void doRender(@Nonnull EntityJudahOath weapon, double par2, double par4, double par6, float par8, float par9) {
		GlStateManager.pushMatrix();
		GlStateManager.translate((float)par2, (float)par4+3F, (float)par6);
		//GlStateManager.translate((float)par2 -1F, (float)par4-0.25F, (float)par6 + 0.06F);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.rotate(weapon.getRotation(), 0F, 1F, 0F);
		float s = 1.65F;

		GlStateManager.pushMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		GlStateManager.color(1F, 1F, 1F, 1F);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
		GlStateManager.disableLighting();
		
		float rz = 180F;
		float rx = 0F;
		float ry = 0F;
		
		float mul = 32F / 20F;
		s *= mul;
		float sr = 1F / s;
		
		TextureAtlasSprite icon = MiscellaneousIcons.INSTANCE.judahIcons[weapon.getType().getMetadata()];
		float f = icon.getMinU();
		float f1 = icon.getMaxU();
		float f2 = icon.getMinV();
		float f3 = icon.getMaxV();
		
		GlStateManager.pushMatrix();
		GlStateManager.rotate(rz, 0F, 0F, 1F);
		GlStateManager.rotate(rx, 1F, 0F, 0F);
		GlStateManager.rotate(ry, 0F, 1F, 0F);
		GlStateManager.scale(s, s, s);
		IconHelper.renderIconIn3D(Tessellator.getInstance(), f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 1F / 16F);
		GlStateManager.scale(sr, sr, sr);
		GlStateManager.rotate(-ry, 0F, 1F, 0F);
		GlStateManager.rotate(-rx, 1F, 0F, 0F);
		GlStateManager.rotate(-rz, 0F, 0F, 1F);
		GlStateManager.popMatrix();
		
		GlStateManager.pushMatrix();
		GlStateManager.scale(-1F, 1F, 1F);
		GlStateManager.rotate(rz, 0F, 0F, 1F);
		GlStateManager.rotate(rx, 1F, 0F, 0F);
		GlStateManager.rotate(ry, 0F, 1F, 0F);
		GlStateManager.scale(s, s, s);
		IconHelper.renderIconIn3D(Tessellator.getInstance(), f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 1F / 16F);
		GlStateManager.scale(sr, sr, sr);
		GlStateManager.rotate(-ry, 1F, 0F, 0F);
		GlStateManager.rotate(-rx, 1F, 0F, 0F);
		GlStateManager.rotate(-rz, 0F, 0F, 1F);
		GlStateManager.popMatrix();

		GlStateManager.popMatrix();
		
		GlStateManager.disableCull();
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		GlStateManager.color(1F, 1F, 1F, 1F);

		ShaderHelper.releaseShader();

		GlStateManager.enableLighting();
		GlStateManager.shadeModel(GL11.GL_FLAT);
		GlStateManager.enableCull();
		
		GlStateManager.popMatrix();
	}

	@Nonnull
	@Override
	protected ResourceLocation getEntityTexture(@Nonnull EntityJudahOath entity) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}
}
