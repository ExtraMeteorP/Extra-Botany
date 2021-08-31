package com.meteor.extrabotany.common.entities;

import com.meteor.extrabotany.common.core.ModSounds;
import com.meteor.extrabotany.common.handler.FlamescionHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class EntityFlamescionUlt extends Entity {

    private PlayerEntity owner;
    private float damage = 12F;

    public EntityFlamescionUlt(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public EntityFlamescionUlt(World worldIn, PlayerEntity owner) {
        super(ModEntities.ULT, worldIn);
        this.owner = owner;
    }

    @Override
    public void tick(){
        super.tick();
        if(this.ticksExisted == 1 && !world.isRemote)
            this.playSound(ModSounds.flamescionult, 1F, 1F);

        if(this.ticksExisted == 10 || this.ticksExisted == 35 || this.ticksExisted == 60){
            damageAllAround(damage);
        }

        if(this.ticksExisted >= 40) {
            if(world.isRemote)
                world.addParticle(ParticleTypes.EXPLOSION,
                        getPosX() - 2D + Math.random() * 4D,
                        getPosY() - 2D + Math.random() * 4D,
                        getPosZ() - 2D + Math.random() * 4D,
                        0, 0, 0);
        }

        if(this.ticksExisted >= 85)
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
        float range = 8F;
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
