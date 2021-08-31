package com.meteor.extrabotany.common.entities;

import com.meteor.extrabotany.common.handler.FlamescionHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import java.util.List;

public class EntityFlamescionSword extends ThrowableEntity {

    private PlayerEntity owner;
    private float damage = 4F;

    public EntityFlamescionSword(EntityType<? extends EntityFlamescionSword> type, World worldIn) {
        super(type, worldIn);
    }

    public EntityFlamescionSword(World worldIn, PlayerEntity owner) {
        super(ModEntities.SWORD, worldIn);
        this.setNoGravity(true);
        this.setInvulnerable(true);
        this.owner = owner;
    }

    @Override
    public void registerData() {

    }

    @Override
    public void tick(){
        super.tick();

        if(ticksExisted % 4 == 0)
            damageAllAround(damage);

        if(this.ticksExisted >= 30)
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

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


}
