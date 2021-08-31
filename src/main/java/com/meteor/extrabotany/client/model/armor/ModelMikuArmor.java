package com.meteor.extrabotany.client.model.armor;

import com.meteor.extrabotany.client.model.ModelArmor;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;

public class ModelMikuArmor extends ModelArmor {

    private final ModelRenderer helmAnchor;
    private final ModelRenderer bodyAnchor;
    private final ModelRenderer body;
    private final ModelRenderer armLAnchor;
    private final ModelRenderer armRAnchor;
    private final ModelRenderer pantsAnchor;
    private final ModelRenderer legL;
    private final ModelRenderer legR;
    private final ModelRenderer bootL;
    private final ModelRenderer bootR;

    private final ModelRenderer lefthair;
    private final ModelRenderer righthair;
    private final ModelRenderer circle2;
    private final ModelRenderer circle1;
    private final ModelRenderer hat;
    private final ModelRenderer oupai;
    private final ModelRenderer Shape1;
    private final ModelRenderer pifeng;
    private final ModelRenderer Shape2;
    private final ModelRenderer a1;
    private final ModelRenderer a2;
    private final ModelRenderer a3;
    private final ModelRenderer c2;
    private final ModelRenderer c1;
    private final ModelRenderer b1;
    private final ModelRenderer b2;
    private final ModelRenderer b3;

    private final ModelRenderer legLeft;
    private final ModelRenderer legRight;

    public ModelMikuArmor(EquipmentSlotType slot) {
        super(slot);

        this.textureWidth = 128;
        this.textureHeight = 128;
        float s = 0.01F;

        // helm
        this.helmAnchor = new ModelRenderer(this, 0, 0);
        this.helmAnchor.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.helmAnchor.addBox(-1.0F, -2.0F, 0.0F, 2, 2, 2, s);

        // body
        this.bodyAnchor = new ModelRenderer(this, 0, 0);
        this.bodyAnchor.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bodyAnchor.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, s);
        this.body = new ModelRenderer(this, 16, 16);
        this.body.addBox(-4F, 0F, -2F, 8, 12, 4);
        this.body.setRotationPoint(0F, 0F, 0F);
        this.body.setTextureSize(64, 32);
        this.body.mirror = true;
        this.setRotateAngle(body, 0F, 0F, 0F);

        // armL
        this.armLAnchor = new ModelRenderer(this, 0, 0);
        this.armLAnchor.mirror = true;
        this.armLAnchor.setRotationPoint(4.0F, 2.0F, 0.0F);
        this.armLAnchor.addBox(0.0F, -1.0F, -1.0F, 2, 2, 2, s);

        // armR
        this.armRAnchor = new ModelRenderer(this, 0, 0);
        this.armRAnchor.mirror = true;
        this.armRAnchor.setRotationPoint(-4.0F, 2.0F, 0.0F);
        this.armRAnchor.addBox(-2.0F, -1.0F, -1.0F, 2, 2, 2, s);

