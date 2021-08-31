package com.meteor.extrabotany.common.entities;

import com.meteor.extrabotany.common.handler.FlamescionHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import java.util.List;

public class EntityStrengthenSlash extends ThrowableEntity {

    private PlayerEntity owner;
    private float damage = 5F;

    private static final String TAG_ROTATION = "rotation";
    private static final String TAG_PITCH = "pitch";
    private static final String TAG_TARGETPOS = "targetpos";

    private static final DataParameter<Float> ROTATION = EntityDataManager.createKey(EntityStrengthenSlash.class,
            DataSerializers.FLOAT);
    private static final DataParameter<Float> PITCH = EntityDataManager.createKey(EntityStrengthenSlash.class,
            DataSerializers.FLOAT);
    private static final DataParameter<BlockPos> TARGET_POS = EntityDataManager.createKey(EntityStrengthenSlash.class,
            DataSerializers.BLOCK_POS);

    public EntityStrengthenSlash(EntityType<? extends EntityStrengthenSlash> type, World worldIn) {
        super(type, worldIn);
    }

    public EntityStrengthenSlash(World worldIn, PlayerEntity owner) {
        super(ModEntities.SRENGTHENSLASH, worldIn);
        this.setNoGravity(true);
        this.setInvulnerable(true);
    }

    @Override
    public void registerData() {
        dataManager.register(ROTATION, 0F);
        dataManager.register(PITCH, 0F);
        dataManager.register(TARGET_POS, BlockPos.ZERO);
    }

    @Override
    public void tick(){
        super.tick();

        if(this.ticksExisted % 2 == 0){
            damageAllAround(damage);
        }

        if(this.ticksExisted >= 15)
            remove();
    }

    public void faceEntity(BlockPos target) {
        double d0 = target.getX() - this.getPosX();
        double d2 = target.getZ() - this.getPosZ();
        double d1 = target.getY() - this.getPosY();

        double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
        float f = (float) (MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
        float f1 = (float) (-(MathHelper.atan2(d1, d3) * (180D / Math.PI)));
        this.rotationPitch = this.updateRotation(this.rotationPitch, f1, 360F);
        this.rotationYaw = this.updateRotation(this.rotationYaw, f, 360F);

        setPitch(-this.rotationPitch);
        setRotation(MathHelper.wrapDegrees(-this.rotationYaw + 180));
    }

    private float updateRotation(float angle, float targetAngle, float maxIncrease) {
        float f = MathHelper.wrapDegrees(targetAngle - angle);

        if (f > maxIncrease) {
            f = maxIncrease;
        }

        if (f < -maxIncrease) {
            f = -maxIncrease;
        }

        return angle + f;
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
        float range = 2.5F;
        return world.getEntitiesWithinAABB(LivingEntity.class,
                new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range,
                        source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
    }

    @Override
    public void writeAdditional(CompoundNBT cmp) {
        super.writeAdditional(cmp);
        cmp.putFloat(TAG_ROTATION, getRotation());
        cmp.putFloat(TAG_PITCH, getPitch());
    }

    @Override
    public void readAdditional(CompoundNBT cmp) {
        super.readAdditional(cmp);
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
