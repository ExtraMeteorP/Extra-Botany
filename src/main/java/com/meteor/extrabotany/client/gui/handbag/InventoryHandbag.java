package com.meteor.extrabotany.client.gui.handbag;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;

public class InventoryHandbag implements IItemHandlerModifiable {

	private final IItemHandlerModifiable boxInv;
	final ItemStack box;

	public InventoryHandbag(ItemStack box) {
		this.box = box;
		boxInv = (IItemHandlerModifiable) box.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
	}

	@Override
	public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
		boxInv.setStackInSlot(slot, stack);
	}

	@Override
	public int getSlots() {
		return boxInv.getSlots();
	}

	@Nonnull
	@Override
	public ItemStack getStackInSlot(int slot) {
		return boxInv.getStackInSlot(slot);
	}

	@Nonnull
	@Override
	public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
		return boxInv.insertItem(slot, stack, simulate);
	}

	@Nonnull
	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		return boxInv.extractItem(slot, amount, simulate);
	}

	@Override
	public int getSlotLimit(int slot) {
		return boxInv.getSlotLimit(slot);
	}
}
