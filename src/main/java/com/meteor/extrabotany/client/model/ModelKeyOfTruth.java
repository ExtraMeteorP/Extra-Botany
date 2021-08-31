package com.meteor.extrabotany.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelKeyOfTruth extends EntityModel{

    private final ModelRenderer bone;
    private final ModelRenderer bone2;
    private final ModelRenderer bone3;
    private final ModelRenderer bone4;
    private final ModelRenderer bone5;

    public ModelKeyOfTruth() {
        textureWidth = 64;
        textureHeight = 64;

        bone = new ModelRenderer(this);
        bone.setRotationPoint(0.0F, 19.0F, -1.0F);
        bone.setTextureOffset(0, 0).addBox(-4.0F, -1.0F, 1.0F, 4.0F, 6.0F, 2.0F, 0.0F, false);
        bone.setTextureOffset(28, 27).addBox(-5.0F, 3.0F, 1.0F, 6.0F, 1.0F, 3.0F, 0.0F, false);
        bone.setTextureOffset(16, 13).addBox(-4.0F, 3.0F, 4.0F, 4.0F, 1.0F, 9.0F, 0.0F, false);
        bone.setTextureOffset(28, 23).addBox(-5.0F, -2.0F, 1.0F, 6.0F, 1.0F, 3.0F, 0.0F, false);
        bone.setTextureOffset(0, 0).addBox(-4.0F, -2.0F, 4.0F, 4.0F, 1.0F, 12.0F, 0.0F, false);
        bone.setTextureOffset(33, 9).addBox(-3.5F, -0.5F, 3.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);
        bone.setTextureOffset(20, 0).addBox(-3.53F, -1.14F, 2.96F, 3.0F, 4.0F, 1.0F, 0.0F, false);
        bone.setTextureOffset(0, 10).addBox(-4.0F, -1.0F, 8.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        bone.setTextureOffset(0, 24).addBox(-1.0F, -1.0F, 8.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
        bone.setTextureOffset(0, 8).addBox(-4.0F, 2.0F, 8.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        bone.setTextureOffset(21, 13).addBox(-4.0F, -1.0F, 8.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
        bone.setTextureOffset(0, 13).addBox(-3.5F, -2.5F, 1.0F, 3.0F, 1.0F, 10.0F, 0.0F, false);
        bone.setTextureOffset(10, 24).addBox(-4.0F, 0.0F, -6.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);
        bone.setTextureOffset(0, 24).addBox(0.0F, 0.0F, -7.3F, 1.0F, 2.0F, 8.0F, 0.0F, false);
        bone.setTextureOffset(18, 23).addBox(-5.0F, 0.0F, -7.3F, 1.0F, 2.0F, 8.0F, 0.0F, false);

        bone2 = new ModelRenderer(this);
        bone2.setRotationPoint(0.0F, 1.0F, 1.0F);
        bone.addChild(bone2);
        setRotationAngle(bone2, 0.4363F, 0.0F, 0.0F);
        bone2.setTextureOffset(32, 32).addBox(-4.04F, -2.6285F, -3.0258F, 4.0F, 6.0F, 4.0F, 0.0F, false);
        bone2.setTextureOffset(15, 33).addBox(-3.5F, -5.7332F, -2.0606F, 3.0F, 4.0F, 3.0F, 0.0F, false);

        bone3 = new ModelRenderer(this);
        bone3.setRotationPoint(2.0F, 5.0F, -3.3F);
        bone.addChild(bone3);
        setRotationAngle(bone3, -0.6109F, 0.0F, 0.0F);
        bone3.setTextureOffset(20, 0).addBox(-6.0F, -2.4752F, -5.1498F, 4.0F, 1.0F, 8.0F, 0.0F, false);

        bone4 = new ModelRenderer(this);
        bone4.setRotationPoint(0.0F, 6.5F, -4.0F);
        bone.addChild(bone4);
        setRotationAngle(bone4, -0.7854F, 0.0F, 0.0F);
        bone4.setTextureOffset(16, 16).addBox(0.1F, -9.1213F, -1.4645F, 1.0F, 3.0F, 3.0F, 0.0F, false);

        bone5 = new ModelRenderer(this);
        bone5.setRotationPoint(-5.0F, 6.5F, -4.0F);
        bone.addChild(bone5);
        setRotationAngle(bone5, -0.7854F, 0.0F, 0.0F);
        bone5.setTextureOffset(0, 13).addBox(-0.1F, -9.1213F, -1.4645F, 1.0F, 3.0F, 3.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        bone.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
