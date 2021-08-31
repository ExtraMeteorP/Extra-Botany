package com.meteor.extrabotany.common.items.lens;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import vazkii.botania.api.internal.IManaBurst;
import vazkii.botania.common.core.helper.Vector3;
import vazkii.botania.common.item.lens.Lens;

import java.util.List;

public class LensTrace extends Lens {

    private static final String TAG_HOME_ID = "homeID";

    @Override
    public void updateBurst(IManaBurst burst, ItemStack stack) {
        ThrowableEntity entity = burst.entity();
        if(entity.world.isRemote)
            return;
        AxisAlignedBB axis = new AxisAlignedBB(entity.getPosX(), entity.getPosY(), entity.getPosZ(), entity.lastTickPosX,
                entity.lastTickPosY, entity.lastTickPosZ).grow(4);
        List<LivingEntity> entities = entity.world.getEntitiesWithinAABB(LivingEntity.class, axis);
        int homeID = entity.getPersistentData().getInt(TAG_HOME_ID);
        if(homeID == 0) {
            for (LivingEntity living : entities) {
                entity.getPersistentData().putInt(TAG_HOME_ID, living.getEntityId());
                break;
            }
        }else {
            Entity result = entity.world.getEntityByID(homeID);
            if (result != null && result.getDistance(entity) < 3F && !burst.isFake()) {
                Vector3 vecEntity = Vector3.fromEntityCenter(result);
                Vector3 vecThis = Vector3.fromEntityCenter(entity);
                Vector3 vecMotion = vecEntity.subtract(vecThis);
                Vector3 vecCurrentMotion = new Vector3(entity.getMotion().x, entity.getMotion().y, entity.getMotion().z);
                vecMotion.normalize().multiply(vecCurrentMotion.mag());
                burst.setBurstMotion(vecMotion.x, vecMotion.y, vecMotion.z);
            }
        }
    }

}
