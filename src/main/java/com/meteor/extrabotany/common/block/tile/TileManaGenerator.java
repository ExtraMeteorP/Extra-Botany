package com.meteor.extrabotany.common.block.tile;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Predicates;
import com.meteor.extrabotany.common.core.config.ConfigHandler;

import cofh.redstoneflux.api.IEnergyConnection;
import cofh.redstoneflux.api.IEnergyReceiver;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.Optional;
import vazkii.botania.api.mana.IManaReceiver;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import vazkii.botania.api.mana.spark.ISparkEntity;
import vazkii.botania.common.block.tile.TileMod;
import vazkii.botania.common.block.tile.mana.TileSpreader;

public class TileManaGenerator extends TileMod implements ITickable, IManaReceiver, ISparkAttachable {

	private static final String TAG_MANA = "mana";
	private static final String TAG_ENERGY = "energy";
	int mana;
	public int energy = 0;
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

		@Override
		public boolean canExtract() {
			return getEnergyStored() > 0;
		}

		@Override
		public int extractEnergy(int maxExtract, boolean simulate) {
			int available = this.getEnergyStored();
			if (!simulate)
				energy = Math.max(this.getEnergyStored() - maxExtract, 0);
			return Math.min(maxExtract, available);
		}

		@Override
		public int receiveEnergy(int maxReceive, boolean simulate) {
			int space = getMaxEnergyStored() - this.getEnergyStored();
			if (!simulate)
				energy = Math.min(this.getEnergyStored() + maxReceive, getMaxEnergyStored());
			return Math.min(space, maxReceive);
		}

		@Override
		public boolean canReceive() {
			return getEnergyStored() < getMaxEnergyStored();
		}
	};

	@Override
	public boolean hasCapability(@Nonnull Capability<?> cap, @Nullable EnumFacing side) {
		return cap == CapabilityEnergy.ENERGY || super.hasCapability(cap, side);
	}

	@Override
	@Nullable
	public <T> T getCapability(@Nonnull Capability<T> cap, @Nullable EnumFacing side) {
		if (cap == CapabilityEnergy.ENERGY) {
			return CapabilityEnergy.ENERGY.cast(energyHandler);
		} else
			return super.getCapability(cap, side);
	}

	@Override
	public void update() {

		if (!ConfigHandler.DISABLE_MANAGENERATOR)
			return;

		int speed = ConfigHandler.MG_TRANSFERSPEED;

		for (EnumFacing e : EnumFacing.VALUES) {
			BlockPos neighbor = getPos().offset(e);
			if (!world.isBlockLoaded(neighbor))
				continue;

			TileEntity te = world.getTileEntity(neighbor);
			if (te == null)
				continue;

			IEnergyStorage storage = null;

			if (te.hasCapability(CapabilityEnergy.ENERGY, e.getOpposite())) {
				storage = te.getCapability(CapabilityEnergy.ENERGY, e.getOpposite());
			} else if (te.hasCapability(CapabilityEnergy.ENERGY, null)) {
				storage = te.getCapability(CapabilityEnergy.ENERGY, null);
			}

			if (storage != null) {
				recieveEnergy(storage.extractEnergy(1000, false));
			}

			if (te instanceof TileSpreader) {
				TileSpreader p = (TileSpreader) te;
				if (getCurrentMana() >= speed && p.getCurrentMana() < p.getMaxMana()) {
					int current = Math.min(speed, p.getMaxMana() - p.getCurrentMana());
					p.recieveMana(current);
					recieveMana(-current);
				}
			}
		}

		if (energy >= 1000) {
			recieveEnergy(-1000);
			recieveMana(ConfigHandler.MG_CONVERT);
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

	public void recieveEnergy(int mana) {
		this.energy = Math.min(MAX_ENERGY, this.energy + mana);
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

	@Override
	public boolean areIncomingTranfersDone() {
		return false;
	}

	@Override
	public void attachSpark(ISparkEntity arg0) {
	}

	@Override
	public boolean canAttachSpark(ItemStack arg0) {
		return true;
	}

	@Override
	public ISparkEntity getAttachedSpark() {
		List sparks = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos.up(), pos.up().add(1, 1, 1)),
				Predicates.instanceOf(ISparkEntity.class));
		if (sparks.size() == 1) {
			Entity e = (Entity) sparks.get(0);
			return (ISparkEntity) e;
		}
		return null;
	}

	@Override
	public int getAvailableSpaceForMana() {
		int space = Math.max(0, 1000000 - getCurrentMana());
		if (space > 0)
			return space;
		else
			return 0;
	}

}
