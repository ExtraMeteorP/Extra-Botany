package com.meteor.extrabotany.client.renderer.entity;

import com.meteor.extrabotany.client.model.ModelSlash;
import com.meteor.extrabotany.common.entities.EntityFlamescionSlash;
import com.meteor.extrabotany.common.libs.LibMisc;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderFlamescionSlash extends EntityRenderer<EntityFlamescionSlash> {

    private EntityModel<Entity> slashModel = new ModelSlash();
    private int frames = 6;

    public RenderFlamescionSlash(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityFlamescionSlash entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        Minecraft mc = Minecraft.getInstance();
        matrixStackIn.push();
        matrixStackIn.rotate(mc.getRenderManager().getCameraOrientation());
        IVertexBuilder buffer = bufferIn.getBuffer(this.slashModel.getRenderType(this.getEntityTexture(entityIn))).lightmap(0xF000F0);
        float s = 3.0F;
        matrixStackIn.scale(s,s,s);
        matrixStackIn.scale(1.0F, -1.0F, -1.0F);
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(entityIn.getPitch()));
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(entityIn.getRotation()));
        this.slashModel.render(matrixStackIn, buffer, 0xF000F0, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.scale(1.0F, -1.0F, -1.0F);
        matrixStackIn.scale(1F/s, 1F/s, 1F/s);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityFlamescionSlash entity) {
        return new ResourceLocation(LibMisc.MOD_ID, "textures/entity/flamescionslash_" + entity.ticksExisted % frames + ".png");
    }
}
