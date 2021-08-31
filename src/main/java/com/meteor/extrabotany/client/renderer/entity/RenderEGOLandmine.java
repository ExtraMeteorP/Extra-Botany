package com.meteor.extrabotany.client.renderer.entity;

import com.meteor.extrabotany.common.entities.ego.EntityEGOLandmine;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.client.render.tile.RenderTileSpecialFlower;

import javax.annotation.Nonnull;

public class RenderEGOLandmine extends EntityRenderer<EntityEGOLandmine> {

    private static final double INITIAL_OFFSET = -1.0 / 16 + 0.005;
    // Global y offset so that overlapping landmines do not Z-fight
    public static double offY = INITIAL_OFFSET;

    public RenderEGOLandmine(EntityRendererManager renderManager) {
        super(renderManager);
    }

    public static void onWorldRenderLast(RenderWorldLastEvent evt) {
        offY = INITIAL_OFFSET;
    }

    @Override
    public void render(EntityEGOLandmine e, float entityYaw, float partialTicks, MatrixStack ms, IRenderTypeBuffer buffers, int light) {
        super.render(e, entityYaw, partialTicks, ms, buffers, light);

        ms.push();
        AxisAlignedBB aabb = e.getBoundingBox().offset(e.getPositionVec().scale(-1));

        float gs = (float) (Math.sin(ClientTickHandler.total / 20) + 1) * 0.2F + 0.6F;
        int r = 0, g = 0, b = 0;
        switch (e.getLandmineType()){
            case 0:
                b = 240;
                break;
            case 1:
                g = 240;
                break;
            case 2:
                r = 240;
                break;
        }
        r = (int)(r * gs);
        g = (int)(g * gs);
        b = (int)(b * gs);

        int color = r << 16 | g << 8 | b;

        int alpha = 32;
        if (e.ticksExisted < 8) {
            alpha *= Math.min((e.ticksExisted + partialTicks) / 8F, 1F);
        } else if (e.ticksExisted > 47) {
            alpha *= Math.min(1F - (e.ticksExisted - 47 + partialTicks) / 8F, 1F);
        }

        RenderTileSpecialFlower.renderRectangle(ms, buffers, aabb, false, color, (byte) alpha);
        offY += 0.001;
        ms.pop();
    }

    @Nonnull
    @Override
    public ResourceLocation getEntityTexture(@Nonnull EntityEGOLandmine entity) {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }
}
