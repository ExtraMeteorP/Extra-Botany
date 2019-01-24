package com.meteor.extrabotany.common.item.equipment.bauble.stone;

import com.meteor.extrabotany.common.item.equipment.bauble.ItemBaubleRelic;
import com.meteor.extrabotany.common.lib.LibItemsName;

import baubles.api.BaubleType;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import vazkii.botania.api.mana.ManaItemHandler;

public class ItemTimeStone extends ItemBaubleRelic{
	
	private static final int COST = 80;

	public ItemTimeStone() {
		super(LibItemsName.STONETIME);
	}
	
	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		super.onWornTick(stack, player);
		if(player instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) player;
			if(ManaItemHandler.requestManaExact(stack, p, COST, true))
				for(int x = -5; x < 5; x++)
					for(int y = -5; y < 5; y++)
						for(int z = -5; z < 5; z++){
							BlockPos newpos = p.getPosition().add(x,y,z);
							this.tickBlock(newpos, p.world);
						}
		}
	}
	
	private void tickBlock(BlockPos pos, World world){
        IBlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        if(block == null) 
        	return;
        if(block instanceof BlockFluidBase) 
        	return;
        if(block.getTickRandomly()){
            for(int i = 0; i < 2; i++){
                if(world.getBlockState(pos) != blockState) 
                	break;
                block.updateTick(world, pos, blockState, world.rand);
            }
        }
        if(block.hasTileEntity(world.getBlockState(pos))){
            TileEntity tile = world.getTileEntity(pos);
            if(tile != null && !tile.isInvalid()){
                for(int i = 0; i < 2; i++){
                	if(tile.isInvalid()) 
                    	break;
                    if(tile instanceof ITickable) 
                    	((ITickable) tile).update();
                }
            }
        }
    }

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.RING;
	}

}
