package com.meteor.extrabotany.common.block.subtile.functional;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.item.equipment.tool.ItemBinder;
import com.meteor.extrabotany.common.lexicon.LexiconData;
import com.meteor.extrabotany.common.lib.LibAdvancements;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.lexicon.multiblock.Multiblock;
import vazkii.botania.api.lexicon.multiblock.MultiblockSet;
import vazkii.botania.api.mana.IManaPool;
import vazkii.botania.api.subtile.SubTileFunctional;

public class SubTileManalinkium extends SubTileFunctional{
	
	private static final int COST = 2000;
	private static final int RANGE = 1;
	

	private static final String TAG_X = "Posx";
	private static final String TAG_Y = "Posy";
	private static final String TAG_Z = "Posz";
	private static final String TAG_DIM = "dim";
	int x,y,z,dim = 0;
	
	private static final DummyPool fallbackPool = new DummyPool();
	
	@Override
	public void writeToPacketNBT(NBTTagCompound cmp) {
		super.writeToPacketNBT(cmp);
		cmp.setInteger(TAG_X, x);
		cmp.setInteger(TAG_Y, y);
		cmp.setInteger(TAG_Z, z);
		cmp.setInteger(TAG_DIM, dim);
	}

	@Override
	public void readFromPacketNBT(NBTTagCompound cmp) {
		super.readFromPacketNBT(cmp);
		x = cmp.getInteger(TAG_X);
		y = cmp.getInteger(TAG_Y);
		z = cmp.getInteger(TAG_Z);
		dim = cmp.getInteger(TAG_DIM);
	}
	
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) { 
		if(player.getHeldItem(hand).getItem() instanceof ItemBinder){
			ItemBinder bind = (ItemBinder) player.getHeldItem(hand).getItem();
			x = bind.getPosX(player.getHeldItem(hand));
			y = bind.getPosY(player.getHeldItem(hand));
			z = bind.getPosZ(player.getHeldItem(hand));
			dim = bind.getDim(player.getHeldItem(hand));
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
		
		IManaPool pool = getManaPool(new BlockPos(x,y,z), dim);
		if(!(pool instanceof DummyPool)) {
			if(pool == null){
				
			}
			else {
				if(mana > ConfigHandler.SPEED && !pool.isFull()){
					mana -=ConfigHandler.SPEED;
					pool.recieveMana(ConfigHandler.SPEED);
				}
			}
		}
		
	}
	
	public IManaPool getManaPool(BlockPos pos, int dim) {
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		if(server == null)
			return fallbackPool;

		BlockPos coords = pos;
		if(coords.getY() == -1)
			return null;
		
		World world = null;
		for(World w : server.worlds)
			if(w.provider.getDimension() == dim) {
				world = w;
				break;
			}

		if(world != null) {
			TileEntity tile = world.getTileEntity(coords);
			if(tile != null && tile instanceof IManaPool)
				return (IManaPool) tile;
		}

		return null;
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
	
	private static class DummyPool implements IManaPool {

		@Override
		public boolean isFull() {
			return false;
		}

		@Override
		public void recieveMana(int mana) {}

		@Override
		public boolean canRecieveManaFromBursts() {
			return false;
		}

		@Override
		public int getCurrentMana() {
			return 0;
		}

		@Override
		public boolean isOutputtingPower() {
			return false;
		}

		@Override
		public EnumDyeColor getColor() {
			return EnumDyeColor.WHITE;
		}

		@Override
		public void setColor(EnumDyeColor color) {}

	}
}
