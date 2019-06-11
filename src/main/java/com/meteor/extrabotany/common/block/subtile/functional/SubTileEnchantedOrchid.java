package com.meteor.extrabotany.common.block.subtile.functional;

import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.lexicon.LexiconData;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileFunctional;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.ModItems;

public class SubTileEnchantedOrchid extends SubTileFunctional{
	
	private static final int RANGE = 1;
	
	private static final String TAG_MANA = "consumed";
	private static final String TAG_COST = "cost";
	private static final String TAG_INGOT = "hasingot";
	
	int consumed = 0;
	int shouldCost = 0;
	boolean hasIngot = false;
	
	private static final BlockPos[] OFFSETS = { 
			new BlockPos(0, -1, 0), new BlockPos(0, -1, 1), new BlockPos(0, -1, -1), new BlockPos(1, -1, 0), new BlockPos(-1, -1, 0), new BlockPos(1, -1, 1), new BlockPos(-1, -1, -1), new BlockPos(1, -1, -1), new BlockPos(-1, -1, 1), 
	};
	
	@Override
	public void writeToPacketNBT(NBTTagCompound cmp) {
		super.writeToPacketNBT(cmp);
		cmp.setInteger(TAG_MANA, consumed);
		cmp.setInteger(TAG_COST, shouldCost);
		cmp.setBoolean(TAG_INGOT, hasIngot);
	}

	@Override
	public void readFromPacketNBT(NBTTagCompound cmp) {
		super.readFromPacketNBT(cmp);
		consumed = cmp.getInteger(TAG_MANA);
		shouldCost = cmp.getInteger(TAG_COST);
		hasIngot = cmp.getBoolean(TAG_INGOT);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if(redstoneSignal > 0)
			return;
		
		int posx = this.supertile.getPos().getX();
		int posy = this.supertile.getPos().getY();
		int posz = this.supertile.getPos().getZ();
		
		shouldCost = ConfigHandler.EO_COST;
		
		if(!hasIngot){
			for(EntityItem item : supertile.getWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(supertile.getPos().add(-RANGE, -RANGE, -RANGE), supertile.getPos().add(RANGE + 1, RANGE + 1, RANGE + 1)))){
				if(item.getItem().getItem() == ModItems.blackLotus && item.getItem().getCount() > 0){
					item.getItem().shrink(1);
					hasIngot = true;
				}
			}
		}
		
		if(hasIngot && consumed < shouldCost && mana > ConfigHandler.EO_SPEED){
			mana -=ConfigHandler.EO_SPEED;
			consumed +=ConfigHandler.EO_SPEED;
		}
		
		int r = ConfigHandler.EO_RANGE;
		
		if(consumed >= shouldCost){
			for(int x = -r; x < r; x++)
				for(int z = -r; z < r; z++){
					if(getWorld().getBlockState(getPos().add(x, -1, z)).getBlock() == Blocks.GRASS && hasIngot){
						if(!getWorld().isRemote)
							getWorld().setBlockState(getPos().add(x, -1, z), ModBlocks.enchantedSoil.getDefaultState());
						hasIngot = false;
						consumed = 0;
						break;
					}	
				}
		}
		
	}
	
	@Override
	public int getColor() {
		return 0x4B0082;
	}
	
	@Override
	public int getMaxMana() {
		return 10000;
	}
	
	@Override
	public LexiconEntry getEntry() {
		return LexiconData.enchantedorchid;
	}
	
	@Override
	public boolean acceptsRedstone() {
		return true;
	}
	
	@Override
	public RadiusDescriptor getRadius() {
		return new RadiusDescriptor.Square(toBlockPos(), RANGE);
	}

}
