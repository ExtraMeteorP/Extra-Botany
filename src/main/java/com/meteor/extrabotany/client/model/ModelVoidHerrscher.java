package com.meteor.extrabotany.client.model;

import com.meteor.extrabotany.common.entity.gaia.EntityVoidHerrscher;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelVoidHerrscher extends ModelBiped{

	  ModelRenderer winga;
	  ModelRenderer wingb;
	  public ModelRenderer bipedLeftArmwear;
	  public ModelRenderer bipedRightArmwear;
	  public ModelRenderer bipedLeftLegwear;
	  public ModelRenderer bipedRightLegwear;
	  public ModelRenderer bipedBodyWear;
	  private final ModelRenderer bipedCape;
	  private final ModelRenderer bipedDeadmau5Head;
	  ModelRenderer Shield1;
	  ModelRenderer Shield2;
	  ModelRenderer Shield3;
	  
	  public ModelVoidHerrscher(float modelSize){
		  super(modelSize, 0.0F, 128, 128);
	      this.bipedDeadmau5Head = new ModelRenderer(this, 24, 0);
	      this.bipedDeadmau5Head.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, modelSize);
	      this.bipedCape = new ModelRenderer(this, 0, 0);
	      this.bipedCape.setTextureSize(64, 32);
	      this.bipedCape.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, modelSize);
	      
	      this.bipedLeftArm = new ModelRenderer(this, 32, 48);
          this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
          this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
          this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
          this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
          this.bipedLeftArmwear.setRotationPoint(5.0F, 2.0F, 0.0F);
          this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
          this.bipedRightArmwear.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
          this.bipedRightArmwear.setRotationPoint(-5.0F, 2.0F, 10.0F);
          
          this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
          this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
          this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
          this.bipedLeftLegwear = new ModelRenderer(this, 0, 48);
          this.bipedLeftLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
          this.bipedLeftLegwear.setRotationPoint(1.9F, 12.0F, 0.0F);
          this.bipedRightLegwear = new ModelRenderer(this, 0, 32);
          this.bipedRightLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
          this.bipedRightLegwear.setRotationPoint(-1.9F, 12.0F, 0.0F);
          this.bipedBodyWear = new ModelRenderer(this, 16, 32);
          this.bipedBodyWear.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, modelSize + 0.25F);
          this.bipedBodyWear.setRotationPoint(0.0F, 0.0F, 0.0F);
	        
	      winga = new ModelRenderer(this, 0, 64);
	      winga.addBox(0F, 0F, 0F, 26, 32, 0);
	      winga.setRotationPoint(-30F, -11F, 5F);
	      winga.setTextureSize(128, 128);
	      winga.mirror = true;
	      setRotation(winga, 0F, 0F, -0.1745329F);
	      wingb = new ModelRenderer(this, 52, 64);
	      wingb.addBox(0F, 0F, 0F, 26, 32, 0);
	      wingb.setRotationPoint(4F, -15.8F, 5F);
	      wingb.setTextureSize(128, 128);
	      wingb.mirror = true;
	      setRotation(wingb, 0F, 0F, 0.1745329F);
	      
	      Shield1 = new ModelRenderer(this, 0, 113);
	      Shield1.addBox(-6F, -1F, 9F, 12, 11, 0);
	      Shield1.setRotationPoint(0F, 0F, 0F);
	      Shield1.setTextureSize(128, 128);
	      Shield1.mirror = true;
	      setRotation(Shield1, 0F, 0F, 0F);
	      
	      Shield2 = new ModelRenderer(this, 0, 113);
	      Shield2.addBox(-6F, -1F, 9F, 12, 11, 0);
	      Shield2.setRotationPoint(0F, 0F, 0F);
	      Shield2.setTextureSize(128, 128);
	      Shield2.mirror = true;
	      setRotation(Shield2, 0F, 2.094395F, 0F);
	      
	      Shield3 = new ModelRenderer(this, 0, 113);
	      Shield3.addBox(-6F, -1F, 9F, 12, 11, 0);
	      Shield3.setRotationPoint(0F, 0F, 0F);
	      Shield3.setTextureSize(128, 128);
	      Shield3.mirror = true;
	      setRotation(Shield1, 0F, -2.094395F, 0F);
	  }
	  
	  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float scale){
	    super.render(entity, f, f1, f2, f3, f4, scale);
	    GlStateManager.pushMatrix();
	    if (entity.isSneaking()){
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
        }

        this.bipedLeftLegwear.render(scale);
        this.bipedRightLegwear.render(scale);
        this.bipedLeftArmwear.render(scale);
        this.bipedRightArmwear.render(scale);
        this.bipedBodyWear.render(scale);
        GlStateManager.popMatrix();
	    setRotationAngles(f, f1, f2, f3, f4, scale, entity);
	    if(((EntityVoidHerrscher)entity).getRankII()){
		    winga.render(scale);
		    wingb.render(scale);
	    }
	    
	    if(((EntityVoidHerrscher)entity).getShields() > 2)
		    Shield3.render(scale);
	    if(((EntityVoidHerrscher)entity).getShields() > 1)
		    Shield2.render(scale);
	    if(((EntityVoidHerrscher)entity).getShields() > 0)
		    Shield1.render(scale);
	  }
	    
	 public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn){
	        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
	        copyModelAngles(this.bipedLeftLeg, this.bipedLeftLegwear);
	        copyModelAngles(this.bipedRightLeg, this.bipedRightLegwear);
	        copyModelAngles(this.bipedLeftArm, this.bipedLeftArmwear);
	        copyModelAngles(this.bipedRightArm, this.bipedRightArmwear);
	        copyModelAngles(this.bipedBody, this.bipedBodyWear);

	        if (entityIn.isSneaking()){
	            this.bipedCape.rotationPointY = 2.0F;
	        }
	        else{
	            this.bipedCape.rotationPointY = 0.0F;
	        }
	        
	        this.Shield1.rotateAngleY = (float) (MathHelper.sin(ageInTicks*0.1F) + Math.PI*2 / ((EntityVoidHerrscher)entityIn).getShields() *1);
	        this.Shield2.rotateAngleY = (float) (MathHelper.sin(ageInTicks*0.1F) + Math.PI*2 / ((EntityVoidHerrscher)entityIn).getShields() *2);
	        this.Shield3.rotateAngleY = (float) (MathHelper.sin(ageInTicks*0.1F) + Math.PI*2 / ((EntityVoidHerrscher)entityIn).getShields() *3);
	 }
	  
	 private void setRotation(ModelRenderer model, float x, float y, float z){
		 model.rotateAngleX = x;
		 model.rotateAngleY = y;
		 model.rotateAngleZ = z;
	 }
	  
}
