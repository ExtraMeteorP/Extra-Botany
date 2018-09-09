package com.meteor.extrabotany.common.block;

import java.util.Random;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.common.block.tile.TileElfJar;
import com.meteor.extrabotany.common.lib.LibBlocksName;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockElfJar extends BlockMod{
	
	private static final AxisAlignedBB AABB = new AxisAlignedBB(0, 0, 0, 1, 0.875, 1);

	public BlockElfJar() {
		super(Material.GLASS, LibBlocksName.TILE_ELFJAR);
		setHardness(1.5F);
		setResistance(10.0F);
		setSoundType(SoundType.GLASS);
	}
	
	@SideOnly(Side.CLIENT)
	@Nonnull
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
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
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Nonnull
	@Override
	public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
		return new TileElfJar();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){
		TileEntity tile = world.getTileEntity(pos);
		return tile instanceof TileElfJar && manageInventory(player, (TileElfJar) tile, hand, player.getHeldItem(hand));
	}
	
	public static boolean manageInventory(EntityPlayer player, TileElfJar tileEntity, EnumHand hand, ItemStack itemStack)
	{
		if(tileEntity.fluidTank == null)
		{
			return false;
		}
		
		ItemStack copyStack = size(itemStack, 1);
		
		if(isFluidContainer(itemStack))
		{
			IFluidHandlerItem handler = FluidUtil.getFluidHandler(copyStack);
			
			if(FluidUtil.getFluidContained(copyStack) == null)
			{
				if(tileEntity.fluidTank.getFluid() != null)
				{
					int filled = handler.fill(tileEntity.fluidTank.getFluid(), !player.capabilities.isCreativeMode);
					copyStack = handler.getContainer();
					
					if(filled > 0)
					{
						if(player.capabilities.isCreativeMode)
						{
							tileEntity.fluidTank.getFluid().amount -= filled;
						}
						else if(itemStack.getCount() == 1)
						{
							tileEntity.fluidTank.getFluid().amount -= filled;
							player.setHeldItem(hand, copyStack);
						}
						else if(itemStack.getCount() > 1 && player.inventory.addItemStackToInventory(copyStack))
						{
							tileEntity.fluidTank.getFluid().amount -= filled;
							itemStack.shrink(1);
						}
						
						if(tileEntity.fluidTank.getFluid().amount == 0)
						{
							tileEntity.fluidTank.setFluid(null);;
						}
						
						return true;
					}
				}
			}
			else {
				FluidStack itemFluid = FluidUtil.getFluidContained(copyStack);
				int stored = tileEntity.fluidTank.getFluid() != null ? tileEntity.fluidTank.getFluid().amount : 0;
				int needed = (16000)-stored;
				
				if(tileEntity.fluidTank.getFluid() != null && !tileEntity.fluidTank.getFluid().isFluidEqual(itemFluid))
				{
					return false;
				}
				
				boolean filled = false;
				FluidStack drained = handler.drain(needed, !player.capabilities.isCreativeMode);
				copyStack = handler.getContainer();
				
				if(copyStack.getCount() == 0)
				{
					copyStack = ItemStack.EMPTY;
				}
				
				if(drained != null)
				{
					if(player.capabilities.isCreativeMode)
					{
						filled = true;
					}
					else {
						if(!copyStack.isEmpty())
						{
							if(itemStack.getCount() == 1)
							{
								player.setHeldItem(hand, copyStack);
								filled = true;
							}
							else {
								if(player.inventory.addItemStackToInventory(copyStack))
								{
									itemStack.shrink(1);
	
									filled = true;
								}
							}
						}
						else {
							itemStack.shrink(1);
	
							if(itemStack.getCount() == 0)
							{
								player.setHeldItem(hand, ItemStack.EMPTY);
							}
	
							filled = true;
						}
					}
	
					if(filled)
					{
						if(tileEntity.fluidTank.getFluid() == null)
						{
							tileEntity.fluidTank.setFluid(drained);
						}
						else {
							tileEntity.fluidTank.getFluid().amount += drained.amount;
						}
						
						return true;
					}
				}
			}
		}

		return false;
	}
	
	public static boolean isFluidContainer(ItemStack stack)
	{
		return !stack.isEmpty() && stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
	}
	
	public static ItemStack size(ItemStack stack, int size)
	{
		if(size <= 0 || stack.isEmpty())
		{
			return ItemStack.EMPTY;
		}

		ItemStack ret = stack.copy();
		ret.setCount(size);
		
		return ret;
	}


}
