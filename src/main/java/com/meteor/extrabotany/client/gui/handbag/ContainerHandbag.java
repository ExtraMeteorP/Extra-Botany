package com.meteor.extrabotany.client.gui.handbag;

import com.meteor.extrabotany.client.gui.SlotArmor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.SlotItemHandler;
import vazkii.botania.client.gui.SlotLocked;

import javax.annotation.Nonnull;
import java.util.List;

public class ContainerHandbag extends Container {

	private final InventoryHandbag baubleBoxInv;

	public ContainerHandbag(EntityPlayer player, InventoryHandbag boxInv) {
		int i;
		int j;

		IInventory playerInv = player.inventory;
		baubleBoxInv = boxInv;

		addSlotToContainer(new SlotArmor(playerInv, 39, 8, 8 + 0 * 18, EntityEquipmentSlot.HEAD));
		addSlotToContainer(new SlotArmor(playerInv, 38, 8, 8 + 1 * 18, EntityEquipmentSlot.CHEST));
		addSlotToContainer(new SlotArmor(playerInv, 37, 8, 8 + 2 * 18, EntityEquipmentSlot.LEGS));
		addSlotToContainer(new SlotArmor(playerInv, 36, 8, 8 + 3 * 18, EntityEquipmentSlot.FEET));

		for(i = 0; i < 4; ++i)
			for(j = 0; j < 6; ++j) {
				int k = j + i * 6;
				addSlotToContainer(new SlotItemHandler(baubleBoxInv, k, 62 + j * 18, 8 + i * 18));
			}

		for(i = 0; i < 3; ++i)
			for(j = 0; j < 9; ++j)
				addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

		for(i = 0; i < 9; ++i) {
			if(playerInv.getStackInSlot(i) == baubleBoxInv.box)
				addSlotToContainer(new SlotLocked(playerInv, i, 8 + i * 18, 142));
			else addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 142));
		}

	}

	@Override
	public boolean canInteractWith(@Nonnull EntityPlayer player) {
		return player.getHeldItemMainhand() == baubleBoxInv.box
				|| player.getHeldItemOffhand() == baubleBoxInv.box;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void setAll(List<ItemStack> l) {
		super.setAll(l);
	}

	@Nonnull
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(slotIndex);

		if(slot != null && slot.getHasStack() && canInteractWith(player)) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			int boxStart = 4;
			int boxEnd = boxStart + 24;
			int invEnd = boxEnd + 36;
			
			if(slotIndex < boxEnd) {
				if(!mergeItemStack(itemstack1, boxEnd, invEnd, true))
					return ItemStack.EMPTY;
			} else {
				if(!itemstack1.isEmpty() && !mergeItemStack(itemstack1, boxStart, boxEnd, false))
					return ItemStack.EMPTY;
			}

			if(itemstack1.isEmpty())
				slot.putStack(ItemStack.EMPTY);
			else slot.onSlotChanged();

			if(itemstack1.getCount() == itemstack.getCount())
				return ItemStack.EMPTY;

			slot.onTake(player, itemstack1);
		}

		return itemstack;
	}
}
