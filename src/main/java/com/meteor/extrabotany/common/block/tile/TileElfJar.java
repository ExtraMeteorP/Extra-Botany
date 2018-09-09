package com.meteor.extrabotany.common.block.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import vazkii.botania.common.block.tile.TileMod;

public class TileElfJar extends TileMod implements ITickable{
	
	public FluidTankBase fluidTank = new FluidTankBase(16000);

	@Override
	public void update() {
		this.getWorld().getBlockState(pos).getBlock().setLightLevel(fluidTank.getFluid() == null ? 0 : fluidTank.getFluid().getFluid().getLuminosity());
	}

	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		if (compound.hasKey("Fluid"))
			fluidTank.setFluid(FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("Fluid")));
		else
			fluidTank.setFluid(null);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		compound = super.writeToNBT(compound);
		if (fluidTank.getFluid() != null){
			NBTTagCompound fluidTag = new NBTTagCompound();
			fluidTank.getFluid().writeToNBT(fluidTag);
			compound.setTag("Fluid", fluidTag);
		}
		return compound;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ? (T) fluidTank : super.getCapability(capability, facing);
	}
	
	public class FluidTankBase extends FluidTank
	{
		public FluidTankBase(int capacity)
		{
			super(capacity);
		}

		@Override
		public int fill(FluidStack resource, boolean doFill)
		{
			if (resource == null || !canFillFluidType(resource))
				return 0;

			int amountFilled = super.fill(resource, doFill);

			return amountFilled;
		}

		@Override
		public FluidStack drain(FluidStack resource, boolean doDrain)
		{
			if (resource == null || !canDrainFluidType(resource))
				return null;

			return drain(resource.amount, doDrain);
		}

		@Override
		public FluidStack drain(int maxDrain, boolean doDrain)
		{
			FluidStack drainedStack = super.drain(maxDrain, doDrain);

			return drainedStack;
		}

		@Override
		public boolean canFillFluidType(FluidStack fluid)
		{
			return this.fluid == null || this.fluid.getFluid() == null || this.fluid.getFluid() == fluid.getFluid();
		}

		@Override
		public boolean canDrainFluidType(FluidStack fluid)
		{
			return this.fluid != null && this.fluid.getFluid() != null && this.fluid.getFluid() == fluid.getFluid();
		}
	}

}
