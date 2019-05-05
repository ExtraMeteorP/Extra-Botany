package com.meteor.extrabotany.common.block;

import com.meteor.extrabotany.common.lib.LibBlocksName;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class BlockTrophy extends BlockMod{
	
	private static final AxisAlignedBB AABB = new AxisAlignedBB(0.3125, 0, 0.3125, 0.6875, 0.75, 0.6875);

	public BlockTrophy() {
		super(Material.ANVIL, LibBlocksName.TILE_TROPHY);
		setHardness(2.0F);
		setResistance(10.0F);
		setSoundType(SoundType.STONE);
	}
	
	
	@SideOnly(Side.CLIENT)
	@Nonnull
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	@Nonnull
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing side) {
		return side.getAxis() == EnumFacing.Axis.Y ? BlockFaceShape.CENTER_BIG : BlockFaceShape.MIDDLE_POLE_THICK;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state){
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state){
		return false;
	}
	
	@Nonnull
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return AABB;
	}

}
