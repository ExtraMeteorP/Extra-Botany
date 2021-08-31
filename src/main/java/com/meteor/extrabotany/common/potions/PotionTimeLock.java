package com.meteor.extrabotany.common.potions;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

import javax.annotation.Nonnull;

public class PotionTimeLock extends Effect {

    public PotionTimeLock() {
        super(EffectType.HARMFUL, 0xFFD700);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public void performEffect(@Nonnull LivingEntity living, int amplified) {
        if(living.getMotion().y < 0)
            living.setMotion(living.getMotion().scale(0.03D));
    }

}
