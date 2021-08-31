package com.meteor.extrabotany.client.model.armor;

import com.meteor.extrabotany.client.model.ModelArmor;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;

public class ModelGoblinSlayerArmor extends ModelArmor {

    private final ModelRenderer plates;
    private final ModelRenderer bang2;
    private final ModelRenderer bang1;
    private final ModelRenderer ear;
    private final ModelRenderer hair;
    private final ModelRenderer hair2;
    private final ModelRenderer bone;
    private final ModelRenderer bone3;
    private final ModelRenderer bone7;
    private final ModelRenderer bone2;
    private final ModelRenderer bone8;
    private final ModelRenderer bone5;
    private final ModelRenderer handL;
    private final ModelRenderer bone4;
    private final ModelRenderer handR;

    private final ModelRenderer head;
    private final ModelRenderer body;
    private final ModelRenderer leftArm;
    private final ModelRenderer rightArm;
    private final ModelRenderer rightLeg;
    private final ModelRenderer leftLeg;
    private final ModelRenderer bootL;
    private final ModelRenderer bootR;

    public ModelGoblinSlayerArmor(EquipmentSlotType slot) {
        super(slot);
        textureWidth = 128;
        textureHeight = 128;
        float s = 0.01F;

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.setTextureOffset(0, 18).addBox(-2.0F, -1.0F, -5.0F, 4.0F, 2.0F, 2.0F, 0.0F, false);
        head.setTextureOffset(4, 23).addBox(-1.5F, -6.0F, -5.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        head.setTextureOffset(8, 23).addBox(0.5F, -6.0F, -5.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        head.setTextureOffset(0, 0).addBox(-4.5F, -8.5F, -4.5F, 9.0F, 9.0F, 9.0F, 0.0F, false);

        plates = new ModelRenderer(this);
        plates.setRotationPoint(0.0F, -8.5F, -2.0F);
        head.addChild(plates);
        setRotationAngle(plates, 0.0F, 0.7854F, 0.0F);
        plates.setTextureOffset(0, 49).addBox(-3.0F, -0.5F, -3.0F, 6.0F, 1.0F, 6.0F, 0.0F, false);
        plates.setTextureOffset(0, 56).addBox(-3.0F, -1.5F, -2.0F, 5.0F, 1.0F, 5.0F, 0.0F, false);
        plates.setTextureOffset(0, 65).addBox(-1.0F, -2.5F, 1.0F, 2.0F, 1.0F, 3.0F, 0.0F, false);
        plates.setTextureOffset(0, 74).addBox(-3.0F, -0.5F, 3.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        plates.setTextureOffset(0, 69).addBox(-4.0F, -0.5F, -1.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
        plates.setTextureOffset(0, 62).addBox(-4.0F, -2.5F, -1.0F, 5.0F, 1.0F, 2.0F, 0.0F, false);
        plates.setTextureOffset(0, 35).addBox(-2.0F, 1.5F, -4.0F, 6.0F, 1.0F, 6.0F, 0.0F, false);
        plates.setTextureOffset(0, 29).addBox(-1.5F, 2.5F, -3.5F, 5.0F, 1.0F, 5.0F, 0.0F, false);
        plates.setTextureOffset(0, 42).addBox(-2.5F, 0.5F, -3.5F, 6.0F, 1.0F, 6.0F, 0.0F, false);

        bang2 = new ModelRenderer(this);
        bang2.setRotationPoint(-2.0F, 0.0F, -5.0F);
        head.addChild(bang2);
        setRotationAngle(bang2, 0.0F, 0.0F, -0.1745F);
        bang2.setTextureOffset(0, 22).addBox(-0.75F, -6.25F, 0.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);

        bang1 = new ModelRenderer(this);
        bang1.setRotationPoint(2.0F, 0.0F, -4.0F);
        head.addChild(bang1);
        setRotationAngle(bang1, 0.0F, 0.0F, 0.1745F);
        bang1.setTextureOffset(12, 22).addBox(-0.25F, -6.25F, -1.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);

        ear = new ModelRenderer(this);
        ear.setRotationPoint(5.5F, -4.0F, 0.0F);
        head.addChild(ear);
        setRotationAngle(ear, 0.7854F, 0.0F, 0.0F);
        ear.setTextureOffset(16, 18).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 3.0F, 3.0F, 0.0F, false);
        ear.setTextureOffset(16, 24).addBox(-11.0F, -1.0F, -2.0F, 1.0F, 3.0F, 3.0F, 0.0F, false);

        hair = new ModelRenderer(this);
        hair.setRotationPoint(0.0F, -9.5F, 2.0F);
        head.addChild(hair);
        setRotationAngle(hair, 0.1745F, -0.3491F, 0.0F);
        hair.setTextureOffset(35, 0).addBox(-1.5F, -0.5F, 0.25F, 3.0F, 0.0F, 4.0F, 0.0F, false);

        hair2 = new ModelRenderer(this);
        hair2.setRotationPoint(-0.25F, -9.5F, 2.25F);
        head.addChild(hair2);
        setRotationAngle(hair2, -0.3491F, 0.2618F, 0.0F);
        hair2.setTextureOffset(34, 4).addBox(-1.25F, -0.5F, 0.0F, 3.0F, 0.0F, 5.0F, 0.0F, false);

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.setTextureOffset(100, 0).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 13.0F, 5.0F, 0.0F, false);
        body.setTextureOffset(50, 8).addBox(-5.0F, 6.0F, -3.0F, 10.0F, 1.0F, 6.0F, 0.0F, false);
        body.setTextureOffset(68, 0).addBox(-5.0F, 11.0F, -3.0F, 10.0F, 2.0F, 6.0F, 0.0F, false);
        body.setTextureOffset(110, 18).addBox(-4.0F, 0.0F, 2.25F, 8.0F, 6.0F, 1.0F, 0.0F, false);

        bone = new ModelRenderer(this);
        bone.setRotationPoint(0.0F, 7.0F, -2.5F);
        body.addChild(bone);
        setRotationAngle(bone, 0.0F, 0.0F, -0.7854F);
        bone.setTextureOffset(50, 0).addBox(5.5F, -4.0F, -1.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
        bone.setTextureOffset(82, 8).addBox(-1.5F, -2.5F, -1.0F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        bone.setTextureOffset(54, 0).addBox(-0.5F, -5.5F, -2.0F, 6.0F, 6.0F, 1.0F, 0.0F, false);
        bone.setTextureOffset(96, 18).addBox(-3.5F, -2.5F, 5.0F, 6.0F, 6.0F, 1.0F, 0.0F, false);
        bone.setTextureOffset(46, 9).addBox(0.0F, -6.5F, -1.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        bone.setTextureOffset(50, 15).addBox(0.5F, -4.5F, -3.0F, 4.0F, 2.0F, 1.0F, 0.0F, false);
        bone.setTextureOffset(60, 15).addBox(2.5F, -2.5F, -3.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        bone.setTextureOffset(92, 8).addBox(-5.5F, 2.5F, -2.5F, 3.0F, 3.0F, 1.0F, 0.0F, false);

        bone3 = new ModelRenderer(this);
        bone3.setRotationPoint(0.0F, 12.0F, 0.0F);
        body.addChild(bone3);
        setRotationAngle(bone3, 0.0F, 0.0F, 0.2618F);
        bone3.setTextureOffset(66, 15).addBox(-5.0F, -1.0F, -4.0F, 5.0F, 2.0F, 1.0F, 0.0F, false);
        bone3.setTextureOffset(66, 15).addBox(-5.0F, -1.0F, 3.0F, 5.0F, 2.0F, 1.0F, 0.0F, false);
        bone3.setTextureOffset(66, 21).addBox(-6.0F, -1.0F, -3.0F, 1.0F, 2.0F, 6.0F, 0.0F, false);

        bone7 = new ModelRenderer(this);
        bone7.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone3.addChild(bone7);
        setRotationAngle(bone7, 0.0F, 0.0F, -0.5236F);
        bone7.setTextureOffset(66, 18).addBox(-5.0F, -0.25F, -4.0F, 5.0F, 2.0F, 1.0F, 0.0F, false);
        bone7.setTextureOffset(66, 18).addBox(-5.0F, -0.25F, 3.0F, 5.0F, 2.0F, 1.0F, 0.0F, false);
        bone7.setTextureOffset(80, 21).addBox(-6.0F, -0.25F, -3.0F, 1.0F, 2.0F, 6.0F, 0.0F, false);

        bone2 = new ModelRenderer(this);
        bone2.setRotationPoint(0.0F, 12.0F, 0.0F);
        body.addChild(bone2);
        setRotationAngle(bone2, 0.0F, 0.0F, -0.2618F);
        bone2.setTextureOffset(78, 15).addBox(0.0F, -1.0F, -4.0F, 5.0F, 2.0F, 1.0F, 0.0F, false);
        bone2.setTextureOffset(78, 15).addBox(0.0F, -1.0F, 3.0F, 5.0F, 2.0F, 1.0F, 0.0F, false);
        bone2.setTextureOffset(66, 21).addBox(5.0F, -1.0F, -3.0F, 1.0F, 2.0F, 6.0F, 0.0F, false);

        bone8 = new ModelRenderer(this);
        bone8.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone2.addChild(bone8);
        setRotationAngle(bone8, 0.0F, 0.0F, 0.5236F);
        bone8.setTextureOffset(78, 18).addBox(0.0F, -0.25F, -4.0F, 5.0F, 2.0F, 1.0F, 0.0F, false);
        bone8.setTextureOffset(78, 18).addBox(0.0F, -0.25F, 3.0F, 5.0F, 2.0F, 1.0F, 0.0F, false);
        bone8.setTextureOffset(80, 21).addBox(5.0F, -0.25F, -3.0F, 1.0F, 2.0F, 6.0F, 0.0F, false);

        leftArm = new ModelRenderer(this);
        leftArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        leftArm.setTextureOffset(108, 48).addBox(-3.75F, 2.5F, -2.5F, 5.0F, 8.0F, 5.0F, 0.0F, false);
        leftArm.setTextureOffset(98, 48).addBox(-4.25F, 3.0F, -2.0F, 1.0F, 6.0F, 4.0F, 0.0F, false);

        bone5 = new ModelRenderer(this);
        bone5.setRotationPoint(0.0F, 0.0F, 0.0F);
        leftArm.addChild(bone5);
        setRotationAngle(bone5, 0.0F, 0.0F, 0.3491F);
        bone5.setTextureOffset(100, 25).addBox(-5.25F, -4.0F, -3.5F, 7.0F, 5.0F, 7.0F, 0.0F, false);

        handL = new ModelRenderer(this);
        handL.setRotationPoint(0.0F, 0.0F, 0.0F);
        leftArm.addChild(handL);
        setRotationAngle(handL, 0.0F, 0.0F, 0.1745F);
        handL.setTextureOffset(104, 37).addBox(-4.0F, 0.0F, -3.0F, 6.0F, 4.0F, 6.0F, 0.0F, false);

        rightArm = new ModelRenderer(this);
        rightArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        rightArm.setTextureOffset(108, 48).addBox(-1.25F, 2.5F, -2.5F, 5.0F, 8.0F, 5.0F, 0.0F, true);
        rightArm.setTextureOffset(98, 48).addBox(3.25F, 3.0F, -2.0F, 1.0F, 6.0F, 4.0F, 0.0F, true);

        bone4 = new ModelRenderer(this);
        bone4.setRotationPoint(0.0F, 0.0F, 0.0F);
        rightArm.addChild(bone4);
        setRotationAngle(bone4, 0.0F, 0.0F, -0.3491F);
        bone4.setTextureOffset(100, 25).addBox(-1.75F, -4.0F, -3.5F, 7.0F, 5.0F, 7.0F, 0.0F, true);

        handR = new ModelRenderer(this);
        handR.setRotationPoint(0.0F, 0.0F, 0.0F);
        rightArm.addChild(handR);
        setRotationAngle(handR, 0.0F, 0.0F, -0.1745F);
        handR.setTextureOffset(104, 37).addBox(-2.0F, 0.0F, -3.0F, 6.0F, 4.0F, 6.0F, 0.0F, true);

        rightLeg = new ModelRenderer(this);
        rightLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
        rightLeg.setTextureOffset(112, 112).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);
        rightLeg.setTextureOffset(108, 61).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 13.0F, 5.0F, 0.0F, true);
        rightLeg.setTextureOffset(98, 61).addBox(-2.1F, 1.0F, -3.5F, 4.0F, 5.0F, 1.0F, 0.0F, true);
        rightLeg.setTextureOffset(84, 70).addBox(-3.1F, 2.0F, -3.0F, 6.0F, 3.0F, 6.0F, 0.0F, true);

        leftLeg = new ModelRenderer(this);
        leftLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
        leftLeg.setTextureOffset(112, 112).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        leftLeg.setTextureOffset(108, 61).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 13.0F, 5.0F, 0.0F, false);
        leftLeg.setTextureOffset(98, 61).addBox(-2.0F, 1.0F, -3.5F, 4.0F, 5.0F, 1.0F, 0.0F, false);
        leftLeg.setTextureOffset(84, 70).addBox(-3.0F, 2.0F, -3.0F, 6.0F, 3.0F, 6.0F, 0.0F, false);

        bootL = new ModelRenderer(this);
        bootL.setRotationPoint(-1.9F, 12.0F, 0.0F);
        bootL.setTextureOffset(24, 31).addBox(-3.0F, 8.0F, -3.0F, 6.0F, 5.0F, 6.0F, 0.0F, false);
        bootL.setTextureOffset(24, 42).addBox(-3.0F, 6.0F, 2.0F, 6.0F, 2.0F, 1.0F, 0.0F, false);
        bootL.setTextureOffset(24, 45).addBox(2.0F, 6.0F, -1.0F, 1.0F, 2.0F, 3.0F, 0.0F, false);
        bootL.setTextureOffset(24, 45).addBox(-3.0F, 6.0F, -1.0F, 1.0F, 2.0F, 3.0F, 0.0F, false);
        bootL.setTextureOffset(29, 45).addBox(2.0F, 7.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bootL.setTextureOffset(29, 45).addBox(-3.0F, 7.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bootL.setTextureOffset(24, 27).addBox(-2.0F, 10.0F, -4.0F, 4.0F, 3.0F, 1.0F, 0.0F, false);
        bootL.setTextureOffset(21, 33).addBox(-1.0F, 9.0F, -4.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);

        bootR = new ModelRenderer(this);
        bootR.setRotationPoint(1.9F, 12.0F, 0.0F);
        bootR.setTextureOffset(24, 31).addBox(-3.0F, 8.0F, -3.0F, 6.0F, 5.0F, 6.0F, 0.0F, true);
        bootR.setTextureOffset(24, 42).addBox(-3.0F, 6.0F, 2.0F, 6.0F, 2.0F, 1.0F, 0.0F, true);
        bootR.setTextureOffset(24, 45).addBox(-3.0F, 6.0F, -1.0F, 1.0F, 2.0F, 3.0F, 0.0F, true);
        bootR.setTextureOffset(24, 45).addBox(2.0F, 6.0F, -1.0F, 1.0F, 2.0F, 3.0F, 0.0F, true);
        bootR.setTextureOffset(29, 45).addBox(-3.0F, 7.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
        bootR.setTextureOffset(29, 45).addBox(2.0F, 7.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);
        bootR.setTextureOffset(24, 27).addBox(-2.0F, 10.0F, -4.0F, 4.0F, 3.0F, 1.0F, 0.0F, true);
        bootR.setTextureOffset(21, 33).addBox(-1.0F, 9.0F, -4.0F, 2.0F, 1.0F, 1.0F, 0.0F, true);
    }

    @Override
    public void render(MatrixStack ms, IVertexBuilder buffer, int light, int overlay, float r, float g, float b, float a) {

        head.showModel = slot == EquipmentSlotType.HEAD;
        body.showModel = slot == EquipmentSlotType.CHEST;
        leftArm.showModel = slot == EquipmentSlotType.CHEST;
        rightArm.showModel = slot == EquipmentSlotType.CHEST;
        leftLeg.showModel = slot == EquipmentSlotType.LEGS;
        rightLeg.showModel = slot == EquipmentSlotType.LEGS;
        bootL.showModel = slot == EquipmentSlotType.FEET;
        bootR.showModel = slot == EquipmentSlotType.FEET;
        bipedHeadwear.showModel = false;

        bipedHead = head;
        bipedBody = body;
        bipedRightArm = leftArm;
        bipedLeftArm = rightArm;
        if (slot == EquipmentSlotType.LEGS) {
            bipedRightLeg = leftLeg;
            bipedLeftLeg = rightLeg;
        } else {
            bipedRightLeg = bootL;
            bipedLeftLeg = bootR;
        }
        super.render(ms, buffer, light, overlay, r, g, b, a);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
