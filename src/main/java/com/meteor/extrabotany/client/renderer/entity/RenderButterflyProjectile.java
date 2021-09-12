package com.meteor.extrabotany.client.renderer.entity;

import com.meteor.extrabotany.client.RenderHelper;
import com.meteor.extrabotany.client.handler.MiscellaneousIcons;
import com.meteor.extrabotany.common.entities.projectile.EntityButterflyProjectile;
import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.libs.LibMisc;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderButterflyProjectile extends EntityRenderer<EntityButterflyProjectile> {

    public RenderButterflyProjectile(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityButterflyProjectile entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        Minecraft mc = Minecraft.getInstance();
        matrixStackIn.push();
        matrixStackIn.rotate(mc.getRenderManager().getCameraOrientation());

        float alpha = 0.9F;
        IBakedModel model = MiscellaneousIcons.INSTANCE.butterflyprojectileModel[0];
        int color = 0xFFFFFF | ((int) (alpha * 255F)) << 24;
        RenderHelper.renderItemCustomColor(mc.player, new ItemStack(ModItems.uuzfan), color, matrixStackIn, bufferIn, 0xF000F0, OverlayTexture.NO_OVERLAY, model);

        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityButterflyProjectile entity) {
        return new ResourceLocation(LibMisc.MOD_ID, "textures/entity/proj_butterfly.png");
    }
}
