package com.meteor.extrabotany.client.render.tile;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.client.model.ModelQuantumManaBuffer;
import com.meteor.extrabotany.common.block.ModBlocks;
import com.meteor.extrabotany.common.block.tile.TileQuantumManaBuffer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderQuantumManaBuffer extends TileEntitySpecialRenderer<TileQuantumManaBuffer> {

	private static final ModelQuantumManaBuffer model = new ModelQuantumManaBuffer();

	private static final ResourceLocation texture = new ResourceLocation(
			"extrabotany:textures/model/quantummanabuffer.png");

	@Override
	public void render(@Nonnull TileQuantumManaBuffer spreader, double d0, double d1, double d2, float ticks,
			int digProgress, float unused) {
		if (!spreader.getWorld().isBlockLoaded(spreader.getPos(), false)
				|| spreader.getWorld().getBlockState(spreader.getPos()).getBlock() != ModBlocks.quantummanabuffer)
			return;

		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.translate(d0, d1, d2);

		GlStateManager.translate(0.5F, 1.5F, 0.5F);
		GlStateManager.translate(0F, -1F, 0F);
		GlStateManager.translate(0F, 1F, 0F);

		ResourceLocation r = texture;

		Minecraft.getMinecraft().renderEngine.bindTexture(r);
		GlStateManager.scale(1F, -1F, -1F);
		model.render();
		model.renderMana();
		GlStateManager.color(1F, 1F, 1F);

		GlStateManager.pushMatrix();
		// GlStateManager.enableAlpha();
		// GlStateManager.enableBlend();
		// GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// GlStateManager.color(0.1F, 0.1F, 1F, 0.5F);
		// model.renderCube();
		// model.renderMana();
		// GlStateManager.disableBlend();
		// GlStateManager.disableAlpha();
		GlStateManager.popMatrix();
		GlStateManager.scale(1F, -1F, -1F);

		GlStateManager.enableRescaleNormal();
		GlStateManager.popMatrix();
	}

}
