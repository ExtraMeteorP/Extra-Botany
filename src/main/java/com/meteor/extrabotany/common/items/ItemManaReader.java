package com.meteor.extrabotany.common.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import vazkii.botania.api.mana.IManaBlock;
import vazkii.botania.common.block.tile.mana.TilePool;

import javax.annotation.Nonnull;

public class ItemManaReader extends Item {

    public ItemManaReader(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public ActionResultType onItemUse(ItemUseContext ctx) {
        TileEntity tile = ctx.getWorld().getTileEntity(ctx.getPos());
        PlayerEntity player = ctx.getPlayer();
        int mana = 0;
        if (tile instanceof TilePool) {
            TilePool pool = (TilePool) tile;
            mana = pool.getCurrentMana();
        }else if(tile instanceof IManaBlock){
            IManaBlock t = (IManaBlock) tile;
            mana = t.getCurrentMana();
        }
        if(!ctx.getWorld().isRemote)
            player.sendMessage(new StringTextComponent(String.format("Mana:%d", mana)), Util.DUMMY_UUID);
        return ActionResultType.PASS;
    }
}
