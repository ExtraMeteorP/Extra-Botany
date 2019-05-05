package com.meteor.extrabotany.client.render.entity;

import com.meteor.extrabotany.common.entity.EntitySubspace;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import vazkii.botania.client.core.helper.ShaderHelper;

import javax.annotation.Nonnull;

public class RenderSubspace extends Render<EntitySubspace>{
	
	public ResourceLocation getTexture(EntitySubspace sub){
		return new ResourceLocation("extrabotany:textures/misc/subspace_" + Math.floorMod(sub.ticksExisted, 6) + ".png");
	}
	
	public RenderSubspace(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(@Nonnull EntitySubspace weapon, double par2, double par4, double par6, float par8, float par9) {
		GlStateManager.pushMatrix();
		GlStateManager.translate((float)par2, (float)par4, (float)par6);
		GlStateManager.rotate(weapon.getRotation(), 0F, 1F, 0F);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		GlStateManager.pushMatrix();
		
		GlStateManager.popMatrix();
		GlStateManager.disableCull();
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		GlStateManager.color(1F, 1F, 1F, 1F);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(getTexture(weapon));

		Tessellator tes = Tessellator.getInstance();
		ShaderHelper.useShader(ShaderHelper.halo);
		GlStateManager.rotate(-90F, 1F, 0F, 0F);
		if(weapon.ticksExisted < weapon.getLiveTicks()){
			float s = Math.min(weapon.getSize(), Math.max(0F, (float)(weapon.ticksExisted - weapon.getDelay()) / 10F));
			GlStateManager.scale(s,s,s);
		}else{
			float s = Math.max(0F, weapon.getSize() - (float)(weapon.ticksExisted- weapon.getLiveTicks()) / 5F);
			GlStateManager.scale(s,s,s);
		}
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
	protected ResourceLocation getEntityTexture(@Nonnull EntitySubspace entity) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

}
