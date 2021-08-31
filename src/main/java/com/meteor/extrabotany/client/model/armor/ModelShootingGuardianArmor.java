package com.meteor.extrabotany.client.model.armor;

import com.meteor.extrabotany.client.model.ModelArmor;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;

public class ModelShootingGuardianArmor extends ModelArmor {

    private final ModelRenderer rightArm;
    private final ModelRenderer bone54;
    private final ModelRenderer bone27;
    private final ModelRenderer bone28;
    private final ModelRenderer bone29;
    private final ModelRenderer bone30;
    private final ModelRenderer bone32;
    private final ModelRenderer bone33;
    private final ModelRenderer bone34;
    private final ModelRenderer bone24;
    private final ModelRenderer bone25;
    private final ModelRenderer bone17;
    private final ModelRenderer bone16;
    private final ModelRenderer bone26;
    private final ModelRenderer bone15;
    private final ModelRenderer bone14;
    private final ModelRenderer bone20;
    private final ModelRenderer bone31;
    private final ModelRenderer bone18;
    private final ModelRenderer bone19;
    private final ModelRenderer bone21;
    private final ModelRenderer bone7;
    private final ModelRenderer bone11;
    private final ModelRenderer bone12;
    private final ModelRenderer bone13;
    private final ModelRenderer bone6;
    private final ModelRenderer bone8;
    private final ModelRenderer bone9;
    private final ModelRenderer bone10;
    private final ModelRenderer bone;
    private final ModelRenderer bone2;
    private final ModelRenderer bone5;
    private final ModelRenderer bone3;
    private final ModelRenderer bone4;
    private final ModelRenderer leftArm;
    private final ModelRenderer bone55;
    private final ModelRenderer bone73;
    private final ModelRenderer bone74;
    private final ModelRenderer bone75;
    private final ModelRenderer bone76;
    private final ModelRenderer bone77;
    private final ModelRenderer bone78;
    private final ModelRenderer bone79;
    private final ModelRenderer bone80;
    private final ModelRenderer bone81;
    private final ModelRenderer bone82;
    private final ModelRenderer bone83;
    private final ModelRenderer bone84;
    private final ModelRenderer bone85;
    private final ModelRenderer bone86;
    private final ModelRenderer bone87;
    private final ModelRenderer bone88;
    private final ModelRenderer bone89;
    private final ModelRenderer bone90;
    private final ModelRenderer bone91;
    private final ModelRenderer bone92;
    private final ModelRenderer bone93;
    private final ModelRenderer bone94;
    private final ModelRenderer bone95;
    private final ModelRenderer bone96;
    private final ModelRenderer bone97;
    private final ModelRenderer bone98;
    private final ModelRenderer bone99;
    private final ModelRenderer bone100;
    private final ModelRenderer bone101;
    private final ModelRenderer bone102;
    private final ModelRenderer bone103;
    private final ModelRenderer bone104;
    private final ModelRenderer armor;
    private final ModelRenderer bone36;
    private final ModelRenderer bone48;
    private final ModelRenderer bone37;
    private final ModelRenderer bone38;
    private final ModelRenderer bone39;
    private final ModelRenderer bone40;
    private final ModelRenderer bone41;
    private final ModelRenderer bone47;
    private final ModelRenderer bone49;
    private final ModelRenderer bone46;
    private final ModelRenderer bone42;
    private final ModelRenderer bone43;
    private final ModelRenderer bone44;
    private final ModelRenderer bone45;
    private final ModelRenderer bone50;
    private final ModelRenderer medal;
    private final ModelRenderer rightMedal;
    private final ModelRenderer bone35;
    private final ModelRenderer bone51;
    private final ModelRenderer leftMedal;
    private final ModelRenderer bone52;
    private final ModelRenderer bone53;
    private final ModelRenderer rightLeg;
    private final ModelRenderer bone56;
    private final ModelRenderer bone57;
    private final ModelRenderer bone60;
    private final ModelRenderer bone61;
    private final ModelRenderer bone58;
    private final ModelRenderer bone59;
    private final ModelRenderer bone62;
    private final ModelRenderer bone63;
    private final ModelRenderer bone66;
    private final ModelRenderer bone64;
    private final ModelRenderer bone65;
    private final ModelRenderer leftLeg;
    private final ModelRenderer bone105;
    private final ModelRenderer bone106;
    private final ModelRenderer bone107;
    private final ModelRenderer bone108;
    private final ModelRenderer bone109;
    private final ModelRenderer bone110;
    private final ModelRenderer bone111;
    private final ModelRenderer bone112;
    private final ModelRenderer bone113;
    private final ModelRenderer bone114;
    private final ModelRenderer bone115;
    private final ModelRenderer shoeRight;
    private final ModelRenderer bone67;
    private final ModelRenderer bone70;
    private final ModelRenderer bone71;
    private final ModelRenderer bone68;
    private final ModelRenderer bone69;
    private final ModelRenderer bone72;
    private final ModelRenderer shoeLeft;
    private final ModelRenderer bone116;
    private final ModelRenderer bone117;
    private final ModelRenderer bone118;
    private final ModelRenderer bone119;
    private final ModelRenderer bone120;
    private final ModelRenderer bone121;

    private final ModelRenderer helmet;
    private final ModelRenderer bone233;
    private final ModelRenderer bone244;
    private final ModelRenderer bone344;
    private final ModelRenderer bone544;
    private final ModelRenderer bone444;
    private final ModelRenderer bone611;
    private final ModelRenderer bone1322;
    private final ModelRenderer bone1111;
    private final ModelRenderer bone1923;
    private final ModelRenderer bone2034;
    private final ModelRenderer bone1733;
    private final ModelRenderer bone1821;
    private final ModelRenderer bone721;
    private final ModelRenderer bone1021;
    private final ModelRenderer bone1221;
    private final ModelRenderer bone834;
    private final ModelRenderer bone1555;
    private final ModelRenderer bone966;
    private final ModelRenderer bone1477;
    private final ModelRenderer bone1688;

    public ModelShootingGuardianArmor(EquipmentSlotType slot) {
        super(slot);
        textureWidth = 64;
        textureHeight = 64;

        rightArm = new ModelRenderer(this);
        rightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);


        bone54 = new ModelRenderer(this);
        bone54.setRotationPoint(5.0F, 22.0F, 0.0F);
        rightArm.addChild(bone54);


        bone27 = new ModelRenderer(this);
        bone27.setRotationPoint(5.0F, 22.0F, 0.0F);
        rightArm.addChild(bone27);


        bone28 = new ModelRenderer(this);
        bone28.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone27.addChild(bone28);


