package com.meteor.extrabotany.api.orb;

import com.meteor.extrabotany.api.item.INatureOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import vazkii.botania.api.BotaniaAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	
	public static Map<Integer, ItemStack> getOrbBaubles(EntityPlayer player) {
		if (player == null)
			return new HashMap<Integer, ItemStack>();

		IItemHandler baublesInv = BotaniaAPI.internalHandler.getBaublesInventoryWrapped(player);
		if (baublesInv == null)
			return new HashMap<Integer, ItemStack>();


		Map<Integer, ItemStack> toReturn = new HashMap<Integer, ItemStack>();
		int size = baublesInv.getSlots();

		for(int slot = 0; slot < size; slot++) {
			ItemStack stackInSlot = baublesInv.getStackInSlot(slot);

			if(!stackInSlot.isEmpty() && stackInSlot.getItem() instanceof INatureOrb) {
				toReturn.put(slot, stackInSlot);
			}
		}

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
				if(stack.getItem() instanceof INatureOrb && !((INatureOrb) stack.getItem()).canReceiveFrom(stack, stackInSlot))
					continue;
				
				if(orb.getXP(stackInSlot) < xpToGet)
					return 0;

				if(remove)
					orb.addXP(stackInSlot, -xpToGet);;

				return xpToGet;
			}
		
		Map<Integer, ItemStack> baubles = getOrbBaubles(player);
		for (Entry<Integer, ItemStack> entry : baubles.entrySet()) {
			ItemStack stackInSlot = entry.getValue();
			if(stackInSlot == stack)
				continue;
			INatureOrb manaItem = (INatureOrb) stackInSlot.getItem();
			if(manaItem.getXP(stackInSlot) > 0) {
				if(stack.getItem() instanceof INatureOrb && !((INatureOrb) stack.getItem()).canReceiveFrom(stack, stackInSlot))
					continue;

				int mana = Math.min(xpToGet, manaItem.getXP(stackInSlot));

				if(remove)
					manaItem.addXP(stackInSlot, -mana);

				BotaniaAPI.internalHandler.sendBaubleUpdatePacket(player, entry.getKey());

				return mana;
			}
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
				if(stack.getItem() instanceof INatureOrb && !((INatureOrb) stack.getItem()).canReceiveFrom(stack, stackInSlot))
					continue;
				
				if(orb.getXP(stackInSlot) < xpToGet)
					return false;

				if(remove)
					orb.addXP(stackInSlot, -xpToGet);;

				return true;
			}
		
		Map<Integer, ItemStack> baubles = getOrbBaubles(player);
		for (Entry<Integer, ItemStack> entry : baubles.entrySet()) {
			ItemStack stackInSlot = entry.getValue();
			if(stackInSlot == stack)
				continue;
			INatureOrb manaItemSlot = (INatureOrb) stackInSlot.getItem();
			if(manaItemSlot.getXP(stackInSlot) > xpToGet) {
				if(stack.getItem() instanceof INatureOrb && !((INatureOrb) stack.getItem()).canReceiveFrom(stack, stackInSlot))
					continue;

				if(remove)
					manaItemSlot.addXP(stackInSlot, -xpToGet);
				BotaniaAPI.internalHandler.sendBaubleUpdatePacket(player, entry.getKey());

				return true;
			}
		}

		return false;
	}
	
	public static int dispatchMana(ItemStack stack, EntityPlayer player, int xpToSend, boolean add) {
		if(stack.isEmpty())
			return 0;

		List<ItemStack> items = getOrbItems(player);
		for (ItemStack stackInSlot : items) {
			if(stackInSlot == stack)
				continue;
			INatureOrb manaItemSlot = (INatureOrb) stackInSlot.getItem();
			if(manaItemSlot.canReceiveFrom(stackInSlot, stack)) {
				if(stack.getItem() instanceof INatureOrb && !((INatureOrb) stack.getItem()).canExportTo(stack, stackInSlot))
					continue;

				int received;
				if(manaItemSlot.getXP(stackInSlot) + xpToSend <= manaItemSlot.getMaxXP(stackInSlot))
					received = xpToSend;
				else received = xpToSend - (manaItemSlot.getXP(stackInSlot) + xpToSend - manaItemSlot.getMaxXP(stackInSlot));


				if(add)
					manaItemSlot.addXP(stackInSlot, xpToSend);

				return received;
			}
		}

		Map<Integer, ItemStack> baubles = getOrbBaubles(player);
		for (Entry<Integer, ItemStack> entry : baubles.entrySet()) {
			ItemStack stackInSlot = entry.getValue();
			if(stackInSlot == stack)
				continue;
			INatureOrb manaItemSlot = (INatureOrb) stackInSlot.getItem();
			if(manaItemSlot.canReceiveFrom(stackInSlot, stack)) {
				if(stack.getItem() instanceof INatureOrb && !((INatureOrb) stack.getItem()).canExportTo(stack, stackInSlot))
					continue;

				int received;
				if(manaItemSlot.getXP(stackInSlot) + xpToSend <= manaItemSlot.getMaxXP(stackInSlot))
					received = xpToSend;
				else received = xpToSend - (manaItemSlot.getXP(stackInSlot) + xpToSend - manaItemSlot.getMaxXP(stackInSlot));


				if(add)
					manaItemSlot.addXP(stackInSlot, xpToSend);
				BotaniaAPI.internalHandler.sendBaubleUpdatePacket(player, entry.getKey());

				return received;
			}
		}

		return 0;
	}

	public static boolean dispatchManaExact(ItemStack stack, EntityPlayer player, int xpToSend, boolean add) {
		if(stack.isEmpty())
			return false;

		List<ItemStack> items = getOrbItems(player);
		for (ItemStack stackInSlot : items) {
			if(stackInSlot == stack)
				continue;
			INatureOrb manaItemSlot = (INatureOrb) stackInSlot.getItem();
			if(manaItemSlot.getXP(stackInSlot) + xpToSend <= manaItemSlot.getMaxXP(stackInSlot) && manaItemSlot.canReceiveFrom(stackInSlot, stack)) {
				if(stack.getItem() instanceof INatureOrb && !((INatureOrb) stack.getItem()).canExportTo(stack, stackInSlot))
					continue;

				if(add)
					manaItemSlot.addXP(stackInSlot, xpToSend);

				return true;
			}
		}

		Map<Integer, ItemStack> baubles = getOrbBaubles(player);
		for (Entry<Integer, ItemStack> entry : baubles.entrySet()) {
			ItemStack stackInSlot = entry.getValue();
			if(stackInSlot == stack)
				continue;
			INatureOrb manaItemSlot = (INatureOrb) stackInSlot.getItem();
			if(manaItemSlot.getXP(stackInSlot) + xpToSend <= manaItemSlot.getMaxXP(stackInSlot) && manaItemSlot.canReceiveFrom(stackInSlot, stack)) {
				if(stack.getItem() instanceof INatureOrb && !((INatureOrb) stack.getItem()).canExportTo(stack, stackInSlot))
					continue;

				if(add)
					manaItemSlot.addXP(stackInSlot, xpToSend);
				BotaniaAPI.internalHandler.sendBaubleUpdatePacket(player, entry.getKey());

				return true;
			}
		}

		return false;
	}

}
