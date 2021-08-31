package com.meteor.extrabotany.common.items;

import com.meteor.extrabotany.common.entities.ego.EntityEGO;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemChallengeTicket extends Item {

    public ItemChallengeTicket(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand handIn) {
        ItemStack itemstack = player.getHeldItem(handIn);
        //EntityEGOMinion.spawn(worldIn, player.getPosition(), 60);
        //EntityEGOLandmine.spawnLandmine(4, worldIn, player.getPosition(), null);
        return  ActionResult.resultPass(itemstack);
    }

    @Nonnull
    @Override
    public ActionResultType onItemUse(ItemUseContext ctx) {
        ItemStack stack = ctx.getItem();
        return EntityEGO.spawn(ctx.getPlayer(), stack, ctx.getWorld(), ctx.getPos()) ? ActionResultType.SUCCESS : ActionResultType.FAIL;
    }
}
