package com.meteor.extrabotany.common.block.tile;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.api.item.INatureOrb;
import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.crafting.recipe.RecipePedestal;
import com.meteor.extrabotany.common.item.ModItems;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.botania.api.lexicon.multiblock.Multiblock;
import vazkii.botania.api.lexicon.multiblock.MultiblockSet;
import vazkii.botania.api.state.BotaniaStateProps;
import vazkii.botania.api.state.enums.PylonVariant;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.ModBlocks;

public class TilePedestal extends TileInventoryBase implements ITickable{

	public TilePedestal() {
		super(1,1);
	}

	private int rot;
	private boolean isDirty;
	public int strikes = 0;
	private int solar = 0;

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound){
		super.readFromNBT(nbttagcompound);
		rot = nbttagcompound.getInteger("Rot");
		strikes = nbttagcompound.getInteger("strikes");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound){
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("Rot", rot);
		nbttagcompound.setInteger("strikes", strikes);

		return nbttagcompound;
	}

	@Override
	public void update(){
		if(isDirty){
			world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 2);
			isDirty = false;
		}

		if(rot == 360)
			rot = 0;
		if(!getItem().isEmpty())
			rot++;
		 
		if(getItem().getItem() instanceof INatureOrb){
			INatureOrb o = (INatureOrb) getItem().getItem();
			if(canInfuse2(world, pos)){
				if (!getWorld().isRemote)
					o.addXP(getItem(), 9);
				Botania.proxy.sparkleFX(pos.getX() + 0.5F, pos.getY() + 1.3F, pos.getZ() + 0.5F, 0.1F, 1F, 0.1F, 4F, 10);
			}else if(canInfuse(world, pos)){
				if (!getWorld().isRemote)
					o.addXP(getItem(), 4);
				Botania.proxy.sparkleFX(pos.getX() + 0.5F, pos.getY() + 1.3F, pos.getZ() + 0.5F, 0.15F, 0.8F, 0.15F, 3F, 10);
			}
		}
		
		if(getItem().getItem() == ModItems.nightmareFuel){
			if(world.isDaytime()){
				solar++;
				if(solar >= 6000)
					setItem(new ItemStack(ModItems.spiritFuel));
			}
		}else
			solar = 0;
		
		if (getStrikes() >= ConfigHandler.CRAFTING_STRIKES) {
			ItemStack input = getItem();
			for(RecipePedestal rp : ExtraBotanyAPI.pedestalRecipes){
				ItemStack output = rp.getOutput(getItem());
				if (output.isEmpty() || output.equals(input)) {
					setStrikes(0);
					return;
				} else {
					setStrikes(0);
					setItem(output);
					EntityXPOrb orb = new EntityXPOrb(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 1);
					if(!world.isRemote)
						world.spawnEntity(orb);
					break;
				}
			}
		}
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag(){
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet){
		readFromNBT(packet.getNbtCompound());
	}
	
	public static final BlockPos[] PYLON_LOCATIONS = {
			new BlockPos(2, 0, 2), new BlockPos(-2, 0, 2), new BlockPos(2, 0, -2), new BlockPos(-2, 0, -2)
	};
	
	public static final BlockPos[] PYLON2_LOCATIONS = {
			new BlockPos(3, 1, 3), new BlockPos(-3, 1, 3), new BlockPos(3, 1, -3), new BlockPos(-3, 1, -3),
			new BlockPos(2, 0, 2), new BlockPos(-2, 0, 2), new BlockPos(2, 0, -2), new BlockPos(-2, 0, -2)
	};
	
	public static final BlockPos[] POOL_LOCATIONS = {
			new BlockPos(3, 0, 3), new BlockPos(-3, 0, 3), new BlockPos(3, 0, -3), new BlockPos(-3, 0, -3)
	};
	
	public static final BlockPos[] ROCK_LOCATIONS = {
			new BlockPos(2, -1, 2), new BlockPos(2, -1, 1), new BlockPos(2, -1, 0), new BlockPos(2, -1, -1), new BlockPos(2, -1, -2),
			new BlockPos(1, -1, 2),new BlockPos(1, -1, -2),
			new BlockPos(0, -1, 2),new BlockPos(0, -1, -2),
			new BlockPos(-1, -1, 2),new BlockPos(-1, -1, -2),
			new BlockPos(-2, -1, 2),new BlockPos(-2, -1, 1),new BlockPos(-2, -1, 0),new BlockPos(-2, -1, -1),new BlockPos(-2, -1, -2),
	};
	
	public static MultiblockSet makeMultiblockSet2() {
		Multiblock mb = new Multiblock();
		for(BlockPos o : TilePedestal.PYLON2_LOCATIONS)
			mb.addComponent(o.up(), ModBlocks.pylon.getStateFromMeta(1));
		for(BlockPos o : TilePedestal.POOL_LOCATIONS)
			mb.addComponent(o.up(), ModBlocks.pool.getDefaultState());
		for(BlockPos o : TilePedestal.ROCK_LOCATIONS)
			mb.addComponent(o.up(), ModBlocks.shimmerrock.getDefaultState());
		mb.addComponent(new BlockPos(0,0,0).up(), com.meteor.extrabotany.common.block.ModBlocks.pedestal.getDefaultState());

		return mb.makeSet();
	}
	
	public static MultiblockSet makeMultiblockSet() {
		Multiblock mb = new Multiblock();
		for(BlockPos o : PYLON_LOCATIONS)
			mb.addComponent(o.up(), ModBlocks.pylon.getStateFromMeta(1));
		mb.addComponent(new BlockPos(0,0,0).up(), com.meteor.extrabotany.common.block.ModBlocks.pedestal.getDefaultState());
		return mb.makeSet();
	}
	
	public static boolean canInfuse(World world, BlockPos pos) {
		for(BlockPos o : PYLON_LOCATIONS)
			if(world.getBlockState(pos.add(o)).getBlock() != ModBlocks.pylon || world.getBlockState(pos.add(o)).getValue(BotaniaStateProps.PYLON_VARIANT) != PylonVariant.NATURA)
				return false;

		return true;
	}
	
	public static boolean canInfuse2(World world, BlockPos pos) {
		for(BlockPos o : PYLON2_LOCATIONS)
			if(world.getBlockState(pos.add(o)).getBlock() != ModBlocks.pylon || world.getBlockState(pos.add(o)).getValue(BotaniaStateProps.PYLON_VARIANT) != PylonVariant.NATURA)
				return false;
		for(BlockPos o : POOL_LOCATIONS)
			if(world.getBlockState(pos.add(o)).getBlock() != ModBlocks.pool)
				return false;
		for(BlockPos o : ROCK_LOCATIONS)
			if(world.getBlockState(pos.add(o)).getBlock() != ModBlocks.shimmerrock)
				return false;

		return true;
	}
	
	public void markForUpdate() {
        IBlockState state = this.getWorld().getBlockState(this.getPos());
        this.getWorld().notifyBlockUpdate(this.getPos(), state, state, 3);
    }
	
	public void setStrikes(int strikes) {
		this.strikes = strikes;
		markForUpdate();
	}

	public int getStrikes() {
		return strikes;
	}

	public int getRotation(){
		return rot;
	}

	public ItemStack getItem(){
		return stacks.getStackInSlot(0);
	}

	public void setItem(ItemStack item){
		stacks.setStackInSlot(0, item);;
		isDirty = true;
	}
}
