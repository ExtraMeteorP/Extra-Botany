package com.meteor.extrabotany.common.entities.projectile;

import com.meteor.extrabotany.client.handler.MiscellaneousIcons;
import com.meteor.extrabotany.common.entities.ModEntities;
import com.meteor.extrabotany.common.handler.DamageHandler;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import vazkii.botania.client.fx.WispParticleData;
import vazkii.botania.common.Botania;

import javax.annotation.Nonnull;
import java.util.List;

public class EntityTrueTerrabladeProjectile extends EntityProjectileBase{

    public static final int LIVE_TICKS = 60;

    public EntityTrueTerrabladeProjectile(EntityType<? extends ThrowableEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public EntityTrueTerrabladeProjectile(World worldIn, LivingEntity thrower) {
        super(ModEntities.TRUETERRABLADE, worldIn, thrower);
    }

    @Override
    public void tick() {

        if (this.ticksExisted >= LIVE_TICKS)
            remove();

        if (!world.isRemote && (getThrower() == null || getThrower().removed)) {
            remove();
            return;
        }

        super.tick();

        if(world.isRemote && ticksExisted % 2 == 0){
            WispParticleData data = WispParticleData.wisp(0.3F, 0.1F, 0.95F, 0.1F ,1F);
            Botania.proxy.addParticleForce(world, data, getPosX(), getPosY(), getPosZ(), 0, 0, 0);
        }

        if (!world.isRemote && ticksExisted % 3 == 0) {
            AxisAlignedBB axis = new AxisAlignedBB(getPosX(), getPosY(), getPosZ(), lastTickPosX, lastTickPosY, lastTickPosZ).grow(2);
            List<LivingEntity> entities = world.getEntitiesWithinAABB(LivingEntity.class, axis);
            List<LivingEntity> list = DamageHandler.INSTANCE.getFilteredEntities(entities, getThrower());
            for (LivingEntity living : list) {
                if(getThrower() instanceof PlayerEntity) {
                    DamageHandler.INSTANCE.dmg(living, getThrower(), 11F, DamageHandler.INSTANCE.NETURAL);
                }else{
                    if(living.hurtResistantTime == 0)
                        DamageHandler.INSTANCE.dmg(living, getThrower(), 2.5F, DamageHandler.INSTANCE.LIFE_LOSING);
                    DamageHandler.INSTANCE.dmg(living, getThrower(), 7F, DamageHandler.INSTANCE.MAGIC);
                }
            }
        }
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IBakedModel getIcon(){
        return MiscellaneousIcons.INSTANCE.trueterrabladeprojectileModel[0];
    }

}
