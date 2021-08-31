package com.meteor.extrabotany.common.items.lens;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import vazkii.botania.api.internal.IManaBurst;
import vazkii.botania.common.item.lens.Lens;

import java.util.List;

public class LensPush extends Lens {

    @Override
    public void updateBurst(IManaBurst burst, ItemStack stack) {
        ThrowableEntity entity = burst.entity();
        AxisAlignedBB axis = new AxisAlignedBB(entity.getPosX(), entity.getPosY(), entity.getPosZ(), entity.lastTickPosX,
                entity.lastTickPosY, entity.lastTickPosZ);
        List<LivingEntity> entities = entity.world.getEntitiesWithinAABB(LivingEntity.class, axis);

        if(!burst.isFake()) {
            for (LivingEntity living : entities) {
                living.setMotion(entity.getMotion());
            }
        }

    }

}
