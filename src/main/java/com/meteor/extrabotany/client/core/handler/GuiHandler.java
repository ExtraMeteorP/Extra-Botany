package com.meteor.extrabotany.client.core.handler;

import com.meteor.extrabotany.client.gui.handbag.ContainerHandbag;
import com.meteor.extrabotany.client.gui.handbag.GuiHandbag;
import com.meteor.extrabotany.client.gui.handbag.InventoryHandbag;
import com.meteor.extrabotany.client.lib.LibGui;
import com.meteor.extrabotany.common.item.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int handId, int unused1, int unused2) {
		EnumHand hand = handId == 1 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
		ItemStack stack = player.getHeldItem(hand);

		switch(ID) {
		case LibGui.MASTERHANDBAG :
			if(stack.getItem() == ModItems.masterhandbag)
				return new ContainerHandbag(player, new InventoryHandbag(stack));
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int handId, int unused1, int unused2) {
		EnumHand hand = handId == 1 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
		ItemStack stack = player.getHeldItem(hand);

		switch(ID) {
		case LibGui.MASTERHANDBAG :
			if(stack.getItem() == ModItems.masterhandbag)
				return new GuiHandbag(player, new InventoryHandbag(stack));
		}

		return null;
	}
}
