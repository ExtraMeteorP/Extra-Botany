package com.meteor.extrabotany.common.item.equipment.bauble;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public abstract class WallJumpingShim extends ItemBauble {

	public WallJumpingShim(String name) {
		super(name);
	}

	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		super.onWornTick(stack, player);

		clientWornTick(stack, player);
	}

	public void clientWornTick(ItemStack stack, EntityLivingBase player) {
		// NO-OP
	}

	public int getMaxAllowedJumps() {
		return 5;
	}
}
