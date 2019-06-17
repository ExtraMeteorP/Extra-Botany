package com.meteor.extrabotany.common.item.equipment.armor.combatmaid;

import com.meteor.extrabotany.common.lib.LibItemsName;

import net.minecraft.inventory.EntityEquipmentSlot;

public class ItemCombatMaidChest extends ItemCombatMaidArmor {

	public ItemCombatMaidChest() {
		this(LibItemsName.CMCHEST);
	}

	public ItemCombatMaidChest(String name) {
		super(EntityEquipmentSlot.CHEST, name);
	}
}
