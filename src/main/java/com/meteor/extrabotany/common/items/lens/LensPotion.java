package com.meteor.extrabotany.common.items.lens;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.api.internal.IManaBurst;
import vazkii.botania.api.mana.BurstProperties;
import vazkii.botania.common.item.lens.Lens;

import java.util.List;

public class LensPotion extends Lens {

    @Override
    public void apply(ItemStack stack, BurstProperties props) {
        props.motionModifier *= 0.9F;
        props.maxMana *= 4;
        props.manaLossPerTick *= 4;
    }

    @Override
    public boolean collideBurst(IManaBurst burst, RayTraceResult pos, boolean isManaBlock, boolean dead, ItemStack stack) {
        ThrowableEntity entity = burst.entity();

        AxisAlignedBB axis = new AxisAlignedBB(entity.getPosX(), entity.getPosY(), entity.getPosZ(), entity.lastTickPosX,
                entity.lastTickPosY, entity.lastTickPosZ).grow(1);
        List<LivingEntity> entities = entity.world.getEntitiesWithinAABB(LivingEntity.class, axis);
        if (stack.getItem() instanceof ItemLens) {
            ItemLens lens = (ItemLens) stack.getItem();
            Brew brew = lens.getBrew(stack);
            for (LivingEntity living : entities) {
                if (!burst.isFake()) {
                    if (!entity.world.isRemote) {
                        for (EffectInstance effect : brew.getPotionEffects(stack)) {
                            EffectInstance newEffect = new EffectInstance(effect.getPotion(), effect.getDuration()/3,
                                    effect.getAmplifier(), true, true);
                                if (effect.getPotion().isInstant())
                                    effect.getPotion().affectEntity(living, living, living, newEffect.getAmplifier(),
                                            1F);
                                else
                                    living.addPotionEffect(newEffect);
                        }
                        dead = true;
                    }
                }
            }
        }
        return dead;
    }

}
