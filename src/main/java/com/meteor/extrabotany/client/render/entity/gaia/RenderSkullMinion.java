package com.meteor.extrabotany.client.render.entity.gaia;

import com.meteor.extrabotany.common.entity.gaia.EntitySkullMinion;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSkullMinion extends Render<EntitySkullMinion>{
  
    private static final ResourceLocation SKULL_TEXTURES = new ResourceLocation("extrabotany:textures/entity/skullminion1.png");
    private static final ResourceLocation SKULL_TEXTURES2 = new ResourceLocation("extrabotany:textures/entity/skullminion2.png");
    private static final ResourceLocation SKULL_TEXTURES3 = new ResourceLocation("extrabotany:textures/entity/skullminion3.png");
    private static final ResourceLocation SKULL_TEXTURES4 = new ResourceLocation("extrabotany:textures/entity/skullminion4.png");

    private final static ModelSkeletonHead skeletonHeadModel = new ModelSkeletonHead();

    public RenderSkullMinion(RenderManager renderManagerIn){
        super(renderManagerIn);
    }

    private float getRenderYaw(float p_82400_1_, float p_82400_2_, float p_82400_3_){
        float f;

        for (f = p_82400_2_ - p_82400_1_; f < -180.0F; f += 360.0F){
            ;
        }

        while (f >= 180.0F){
            f -= 360.0F;
        }

        return p_82400_1_ + p_82400_3_ * f;
    }

    public void doRender(EntitySkullMinion entity, double x, double y, double z, float entityYaw, float partialTicks){
    	GlStateManager.pushMatrix();
    	GlStateManager.disableCull();
        float f = this.getRenderYaw(entity.prevRotationYaw, entity.rotationYaw, partialTicks);
        float f1 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
        GlStateManager.translate((float)x, (float)y, (float)z);
        float f2 = 0.0625F;
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        GlStateManager.enableAlpha();
        this.bindEntityTexture(entity);

        if (this.renderOutlines){
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        this.skeletonHeadModel.render(entity, 0.0F, 0.0F, 0.0F, f, f1, 0.0625F);

        if (this.renderOutlines){
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    protected ResourceLocation getEntityTexture(EntitySkullMinion entity){
    	switch(entity.getType()){
    		case 0:
    			return SKULL_TEXTURES;
    		case 1:
    			return SKULL_TEXTURES2;
    		case 2:
    			return SKULL_TEXTURES3;
    		case 3:
    			return SKULL_TEXTURES4;
		default:
    			return SKULL_TEXTURES;
    	}
    }
}
