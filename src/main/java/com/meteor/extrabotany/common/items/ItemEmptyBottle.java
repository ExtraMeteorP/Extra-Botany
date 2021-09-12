package com.meteor.extrabotany.common.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import vazkii.botania.common.block.tile.mana.TilePool;

import javax.annotation.Nonnull;

public class ItemEmptyBottle extends Item {

    public ItemEmptyBottle(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public ActionResultType onItemUse(ItemUseContext ctx) {
        ItemStack stack = ctx.getItem();
        TileEntity tile = ctx.getWorld().getTileEntity(ctx.getPos());
        if(tile instanceof TilePool){
            TilePool pool = (TilePool) tile;
            if(!ctx.getWorld().isRemote && pool.getCurrentMana() >= 25000){
                pool.receiveMana(-25000);
                if(!ctx.getPlayer().abilities.isCreativeMode){
                    stack.shrink(1);
                }

                if(stack.isEmpty()){
                    ctx.getPlayer().setHeldItem(ctx.getHand(), new ItemStack(ModItems.manadrink));
                }else {
                    ctx.getPlayer().addItemStackToInventory(new ItemStack(ModItems.manadrink));
                }

                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }
}
