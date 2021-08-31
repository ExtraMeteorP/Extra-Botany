package com.meteor.extrabotany.client.renderer.entity;

import com.meteor.extrabotany.client.model.ModelSlash;
import com.meteor.extrabotany.common.entities.EntitySlash;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderSlash extends EntityRenderer<EntitySlash> {

    private EntityModel<Entity> slashModel = new ModelSlash();
    private int frames = 35;
    public RenderSlash(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(EntitySlash entity) {
        return new ResourceLocation(LibMisc.MOD_ID, "textures/entity/slash_" + entity.ticksExisted % frames + ".png");
    }

    @Override
    public void render(EntitySlash entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        Minecraft mc = Minecraft.getInstance();
        matrixStackIn.push();
        matrixStackIn.rotate(mc.getRenderManager().getCameraOrientation());
        IVertexBuilder buffer = bufferIn.getBuffer(this.slashModel.getRenderType(this.getEntityTexture(entityIn))).lightmap(0xF000F0);
        matrixStackIn.scale(1.75F, 1.75F, 1.75F);
        matrixStackIn.scale(1.0F, -1.0F, -1.0F);
        this.slashModel.render(matrixStackIn, buffer, 0xF000F0, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.scale(1.0F, -1.0F, -1.0F);
        matrixStackIn.scale(1F/1.75F, 1F/1.75F, 1F/1.75F);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

}
