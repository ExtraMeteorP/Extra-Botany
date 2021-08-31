package com.meteor.extrabotany.common.items.relic;

import com.meteor.extrabotany.common.core.Helper;
import com.meteor.extrabotany.common.entities.projectile.EntityInfluxWaverProjectile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemTier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class ItemInfluxWaver extends ItemSwordRelic {

    public ItemInfluxWaver(Properties prop) {
        super(ItemTier.DIAMOND, 5, -2F, prop);
    }

    public void attackEntity(LivingEntity player, Entity target){
        Vector3d targetpos = target == null ? Helper.PosToVec(raytraceFromEntity(player, 64F, true).getPos()).add(0, 1, 0) : target.getPositionVec().add(0, 1, 0);
        EntityInfluxWaverProjectile proj = new EntityInfluxWaverProjectile(player.world, player);
        proj.setPosition(player.getPosX(), player.getPosY()+0.5D, player.getPosZ());
        proj.setTargetPos(targetpos);
        proj.faceTargetAccurately(0.7F);
        proj.setStrikeTimes(3);
        player.world.addEntity(proj);
    }

    @Override
    public void onLeftClick(PlayerEntity player, Entity target) {
        if (!player.world.isRemote && !player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() == this
                && player.getCooledAttackStrength(0) == 1) {
            attackEntity(player, target);
        }
    }

}