        bone29 = new ModelRenderer(this);
        bone29.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone28.addChild(bone29);
        bone29.setTextureOffset(48, 43).addBox(-8.4F, -14.5F, -2.35F, 5.0F, 1.0F, 2.0F, 0.0F, false);

        bone30 = new ModelRenderer(this);
        bone30.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone28.addChild(bone30);
        bone30.setTextureOffset(35, 27).addBox(-8.4F, -14.5F, -0.65F, 5.0F, 1.0F, 3.0F, 0.0F, false);

        bone32 = new ModelRenderer(this);
        bone32.setRotationPoint(-6.0F, -12.0F, 0.0F);
        bone27.addChild(bone32);
        setRotationAngle(bone32, 0.0F, 0.0F, -0.1745F);


        bone33 = new ModelRenderer(this);
        bone33.setRotationPoint(6.0F, 12.0F, 0.0F);
        bone32.addChild(bone33);
        bone33.setTextureOffset(10, 16).addBox(-8.4F, -13.4F, -2.35F, 3.0F, 2.0F, 2.0F, 0.0F, false);

        bone34 = new ModelRenderer(this);
        bone34.setRotationPoint(6.0F, 12.0F, 0.0F);
        bone32.addChild(bone34);
        bone34.setTextureOffset(49, 14).addBox(-8.4F, -13.4F, -0.65F, 3.0F, 2.0F, 3.0F, 0.0F, false);

        bone24 = new ModelRenderer(this);
        bone24.setRotationPoint(5.0F, 22.0F, 0.0F);
        rightArm.addChild(bone24);


        bone25 = new ModelRenderer(this);
        bone25.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone24.addChild(bone25);


        bone17 = new ModelRenderer(this);
        bone17.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone25.addChild(bone17);
        bone17.setTextureOffset(35, 21).addBox(-8.9F, -19.0F, -0.4F, 5.0F, 3.0F, 3.0F, 0.0F, false);

        bone16 = new ModelRenderer(this);
        bone16.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone25.addChild(bone16);
        bone16.setTextureOffset(30, 37).addBox(-8.9F, -19.0F, -2.6F, 5.0F, 3.0F, 3.0F, 0.0F, false);

        bone26 = new ModelRenderer(this);
        bone26.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone24.addChild(bone26);


        bone15 = new ModelRenderer(this);
        bone15.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone26.addChild(bone15);
        bone15.setTextureOffset(23, 50).addBox(-8.81F, -22.0F, -2.35F, 4.0F, 4.0F, 2.0F, 0.0F, false);

        bone14 = new ModelRenderer(this);
        bone14.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone26.addChild(bone14);
        bone14.setTextureOffset(25, 43).addBox(-8.8F, -22.0F, -0.65F, 4.0F, 4.0F, 3.0F, 0.0F, false);

        bone20 = new ModelRenderer(this);
        bone20.setRotationPoint(-1.0F, 8.0F, 0.0F);
        rightArm.addChild(bone20);
        setRotationAngle(bone20, 0.0F, 0.0F, -0.1745F);


        bone31 = new ModelRenderer(this);
        bone31.setRotationPoint(0.0F, -1.0F, 0.0F);
        bone20.addChild(bone31);


        bone18 = new ModelRenderer(this);
        bone18.setRotationPoint(-0.5F, -1.0F, 1.3F);
        bone31.addChild(bone18);
        setRotationAngle(bone18, -0.2618F, 0.0F, 0.0F);
        bone18.setTextureOffset(49, 0).addBox(-1.9132F, -1.4756F, -1.6274F, 4.0F, 2.0F, 3.0F, 0.0F, false);

        bone19 = new ModelRenderer(this);
        bone19.setRotationPoint(-0.81F, -1.0F, -1.3F);
        bone31.addChild(bone19);
        setRotationAngle(bone19, 0.2618F, 0.0F, 0.0F);
        bone19.setTextureOffset(48, 38).addBox(-1.6132F, -1.4756F, -1.3726F, 4.0F, 2.0F, 3.0F, 0.0F, false);

        bone21 = new ModelRenderer(this);
        bone21.setRotationPoint(-2.5F, -2.5F, -0.5F);
        bone31.addChild(bone21);
        setRotationAngle(bone21, -0.7854F, 0.0F, 0.0F);
        bone21.setTextureOffset(8, 56).addBox(-0.2F, -1.5F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);

        bone7 = new ModelRenderer(this);
        bone7.setRotationPoint(-2.5F, -2.0F, 0.5F);
        rightArm.addChild(bone7);
        setRotationAngle(bone7, 0.0F, 0.0F, 1.7453F);


        bone11 = new ModelRenderer(this);
        bone11.setRotationPoint(7.5F, 24.0F, -0.5F);
        bone7.addChild(bone11);
        bone11.setTextureOffset(38, 0).addBox(-6.0F, -23.0F, -2.51F, 3.0F, 1.0F, 5.0F, 0.0F, false);

        bone12 = new ModelRenderer(this);
        bone12.setRotationPoint(4.0F, -1.0F, -3.1F);
        bone7.addChild(bone12);
        bone12.setTextureOffset(22, 56).addBox(-1.5F, -1.0F, 0.2F, 2.0F, 3.0F, 1.0F, 0.0F, false);

        bone13 = new ModelRenderer(this);
        bone13.setRotationPoint(7.5F, 24.0F, -0.5F);
        bone7.addChild(bone13);
        bone13.setTextureOffset(52, 32).addBox(-6.0F, -26.0F, 1.4F, 3.0F, 3.0F, 1.0F, 0.0F, false);

        bone6 = new ModelRenderer(this);
        bone6.setRotationPoint(-2.5F, -2.0F, 0.5F);
        rightArm.addChild(bone6);
        setRotationAngle(bone6, 0.0F, 0.0F, 1.8326F);


        bone8 = new ModelRenderer(this);
        bone8.setRotationPoint(7.5F, 24.0F, -0.5F);
        bone6.addChild(bone8);
        bone8.setTextureOffset(43, 32).addBox(-6.0F, -23.0F, -2.5F, 2.0F, 1.0F, 5.0F, 0.0F, false);

        bone9 = new ModelRenderer(this);
        bone9.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone6.addChild(bone9);
        bone9.setTextureOffset(53, 9).addBox(1.0F, -3.0F, -3.0F, 2.0F, 4.0F, 1.0F, 0.0F, false);

        bone10 = new ModelRenderer(this);
        bone10.setRotationPoint(7.5F, 24.0F, -0.5F);
        bone6.addChild(bone10);
        bone10.setTextureOffset(4, 53).addBox(-6.5F, -27.0F, 1.5F, 2.0F, 4.0F, 1.0F, 0.0F, false);

