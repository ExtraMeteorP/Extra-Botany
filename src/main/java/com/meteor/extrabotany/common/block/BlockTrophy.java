package com.meteor.extrabotany.common.block;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.common.lib.LibBlocksName;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTrophy extends BlockMod{
	
	public static final PropertyDirection FACING = BlockDirectional.FACING;
	private static final AxisAlignedBB AABB = new AxisAlignedBB(0.3125, 0, 0.3125, 0.6875, 0.75, 0.6875);

	public BlockTrophy() {
		super(Material.ANVIL, LibBlocksName.TILE_TROPHY);
		setHardness(2.0F);
		setResistance(10.0F);
		setSoundType(SoundType.STONE);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
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
	
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
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
	
	@Override
	public IBlockState getStateFromMeta(int meta){
	        return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7));
	}
	
	@Override
	public int getMetaFromState(IBlockState state){
		int i = 0;
		i = i | ((EnumFacing)state.getValue(FACING)).getIndex();
		return i;
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot){
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn){
		return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	protected BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] {FACING});
	}

}
