package com.meteor.extrabotany.common.block;

import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.meteor.extrabotany.common.lib.LibBlocksName;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLightSource extends BlockMod{

	public BlockLightSource() {
		super(Material.AIR, LibBlocksName.LIGHTSOURCE);
		setLightLevel(1F);
        setSoundType(SoundType.SLIME);
        disableStats();
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
		return NULL_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos){
	    return NULL_AABB.offset(pos);
	}

	@Override
	public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid){
	    return true;
	}

	@Override
	public boolean isCollidable(){
		return false;
	}

	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos){
		return null;
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos){
		return true;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state){
		return EnumBlockRenderType.INVISIBLE;
	}

	@Override
	public boolean causesSuffocation(IBlockState state){
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state){
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state){
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getAmbientOcclusionLightValue(IBlockState state){
		return 1.0F;
	}

	@Override
	public boolean isNormalCube(IBlockState state){
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos){
		return false;
	}

	@Override
	public boolean isBlockNormalCube(IBlockState state){
		return false;
	}

	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune){}

	@Override
	protected boolean canSilkHarvest(){
		return false;
	}

	@Override
	public boolean isReplaceableOreGen(IBlockState state, IBlockAccess world, BlockPos pos, Predicate<IBlockState> target){
		return false;
	}

	@Override
	public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos){
		return true;
	}

	@Override
	public EnumPushReaction getMobilityFlag(IBlockState state){
		return EnumPushReaction.DESTROY;
	}

	@Override
    @SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand){
		float x = pos.getX() + 0.5F;
		float y = pos.getY() + 0.3F;
		float z = pos.getZ() + 0.5F;
		worldIn.spawnParticle(EnumParticleTypes.FLAME, x, y, z, 0, 0, 0);
		super.randomDisplayTick(stateIn, worldIn, pos, rand);
	}
}
