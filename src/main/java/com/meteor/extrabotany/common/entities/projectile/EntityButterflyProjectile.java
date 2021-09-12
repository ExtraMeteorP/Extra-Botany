package com.meteor.extrabotany.common.entities.projectile;

import com.meteor.extrabotany.common.entities.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;

public class EntityButterflyProjectile extends EntityProjectileBase{

    public EntityButterflyProjectile(EntityType<? extends ThrowableEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public EntityButterflyProjectile(World worldIn, LivingEntity thrower) {
        super(ModEntities.BUTTERFLY, worldIn, thrower);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.ticksExisted > 100)
            this.setDead();
    }

    @Override
    public void onEntityHit(EntityRayTraceResult result) {
        if (getThrower() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) getThrower();
            if (result.getEntity() != player) {

            }
        }
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
