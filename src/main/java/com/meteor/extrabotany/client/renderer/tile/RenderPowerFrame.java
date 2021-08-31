package com.meteor.extrabotany.client.renderer.tile;

import com.meteor.extrabotany.client.handler.ClientTickHandler;
import com.meteor.extrabotany.common.blocks.tile.TilePowerFrame;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

public class RenderPowerFrame extends TileEntityRenderer<TilePowerFrame> {

    public RenderPowerFrame(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(TilePowerFrame tile, float partialTicks, MatrixStack ms, IRenderTypeBuffer buffers, int light, int overlay) {
        ms.push();
        ms.translate(0.5, 0.5, 0.5);
        boolean hasItem = !tile.getItemHandler().isEmpty();
        if(hasItem){
            ms.rotate(Vector3f.YP.rotationDegrees(ClientTickHandler.ticksInGame * 0.5F));
            ItemStack stack = tile.getItemHandler().getStackInSlot(0);
            Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.GROUND, light, overlay, ms, buffers);
        }
        ms.pop();
    }
}
