package com.meteor.extrabotany.client;

import com.meteor.extrabotany.client.handler.ClientTickHandler;
import com.meteor.extrabotany.client.handler.MiscellaneousIcons;
import com.meteor.extrabotany.common.handler.FlamescionHandler;
import com.meteor.extrabotany.common.items.ModItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

public class LayerFlamescion extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {

    public LayerFlamescion(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack ms, IRenderTypeBuffer buffers, int packedLightIn, AbstractClientPlayerEntity player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(player != null && player.getHeldItemMainhand().getItem() == FlamescionHandler.getFlamescionWeapon()) {
            ms.push();
            getEntityModel().bipedBody.translateRotate(ms);
            float alpha = (float) (0.75F + 0.15F * Math.cos(ClientTickHandler.ticksInGame/20F));
            int color = 0xFFFFFF | ((int) (alpha * 255F)) << 24;
            IBakedModel model = MiscellaneousIcons.INSTANCE.flamescionringModel[0];
            ms.translate(-0.6F, -0.6F, 0);
            float s = 1.2F;
            ms.scale(s,s,s);
            ms.rotate(Vector3f.YP.rotationDegrees(-20F));
            ms.rotate(Vector3f.ZP.rotationDegrees(-40F));
            ms.rotate(Vector3f.XP.rotationDegrees(100F));
            ms.rotate(Vector3f.ZN.rotationDegrees(ClientTickHandler.ticksInGame/5F));
            int light = (int) (0xF000B0 + 0x000030 * Math.cos(ClientTickHandler.ticksInGame/20F));
            RenderHelper.renderItemCustomColor(player, new ItemStack(ModItems.flamescionweapon), color, ms, buffers, light, OverlayTexture.NO_OVERLAY, model);
            ms.scale(1F/s, 1F/s,  1F/s);
            ms.pop();
        }
    }
}
