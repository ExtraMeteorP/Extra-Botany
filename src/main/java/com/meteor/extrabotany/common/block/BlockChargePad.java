package com.meteor.extrabotany.common.block;

import java.util.Random;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.common.block.tile.TileChargePad;
import com.meteor.extrabotany.common.lexicon.LexiconData;
import com.meteor.extrabotany.common.lib.LibBlocksName;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.mana.IManaItem;

public class BlockChargePad extends BlockMod implements ILexiconable{

	public BlockChargePad() {
		super(Material.IRON, LibBlocksName.TILE_CHARGEPAD);
		setHardness(3.0F);
		setResistance(15.0F);
		setSoundType(SoundType.STONE);
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state){
		Random rand = new Random();
		TileChargePad pad = (TileChargePad) world.getTileEntity(pos);

		if(pad != null)
			if(!pad.getItem().isEmpty()){
				float f = rand.nextFloat() * 0.8F + 0.1F;
				float f1 = rand.nextFloat() * 0.8F + 0.1F;
				float f2 = rand.nextFloat() * 0.8F + 0.1F;

				EntityItem item = new EntityItem(world, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2, pad.getItem());
				float f3 = 0.05F;
				item.motionX = (float)rand.nextGaussian() * f3;
				item.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
				item.motionZ = (float)rand.nextGaussian() * f3;
				world.spawnEntity(item);
			}

		super.breakBlock(world, pos, state);
	}
	
	public static boolean handleBlockActivation(World world, BlockPos pos, EntityPlayer player, ItemStack heldItem){
		TileEntity tile = world.getTileEntity(pos);
		if(tile != null && tile instanceof TileChargePad){
			TileChargePad te = (TileChargePad) tile;
			if(!heldItem.isEmpty() && te.getItem().isEmpty()){
				if(!(heldItem.getItem() instanceof IManaItem))
					return false;
				te.markForUpdate();
				ItemStack newItem = heldItem.copy();
				newItem.setCount(1);
				te.setItem(newItem);
				heldItem.shrink(1);
				world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() - world.rand.nextFloat() * 0.2F + 1, false);
				return true;
			}else if(player.inventory.addItemStackToInventory(((TileChargePad)tile).getItem())){
				te.markForUpdate();
				te.setItem(ItemStack.EMPTY);
				world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() - world.rand.nextFloat() * 0.2F + 1, false);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		return handleBlockActivation(world, pos, player, player.getHeldItem(hand));
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player){
		return new ItemStack(Item.getItemFromBlock(this));
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Nonnull
	@Override
	public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
		return new TileChargePad();
	}
	
	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) {
		TileChargePad pad = (TileChargePad) world.getTileEntity(pos);
		if(pad.getItem().getItem() instanceof IManaItem) {
			IManaItem item = (IManaItem) pad.getItem().getItem();
			return (int)(15 * item.getMana(pad.getItem()) / item.getMaxMana(pad.getItem()));
		}
		return 0;
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

	@Override
	public LexiconEntry getEntry(World arg0, BlockPos arg1, EntityPlayer arg2, ItemStack arg3) {
		return LexiconData.chargepad;
	}


}
