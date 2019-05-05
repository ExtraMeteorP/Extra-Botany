package com.meteor.extrabotany.common.block;

import com.meteor.extrabotany.common.block.tile.TileCocoonDesire;
import com.meteor.extrabotany.common.lexicon.LexiconData;
import com.meteor.extrabotany.common.lib.LibBlocksName;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockCocoonDesire extends BlockMod implements ILexiconable{

	private static final AxisAlignedBB AABB = new AxisAlignedBB(3.0/16, 0, 3.0/16, 13.0/16, 0.875, 13.0/16);;

	public BlockCocoonDesire() {
		super(Material.CLOTH, LibBlocksName.TILE_COCOON);
		setHardness(3.0F);
		setResistance(50.0F);
		setSoundType(SoundType.CLOTH);
	}

	@Nonnull
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return AABB;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Nonnull
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Nonnull
	@Override
	public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
		return new TileCocoonDesire();
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state){
		Random rand = new Random();
		TileCocoonDesire pedestal = (TileCocoonDesire) world.getTileEntity(pos);

		if(pedestal != null)
			if(!pedestal.getItem().isEmpty()){
				float f = rand.nextFloat() * 0.8F + 0.1F;
				float f1 = rand.nextFloat() * 0.8F + 0.1F;
				float f2 = rand.nextFloat() * 0.8F + 0.1F;

				EntityItem item = new EntityItem(world, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2, pedestal.getItem());
				float f3 = 0.05F;
				item.motionX = (float)rand.nextGaussian() * f3;
				item.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
				item.motionZ = (float)rand.nextGaussian() * f3;
				world.spawnEntity(item);
			}

		super.breakBlock(world, pos, state);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		return handleBlockActivation(world, pos, player, player.getHeldItem(hand));
	}
	
	public static boolean handleBlockActivation(World world, BlockPos pos, EntityPlayer player, ItemStack heldItem){
		TileEntity tile = world.getTileEntity(pos);
		if(tile != null && tile instanceof TileCocoonDesire){
			TileCocoonDesire te = (TileCocoonDesire) tile;
			if(!heldItem.isEmpty() && te.getItem().isEmpty()){
				ItemStack newItem = heldItem.copy();
				newItem.setCount(1);
				te.setItem(newItem);
				heldItem.shrink(1);
				world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() - world.rand.nextFloat() * 0.2F + 1, false);
				return true;
			}
			if(!te.getItem().isEmpty()){
				if(player.inventory.addItemStackToInventory(te.getItem())){
					te.setItem(ItemStack.EMPTY);
					world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() - world.rand.nextFloat() * 0.2F + 1, false);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public LexiconEntry getEntry(World world, BlockPos pos, EntityPlayer player, ItemStack lexicon) {
		return LexiconData.cocoondesire;
	}

	@Nonnull
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing side) {
		return side.getAxis() == EnumFacing.Axis.Y ? BlockFaceShape.CENTER_BIG : BlockFaceShape.MIDDLE_POLE_THICK;
	}

}
