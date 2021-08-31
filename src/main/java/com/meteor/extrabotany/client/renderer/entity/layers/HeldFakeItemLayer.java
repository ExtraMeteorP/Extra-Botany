package com.meteor.extrabotany.client.renderer.entity.layers;

import com.meteor.extrabotany.common.entities.ego.EntityEGO;
import com.meteor.extrabotany.common.entities.ego.EntityEGOMinion;
import com.meteor.extrabotany.common.items.ModItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HeldFakeItemLayer<T extends LivingEntity, M extends EntityModel<T> & IHasArm> extends LayerRenderer<T, M> {

    public HeldFakeItemLayer(IEntityRenderer<T, M> p_i50926_1_) {
        super(p_i50926_1_);
    }

    @Override
    public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        boolean lvt_11_1_ = p_225628_4_.getPrimaryHand() == HandSide.RIGHT;
        ItemStack mainHand = ItemStack.EMPTY;
        ItemStack offHand = ItemStack.EMPTY;

        if(p_225628_4_ instanceof EntityEGOMinion){
            EntityEGOMinion minion = (EntityEGOMinion) p_225628_4_;
            mainHand = minion.getWeapon();
        }

        if(p_225628_4_ instanceof EntityEGO){
            EntityEGO minion = (EntityEGO) p_225628_4_;
            mainHand = minion.getWeapon();
        }

        ItemStack lvt_12_1_ = lvt_11_1_ ? offHand : mainHand;
        ItemStack lvt_13_1_ = lvt_11_1_ ? mainHand : offHand;

        if (!lvt_12_1_.isEmpty() || !lvt_13_1_.isEmpty()) {
            p_225628_1_.push();
            if (this.getEntityModel().isChild) {
                float lvt_14_1_ = 0.5F;
                p_225628_1_.translate(0.0D, 0.75D, 0.0D);
                p_225628_1_.scale(0.5F, 0.5F, 0.5F);
            }

            this.func_229135_a_(p_225628_4_, lvt_13_1_, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, HandSide.RIGHT, p_225628_1_, p_225628_2_, p_225628_3_);
            this.func_229135_a_(p_225628_4_, lvt_12_1_, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, HandSide.LEFT, p_225628_1_, p_225628_2_, p_225628_3_);
            p_225628_1_.pop();
        }
    }

    private void func_229135_a_(LivingEntity p_229135_1_, ItemStack p_229135_2_, ItemCameraTransforms.TransformType p_229135_3_, HandSide p_229135_4_, MatrixStack p_229135_5_, IRenderTypeBuffer p_229135_6_, int p_229135_7_) {
        if (!p_229135_2_.isEmpty()) {
            p_229135_5_.push();
            ((IHasArm)this.getEntityModel()).translateHand(p_229135_4_, p_229135_5_);
            p_229135_5_.rotate(Vector3f.XP.rotationDegrees(-90.0F));
            p_229135_5_.rotate(Vector3f.YP.rotationDegrees(180.0F));
            boolean lvt_8_1_ = p_229135_4_ == HandSide.LEFT;
            p_229135_5_.translate((double)((float)(lvt_8_1_ ? -1 : 1) / 16.0F), 0.125D, -0.625D);
            Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(p_229135_1_, p_229135_2_, p_229135_3_, lvt_8_1_, p_229135_5_, p_229135_6_, p_229135_7_);
            p_229135_5_.pop();
        }
    }
}
