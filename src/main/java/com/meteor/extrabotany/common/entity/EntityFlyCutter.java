package com.meteor.extrabotany.common.entity;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import vazkii.botania.common.Botania;

import javax.annotation.Nonnull;
import java.util.List;

public class EntityFlyCutter extends EntityThrowable {

    private static final String TAG_DAMAGE = "damage";
    private static final String TAG_LIFE = "life";
    private static final String TAG_DELAY = "delay";
    private static final String TAG_ROTATION = "rotation";
    private static final String TAG_VARIETY = "variety";
    private static final String TAG_PITCH = "pitch";

    private static final DataParameter<Integer> DAMAGE = EntityDataManager.createKey(EntityFlyCutter.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> LIFE = EntityDataManager.createKey(EntityFlyCutter.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> DELAY = EntityDataManager.createKey(EntityFlyCutter.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Float> ROTATION = EntityDataManager.createKey(EntityFlyCutter.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> PITCH = EntityDataManager.createKey(EntityFlyCutter.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> VARIETY = EntityDataManager.createKey(EntityFlyCutter.class, DataSerializers.VARINT);

    public EntityLivingBase summoner = null;

    public EntityFlyCutter(World worldIn) {
        super(worldIn);
    }

    public static void attackedFrom(EntityLivingBase target, EntityLivingBase player, int i) {
        if (player instanceof EntityPlayer)
            target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) player), i);
        else
            target.attackEntityFrom(DamageSource.GENERIC, i);
    }

    public void setProps(EntityLivingBase summoner) {
        this.summoner = summoner;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        setSize(0F, 0F);
        dataManager.register(DAMAGE, 0);
        dataManager.register(LIFE, 0);
        dataManager.register(DELAY, false);
        dataManager.register(ROTATION, 0F);
        dataManager.register(PITCH, 0F);
        dataManager.register(VARIETY, 0);
    }

    @Override
    public boolean isImmuneToExplosions() {
        return true;
    }

    @Override
    public void onUpdate() {
        BlockPos source = this.getPosition();

        if (this.ticksExisted < getLife() || getDelay() || this.ticksExisted > 200)
            return;

        super.onUpdate();

        Botania.proxy.wispFX(posX, posY, posZ, 0.1F, 0.85F, 0.1F, 0.2F, 0F);

        List<EntityLivingBase> livings = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(source.getX() + 0.5 - 1.5, source.getY() + 0.5 - 1.5, source.getZ() + 0.5 - 1.5, source.getX() + 0.5 + 1.5, source.getY() + 0.5 + 1.5, source.getZ() + 0.5 + 1.5));
        for (EntityLivingBase living : livings) {
            if (living == summoner)
                continue;
            attackedFrom(living, summoner, getDamage());
            ExtraBotanyAPI.dealTrueDamage(living, 6F);
            setDead();
        }

        if (!world.isRemote
                && ticksExisted > 600)
            setDead();
    }

    @Override
    public void writeEntityToNBT(@Nonnull NBTTagCompound cmp) {
        super.writeEntityToNBT(cmp);
        cmp.setInteger(TAG_LIFE, getLife());
        cmp.setInteger(TAG_DAMAGE, getDamage());
        cmp.setBoolean(TAG_DELAY, getDelay());
        cmp.setFloat(TAG_ROTATION, getRotation());
        cmp.setInteger(TAG_VARIETY, getVariety());
        cmp.setFloat(TAG_PITCH, getPitch());
    }

    @Override
    public void readEntityFromNBT(@Nonnull NBTTagCompound cmp) {
        super.readEntityFromNBT(cmp);
        setLife(cmp.getInteger(TAG_LIFE));
        setDamage(cmp.getInteger(TAG_DAMAGE));
        setDelay(cmp.getBoolean(TAG_DELAY));
        setRotation(cmp.getFloat(TAG_ROTATION));
        setVariety(cmp.getInteger(TAG_VARIETY));
        setPitch(cmp.getFloat(TAG_PITCH));
    }

    public int getLife() {
        return dataManager.get(LIFE);
    }

    public void setLife(int delay) {
        dataManager.set(LIFE, delay);
    }

    public int getDamage() {
        return dataManager.get(DAMAGE);
    }

    public void setDamage(int delay) {
        dataManager.set(DAMAGE, delay);
    }

    public boolean getDelay() {
        return dataManager.get(DELAY);
    }

    public void setDelay(boolean delay) {
        dataManager.set(DELAY, delay);
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

    public int getVariety() {
        return dataManager.get(VARIETY);
    }

    public void setVariety(int var) {
        dataManager.set(VARIETY, var);
    }

    @Override
    protected void onImpact(RayTraceResult result) {

    }


}
