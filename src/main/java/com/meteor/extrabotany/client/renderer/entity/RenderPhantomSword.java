package com.meteor.extrabotany.client.renderer.entity;

import com.meteor.extrabotany.client.handler.MiscellaneousIcons;
import com.meteor.extrabotany.client.RenderHelper;
import com.meteor.extrabotany.common.entities.projectile.EntityPhantomSword;
import com.meteor.extrabotany.common.items.ModItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class RenderPhantomSword extends EntityRenderer<EntityPhantomSword> {

    public RenderPhantomSword(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityPhantomSword weapon, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        Minecraft mc = Minecraft.getInstance();
        if(weapon.getDelay() > 0)
            return;

        matrixStackIn.push();

        matrixStackIn.push();
        float s = 1.5F;
        matrixStackIn.scale(s, s, s);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(weapon.getRotation()+90F));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(weapon.getPitch()));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(-45));

        float alpha = weapon.getFake() ? Math.max(0F, 0.75F - weapon.ticksExisted * (0.75F / EntityPhantomSword.LIVE_TICKS) * 1.5F) : 1F;
        IBakedModel model = MiscellaneousIcons.INSTANCE.firstFractalWeaponModels[weapon.getVariety()];
        int color = 0xFFFFFF | ((int) (alpha * 255F)) << 24;
        RenderHelper.renderItemCustomColor(mc.player, new ItemStack(ModItems.firstfractal), color, matrixStackIn, bufferIn, 0xF000F0, OverlayTexture.NO_OVERLAY, model);

        matrixStackIn.scale(1 / s, 1 / s, 1 / s);
        matrixStackIn.pop();

        matrixStackIn.pop();
        super.render(weapon, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityPhantomSword entity) {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }

}
