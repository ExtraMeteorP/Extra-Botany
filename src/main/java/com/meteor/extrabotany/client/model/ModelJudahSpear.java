package com.meteor.extrabotany.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelJudahSpear extends ModelBase {
    ModelRenderer main;
    ModelRenderer arrowa1;
    ModelRenderer arrowa2;
    ModelRenderer arrowa3;
    ModelRenderer arrowa4;
    ModelRenderer arrowb1;
    ModelRenderer arrowb2;
    ModelRenderer arrowb3;
    ModelRenderer arrowb4;

    public ModelJudahSpear() {
        textureWidth = 64;
        textureHeight = 64;

        main = new ModelRenderer(this, 4, 0);
        main.addBox(0F, 0F, 0F, 1, 24, 1);
        main.setRotationPoint(0F, -4F, 0F);
        main.setTextureSize(64, 32);
        main.mirror = true;
        setRotation(main, 0F, 0F, 0F);
        arrowa1 = new ModelRenderer(this, 0, 0);
        arrowa1.addBox(0F, 0F, 0F, 1, 3, 1);
        arrowa1.setRotationPoint(0F, 18.3F, -1F);
        arrowa1.setTextureSize(64, 32);
        arrowa1.mirror = true;
        setRotation(arrowa1, 0.3490659F, 0F, 0F);
        arrowa2 = new ModelRenderer(this, 0, 0);
        arrowa2.addBox(0F, 0F, 0F, 1, 3, 1);
        arrowa2.setRotationPoint(0F, 18F, 1F);
        arrowa2.setTextureSize(64, 32);
        arrowa2.mirror = true;
        setRotation(arrowa2, -0.3490659F, 0F, 0F);
        arrowa3 = new ModelRenderer(this, 0, 0);
        arrowa3.addBox(0F, 0F, 0F, 1, 3, 1);
        arrowa3.setRotationPoint(1F, 18F, 0F);
        arrowa3.setTextureSize(64, 32);
        arrowa3.mirror = true;
        setRotation(arrowa3, 0F, 0F, 0.3490659F);
        arrowa4 = new ModelRenderer(this, 0, 0);
        arrowa4.addBox(0F, 0F, 0F, 1, 3, 1);
        arrowa4.setRotationPoint(-1F, 18.4F, 0F);
        arrowa4.setTextureSize(64, 32);
        arrowa4.mirror = true;
        setRotation(arrowa4, 0F, 0F, -0.3490659F);
        arrowb1 = new ModelRenderer(this, 0, 0);
        arrowb1.addBox(0F, 0F, 0F, 1, 3, 1);
        arrowb1.setRotationPoint(-1F, 18.3F, -0.3F);
        arrowb1.setTextureSize(64, 32);
        arrowb1.mirror = true;
        setRotation(arrowb1, 0.4363323F, 0.7853982F, 0F);
        arrowb2 = new ModelRenderer(this, 0, 0);
        arrowb2.addBox(0F, 0F, 0F, 1, 3, 1);
        arrowb2.setRotationPoint(1.3F, 18.3F, -1F);
        arrowb2.setTextureSize(64, 32);
        arrowb2.mirror = true;
        setRotation(arrowb2, 0.4363323F, -0.7853982F, 0F);
        arrowb3 = new ModelRenderer(this, 0, 0);
        arrowb3.addBox(0F, 0F, 0F, 1, 3, 1);
        arrowb3.setRotationPoint(-1F, 18.3F, 1.3F);
        arrowb3.setTextureSize(64, 32);
        arrowb3.mirror = true;
        setRotation(arrowb3, 0F, 0.7853982F, -0.4363323F);
        arrowb4 = new ModelRenderer(this, 0, 0);
        arrowb4.addBox(0F, 0F, 0F, 1, 3, 1);
        arrowb4.setRotationPoint(1.3F, 17.9F, 0.7F);
        arrowb4.setTextureSize(64, 32);
        arrowb4.mirror = true;
        setRotation(arrowb4, 0F, -0.7853982F, 0.4363323F);
    }

    public void render() {
        float f5 = 1 / 16F;
        main.render(f5);
        arrowa1.render(f5);
        arrowa2.render(f5);
        arrowa3.render(f5);
        arrowa4.render(f5);
        arrowb1.render(f5);
        arrowb2.render(f5);
        arrowb3.render(f5);
        arrowb4.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

}
