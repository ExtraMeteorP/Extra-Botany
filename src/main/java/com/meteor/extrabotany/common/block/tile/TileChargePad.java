package com.meteor.extrabotany.common.block.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import vazkii.botania.api.mana.IManaItem;
import vazkii.botania.api.mana.IManaPool;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.helper.Vector3;

public class TileChargePad extends TileInventoryBase implements ITickable{
	
	public TileChargePad() {
		super(1,1);
	}
	
	private boolean isDirty;
	private int rot;
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound){
		super.readFromNBT(nbttagcompound);
		rot = nbttagcompound.getInteger("Rot");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound){
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("Rot", rot);
		return nbttagcompound;
	}
	
	public int getRotation(){
		return rot;
	}

	@Override
	public void update() {
		if(isDirty){
			world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 2);
			isDirty = false;
		}
		
		if(rot == 360)
			rot = 0;
		if(!getItem().isEmpty())
			rot++;
		
		int redstoneSignal = 0;
		for(EnumFacing dir : EnumFacing.VALUES) {
			int redstoneSide = this.getWorld().getRedstonePower(this.getPos().offset(dir), dir);
			redstoneSignal = Math.max(redstoneSignal, redstoneSide);
		}
		boolean working = false;
		TileEntity te = this.getWorld().getTileEntity(this.getPos().add(0, 1, 0));
		if(te instanceof IManaPool) {
			IManaPool pool = (IManaPool) te;	
			if(!(this.getItem().getItem() instanceof IManaItem))
				return;
			IManaItem item = (IManaItem) this.getItem().getItem();
			int manaToUse = Math.min(1000, pool.getCurrentMana());
			int manaToCharge = item.getMaxMana(this.getItem()) - item.getMana(this.getItem());
			int mana = Math.min(manaToUse, manaToCharge);
			if(mana > 0 && redstoneSignal == 0) {
				if(!this.getWorld().isRemote) {
					item.addMana(this.getItem(), mana);
					pool.recieveMana(-mana);	
					this.markDirty();
				}
				Botania.proxy.sparkleFX(this.pos.getX() + 0.5F, this.pos.getY() + 0.5F, this.pos.getZ() + 0.5F, 0F, 0.8F, 1F, 0.5F, 4);
			}
			int manaToSpend = Math.min(1000, item.getMana(this.getItem()));
			if(!pool.isFull() && manaToSpend > 0 && redstoneSignal > 0) {
				if(!this.getWorld().isRemote) {
					item.addMana(this.getItem(), -manaToSpend);
					pool.recieveMana(manaToSpend);
					this.markDirty();
				}
				Botania.proxy.sparkleFX(this.pos.getX() + 0.5F, this.pos.getY() + 0.5F, this.pos.getZ() + 0.5F, 0F, 0.8F, 1F, 0.5F, 4);
			}				
		}
			
	}
	
    public boolean isItemValidForSlot(int index, ItemStack stack){
        return stack.getItem() instanceof IManaItem;
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
	
	public void markForUpdate() {
        IBlockState state = this.getWorld().getBlockState(this.getPos());
        this.getWorld().notifyBlockUpdate(this.getPos(), state, state, 3);
    }
	
	public ItemStack getItem(){
		return stacks.getStackInSlot(0);
	}

	public void setItem(ItemStack item){
		stacks.setStackInSlot(0, item);
		isDirty = true;
	}
	
}
