package com.meteor.extrabotany.common.entities.projectile;

import com.meteor.extrabotany.common.entities.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import vazkii.botania.client.fx.WispParticleData;
import vazkii.botania.common.Botania;

import java.util.List;

public class EntityMagicArrow extends ThrowableEntity {

    private static final String TAG_DAMAGE = "damage";
    private static final String TAG_LIFE = "life";

    private static final DataParameter<Integer> DAMAGE = EntityDataManager.createKey(EntityMagicArrow.class,
            DataSerializers.VARINT);
    private static final DataParameter<Integer> LIFE = EntityDataManager.createKey(EntityMagicArrow.class,
            DataSerializers.VARINT);

    private LivingEntity thrower;

    public EntityMagicArrow(EntityType<? extends EntityMagicArrow> type, World worldIn) {
        super(type, worldIn);
    }

    public EntityMagicArrow(World worldIn, LivingEntity thrower) {
        this(ModEntities.MAGICARROW, worldIn);
        this.thrower = thrower;
    }

    @Override
    public void registerData() {
        dataManager.register(DAMAGE, 0);
        dataManager.register(LIFE, 0);
    }

    @Override
    public void tick(){
        super.tick();

        if (!world.isRemote && (thrower == null || !(thrower instanceof PlayerEntity) || thrower.removed)) {
            remove();
            return;
        }

        if(world.isRemote){
            WispParticleData data = WispParticleData.wisp(0.5F, 0.1F, 0.85F, 0.1F ,1F);
            Botania.proxy.addParticleForce(world, data, getPosX(), getPosY(), getPosZ(), 0, 0, 0);
        }

        PlayerEntity player = (PlayerEntity) thrower;
        if (!world.isRemote) {
            AxisAlignedBB axis = new AxisAlignedBB(getPosX() - 2F, getPosY() - 2F, getPosZ() - 2F, lastTickPosX + 2F,
                    lastTickPosY + 2F, lastTickPosZ + 2F);
            List<LivingEntity> entities = world.getEntitiesWithinAABB(LivingEntity.class, axis);
            for (LivingEntity living : entities) {
                if (living == thrower)
                    continue;

                if (living.hurtTime == 0) {
                    attackedFrom(living, player, getDamage());
                }

            }
        }

        if (ticksExisted > getLife())
            remove();
    }

    public static void attackedFrom(LivingEntity target, PlayerEntity player, float i) {
        if (player != null)
            target.attackEntityFrom(DamageSource.causePlayerDamage(player), i);
        else
            target.attackEntityFrom(DamageSource.GENERIC, i);
    }

    @Override
    public boolean isImmuneToExplosions() {
        return true;
    }

    @Override
    public float getGravityVelocity() {
        return 0F;
    }

    @Override
    public void writeAdditional(CompoundNBT cmp) {
        cmp.putInt(TAG_LIFE, getLife());
        cmp.putInt(TAG_DAMAGE, getDamage());
    }

    @Override
    public void readAdditional(CompoundNBT cmp) {
        setLife(cmp.getInt(TAG_LIFE));
        setDamage(cmp.getInt(TAG_DAMAGE));
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

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public boolean isPushedByWater() {
        return false;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
