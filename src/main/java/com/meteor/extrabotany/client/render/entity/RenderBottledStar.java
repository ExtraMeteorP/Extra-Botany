package com.meteor.extrabotany.client.render.entity;

import com.meteor.extrabotany.common.entity.EntityBottledStar;
import com.meteor.extrabotany.common.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import vazkii.botania.client.core.helper.ShaderHelper;

import javax.annotation.Nonnull;

public class RenderBottledStar extends Render<EntityBottledStar>{

	public RenderBottledStar(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(@Nonnull EntityBottledStar weapon, double x, double y, double z, float par8, float par9) {
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.disableLighting();
		GlStateManager.disableCull();
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		
		ShaderHelper.useShader(ShaderHelper.halo);
		GlStateManager.translate((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GlStateManager.rotate(weapon.ticksExisted*6F, 0F, 1F, 0F);
		Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(ModItems.bottledstar), ItemCameraTransforms.TransformType.GROUND);
		
		ShaderHelper.releaseShader();

		GlStateManager.enableLighting();
		GlStateManager.shadeModel(GL11.GL_FLAT);
		GlStateManager.enableCull();
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBottledStar entity) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

}