        // pants
        this.pantsAnchor = new ModelRenderer(this, 0, 0);
        this.pantsAnchor.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.pantsAnchor.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, s);
        this.legR = new ModelRenderer(this, 0, 0);
        this.legR.addBox(-2F, 0F, -2F, 0, 0, 0, s);
        this.legR.setRotationPoint(-2F, 12F, 0F);
        this.legR.setTextureSize(64, 32);
        this.legR.mirror = true;
        this.setRotateAngle(legR, 0F, 0F, 0F);
        this.legL = new ModelRenderer(this, 0, 0);
        this.legL.addBox(-2F, 0F, -2F, 0, 0, 0, s);
        this.legL.setRotationPoint(2F, 12F, 0F);
        this.legL.setTextureSize(64, 32);
        this.legL.mirror = true;
        this.setRotateAngle(legL, 0F, 0F, 0F);

        // boots
        this.bootL = new ModelRenderer(this, 0, 0);
        this.bootL.mirror = true;
        this.bootL.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.bootL.addBox(-2.39F, 8.5F, -2.49F, 2, 2, 2, s);
        this.bootR = new ModelRenderer(this, 0, 0);
        this.bootR.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.bootR.addBox(-2.5F, 8.5F, -2.51F, 2, 2, 2, s);

        this.lefthair = new ModelRenderer(this, 0, 33);
        this.lefthair.addBox(0F, 0F, 0F, 2, 12, 2, s);
        this.lefthair.setRotationPoint(2.5F, -9F, -1F);
        this.lefthair.setTextureSize(128, 128);
        this.lefthair.mirror = true;
        this.setRotateAngle(lefthair, 0.2617994F, 0F, -0.4014257F);
        this.righthair = new ModelRenderer(this, 9, 33);
        this.righthair.addBox(0F, 0F, 0F, 2, 12, 2, s);
        this.righthair.setRotationPoint(-4.5F, -9.8F, 1F);
        this.righthair.setTextureSize(128, 128);
        this.righthair.mirror = true;
        this.setRotateAngle(righthair, 0.2617994F, 0, 0.4014257F);
        this.circle2 = new ModelRenderer(this, 18, 33);
        this.circle2.addBox(0F, 0F, 0F, 1, 3, 3, s);
        this.circle2.setRotationPoint(4.5F, -10F, -1F);
        this.circle2.setTextureSize(128, 128);
        this.circle2.mirror = true;
        this.setRotateAngle(circle2, 0F, 0F, -0.1047198F);
        this.circle1 = new ModelRenderer(this, 27, 33);
        this.circle1.addBox(0F, 0F, 0F, 1, 3, 3, s);
        this.circle1.setRotationPoint(-5.5F, -10F, -1F);
        this.circle1.setTextureSize(128, 128);
        this.circle1.mirror = true;
        this.setRotateAngle(circle1, 0F, 0F, 0.1047198F);
        this.hat = new ModelRenderer(this, 36, 33);
        this.hat.addBox(0F, 0F, 0F, 10, 3, 1, s);
        this.hat.setRotationPoint(-5F, -9F, -1.3F);
        this.hat.setTextureSize(128, 128);
        this.hat.mirror = true;
        this.setRotateAngle(hat, 0F, 0F, 0F);
        this.oupai = new ModelRenderer(this, 0, 48);
        this.oupai.addBox(0F, 0F, 0F, 6, 3, 3, s);
        this.oupai.setRotationPoint(-3F, 2F, -4F);
        this.oupai.setTextureSize(128, 128);
        this.oupai.mirror = true;
        this.setRotateAngle(oupai, 0.6108652F, 0F, 0F);
        this.Shape1 = new ModelRenderer(this, 19, 48);
        this.Shape1.addBox(0F, 0F, 0F, 5, 2, 6, s);
        this.Shape1.setRotationPoint(-1F, -3F, -3F);
        this.Shape1.setTextureSize(128, 128);
        this.Shape1.mirror = true;
        this.setRotateAngle(Shape1, 0F, 0F, 0.1745329F);
        this.Shape2 = new ModelRenderer(this, 42, 48);
        this.Shape2.addBox(0F, 0F, 0F, 5, 2, 6, s);
        this.Shape2.setRotationPoint(-4F, -2F, -3F);
        this.Shape2.setTextureSize(128, 128);
        this.Shape2.mirror = true;
        this.setRotateAngle(Shape2, 0F, 0F, -0.1745329F);
        this.pifeng = new ModelRenderer(this, 65, 48);
        this.pifeng.addBox(0F, 0F, 0F, 8, 12, 1, s);
        this.pifeng.setRotationPoint(-4F, 0F, 2F);
        this.pifeng.setTextureSize(128, 128);
        this.pifeng.mirror = true;
        this.setRotateAngle(pifeng, 0.1396263F, 0F, 0F);
        this.a1 = new ModelRenderer(this, 0, 65);
        this.a1.addBox(0F, 0F, 0F, 6, 7, 1, s);
        this.a1.setRotationPoint(-3F, 9F, -2F);
        this.a1.setTextureSize(128, 128);
        this.a1.mirror = true;
        this.setRotateAngle(a1, -0.2617994F, 0F, 0F);
        this.a2 = new ModelRenderer(this, 15, 65);
        this.a2.addBox(0F, 0F, 0F, 3, 7, 1, s);
        this.a2.setRotationPoint(2.7F, 9F, -2F);
        this.a2.setTextureSize(128, 128);
        this.a2.mirror = true;
        this.setRotateAngle(a2, -0.2617994F, -0.5235988F, 0F);
        this.a3 = new ModelRenderer(this, 24, 65);
        this.a3.addBox(0F, 0F, 0F, 3, 7, 1, s);
        this.a3.setRotationPoint(-2.2F, 9F, -1F);
        this.a3.setTextureSize(128, 128);
        this.a3.mirror = true;
        this.setRotateAngle(a3, 0.2617994F, 3.665191F, 0F);
        this.c2 = new ModelRenderer(this, 33, 65);
        this.c2.addBox(0F, 0F, 0F, 1, 6, 3, s);
        this.c2.setRotationPoint(-4F, 9F, -2F);
        this.c2.setTextureSize(128, 128);
        this.c2.mirror = true;
        this.setRotateAngle(c2, 0F, 0F, 0.3490659F);
        this.c1 = new ModelRenderer(this, 42, 65);
        this.c1.addBox(0F, 0F, 0F, 1, 6, 3, s);
        this.c1.setRotationPoint(3F, 9F, -2F);
        this.c1.setTextureSize(128, 128);
        this.c1.mirror = true;
        this.setRotateAngle(c1, 0F, 0F, -0.3490659F);
        this.b1 = new ModelRenderer(this, 51, 65);
        this.b1.addBox(0F, 0F, 0F, 6, 7, 1, s);
        this.b1.setRotationPoint(-3F, 9F, 1F);
        this.b1.setTextureSize(128, 128);
        this.b1.mirror = true;
        this.setRotateAngle(b1, 0.2617994F, 0F, 0F);
        this.b2 = new ModelRenderer(this, 66, 65);
        this.b2.addBox(0F, 0F, 0F, 3, 7, 1, s);
        this.b2.setRotationPoint(5.5F, 9F, 0.5F);
        this.b2.setTextureSize(128, 128);
        this.b2.mirror = true;
        this.setRotateAngle(b2, -0.2617994F, 3.665191F, 0F);
        this.b3 = new ModelRenderer(this, 75, 65);
        this.b3.addBox(0F, 0F, 0F, 3, 7, 1, s);
        this.b3.setRotationPoint(-4.5F, 9F, -0.5F);
        this.b3.setTextureSize(128, 128);
        this.b3.mirror = true;
        this.setRotateAngle(b3, 0.2617994F, -0.5235988F, 0F);

        this.legRight = new ModelRenderer(this, 0, 16);
        this.legRight.addBox(-2F, 0F, -2F, 4, 12, 4, s);
        this.legRight.setRotationPoint(0F, 0F, 0F);
        this.legRight.setTextureSize(64, 32);
        this.legRight.mirror = true;
        this.setRotateAngle(legRight, 0F, 0F, 0F);
        this.legLeft = new ModelRenderer(this, 0, 16);
        this.legLeft.addBox(-2F, 0F, -2F, 4, 12, 4, s);
        this.legLeft.setRotationPoint(0F, 0F, 0F);
        this.legLeft.setTextureSize(64, 32);
        this.legLeft.mirror = true;
        this.setRotateAngle(legLeft, 0F, 0F, 0F);

        // hierarchy
        this.helmAnchor.addChild(this.hat);
        this.helmAnchor.addChild(this.lefthair);
        this.helmAnchor.addChild(this.righthair);
        this.helmAnchor.addChild(this.circle1);
        this.helmAnchor.addChild(this.circle2);

        this.bodyAnchor.addChild(this.body);
        this.body.addChild(this.oupai);
        this.body.addChild(this.pifeng);

        this.pantsAnchor.addChild(this.a1);
        this.pantsAnchor.addChild(this.a2);
        this.pantsAnchor.addChild(this.a3);
        this.pantsAnchor.addChild(this.b3);
        this.pantsAnchor.addChild(this.b1);
        this.pantsAnchor.addChild(this.b2);
        this.pantsAnchor.addChild(this.c1);
        this.pantsAnchor.addChild(this.c2);

        this.bootL.addChild(this.legLeft);
        this.bootR.addChild(this.legRight);
    }

    @Override
    public void render(MatrixStack ms, IVertexBuilder buffer, int light, int overlay, float r, float g, float b, float a) {

        helmAnchor.showModel = slot == EquipmentSlotType.HEAD;
        bodyAnchor.showModel = slot == EquipmentSlotType.CHEST;
        armRAnchor.showModel = slot == EquipmentSlotType.CHEST;
        armLAnchor.showModel = slot == EquipmentSlotType.CHEST;
        legR.showModel = slot == EquipmentSlotType.LEGS;
        legL.showModel = slot == EquipmentSlotType.LEGS;
        bootL.showModel = slot == EquipmentSlotType.FEET;
        bootR.showModel = slot == EquipmentSlotType.FEET;
        bipedHeadwear.showModel = false;

        bipedHead = helmAnchor;
        bipedBody = bodyAnchor;
        bipedRightArm = armRAnchor;
        bipedLeftArm = armLAnchor;
        if (slot == EquipmentSlotType.LEGS) {
            bipedBody = pantsAnchor;
            bipedRightLeg = legR;
            bipedLeftLeg = legL;
        } else {
            bipedRightLeg = bootR;
            bipedLeftLeg = bootL;
        }

        super.render(ms, buffer, light, overlay, r, g, b, a);
    }
}
