package com.meteor.extrabotany.client.model;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import vazkii.botania.client.model.armor.ModelArmor;

import javax.annotation.Nonnull;

public class ModelArmorGoblinSlayerNew extends ModelArmor {

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

    public ModelArmorGoblinSlayerNew(EntityEquipmentSlot slot) {
        super(slot);
        textureWidth = 128;
        textureHeight = 128;
        float s = 0.01F;

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.cubeList.add(new ModelBox(head, 0, 18, -2.0F, -1.0F, -5.0F, 4, 2, 2, 0.0F, false));
        head.cubeList.add(new ModelBox(head, 4, 23, -1.5F, -6.0F, -5.0F, 1, 5, 1, 0.0F, false));
        head.cubeList.add(new ModelBox(head, 8, 23, 0.5F, -6.0F, -5.0F, 1, 5, 1, 0.0F, false));
        head.cubeList.add(new ModelBox(head, 0, 0, -4.5F, -8.5F, -4.5F, 9, 9, 9, 0.0F, false));

        plates = new ModelRenderer(this);
        plates.setRotationPoint(0.0F, -8.5F, -2.0F);
        setRotationAngle(plates, 0.0F, 0.7854F, 0.0F);
        head.addChild(plates);
        plates.cubeList.add(new ModelBox(plates, 0, 49, -3.0F, -0.5F, -3.0F, 6, 1, 6, 0.0F, false));
        plates.cubeList.add(new ModelBox(plates, 0, 56, -3.0F, -1.5F, -2.0F, 5, 1, 5, 0.0F, false));
        plates.cubeList.add(new ModelBox(plates, 0, 65, -1.0F, -2.5F, 1.0F, 2, 1, 3, 0.0F, false));
        plates.cubeList.add(new ModelBox(plates, 0, 74, -3.0F, -0.5F, 3.0F, 4, 1, 1, 0.0F, false));
        plates.cubeList.add(new ModelBox(plates, 0, 69, -4.0F, -0.5F, -1.0F, 1, 1, 4, 0.0F, false));
        plates.cubeList.add(new ModelBox(plates, 0, 62, -4.0F, -2.5F, -1.0F, 5, 1, 2, 0.0F, false));
        plates.cubeList.add(new ModelBox(plates, 0, 35, -2.0F, 1.5F, -4.0F, 6, 1, 6, 0.0F, false));
        plates.cubeList.add(new ModelBox(plates, 0, 29, -1.5F, 2.5F, -3.5F, 5, 1, 5, 0.0F, false));
        plates.cubeList.add(new ModelBox(plates, 0, 42, -2.5F, 0.5F, -3.5F, 6, 1, 6, 0.0F, false));

        bang2 = new ModelRenderer(this);
        bang2.setRotationPoint(-2.0F, 0.0F, -5.0F);
        setRotationAngle(bang2, 0.0F, 0.0F, -0.1745F);
        head.addChild(bang2);
        bang2.cubeList.add(new ModelBox(bang2, 0, 22, -0.75F, -6.25F, 0.0F, 1, 6, 1, 0.0F, false));

        bang1 = new ModelRenderer(this);
        bang1.setRotationPoint(2.0F, 0.0F, -4.0F);
        setRotationAngle(bang1, 0.0F, 0.0F, 0.1745F);
        head.addChild(bang1);
        bang1.cubeList.add(new ModelBox(bang1, 12, 22, -0.25F, -6.25F, -1.0F, 1, 6, 1, 0.0F, false));

        ear = new ModelRenderer(this);
        ear.setRotationPoint(5.5F, -4.0F, 0.0F);
        setRotationAngle(ear, 0.7854F, 0.0F, 0.0F);
        head.addChild(ear);
        ear.cubeList.add(new ModelBox(ear, 16, 18, -1.0F, -1.0F, -2.0F, 1, 3, 3, 0.0F, false));
        ear.cubeList.add(new ModelBox(ear, 16, 24, -11.0F, -1.0F, -2.0F, 1, 3, 3, 0.0F, false));

        hair = new ModelRenderer(this);
        hair.setRotationPoint(0.0F, -9.5F, 2.0F);
        setRotationAngle(hair, 0.1745F, -0.3491F, 0.0F);
        head.addChild(hair);
        hair.cubeList.add(new ModelBox(hair, 35, 0, -1.5F, -0.5F, 0.25F, 3, 0, 4, 0.0F, false));

        hair2 = new ModelRenderer(this);
        hair2.setRotationPoint(-0.25F, -9.5F, 2.25F);
        setRotationAngle(hair2, -0.3491F, 0.2618F, 0.0F);
        head.addChild(hair2);
        hair2.cubeList.add(new ModelBox(hair2, 34, 4, -1.25F, -0.5F, 0.0F, 3, 0, 5, 0.0F, false));

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.cubeList.add(new ModelBox(body, 100, 0, -4.5F, -0.5F, -2.5F, 9, 13, 5, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 50, 8, -5.0F, 6.0F, -3.0F, 10, 1, 6, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 68, 0, -5.0F, 11.0F, -3.0F, 10, 2, 6, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 110, 18, -4.0F, 0.0F, 2.25F, 8, 6, 1, 0.0F, false));

        bone = new ModelRenderer(this);
        bone.setRotationPoint(0.0F, 7.0F, -2.5F);
        setRotationAngle(bone, 0.0F, 0.0F, -0.7854F);
        body.addChild(bone);
        bone.cubeList.add(new ModelBox(bone, 50, 0, 5.5F, -4.0F, -1.0F, 1, 4, 1, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 82, 8, -1.5F, -2.5F, -1.0F, 4, 4, 1, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 54, 0, -0.5F, -5.5F, -2.0F, 6, 6, 1, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 96, 18, -3.5F, -2.5F, 5.0F, 6, 6, 1, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 46, 9, 0.0F, -6.5F, -1.0F, 4, 1, 1, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 50, 15, 0.5F, -4.5F, -3.0F, 4, 2, 1, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 60, 15, 2.5F, -2.5F, -3.0F, 2, 2, 1, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 92, 8, -5.5F, 2.5F, -2.5F, 3, 3, 1, 0.0F, false));

        bone3 = new ModelRenderer(this);
        bone3.setRotationPoint(0.0F, 12.0F, 0.0F);
        setRotationAngle(bone3, 0.0F, 0.0F, 0.2618F);
        body.addChild(bone3);
        bone3.cubeList.add(new ModelBox(bone3, 66, 15, -5.0F, -1.0F, -4.0F, 5, 2, 1, 0.0F, false));
        bone3.cubeList.add(new ModelBox(bone3, 66, 15, -5.0F, -1.0F, 3.0F, 5, 2, 1, 0.0F, false));
        bone3.cubeList.add(new ModelBox(bone3, 66, 21, -6.0F, -1.0F, -3.0F, 1, 2, 6, 0.0F, false));

        bone7 = new ModelRenderer(this);
        bone7.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotationAngle(bone7, 0.0F, 0.0F, -0.5236F);
        bone3.addChild(bone7);
        bone7.cubeList.add(new ModelBox(bone7, 66, 18, -5.0F, -0.25F, -4.0F, 5, 2, 1, 0.0F, false));
        bone7.cubeList.add(new ModelBox(bone7, 66, 18, -5.0F, -0.25F, 3.0F, 5, 2, 1, 0.0F, false));
        bone7.cubeList.add(new ModelBox(bone7, 80, 21, -6.0F, -0.25F, -3.0F, 1, 2, 6, 0.0F, false));

        bone2 = new ModelRenderer(this);
        bone2.setRotationPoint(0.0F, 12.0F, 0.0F);
        setRotationAngle(bone2, 0.0F, 0.0F, -0.2618F);
        body.addChild(bone2);
        bone2.cubeList.add(new ModelBox(bone2, 78, 15, 0.0F, -1.0F, -4.0F, 5, 2, 1, 0.0F, false));
        bone2.cubeList.add(new ModelBox(bone2, 78, 15, 0.0F, -1.0F, 3.0F, 5, 2, 1, 0.0F, false));
        bone2.cubeList.add(new ModelBox(bone2, 66, 21, 5.0F, -1.0F, -3.0F, 1, 2, 6, 0.0F, false));

        bone8 = new ModelRenderer(this);
        bone8.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotationAngle(bone8, 0.0F, 0.0F, 0.5236F);
        bone2.addChild(bone8);
        bone8.cubeList.add(new ModelBox(bone8, 78, 18, 0.0F, -0.25F, -4.0F, 5, 2, 1, 0.0F, false));
        bone8.cubeList.add(new ModelBox(bone8, 78, 18, 0.0F, -0.25F, 3.0F, 5, 2, 1, 0.0F, false));
        bone8.cubeList.add(new ModelBox(bone8, 80, 21, 5.0F, -0.25F, -3.0F, 1, 2, 6, 0.0F, false));

        leftArm = new ModelRenderer(this);
        leftArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        leftArm.cubeList.add(new ModelBox(leftArm, 108, 48, -3.75F, 2.5F, -2.5F, 5, 8, 5, 0.0F, false));
        leftArm.cubeList.add(new ModelBox(leftArm, 98, 48, -4.25F, 3.0F, -2.0F, 1, 6, 4, 0.0F, false));

        bone5 = new ModelRenderer(this);
        bone5.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotationAngle(bone5, 0.0F, 0.0F, 0.3491F);
        leftArm.addChild(bone5);
        bone5.cubeList.add(new ModelBox(bone5, 100, 25, -5.25F, -4.0F, -3.5F, 7, 5, 7, 0.0F, false));

        handL = new ModelRenderer(this);
        handL.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotationAngle(handL, 0.0F, 0.0F, 0.1745F);
        leftArm.addChild(handL);
        handL.cubeList.add(new ModelBox(handL, 104, 37, -4.0F, 0.0F, -3.0F, 6, 4, 6, 0.0F, false));

        rightArm = new ModelRenderer(this);
        rightArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        rightArm.cubeList.add(new ModelBox(rightArm, 108, 48, -1.25F, 2.5F, -2.5F, 5, 8, 5, 0.0F, true));
        rightArm.cubeList.add(new ModelBox(rightArm, 98, 48, 3.25F, 3.0F, -2.0F, 1, 6, 4, 0.0F, true));

        bone4 = new ModelRenderer(this);
        bone4.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotationAngle(bone4, 0.0F, 0.0F, -0.3491F);
        rightArm.addChild(bone4);
        bone4.cubeList.add(new ModelBox(bone4, 100, 25, -1.75F, -4.0F, -3.5F, 7, 5, 7, 0.0F, true));

        handR = new ModelRenderer(this);
        handR.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotationAngle(handR, 0.0F, 0.0F, -0.1745F);
        rightArm.addChild(handR);
        handR.cubeList.add(new ModelBox(handR, 104, 37, -2.0F, 0.0F, -3.0F, 6, 4, 6, 0.0F, true));

        leftLeg = new ModelRenderer(this);
        leftLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
        leftLeg.cubeList.add(new ModelBox(leftLeg, 112, 112, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));
        leftLeg.cubeList.add(new ModelBox(leftLeg, 108, 61, -2.5F, -0.5F, -2.5F, 5, 13, 5, 0.0F, false));
        leftLeg.cubeList.add(new ModelBox(leftLeg, 98, 61, -2.0F, 1.0F, -3.5F, 4, 5, 1, 0.0F, false));
        leftLeg.cubeList.add(new ModelBox(leftLeg, 84, 70, -3.0F, 2.0F, -3.0F, 6, 3, 6, 0.0F, false));

        bootL = new ModelRenderer(this);
        bootL.setRotationPoint(-1.9F, 12.0F, 0.0F);
        bootL.cubeList.add(new ModelBox(bootL, 24, 31, -3.0F, 8.0F, -3.0F, 6, 5, 6, 0.0F, false));
        bootL.cubeList.add(new ModelBox(bootL, 24, 42, -3.0F, 6.0F, 2.0F, 6, 2, 1, 0.0F, false));
        bootL.cubeList.add(new ModelBox(bootL, 24, 45, 2.0F, 6.0F, -1.0F, 1, 2, 3, 0.0F, false));
        bootL.cubeList.add(new ModelBox(bootL, 24, 45, -3.0F, 6.0F, -1.0F, 1, 2, 3, 0.0F, false));
        bootL.cubeList.add(new ModelBox(bootL, 29, 45, 2.0F, 7.0F, -2.0F, 1, 1, 1, 0.0F, false));
        bootL.cubeList.add(new ModelBox(bootL, 29, 45, -3.0F, 7.0F, -2.0F, 1, 1, 1, 0.0F, false));
        bootL.cubeList.add(new ModelBox(bootL, 24, 27, -2.0F, 10.0F, -4.0F, 4, 3, 1, 0.0F, false));
        bootL.cubeList.add(new ModelBox(bootL, 21, 33, -1.0F, 9.0F, -4.0F, 2, 1, 1, 0.0F, false));

        bootR = new ModelRenderer(this);
        bootR.setRotationPoint(1.9F, 12.0F, 0.0F);
        bootR.cubeList.add(new ModelBox(bootR, 24, 31, -3.0F, 8.0F, -3.0F, 6, 5, 6, 0.0F, true));
        bootR.cubeList.add(new ModelBox(bootR, 24, 42, -3.0F, 6.0F, 2.0F, 6, 2, 1, 0.0F, true));
        bootR.cubeList.add(new ModelBox(bootR, 24, 45, -3.0F, 6.0F, -1.0F, 1, 2, 3, 0.0F, true));
        bootR.cubeList.add(new ModelBox(bootR, 24, 45, 2.0F, 6.0F, -1.0F, 1, 2, 3, 0.0F, true));
        bootR.cubeList.add(new ModelBox(bootR, 29, 45, -3.0F, 7.0F, -2.0F, 1, 1, 1, 0.0F, true));
        bootR.cubeList.add(new ModelBox(bootR, 29, 45, 2.0F, 7.0F, -2.0F, 1, 1, 1, 0.0F, true));
        bootR.cubeList.add(new ModelBox(bootR, 24, 27, -2.0F, 10.0F, -4.0F, 4, 3, 1, 0.0F, true));
        bootR.cubeList.add(new ModelBox(bootR, 21, 33, -1.0F, 9.0F, -4.0F, 2, 1, 1, 0.0F, true));

        rightLeg = new ModelRenderer(this);
        rightLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
        rightLeg.cubeList.add(new ModelBox(rightLeg, 112, 112, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, true));
        rightLeg.cubeList.add(new ModelBox(rightLeg, 108, 61, -2.5F, -0.5F, -2.5F, 5, 13, 5, 0.0F, true));
        rightLeg.cubeList.add(new ModelBox(rightLeg, 98, 61, -2.1F, 1.0F, -3.5F, 4, 5, 1, 0.0F, true));
        rightLeg.cubeList.add(new ModelBox(rightLeg, 84, 70, -3.1F, 2.0F, -3.0F, 6, 3, 6, 0.0F, true));
    }

    @Override
    public void render(@Nonnull Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {

        head.showModel = slot == EntityEquipmentSlot.HEAD;
        body.showModel = slot == EntityEquipmentSlot.CHEST;
        leftArm.showModel = slot == EntityEquipmentSlot.CHEST;
        rightArm.showModel = slot == EntityEquipmentSlot.CHEST;
        leftLeg.showModel = slot == EntityEquipmentSlot.LEGS;
        rightLeg.showModel = slot == EntityEquipmentSlot.LEGS;
        bootL.showModel = slot == EntityEquipmentSlot.FEET;
        bootR.showModel = slot == EntityEquipmentSlot.FEET;
        bipedHeadwear.showModel = false;

        bipedHead = head;
        bipedBody = body;
        bipedRightArm = leftArm;
        bipedLeftArm = rightArm;
        if (slot == EntityEquipmentSlot.LEGS) {
            bipedRightLeg = leftLeg;
            bipedLeftLeg = rightLeg;
        } else {
            bipedRightLeg = bootL;
            bipedLeftLeg = bootR;
        }
        super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
