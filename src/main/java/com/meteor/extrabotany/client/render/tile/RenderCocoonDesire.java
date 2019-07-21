package com.meteor.extrabotany.client.render.tile;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import com.meteor.extrabotany.common.block.tile.TileCocoonDesire;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderCocoonDesire extends TileEntitySpecialRenderer<TileCocoonDesire> {

	@Override
	public void render(@Nonnull TileCocoonDesire cocoon, double x, double y, double z, float f, int digProgress,
			float unused) {
		float rot = 0F;
		float modval = 60F - (float) cocoon.timeExisted / (float) TileCocoonDesire.TIME * 30F;
		if (cocoon.timeExisted % modval < 10) {
			float mod = (cocoon.timeExisted + f) % modval;
			float v = mod / 5 * (float) Math.PI * 2;
			rot = (float) Math.sin(v) * (float) Math.log(cocoon.timeExisted + f);
		}

		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.translate(x, y, z + 1);
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		GlStateManager.translate(0.5F, 0, 0F);
		GlStateManager.rotate(rot, 1F, 0F, 0F);
		GlStateManager.translate(-0.5F, 0, 0F);
		IBlockState state = cocoon.getWorld().getBlockState(cocoon.getPos());
		IBakedModel model = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes()
				.getModelForState(state);
		Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModelBrightness(model,
				state, 1.0F, false);
		GlStateManager.color(1F, 1F, 1F);
		GlStateManager.enableRescaleNormal();
		GlStateManager.popMatrix();
	}
}
