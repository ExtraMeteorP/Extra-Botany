package com.meteor.extrabotany.client.renderer.entity;

import com.meteor.extrabotany.client.RenderHelper;
import com.meteor.extrabotany.common.entities.projectile.EntityProjectileBase;
import com.meteor.extrabotany.common.items.ModItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderProjectileBase extends EntityRenderer<EntityProjectileBase> {

    public RenderProjectileBase(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityProjectileBase weapon, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        Minecraft mc = Minecraft.getInstance();

        matrixStackIn.push();

        matrixStackIn.push();
        float s = 1.2F;
        matrixStackIn.scale(s, s, s);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(weapon.getRotation()+90F));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(weapon.getPitch()));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(-45));

        float alpha = 0.9F;
        IBakedModel model = weapon.getIcon();
        int color = 0xFFFFFF | ((int) (alpha * 255F)) << 24;
        RenderHelper.renderItemCustomColor(mc.player, new ItemStack(ModItems.firstfractal), color, matrixStackIn, bufferIn, 0xF000F0, OverlayTexture.NO_OVERLAY, model);

        matrixStackIn.scale(1 / s, 1 / s, 1 / s);
        matrixStackIn.pop();

        matrixStackIn.pop();
        super.render(weapon, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityProjectileBase entity) {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }

}
