package com.meteor.extrabotany.common.potions;

import com.meteor.extrabotany.common.handler.FlamescionHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

import javax.annotation.Nonnull;

public class PotionIncandescence extends Effect {

    public PotionIncandescence() {
        super(EffectType.BENEFICIAL, 0xDC143C);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public void performEffect(@Nonnull LivingEntity living, int amplified) {
        if(living.getMotion().y < 0)
            living.setMotion(living.getMotion().mul(1D, 0.05D, 1D));
        if(living instanceof PlayerEntity && !FlamescionHandler.isFlamescionMode((PlayerEntity) living))
            living.removePotionEffect(this);
    }

}
