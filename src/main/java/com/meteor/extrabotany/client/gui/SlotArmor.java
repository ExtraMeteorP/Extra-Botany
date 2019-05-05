package com.meteor.extrabotany.client.gui;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class SlotArmor extends Slot {

    EntityEquipmentSlot type;

    public SlotArmor(IInventory inv, int index, int xPos, int yPos, EntityEquipmentSlot type) {
        super(inv, index, xPos, yPos);
        this.type = type;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if (stack.getItem() instanceof ItemArmor) {
            ItemArmor armor = (ItemArmor) stack.getItem();
            if (armor.armorType == type)
                return true;
        }

        return false;
    }
}
