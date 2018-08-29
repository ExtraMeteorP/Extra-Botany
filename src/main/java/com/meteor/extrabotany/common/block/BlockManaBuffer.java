package com.meteor.extrabotany.common.block;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.common.block.tile.TileManaBuffer;
import com.meteor.extrabotany.common.lexicon.LexiconData;
import com.meteor.extrabotany.common.lib.LibBlocksName;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;

public class BlockManaBuffer extends BlockMod implements ILexiconable{
	
	public BlockManaBuffer() {
		super(Material.ROCK, LibBlocksName.TILE_BATTERYBOX);
		setHardness(2.0F);
		setResistance(10.0F);
		setSoundType(SoundType.STONE);
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
		TileManaBuffer pool = (TileManaBuffer) world.getTileEntity(pos);
		return TileManaBuffer.calculateComparatorLevel(pool.getCurrentMana(), pool.manaCap);
	}

	@Nonnull
	@Override
	public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
		return new TileManaBuffer();
	}

	@Override
	public LexiconEntry getEntry(World arg0, BlockPos arg1, EntityPlayer arg2, ItemStack arg3) {
		return LexiconData.manabarrel;
	}

}