        bone = new ModelRenderer(this);
        bone.setRotationPoint(-2.5F, -2.0F, 0.5F);
        rightArm.addChild(bone);
        setRotationAngle(bone, 0.0F, 0.0F, 1.1345F);


        bone2 = new ModelRenderer(this);
        bone2.setRotationPoint(7.5F, 24.0F, -0.5F);
        bone.addChild(bone2);
        bone2.setTextureOffset(41, 41).addBox(-8.0F, -26.0F, -2.5F, 1.0F, 5.0F, 5.0F, 0.0F, false);

        bone5 = new ModelRenderer(this);
        bone5.setRotationPoint(7.5F, 24.0F, -0.5F);
        bone.addChild(bone5);
        bone5.setTextureOffset(49, 49).addBox(-7.0F, -22.0F, -2.0F, 2.0F, 1.0F, 4.0F, 0.0F, false);

        bone3 = new ModelRenderer(this);
        bone3.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone.addChild(bone3);
        bone3.setTextureOffset(35, 54).addBox(0.0F, -1.5F, -3.1F, 2.0F, 4.0F, 1.0F, 0.0F, false);

        bone4 = new ModelRenderer(this);
        bone4.setRotationPoint(7.5F, 24.0F, -0.5F);
        bone.addChild(bone4);
        bone4.setTextureOffset(48, 54).addBox(-7.5F, -25.5F, 1.6F, 2.0F, 4.0F, 1.0F, 0.0F, false);

        leftArm = new ModelRenderer(this);
        leftArm.setRotationPoint(5.0F, 2.0F, 0.0F);


        bone55 = new ModelRenderer(this);
        bone55.setRotationPoint(-5.0F, 22.0F, 0.0F);
        leftArm.addChild(bone55);


        bone73 = new ModelRenderer(this);
        bone73.setRotationPoint(-5.0F, 22.0F, 0.0F);
        leftArm.addChild(bone73);


        bone74 = new ModelRenderer(this);
        bone74.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone73.addChild(bone74);


        bone75 = new ModelRenderer(this);
        bone75.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone74.addChild(bone75);
        bone75.setTextureOffset(48, 43).addBox(3.4F, -14.5F, -2.35F, 5.0F, 1.0F, 2.0F, 0.0F, true);

        bone76 = new ModelRenderer(this);
        bone76.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone74.addChild(bone76);
        bone76.setTextureOffset(35, 27).addBox(3.4F, -14.5F, -0.65F, 5.0F, 1.0F, 3.0F, 0.0F, true);

        bone77 = new ModelRenderer(this);
        bone77.setRotationPoint(6.0F, -12.0F, 0.0F);
        bone73.addChild(bone77);
        setRotationAngle(bone77, 0.0F, 0.0F, 0.1745F);


        bone78 = new ModelRenderer(this);
        bone78.setRotationPoint(-6.0F, 12.0F, 0.0F);
        bone77.addChild(bone78);
        bone78.setTextureOffset(10, 16).addBox(5.4F, -13.4F, -2.35F, 3.0F, 2.0F, 2.0F, 0.0F, true);

        bone79 = new ModelRenderer(this);
        bone79.setRotationPoint(-6.0F, 12.0F, 0.0F);
        bone77.addChild(bone79);
        bone79.setTextureOffset(49, 14).addBox(5.4F, -13.4F, -0.65F, 3.0F, 2.0F, 3.0F, 0.0F, true);

        bone80 = new ModelRenderer(this);
        bone80.setRotationPoint(-5.0F, 22.0F, 0.0F);
        leftArm.addChild(bone80);


        bone81 = new ModelRenderer(this);
        bone81.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone80.addChild(bone81);


        bone82 = new ModelRenderer(this);
        bone82.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone81.addChild(bone82);
        bone82.setTextureOffset(35, 21).addBox(3.9F, -19.0F, -0.4F, 5.0F, 3.0F, 3.0F, 0.0F, true);

        bone83 = new ModelRenderer(this);
        bone83.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone81.addChild(bone83);
        bone83.setTextureOffset(30, 37).addBox(3.9F, -19.0F, -2.6F, 5.0F, 3.0F, 3.0F, 0.0F, true);

        bone84 = new ModelRenderer(this);
        bone84.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone80.addChild(bone84);


        bone85 = new ModelRenderer(this);
        bone85.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone84.addChild(bone85);
        bone85.setTextureOffset(23, 50).addBox(4.81F, -22.0F, -2.35F, 4.0F, 4.0F, 2.0F, 0.0F, true);

        bone86 = new ModelRenderer(this);
        bone86.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone84.addChild(bone86);
        bone86.setTextureOffset(25, 43).addBox(4.8F, -22.0F, -0.65F, 4.0F, 4.0F, 3.0F, 0.0F, true);

        bone87 = new ModelRenderer(this);
        bone87.setRotationPoint(1.0F, 8.0F, 0.0F);
        leftArm.addChild(bone87);
        setRotationAngle(bone87, 0.0F, 0.0F, 0.1745F);


        bone88 = new ModelRenderer(this);
        bone88.setRotationPoint(0.0F, -1.0F, 0.0F);
        bone87.addChild(bone88);


        bone89 = new ModelRenderer(this);
        bone89.setRotationPoint(0.5F, -1.0F, 1.3F);
        bone88.addChild(bone89);
        setRotationAngle(bone89, -0.2618F, 0.0F, 0.0F);
        bone89.setTextureOffset(49, 0).addBox(-2.0868F, -1.4756F, -1.6274F, 4.0F, 2.0F, 3.0F, 0.0F, true);

        bone90 = new ModelRenderer(this);
        bone90.setRotationPoint(0.81F, -1.0F, -1.3F);
        bone88.addChild(bone90);
        setRotationAngle(bone90, 0.2618F, 0.0F, 0.0F);
        bone90.setTextureOffset(48, 38).addBox(-2.3868F, -1.4756F, -1.3726F, 4.0F, 2.0F, 3.0F, 0.0F, true);

        bone91 = new ModelRenderer(this);
        bone91.setRotationPoint(2.5F, -2.5F, -0.5F);
        bone88.addChild(bone91);
        setRotationAngle(bone91, -0.7854F, 0.0F, 0.0F);
        bone91.setTextureOffset(8, 56).addBox(-0.8F, -1.5F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, true);

        bone92 = new ModelRenderer(this);
        bone92.setRotationPoint(2.5F, -2.0F, 0.5F);
        leftArm.addChild(bone92);
        setRotationAngle(bone92, 0.0F, 0.0F, -1.7453F);


