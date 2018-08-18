package com.meteor.extrabotany.common.block.tile;

import java.util.List;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import vazkii.botania.common.block.tile.TileMod;

public class TileCocoonDesire extends TileMod implements ITickable{
	
	private static final String TAG_TIME_PASSED = "timePassed";
	public static final int TIME = 2400;
	public int timePassed;
	private ItemStack item = ItemStack.EMPTY;
	private int rot;
	private boolean isDirty;
	private static Item[] items = new Item[]{
			Items.CHORUS_FRUIT
	};

	@Override
	public void update() {
		timePassed++;
		if(timePassed >= TIME){
			hatch();
			timePassed = 0;
		}
		
		if(getItem() == ItemStack.EMPTY && !world.isRemote){
			for(EntityItem e : getItemsAround()){
				ItemStack stack = e.getItem();
				for(int i = 0; i < items.length; i++)
					if(stack.getItem().equals(items[i])){
						ItemStack newItem = stack.copy();
						newItem.setCount(1);
						setItem(newItem);
						stack.shrink(1);
					}			
			}
		}
		
		if(isDirty){
			world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 2);
			isDirty = false;
		}

		if(rot == 360)
			rot = 0;
		if(!item.isEmpty())
			rot++;
	}
	
	private void hatch() {
		if(!world.isRemote) {
		
			EntityLiving entity = null;
			float specialChance = 0.05F;
			if(getItem() != ItemStack.EMPTY){
				Item i = getItem().getItem();
				if(i == Items.CHORUS_FRUIT)
					entity = new EntityShulker(world);
				
				if(entity != null)
					setItem(ItemStack.EMPTY);
			}
			if(entity != null) {
				entity.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
				if(entity instanceof EntityAgeable)
					((EntityAgeable) entity).setGrowingAge(-24000);
				entity.onInitialSpawn(world.getDifficultyForLocation(getPos()), null);
				world.spawnEntity(entity);
				entity.spawnExplosionParticle();
			}
		}
	}
	
	private List<EntityItem> getItemsAround() {
		BlockPos source = this.pos;
		float range = 1F;
		return world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range, source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
	}
	
	public int getRotation(){
		return rot;
	}

	public ItemStack getItem(){
		return item;
	}

	public void setItem(ItemStack item){
		this.item = item;
		isDirty = true;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound){
		super.readFromNBT(nbttagcompound);
		NBTTagCompound nbtItem = nbttagcompound.getCompoundTag("Item");
		item = new ItemStack(nbtItem);
		rot = nbttagcompound.getInteger("Rot");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound){
		super.writeToNBT(nbttagcompound);
		NBTTagCompound nbtItem = new NBTTagCompound();
		if(!item.isEmpty())
			item.writeToNBT(nbtItem);
		nbttagcompound.setTag("Item", nbtItem);
		nbttagcompound.setInteger("Rot", rot);

		return nbttagcompound;
	}

}
