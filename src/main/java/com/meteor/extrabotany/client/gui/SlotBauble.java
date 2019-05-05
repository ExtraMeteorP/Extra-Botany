package com.meteor.extrabotany.client.gui;

import baubles.api.IBauble;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBauble extends Slot {

    public SlotBauble(IInventory inv, int index, int xPos, int yPos) {
        super(inv, index, xPos, yPos);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if (stack.getItem() instanceof IBauble) {
            return true;
        }
        return false;
    }
}
