package com.meteor.extrabotany.common.block;

import com.meteor.extrabotany.common.block.tile.TileGildedTinyPotato;
import com.meteor.extrabotany.common.lib.LibBlocksName;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.state.BotaniaStateProps;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import javax.annotation.Nonnull;

public class BlockGildedTinyPotato extends BlockMod implements ILexiconable{
	
	private static final AxisAlignedBB AABB = new AxisAlignedBB(0.375, 0, 0.375, 0.625, 0.375, 0.625);
	
	private static final String TAG_HUNGER = "hunger";
	private static final String TAG_LOVE = "love";
	private static final String TAG_ISTOUCHED = "istouched";

	public BlockGildedTinyPotato() {
		super(Material.TNT, LibBlocksName.TILE_GILDEDTINYPOTATO);
		setHardness(0.45F);
		setDefaultState(blockState.getBaseState()
				.withProperty(BotaniaStateProps.CARDINALS, EnumFacing.SOUTH));
	}
	
	@Nonnull
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BotaniaStateProps.CARDINALS);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		switch (state.getValue(BotaniaStateProps.CARDINALS)) {
		case NORTH: return 0;
		case WEST: return 3;
		case EAST: return 1;
		case SOUTH:
		default:
			return 2; // 
		}
	}

	@Nonnull
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing side;
		switch (meta) {
		case 3: side = EnumFacing.WEST; break;
		case 0: side = EnumFacing.NORTH; break;
		case 1: side = EnumFacing.EAST; break;
		case 2:
		default:
			side = EnumFacing.SOUTH; break;
		}
		return getDefaultState().withProperty(BotaniaStateProps.CARDINALS, side);
	}

	@Override
	public LexiconEntry getEntry(World arg0, BlockPos arg1, EntityPlayer arg2, ItemStack arg3) {
		return null;
	}
	
	@Nonnull
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return AABB;
	}
	
	@Override
	public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
		super.breakBlock(world, pos, state);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing par6, float par7, float par8, float par9) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TileGildedTinyPotato) {
			((TileGildedTinyPotato) tile).touch();
			((TileGildedTinyPotato) tile).eat(player.getHeldItem(hand));
			world.spawnParticle(EnumParticleTypes.HEART, pos.getX() + AABB.minX + Math.random() * (AABB.maxX - AABB.minX), pos.getY() + AABB.maxY, pos.getZ() + AABB.minZ + Math.random() * (AABB.maxZ - AABB.minZ), 0, 0 ,0);
		}
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entityLiving, ItemStack stack) {
		world.setBlockState(pos, state.withProperty(BotaniaStateProps.CARDINALS, entityLiving.getHorizontalFacing().getOpposite()));
		if (stack.hasDisplayName())
			((TileGildedTinyPotato) world.getTileEntity(pos)).name = stack.getDisplayName();
		((TileGildedTinyPotato) world.getTileEntity(pos)).love = ItemNBTHelper.getInt(stack, TAG_LOVE, 0);
		((TileGildedTinyPotato) world.getTileEntity(pos)).hunger = ItemNBTHelper.getInt(stack, TAG_HUNGER, 0);
		((TileGildedTinyPotato) world.getTileEntity(pos)).touchTicks = ItemNBTHelper.getInt(stack, TAG_ISTOUCHED, 0);
	}

	@Override
	public boolean removedByPlayer(@Nonnull IBlockState state, World world, @Nonnull BlockPos pos, @Nonnull EntityPlayer player, boolean willHarvest) {
		if (willHarvest) {
			// Copy of super.removedByPlayer but don't set to air yet
			// This is so getDrops below will have a TE to work with
			onBlockHarvested(world, pos, state, player);
			return true;
		} else {
			return super.removedByPlayer(state, world, pos, player, willHarvest);
		}
	}

	@Override
	public void harvestBlock(@Nonnull World world, EntityPlayer player, @Nonnull BlockPos pos, @Nonnull IBlockState state, TileEntity te, ItemStack stack) {
		super.harvestBlock(world, player, pos, state, te, stack);
		// Now delete the block and TE
		world.setBlockToAir(pos);
	}
	
	@Override
	public void getDrops(NonNullList<ItemStack> list, IBlockAccess world, BlockPos pos, @Nonnull IBlockState state, int fortune) {
		TileEntity tile = world.getTileEntity(pos);

		if(tile != null) {
			ItemStack stack = new ItemStack(this);
			String name = ((TileGildedTinyPotato) tile).name;
			if(!name.isEmpty())
				stack.setStackDisplayName(name);
			ItemNBTHelper.setInt(stack, TAG_LOVE, ((TileGildedTinyPotato) tile).love);
			ItemNBTHelper.setInt(stack, TAG_HUNGER, ((TileGildedTinyPotato) tile).hunger);
			ItemNBTHelper.setInt(stack, TAG_ISTOUCHED, ((TileGildedTinyPotato) tile).touchTicks);
			list.add(stack);
		}
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
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
		return new TileGildedTinyPotato();
	}
	

	@Nonnull
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing side) {
		return BlockFaceShape.UNDEFINED;
	}


}
