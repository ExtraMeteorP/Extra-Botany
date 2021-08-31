package com.meteor.extrabotany.common.entities;

import com.meteor.extrabotany.common.handler.FlamescionHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import java.util.List;

public class EntityFlamescionSlash extends Entity {

    private PlayerEntity owner;
    private float damage = 1F;

    private static final String TAG_ROTATION = "rotation";
    private static final String TAG_PITCH = "pitch";

    private static final DataParameter<Float> ROTATION = EntityDataManager.createKey(EntityFlamescionSlash.class,
            DataSerializers.FLOAT);
    private static final DataParameter<Float> PITCH = EntityDataManager.createKey(EntityFlamescionSlash.class,
            DataSerializers.FLOAT);

    public EntityFlamescionSlash(EntityType<? extends EntityFlamescionSlash> type, World worldIn) {
        super(type, worldIn);
    }

    public EntityFlamescionSlash(World worldIn, PlayerEntity owner) {
        super(ModEntities.FLAMESCIONSLASH, worldIn);
        this.owner = owner;
        setRotation((float) (120F * Math.random()) - 60F);
        setPitch((float) (360F * Math.random()));
    }

    @Override
    public void registerData() {
        dataManager.register(ROTATION, 0F);
        dataManager.register(PITCH, 0F);
    }

    @Override
    public void tick(){
        super.tick();
        dmg();
    }

    public void dmg(){
        for(LivingEntity entity : getEntitiesAround()){
            if(entity instanceof IMob){
                entity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 2, 3));
            }
        }

        if(this.ticksExisted == 2 || this.ticksExisted == 5){
            damageAllAround(damage);
        }

        if(this.ticksExisted >= 6)
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
        float range = 3.5F;
        return world.getEntitiesWithinAABB(LivingEntity.class,
                new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range,
                        source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
    }

    @Override
    public void writeAdditional(CompoundNBT cmp) {
        cmp.putFloat(TAG_ROTATION, getRotation());
        cmp.putFloat(TAG_PITCH, getPitch());
    }

    @Override
    public void readAdditional(CompoundNBT cmp) {
        setRotation(cmp.getFloat(TAG_ROTATION));
        setPitch(cmp.getFloat(TAG_PITCH));
    }

    public float getRotation() {
        return dataManager.get(ROTATION);
    }

    public void setRotation(float rot) {
        dataManager.set(ROTATION, rot);
    }

    public float getPitch() {
        return dataManager.get(PITCH);
    }

    public void setPitch(float rot) {
        dataManager.set(PITCH, rot);
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
