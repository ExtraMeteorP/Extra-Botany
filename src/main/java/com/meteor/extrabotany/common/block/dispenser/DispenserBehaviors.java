package com.meteor.extrabotany.common.block.dispenser;

import com.meteor.extrabotany.api.item.IHammer;
import com.meteor.extrabotany.common.item.ModItems;
import com.sun.jna.platform.win32.WinUser.INPUT;

import net.minecraft.block.BlockDispenser;
import net.minecraft.item.Item;

public class DispenserBehaviors {
	
	public static void init() {
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.hammerelementium, new BehaviourHammer());
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.hammermanasteel, new BehaviourHammer());
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.hammerterrasteel, new BehaviourHammer());
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.hammerultimate, new BehaviourHammer());
	}

}