        bone93 = new ModelRenderer(this);
        bone93.setRotationPoint(-7.5F, 24.0F, -0.5F);
        bone92.addChild(bone93);
        bone93.setTextureOffset(38, 0).addBox(3.0F, -23.0F, -2.51F, 3.0F, 1.0F, 5.0F, 0.0F, true);

        bone94 = new ModelRenderer(this);
        bone94.setRotationPoint(-4.0F, -1.0F, -3.1F);
        bone92.addChild(bone94);
        bone94.setTextureOffset(22, 56).addBox(-0.5F, -1.0F, 0.2F, 2.0F, 3.0F, 1.0F, 0.0F, true);

        bone95 = new ModelRenderer(this);
        bone95.setRotationPoint(-7.5F, 24.0F, -0.5F);
        bone92.addChild(bone95);
        bone95.setTextureOffset(52, 32).addBox(3.0F, -26.0F, 1.4F, 3.0F, 3.0F, 1.0F, 0.0F, true);

        bone96 = new ModelRenderer(this);
        bone96.setRotationPoint(2.5F, -2.0F, 0.5F);
        leftArm.addChild(bone96);
        setRotationAngle(bone96, 0.0F, 0.0F, -1.8326F);


        bone97 = new ModelRenderer(this);
        bone97.setRotationPoint(-7.5F, 24.0F, -0.5F);
        bone96.addChild(bone97);
        bone97.setTextureOffset(43, 32).addBox(4.0F, -23.0F, -2.5F, 2.0F, 1.0F, 5.0F, 0.0F, true);

        bone98 = new ModelRenderer(this);
        bone98.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone96.addChild(bone98);
        bone98.setTextureOffset(53, 9).addBox(-3.0F, -3.0F, -3.0F, 2.0F, 4.0F, 1.0F, 0.0F, true);

        bone99 = new ModelRenderer(this);
        bone99.setRotationPoint(-7.5F, 24.0F, -0.5F);
        bone96.addChild(bone99);
        bone99.setTextureOffset(4, 53).addBox(4.5F, -27.0F, 1.5F, 2.0F, 4.0F, 1.0F, 0.0F, true);

        bone100 = new ModelRenderer(this);
        bone100.setRotationPoint(2.5F, -2.0F, 0.5F);
        leftArm.addChild(bone100);
        setRotationAngle(bone100, 0.0F, 0.0F, -1.1345F);


        bone101 = new ModelRenderer(this);
        bone101.setRotationPoint(-7.5F, 24.0F, -0.5F);
        bone100.addChild(bone101);
        bone101.setTextureOffset(41, 41).addBox(7.0F, -26.0F, -2.5F, 1.0F, 5.0F, 5.0F, 0.0F, true);

        bone102 = new ModelRenderer(this);
        bone102.setRotationPoint(-7.5F, 24.0F, -0.5F);
        bone100.addChild(bone102);
        bone102.setTextureOffset(49, 49).addBox(5.0F, -22.0F, -2.0F, 2.0F, 1.0F, 4.0F, 0.0F, true);

        bone103 = new ModelRenderer(this);
        bone103.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone100.addChild(bone103);
        bone103.setTextureOffset(35, 54).addBox(-2.0F, -1.5F, -3.1F, 2.0F, 4.0F, 1.0F, 0.0F, true);

        bone104 = new ModelRenderer(this);
        bone104.setRotationPoint(-7.5F, 24.0F, -0.5F);
        bone100.addChild(bone104);
        bone104.setTextureOffset(48, 54).addBox(5.5F, -25.5F, 1.6F, 2.0F, 4.0F, 1.0F, 0.0F, true);

        armor = new ModelRenderer(this);
        armor.setRotationPoint(0.0F, 0.0F, 0.0F);


        bone36 = new ModelRenderer(this);
        bone36.setRotationPoint(-2.5F, 1.0F, -2.0F);
        armor.addChild(bone36);
        bone36.setTextureOffset(35, 12).addBox(-1.5F, -0.5F, -0.6F, 8.0F, 1.0F, 1.0F, 0.0F, false);

        bone48 = new ModelRenderer(this);
        bone48.setRotationPoint(-2.5F, 1.0F, -2.0F);
        armor.addChild(bone48);
        bone48.setTextureOffset(35, 9).addBox(-1.5F, -0.5F, 3.6F, 8.0F, 2.0F, 1.0F, 0.0F, false);

        bone37 = new ModelRenderer(this);
        bone37.setRotationPoint(-2.5F, 1.0F, -2.0F);
        armor.addChild(bone37);
        bone37.setTextureOffset(53, 19).addBox(-1.5F, 0.5F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);

