package com.meteor.extrabotany.client.render.entity.gaia;

import com.meteor.extrabotany.client.core.handler.MiscellaneousIcons;
import com.meteor.extrabotany.common.entity.gaia.EntitySwordDomain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import vazkii.botania.client.core.helper.IconHelper;
import vazkii.botania.client.core.helper.ShaderHelper;

import javax.annotation.Nonnull;

public class RenderSwordDomain extends Render<EntitySwordDomain> {
		
	public RenderSwordDomain(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(@Nonnull EntitySwordDomain weapon, double par2, double par4, double par6, float par8, float par9) {
		GlStateManager.pushMatrix();
		GlStateManager.translate((float)par2, (float)par4, (float)par6);

		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		GlStateManager.pushMatrix();
		float s = 1.5F;
		GlStateManager.scale(s, s, s);
		GlStateManager.rotate(-90F, 0F, 1F, 0F);
		GlStateManager.rotate(135F, 0F, 0F, 1F);
		TextureAtlasSprite icon = MiscellaneousIcons.INSTANCE.swordDomainIcons[weapon.getType()];
		GlStateManager.color(1F, 1F, 1F, 1F);
		float f = icon.getMinU();
		float f1 = icon.getMaxU();
		float f2 = icon.getMinV();
		float f3 = icon.getMaxV();
		GlStateManager.scale(2F, 2F, 2F);

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
		GlStateManager.disableLighting();
		IconHelper.renderIconIn3D(Tessellator.getInstance(), f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 1F / 16F);
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
	protected ResourceLocation getEntityTexture(@Nonnull EntitySwordDomain entity) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}


}
