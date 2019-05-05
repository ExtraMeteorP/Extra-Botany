package com.meteor.extrabotany.common.block.tile;

import com.meteor.extrabotany.common.core.network.ExtraBotanyNetwork;
import com.meteor.extrabotany.common.core.network.FluidUpdatePacket;
import com.meteor.extrabotany.common.core.network.FluidUpdatePacket.IFluidPacketReceiver;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import vazkii.botania.common.block.tile.TileAltar;

import javax.annotation.Nullable;

public class TileLivingrockBarrel extends TileEntity implements ITickable, IFluidPacketReceiver{
	
	public FluidTank fluidTank = new FluidTank(16000){
        protected void onContentsChanged(){
        	TileLivingrockBarrel.this.onContentsChanged(0);
        };
    };

	@Override
	public void update() {
		
		TileAltar tile = null;
		for(EnumFacing dir : EnumFacing.VALUES) {
			if(this.getWorld().getTileEntity(this.getPos().offset(dir)) instanceof TileAltar)
				tile = (TileAltar) this.getWorld().getTileEntity(this.getPos().offset(dir));
			if(tile != null && tile.hasWater == false && fluidTank.getFluid() != null){
				if(fluidTank.getFluid().getFluid() == FluidRegistry.WATER && fluidTank.getFluidAmount() >= 1000){
					tile.setWater(true);
					fluidTank.drain(1000, true);
				}
			}
		}
	}

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        fluidTank.readFromNBT(compound.getCompoundTag("Fluid"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setTag("Fluid", fluidTank.writeToNBT(new NBTTagCompound()));
        return compound;
    }
	
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing){
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    @Nullable
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing){
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fluidTank);
        }
        return super.getCapability(capability, facing);
    }
    
    public void onContentsChanged(int slot){
        refresh();
    }
		
    void refresh(){
        if (hasWorld() && !world.isRemote){
            IBlockState state = world.getBlockState(pos);
            world.markAndNotifyBlock(pos, null, state, state, 11);
            world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), false);
            ExtraBotanyNetwork.sendToClients((WorldServer) world, getPos(), new FluidUpdatePacket(getPos(), fluidTank.getFluid()));
        }
    }

	@Override
	public void updateFluidTo(FluidStack fluid) {
		int oldAmount = fluidTank.getFluidAmount();
		fluidTank.setFluid(fluid);		
	}

}
