package com.meteor.extrabotany.common.entity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityItemUnbreakable extends EntityItem {

	public EntityItemUnbreakable(World worldIn) {
		super(worldIn);
	}

	public EntityItemUnbreakable(World worldIn, double x, double y, double z, ItemStack stack) {
		super(worldIn, x, y, z, stack);
		this.isImmuneToFire = true;
		this.setNoDespawn();
	}

	@Override
	public boolean isImmuneToExplosions() {
		return true;
	}

}
