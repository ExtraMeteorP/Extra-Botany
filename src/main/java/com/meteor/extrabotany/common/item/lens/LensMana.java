package com.meteor.extrabotany.common.item.lens;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import vazkii.botania.api.internal.IManaBurst;
import vazkii.botania.api.mana.BurstProperties;
import vazkii.botania.api.recipe.RecipeManaInfusion;
import vazkii.botania.common.block.tile.mana.TilePool;
import vazkii.botania.common.item.lens.Lens;

import java.util.List;

public class LensMana extends Lens{
	
	@Override
	public void apply(ItemStack stack, BurstProperties props) {
		props.maxMana = 1000;
		props.motionModifier *= 0.5F;
		props.manaLossPerTick *= 2F;
	}
	
	@Override
	public void updateBurst(IManaBurst burst, EntityThrowable entity, ItemStack stack) {
		if (entity.world.isRemote)
			return;
		
		World world = entity.world;
		int mana = burst.getMana();
		IBlockState state = world.getBlockState(burst.getBurstSourceBlockPos().add(0, -1, 0));
		AxisAlignedBB axis = new AxisAlignedBB(entity.posX, entity.posY, entity.posZ, entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).grow(1);
		List<EntityItem> entities = entity.world.getEntitiesWithinAABB(EntityItem.class, axis);
		for(EntityItem items : entities) {
			if(items.cannotPickup() || burst.isFake())
				continue;
			ItemStack itemstack = items.getItem();
			RecipeManaInfusion recipe = TilePool.getMatchingRecipe(itemstack, state);
			if(recipe != null) {
				int manaToConsume = recipe.getManaToConsume();
				if(mana >= manaToConsume) {
					burst.setMana((int) (mana - manaToConsume));
					itemstack.shrink(1);

					ItemStack output = recipe.getOutput().copy();
					EntityItem outputItem = new EntityItem(world, items.posX + 0.5, items.posY + 0.5, items.posZ + 0.5, output);
					outputItem.setPickupDelay(30);
					world.spawnEntity(outputItem);
				}
			}
		}
	}

}
