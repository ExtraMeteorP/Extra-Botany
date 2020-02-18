package com.meteor.extrabotany.common.item.lens;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import vazkii.botania.api.internal.IManaBurst;
import vazkii.botania.api.mana.BurstProperties;
import vazkii.botania.common.item.lens.Lens;

public class LensSuperconductor extends Lens{
	
	@Override
	public void apply(ItemStack stack, BurstProperties props) {
		props.maxMana *=8F;
		props.motionModifier *= 1.5F;
		props.manaLossPerTick *= 16F;
		props.ticksBeforeManaLoss *= 0.8F;
	}
	
	@Override
	public void updateBurst(IManaBurst burst, EntityThrowable entity, ItemStack stack) {
		if (entity.world.isRemote)
			return;

		World world = entity.world;
		int mana = burst.getMana();
		IBlockState state = world.getBlockState(burst.getBurstSourceBlockPos().add(0, -1, 0));
		AxisAlignedBB axis = new AxisAlignedBB(entity.posX-0.5F, entity.posY-0.5F, entity.posZ-0.5F, entity.lastTickPosX+0.5F,
				entity.lastTickPosY+0.5F, entity.lastTickPosZ+0.5F).grow(1);
		List<EntityLivingBase> entities = entity.world.getEntitiesWithinAABB(EntityLivingBase.class, axis);
		for (EntityLivingBase items : entities) {
			if(!burst.isFake()) {
				items.attackEntityFrom(DamageSource.MAGIC.setDamageBypassesArmor().setDamageIsAbsolute().setDifficultyScaled(), items instanceof EntityPlayer ? 25F : 8F);
			}
		}
	}

}
