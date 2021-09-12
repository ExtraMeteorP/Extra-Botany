package com.meteor.extrabotany.common.items;

import com.meteor.extrabotany.common.entities.projectile.EntityButterflyProjectile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemUUZFan extends Item {

    public ItemUUZFan(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand handIn) {
        ItemStack itemstack = player.getHeldItem(handIn);
        player.getCooldownTracker().setCooldown(this, 10);
        if(!worldIn.isRemote)
            for(int i = -1; i < 2; i++){
                EntityButterflyProjectile proj = new EntityButterflyProjectile(worldIn, player);
                proj.setPosition(player.getPosX(), player.getPosY(), player.getPosZ());
                proj.func_234612_a_(player, player.rotationPitch, player.rotationYaw + 25F * i, 0.0F, 0.5F, 1F);
                worldIn.addEntity(proj);
            }
        return ActionResult.resultPass(itemstack);
    }

}
