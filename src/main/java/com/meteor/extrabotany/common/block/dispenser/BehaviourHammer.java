package com.meteor.extrabotany.common.block.dispenser;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.api.item.IHammer;
import com.meteor.extrabotany.common.block.tile.TilePedestal;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BehaviourHammer extends BehaviorDefaultDispenseItem{
	
	public BehaviourHammer() {
		
	}

	@Nonnull
	@Override
	public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack) {
		EnumFacing facing = par1IBlockSource.getBlockState().getValue(BlockDispenser.FACING);
		BlockPos pos = par1IBlockSource.getBlockPos().offset(facing);
		World world = par1IBlockSource.getWorld();
		if(par2ItemStack.getItem() instanceof IHammer && world.getTileEntity(pos) instanceof TilePedestal){
			TilePedestal te = (TilePedestal) world.getTileEntity(pos);
			te.setStrikes(te.getStrikes()+5);
			par2ItemStack.setItemDamage(par2ItemStack.getItemDamage() + 5);
			world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_ANVIL_HIT, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() - world.rand.nextFloat() * 0.2F + 1, false);
			return par2ItemStack;
		}
		return super.dispense(par1IBlockSource, par2ItemStack);	
	}

}
