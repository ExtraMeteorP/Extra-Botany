package com.meteor.extrabotany.client.renderer.entity;

import com.meteor.extrabotany.client.model.ModelUfo;
import com.meteor.extrabotany.common.entities.mountable.EntityUfo;
import com.meteor.extrabotany.common.libs.LibMisc;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderUfo extends EntityRenderer<EntityUfo> {

    private EntityModel<EntityUfo> ufoModel = new ModelUfo();

    public RenderUfo(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityUfo entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.translate(0.0D, 2.500D, 0.0D);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F - entityYaw));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(entityIn.getRotation()));
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(entityIn.getPitch()));
        //matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90.0F));
        this.ufoModel.setRotationAngles(entityIn, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.ufoModel.getRenderType(this.getEntityTexture(entityIn)));
        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        float s = 1.35F;
        matrixStackIn.scale(s, s, s);
        this.ufoModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.scale(1F/s, 1F/s, 1F/s);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityUfo entity) {
        return new ResourceLocation(LibMisc.MOD_ID, "textures/entity/ufo.png");
    }
}
