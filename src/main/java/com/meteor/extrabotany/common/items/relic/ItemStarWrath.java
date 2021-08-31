package com.meteor.extrabotany.common.items.relic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemTier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import vazkii.botania.common.entity.EntityFallingStar;

public class ItemStarWrath extends ItemSwordRelic{

    public ItemStarWrath(Properties prop) {
        super(ItemTier.DIAMOND, 6, -1.6F, prop);
    }

    public void attackEntity(LivingEntity player, Entity target){
        BlockPos targetpos = target == null ? raytraceFromEntity(player, 64F, true).getPos().add(0, 1, 0) : new BlockPos(target.getPositionVec()).add(0, 1, 0);

        for(int i = 0; i < 5; i++) {
            Vector3d posVec = Vector3d.copy(targetpos).add((0.5F - Math.random()) * 6F, 0, (0.5F - Math.random()) * 6F);
            Vector3d motVec = new Vector3d((0.5 * Math.random() - 0.25) * 18, 24, (0.5 * Math.random() - 0.25) * 18);
            posVec = posVec.add(motVec);
            motVec = motVec.normalize().inverse().scale(1.5);

            EntityFallingStar star = new EntityFallingStar(player, player.world);
            star.setPosition(posVec.x, posVec.y, posVec.z);
            star.setMotion(motVec);
            player.world.addEntity(star);
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
