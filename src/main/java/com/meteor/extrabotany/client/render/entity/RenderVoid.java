package com.meteor.extrabotany.client.render.entity;

import com.meteor.extrabotany.client.model.ModelVoid;
import com.meteor.extrabotany.common.entity.gaia.EntityVoid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderVoid extends Render<EntityVoid>{

    private final ModelVoid model = new ModelVoid();

    public RenderVoid(RenderManager renderManagerIn){
        super(renderManagerIn);
    }

    public void doRender(EntityVoid entity, double x, double y, double z, float entityYaw, float partialTicks){
		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.translate(x+0.5,y+2F,z-0.5F);

		ResourceLocation r = new ResourceLocation("extrabotany:textures/model/void.png");

		Minecraft.getMinecraft().renderEngine.bindTexture(r);
		GlStateManager.scale(1F, -1F, -1F);
		GlStateManager.color(1F, 1F, 1F);

		GlStateManager.pushMatrix();
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1F, 0.55F, 0.02F, 0.3F);
		model.render();
		GlStateManager.disableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.popMatrix();
		GlStateManager.scale(1F, -1F, -1F);
		
		GlStateManager.enableRescaleNormal();
		GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    protected ResourceLocation getEntityTexture(EntityVoid entity){
        return new ResourceLocation("extrabotany:textures/model/void.png");
    }
}
