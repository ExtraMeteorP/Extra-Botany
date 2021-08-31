package com.meteor.extrabotany.common.entities.projectile;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityProjectileBase extends ThrowableEntity {

    private static final String TAG_ROTATION = "rotation";
    private static final String TAG_PITCH = "pitch";
    private static final String TAG_TARGETPOS = "targetpos";
    private static final String TAG_TARGETPOSX = "targetposx";
    private static final String TAG_TARGETPOSY = "targetposy";
    private static final String TAG_TARGETPOSZ = "targetposz";

    private static final DataParameter<Float> ROTATION = EntityDataManager.createKey(EntityProjectileBase.class,
            DataSerializers.FLOAT);
    private static final DataParameter<Float> PITCH = EntityDataManager.createKey(EntityProjectileBase.class,
            DataSerializers.FLOAT);
    private static final DataParameter<BlockPos> TARGET_POS = EntityDataManager.createKey(EntityProjectileBase.class,
            DataSerializers.BLOCK_POS);
    private static final DataParameter<Float> TARGET_POS_X = EntityDataManager.createKey(EntityProjectileBase.class,
            DataSerializers.FLOAT);
    private static final DataParameter<Float> TARGET_POS_Y = EntityDataManager.createKey(EntityProjectileBase.class,
            DataSerializers.FLOAT);
    private static final DataParameter<Float> TARGET_POS_Z = EntityDataManager.createKey(EntityProjectileBase.class,
            DataSerializers.FLOAT);

    private LivingEntity thrower;

    public EntityProjectileBase(EntityType<? extends ThrowableEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public EntityProjectileBase(EntityType<? extends ThrowableEntity> type, World worldIn, LivingEntity thrower) {
        super(type, worldIn);
        this.thrower = thrower;
        this.setNoGravity(true);
    }

    @Nullable
    public LivingEntity getThrower(){
        return this.thrower;
    }

    @Override
    public void registerData() {
        dataManager.register(ROTATION, 0F);
        dataManager.register(PITCH, 0F);
        dataManager.register(TARGET_POS, BlockPos.ZERO);
        dataManager.register(TARGET_POS_X, 0F);
        dataManager.register(TARGET_POS_Y, 0F);
        dataManager.register(TARGET_POS_Z, 0F);
    }

    public void faceTargetAccurately(float modifier){
        this.faceEntity(this.getTargetPosX(), this.getTargetPosY(), this.getTargetPosZ());
        Vector3d vec = new Vector3d(getTargetPosX() - getPosX(), getTargetPosY() - getPosY(), getTargetPosZ() - getPosZ())
                .normalize();
        this.setMotion(vec.x * modifier, vec.y * modifier, vec.z * modifier);
    }

    public void faceTarget(float modifier){
        this.faceEntity(this.getTargetPos());
        Vector3d vec = new Vector3d(getTargetPos().getX() - getPosX(), getTargetPos().getY() - getPosY(), getTargetPos().getZ() - getPosZ())
                .normalize();
        this.setMotion(vec.x * modifier, vec.y * modifier, vec.z * modifier);
    }

    public void setTargetPos(Vector3d vec) {
        setTargetPosX((float) vec.x);
        setTargetPosY((float) vec.y);
        setTargetPosZ((float) vec.z);
        setTargetPos(new BlockPos(vec));
    }

    public void faceEntity(float vx, float vy, float vz) {
        double d0 = vx - this.getPosX();
        double d2 = vz - this.getPosZ();
        double d1 = vy - this.getPosY();

        double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
        float f = (float) (MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
        float f1 = (float) (-(MathHelper.atan2(d1, d3) * (180D / Math.PI)));
        this.rotationPitch = this.updateRotation(this.rotationPitch, f1, 360F);
        this.rotationYaw = this.updateRotation(this.rotationYaw, f, 360F);

        setPitch(-this.rotationPitch);
        setRotation(MathHelper.wrapDegrees(-this.rotationYaw + 180));
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

    @Override
    public void writeAdditional(CompoundNBT cmp) {
        super.writeAdditional(cmp);
        cmp.putFloat(TAG_ROTATION, getRotation());
        cmp.putFloat(TAG_PITCH, getPitch());
        cmp.putLong(TAG_TARGETPOS, getTargetPos().toLong());
        cmp.putFloat(TAG_TARGETPOSX, getTargetPosX());
        cmp.putFloat(TAG_TARGETPOSY, getTargetPosY());
        cmp.putFloat(TAG_TARGETPOSZ, getTargetPosZ());
    }

    @Override
    public void readAdditional(CompoundNBT cmp) {
        super.readAdditional(cmp);
        setRotation(cmp.getFloat(TAG_ROTATION));
        setPitch(cmp.getFloat(TAG_PITCH));
        setTargetPos(BlockPos.fromLong(cmp.getLong(TAG_TARGETPOS)));
        setTargetPosX(cmp.getFloat(TAG_TARGETPOSX));
        setTargetPosY(cmp.getFloat(TAG_TARGETPOSY));
        setTargetPosZ(cmp.getFloat(TAG_TARGETPOSZ));
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

    public BlockPos getTargetPos() {
        return dataManager.get(TARGET_POS);
    }

    public void setTargetPos(BlockPos pos) {
        dataManager.set(TARGET_POS, pos);
    }

    public float getTargetPosX() {
        return dataManager.get(TARGET_POS_X);
    }

    public void setTargetPosX(float f) {
        dataManager.set(TARGET_POS_X, f);
    }

    public float getTargetPosY() {
        return dataManager.get(TARGET_POS_Y);
    }

    public void setTargetPosY(float f) {
        dataManager.set(TARGET_POS_Y, f);
    }

    public float getTargetPosZ() {
        return dataManager.get(TARGET_POS_Z);
    }

    public void setTargetPosZ(float f) {
        dataManager.set(TARGET_POS_Z, f);
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @OnlyIn(Dist.CLIENT)
    public IBakedModel getIcon(){
        return null;
    }

}
