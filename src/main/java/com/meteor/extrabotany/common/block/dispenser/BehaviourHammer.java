package com.meteor.extrabotany.common.block.dispenser;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.api.item.IHammer;
import com.meteor.extrabotany.common.block.tile.TilePedestal;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BehaviourHammer extends BehaviorDefaultDispenseItem{
	
	public BehaviourHammer() {
		
	}

	@Nonnull
	@Override
	public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack stack) {
		EnumFacing facing = par1IBlockSource.getBlockState().getValue(BlockDispenser.FACING);
		BlockPos pos = par1IBlockSource.getBlockPos().offset(facing);
		World world = par1IBlockSource.getWorld();
		if(stack.getItem() instanceof IHammer && world.getTileEntity(pos) instanceof TilePedestal){
			TilePedestal te = (TilePedestal) world.getTileEntity(pos);
			te.setStrikes(te.getStrikes()+Math.min(5, stack.getMaxDamage() - stack.getItemDamage()));
			stack.setItemDamage(stack.getItemDamage() + 5);
			if(stack.getItemDamage() >= stack.getMaxDamage())
				stack.shrink(1);
			world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_ANVIL_HIT, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() - world.rand.nextFloat() * 0.2F + 1, false);
			return stack;
		}
		return super.dispenseStack(par1IBlockSource, stack);	
	}

}
