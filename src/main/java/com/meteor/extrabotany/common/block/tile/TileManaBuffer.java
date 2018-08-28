package com.meteor.extrabotany.common.block.tile;

import com.meteor.extrabotany.common.core.handler.ConfigHandler;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.mana.IManaReceiver;
import vazkii.botania.api.mana.IThrottledPacket;
import vazkii.botania.common.block.tile.mana.TilePool;

public class TileManaBuffer extends TileEntity implements IManaReceiver, ITickable, IThrottledPacket{
	
	private static final BlockPos[] POOL_LOCATIONS = {
			new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, -1, 0)
	};
	
	private static final BlockPos[] POOL_LOCATIONS2 = {
			new BlockPos(0, 1, 0)
	};
	
	private static final String TAG_MANA = "mana";
	private static final String TAG_KNOWN_MANA = "knownMana";
	private static final String TAG_MANA_CAP = "manaCap";
	
	int mana;
	
	public int manaCap = 64000000;
	
	boolean isDoingTransfer = false;
	int ticksDoingTransfer = 0;
	
	private boolean sendPacket = false;
	private int ticks = 0;

	@Override
	public boolean canRecieveManaFromBursts() {
		return false;
	}

	@Override
	public boolean isFull() {
		return true;
	}

	@Override
	public void recieveMana(int mana) {
		int old = this.mana;
		this.mana = Math.max(0, Math.min(getCurrentMana() + mana, manaCap));
		if(old != this.mana) {
			world.updateComparatorOutputLevel(pos, world.getBlockState(pos).getBlock());
			markDispatchable();
		}
	}
	
	public static int calculateComparatorLevel(int mana, int max) {
		int val = (int) ((double) mana / (double) max * 15.0);
		if(mana > 0)
			val = Math.max(val, 1);
		return val;
	}

	@Override
	public int getCurrentMana() {
		if(world != null) {
			return mana;
		}	
		return 0;
	}

	@Override
	public void update() {

		boolean wasDoingTransfer = isDoingTransfer;
		isDoingTransfer = false;

		if(sendPacket && ticks % 10 == 0) {
			VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
			sendPacket = false;
		}

		if(isDoingTransfer)
			ticksDoingTransfer++;
		else {
			ticksDoingTransfer = 0;
			if(wasDoingTransfer)
				VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
		}
		
		int speed = ConfigHandler.MB_SPEED;
		
		for(BlockPos o : POOL_LOCATIONS)//input
			if(world.getTileEntity(pos.add(o)) instanceof TilePool){
				TilePool p = (TilePool) world.getTileEntity(pos.add(o));
				if(p.getCurrentMana() >= speed && getCurrentMana() < this.manaCap){
					int current = Math.min(speed, this.manaCap - getCurrentMana());
					p.recieveMana(-current);
					recieveMana(current);
				}
			}else if(world.getTileEntity(pos.add(o)) instanceof TileManaBuffer){
				TileManaBuffer p = (TileManaBuffer) world.getTileEntity(pos.add(o));
				if(p.getCurrentMana() >= speed && getCurrentMana() < this.manaCap){
					int current = Math.min(speed, this.manaCap - getCurrentMana());
					p.recieveMana(-current);
					recieveMana(current);
				}
			}
		
		for(BlockPos o : POOL_LOCATIONS2)//output
			if(world.getTileEntity(pos.add(o)) instanceof TilePool){
				TilePool p = (TilePool) world.getTileEntity(pos.add(0, 1, 0));
				if(getCurrentMana() >= speed && p.getCurrentMana() < p.manaCap){
					int current = Math.min(speed, p.manaCap - p.getCurrentMana());
					p.recieveMana(current);
					recieveMana(-current);
				}
			}else if(world.getTileEntity(pos.add(o)) instanceof TileManaBuffer){
				TileManaBuffer p = (TileManaBuffer) world.getTileEntity(pos.add(o));
				if(getCurrentMana() >= speed && p.getCurrentMana() < p.manaCap){
					int current = Math.min(speed, p.manaCap - p.getCurrentMana());
					p.recieveMana(current);
					recieveMana(-current);
				}
			}
		
		ticks++;
		
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound cmp) {
		super.writeToNBT(cmp);
		cmp.setInteger(TAG_MANA, mana);
		cmp.setInteger(TAG_MANA_CAP, manaCap);
		return cmp;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound cmp) {
		super.readFromNBT(cmp);
		mana = cmp.getInteger(TAG_MANA);
		manaCap = cmp.getInteger(TAG_MANA_CAP);
	}
	
	@Override
	public void markDispatchable() {
		sendPacket = true;
	}
	
}
