package com.meteor.extrabotany.common.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemTreasureBox extends Item {

    public ItemTreasureBox(Properties prop) {
        super(prop);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand handIn) {
        ItemStack itemstack = player.getHeldItem(handIn);
        if(!worldIn.isRemote) {
            player.entityDropItem(new ItemStack(ModItems.rewardbaga, 32)).setNoPickupDelay();
            player.entityDropItem(new ItemStack(ModItems.rewardbagb, 16)).setNoPickupDelay();
            player.entityDropItem(new ItemStack(ModItems.rewardbagc, 10)).setNoPickupDelay();
            player.entityDropItem(new ItemStack(ModItems.rewardbagd, 10)).setNoPickupDelay();
            player.entityDropItem(new ItemStack(ModItems.heromedal)).setNoPickupDelay();
            if (!player.abilities.isCreativeMode) {
                itemstack.shrink(1);
            }
        }
        return ActionResult.resultPass(itemstack);
    }

}
