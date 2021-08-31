package com.meteor.extrabotany.common.items.lens;

import com.meteor.extrabotany.common.handler.DamageHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import vazkii.botania.api.internal.IManaBurst;
import vazkii.botania.api.mana.BurstProperties;
import vazkii.botania.common.item.lens.Lens;

import java.util.List;

public class LensSuperconductor extends Lens {

    @Override
    public void apply(ItemStack stack, BurstProperties props) {
        props.maxMana *=8F;
        props.motionModifier *= 1.5F;
        props.manaLossPerTick *= 16F;
        props.ticksBeforeManaLoss *= 0.8F;
    }

    @Override
    public void updateBurst(IManaBurst burst, ItemStack stack) {
        ThrowableEntity entity = burst.entity();
        if (entity.world.isRemote)
            return;

        World world = entity.world;
        AxisAlignedBB axis = new AxisAlignedBB(entity.getPosX()-0.5F, entity.getPosY()-0.5F, entity.getPosZ()-0.5F, entity.lastTickPosX+0.5F,
                entity.lastTickPosY+0.5F, entity.lastTickPosZ+0.5F).grow(1);
        List<LivingEntity> entities = entity.world.getEntitiesWithinAABB(LivingEntity.class, axis);
        for (LivingEntity living : entities) {
            if(!burst.isFake()) {
                DamageHandler.INSTANCE.dmg(living, null, living instanceof PlayerEntity ? 25F : 8F, DamageHandler.INSTANCE.MAGIC_PIERCING);
            }
        }
    }

}
