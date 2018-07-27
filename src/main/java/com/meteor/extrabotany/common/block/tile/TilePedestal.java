package com.meteor.extrabotany.common.block.tile;

import com.meteor.extrabotany.common.core.handler.ConfigHandler;
import com.meteor.extrabotany.common.crafting.recipe.RecipePedestal;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TilePedestal extends TileEntity implements ITickable{

	private ItemStack item = ItemStack.EMPTY;
	private int rot;
	private boolean isDirty;
	public int strikes = 0;

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound){
		super.readFromNBT(nbttagcompound);
		NBTTagCompound nbtItem = nbttagcompound.getCompoundTag("Item");
		item = new ItemStack(nbtItem);
		rot = nbttagcompound.getInteger("Rot");
		strikes = nbttagcompound.getInteger("strikes");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound){
		super.writeToNBT(nbttagcompound);
		NBTTagCompound nbtItem = new NBTTagCompound();
		if(!item.isEmpty())
			item.writeToNBT(nbtItem);
		nbttagcompound.setTag("Item", nbtItem);
		nbttagcompound.setInteger("Rot", rot);
		nbttagcompound.setInteger("strikes", strikes);

		return nbttagcompound;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag(){
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet){
		readFromNBT(packet.getNbtCompound());
	}

	@Override
	public void update(){
		if(isDirty){
			world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 2);
			isDirty = false;
		}

		if(rot == 360)
			rot = 0;
		if(!item.isEmpty())
			rot++;
		if (!getWorld().isRemote) {
			if (getStrikes() >= ConfigHandler.CRAFTING_STRIKES) {
				ItemStack input = getItem();
				ItemStack output = RecipePedestal.getOutput(getItem());
					if (output.isEmpty() || output.equals(input)) {
						setStrikes(0);
						return;
					} else {
						setItem(output);
						setStrikes(0);
						EntityXPOrb orb = new EntityXPOrb(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 1);
						world.spawnEntity(orb);
					}
			}
		}
	}
	
	public void markForUpdate() {
        IBlockState state = this.getWorld().getBlockState(this.getPos());
        this.getWorld().notifyBlockUpdate(this.getPos(), state, state, 3);
    }
	
	public void setStrikes(int strikes) {
		this.strikes = strikes;
		markForUpdate();
	}

	public int getStrikes() {
		return strikes;
	}

	public int getRotation(){
		return rot;
	}

	public ItemStack getItem(){
		return item;
	}

	public void setItem(ItemStack item){
		this.item = item;
		isDirty = true;
	}
}
