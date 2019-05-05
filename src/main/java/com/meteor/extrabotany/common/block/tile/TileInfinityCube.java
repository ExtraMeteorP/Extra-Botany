package com.meteor.extrabotany.common.block.tile;

import com.google.common.base.Predicates;
import com.meteor.extrabotany.common.block.BlockInfinityCube;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import vazkii.botania.api.mana.IManaReceiver;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import vazkii.botania.api.mana.spark.ISparkEntity;

import java.util.List;

public class TileInfinityCube extends TileEntity implements ITickable, IManaReceiver, ISparkAttachable{
	
	private static final String TAG_MANA = "mana";
	private static final String TAG_ROTATION = "rotation";
	private int mana;
	private int rot;
	private static final int MAX_MANA = 1000000;
	private static final int COST = 150;

	@Override
	public void update() {
		if(rot == 360)
			rot = 0;
			rot++;
		if(this.world.isRemote) 
			return;
		if(this.getCurrentMana() >= COST)
			this.recieveMana(-COST);
		else return;
		for(int x = -4; x < 4; x++)
			for(int y = -4; y < 4; y++)
				for(int z = -4; z < 4; z++){
					BlockPos newpos = this.getPos().add(x,y,z);
					if(newpos == this.getPos())
						continue;
					this.tickBlock(newpos);
				}
	}
	
	    
	  private void tickBlock(BlockPos pos)
	    {
	        IBlockState blockState = this.world.getBlockState(pos);
	        Block block = blockState.getBlock();
	        if(block == null) 
	        	return;
	        if(block instanceof BlockFluidBase) 
	        	return;
	        if(block instanceof BlockInfinityCube) 
	        	return;
	        if(block.getTickRandomly())
	        {
	            for(int i = 0; i < 1; i++)
	            {
	                if(getWorld().getBlockState(pos) != blockState) 
	                	break;
	                block.updateTick(this.world, pos, blockState, this.world.rand);
	            }
	        }
	        if(block.hasTileEntity(this.world.getBlockState(pos)))
	        {
	            TileEntity tile = this.world.getTileEntity(pos);
	            if(tile != null && !tile.isInvalid())
	            {
	                for(int i = 0; i < 1; i++)
	                {
	                    if(tile.isInvalid()) 
	                    	break;
	                    if(tile instanceof ITickable) 
	                    	((ITickable) tile).update();
	                }
	            }
	        }
	    }
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound cmp){
		super.writeToNBT(cmp);
		cmp.setInteger(TAG_MANA, mana);
		cmp.setInteger(TAG_ROTATION, rot);
		return cmp;
	}

	@Override
	public void readFromNBT(NBTTagCompound cmp) {
		super.readFromNBT(cmp);
		mana = cmp.getInteger(TAG_MANA);
		rot = cmp.getInteger(TAG_ROTATION);
	}

	@Override
	public boolean areIncomingTranfersDone() {
		return false;
	}

	@Override
	public void attachSpark(ISparkEntity arg0) {}

	@Override
	public boolean canAttachSpark(ItemStack arg0) {
		return true;
	}

	@Override
	public ISparkEntity getAttachedSpark() {
		List sparks = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos.up(), pos.up().add(1, 1, 1)), Predicates.instanceOf(ISparkEntity.class));
		if(sparks.size() == 1) {
			Entity e = (Entity) sparks.get(0);
			return (ISparkEntity) e;
		}
		return null;
	}

	@Override
	public int getAvailableSpaceForMana() {
		int space = Math.max(0, 1000000 - getCurrentMana());
		if(space > 0)
			return space;
		else return 0;
	}

	@Override
	public boolean isFull() {
		return mana >= MAX_MANA;
	}

	@Override
	public void recieveMana(int mana) {
		this.mana = Math.min(1000000, this.mana + mana);
	}

	@Override
	public int getCurrentMana() {
		return mana;
	}

	@Override
	public boolean canRecieveManaFromBursts() {
		return true;
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket(){
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new SPacketUpdateTileEntity(getPos(), -999, nbt);
	}
	    
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt){
		super.onDataPacket(net, pkt);
		this.readFromNBT(pkt.getNbtCompound());
	}
	    
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState){
		return oldState.getBlock() != newState.getBlock();
	}
	
	public int getRotation(){
		return rot;
	}

}
