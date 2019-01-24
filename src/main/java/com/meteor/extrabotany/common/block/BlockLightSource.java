package com.meteor.extrabotany.common.block;

import java.util.Random;

import javax.annotation.Nullable;

import com.meteor.extrabotany.common.lib.LibBlocksName;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLightSource extends BlockMod{

	public BlockLightSource() {
		super(Material.AIR, LibBlocksName.LIGHTSOURCE);
		this.setLightLevel(1F);
		this.setHardness(0.1F);
		this.setResistance(0.1F);
	}
	
	@Override
	public int quantityDropped(Random random){
        return 0;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer(){
        return BlockRenderLayer.CUTOUT;
    }
	
	@Override
    public EnumBlockRenderType getRenderType(IBlockState state){
        return EnumBlockRenderType.INVISIBLE;
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state){
        return false;
    }
    
    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos){
        return NULL_AABB;
    }
    
    @Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos){
        return true;
    }

    @Override
    public boolean isFullCube(IBlockState state){
        return false;
    }
    
    @Override
    public boolean canSilkHarvest(){
        return false;
    }

}
