package com.meteor.extrabotany.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelVoid extends ModelBase{
	
	ModelRenderer Shape1;
	  
	public ModelVoid(){
		textureWidth = 64;
		textureHeight = 64;
	    
		Shape1 = new ModelRenderer(this, 0, 0);
		Shape1.addBox(0F, 0F, 0F, 16, 16, 16);
		Shape1.setRotationPoint(-12F, 3F, -6F);
		Shape1.setTextureSize(64, 64);
		Shape1.mirror = true;
		setRotation(Shape1, 0.7853982F, 0.7853982F, 0.7853982F);
	}
	  
	public void render(){
		float f5 = 2.8F/16F;
		Shape1.render(f5);
	}
	  
	protected void setRotation(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
