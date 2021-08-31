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

public class EntityTrueShadowKatanaProjectile extends EntityProjectileBase{

    public static final int LIVE_TICKS = 40;

    public EntityTrueShadowKatanaProjectile(EntityType<? extends ThrowableEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public EntityTrueShadowKatanaProjectile(World worldIn, LivingEntity thrower) {
        super(ModEntities.TRUESHADOWKATANA, worldIn, thrower);
    }

    @Override
    public void tick() {

        if (this.ticksExisted >= LIVE_TICKS)
            remove();

        if (!world.isRemote && (getThrower() == null || getThrower().removed)) {
            remove();
            return;
        }

        if(this.ticksExisted <= 3)
            return;

        if(world.isRemote && ticksExisted % 2 == 0){
            WispParticleData data = WispParticleData.wisp(0.15F, 0F, 0F, 0F ,1F);
            Botania.proxy.addParticleForce(world, data, getPosX(), getPosY(), getPosZ(), 0, 0, 0);
        }

        super.tick();

        if (!world.isRemote) {
            AxisAlignedBB axis = new AxisAlignedBB(getPosX(), getPosY(), getPosZ(), lastTickPosX, lastTickPosY, lastTickPosZ).grow(2);
            List<LivingEntity> entities = world.getEntitiesWithinAABB(LivingEntity.class, axis);
            List<LivingEntity> list = DamageHandler.INSTANCE.getFilteredEntities(entities, getThrower());
            for (LivingEntity living : list) {
                living.hurtResistantTime = 0;
                if(getThrower() instanceof PlayerEntity) {
                    DamageHandler.INSTANCE.dmg(living, getThrower(), 6F, DamageHandler.INSTANCE.NETURAL);
                }else{
                    DamageHandler.INSTANCE.dmg(living, getThrower(), 0.05F, DamageHandler.INSTANCE.LIFE_LOSING);;
                    DamageHandler.INSTANCE.dmg(living, getThrower(), 4F, DamageHandler.INSTANCE.MAGIC);
                }
                remove();
                break;
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
        return MiscellaneousIcons.INSTANCE.trueshadowkatanaprojectileModel[0];
    }

}
