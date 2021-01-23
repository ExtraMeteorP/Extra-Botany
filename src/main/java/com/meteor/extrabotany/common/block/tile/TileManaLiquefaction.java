package com.meteor.extrabotany.common.block.tile;

import java.util.List;

import com.google.common.base.Predicates;
import com.meteor.extrabotany.common.block.fluid.ModFluid;
import com.meteor.extrabotany.common.core.config.ConfigHandler;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import vazkii.botania.api.mana.IManaReceiver;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import vazkii.botania.api.mana.spark.ISparkEntity;
import vazkii.botania.common.block.tile.TileMod;
import vazkii.botania.common.block.tile.mana.TileSpreader;

public class TileManaLiquefaction extends TileMod implements ITickable, IManaReceiver, ISparkAttachable{

	private static final String TAG_MANA = "mana";
	private static final String TAG_ENERGY = "energy";
	int mana;
	public int energy = ConfigHandler.INITIAL_LIQUEFACTION_ENERGY;
	private static final int MAX_ENERGY = 16000;

	@Override
	public void update() {

		if(!ConfigHandler.DISABLE_MANALIQUEFICATION)
			return;

		int redstoneSignal = 0;
		for(EnumFacing dir : EnumFacing.VALUES) {
			int redstoneSide = this.getWorld().getRedstonePower(this.getPos().offset(dir), dir);
			redstoneSignal = Math.max(redstoneSignal, redstoneSide);
		}

		for(EnumFacing e : EnumFacing.VALUES) {
			BlockPos neighbor = getPos().offset(e);
			if(!world.isBlockLoaded(neighbor))
				continue;

			TileEntity te = world.getTileEntity(neighbor);
			if(te == null)
				continue;

			IFluidHandler storage = null;

			if(te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, e.getOpposite())) {
				storage = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, e.getOpposite());
			} else if(te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
				storage = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
			}

			if(storage != null) {
				if(redstoneSignal == 0){
					if(!isFull()){
						if(storage.drain(new FluidStack(ModFluid.fluidMana, ConfigHandler.LIQUEFACTION_STORAGE_DRAIN_CONTAINER), true)!=null)
							energy += ConfigHandler.LIQUEFACTION_STORAGE_DRAIN;
					}
				}else{
					if(energy >= ConfigHandler.LIQUEFACTION_STORAGE_PUMP) {
						energy -= ConfigHandler.LIQUEFACTION_STORAGE_PUMP;
						storage.fill(new FluidStack(ModFluid.fluidMana, ConfigHandler.LIQUEFACTION_STORAGE_PUMP_CONTAINER), true);
					}
				}
			}

			int speed = ConfigHandler.MG_TRANSFERSPEED;

			if(te instanceof TileSpreader && redstoneSignal == 0){
				TileSpreader p = (TileSpreader) te;
				if(getCurrentMana() >= speed && p.getCurrentMana() < p.getMaxMana()){
					int current = Math.min(speed, p.getMaxMana() - p.getCurrentMana());
					p.recieveMana(current);
					recieveMana(-current);
				}
			}
		}

		if(redstoneSignal == 0){
			if(energy > 0 && getCurrentMana() <= ConfigHandler.MAX_LIQUEFACTION_MANA - ConfigHandler.LIQUEFACTION_MANA_RECEIVE){
				recieveMana(ConfigHandler.LIQUEFACTION_MANA_RECEIVE);
				energy -= ConfigHandler.LIQUEFACTION_ENERGY_LOSS;
			}
		}else{
			if(getCurrentMana() >= ConfigHandler.LIQUEFACTION_MANA_GIVE){
				recieveMana(-ConfigHandler.LIQUEFACTION_MANA_GIVE);
				energy += ConfigHandler.LIQUEFACTION_ENERGY_GAIN;
			}
		}

	}

	@Override
	public boolean canRecieveManaFromBursts() {
		return true;
	}

	@Override
	public boolean isFull() {
		return this.mana >= ConfigHandler.MAX_LIQUEFACTION_MANA;
	}

	@Override
	public void recieveMana(int mana) {
		this.mana = Math.min(ConfigHandler.MAX_LIQUEFACTION_MANA, this.mana + mana);
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
		int space = Math.max(0, ConfigHandler.MAX_LIQUEFACTION_MANA - getCurrentMana());
		if(space > 0)
			return space;
		else return 0;
	}

}
