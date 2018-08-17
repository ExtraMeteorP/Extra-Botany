package com.meteor.extrabotany.api.orb;

import java.util.ArrayList;
import java.util.List;

import com.meteor.extrabotany.api.item.INatureOrb;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class OrbItemHandler {
	
	public static List<ItemStack> getOrbItems(EntityPlayer player) {
		if (player == null)
			return new ArrayList<ItemStack>();

		IItemHandler mainInv = player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		List<ItemStack> toReturn = new ArrayList<ItemStack>();
		int size = mainInv.getSlots();

		for(int slot = 0; slot < size; slot++) {
			ItemStack stackInSlot = mainInv.getStackInSlot(slot);

			if(!stackInSlot.isEmpty() && stackInSlot.getItem() instanceof INatureOrb) {
				toReturn.add(stackInSlot);
			}
		}

		OrbItemsEvent event = new OrbItemsEvent(player, toReturn);
		MinecraftForge.EVENT_BUS.post(event);
		toReturn = event.getItems();
		return toReturn;
	}
	
	public static int requestNature(ItemStack stack, EntityPlayer player, int xpToGet, boolean remove) {
		if(stack.isEmpty())
			return 0;

		List<ItemStack> items = getOrbItems(player);
		for (ItemStack stackInSlot : items) {
			if(stackInSlot == stack)
				continue;
				INatureOrb orb = (INatureOrb) stackInSlot.getItem();
				if(stack.getItem() instanceof INatureOrb)
					continue;
				
				if(orb.getXP(stackInSlot) < xpToGet)
					return 0;

				if(remove)
					orb.addXP(stackInSlot, -xpToGet);;

				return xpToGet;
			}

		return 0;
	}
	
	public static boolean requestNatureExact(ItemStack stack, EntityPlayer player, int xpToGet, boolean remove) {
		if(stack.isEmpty())
			return false;

		List<ItemStack> items = getOrbItems(player);
		for (ItemStack stackInSlot : items) {
			if(stackInSlot == stack)
				continue;
				INatureOrb orb = (INatureOrb) stackInSlot.getItem();
				if(stack.getItem() instanceof INatureOrb)
					continue;
				
				if(orb.getXP(stackInSlot) < xpToGet)
					return false;

				if(remove)
					orb.addXP(stackInSlot, -xpToGet);;

				return true;
			}

		return false;
	}

}
