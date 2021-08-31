package com.meteor.extrabotany.client.renderer.entity;

import com.meteor.extrabotany.common.entities.mountable.EntityMotor;
import com.meteor.extrabotany.common.libs.LibMisc;
import com.meteor.extrabotany.client.model.ModelMotor;
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
public class RenderMotor extends EntityRenderer<EntityMotor> {

    private EntityModel<EntityMotor> motorModel = new ModelMotor();

    public RenderMotor(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityMotor entity) {
        return new ResourceLocation(LibMisc.MOD_ID, "textures/entity/motor.png");
    }

    @Override
    public void render(EntityMotor entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.translate(0.0D, 1.500D, 0.0D);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F - entityYaw));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(entityIn.getRotation()));
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(entityIn.getPitch()));
        //matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90.0F));
        this.motorModel.setRotationAngles(entityIn, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.motorModel.getRenderType(this.getEntityTexture(entityIn)));
        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        this.motorModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
}
