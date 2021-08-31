package com.meteor.extrabotany.client.renderer.entity;

import com.meteor.extrabotany.common.entities.EntityFlamescionUlt;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderFlamescionUlt extends EntityRenderer<EntityFlamescionUlt> {

    TranslationTextComponent[] texts = new TranslationTextComponent[]{
            new TranslationTextComponent("mri.ult0"),
            new TranslationTextComponent("mri.ult1"),
            new TranslationTextComponent("mri.ult2"),
            new TranslationTextComponent("mri.ult3"),
            new TranslationTextComponent("mri.ult4"),
            new TranslationTextComponent("mri.ult5"),
            new TranslationTextComponent("mri.ult6"),
            new TranslationTextComponent("mri.ult7"),
    };

    public RenderFlamescionUlt(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityFlamescionUlt entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        Minecraft mc = Minecraft.getInstance();
        matrixStackIn.push();
        matrixStackIn.rotate(mc.getRenderManager().getCameraOrientation());
        float f1 = 0.016666668F * 4F;
        matrixStackIn.scale(-f1, -f1, f1);
        matrixStackIn.translate(0, -60D, 0);
        for(TranslationTextComponent text : texts) {

            matrixStackIn.translate(0, 12D, 0);
            int halfWidth = mc.fontRenderer.getStringWidth(text.getString()) / 2;
            mc.fontRenderer.func_243247_a(text.mergeStyle(TextFormatting.DARK_RED), -halfWidth, 0, 0xFFFFFFFF, false, matrixStackIn.getLast().getMatrix(), bufferIn, false, 0, packedLightIn);

        }
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityFlamescionUlt entity) {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }

    @Override
    public boolean shouldRender(EntityFlamescionUlt entity, ClippingHelper camera, double camX, double camY, double camZ) {
        return true;
    }

}
