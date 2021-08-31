package com.meteor.extrabotany.client.model;

import com.meteor.extrabotany.client.handler.ClientTickHandler;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelUfo extends EntityModel {
    private final ModelRenderer body;
    private final ModelRenderer cube_r1;
    private final ModelRenderer glow;

    public ModelUfo() {
        textureWidth = 256;
        textureHeight = 256;

        body = new ModelRenderer(this);
        body.setRotationPoint(6.0F, 24.0F, -6.0F);
        body.setTextureOffset(112, 0).addBox(-24.0F, 0.0F, -8.0F, 32.0F, 2.0F, 32.0F, 0.0F, false);
        body.setTextureOffset(80, 38).addBox(-19.0F, -7.0F, -3.0F, 4.0F, 5.0F, 22.0F, 0.0F, false);
        body.setTextureOffset(0, 86).addBox(-15.0F, -9.0F, 15.0F, 14.0F, 7.0F, 4.0F, 0.0F, false);
        body.setTextureOffset(80, 65).addBox(-15.0F, -7.0F, -3.0F, 14.0F, 5.0F, 4.0F, 0.0F, false);
        body.setTextureOffset(132, 38).addBox(-1.0F, -7.0F, -3.0F, 4.0F, 5.0F, 22.0F, 0.0F, false);
        body.setTextureOffset(0, 62).addBox(-12.0F, -5.0F, -5.0F, 8.0F, 3.0F, 2.0F, 0.0F, false);
        body.setTextureOffset(1, 82).addBox(-18.0F, -8.0F, 6.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
        body.setTextureOffset(1, 83).addBox(0.0F, -8.0F, 6.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
        body.setTextureOffset(184, 53).addBox(-13.0F, -4.0F, 3.0F, 10.0F, 2.0F, 10.0F, 0.0F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setRotationPoint(-7.0F, -8.0F, 7.0F);
        body.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, -0.7854F, 0.0F);
        cube_r1.setTextureOffset(0, 0).addBox(-19.0F, 6.0F, -17.0F, 36.0F, 2.0F, 36.0F, 0.0F, false);

        glow = new ModelRenderer(this);
        glow.setRotationPoint(6.0F, 24.0F, -6.0F);
        glow.setTextureOffset(0, 67).addBox(-2.0F, -6.0F, -4.0F, 6.0F, 4.0F, 6.0F, 0.0F, false);
        glow.setTextureOffset(0, 77).addBox(-1.0F, -13.0F, 5.0F, 4.0F, 5.0F, 4.0F, 0.0F, false);
        glow.setTextureOffset(16, 77).addBox(-19.0F, -13.0F, 5.0F, 4.0F, 5.0F, 4.0F, 0.0F, false);
        glow.setTextureOffset(24, 67).addBox(-20.0F, -6.0F, -4.0F, 6.0F, 4.0F, 6.0F, 0.0F, false);
        glow.setTextureOffset(0, 38).addBox(-18.0F, 2.0F, -2.0F, 20.0F, 4.0F, 20.0F, 0.0F, false);
        glow.setTextureOffset(36, 89).addBox(3.0F, 2.0F, 5.0F, 3.0F, 2.0F, 6.0F, 0.0F, false);
        glow.setTextureOffset(54, 89).addBox(-22.0F, 2.0F, 5.0F, 3.0F, 2.0F, 6.0F, 0.0F, false);
        glow.setTextureOffset(0, 0).addBox(-9.0F, -4.0F, -6.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        glow.setTextureOffset(6, 12).addBox(4.0F, -10.0F, 6.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
        glow.setTextureOffset(4, 9).addBox(3.0F, -10.0F, 6.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        glow.setTextureOffset(0, 14).addBox(-20.0F, -10.0F, 6.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        glow.setTextureOffset(4, 12).addBox(-21.0F, -10.0F, 6.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        body.render(matrixStack, buffer, packedLight, packedOverlay);
        int light = (int) (0xF00090 + 0x000060 * Math.sin(ClientTickHandler.ticksInGame*0.15F));
        glow.render(matrixStack, buffer, light, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
