package com.meteor.extrabotany.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBoatNew extends ModelBase
{
	  //fields
	    ModelRenderer bu1;
	    ModelRenderer bu2;
	    ModelRenderer bu3;
	    ModelRenderer Shape1;
	    ModelRenderer Shape2;
	    ModelRenderer Shape10;
	    ModelRenderer Shape3;
	    ModelRenderer Shape4;
	    ModelRenderer Shape11;
	    ModelRenderer Shape5;
	    ModelRenderer Shape6;
	    ModelRenderer Shape12;
	    ModelRenderer Shape7;
	    ModelRenderer Shape13;
	    ModelRenderer Shape8;
	  
	  public ModelBoatNew()
	  {
	    textureWidth = 128;
	    textureHeight = 32;
	    
	      bu1 = new ModelRenderer(this, 0, 0);
	      bu1.addBox(-11F, 0F, 0F, 22, 1, 11);
	      bu1.setRotationPoint(0F, 23F, 0F);
	      bu1.setTextureSize(64, 32);
	      bu1.mirror = true;
	      setRotation(bu1, 0.4363323F, -0.0174533F, 0F);
	      bu2 = new ModelRenderer(this, 0, 0);
	      bu2.addBox(-11F, 0F, 0F, 22, 1, 11);
	      bu2.setRotationPoint(0F, 24F, 0F);
	      bu2.setTextureSize(64, 32);
	      bu2.mirror = true;
	      setRotation(bu2, 2.70526F, 0.0174533F, 0F);
	      bu3 = new ModelRenderer(this, 0, 0);
	      bu3.addBox(0F, 0F, 0F, 9, 1, 8);
	      bu3.setRotationPoint(-16F, 22F, -0.5F);
	      bu3.setTextureSize(64, 32);
	      bu3.mirror = true;
	      setRotation(bu3, 0.4712389F, -0.4712389F, 0F);
	      Shape1 = new ModelRenderer(this, 0, 0);
	      Shape1.addBox(0F, -1F, 0F, 9, 1, 8);
	      Shape1.setRotationPoint(-16F, 22F, 1F);
	      Shape1.setTextureSize(64, 32);
	      Shape1.mirror = true;
	      setRotation(Shape1, 2.670354F, 0.4833219F, 0F);
	      Shape2 = new ModelRenderer(this, 0, 0);
	      Shape2.addBox(0F, 0F, 0F, 6, 1, 5);
	      Shape2.setRotationPoint(-22F, 19F, -0.5F);
	      Shape2.setTextureSize(64, 32);
	      Shape2.mirror = true;
	      setRotation(Shape2, 3.141593F, -0.3839724F, 0.6457718F);
	      Shape10 = new ModelRenderer(this, 0, 0);
	      Shape10.addBox(0F, -1F, 0F, 6, 1, 5);
	      Shape10.setRotationPoint(-22F, 19F, 1.3F);
	      Shape10.setTextureSize(64, 32);
	      Shape10.mirror = true;
	      setRotation(Shape10, 0F, 0.3839724F, 0.6283185F);
	      Shape3 = new ModelRenderer(this, 0, 0);
	      Shape3.addBox(0F, 0F, -2F, 4, 1, 4);
	      Shape3.setRotationPoint(-15F, 21.9F, 0F);
	      Shape3.setTextureSize(64, 32);
	      Shape3.mirror = true;
	      setRotation(Shape3, 0F, 0F, 0.122173F);
	      Shape4 = new ModelRenderer(this, 0, 0);
	      Shape4.addBox(0F, 0F, 0F, 1, 3, 11);
	      Shape4.setRotationPoint(11F, 21F, -1F);
	      Shape4.setTextureSize(64, 32);
	      Shape4.mirror = true;
	      setRotation(Shape4, 0.4363323F, 0F, 0F);
	      Shape11 = new ModelRenderer(this, 0, 0);
	      Shape11.addBox(0F, 0F, -11F, 1, 3, 11);
	      Shape11.setRotationPoint(11F, 21F, 1F);
	      Shape11.setTextureSize(64, 32);
	      Shape11.mirror = true;
	      setRotation(Shape11, -0.4363323F, 0F, 0F);
	      Shape5 = new ModelRenderer(this, 0, 0);
	      Shape5.addBox(0F, 0F, -6F, 1, 4, 12);
	      Shape5.setRotationPoint(11F, 17F, 0F);
	      Shape5.setTextureSize(64, 32);
	      Shape5.mirror = true;
	      setRotation(Shape5, 0F, 0F, 0F);
	      Shape6 = new ModelRenderer(this, 0, 0);
	      Shape6.addBox(0F, 0F, 0F, 23, 2, 1);
	      Shape6.setRotationPoint(-11F, 17F, -10F);
	      Shape6.setTextureSize(64, 32);
	      Shape6.mirror = true;
	      setRotation(Shape6, 0F, 0F, 0F);
	      Shape12 = new ModelRenderer(this, 0, 0);
	      Shape12.addBox(0F, 0F, 0F, 23, 2, 1);
	      Shape12.setRotationPoint(-11F, 17F, 9F);
	      Shape12.setTextureSize(64, 32);
	      Shape12.mirror = true;
	      setRotation(Shape12, 0F, 0F, 0F);
	      Shape7 = new ModelRenderer(this, 0, 0);
	      Shape7.addBox(0F, -2F, 0F, 15, 2, 2);
	      Shape7.setRotationPoint(0F, 22F, -8F);
	      Shape7.setTextureSize(64, 32);
	      Shape7.mirror = true;
	      setRotation(Shape7, -0.2617994F, 0F, 0F);
	      Shape13 = new ModelRenderer(this, 0, 0);
	      Shape13.addBox(0F, 0F, -2F, 15, 2, 2);
	      Shape13.setRotationPoint(0F, 20F, 8F);
	      Shape13.setTextureSize(64, 32);
	      Shape13.mirror = true;
	      setRotation(Shape13, 0.2617994F, 0F, 0F);
	      Shape8 = new ModelRenderer(this, 0, 0);
	      Shape8.addBox(0F, -3F, -1F, 1, 2, 2);
	      Shape8.setRotationPoint(-20F, 21F, 0.4F);
	      Shape8.setTextureSize(64, 32);
	      Shape8.mirror = true;
	      setRotation(Shape8, 0F, 0F, -0.6806784F);
	  }
	  
	  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	  {
	    super.render(entity, f, f1, f2, f3, f4, f5);
	    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	    bu1.render(f5);
	    bu2.render(f5);
	    bu3.render(f5);
	    Shape1.render(f5);
	    Shape2.render(f5);
	    Shape10.render(f5);
	    Shape3.render(f5);
	    Shape4.render(f5);
	    Shape11.render(f5);
	    Shape5.render(f5);
	    Shape6.render(f5);
	    Shape12.render(f5);
	    Shape7.render(f5);
	    Shape13.render(f5);
	    Shape8.render(f5);
	  }
	  
	  private void setRotation(ModelRenderer model, float x, float y, float z)
	  {
	    model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
	  }
	  
	  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e)
	  {
	    super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
	  }
}
