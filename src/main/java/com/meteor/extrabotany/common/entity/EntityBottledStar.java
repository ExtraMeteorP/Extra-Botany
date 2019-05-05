package com.meteor.extrabotany.common.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import vazkii.botania.common.core.helper.Vector3;
import vazkii.botania.common.entity.EntityFallingStar;

import java.util.List;

public class EntityBottledStar extends EntityThrowableCopy {

    public EntityBottledStar(World worldIn) {
        super(worldIn);
    }

    public EntityBottledStar(World world, EntityLivingBase thrower) {
        super(world, thrower);
    }

    @Override
    public void onUpdate() {
        this.motionX = this.motionY = this.motionZ = 0;
        super.onUpdate();
        if (this.ticksExisted > 130)
            setDead();
        if (this.getThrower() != null) {
            World world = this.getThrower().world;
            float range = 1.8F;
            float drange = 2 * range;

            Vector3 posVec = Vector3.fromBlockPos(this.getPosition());
            Vector3 motVec = new Vector3((drange * Math.random() - range) * 18, 24, (drange * Math.random() - range) * 18);
            posVec = posVec.add(motVec);
            motVec = motVec.normalize().negate().multiply(1.5);

            EntityFallingStar star = new EntityFallingStar(world, this.getThrower());
            star.setPosition(this.posX, posVec.y, this.posZ);
            star.motionX = motVec.x;
            star.motionY = motVec.y;
            star.motionZ = motVec.z;
            if (this.ticksExisted % 6 == 0 && !world.isRemote)
                world.spawnEntity(star);

            if (this.ticksExisted % 4 == 0) {
                EntityFallingStar bonusStar = new EntityFallingStar(world, this.getThrower());
                Vector3 motVec2 = new Vector3((0.5 * Math.random() - 0.25) * 18, 24, (0.5 * Math.random() - 0.25) * 18);
                motVec2 = motVec.normalize().negate().multiply(1.5);
                bonusStar.motionX = motVec2.x;
                bonusStar.motionY = motVec2.y;
                bonusStar.motionZ = motVec2.z;
                AxisAlignedBB axis = new AxisAlignedBB(posX - 12F, posY - 12F, posZ - 12F, lastTickPosX + 12F, lastTickPosY + 12F, lastTickPosZ + 12F);
                List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, axis);
                for (EntityLivingBase living : entities) {
                    bonusStar.setPosition(living.posX, posVec.y, living.posZ);
                    if (!world.isRemote)
                        world.spawnEntity(bonusStar);
                    break;
                }
            }
        }
    }

    @Override
    protected void entityInit() {

    }

    @Override
    protected void onImpact(RayTraceResult result) {

    }

}
