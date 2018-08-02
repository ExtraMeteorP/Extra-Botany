package com.meteor.extrabotany.common.block;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.api.BatteryBoxVariant;
import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.client.core.handler.ModelHandler;
import com.meteor.extrabotany.common.block.tile.TileManaBarrel;
import com.meteor.extrabotany.common.lib.LibBlocksName;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.wand.IWandHUD;
import vazkii.botania.api.wand.IWandable;

public class BlockManaBarrel extends BlockMod implements ILexiconable{
	
	public BlockManaBarrel() {
		super(Material.ROCK, LibBlocksName.TILE_BATTERYBOX);
		setHardness(2.0F);
		setResistance(10.0F);
		setSoundType(SoundType.STONE);
		setDefaultState(blockState.getBaseState()
				.withProperty(ExtraBotanyAPI.BATTERYBOX_VARIANT, BatteryBoxVariant.DEFAULT));
	}
	
	@Nonnull
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, ExtraBotanyAPI.BATTERYBOX_VARIANT);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(ExtraBotanyAPI.BATTERYBOX_VARIANT).ordinal();
	}

	@Nonnull
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(ExtraBotanyAPI.BATTERYBOX_VARIANT, BatteryBoxVariant.values()[meta]);
	}
	
	@Nonnull
	@Override
	public IBlockState getActualState(@Nonnull IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity te = world instanceof ChunkCache ? ((ChunkCache)world).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : world.getTileEntity(pos);
		if (te instanceof TileManaBarrel) {
			return state.withProperty(ExtraBotanyAPI.BATTERYBOX_VARIANT, BatteryBoxVariant.values()[te.getBlockMetadata()]);
		} else {
			return state.withProperty(ExtraBotanyAPI.BATTERYBOX_VARIANT, BatteryBoxVariant.values()[te.getBlockMetadata()]);
		}
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getBlock().getMetaFromState(state);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return true;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) {
		TileManaBarrel pool = (TileManaBarrel) world.getTileEntity(pos);
		return TileManaBarrel.calculateComparatorLevel(pool.getCurrentMana(), pool.manaCap);
	}

	@Nonnull
	@Override
	public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
		return new TileManaBarrel();
	}

	@Override
	public LexiconEntry getEntry(World arg0, BlockPos arg1, EntityPlayer arg2, ItemStack arg3) {
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		ModelHandler.registerBlockToState(this, BatteryBoxVariant.values().length);
	}

}
