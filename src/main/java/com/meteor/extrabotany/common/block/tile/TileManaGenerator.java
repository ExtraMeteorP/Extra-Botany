package com.meteor.extrabotany.common.block.tile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.meteor.extrabotany.common.core.handler.ConfigHandler;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import vazkii.botania.api.mana.IManaReceiver;
import vazkii.botania.common.block.tile.TileMod;
import vazkii.botania.common.block.tile.mana.TileSpreader;

public class TileManaGenerator extends TileMod implements ITickable, IManaReceiver{
	
	private static final String TAG_MANA = "mana";
	private static final String TAG_ENERGY = "energy";
	int mana;
	int energy = 0;
	private static final int MAX_ENERGY = ConfigHandler.MG_MAXENERGY;

	private final IEnergyStorage energyHandler = new IEnergyStorage() {
		@Override
		public int getEnergyStored() {
			return energy;
		}

		@Override
		public int getMaxEnergyStored() {
			return MAX_ENERGY;
		}

		@Override public boolean canExtract() { return false; }
		@Override public int extractEnergy(int maxExtract, boolean simulate) { return 0; }

		@Override public int receiveEnergy(int maxReceive, boolean simulate) { return 0; }
		@Override public boolean canReceive() { return false; }
	};
	
	@Override
	public boolean hasCapability(@Nonnull Capability<?> cap, @Nullable EnumFacing side) {
		return cap == CapabilityEnergy.ENERGY || super.hasCapability(cap, side);
	}

	@Override
	@Nullable
	public <T> T getCapability(@Nonnull Capability<T> cap, @Nullable EnumFacing side) {
		if(cap == CapabilityEnergy.ENERGY) {
			return CapabilityEnergy.ENERGY.cast(energyHandler);
		} else return super.getCapability(cap, side);
	}

	@Override
	public void update() {
		
		int speed = ConfigHandler.MG_TRANSFERSPEED;
		
		for(EnumFacing e : EnumFacing.VALUES) {
			BlockPos neighbor = getPos().offset(e);
			if(!world.isBlockLoaded(neighbor))
				continue;

			TileEntity te = world.getTileEntity(neighbor);
			if(te == null)
				continue;

			IEnergyStorage storage = null;

			if(te.hasCapability(CapabilityEnergy.ENERGY, e.getOpposite())) {
				storage = te.getCapability(CapabilityEnergy.ENERGY, e.getOpposite());
			} else if(te.hasCapability(CapabilityEnergy.ENERGY, null)) {
				storage = te.getCapability(CapabilityEnergy.ENERGY, null);
			}

			if(storage != null) {
				energy += storage.extractEnergy(1000, false);
			}
			
			if(te instanceof TileSpreader){
				TileSpreader p = (TileSpreader) world.getTileEntity(pos.add(new BlockPos(0, 1, 0)));
				if(getCurrentMana() >= speed && p.getCurrentMana() < p.getMaxMana()){
					int current = Math.min(speed, p.getMaxMana() - p.getCurrentMana());
					p.recieveMana(current);
					recieveMana(-current);
				}
			}
		}
		
		if(energy >= 1000){
			energy -=1000;
			mana +=ConfigHandler.MG_CONVERT;
		}
		
	}

	@Override
	public boolean canRecieveManaFromBursts() {
		return true;
	}

	@Override
	public boolean isFull() {
		return energy >= MAX_ENERGY;
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
	public void writePacketNBT(NBTTagCompound cmp) {
		cmp.setInteger(TAG_MANA, mana);
		cmp.setInteger(TAG_ENERGY, energy);
	}

	@Override
	public void readPacketNBT(NBTTagCompound cmp) {
		mana = cmp.getInteger(TAG_MANA);
		energy = cmp.getInteger(TAG_ENERGY);
	}

}
