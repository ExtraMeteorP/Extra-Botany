package com.meteor.extrabotany.common.entities.projectile;

import com.meteor.extrabotany.client.handler.MiscellaneousIcons;
import com.meteor.extrabotany.common.entities.ModEntities;
import com.meteor.extrabotany.common.handler.DamageHandler;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import vazkii.botania.client.fx.WispParticleData;
import vazkii.botania.common.Botania;

import javax.annotation.Nonnull;
import java.util.List;

public class EntityInfluxWaverProjectile extends EntityProjectileBase{

    public static final int LIVE_TICKS = 60;

    private static final String TAG_STRIKE_TIMES = "strike_times";
    private static final String TAG_NEXT = "next";
    private static final DataParameter<Integer> STRIKE_TIMES = EntityDataManager.createKey(EntityInfluxWaverProjectile.class,
            DataSerializers.VARINT);
    private static final DataParameter<BlockPos> NEXT = EntityDataManager.createKey(EntityInfluxWaverProjectile.class,
            DataSerializers.BLOCK_POS);

    private int removeFlag = -1;

    public EntityInfluxWaverProjectile(EntityType<? extends EntityProjectileBase> type, World worldIn) {
        super(type, worldIn);
    }

    public EntityInfluxWaverProjectile(World worldIn, LivingEntity thrower) {
        super(ModEntities.INFLUXWAVER, worldIn, thrower);
    }

    @Override
    public void registerData() {
        super.registerData();
        dataManager.register(STRIKE_TIMES, 0);
        dataManager.register(NEXT, BlockPos.ZERO);
    }

    @Override
    public void tick() {

        if(this.removeFlag != -1 && this.ticksExisted >= this.removeFlag + 4){
            if(!world.isRemote && !getNext().equals(BlockPos.ZERO)) {
                EntityInfluxWaverProjectile proj = make(getNext());
                world.addEntity(proj);
                remove();
            }
        }

        if (this.ticksExisted >= LIVE_TICKS)
            remove();

        if (!world.isRemote && (getThrower() == null || getThrower().removed)) {
            remove();
            return;
        }

        super.tick();

        if(this.removeFlag != -1)
            return;

        if(world.isRemote && ticksExisted % 2 == 0){
            WispParticleData data = WispParticleData.wisp(0.3F, 0.1F, 0.1F, 0.85F ,1F);
            Botania.proxy.addParticleForce(world, data, getPosX(), getPosY(), getPosZ(), 0, 0, 0);
        }

        if (!world.isRemote) {
            AxisAlignedBB axis = new AxisAlignedBB(getPosX(), getPosY(), getPosZ(), lastTickPosX, lastTickPosY, lastTickPosZ).grow(2);
            List<LivingEntity> entities = world.getEntitiesWithinAABB(LivingEntity.class, axis);
            boolean flag = false;
            List<LivingEntity> list = DamageHandler.INSTANCE.getFilteredEntities(entities, getThrower());
            for (LivingEntity living : list) {
                if(!living.removed) {
                    living.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 60, 1));
                    if(getThrower() instanceof PlayerEntity) {
                        DamageHandler.INSTANCE.dmg(living, getThrower(), 12F, DamageHandler.INSTANCE.NETURAL);
                    }else{
                        if(living.hurtResistantTime == 0)
                            DamageHandler.INSTANCE.dmg(living, getThrower(), 2.5F, DamageHandler.INSTANCE.LIFE_LOSING);
                        DamageHandler.INSTANCE.dmg(living, getThrower(), 7F, DamageHandler.INSTANCE.MAGIC);
                    }
                    flag = living.removed;
                    if(getStrikeTimes() > 0 && !flag){
                        setNext(living.getPosition().add(0, 1, 0));
                        removeFlag = this.ticksExisted;
                    }
                    break;
                }
            }

            if(getStrikeTimes() > 0 && flag){
                axis = axis.grow(5D);
                List<LivingEntity> others = world.getEntitiesWithinAABB(LivingEntity.class, axis);
                List<LivingEntity> olist = DamageHandler.INSTANCE.getFilteredEntities(others, getThrower());
                for(LivingEntity living : olist){
                    setNext(living.getPosition().add(0, 1, 0));
                    removeFlag = this.ticksExisted;
                    break;
                }
            }
        }

    }

    public EntityInfluxWaverProjectile make(BlockPos targetpos){
        EntityInfluxWaverProjectile proj = new EntityInfluxWaverProjectile(world, getThrower());
        float range = 6F;
        double j = -Math.PI + 2 * Math.PI * Math.random();
        double k;
        double x,y,z;
        k = 0.12F * Math.PI * Math.random() + 0.28F * Math.PI;
        x = targetpos.getX() + range * Math.sin(k) * Math.cos(j);
        y = targetpos.getY() + range * Math.cos(k);
        z = targetpos.getZ() + range * Math.sin(k) * Math.sin(j);
        proj.setPosition(x,y,z);
        proj.setTargetPos(targetpos);
        proj.faceTarget(0.8F);
        proj.setStrikeTimes(getStrikeTimes()-1);
        return proj;
    }

    @Override
    public void writeAdditional(CompoundNBT cmp) {
        super.writeAdditional(cmp);
        cmp.putInt(TAG_STRIKE_TIMES, getStrikeTimes());
        cmp.putLong(TAG_NEXT, getNext().toLong());
    }

    @Override
    public void readAdditional(CompoundNBT cmp) {
        super.readAdditional(cmp);
        setStrikeTimes(cmp.getInt(TAG_STRIKE_TIMES));
        setNext(BlockPos.fromLong(cmp.getLong(TAG_NEXT)));
    }

    public int getStrikeTimes(){
        return dataManager.get(STRIKE_TIMES);
    }

    public void setStrikeTimes(int i){
        dataManager.set(STRIKE_TIMES, i);
    }

    public BlockPos getNext(){
        return dataManager.get(NEXT);
    }

    public void setNext(BlockPos pos){
        dataManager.set(NEXT, pos);
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IBakedModel getIcon(){
        return MiscellaneousIcons.INSTANCE.influxwaverprojectileModel[0];
    }

}
