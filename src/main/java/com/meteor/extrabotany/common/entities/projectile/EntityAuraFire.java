package com.meteor.extrabotany.common.entities.projectile;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.entities.ModEntities;
import com.meteor.extrabotany.common.handler.DamageHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;

public class EntityAuraFire extends EntityProjectileBase{

    public EntityAuraFire(EntityType<? extends EntityProjectileBase> type, World worldIn) {
        super(type, worldIn);
    }

    public EntityAuraFire(World worldIn, LivingEntity thrower) {
        super(ModEntities.AURAFIRE, worldIn, thrower);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.ticksExisted > 80)
            this.setDead();
        if (this.world.isRemote)
            for (int i = 0; i < 5; i++)
                this.world.addParticle(ParticleTypes.FLAME, this.getPosX() + Math.random() * 0.4F - 0.2F,
                        this.getPosY() + Math.random() * 0.4F - 0.2F, this.getPosZ() + Math.random() * 0.4F - 0.2F, 0, 0, 0);
    }

    @Override
    public void onEntityHit(EntityRayTraceResult result) {
        if(getThrower() instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) getThrower();
            if(result.getEntity() != player){
                float dmg = ExtraBotanyAPI.calcDamage(5F, player);
                DamageHandler.INSTANCE.dmg(result.getEntity(), player, dmg, DamageHandler.INSTANCE.NETURAL_PIERCING);
                player.setAbsorptionAmount(Math.min(10, player.getAbsorptionAmount()+1F));
                remove();
            }
        }
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


}
