package com.meteor.extrabotany.common.block.subtile.functional;

import com.meteor.extrabotany.common.block.tile.TilePedestal;
import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.item.ItemFriedChicken;
import com.meteor.extrabotany.common.lexicon.LexiconData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTableList;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileFunctional;

import java.util.List;
import java.util.Random;

public class SubTileAnnoyingFlower extends SubTileFunctional{
	
	private static final int COST = ConfigHandler.ANNOYINGFLOWER_COST;
	private static final int RANGE = 3;
	private static final int[][] OFFSETS = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 }, { -1, 1 }, { -1, -1 }, { 1, 1 }, { 1, -1 } };
	private static final String TAG_TIME = "times";
	int times;
	
	@Override
	public void writeToPacketNBT(NBTTagCompound cmp) {
		super.writeToPacketNBT(cmp);
		cmp.setInteger(TAG_TIME, times);
	}

	@Override
	public void readFromPacketNBT(NBTTagCompound cmp) {
		super.readFromPacketNBT(cmp);
		times = cmp.getInteger(TAG_TIME);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if(redstoneSignal > 0)
			return;
		
		boolean hasWater = false;
		
		for(int x = -4; x < 4; x++){
			for(int z = -4; z < 4; z++){
				BlockPos posi = new BlockPos(supertile.getPos().add(x, 0, z));
				if(supertile.getWorld().getTileEntity(posi) instanceof TilePedestal){
					TilePedestal te = (TilePedestal) supertile.getWorld().getTileEntity(posi);
					Item i = te.getItem().getItem();
					if(i != null){
						if(i == Items.WATER_BUCKET){
							hasWater = true;
						}
					}
				}
			}
        }
		
		for(EntityItem item : supertile.getWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(supertile.getPos().add(-RANGE, -RANGE, -RANGE), supertile.getPos().add(RANGE + 1, RANGE + 1, RANGE + 1)))){
			if(item.getItem().getItem() instanceof ItemFriedChicken && item.getItem().getCount() > 0){
				item.getItem().shrink(1);
				times += ConfigHandler.TIMES;
			}
		}
		
		int cd = times > 0 ? (ConfigHandler.TICKS * 2/5) : ConfigHandler.TICKS;
		
		if(redstoneSignal == 0 && ticksExisted % cd == 0 && mana >= COST && hasWater && !this.getWorld().isRemote) {
			Random rand = supertile.getWorld().rand;
			ItemStack stack;
			do {
				if(times > 0){
					LootContext.Builder lootcontext$builder = new LootContext.Builder((WorldServer) this.getWorld());
					List<ItemStack> rares = this.supertile.getWorld().getLootTableManager().getLootTableFromLocation(LootTableList.GAMEPLAY_FISHING_TREASURE).generateLootForPools(rand, lootcontext$builder.build());
					stack = rares.get(rand.nextInt(rares.size()));
					times--;
				}else {
					LootContext.Builder lootcontext$builder = new LootContext.Builder((WorldServer)this.getWorld());
					List<ItemStack> items = this.supertile.getWorld().getLootTableManager().getLootTableFromLocation(LootTableList.GAMEPLAY_FISHING).generateLootForPools(rand, lootcontext$builder.build());
					stack = items.get(rand.nextInt(items.size()));
				}
				
			} while(stack == null);
		
			int bound = RANGE * 2 + 1;
			EntityItem entity = new EntityItem(supertile.getWorld(), supertile.getPos().getX() - RANGE + rand.nextInt(bound) , supertile.getPos().getY() + 2, supertile.getPos().getZ() - RANGE + rand.nextInt(bound), stack);
			entity.motionX = 0;
			entity.motionY = 0;
			entity.motionZ = 0;
			
			if(!supertile.getWorld().isRemote)
				supertile.getWorld().spawnEntity(entity);
			mana -= COST;
			sync();
		}
	}

	@Override
	public int getColor() {
		return 0x000000;
	}

	@Override
	public int getMaxMana() {
		return 1000;
	}
	
	@Override
	public LexiconEntry getEntry() {
		return LexiconData.annoyingflower;
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
