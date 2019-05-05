package com.meteor.extrabotany.client.render.entity.gaia;

import com.meteor.extrabotany.common.entity.gaia.EntityDomain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import vazkii.botania.client.core.helper.ShaderHelper;

import javax.annotation.Nonnull;
import java.util.Random;

public class RenderDomain extends Render<EntityDomain> {
	
	private static final ResourceLocation flowerweapon = new ResourceLocation("extrabotany:textures/misc/flowerweapon.png");
	
	public RenderDomain(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(@Nonnull EntityDomain weapon, double par2, double par4, double par6, float par8, float par9) {
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
		GlStateManager.scale(2F, 2F, 2F);

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
		GlStateManager.disableLighting();
		GlStateManager.popMatrix();

		GlStateManager.disableCull();
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		GlStateManager.color(1F, 1F, 1F, 1F);

		Minecraft.getMinecraft().renderEngine.bindTexture(flowerweapon);

		Tessellator tes = Tessellator.getInstance();
		ShaderHelper.useShader(ShaderHelper.halo);
		Random rand = new Random(weapon.getUniqueID().getMostSignificantBits());
		GlStateManager.rotate(-90F, 1F, 0F, 0F);

		s = 2F;
		GlStateManager.scale(s, s, s);

		GlStateManager.rotate(90, 1F, 0F, 0F);

		tes.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		tes.getBuffer().pos(-1, 0, -1).tex(0, 0).endVertex();
		tes.getBuffer().pos(-1, 0, 1).tex(0, 1).endVertex();
		tes.getBuffer().pos(1, 0, 1).tex(1, 1).endVertex();
		tes.getBuffer().pos(1, 0, -1).tex(1, 0).endVertex();
		tes.draw();

		ShaderHelper.releaseShader();

		GlStateManager.enableLighting();
		GlStateManager.shadeModel(GL11.GL_FLAT);
		GlStateManager.enableCull();
		GlStateManager.popMatrix();
	}
	
	@Nonnull
	@Override
	protected ResourceLocation getEntityTexture(@Nonnull EntityDomain entity) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}
}
