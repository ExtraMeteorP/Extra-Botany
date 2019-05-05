package com.meteor.extrabotany.api.orb;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.List;

public class OrbItemsEvent extends Event {

	private final EntityPlayer entityPlayer;
	private List<ItemStack> items;

	public OrbItemsEvent(EntityPlayer entityPlayer, List<ItemStack> items) {
		this.entityPlayer = entityPlayer;
		this.items = items;
	}

	public EntityPlayer getEntityPlayer() {
		return entityPlayer;
	}

	public List<ItemStack> getItems() {
		return items;
	}

	public void add(ItemStack item) {
		items.add(item);
	}
}
