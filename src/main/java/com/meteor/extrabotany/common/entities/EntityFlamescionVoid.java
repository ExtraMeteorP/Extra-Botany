package com.meteor.extrabotany.common.entities;

import com.meteor.extrabotany.common.handler.FlamescionHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class EntityFlamescionVoid extends Entity {

    private PlayerEntity owner;
    private float damage = 1.5F;

    public EntityFlamescionVoid(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public EntityFlamescionVoid(World worldIn, PlayerEntity owner) {
        super(ModEntities.VOID, worldIn);
        this.owner = owner;
    }

    @Override
    public void tick(){
        super.tick();
        dmg();
    }

    public void dmg(){
        for(LivingEntity entity : getEntitiesAround()){
            if(owner != null) {
                if (entity instanceof IMob) {
                    entity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 2, 3));
                }
                if (entity == owner)
                    continue;
                Vector3d vec = this.getPositionVec().subtract(entity.getPositionVec());
                entity.setMotion(vec.normalize().scale(1.5D));
            }
        }

        if(this.ticksExisted %15 == 0){
            damageAllAround(damage);
        }

        if(this.ticksExisted >= 40)
            remove();
    }

    public void damageAllAround(float dmg){
        for(LivingEntity entity : getEntitiesAround()){
            if(owner != null){
                if(entity == owner)
                    continue;
                entity.hurtResistantTime = 0;
                entity.attackEntityFrom(FlamescionHandler.flameSource(), dmg);
            }
        }
    }

    public List<LivingEntity> getEntitiesAround() {
        BlockPos source = new BlockPos(getPosX(), getPosY(), getPosZ());
        float range = 6F;
        return world.getEntitiesWithinAABB(LivingEntity.class,
                new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range,
                        source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
    }

    @Override
    protected void registerData() {

    }

    @Override
    protected void readAdditional(CompoundNBT compound) {

    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {

    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
