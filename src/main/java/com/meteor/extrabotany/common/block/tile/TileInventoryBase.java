package com.meteor.extrabotany.common.block.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class TileInventoryBase extends TileEntity{
    
	public class StackHandler extends ItemStackHandler{
        private final int stackLimit;

        StackHandler(TileInventoryBase tile, int i, int stackLimit){
            super(i);
            this.stackLimit = stackLimit;
        }

        @Override
        public int getSlotLimit(int slot){
            return stackLimit;
        }

        public List<ItemStack> getStacks(){
            return stacks;
        }

        public boolean isItemValid(int slot, ItemStack stack){
            return isItemValidForSlot(slot, stack);
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate){
            if (!isItemValid(slot, stack)){
                return stack;
            }
            return super.insertItem(slot, stack, simulate);
        }

        @Override
        protected void onContentsChanged(int slot){
            TileInventoryBase.this.onContentsChanged(slot);
        }
    }

    public final StackHandler stacks;

    @SuppressWarnings("deprecation")
    public TileInventoryBase(int slot){
        this(slot, Items.AIR.getItemStackLimit()); // Default to a standard stack
    }

    public TileInventoryBase(int slot, int stackLimit){
        stacks = new StackHandler(this, slot, stackLimit);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing){
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing){
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(stacks);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        if (compound.hasKey("Items", Constants.NBT.TAG_COMPOUND)){
            stacks.deserializeNBT(compound.getCompoundTag("Items"));
        }
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setTag("Items", stacks.serializeNBT());
        return compound;
    }
    
    @Override
    public void onDataPacket(NetworkManager manager, SPacketUpdateTileEntity packet){
        this.readPacketData(packet.getNbtCompound());
    }

    protected void readPacketData(NBTTagCompound data){
        this.stacks.deserializeNBT(data.getCompoundTag("Items"));
    }
    
    @Override
    public SPacketUpdateTileEntity getUpdatePacket(){
        return new SPacketUpdateTileEntity(this.pos, -1, this.writePacketData(new NBTTagCompound()));
    }

    protected NBTTagCompound writePacketData(NBTTagCompound data){
        data.setTag("Items", this.stacks.serializeNBT());
        return data;
    }

    public boolean isItemValidForSlot(int index, ItemStack stack){
        return true;
    }

    public void onContentsChanged(int slot){
        refresh();
    }
    
    protected void refresh(){
        if (hasWorld() && !world.isRemote){
            IBlockState state = world.getBlockState(pos);
            world.markAndNotifyBlock(pos, null, state, state, 11);
        }
    }
}
