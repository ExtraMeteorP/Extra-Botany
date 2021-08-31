package com.meteor.extrabotany.client;

import com.meteor.extrabotany.client.model.ModelHerrscher;
import com.meteor.extrabotany.common.handler.HerrscherHandler;
import com.meteor.extrabotany.common.libs.LibMisc;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;

public class LayerHerrscher extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {

    private ModelHerrscher layer = new ModelHerrscher();
    private ResourceLocation texture = new ResourceLocation(LibMisc.MOD_ID, "textures/entity/herrscher.png");

    public LayerHerrscher(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> renderer) {
        super(renderer);
    }

    @Override
    public void render(@Nonnull MatrixStack ms, @Nonnull IRenderTypeBuffer buffers, int light, AbstractClientPlayerEntity player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(player != null && HerrscherHandler.isHerrscherOfThunder(player)) {
            IVertexBuilder buffer = buffers.getBuffer(layer.getRenderType(texture)).lightmap(0xF000F0);
            ms.push();
            ms.translate(0, -0.4F, 0);
            getEntityModel().bipedLeftArm.translateRotate(ms);
            layer.renderLeftArm(ms, buffer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            ms.pop();

            ms.push();
            ms.translate(0, -0.4F, 0);
            getEntityModel().bipedRightArm.translateRotate(ms);
            layer.renderRightArm(ms, buffer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            ms.pop();

            ms.push();
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            RenderSystem.disableLighting();
            ms.scale(1.4F, 1.4F, 1.4F);
            ms.translate(0, -0.2F, 0);
            getEntityModel().bipedBody.translateRotate(ms);
            layer.renderStigma(ms, buffer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.75F);
            ms.scale(1 / 1.4F, 1 / 1.4F, 1 / 1.4F);
            RenderSystem.enableLighting();
            ms.pop();
        }
    }

}
