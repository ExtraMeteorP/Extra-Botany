package com.meteor.extrabotany.client.render.entity;

import com.meteor.extrabotany.client.model.ModelDragonCopy;
import com.meteor.extrabotany.common.entity.EntityMagicArrow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class RenderMagicArrow extends Render<EntityMagicArrow>{
	
	private static final ModelDragonCopy model = new ModelDragonCopy(0.0F);
	private static final ResourceLocation DRAGON_TEXTURES = new ResourceLocation("textures/entity/enderdragon/dragon.png");

	public RenderMagicArrow(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(@Nonnull EntityMagicArrow arrow, double d0, double d1, double d2, float par8, float par9) {
		if(arrow.dimension == 1){
			GlStateManager.pushMatrix();
			GlStateManager.enableRescaleNormal();
			GlStateManager.color(1F, 1F, 1F, 1F);
			GlStateManager.translate(d0, d1, d2);
			GlStateManager.translate(0.5F, 1.5F, 0.5F);
			GlStateManager.translate(0F, -1F, 0F);
			GlStateManager.translate(0F, 1F, 0F);
			Minecraft.getMinecraft().renderEngine.bindTexture(DRAGON_TEXTURES);
			GlStateManager.scale(1F, -1F, -1F);
			model.render(arrow, arrow.rotationYaw, arrow.rotationPitch, 0.05F);
			GlStateManager.color(1F, 1F, 1F);
			GlStateManager.scale(1F, -1F, -1F);
			GlStateManager.enableRescaleNormal();
			GlStateManager.popMatrix();
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityMagicArrow entity) {
		return DRAGON_TEXTURES;
	}

}
