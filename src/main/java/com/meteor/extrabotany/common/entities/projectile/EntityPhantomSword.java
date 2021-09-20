package com.meteor.extrabotany.common.entities.projectile;

import com.meteor.extrabotany.common.entities.ModEntities;
import com.meteor.extrabotany.common.handler.DamageHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import java.util.List;

public class EntityPhantomSword extends EntityProjectileBase {

    public static final int LIVE_TICKS = 30;

    private static final String TAG_VARIETY = "variety";
    private static final String TAG_DELAY = "delay";
    private static final String TAG_FAKE = "fake";

    private static final DataParameter<Integer> VARIETY = EntityDataManager.createKey(EntityPhantomSword.class,
            DataSerializers.VARINT);
    private static final DataParameter<Integer> DELAY = EntityDataManager.createKey(EntityPhantomSword.class,
            DataSerializers.VARINT);
    private static final DataParameter<Boolean> FAKE = EntityDataManager.createKey(EntityPhantomSword.class,
            DataSerializers.BOOLEAN);

    private static final float rgb[][] = { { 0.82F, 0.2F, 0.58F }, { 0F, 0.71F, 0.10F }, { 0.74F, 0.07F, 0.32F },
            { 0.01F, 0.45F, 0.8F }, { 0.05F, 0.39F, 0.9F }, { 0.38F, 0.34F, 0.42F }, { 0.41F, 0.31F, 0.14F },
            { 0.92F, 0.92F, 0.21F }, { 0.61F, 0.92F, 0.98F }, { 0.18F, 0.45F, 0.43F } };

    public EntityPhantomSword(EntityType<EntityPhantomSword> type, World worldIn) {
        super(type, worldIn);
    }

    public EntityPhantomSword(World worldIn) {
        super(ModEntities.PHANTOMSWORD, worldIn);
    }


    public EntityPhantomSword(World world, LivingEntity thrower, BlockPos targetpos) {
        super(ModEntities.PHANTOMSWORD, world, thrower);
        setTargetPos(targetpos);
        setVariety((int) (10 * Math.random()));
    }

    @Override
    public void registerData() {
        super.registerData();
        dataManager.register(VARIETY, 0);
        dataManager.register(DELAY, 0);
        dataManager.register(FAKE, false);
    }

    @Override
    public boolean isImmuneToExplosions() {
        return true;
    }

    @Override
    public void tick() {

        if (getDelay() > 0) {
            setDelay(getDelay() - 1);
            return;
        }

        if (this.ticksExisted >= LIVE_TICKS)
            remove();

        if(getFake()) {
            this.setMotion(0D,0D,0D);
            return;
        }

        if (!getFake() && !world.isRemote && (getThrower() == null || getThrower().removed)) {
            remove();
            return;
        }

        super.tick();

        if(!world.isRemote && !getFake() && this.ticksExisted % 6 == 0) {
            EntityPhantomSword illusion = new EntityPhantomSword(world);
            illusion.setFake(true);
            illusion.setRotation(this.getRotation());
            illusion.setPitch(this.getPitch());
            illusion.setPosition(getPosX(), getPosY(), getPosZ());
            illusion.setVariety(getVariety());
            world.addEntity(illusion);
        }

        if (!world.isRemote) {
            AxisAlignedBB axis = new AxisAlignedBB(getPosX(), getPosY(), getPosZ(), lastTickPosX, lastTickPosY, lastTickPosZ).grow(2);
            List<LivingEntity> entities = world.getEntitiesWithinAABB(LivingEntity.class, axis);
            List<LivingEntity> list = DamageHandler.INSTANCE.getFilteredEntities(entities, getThrower());
            for (LivingEntity living : list) {
                if(getThrower() instanceof PlayerEntity) {
                    //DamageHandler.INSTANCE.dmg(living, getThrower(), 1F, DamageHandler.INSTANCE.LIFE_LOSING);
                    DamageHandler.INSTANCE.dmg(living, getThrower(), 7F, DamageHandler.INSTANCE.MAGIC_PIERCING);
                }else{
                    if(living.hurtResistantTime == 0)
                        DamageHandler.INSTANCE.dmg(living, getThrower(), 2.5F, DamageHandler.INSTANCE.LIFE_LOSING);;
                    DamageHandler.INSTANCE.dmg(living, getThrower(), 7.5F, DamageHandler.INSTANCE.MAGIC);
                }
            }
        }

    }

    @Override
    protected void onImpact(RayTraceResult result) {

    }

    @Override
    public void writeAdditional(CompoundNBT cmp) {
        super.writeAdditional(cmp);
        cmp.putInt(TAG_VARIETY, getVariety());
        cmp.putInt(TAG_DELAY, getDelay());
        cmp.putBoolean(TAG_FAKE, getFake());
    }

    @Override
    public void readAdditional(CompoundNBT cmp) {
        super.readAdditional(cmp);
        setVariety(cmp.getInt(TAG_VARIETY));
        setDelay(cmp.getInt(TAG_DELAY));
        setFake(cmp.getBoolean(TAG_FAKE));
    }

    public int getVariety() {
        return dataManager.get(VARIETY);
    }

    public void setVariety(int var) {
        dataManager.set(VARIETY, var);
    }

    public int getDelay() {
        return dataManager.get(DELAY);
    }

    public void setDelay(int var) {
        dataManager.set(DELAY, var);
    }

    public boolean getFake() {
        return dataManager.get(FAKE);
    }

    public void setFake(boolean rot) {
        dataManager.set(FAKE, rot);
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
