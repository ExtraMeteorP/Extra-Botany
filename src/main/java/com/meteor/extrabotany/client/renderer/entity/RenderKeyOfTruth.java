package com.meteor.extrabotany.client.renderer.entity;

import com.meteor.extrabotany.common.entities.EntityKeyOfTruth;
import com.meteor.extrabotany.common.libs.LibMisc;
import com.meteor.extrabotany.client.model.ModelKeyOfTruth;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderKeyOfTruth extends EntityRenderer<EntityKeyOfTruth> {

    private EntityModel<EntityKeyOfTruth> motorModel = new ModelKeyOfTruth();

    private static final ResourceLocation GUARDIAN_BEAM_TEXTURE = new ResourceLocation(LibMisc.MOD_ID, "textures/entity/energybeam.png");
    private static final RenderType field_229107_h_ = RenderType.getEntityCutoutNoCull(GUARDIAN_BEAM_TEXTURE);
    public RenderKeyOfTruth(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityKeyOfTruth entity) {
        return new ResourceLocation(LibMisc.MOD_ID, "textures/entity/keyoftruth.png");
    }

    @Override
    public void render(EntityKeyOfTruth entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.translate(0.0D, 1.375D, 0.0D);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(entityIn.getRotation()));
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(entityIn.getPitch()));
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.motorModel.getRenderType(this.getEntityTexture(entityIn)));
        matrixStackIn.scale(1.0F, -1.0F, -1.0F);
        this.motorModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.65F);
        matrixStackIn.scale(1.0F, -1.0F, -1.0F);
        matrixStackIn.pop();

        if (entityIn.getTarget() != -1 && entityIn.getShoot()) {
            Entity livingentity = entityIn.world.getEntityByID(entityIn.getTarget());
            if(livingentity != null && livingentity.isLiving()) {
                float f = 0.5F;
                float f1 = (float) entityIn.world.getGameTime() + partialTicks;
                float f2 = f1 * 0.5F % 1.0F;
                float f3 = entityIn.getEyeHeight();
                matrixStackIn.push();
                matrixStackIn.translate(0.0D, (double) f3, 0.0D);
                Vector3d vec3d= this.getPosition(livingentity, (double) livingentity.getHeight() * 0.5D, partialTicks);
                Vector3d vec3d1 = this.getPosition(entityIn, (double) f3, partialTicks);
                Vector3d vec3d2 = vec3d.subtract(vec3d1);
                float f4 = (float) (vec3d2.length() + 1.0D);
                vec3d2 = vec3d2.normalize();
                float f5 = (float) Math.acos(vec3d2.y);
                float f6 = (float) Math.atan2(vec3d2.z, vec3d2.x);
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees((((float) Math.PI / 2F) - f6) * (180F / (float) Math.PI)));
                matrixStackIn.rotate(Vector3f.XP.rotationDegrees(f5 * (180F / (float) Math.PI)));
                int i = 1;
                float f7 = f1 * 0.05F * -1.5F;
                float f8 = f * f;
                int j = 10;
                int k = 229;
                int l = 238;
                float f9 = 0.2F;
                float f10 = 0.282F;
                float f11 = MathHelper.cos(f7 + 2.3561945F) * 0.282F;
                float f12 = MathHelper.sin(f7 + 2.3561945F) * 0.282F;
                float f13 = MathHelper.cos(f7 + ((float) Math.PI / 4F)) * 0.282F;
                float f14 = MathHelper.sin(f7 + ((float) Math.PI / 4F)) * 0.282F;
                float f15 = MathHelper.cos(f7 + 3.926991F) * 0.282F;
                float f16 = MathHelper.sin(f7 + 3.926991F) * 0.282F;
                float f17 = MathHelper.cos(f7 + 5.4977875F) * 0.282F;
                float f18 = MathHelper.sin(f7 + 5.4977875F) * 0.282F;
                float f19 = MathHelper.cos(f7 + (float) Math.PI) * 0.2F;
                float f20 = MathHelper.sin(f7 + (float) Math.PI) * 0.2F;
                float f21 = MathHelper.cos(f7 + 0.0F) * 0.2F;
                float f22 = MathHelper.sin(f7 + 0.0F) * 0.2F;
                float f23 = MathHelper.cos(f7 + ((float) Math.PI / 2F)) * 0.2F;
                float f24 = MathHelper.sin(f7 + ((float) Math.PI / 2F)) * 0.2F;
                float f25 = MathHelper.cos(f7 + ((float) Math.PI * 1.5F)) * 0.2F;
                float f26 = MathHelper.sin(f7 + ((float) Math.PI * 1.5F)) * 0.2F;
                float f27 = 0.0F;
                float f28 = 0.4999F;
                float f29 = -1.0F + f2;
                float f30 = f4 * 2.5F + f29;
                IVertexBuilder ivertexbuilder2 = bufferIn.getBuffer(field_229107_h_);
                MatrixStack.Entry matrixstack$entry = matrixStackIn.getLast();
                Matrix4f matrix4f = matrixstack$entry.getMatrix();
                Matrix3f matrix3f = matrixstack$entry.getNormal();
                func_229108_a_(ivertexbuilder2, matrix4f, matrix3f, f19, f4, f20, j, k, l, 0.4999F, f30);
                func_229108_a_(ivertexbuilder2, matrix4f, matrix3f, f19, 0.0F, f20, j, k, l, 0.4999F, f29);
                func_229108_a_(ivertexbuilder2, matrix4f, matrix3f, f21, 0.0F, f22, j, k, l, 0.0F, f29);
                func_229108_a_(ivertexbuilder2, matrix4f, matrix3f, f21, f4, f22, j, k, l, 0.0F, f30);
                func_229108_a_(ivertexbuilder2, matrix4f, matrix3f, f23, f4, f24, j, k, l, 0.4999F, f30);
                func_229108_a_(ivertexbuilder2, matrix4f, matrix3f, f23, 0.0F, f24, j, k, l, 0.4999F, f29);
                func_229108_a_(ivertexbuilder2, matrix4f, matrix3f, f25, 0.0F, f26, j, k, l, 0.0F, f29);
                func_229108_a_(ivertexbuilder2, matrix4f, matrix3f, f25, f4, f26, j, k, l, 0.0F, f30);
                float f31 = 0.0F;

                func_229108_a_(ivertexbuilder2, matrix4f, matrix3f, f11, f4, f12, j, k, l, 0.5F, f31 + 0.5F);
                func_229108_a_(ivertexbuilder2, matrix4f, matrix3f, f13, f4, f14, j, k, l, 1.0F, f31 + 0.5F);
                func_229108_a_(ivertexbuilder2, matrix4f, matrix3f, f17, f4, f18, j, k, l, 1.0F, f31);
                func_229108_a_(ivertexbuilder2, matrix4f, matrix3f, f15, f4, f16, j, k, l, 0.5F, f31);
                matrixStackIn.pop();
            }
        }

        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    private Vector3d getPosition(Entity entityLivingBaseIn, double p_177110_2_, float p_177110_4_) {
        double d0 = MathHelper.lerp((double)p_177110_4_, entityLivingBaseIn.lastTickPosX, entityLivingBaseIn.getPosX());
        double d1 = MathHelper.lerp((double)p_177110_4_, entityLivingBaseIn.lastTickPosY, entityLivingBaseIn.getPosY()) + p_177110_2_;
        double d2 = MathHelper.lerp((double)p_177110_4_, entityLivingBaseIn.lastTickPosZ, entityLivingBaseIn.getPosZ());
        return new Vector3d(d0, d1, d2);
    }

    private static void func_229108_a_(IVertexBuilder p_229108_0_, Matrix4f p_229108_1_, Matrix3f p_229108_2_, float p_229108_3_, float p_229108_4_, float p_229108_5_, int p_229108_6_, int p_229108_7_, int p_229108_8_, float p_229108_9_, float p_229108_10_) {
        p_229108_0_.pos(p_229108_1_, p_229108_3_, p_229108_4_, p_229108_5_).color(p_229108_6_, p_229108_7_, p_229108_8_, 255).tex(p_229108_9_, p_229108_10_).overlay(OverlayTexture.NO_OVERLAY).lightmap(15728880).normal(p_229108_2_, 0.0F, 1.0F, 0.0F).endVertex();
    }

}
