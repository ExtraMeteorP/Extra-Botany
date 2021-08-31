package com.meteor.extrabotany.client.model.armor;

import com.meteor.extrabotany.client.model.ModelArmor;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;

public class ModelShootingGuardianHelmet extends ModelArmor {

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

    public ModelShootingGuardianHelmet(EquipmentSlotType slot) {
        super(slot);

        textureWidth = 64;
        textureHeight = 64;

        helmet = new ModelRenderer(this);
        helmet.setRotationPoint(0.0F, 24.0F, 0.0F);


        bone1688 = new ModelRenderer(this);
        bone1688.setRotationPoint(0.0F, -6.0F, -4.5F);
        helmet.addChild(bone1688);
        setRotationAngle(bone1688, 0.0F, 0.0F, -0.7854F);
        bone1688.setTextureOffset(0, 28).addBox(-1.5F, -4.5F, -0.01F, 6.0F, 6.0F, 0.0F, 0.0F, false);

        bone1477 = new ModelRenderer(this);
        bone1477.setRotationPoint(5.8F, -5.0F, 0.0F);
        helmet.addChild(bone1477);
        bone1477.setTextureOffset(30, 18).addBox(-10.8F, -4.0F, -4.5F, 10.0F, 2.0F, 0.0F, 0.0F, false);

        bone966 = new ModelRenderer(this);
        bone966.setRotationPoint(5.8F, -5.0F, 0.0F);
        helmet.addChild(bone966);
        bone966.setTextureOffset(30, 0).addBox(-10.8F, -4.0F, 3.5F, 10.0F, 8.0F, 1.0F, 0.0F, false);

        bone1555 = new ModelRenderer(this);
        bone1555.setRotationPoint(2.5F, -8.5F, 0.02F);
        helmet.addChild(bone1555);
        setRotationAngle(bone1555, 0.0F, 0.0F, 0.2618F);
        bone1555.setTextureOffset(0, 0).addBox(-2.7F, -0.5F, -4.5F, 5.0F, 1.0F, 9.0F, 0.0F, false);

        bone834 = new ModelRenderer(this);
        bone834.setRotationPoint(-2.5F, -8.5F, 0.02F);
        helmet.addChild(bone834);
        setRotationAngle(bone834, 0.0F, 0.0F, -0.2618F);
        bone834.setTextureOffset(0, 10).addBox(-2.3F, -0.5F, -4.5F, 5.0F, 1.0F, 9.0F, 0.0F, false);

        bone1221 = new ModelRenderer(this);
        bone1221.setRotationPoint(5.8F, -5.0F, 0.0F);
        helmet.addChild(bone1221);
        setRotationAngle(bone1221, 0.0F, 0.0F, -0.1745F);
        bone1221.setTextureOffset(0, 10).addBox(-3.5F, -4.0F, -4.48F, 3.0F, 8.0F, 0.0F, 0.0F, false);

        bone1021 = new ModelRenderer(this);
        bone1021.setRotationPoint(5.8F, -5.0F, 0.0F);
        helmet.addChild(bone1021);
        setRotationAngle(bone1021, 0.0F, 0.0F, -0.1745F);
        bone1021.setTextureOffset(6, 0).addBox(-1.5F, -4.0F, 4.49F, 1.0F, 8.0F, 0.0F, 0.0F, false);

        bone721 = new ModelRenderer(this);
        bone721.setRotationPoint(5.8F, -5.0F, 0.0F);
        helmet.addChild(bone721);
        setRotationAngle(bone721, 0.0F, 0.0F, -0.1745F);
        bone721.setTextureOffset(19, 1).addBox(-1.5F, -4.0F, -4.49F, 1.0F, 8.0F, 9.0F, 0.0F, false);

        bone1821 = new ModelRenderer(this);
        bone1821.setRotationPoint(5.1F, -5.0F, -1.5F);
        helmet.addChild(bone1821);
        setRotationAngle(bone1821, -0.6981F, 0.0F, -0.1745F);
        bone1821.setTextureOffset(32, 24).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);

