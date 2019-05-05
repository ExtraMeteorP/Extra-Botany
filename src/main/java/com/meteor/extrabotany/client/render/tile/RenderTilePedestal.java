package com.meteor.extrabotany.client.render.tile;

import com.meteor.extrabotany.common.block.ModBlocks;
import com.meteor.extrabotany.common.block.tile.TilePedestal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;

public class RenderTilePedestal extends TileEntitySpecialRenderer {

	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if(te != null && (!te.getWorld().isBlockLoaded(te.getPos(), false)
				|| te.getWorld().getBlockState(te.getPos()).getBlock() != ModBlocks.pedestal))
			return;
		
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GlStateManager.pushMatrix();
		GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);

		TilePedestal ped = (TilePedestal)te;

		if (ped != null && !ped.getItem().isEmpty()){
			GlStateManager.pushMatrix();

			boolean flag = ped.getItem().getItem() instanceof ItemBlock;

			GlStateManager.rotate(180F, 1F, 0F, 0F);
			GlStateManager.translate(0.0F, flag ? -0.56F : -0.37F, 0F);
			GlStateManager.rotate(ped.getRotation(), 0F, 1F, 0F);

			Minecraft.getMinecraft().getRenderItem().renderItem(ped.getItem(), ItemCameraTransforms.TransformType.GROUND);

			GlStateManager.popMatrix();
		}

		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
	}
}
