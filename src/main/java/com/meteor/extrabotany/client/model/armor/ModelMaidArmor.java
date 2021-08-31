package com.meteor.extrabotany.client.model.armor;

import com.meteor.extrabotany.client.model.ModelArmor;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;

public class ModelMaidArmor extends ModelArmor {

    private final ModelRenderer helmAnchor;
    private final ModelRenderer ear1;
    private final ModelRenderer ear2;
    private final ModelRenderer hat;

    private final ModelRenderer bodyAnchor;
    private final ModelRenderer body;
    private final ModelRenderer oupai;
    private final ModelRenderer left;
    private final ModelRenderer right;

    private final ModelRenderer armLAnchor;
    private final ModelRenderer armRAnchor;
    private final ModelRenderer pantsAnchor;
    private final ModelRenderer a1;
    private final ModelRenderer a2;
    private final ModelRenderer a3;
    private final ModelRenderer a4;
    private final ModelRenderer b1;
    private final ModelRenderer b2;

    private final ModelRenderer legL;
    private final ModelRenderer legR;

    private final ModelRenderer legLeft;
    private final ModelRenderer legRight;

    private final ModelRenderer bootL;
    private final ModelRenderer bootR;

    public ModelMaidArmor(EquipmentSlotType slot) {
        super(slot);

        this.textureWidth = 128;
        this.textureHeight = 128;
        float s = 0.01F;

        // helm
        this.helmAnchor = new ModelRenderer(this, 0, 0);
        this.helmAnchor.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.helmAnchor.addBox(-1.0F, -2.0F, 0.0F, 2, 2, 2, s);
        this.ear1 = new ModelRenderer(this, 1, 55);
        this.ear1.addBox(0F, 0F, 0F, 3, 3, 2, s);
        this.ear1.setRotationPoint(-5F, -8.5F, -3F);
        this.ear1.setTextureSize(64, 32);
        this.ear1.mirror = true;
        this.setRotateAngle(ear1, 0F, 0F, -0.9075712F);
        this.ear2 = new ModelRenderer(this, 12, 55);
        this.ear2.addBox(0F, 0F, 0F, 3, 3, 2, s);
        this.ear2.setRotationPoint(3F, -10.8F, -3F);
        this.ear2.setTextureSize(64, 32);
        this.ear2.mirror = true;
        this.setRotateAngle(ear2, 0F, 0F, 0.9075712F);
        this.hat = new ModelRenderer(this, 1, 61);
        this.hat.addBox(0F, 0F, 0F, 10, 4, 1);
        this.hat.setRotationPoint(-5F, -9F, -2F);
        this.hat.setTextureSize(64, 32);
        this.hat.mirror = true;
        this.setRotateAngle(hat, 0F, 0F, 0F);

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
        this.oupai = new ModelRenderer(this, 1, 67);
        this.oupai.addBox(0F, 0F, 0F, 6, 4, 3);
        this.oupai.setRotationPoint(-3F, 1F, -4F);
        this.oupai.setTextureSize(64, 32);
        this.oupai.mirror = true;
        this.setRotateAngle(oupai, 0.1745329F, 0F, 0F);
        this.left = new ModelRenderer(this, 1, 75);
        this.left.addBox(0F, 0F, 0F, 5, 4, 6, s);
        this.left.setRotationPoint(-1F, -2F, -3F);
        this.left.setTextureSize(64, 32);
        this.left.mirror = true;
        this.setRotateAngle(left, 0F, 0F, 0F);
        this.right = new ModelRenderer(this, 24, 75);
        this.right.addBox(0F, 0F, 0F, 5, 4, 6, s);
        this.right.setRotationPoint(-4F, -2F, -3F);
        this.right.setTextureSize(64, 32);
        this.right.mirror = true;
        this.setRotateAngle(right, 0F, 0F, 0F);

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

        this.b2 = new ModelRenderer(this, 1, 32);
        this.b2.addBox(0F, 0F, 0F, 6, 4, 1);
        this.b2.setRotationPoint(-3F, 9F, -2F);
        this.b2.setTextureSize(64, 32);
        this.b2.mirror = true;
        this.setRotateAngle(b2, -0.5235988F, 0F, 0F);
        this.b1 = new ModelRenderer(this, 16, 32);
        this.b1.addBox(0F, 0F, 0F, 6, 4, 1);
        this.b1.setRotationPoint(-3F, 9F, 1F);
        this.b1.setTextureSize(64, 32);
        this.b1.mirror = true;
        this.setRotateAngle(b1, 0.5235988F, 0F, 0F);
        this.a4 = new ModelRenderer(this, 1, 38);
        this.a4.addBox(0F, 0F, 0F, 1, 5, 4);
        this.a4.setRotationPoint(3F, 9F, -2F);
        this.a4.setTextureSize(64, 32);
        this.a4.mirror = true;
        this.setRotateAngle(a4, 0F, 0F, -0.3490659F);
        this.a3 = new ModelRenderer(this, 1, 48);
        this.a3.addBox(0F, 0F, 0F, 8, 5, 1);
        this.a3.setRotationPoint(-4F, 9F, -2F);
        this.a3.setTextureSize(64, 32);
        this.a3.mirror = true;
        this.setRotateAngle(a3, -0.3490659F, 0F, 0F);
        this.a2 = new ModelRenderer(this, 12, 38);
        this.a2.addBox(0F, 0F, 0F, 1, 5, 4);
        this.a2.setRotationPoint(-4F, 9F, -2F);
        this.a2.setTextureSize(64, 32);
        this.a2.mirror = true;
        this.setRotateAngle(a2, 0F, 0F, 0.3490659F);
        this.a1 = new ModelRenderer(this, 20, 48);
        this.a1.addBox(0F, 0F, 0F, 8, 5, 1);
        this.a1.setRotationPoint(-4F, 9F, 1F);
        this.a1.setTextureSize(64, 32);
        this.a1.mirror = true;
        this.setRotateAngle(a1, 0.3490659F, 0F, 0F);

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

        // boots
        this.bootL = new ModelRenderer(this, 0, 0);
        this.bootL.mirror = true;
        this.bootL.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.bootL.addBox(-2.39F, 8.5F, -2.49F, 2, 2, 2, s);
        this.bootR = new ModelRenderer(this, 0, 0);
        this.bootR.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.bootR.addBox(-2.5F, 8.5F, -2.51F, 2, 2, 2, s);

        // hierarchy
        this.helmAnchor.addChild(this.hat);
        this.helmAnchor.addChild(this.ear1);
        this.helmAnchor.addChild(this.ear2);

        this.bodyAnchor.addChild(this.body);
        this.body.addChild(this.oupai);

        this.armLAnchor.addChild(this.left);
        this.armRAnchor.addChild(this.right);

        this.pantsAnchor.addChild(this.a1);
        this.pantsAnchor.addChild(this.a2);
        this.pantsAnchor.addChild(this.a3);
        this.pantsAnchor.addChild(this.a4);
        this.pantsAnchor.addChild(this.b1);
        this.pantsAnchor.addChild(this.b2);

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
