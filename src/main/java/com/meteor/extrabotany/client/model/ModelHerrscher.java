package com.meteor.extrabotany.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;

public class ModelHerrscher extends ModelArmor {

    private final ModelRenderer rightArm;
    private final ModelRenderer bone2;
    private final ModelRenderer katanaSheath;
    private final ModelRenderer bone4;
    private final ModelRenderer bone3;
    private final ModelRenderer leftArm;
    private final ModelRenderer bone5;
    private final ModelRenderer bone6;
    private final ModelRenderer katana;
    private final ModelRenderer bone7;
    private final ModelRenderer back;

    public ModelHerrscher() {
        super(EquipmentSlotType.CHEST);
        textureWidth = 64;
        textureHeight = 64;

        rightArm = new ModelRenderer(this);
        rightArm.setRotationPoint(5.75F, 1.0F, 2.75F);
        setRotationAngle(rightArm, -0.4363F, 0.0F, -0.3491F);
        rightArm.setTextureOffset(0, 13).addBox(0.6859F, 1.3201F, -2.6295F, 3.0F, 9.0F, 5.0F, 0.0F, false);
        rightArm.setTextureOffset(11, 0).addBox(0.6859F, 2.3201F, -3.6295F, 2.0F, 7.0F, 1.0F, 0.0F, false);
        rightArm.setTextureOffset(0, 0).addBox(0.6859F, 2.3201F, 2.3705F, 2.0F, 7.0F, 1.0F, 0.0F, false);
        rightArm.setTextureOffset(0, 0).addBox(1.1859F, 3.3201F, -4.6295F, 1.0F, 4.0F, 9.0F, 0.0F, false);
        rightArm.setTextureOffset(12, 5).addBox(2.1859F, 3.3201F, -4.1295F, 1.0F, 2.0F, 8.0F, 0.0F, false);
        rightArm.setTextureOffset(17, 0).addBox(3.1859F, 1.3201F, -1.6295F, 1.0F, 2.0F, 3.0F, 0.0F, false);
        rightArm.setTextureOffset(22, 0).addBox(3.1859F, 3.3201F, -0.6295F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightArm.setTextureOffset(16, 16).addBox(0.1859F, 10.3201F, -1.1295F, 3.0F, 3.0F, 3.0F, 0.0F, false);
        rightArm.setTextureOffset(11, 15).addBox(0.1859F, 10.3201F, -2.1295F, 3.0F, 2.0F, 1.0F, 0.0F, false);
        rightArm.setTextureOffset(16, 7).addBox(1.1859F, 4.3201F, 4.3705F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        rightArm.setTextureOffset(5, 7).addBox(1.1859F, 4.3201F, -5.6295F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        bone2 = new ModelRenderer(this);
        bone2.setRotationPoint(3.6859F, 1.3201F, -0.1295F);
        rightArm.addChild(bone2);
        setRotationAngle(bone2, 0.0F, 0.0F, 0.7854F);
        bone2.setTextureOffset(0, 13).addBox(-0.2835F, -2.991F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F, false);

        katanaSheath = new ModelRenderer(this);
        katanaSheath.setRotationPoint(1.2474F, 13.2096F, 0.6816F);
        rightArm.addChild(katanaSheath);
        setRotationAngle(katanaSheath, -0.0873F, 0.0F, -0.0873F);
        katanaSheath.setTextureOffset(31, 49).addBox(-0.1555F, -2.0137F, -1.1343F, 1.0F, 2.0F, 13.0F, 0.0F, false);

        bone4 = new ModelRenderer(this);
        bone4.setRotationPoint(0.0F, 0.0F, 13.0F);
        katanaSheath.addChild(bone4);
        setRotationAngle(bone4, 0.1745F, 0.0F, 0.0F);
        bone4.setTextureOffset(46, 52).addBox(-0.1555F, -2.2105F, -1.1147F, 1.0F, 2.0F, 8.0F, 0.0F, false);

        bone3 = new ModelRenderer(this);
        bone3.setRotationPoint(2.1859F, 0.5701F, 0.3705F);
        rightArm.addChild(bone3);
        setRotationAngle(bone3, 0.0F, 0.0F, -0.2618F);
        bone3.setTextureOffset(16, 22).addBox(-2.0F, -2.0F, -2.0F, 2.0F, 3.0F, 3.0F, 0.0F, false);
        bone3.setTextureOffset(22, 2).addBox(0.0F, -1.0F, -2.0F, 1.0F, 2.0F, 3.0F, 0.0F, false);

        leftArm = new ModelRenderer(this);
        leftArm.setRotationPoint(-8.0F, 3.0F, 2.5F);
        setRotationAngle(leftArm, -0.4363F, 0.0F, 0.3491F);
        leftArm.setTextureOffset(30, 0).addBox(-1.0F, -1.0F, -3.0F, 3.0F, 10.0F, 4.0F, 0.0F, false);
        leftArm.setTextureOffset(44, 0).addBox(-2.0F, 1.0F, -2.0F, 1.0F, 4.0F, 2.0F, 0.0F, false);
        leftArm.setTextureOffset(30, 20).addBox(-1.0F, -3.0F, -3.0F, 3.0F, 2.0F, 1.0F, 0.0F, false);
        leftArm.setTextureOffset(30, 23).addBox(-1.0F, -3.0F, 0.0F, 3.0F, 2.0F, 1.0F, 0.0F, false);
        leftArm.setTextureOffset(44, 6).addBox(-1.0F, -4.0F, -2.0F, 3.0F, 1.0F, 2.0F, 0.0F, false);
        leftArm.setTextureOffset(54, 0).addBox(-0.6381F, -0.7174F, -4.039F, 2.0F, 5.0F, 1.0F, 0.0F, false);
        leftArm.setTextureOffset(54, 6).addBox(-0.4238F, -0.7607F, 0.9059F, 2.0F, 5.0F, 1.0F, 0.0F, false);

        bone5 = new ModelRenderer(this);
        bone5.setRotationPoint(0.0F, 0.0F, -1.0F);
        leftArm.addChild(bone5);
        setRotationAngle(bone5, 0.0F, 0.0F, 0.5236F);
        bone5.setTextureOffset(30, 14).addBox(-3.1094F, -0.3639F, -1.0F, 3.0F, 2.0F, 2.0F, 0.0F, false);

        bone6 = new ModelRenderer(this);
        bone6.setRotationPoint(-3.9575F, 1.2207F, 0.0F);
        bone5.addChild(bone6);
        setRotationAngle(bone6, 0.0F, 0.0F, 0.3491F);
        bone6.setTextureOffset(30, 18).addBox(-2.7531F, -1.3651F, -0.5F, 4.0F, 1.0F, 1.0F, 0.0F, false);

        katana = new ModelRenderer(this);
        katana.setRotationPoint(1.0F, 8.0F, -4.0F);
        leftArm.addChild(katana);
        setRotationAngle(katana, -0.0873F, 3.0543F, 0.0F);
        katana.setTextureOffset(31, 13).addBox(-0.1555F, -2.0137F, -1.1343F, 1.0F, 2.0F, 13.0F, 0.0F, false);
        katana.setTextureOffset(46, 14).addBox(-0.1596F, -1.9492F, -7.5283F, 1.0F, 2.0F, 3.0F, 0.0F, false);
        katana.setTextureOffset(46, 9).addBox(-1.6025F, -3.4558F, -0.4588F, 4.0F, 5.0F, 0.0F, 0.0F, false);

        bone7 = new ModelRenderer(this);
        bone7.setRotationPoint(0.0F, 0.0F, 13.0F);
        katana.addChild(bone7);
        setRotationAngle(bone7, 0.1745F, 0.0F, 0.0F);
        bone7.setTextureOffset(46, 16).addBox(-0.1555F, -2.2105F, -1.1147F, 1.0F, 2.0F, 8.0F, 0.0F, false);

        back = new ModelRenderer(this);
        back.setRotationPoint(0.0F, 24.0F, 0.0F);
        back.setTextureOffset(0, 28).addBox(-16.0F, -34.0F, 10.0F, 32.0F, 21.0F, 0.0F, 0.0F, false);
    }

    public void renderLeftArm(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.rightArm.render(matrixStack, buffer, 0xF000F0, packedOverlay);
    }

    public void renderRightArm(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.leftArm.render(matrixStack, buffer, 0xF000F0, packedOverlay);
    }

    public void renderStigma(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.back.render(matrixStack, buffer, 0xF000F0, packedOverlay);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){

    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

}