        bone38 = new ModelRenderer(this);
        bone38.setRotationPoint(3.0F, 2.0F, -2.0F);
        armor.addChild(bone38);
        bone38.setTextureOffset(43, 38).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);

        bone39 = new ModelRenderer(this);
        bone39.setRotationPoint(0.0F, 2.0F, -2.0F);
        armor.addChild(bone39);
        setRotationAngle(bone39, 0.0F, 0.0F, 0.7854F);
        bone39.setTextureOffset(28, 56).addBox(-1.5F, -1.5F, -0.5F, 2.0F, 2.0F, 1.0F, 0.0F, false);

        bone40 = new ModelRenderer(this);
        bone40.setRotationPoint(0.0F, 2.0F, -2.0F);
        armor.addChild(bone40);
        setRotationAngle(bone40, 0.0F, 0.0F, 0.7854F);
        bone40.setTextureOffset(0, 44).addBox(-2.0F, -2.0F, -0.41F, 3.0F, 3.0F, 1.0F, 0.0F, false);

        bone41 = new ModelRenderer(this);
        bone41.setRotationPoint(-2.5F, 1.0F, -2.0F);
        armor.addChild(bone41);
        bone41.setTextureOffset(13, 43).addBox(0.0F, 0.0F, -0.4F, 5.0F, 8.0F, 1.0F, 0.0F, false);

        bone47 = new ModelRenderer(this);
        bone47.setRotationPoint(-2.5F, 1.0F, -2.0F);
        armor.addChild(bone47);
        bone47.setTextureOffset(15, 15).addBox(-2.0F, 0.0F, -0.38F, 5.0F, 11.0F, 5.0F, 0.0F, false);

        bone49 = new ModelRenderer(this);
        bone49.setRotationPoint(-2.5F, 1.0F, -2.0F);
        armor.addChild(bone49);
        bone49.setTextureOffset(0, 0).addBox(2.0F, -0.01F, -0.37F, 5.0F, 11.0F, 5.0F, 0.0F, false);

        bone46 = new ModelRenderer(this);
        bone46.setRotationPoint(0.0F, 9.0F, -1.89F);
        armor.addChild(bone46);
        setRotationAngle(bone46, 0.0F, 0.0F, 0.7854F);
        bone46.setTextureOffset(54, 54).addBox(1.0F, -2.0F, -0.5F, 2.0F, 3.0F, 1.0F, 0.0F, false);
        bone46.setTextureOffset(51, 24).addBox(-2.0F, 1.0F, -0.5F, 3.0F, 2.0F, 1.0F, 0.0F, false);

        bone42 = new ModelRenderer(this);
        bone42.setRotationPoint(-2.5F, 1.0F, -2.0F);
        armor.addChild(bone42);
        bone42.setTextureOffset(20, 9).addBox(-1.0F, 1.0F, -0.41F, 1.0F, 3.0F, 1.0F, 0.0F, false);

        bone43 = new ModelRenderer(this);
        bone43.setRotationPoint(-2.5F, 1.0F, -2.0F);
        armor.addChild(bone43);
        bone43.setTextureOffset(0, 16).addBox(5.0F, 1.0F, -0.41F, 1.0F, 3.0F, 1.0F, 0.0F, false);

        bone44 = new ModelRenderer(this);
        bone44.setRotationPoint(3.5F, 6.0F, -1.92F);
        armor.addChild(bone44);
        setRotationAngle(bone44, 0.0F, 0.0F, 0.1745F);
        bone44.setTextureOffset(0, 53).addBox(-1.5F, -4.0F, -0.5F, 1.0F, 8.0F, 1.0F, 0.0F, false);

        bone45 = new ModelRenderer(this);
        bone45.setRotationPoint(-2.5F, 6.0F, -1.92F);
        armor.addChild(bone45);
        setRotationAngle(bone45, 0.0F, 0.0F, -0.1745F);
        bone45.setTextureOffset(18, 52).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 8.0F, 1.0F, 0.0F, false);

        bone50 = new ModelRenderer(this);
        bone50.setRotationPoint(-2.5F, 1.0F, -2.0F);
        armor.addChild(bone50);
        bone50.setTextureOffset(44, 6).addBox(-1.0F, 9.1F, -0.4F, 7.0F, 2.0F, 1.0F, 0.0F, false);

        medal = new ModelRenderer(this);
        medal.setRotationPoint(0.0F, 0.0F, 0.0F);
        armor.addChild(medal);


        rightMedal = new ModelRenderer(this);
        rightMedal.setRotationPoint(0.0F, 24.0F, 0.0F);
        medal.addChild(rightMedal);


        bone35 = new ModelRenderer(this);
        bone35.setRotationPoint(-4.0F, -21.0F, -2.5F);
        rightMedal.addChild(bone35);
        setRotationAngle(bone35, 0.0F, 0.0F, 0.6981F);
        bone35.setTextureOffset(53, 46).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, 0.0F, false);

        bone51 = new ModelRenderer(this);
        bone51.setRotationPoint(-4.0F, -16.0F, -2.8F);
        rightMedal.addChild(bone51);
        setRotationAngle(bone51, -0.0873F, 0.0F, 0.0F);
        bone51.setTextureOffset(14, 52).addBox(-1.0F, -5.0F, -0.3F, 2.0F, 10.0F, 0.0F, 0.0F, false);

        leftMedal = new ModelRenderer(this);
        leftMedal.setRotationPoint(0.0F, 24.0F, 0.0F);
        medal.addChild(leftMedal);


        bone52 = new ModelRenderer(this);
        bone52.setRotationPoint(-4.0F, -21.0F, -2.5F);
        leftMedal.addChild(bone52);
        setRotationAngle(bone52, 0.0F, 0.0F, 0.8727F);
        bone52.setTextureOffset(27, 31).addBox(4.0F, -7.0F, -0.5F, 2.0F, 2.0F, 1.0F, 0.0F, false);

        bone53 = new ModelRenderer(this);
        bone53.setRotationPoint(4.0F, -16.0F, -2.8F);
        leftMedal.addChild(bone53);
        setRotationAngle(bone53, -0.0873F, 0.0F, 0.0F);
        bone53.setTextureOffset(44, 51).addBox(-1.1F, -5.0F, -0.3F, 2.0F, 10.0F, 0.0F, 0.0F, false);

        rightLeg = new ModelRenderer(this);
        rightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);


        bone56 = new ModelRenderer(this);
        bone56.setRotationPoint(-2.6F, 1.5F, -0.5F);
        rightLeg.addChild(bone56);
        setRotationAngle(bone56, -0.7854F, 0.0F, 0.0873F);
        bone56.setTextureOffset(36, 48).addBox(-0.5F, -2.5F, -1.8F, 1.0F, 3.0F, 3.0F, 0.0F, false);

        bone57 = new ModelRenderer(this);
        bone57.setRotationPoint(-0.1F, 1.5F, 0.0F);
        rightLeg.addChild(bone57);
        setRotationAngle(bone57, 0.0F, 0.0F, 0.0873F);
        bone57.setTextureOffset(20, 9).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 1.0F, 5.0F, 0.0F, false);

        bone60 = new ModelRenderer(this);
        bone60.setRotationPoint(0.4F, 4.5F, -2.4F);
        rightLeg.addChild(bone60);
        setRotationAngle(bone60, 0.1745F, -0.1745F, 0.6981F);
        bone60.setTextureOffset(36, 43).addBox(-3.5F, 0.8F, -0.1F, 4.0F, 1.0F, 1.0F, 0.0F, false);

        bone61 = new ModelRenderer(this);
        bone61.setRotationPoint(0.4F, 4.5F, -2.4F);
        rightLeg.addChild(bone61);
        setRotationAngle(bone61, 0.1745F, -0.1745F, 0.8727F);
        bone61.setTextureOffset(0, 0).addBox(0.1F, -2.7F, -0.1F, 1.0F, 4.0F, 1.0F, 0.0F, false);

        bone58 = new ModelRenderer(this);
        bone58.setRotationPoint(-0.1F, 2.0F, -2.5F);
        rightLeg.addChild(bone58);
        setRotationAngle(bone58, 0.1745F, -0.1745F, 0.7854F);
        bone58.setTextureOffset(15, 0).addBox(-2.0F, -2.0F, -0.1F, 4.0F, 4.0F, 1.0F, 0.0F, false);

        bone59 = new ModelRenderer(this);
        bone59.setRotationPoint(-0.1F, 2.0F, -2.5F);
        rightLeg.addChild(bone59);
        setRotationAngle(bone59, 0.0873F, -0.0873F, 0.7854F);
        bone59.setTextureOffset(30, 15).addBox(0.0F, 0.0F, -0.4F, 3.0F, 3.0F, 1.0F, 0.0F, false);

        bone62 = new ModelRenderer(this);
        bone62.setRotationPoint(-0.1F, 6.5F, -2.35F);
        rightLeg.addChild(bone62);
        setRotationAngle(bone62, -0.1745F, 0.0F, 0.0F);
        bone62.setTextureOffset(0, 30).addBox(-1.0F, -1.5F, -0.14F, 2.0F, 3.0F, 0.0F, 0.0F, false);

        bone63 = new ModelRenderer(this);
        bone63.setRotationPoint(1.9F, 12.0F, 0.0F);
        rightLeg.addChild(bone63);
        bone63.setTextureOffset(14, 31).addBox(-4.5F, -10.0F, -2.3F, 5.0F, 9.0F, 3.0F, 0.0F, false);

        bone66 = new ModelRenderer(this);
        bone66.setRotationPoint(1.9F, 12.0F, 0.0F);
        rightLeg.addChild(bone66);
        bone66.setTextureOffset(48, 21).addBox(-4.5F, -7.0F, 0.7F, 5.0F, 1.0F, 2.0F, 0.0F, false);

        bone64 = new ModelRenderer(this);
        bone64.setRotationPoint(1.9F, 12.0F, 0.0F);
        rightLeg.addChild(bone64);
        bone64.setTextureOffset(0, 30).addBox(-4.3F, -11.4F, -1.7F, 3.0F, 10.0F, 4.0F, 0.0F, false);

        bone65 = new ModelRenderer(this);
        bone65.setRotationPoint(1.9F, 12.0F, 0.0F);
        rightLeg.addChild(bone65);
        bone65.setTextureOffset(0, 16).addBox(-2.7F, -11.41F, -1.71F, 3.0F, 10.0F, 4.0F, 0.0F, false);

        leftLeg = new ModelRenderer(this);
        leftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);


        bone105 = new ModelRenderer(this);
        bone105.setRotationPoint(2.6F, 1.5F, -0.5F);
        leftLeg.addChild(bone105);
        setRotationAngle(bone105, -0.7854F, 0.0F, -0.0873F);
        bone105.setTextureOffset(36, 48).addBox(-0.5F, -2.5F, -1.8F, 1.0F, 3.0F, 3.0F, 0.0F, true);

        bone106 = new ModelRenderer(this);
        bone106.setRotationPoint(0.1F, 1.5F, 0.0F);
        leftLeg.addChild(bone106);
        setRotationAngle(bone106, 0.0F, 0.0F, -0.0873F);
        bone106.setTextureOffset(20, 9).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 1.0F, 5.0F, 0.0F, true);

        bone107 = new ModelRenderer(this);
        bone107.setRotationPoint(-0.4F, 4.5F, -2.4F);
        leftLeg.addChild(bone107);
        setRotationAngle(bone107, 0.1745F, 0.1745F, -0.6981F);
        bone107.setTextureOffset(36, 43).addBox(-0.5F, 0.8F, -0.1F, 4.0F, 1.0F, 1.0F, 0.0F, true);

        bone108 = new ModelRenderer(this);
        bone108.setRotationPoint(-0.4F, 4.5F, -2.4F);
        leftLeg.addChild(bone108);
        setRotationAngle(bone108, 0.1745F, 0.1745F, -0.8727F);
        bone108.setTextureOffset(0, 0).addBox(-1.1F, -2.7F, -0.1F, 1.0F, 4.0F, 1.0F, 0.0F, true);

        bone109 = new ModelRenderer(this);
        bone109.setRotationPoint(0.1F, 2.0F, -2.5F);
        leftLeg.addChild(bone109);
        setRotationAngle(bone109, 0.1745F, 0.1745F, -0.7854F);
        bone109.setTextureOffset(15, 0).addBox(-2.0F, -2.0F, -0.1F, 4.0F, 4.0F, 1.0F, 0.0F, true);

        bone110 = new ModelRenderer(this);
        bone110.setRotationPoint(0.1F, 2.0F, -2.5F);
        leftLeg.addChild(bone110);
        setRotationAngle(bone110, 0.0873F, 0.0873F, -0.7854F);
        bone110.setTextureOffset(30, 15).addBox(-3.0F, 0.0F, -0.4F, 3.0F, 3.0F, 1.0F, 0.0F, true);

        bone111 = new ModelRenderer(this);
        bone111.setRotationPoint(0.1F, 6.5F, -2.35F);
        leftLeg.addChild(bone111);
        setRotationAngle(bone111, -0.1745F, 0.0F, 0.0F);
        bone111.setTextureOffset(0, 30).addBox(-1.0F, -1.5F, -0.14F, 2.0F, 3.0F, 0.0F, 0.0F, true);

        bone112 = new ModelRenderer(this);
        bone112.setRotationPoint(-1.9F, 12.0F, 0.0F);
        leftLeg.addChild(bone112);
        bone112.setTextureOffset(14, 31).addBox(-0.5F, -10.0F, -2.3F, 5.0F, 9.0F, 3.0F, 0.0F, true);

        bone113 = new ModelRenderer(this);
        bone113.setRotationPoint(-1.9F, 12.0F, 0.0F);
        leftLeg.addChild(bone113);
        bone113.setTextureOffset(48, 21).addBox(-0.5F, -7.0F, 0.7F, 5.0F, 1.0F, 2.0F, 0.0F, true);

        bone114 = new ModelRenderer(this);
        bone114.setRotationPoint(-1.9F, 12.0F, 0.0F);
        leftLeg.addChild(bone114);
        bone114.setTextureOffset(0, 30).addBox(1.3F, -11.4F, -1.7F, 3.0F, 10.0F, 4.0F, 0.0F, true);

        bone115 = new ModelRenderer(this);
        bone115.setRotationPoint(-1.9F, 12.0F, 0.0F);
        leftLeg.addChild(bone115);
        bone115.setTextureOffset(0, 16).addBox(-0.3F, -11.41F, -1.71F, 3.0F, 10.0F, 4.0F, 0.0F, true);

        shoeRight = new ModelRenderer(this);
        shoeRight.setRotationPoint(-1.9F, 12.0F, 0.0F);


        bone67 = new ModelRenderer(this);
        bone67.setRotationPoint(1.9F, 12.0F, 0.0F);
        shoeRight.addChild(bone67);
        bone67.setTextureOffset(20, 0).addBox(-5.0F, -2.2F, -3.0F, 6.0F, 3.0F, 6.0F, 0.0F, false);

        bone70 = new ModelRenderer(this);
        bone70.setRotationPoint(1.9F, 12.0F, 0.0F);
        shoeRight.addChild(bone70);
        setRotationAngle(bone70, 0.0873F, 0.0F, 0.0F);
        bone70.setTextureOffset(35, 15).addBox(-5.3F, -1.2F, -3.0F, 5.0F, 2.0F, 4.0F, 0.0F, false);

        bone71 = new ModelRenderer(this);
        bone71.setRotationPoint(1.9F, 12.0F, 0.0F);
        shoeRight.addChild(bone71);
        setRotationAngle(bone71, 0.0873F, 0.0F, 0.0F);
        bone71.setTextureOffset(30, 31).addBox(-3.7F, -1.19F, -3.2F, 5.0F, 2.0F, 4.0F, 0.0F, false);

        bone68 = new ModelRenderer(this);
        bone68.setRotationPoint(-0.1F, 9.5F, 2.5F);
        shoeRight.addChild(bone68);
        setRotationAngle(bone68, 0.1745F, 0.0F, 0.0F);
        bone68.setTextureOffset(0, 48).addBox(0.01F, -0.5F, -3.5F, 3.0F, 1.0F, 4.0F, 0.0F, false);

        bone69 = new ModelRenderer(this);
        bone69.setRotationPoint(-0.1F, 9.5F, 2.5F);
        shoeRight.addChild(bone69);
        setRotationAngle(bone69, 0.1745F, 0.0F, 0.0F);
        bone69.setTextureOffset(47, 27).addBox(-3.01F, -0.5F, -3.5F, 3.0F, 1.0F, 4.0F, 0.0F, false);

        bone72 = new ModelRenderer(this);
        bone72.setRotationPoint(-0.1F, 9.5F, 2.5F);
        shoeRight.addChild(bone72);
        setRotationAngle(bone72, -0.3491F, 0.0F, 0.0F);
        bone72.setTextureOffset(10, 30).addBox(-3.8F, 0.5F, -3.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);

        shoeLeft = new ModelRenderer(this);
        shoeLeft.setRotationPoint(1.9F, 12.0F, 0.0F);


        bone116 = new ModelRenderer(this);
        bone116.setRotationPoint(-1.9F, 12.0F, 0.0F);
        shoeLeft.addChild(bone116);
        bone116.setTextureOffset(20, 0).addBox(-1.0F, -2.2F, -3.0F, 6.0F, 3.0F, 6.0F, 0.0F, true);

        bone117 = new ModelRenderer(this);
        bone117.setRotationPoint(-1.9F, 12.0F, 0.0F);
        shoeLeft.addChild(bone117);
        setRotationAngle(bone117, 0.0873F, 0.0F, 0.0F);
        bone117.setTextureOffset(35, 15).addBox(0.3F, -1.2F, -3.0F, 5.0F, 2.0F, 4.0F, 0.0F, true);

        bone118 = new ModelRenderer(this);
        bone118.setRotationPoint(-1.9F, 12.0F, 0.0F);
        shoeLeft.addChild(bone118);
        setRotationAngle(bone118, 0.0873F, 0.0F, 0.0F);
        bone118.setTextureOffset(30, 31).addBox(-1.3F, -1.19F, -3.2F, 5.0F, 2.0F, 4.0F, 0.0F, true);

        bone119 = new ModelRenderer(this);
        bone119.setRotationPoint(0.1F, 9.5F, 2.5F);
        shoeLeft.addChild(bone119);
        setRotationAngle(bone119, 0.1745F, 0.0F, 0.0F);
        bone119.setTextureOffset(0, 48).addBox(-3.01F, -0.5F, -3.5F, 3.0F, 1.0F, 4.0F, 0.0F, true);

        bone120 = new ModelRenderer(this);
        bone120.setRotationPoint(0.1F, 9.5F, 2.5F);
        shoeLeft.addChild(bone120);
        setRotationAngle(bone120, 0.1745F, 0.0F, 0.0F);
        bone120.setTextureOffset(47, 27).addBox(0.01F, -0.5F, -3.5F, 3.0F, 1.0F, 4.0F, 0.0F, true);

        bone121 = new ModelRenderer(this);
        bone121.setRotationPoint(0.1F, 9.5F, 2.5F);
        shoeLeft.addChild(bone121);
        setRotationAngle(bone121, -0.3491F, 0.0F, 0.0F);
        bone121.setTextureOffset(10, 30).addBox(2.8F, 0.5F, -3.5F, 1.0F, 2.0F, 2.0F, 0.0F, true);

        helmet = new ModelRenderer(this);
        helmet.setRotationPoint(0.0F, 0.0F, 0.0F);

        bone233 = new ModelRenderer(this);
        bone233.setRotationPoint(0.0F, 0.0F, -5.0F);
        helmet.addChild(bone233);
        setRotationAngle(bone233, 0.0F, 0.2618F, 0.0F);
        bone233.setTextureOffset(22, 25).addBox(-3.0F, -2.5F, 0.0F, 3.0F, 3.0F, 0.0F, 0.01F, false);

        bone244 = new ModelRenderer(this);
        bone244.setRotationPoint(0.0F, 0.0F, -5.0F);
        helmet.addChild(bone244);
        setRotationAngle(bone244, 0.0F, -0.2618F, 0.0F);
        bone244.setTextureOffset(19, 0).addBox(0.0F, -2.5F, 0.0F, 3.0F, 3.0F, 0.0F, 0.01F, false);

        bone344 = new ModelRenderer(this);
        bone344.setRotationPoint(0.0F, 24.0F, 0.0F);
        helmet.addChild(bone344);
        bone344.setTextureOffset(0, 20).addBox(-5.0F, -31.0F, -4.3F, 10.0F, 7.0F, 1.0F, 0.01F, false);

        bone544 = new ModelRenderer(this);
        bone544.setRotationPoint(0.0F, 24.0F, 0.0F);
        helmet.addChild(bone544);
        bone544.setTextureOffset(19, 0).addBox(5.0F, -29.0F, -4.5F, 0.0F, 5.0F, 3.0F, 0.01F, false);

        bone444 = new ModelRenderer(this);
        bone444.setRotationPoint(0.0F, 24.0F, 0.0F);
        helmet.addChild(bone444);
        bone444.setTextureOffset(22, 17).addBox(-5.0F, -29.0F, -4.5F, 0.0F, 5.0F, 3.0F, 0.0F, false);

        bone611 = new ModelRenderer(this);
        bone611.setRotationPoint(-5.8F, -4.0F, 0.0F);
        helmet.addChild(bone611);
        setRotationAngle(bone611, 0.0F, 0.0F, 0.1745F);
        bone611.setTextureOffset(6, 10).addBox(0.5F, -4.0F, 4.49F, 1.0F, 8.0F, 0.0F, 0.01F, false);

        bone1322 = new ModelRenderer(this);
        bone1322.setRotationPoint(-5.8F, -4.0F, 0.0F);
        helmet.addChild(bone1322);
        setRotationAngle(bone1322, 0.0F, 0.0F, 0.1745F);
        bone1322.setTextureOffset(0, 0).addBox(0.5F, -4.0F, -4.48F, 3.0F, 8.0F, 0.0F, 0.01F, false);

        bone1111 = new ModelRenderer(this);
        bone1111.setRotationPoint(-5.8F, -4.0F, 0.0F);
        helmet.addChild(bone1111);
        setRotationAngle(bone1111, 0.0F, 0.0F, 0.1745F);
        bone1111.setTextureOffset(19, 19).addBox(0.5F, -4.0F, -4.49F, 1.0F, 8.0F, 9.0F, 0.0F, false);

        bone1923 = new ModelRenderer(this);
        bone1923.setRotationPoint(-5.8F, -4.0F, 0.0F);
        helmet.addChild(bone1923);
        setRotationAngle(bone1923, 0.0F, -0.7854F, 0.1745F);
        bone1923.setTextureOffset(30, 20).addBox(-0.9F, -1.1F, -2.19F, 1.0F, 5.0F, 1.0F, 0.01F, false);

        bone2034 = new ModelRenderer(this);
        bone2034.setRotationPoint(5.8F, -4.0F, 0.0F);
        helmet.addChild(bone2034);
        setRotationAngle(bone2034, 0.0F, 0.7854F, -0.1745F);
        bone2034.setTextureOffset(12, 28).addBox(-0.1F, -1.1F, -2.19F, 1.0F, 5.0F, 1.0F, 0.01F, false);

        bone1733 = new ModelRenderer(this);
        bone1733.setRotationPoint(-5.1F, -4.0F, -1.5F);
        helmet.addChild(bone1733);
        setRotationAngle(bone1733, -0.6981F, 0.0F, 0.1745F);
        bone1733.setTextureOffset(0, 34).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.01F, false);

        bone1821 = new ModelRenderer(this);
        bone1821.setRotationPoint(5.1F, -4.0F, -1.5F);
        helmet.addChild(bone1821);
        setRotationAngle(bone1821, -0.6981F, 0.0F, -0.1745F);
        bone1821.setTextureOffset(32, 24).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);

        bone721 = new ModelRenderer(this);
        bone721.setRotationPoint(5.8F, -4.0F, 0.0F);
        helmet.addChild(bone721);
        setRotationAngle(bone721, 0.0F, 0.0F, -0.1745F);
        bone721.setTextureOffset(19, 1).addBox(-1.5F, -4.0F, -4.49F, 1.0F, 8.0F, 9.0F, 0.01F, false);

        bone1021 = new ModelRenderer(this);
        bone1021.setRotationPoint(5.8F, -4.0F, 0.0F);
        helmet.addChild(bone1021);
        setRotationAngle(bone1021, 0.0F, 0.0F, -0.1745F);
        bone1021.setTextureOffset(6, 0).addBox(-1.5F, -4.0F, 4.49F, 1.0F, 8.0F, 0.0F, 0.01F, false);

        bone1221 = new ModelRenderer(this);
        bone1221.setRotationPoint(5.8F, -4.0F, 0.0F);
        helmet.addChild(bone1221);
        setRotationAngle(bone1221, 0.0F, 0.0F, -0.1745F);
        bone1221.setTextureOffset(0, 10).addBox(-3.5F, -4.0F, -4.48F, 3.0F, 8.0F, 0.0F, 0.01F, false);

        bone834 = new ModelRenderer(this);
        bone834.setRotationPoint(-2.5F, -7.5F, 0.02F);
        helmet.addChild(bone834);
        setRotationAngle(bone834, 0.0F, 0.0F, -0.2618F);
        bone834.setTextureOffset(0, 10).addBox(-2.3F, -0.5F, -4.5F, 5.0F, 1.0F, 9.0F, 0.01F, false);

        bone1555 = new ModelRenderer(this);
        bone1555.setRotationPoint(2.5F, -7.5F, 0.02F);
        helmet.addChild(bone1555);
        setRotationAngle(bone1555, 0.0F, 0.0F, 0.2618F);
        bone1555.setTextureOffset(0, 0).addBox(-2.7F, -0.5F, -4.5F, 5.0F, 1.0F, 9.0F, 0.01F, false);

        bone966 = new ModelRenderer(this);
        bone966.setRotationPoint(5.8F, -4.0F, 0.0F);
        helmet.addChild(bone966);
        bone966.setTextureOffset(30, 0).addBox(-10.8F, -4.0F, 3.5F, 10.0F, 8.0F, 1.0F, 0.0F, false);

        bone1477 = new ModelRenderer(this);
        bone1477.setRotationPoint(5.8F, -4.0F, 0.0F);
        helmet.addChild(bone1477);
        bone1477.setTextureOffset(30, 18).addBox(-10.8F, -4.0F, -4.5F, 10.0F, 2.0F, 0.0F, 0.01F, false);

        bone1688 = new ModelRenderer(this);
        bone1688.setRotationPoint(0.0F, -5.0F, -4.5F);
        helmet.addChild(bone1688);
        setRotationAngle(bone1688, 0.0F, 0.0F, -0.7854F);
        bone1688.setTextureOffset(0, 28).addBox(-1.5F, -4.5F, -0.01F, 6.0F, 6.0F, 0.0F, 0.01F, false);
    }

    @Override
    public void render(MatrixStack ms, IVertexBuilder buffer, int light, int overlay, float r, float g, float b, float a) {

        armor.showModel = slot == EquipmentSlotType.CHEST;
        medal.showModel = slot == EquipmentSlotType.CHEST;
        rightArm.showModel = slot == EquipmentSlotType.CHEST;
        leftArm.showModel = slot == EquipmentSlotType.CHEST;
        rightLeg.showModel = slot == EquipmentSlotType.LEGS;
        leftLeg.showModel = slot == EquipmentSlotType.LEGS;
        shoeLeft.showModel = slot == EquipmentSlotType.FEET;
        shoeRight.showModel = slot == EquipmentSlotType.FEET;
        bipedHeadwear.showModel = false;

        bipedBody = armor;
        bipedRightArm = rightArm;
        bipedLeftArm = leftArm;

        helmet.showModel = slot == EquipmentSlotType.HEAD;
        bipedHead = helmet;

        if (slot == EquipmentSlotType.LEGS) {
            bipedRightLeg = rightLeg;
            bipedLeftLeg = leftLeg;
        } else {
            bipedRightLeg = shoeRight;
            bipedLeftLeg = shoeLeft;
        }

        super.render(ms, buffer, light, overlay, r, g, b, a);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
