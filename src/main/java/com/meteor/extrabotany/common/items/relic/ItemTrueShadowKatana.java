package com.meteor.extrabotany.common.items.relic;

import com.meteor.extrabotany.common.core.Helper;
import com.meteor.extrabotany.common.entities.projectile.EntityTrueShadowKatanaProjectile;
import com.meteor.extrabotany.common.handler.DamageHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemTier;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import vazkii.botania.common.core.helper.Vector3;

import java.util.List;
import java.util.Random;

public class ItemTrueShadowKatana extends ItemSwordRelic {

    public ItemTrueShadowKatana(Properties prop) {
        super(ItemTier.DIAMOND, 5, -2F, prop);
    }

    public void attackEntity(LivingEntity player, Entity target){
        Vector3d targetpos = Vector3d.ZERO;

        float RANGE = 8F;
        AxisAlignedBB axis_ = new AxisAlignedBB(player.getPositionVec().add(-RANGE, -RANGE, -RANGE)
                , player.getPositionVec().add(RANGE + 1, RANGE + 1, RANGE + 1));

        List<LivingEntity> entities = player.world.getEntitiesWithinAABB(LivingEntity.class, axis_);
        List<LivingEntity> list = DamageHandler.INSTANCE.getFilteredEntities(entities, player);
        if(list.size() == 0) {
            targetpos = target == null ? Helper.PosToVec(raytraceFromEntity(player, 64F, true).getPos()).add(0, 1, 0) : target.getPositionVec().add(0, 1, 0);
        }else if(player instanceof MobEntity && ((MobEntity)player).getAttackTarget() != null && entities.contains(((MobEntity)player).getAttackTarget())){
            targetpos = ((MobEntity)player).getAttackTarget().getPositionVec();
        }else if(player.getLastAttackedEntity() != null && entities.contains(player.getLastAttackedEntity())){
            targetpos = player.getLastAttackedEntity().getPositionVec();
        }else {
            for(LivingEntity living : entities){
                targetpos = living.getPositionVec();
                break;
            }
        }

        for(int i = 0; i < 3; i++) {
            Vector3 look = new Vector3(player.getLookVec()).multiply(1, 0, 1);

            double playerRot = Math.toRadians(player.rotationYaw + 90);
            if (look.x == 0 && look.z == 0) {
                look = new Vector3(Math.cos(playerRot), 0, Math.sin(playerRot));
            }

            look = look.normalize().multiply(1.75);

            int div = i / 3;
            int mod = i % 3;

            Vector3 pl = look.add(Vector3.fromEntityCenter(player)).add(0, 0.1, div * 0.1);

            Vector3 axis = look.normalize().crossProduct(new Vector3(-1, 0, -1)).normalize();

            double rot = mod * Math.PI / 4 - Math.PI / 4;

            Vector3 axis1 = axis.multiply(div * 2.5 + 2).rotate(rot, look);
            if (axis1.y < 0) {
                axis1 = axis1.multiply(1, -1, 1);
            }

            Vector3 end = pl.add(axis1);

            EntityTrueShadowKatanaProjectile proj = new EntityTrueShadowKatanaProjectile(player.world, player);
            proj.setPosition(end.x, end.y, end.z);
            proj.setTargetPos(targetpos);
            proj.faceTargetAccurately(0.75F);
            player.world.addEntity(proj);
        }
    }

    @Override
    public void onLeftClick(PlayerEntity player, Entity target) {
        if (!player.world.isRemote && !player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() == this
                && player.getCooledAttackStrength(0) == 1) {
            attackEntity(player, target);
        }
    }

}
