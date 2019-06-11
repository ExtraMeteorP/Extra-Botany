package com.meteor.extrabotany.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGreenHat extends ModelBase{

	    ModelRenderer Shape1;
	    ModelRenderer Shape2;
	  
	  public ModelGreenHat(){
		  textureWidth = 64;
		  textureHeight = 64;
	      Shape1 = new ModelRenderer(this, 0, 17);
	      Shape1.addBox(0F, 0F, 0F, 12, 4, 12);
	      Shape1.setRotationPoint(-6F, -9F, -6F);
	      Shape1.setTextureSize(64, 64);
	      Shape1.mirror = true;
	      setRotation(Shape1, 0F, 0F, 0F);
	      Shape2 = new ModelRenderer(this, 0, 0);
	      Shape2.addBox(0F, 0F, 0F, 8, 9, 8);
	      Shape2.setRotationPoint(-4F, -18F, -4F);
	      Shape2.setTextureSize(64, 64);
	      Shape2.mirror = true;
	      setRotation(Shape2, 0F, 0F, 0F);
	  }
	  
	  public void render(Entity entity, float f, float f1, float f5){
	    super.render(entity, f, f1, entity.ticksExisted, entity.rotationYaw, entity.rotationPitch, f5);
	    setRotationAngles(f, f1, entity.ticksExisted, entity.rotationYaw, entity.rotationPitch, f5, entity);
	    Shape1.render(f5);
	    Shape2.render(f5);
	  }
	  
	  private void setRotation(ModelRenderer model, float x, float y, float z){
	    model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
	  }
	  
	  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity){
	    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	  }
}
