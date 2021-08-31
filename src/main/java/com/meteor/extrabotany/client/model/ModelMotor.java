package com.meteor.extrabotany.client.model;

import com.meteor.extrabotany.common.entities.mountable.EntityMotor;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelMotor extends EntityModel {

    private final ModelRenderer all;
    public ModelRenderer tirefront3;
    private final ModelRenderer bone9;
    private final ModelRenderer bone10;
    private final ModelRenderer bone11;
    private final ModelRenderer bone12;
    private final ModelRenderer bone13;
    private final ModelRenderer bone14;
    private final ModelRenderer bone15;
    private final ModelRenderer side1;
    private final ModelRenderer bone18;
    private final ModelRenderer bone19;
    private final ModelRenderer bone21;
    private final ModelRenderer bone25;
    private final ModelRenderer bone29;
    private final ModelRenderer bone31;
    private final ModelRenderer bone30;
    private final ModelRenderer bone41;
    private final ModelRenderer bone43;
    private final ModelRenderer bone47;
    private final ModelRenderer bone48;
    private final ModelRenderer centerfront;
    private final ModelRenderer bone49;
    private final ModelRenderer bone24;
    private final ModelRenderer bone26;
    private final ModelRenderer bone27;
    private final ModelRenderer bone28;
    private final ModelRenderer bone32;
    private final ModelRenderer bone33;
    private final ModelRenderer bone46;
    private final ModelRenderer bone39;
    private final ModelRenderer bone45;
    private final ModelRenderer bone44;
    private final ModelRenderer bone40;
    private final ModelRenderer bone50;
    private final ModelRenderer centerback;
    private final ModelRenderer bone35;
    private final ModelRenderer bone34;
    private final ModelRenderer bone42;
    private final ModelRenderer bone36;
    private final ModelRenderer bone2;
    private final ModelRenderer bone;
    private final ModelRenderer bone37;
    private final ModelRenderer bone38;
    public ModelRenderer tireback;
    private final ModelRenderer bone6;
    private final ModelRenderer bone16;
    private final ModelRenderer bone17;
    private final ModelRenderer bone20;
    private final ModelRenderer bone22;
    private final ModelRenderer bone23;
    private final ModelRenderer bone62;
    private final ModelRenderer side4;
    private final ModelRenderer bone69;
    private final ModelRenderer bone70;
    private final ModelRenderer bone71;
    private final ModelRenderer bone72;
    private final ModelRenderer bone73;
    private final ModelRenderer bone74;
    private final ModelRenderer bone75;
    private final ModelRenderer bone76;
    private final ModelRenderer bone77;
    private final ModelRenderer bone78;
    private final ModelRenderer bone79;

    public ModelMotor() {
        textureWidth = 128;
        textureHeight = 128;

        all = new ModelRenderer(this);
        all.setRotationPoint(0.0F, 19.0F, 16.0F);


        tirefront3 = new ModelRenderer(this);
        tirefront3.setRotationPoint(0.0F, 0.0F, -33.0F);
        all.addChild(tirefront3);


        bone9 = new ModelRenderer(this);
        bone9.setRotationPoint(0.0F, 5.0F, 12.0F);
        tirefront3.addChild(bone9);


        bone10 = new ModelRenderer(this);
        bone10.setRotationPoint(0.0F, 0.0F, 12.0F);
        tirefront3.addChild(bone10);
        bone10.setTextureOffset(18, 51).addBox(2.01F, -2.0F, -14.0F, 1.0F, 4.0F, 4.0F, 0.0F, false);

        bone11 = new ModelRenderer(this);
        bone11.setRotationPoint(0.0F, 0.0F, 12.0F);
        tirefront3.addChild(bone11);
        bone11.setTextureOffset(0, 51).addBox(-3.01F, -2.0F, -14.0F, 1.0F, 4.0F, 4.0F, 0.0F, false);

        bone12 = new ModelRenderer(this);
        bone12.setRotationPoint(0.0F, 0.0F, 12.0F);
        tirefront3.addChild(bone12);
        bone12.setTextureOffset(68, 96).addBox(-3.0F, -5.0F, -14.0F, 6.0F, 10.0F, 4.0F, 0.0F, false);

        bone13 = new ModelRenderer(this);
        bone13.setRotationPoint(0.0F, 0.0F, 12.0F);
        tirefront3.addChild(bone13);
        setRotationAngle(bone13, -0.7854F, 0.0F, 0.0F);
        bone13.setTextureOffset(48, 92).addBox(-3.0F, 3.4853F, -10.4853F, 6.0F, 10.0F, 4.0F, 0.0F, false);

        bone14 = new ModelRenderer(this);
        bone14.setRotationPoint(0.0F, 0.0F, 12.0F);
        tirefront3.addChild(bone14);
        setRotationAngle(bone14, -1.5708F, 0.0F, 0.0F);
        bone14.setTextureOffset(90, 45).addBox(-3.0F, 7.0F, -2.0F, 6.0F, 10.0F, 4.0F, 0.0F, false);

        bone15 = new ModelRenderer(this);
        bone15.setRotationPoint(0.0F, 0.0F, 12.0F);
        tirefront3.addChild(bone15);
        setRotationAngle(bone15, 0.7854F, 0.0F, 0.0F);
        bone15.setTextureOffset(0, 0).addBox(-3.0F, -13.4853F, -10.4853F, 6.0F, 10.0F, 4.0F, 0.0F, false);

        side1 = new ModelRenderer(this);
        side1.setRotationPoint(1.0F, 5.0F, -16.0F);
        all.addChild(side1);


        bone18 = new ModelRenderer(this);
        bone18.setRotationPoint(0.0F, 0.0F, 0.0F);
        side1.addChild(bone18);


        bone19 = new ModelRenderer(this);
        bone19.setRotationPoint(4.0F, -5.0F, -15.0F);
        side1.addChild(bone19);
        setRotationAngle(bone19, -0.7854F, 0.0F, 0.0F);
        bone19.setTextureOffset(50, 27).addBox(-1.5F, -1.0F, -3.0F, 1.0F, 4.0F, 4.0F, 0.0F, false);

        bone21 = new ModelRenderer(this);
        bone21.setRotationPoint(0.0F, 0.0F, 0.0F);
        side1.addChild(bone21);
        bone21.setTextureOffset(46, 38).addBox(3.0F, -7.5F, -16.0F, 1.0F, 2.0F, 16.0F, 0.0F, false);
        bone21.setTextureOffset(18, 45).addBox(3.0F, -4.5F, -16.0F, 1.0F, 2.0F, 16.0F, 0.0F, false);
        bone21.setTextureOffset(33, 0).addBox(3.5F, -6.0F, -17.0F, 1.0F, 2.0F, 18.0F, 0.0F, false);

        bone25 = new ModelRenderer(this);
        bone25.setRotationPoint(4.0F, -5.0F, 0.0F);
        side1.addChild(bone25);
        setRotationAngle(bone25, -0.7854F, 0.0F, 0.0F);
        bone25.setTextureOffset(24, 47).addBox(-1.5F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, 0.0F, false);

        bone29 = new ModelRenderer(this);
        bone29.setRotationPoint(0.0F, 0.0F, 0.0F);
        side1.addChild(bone29);
        setRotationAngle(bone29, 0.1745F, 0.0F, 0.0F);
        bone29.setTextureOffset(66, 32).addBox(2.0F, -9.0F, -11.0F, 1.0F, 5.0F, 12.0F, 0.0F, false);

        bone31 = new ModelRenderer(this);
        bone31.setRotationPoint(0.0F, 0.0F, 0.0F);
        side1.addChild(bone31);
        setRotationAngle(bone31, -0.1745F, 0.0F, 0.0F);
        bone31.setTextureOffset(59, 75).addBox(1.99F, -7.0F, -11.0F, 1.0F, 6.0F, 11.0F, 0.0F, false);

        bone30 = new ModelRenderer(this);
        bone30.setRotationPoint(0.0F, 0.0F, 0.0F);
        side1.addChild(bone30);


        bone41 = new ModelRenderer(this);
        bone41.setRotationPoint(0.0F, 0.0F, 0.0F);
        side1.addChild(bone41);
        setRotationAngle(bone41, -0.0873F, 0.0F, 0.0F);
        bone41.setTextureOffset(0, 90).addBox(2.0F, -7.0F, 6.0F, 1.0F, 2.0F, 10.0F, 0.0F, false);

        bone43 = new ModelRenderer(this);
        bone43.setRotationPoint(0.0F, 0.0F, 0.0F);
        side1.addChild(bone43);
        setRotationAngle(bone43, 0.7854F, -0.0873F, 0.0F);
        bone43.setTextureOffset(26, 26).addBox(2.5F, 0.0F, 9.0F, 1.0F, 2.0F, 7.0F, 0.0F, false);

        bone47 = new ModelRenderer(this);
        bone47.setRotationPoint(-1.0F, 0.0F, 0.0F);
        side1.addChild(bone47);
        setRotationAngle(bone47, 0.1745F, 0.0F, 0.0F);
        bone47.setTextureOffset(100, 79).addBox(2.5F, -10.0F, -10.0F, 1.0F, 2.0F, 9.0F, 0.0F, false);

        bone48 = new ModelRenderer(this);
        bone48.setRotationPoint(-1.0F, 0.0F, 0.0F);
        side1.addChild(bone48);
        setRotationAngle(bone48, 0.1745F, -0.0873F, 0.0F);
        bone48.setTextureOffset(26, 89).addBox(1.5F, -11.0F, -11.0F, 1.0F, 2.0F, 10.0F, 0.0F, false);

        centerfront = new ModelRenderer(this);
        centerfront.setRotationPoint(0.0F, 5.0F, -16.0F);
        all.addChild(centerfront);
        centerfront.setTextureOffset(0, 32).addBox(-3.0F, -11.5F, -17.0F, 6.0F, 1.0F, 2.0F, 0.0F, false);
        centerfront.setTextureOffset(36, 45).addBox(-3.0F, -11.5F, 13.0F, 6.0F, 1.0F, 6.0F, 0.0F, false);
        centerfront.setTextureOffset(0, 0).addBox(-3.0F, -5.0F, -11.0F, 6.0F, 2.0F, 21.0F, 0.0F, false);
        centerfront.setTextureOffset(44, 38).addBox(-4.0F, -5.5F, -17.5F, 8.0F, 1.0F, 1.0F, 0.0F, false);
        centerfront.setTextureOffset(0, 35).addBox(-3.5F, -5.5F, 15.5F, 7.0F, 1.0F, 1.0F, 0.0F, false);

        bone49 = new ModelRenderer(this);
        bone49.setRotationPoint(0.0F, 0.0F, 0.0F);
        centerfront.addChild(bone49);
        setRotationAngle(bone49, -0.0873F, 0.0F, 0.0F);
        bone49.setTextureOffset(26, 23).addBox(-5.0F, -10.5F, -9.5F, 10.0F, 1.0F, 1.0F, 0.0F, false);

        bone24 = new ModelRenderer(this);
        bone24.setRotationPoint(3.0F, -7.0F, -12.0F);
        centerfront.addChild(bone24);
        setRotationAngle(bone24, -0.7854F, 0.0F, 0.0F);
        bone24.setTextureOffset(80, 32).addBox(-6.0F, -1.5F, -5.0F, 6.0F, 1.0F, 8.0F, 0.0F, false);

        bone26 = new ModelRenderer(this);
        bone26.setRotationPoint(0.0F, 0.0F, 0.0F);
        centerfront.addChild(bone26);
        setRotationAngle(bone26, 0.4363F, 0.0F, 0.0F);
        bone26.setTextureOffset(81, 11).addBox(-3.0F, -14.0F, -7.5F, 6.0F, 3.0F, 7.0F, 0.0F, false);

        bone27 = new ModelRenderer(this);
        bone27.setRotationPoint(0.0F, 0.0F, 0.0F);
        centerfront.addChild(bone27);
        setRotationAngle(bone27, -0.1745F, 0.0F, 0.0F);
        bone27.setTextureOffset(102, 35).addBox(-2.5F, -10.0F, -9.0F, 5.0F, 3.0F, 6.0F, 0.0F, false);

        bone28 = new ModelRenderer(this);
        bone28.setRotationPoint(0.0F, 0.0F, 0.0F);
        centerfront.addChild(bone28);
        bone28.setTextureOffset(0, 23).addBox(-2.5F, -9.0F, -11.0F, 5.0F, 4.0F, 16.0F, 0.0F, false);
        bone28.setTextureOffset(48, 75).addBox(-2.0F, -9.5F, -2.0F, 4.0F, 1.0F, 7.0F, 0.0F, false);

        bone32 = new ModelRenderer(this);
        bone32.setRotationPoint(0.0F, 0.0F, 0.0F);
        centerfront.addChild(bone32);
        setRotationAngle(bone32, -0.2618F, 0.0F, 0.0F);
        bone32.setTextureOffset(85, 77).addBox(-3.0F, -11.0F, 2.0F, 6.0F, 5.0F, 6.0F, 0.0F, false);

        bone33 = new ModelRenderer(this);
        bone33.setRotationPoint(0.0F, 0.0F, 0.0F);
        centerfront.addChild(bone33);
        setRotationAngle(bone33, 0.4363F, 0.0F, 0.0F);
        bone33.setTextureOffset(0, 63).addBox(-2.5F, -7.0F, 8.0F, 5.0F, 3.0F, 11.0F, 0.0F, false);

        bone46 = new ModelRenderer(this);
        bone46.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone33.addChild(bone46);
        setRotationAngle(bone46, 0.0873F, 0.0F, 0.0F);
        bone46.setTextureOffset(0, 77).addBox(2.0F, -4.5F, 12.0F, 1.0F, 2.0F, 11.0F, 0.0F, false);
        bone46.setTextureOffset(77, 49).addBox(-3.0F, -4.5F, 12.0F, 1.0F, 2.0F, 11.0F, 0.0F, false);

        bone39 = new ModelRenderer(this);
        bone39.setRotationPoint(0.0F, 0.0F, 0.0F);
        centerfront.addChild(bone39);
        setRotationAngle(bone39, 0.6109F, 0.0F, 0.0F);


        bone45 = new ModelRenderer(this);
        bone45.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone39.addChild(bone45);
        setRotationAngle(bone45, 0.0F, 0.0524F, 0.0F);
        bone45.setTextureOffset(79, 64).addBox(-3.0F, -4.5F, 11.0F, 1.0F, 2.0F, 11.0F, 0.0F, false);

        bone44 = new ModelRenderer(this);
        bone44.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone39.addChild(bone44);
        setRotationAngle(bone44, 0.0F, -0.0524F, 0.0F);
        bone44.setTextureOffset(72, 81).addBox(2.0F, -4.5F, 11.0F, 1.0F, 2.0F, 11.0F, 0.0F, false);

        bone40 = new ModelRenderer(this);
        bone40.setRotationPoint(0.0F, 0.0F, 0.0F);
        centerfront.addChild(bone40);
        setRotationAngle(bone40, 0.7854F, 0.0F, 0.0F);
        bone40.setTextureOffset(72, 0).addBox(-3.0F, 0.0F, 9.0F, 6.0F, 2.0F, 9.0F, 0.0F, false);

        bone50 = new ModelRenderer(this);
        bone50.setRotationPoint(0.0F, 0.0F, 0.0F);
        centerfront.addChild(bone50);
        setRotationAngle(bone50, -0.0873F, 0.0F, 0.0F);
        bone50.setTextureOffset(0, 14).addBox(-2.5F, -11.0F, -12.0F, 5.0F, 2.0F, 4.0F, 0.0F, false);

        centerback = new ModelRenderer(this);
        centerback.setRotationPoint(0.0F, 5.0F, -16.0F);
        all.addChild(centerback);


        bone35 = new ModelRenderer(this);
        bone35.setRotationPoint(0.0F, 0.0F, 0.0F);
        centerback.addChild(bone35);
        setRotationAngle(bone35, 0.0F, -0.7854F, 0.0F);
        bone35.setTextureOffset(0, 43).addBox(3.0F, -4.0F, 4.0F, 2.0F, 2.0F, 6.0F, 0.0F, false);

        bone34 = new ModelRenderer(this);
        bone34.setRotationPoint(0.0F, 0.0F, 0.0F);
        centerback.addChild(bone34);
        setRotationAngle(bone34, 0.0F, 0.7854F, 0.0F);
        bone34.setTextureOffset(33, 9).addBox(-5.0F, -4.0F, 4.0F, 2.0F, 2.0F, 6.0F, 0.0F, false);

        bone42 = new ModelRenderer(this);
        bone42.setRotationPoint(0.0F, 0.0F, 0.0F);
        centerback.addChild(bone42);
        setRotationAngle(bone42, -0.7854F, 0.0F, 0.0F);
        bone42.setTextureOffset(43, 9).addBox(-5.0F, -9.0F, 3.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);
        bone42.setTextureOffset(52, 21).addBox(3.0F, -9.0F, 3.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);

        bone36 = new ModelRenderer(this);
        bone36.setRotationPoint(0.0F, 0.0F, 0.0F);
        centerback.addChild(bone36);
        setRotationAngle(bone36, 0.0873F, 0.0F, 0.0F);
        bone36.setTextureOffset(34, 35).addBox(-6.0F, -3.5F, 21.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        bone36.setTextureOffset(26, 35).addBox(4.0F, -3.5F, 21.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        bone2 = new ModelRenderer(this);
        bone2.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone36.addChild(bone2);
        bone2.setTextureOffset(58, 59).addBox(-6.51F, -4.0F, 9.0F, 3.0F, 3.0F, 13.0F, 0.0F, false);

        bone = new ModelRenderer(this);
        bone.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone36.addChild(bone);
        bone.setTextureOffset(62, 16).addBox(3.51F, -4.0F, 9.0F, 3.0F, 3.0F, 13.0F, 0.0F, false);

        bone37 = new ModelRenderer(this);
        bone37.setRotationPoint(0.0F, 0.0F, 0.0F);
        centerback.addChild(bone37);
        setRotationAngle(bone37, 0.5236F, 0.0F, 0.0F);
        bone37.setTextureOffset(39, 56).addBox(3.5F, 0.0F, 9.0F, 3.0F, 3.0F, 13.0F, 0.0F, false);
        bone37.setTextureOffset(53, 8).addBox(4.0F, 0.5F, 21.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        bone37.setTextureOffset(6, 51).addBox(-6.0F, 0.5F, 21.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        bone37.setTextureOffset(53, 0).addBox(-6.5F, 0.0F, 9.0F, 3.0F, 3.0F, 13.0F, 0.0F, false);

        bone38 = new ModelRenderer(this);
        bone38.setRotationPoint(0.0F, 0.0F, 0.0F);
        centerback.addChild(bone38);
        setRotationAngle(bone38, -0.3491F, 0.0F, 0.0F);
        bone38.setTextureOffset(33, 0).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 2.0F, 7.0F, 0.0F, false);

        tireback = new ModelRenderer(this);
        tireback.setRotationPoint(0.0F, 0.0F, 0.0F);
        all.addChild(tireback);


        bone6 = new ModelRenderer(this);
        bone6.setRotationPoint(0.0F, 5.0F, 12.0F);
        tireback.addChild(bone6);


        bone16 = new ModelRenderer(this);
        bone16.setRotationPoint(0.0F, 0.0F, 12.0F);
        tireback.addChild(bone16);
        bone16.setTextureOffset(18, 51).addBox(2.01F, -2.0F, -14.0F, 1.0F, 4.0F, 4.0F, 0.0F, false);

        bone17 = new ModelRenderer(this);
        bone17.setRotationPoint(0.0F, 0.0F, 12.0F);
        tireback.addChild(bone17);
        bone17.setTextureOffset(0, 51).addBox(-3.01F, -2.0F, -14.0F, 1.0F, 4.0F, 4.0F, 0.0F, false);

        bone20 = new ModelRenderer(this);
        bone20.setRotationPoint(0.0F, 0.0F, 12.0F);
        tireback.addChild(bone20);
        bone20.setTextureOffset(68, 96).addBox(-3.0F, -5.0F, -14.0F, 6.0F, 10.0F, 4.0F, 0.0F, false);

        bone22 = new ModelRenderer(this);
        bone22.setRotationPoint(0.0F, 0.0F, 12.0F);
        tireback.addChild(bone22);
        setRotationAngle(bone22, -0.7854F, 0.0F, 0.0F);
        bone22.setTextureOffset(48, 92).addBox(-3.0F, 3.4853F, -10.4853F, 6.0F, 10.0F, 4.0F, 0.0F, false);

        bone23 = new ModelRenderer(this);
        bone23.setRotationPoint(0.0F, 0.0F, 12.0F);
        tireback.addChild(bone23);
        setRotationAngle(bone23, -1.5708F, 0.0F, 0.0F);
        bone23.setTextureOffset(90, 45).addBox(-3.0F, 7.0F, -2.0F, 6.0F, 10.0F, 4.0F, 0.0F, false);

        bone62 = new ModelRenderer(this);
        bone62.setRotationPoint(0.0F, 0.0F, 12.0F);
        tireback.addChild(bone62);
        setRotationAngle(bone62, 0.7854F, 0.0F, 0.0F);
        bone62.setTextureOffset(0, 0).addBox(-3.0F, -13.4853F, -10.4853F, 6.0F, 10.0F, 4.0F, 0.0F, false);

        side4 = new ModelRenderer(this);
        side4.setRotationPoint(-1.0F, 5.0F, -16.0F);
        all.addChild(side4);


        bone69 = new ModelRenderer(this);
        bone69.setRotationPoint(0.0F, 0.0F, 0.0F);
        side4.addChild(bone69);


        bone70 = new ModelRenderer(this);
        bone70.setRotationPoint(-4.0F, -5.0F, -15.0F);
        side4.addChild(bone70);
        setRotationAngle(bone70, -0.7854F, 0.0F, 0.0F);
        bone70.setTextureOffset(50, 27).addBox(0.5F, -1.0F, -3.0F, 1.0F, 4.0F, 4.0F, 0.0F, true);

        bone71 = new ModelRenderer(this);
        bone71.setRotationPoint(0.0F, 0.0F, 0.0F);
        side4.addChild(bone71);
        bone71.setTextureOffset(46, 38).addBox(-4.0F, -7.5F, -16.0F, 1.0F, 2.0F, 16.0F, 0.0F, true);
        bone71.setTextureOffset(18, 45).addBox(-4.0F, -4.5F, -16.0F, 1.0F, 2.0F, 16.0F, 0.0F, true);
        bone71.setTextureOffset(33, 0).addBox(-4.5F, -6.0F, -17.0F, 1.0F, 2.0F, 18.0F, 0.0F, true);

        bone72 = new ModelRenderer(this);
        bone72.setRotationPoint(-4.0F, -5.0F, 0.0F);
        side4.addChild(bone72);
        setRotationAngle(bone72, -0.7854F, 0.0F, 0.0F);
        bone72.setTextureOffset(24, 47).addBox(0.5F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, 0.0F, true);

        bone73 = new ModelRenderer(this);
        bone73.setRotationPoint(0.0F, 0.0F, 0.0F);
        side4.addChild(bone73);
        setRotationAngle(bone73, 0.1745F, 0.0F, 0.0F);
        bone73.setTextureOffset(66, 32).addBox(-3.0F, -9.0F, -11.0F, 1.0F, 5.0F, 12.0F, 0.0F, true);

        bone74 = new ModelRenderer(this);
        bone74.setRotationPoint(0.0F, 0.0F, 0.0F);
        side4.addChild(bone74);
        setRotationAngle(bone74, -0.1745F, 0.0F, 0.0F);
        bone74.setTextureOffset(59, 75).addBox(-2.99F, -7.0F, -11.0F, 1.0F, 6.0F, 11.0F, 0.0F, true);

        bone75 = new ModelRenderer(this);
        bone75.setRotationPoint(0.0F, 0.0F, 0.0F);
        side4.addChild(bone75);


        bone76 = new ModelRenderer(this);
        bone76.setRotationPoint(0.0F, 0.0F, 0.0F);
        side4.addChild(bone76);
        setRotationAngle(bone76, -0.0873F, 0.0F, 0.0F);
        bone76.setTextureOffset(0, 90).addBox(-3.0F, -7.0F, 6.0F, 1.0F, 2.0F, 10.0F, 0.0F, true);

        bone77 = new ModelRenderer(this);
        bone77.setRotationPoint(0.0F, 0.0F, 0.0F);
        side4.addChild(bone77);
        setRotationAngle(bone77, 0.7854F, 0.0873F, 0.0F);
        bone77.setTextureOffset(26, 26).addBox(-3.5F, 0.0F, 9.0F, 1.0F, 2.0F, 7.0F, 0.0F, true);

        bone78 = new ModelRenderer(this);
        bone78.setRotationPoint(1.0F, 0.0F, 0.0F);
        side4.addChild(bone78);
        setRotationAngle(bone78, 0.1745F, 0.0F, 0.0F);
        bone78.setTextureOffset(100, 79).addBox(-3.5F, -10.0F, -10.0F, 1.0F, 2.0F, 9.0F, 0.0F, true);

        bone79 = new ModelRenderer(this);
        bone79.setRotationPoint(1.0F, 0.0F, 0.0F);
        side4.addChild(bone79);
        setRotationAngle(bone79, 0.1745F, 0.0873F, 0.0F);
        bone79.setTextureOffset(26, 89).addBox(-2.5F, -11.0F, -11.0F, 1.0F, 2.0F, 10.0F, 0.0F, true);
    }

    @Override
    public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
        if(entity instanceof EntityMotor) {
            EntityMotor motor = (EntityMotor) entity;
            if(motor.getMotion().length() > 0) {
                tirefront3.rotateAngleX = MathHelper.wrapDegrees(40 * motor.ridingTicks);
                tireback.rotateAngleX = MathHelper.wrapDegrees(40 * motor.ridingTicks);
            }
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        all.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
