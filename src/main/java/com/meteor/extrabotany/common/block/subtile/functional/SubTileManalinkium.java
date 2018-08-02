package com.meteor.extrabotany.common.block.subtile.functional;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.core.handler.ConfigHandler;
import com.meteor.extrabotany.common.item.ItemBinder;
import com.meteor.extrabotany.common.lexicon.LexiconData;
import com.meteor.extrabotany.common.lib.LibAdvancements;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.lexicon.multiblock.Multiblock;
import vazkii.botania.api.lexicon.multiblock.MultiblockSet;
import vazkii.botania.api.subtile.SubTileFunctional;
import vazkii.botania.common.block.tile.mana.TilePool;

public class SubTileManalinkium extends SubTileFunctional{
	
	private static final int COST = 2000;
	private static final int RANGE = 1;
	

	private static final String TAG_X = "Posx";
	private static final String TAG_Y = "Posy";
	private static final String TAG_Z = "Posz";
	int x,y,z = 0;
	
	@Override
	public void writeToPacketNBT(NBTTagCompound cmp) {
		super.writeToPacketNBT(cmp);
		cmp.setInteger(TAG_X, x);
		cmp.setInteger(TAG_Y, y);
		cmp.setInteger(TAG_Z, z);
	}

	@Override
	public void readFromPacketNBT(NBTTagCompound cmp) {
		super.readFromPacketNBT(cmp);
		x = cmp.getInteger(TAG_X);
		y = cmp.getInteger(TAG_Y);
		z = cmp.getInteger(TAG_Z);
	}
	
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) { 
		if(player.getHeldItem(hand).getItem() instanceof ItemBinder){
			ItemBinder bind = (ItemBinder) player.getHeldItem(hand).getItem();
			x = bind.getPosX(player.getHeldItem(hand));
			y = bind.getPosY(player.getHeldItem(hand));
			z = bind.getPosZ(player.getHeldItem(hand));
			ExtraBotanyAPI.unlockAdvancement(player, LibAdvancements.MANALINKIUM_USE);
		}
		return true; 
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		int posx = this.supertile.getPos().getX();
		int posy = this.supertile.getPos().getY();
		int posz = this.supertile.getPos().getZ();
		
		if(!canTPExist(this.getWorld(), this.getPos()) || redstoneSignal > 0)
			return;
		
		if(this.getWorld().getTileEntity(new BlockPos(x,y,z)) instanceof TilePool){
			TilePool pool = (TilePool) this.getWorld().getTileEntity(new BlockPos(x,y,z));
			if(mana > ConfigHandler.SPEED && pool.getCurrentMana() < pool.manaCap){
				mana -=ConfigHandler.SPEED;
				pool.recieveMana(ConfigHandler.SPEED);
			}
		}
		
	}
	
	private static final BlockPos[] QUARTZ_LOCATIONS = {
			new BlockPos(2, -1, 2), new BlockPos(2, -1, 1), new BlockPos(2, -1, 0), new BlockPos(2, -1, -1), new BlockPos(2, -1, -2),
			new BlockPos(1, -1, 2), new BlockPos(1, -1, -2),
			new BlockPos(0, -1, 2), new BlockPos(0, -1, -2),
			new BlockPos(-1, -1, 2), new BlockPos(-1, -1, -2),
			new BlockPos(-2, -1, 2), new BlockPos(-2, -1, 1), new BlockPos(-2, -1, 0), new BlockPos(-2, -1, -1), new BlockPos(-2, -1, -2)
	};
	
	private static final BlockPos[] LAMP_LOCATIONS = {
			new BlockPos(1, -1, 1), new BlockPos(-1, -1, 1), new BlockPos(1, -1, -1), new BlockPos(-1, -1, -1)
	};
	
	private static final BlockPos[] PILLAR_LOCATIONS = {
			new BlockPos(1, -1, 0), new BlockPos(0, -1, 1), new BlockPos(-1, -1, 0), new BlockPos(0, -1, -1)
	};


	public static MultiblockSet makeMultiblockSet() {
		Multiblock mb = new Multiblock();

		for(BlockPos o : QUARTZ_LOCATIONS)
			mb.addComponent(o.up(), Blocks.QUARTZ_BLOCK.getDefaultState());
		for(BlockPos o : LAMP_LOCATIONS)
			mb.addComponent(o.up(), Blocks.LAPIS_BLOCK.getDefaultState());
		for(BlockPos o : PILLAR_LOCATIONS)
			mb.addComponent(o.up(), Blocks.OBSIDIAN.getDefaultState());

		return mb.makeSet();
	}
	
	public static boolean canTPExist(World world, BlockPos pos) {
		for(BlockPos o : QUARTZ_LOCATIONS)
			if(world.getBlockState(pos.add(o)).getBlock() != Blocks.QUARTZ_BLOCK)
				return false;
		for(BlockPos o : LAMP_LOCATIONS)
			if(world.getBlockState(pos.add(o)).getBlock() != Blocks.LAPIS_BLOCK)
				return false;

		for(BlockPos o : PILLAR_LOCATIONS)
			if(world.getBlockState(pos.add(o)).getBlock() != Blocks.OBSIDIAN)
				return false;

		return true;
	}
	
	@Override
	public int getMaxMana() {
		return 10000;
	}
	
	@Override
	public LexiconEntry getEntry() {
		return LexiconData.manalinkium;
	}
	
	@Override
	public int getColor() {
		return 0x00FFFF;
	}
}
