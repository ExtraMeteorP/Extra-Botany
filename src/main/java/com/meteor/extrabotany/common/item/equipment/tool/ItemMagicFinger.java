package com.meteor.extrabotany.common.item.equipment.tool;

import com.meteor.extrabotany.api.item.IAdvancementReward;
import com.meteor.extrabotany.common.item.ItemMod;
import com.meteor.extrabotany.common.lib.LibAdvancements;
import com.meteor.extrabotany.common.lib.LibItemsName;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.block.tile.mana.TilePool;

public class ItemMagicFinger extends ItemMod implements IAdvancementReward{

	public ItemMagicFinger() {
		super(LibItemsName.MAGICFINGER);
		this.setMaxStackSize(1);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        TileEntity te = worldIn.getTileEntity(pos);
        if(te instanceof TilePool){
        	TilePool p = (TilePool)te;
        	if(!p.isFull()) {
        		int manaToUse = Math.min(10000, p.manaCap - p.getCurrentMana());
        		if(ManaItemHandler.requestManaExact(player.getHeldItem(hand), player, manaToUse, true))
        			p.recieveMana(manaToUse);
        	}
        }
        return EnumActionResult.PASS;
	}

	@Override
	public String getAdvancementName(ItemStack stack) {
		return LibAdvancements.MAGICFINGERGET;
	}
        

}
