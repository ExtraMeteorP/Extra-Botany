package com.meteor.extrabotany.common.block.dispenser;

import com.meteor.extrabotany.common.item.ModItems;
import net.minecraft.block.BlockDispenser;

public class DispenserBehaviors {
	
	public static void init() {
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.hammerelementium, new BehaviourHammer());
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.hammermanasteel, new BehaviourHammer());
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.hammerterrasteel, new BehaviourHammer());
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.hammerultimate, new BehaviourHammer());
	}

}
