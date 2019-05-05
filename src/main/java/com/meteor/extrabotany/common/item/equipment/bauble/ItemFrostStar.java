package com.meteor.extrabotany.common.item.equipment.bauble;

import baubles.api.BaubleType;
import com.meteor.extrabotany.common.lib.LibItemsName;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentFrostWalker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemFrostStar extends ItemBauble{

	public ItemFrostStar() {
		super(LibItemsName.BAUBLE_FROSTSTAR);
	}
	
	private static final int RANGE = 6;
	
	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase entity) {
		super.onWornTick(stack, entity);
		if(!entity.world.isRemote) {
			boolean lastOnGround = entity.onGround;
			entity.onGround = true;
			EnchantmentFrostWalker.freezeNearby(entity, entity.world, new BlockPos(entity), 4);
			freezeLava(entity, entity.world, new BlockPos(entity), 4);
			entity.onGround = lastOnGround;
			
			for(EntityLivingBase living : entity.getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(entity.getPosition().add(-RANGE, -RANGE, -RANGE), entity.getPosition().add(RANGE + 1, RANGE + 1, RANGE + 1)))){
				if(living.isSpectatedByPlayer((EntityPlayerMP) entity) && living != entity){
					living.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 60, 4));
				}
			}
		}
	}
	
	public static void freezeLava(EntityLivingBase living, World worldIn, BlockPos pos, int level){
        if (living.onGround){
            float f = (float)Math.min(16, 2 + level);
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(0, 0, 0);
            for (BlockPos.MutableBlockPos blockpos$mutableblockpos1 : BlockPos.getAllInBoxMutable(pos.add((double)(-f), -1.0D, (double)(-f)), pos.add((double)f, -1.0D, (double)f))){
                if (blockpos$mutableblockpos1.distanceSqToCenter(living.posX, living.posY, living.posZ) <= (double)(f * f)){
                    blockpos$mutableblockpos.setPos(blockpos$mutableblockpos1.getX(), blockpos$mutableblockpos1.getY() + 1, blockpos$mutableblockpos1.getZ());
                    IBlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos);

                    if (iblockstate.getMaterial() == Material.AIR){
                        IBlockState iblockstate1 = worldIn.getBlockState(blockpos$mutableblockpos1);

                        if (iblockstate1.getMaterial() == Material.LAVA && (iblockstate1.getBlock() == net.minecraft.init.Blocks.LAVA || iblockstate1.getBlock() == net.minecraft.init.Blocks.FLOWING_LAVA) && ((Integer)iblockstate1.getValue(BlockLiquid.LEVEL)).intValue() == 0 && worldIn.mayPlace(Blocks.OBSIDIAN, blockpos$mutableblockpos1, false, EnumFacing.DOWN, (Entity)null)){
                            worldIn.setBlockState(blockpos$mutableblockpos1, Blocks.OBSIDIAN.getDefaultState());
                            worldIn.scheduleUpdate(blockpos$mutableblockpos1.toImmutable(), Blocks.OBSIDIAN, MathHelper.getInt(living.getRNG(), 60, 120));
                        }
                    }
                }
            }
        }
    }

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.RING;
	}

}