        bone1733 = new ModelRenderer(this);
        bone1733.setRotationPoint(-5.1F, -5.0F, -1.5F);
        helmet.addChild(bone1733);
        setRotationAngle(bone1733, -0.6981F, 0.0F, 0.1745F);
        bone1733.setTextureOffset(0, 34).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);

        bone2034 = new ModelRenderer(this);
        bone2034.setRotationPoint(5.8F, -5.0F, 0.0F);
        helmet.addChild(bone2034);
        setRotationAngle(bone2034, 0.0F, 0.7854F, -0.1745F);
        bone2034.setTextureOffset(12, 28).addBox(-0.1F, -1.1F, -2.19F, 1.0F, 5.0F, 1.0F, 0.0F, false);

        bone1923 = new ModelRenderer(this);
        bone1923.setRotationPoint(-5.8F, -5.0F, 0.0F);
        helmet.addChild(bone1923);
        setRotationAngle(bone1923, 0.0F, -0.7854F, 0.1745F);
        bone1923.setTextureOffset(30, 20).addBox(-0.9F, -1.1F, -2.19F, 1.0F, 5.0F, 1.0F, 0.0F, false);

        bone1111 = new ModelRenderer(this);
        bone1111.setRotationPoint(-5.8F, -5.0F, 0.0F);
        helmet.addChild(bone1111);
        setRotationAngle(bone1111, 0.0F, 0.0F, 0.1745F);
        bone1111.setTextureOffset(19, 19).addBox(0.5F, -4.0F, -4.49F, 1.0F, 8.0F, 9.0F, 0.0F, false);

        bone1322 = new ModelRenderer(this);
        bone1322.setRotationPoint(-5.8F, -5.0F, 0.0F);
        helmet.addChild(bone1322);
        setRotationAngle(bone1322, 0.0F, 0.0F, 0.1745F);
        bone1322.setTextureOffset(0, 0).addBox(0.5F, -4.0F, -4.48F, 3.0F, 8.0F, 0.0F, 0.0F, false);

        bone611 = new ModelRenderer(this);
        bone611.setRotationPoint(-5.8F, -5.0F, 0.0F);
        helmet.addChild(bone611);
        setRotationAngle(bone611, 0.0F, 0.0F, 0.1745F);
        bone611.setTextureOffset(6, 10).addBox(0.5F, -4.0F, 4.49F, 1.0F, 8.0F, 0.0F, 0.0F, false);

        bone444 = new ModelRenderer(this);
        bone444.setRotationPoint(0.0F, 23.0F, 0.0F);
        helmet.addChild(bone444);
        bone444.setTextureOffset(22, 17).addBox(-5.0F, -29.0F, -4.5F, 0.0F, 5.0F, 3.0F, 0.0F, false);

        bone544 = new ModelRenderer(this);
        bone544.setRotationPoint(0.0F, 23.0F, 0.0F);
        helmet.addChild(bone544);
        bone544.setTextureOffset(19, 0).addBox(5.0F, -29.0F, -4.5F, 0.0F, 5.0F, 3.0F, 0.0F, false);

        bone344 = new ModelRenderer(this);
        bone344.setRotationPoint(0.0F, 23.0F, 0.0F);
        helmet.addChild(bone344);
        bone344.setTextureOffset(0, 20).addBox(-5.0F, -31.0F, -4.3F, 10.0F, 7.0F, 1.0F, 0.0F, false);

        bone244 = new ModelRenderer(this);
        bone244.setRotationPoint(0.0F, -1.0F, -5.0F);
        helmet.addChild(bone244);
        setRotationAngle(bone244, 0.0F, -0.2618F, 0.0F);
        bone244.setTextureOffset(19, 0).addBox(0.0F, -2.5F, 0.0F, 3.0F, 3.0F, 0.0F, 0.0F, false);

        bone233 = new ModelRenderer(this);
        bone233.setRotationPoint(0.0F, -1.0F, -5.0F);
        helmet.addChild(bone233);
        setRotationAngle(bone233, 0.0F, 0.2618F, 0.0F);
        bone233.setTextureOffset(22, 25).addBox(-3.0F, -2.5F, 0.0F, 3.0F, 3.0F, 0.0F, 0.0F, false);
    }

    @Override
    public void render(MatrixStack ms, IVertexBuilder buffer, int light, int overlay, float r, float g, float b, float a) {
        bipedHeadwear.showModel = false;
        helmet.showModel = slot == EquipmentSlotType.HEAD;
        bipedHead = helmet;
        super.render(ms, buffer, light, overlay, r, g, b, a);
    }

        public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

}
