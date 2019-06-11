package com.meteor.extrabotany.common.item.lens;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import vazkii.botania.api.internal.IManaBurst;
import vazkii.botania.api.mana.IManaBlock;
import vazkii.botania.common.core.handler.ConfigHandler;
import vazkii.botania.common.item.lens.Lens;

public class LensSmelt extends Lens{
	
	public boolean collideBurst(IManaBurst burst, EntityThrowable entity, RayTraceResult pos, boolean isManaBlock, boolean dead, ItemStack stack) {
		World world = entity.world;
		BlockPos collidePos = pos.getBlockPos();
		if (world.isRemote || collidePos == null)
			return false;
		
		IBlockState state = world.getBlockState(collidePos);
		Block block = state.getBlock();
		
		int harvestLevel = ConfigHandler.harvestLevelBore;
		TileEntity tile = world.getTileEntity(collidePos);

		float hardness = state.getBlockHardness(world, collidePos);
		int neededHarvestLevel = block.getHarvestLevel(state);
		int mana = burst.getMana();
		
		BlockPos source = burst.getBurstSourceBlockPos();
		if(!source.equals(pos.getBlockPos()) && !(tile instanceof IManaBlock) && neededHarvestLevel <= harvestLevel && hardness != -1 && hardness < 50F && (burst.isFake() || mana >= 24)) {
			if(!burst.hasAlreadyCollidedAt(collidePos)) {
				if(!burst.isFake()) {
					ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(new ItemStack(block)).copy();
					if(!itemstack.isEmpty()){
						if(!entity.world.isRemote){
							float xp = FurnaceRecipes.instance().getSmeltingExperience(itemstack);
							if(xp >= 1F){
								entity.world.getBlockState(pos.getBlockPos()).getBlock().dropXpOnBlockBreak(entity.world, pos.getBlockPos(), (int) xp);
							}
							entity.world.destroyBlock(pos.getBlockPos(), false);
							entity.entityDropItem(itemstack, 0);
						}else{
							if(ConfigHandler.blockBreakParticles){
								world.playEvent(2001, collidePos, Block.getStateId(state));
								for(int i = 0; i < 3; i++)
									entity.world.spawnParticle(EnumParticleTypes.FLAME,
											pos.getBlockPos().getX() + Math.random() - 0.5F,
											pos.getBlockPos().getY() + Math.random() - 0.5F, pos.getBlockPos().getZ() + Math.random() - 0.5F,
				                            0.0D, 0.0D, 0.0D);
							}
						}
						burst.setMana(mana - 40);
					}
				}
			}
			dead = false;
		}
		return dead;
	}

}
