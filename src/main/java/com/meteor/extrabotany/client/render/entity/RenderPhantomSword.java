package com.meteor.extrabotany.client.render.entity;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import com.meteor.extrabotany.client.core.handler.MiscellaneousIcons;
import com.meteor.extrabotany.common.entity.EntityPhantomSword;
import com.meteor.extrabotany.common.lib.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import vazkii.botania.client.core.helper.IconHelper;
import vazkii.botania.client.core.helper.ShaderHelper;

public class RenderPhantomSword extends Render<EntityPhantomSword> {

	public RenderPhantomSword(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(@Nonnull EntityPhantomSword weapon, double par2, double par4, double par6, float par8,
			float par9) {
		if(weapon.getDelay() > 0)
			return;
		
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) par2, (float) par4, (float) par6);

		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		GlStateManager.pushMatrix();
		float s = 1.5F;
		GlStateManager.scale(s, s, s);
		GlStateManager.rotate(weapon.getRotation()+90F, 0F, 1F, 0F);
		GlStateManager.rotate(weapon.getPitch()-90F, 0F, 0F, 1F);
		GlStateManager.rotate(-45, 0F, 0F, 1F);
		GlStateManager.rotate(0F, 1F, 0F, 0F);

		GlStateManager.color(1F, 1F, 1F, weapon.getFake() ? Math.max(0F, 0.6F - weapon.ticksExisted * 0.015F) : 1F);

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
		GlStateManager.disableLighting();

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();

		TextureAtlasSprite icon = MiscellaneousIcons.INSTANCE.swordDomainIcons[weapon.getVariety()];
		float f = icon.getMinU();
		float f1 = icon.getMaxU();
		float f2 = icon.getMinV();
		float f3 = icon.getMaxV();
		
		IconHelper.renderIconIn3D(Tessellator.getInstance(), f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 1F / 16F);
		
		GlStateManager.scale(1 / s, 1 / s, 1 / s);
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
	protected ResourceLocation getEntityTexture(@Nonnull EntityPhantomSword entity) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

}
