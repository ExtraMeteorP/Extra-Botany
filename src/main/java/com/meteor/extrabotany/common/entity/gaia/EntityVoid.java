package com.meteor.extrabotany.common.entity.gaia;

import com.meteor.extrabotany.common.entity.EntityThrowableCopy;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.List;

public class EntityVoid extends EntityThrowableCopy {

    public EntityVoid(World worldIn) {
        super(worldIn);
    }

    public EntityVoid(EntityLivingBase thrower) {
        super(thrower.world);
    }

    @Override
    protected void entityInit() {

    }

    @Override
    public void onUpdate() {
        this.motionX = this.motionY = this.motionZ = 0;
        if (this.ticksExisted > 60)
            setDead();
        if (!getPlayerAround().isEmpty())
            for (EntityLivingBase player : getPlayerAround()) {
                player.motionX *= 0.12F;
                player.motionY *= 0.12F;
                player.motionZ *= 0.12F;
            }
    }

    private List<EntityLivingBase> getPlayerAround() {
        BlockPos source = this.getPosition();
        float range = 1.5F;
        return world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range, source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
    }

    @Override
    protected void onImpact(RayTraceResult result) {

    }

}
