package com.meteor.extrabotany.common.block.tile;

import com.meteor.extrabotany.common.item.equipment.tool.ItemNatureOrb;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import vazkii.botania.common.block.tile.TileMod;

public class TileCocoonDesire extends TileMod implements ITickable{
	
	private static final String TAG_TIME_PASSED = "timePassed";
	public static final int TIME = 2400;
	public int timePassed;
	private ItemStack item = ItemStack.EMPTY;
	private int rot;
	private boolean isDirty;

	@Override
	public void update() {
		timePassed++;
		if(timePassed >= TIME){
			hatch();
			timePassed = 0;
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
			if(world.rand.nextInt(100) == 7 && !(getItem().getItem() instanceof ItemNatureOrb))
				world.destroyBlock(pos, false);
			
			EntityLiving entity = null;
			float specialChance = 0.05F;
			if(getItem() == ItemStack.EMPTY || getItem().getItem() instanceof ItemNatureOrb){
				if(Math.random() < specialChance) {
					int entityType = world.rand.nextInt(3);
					switch(entityType) {
					case 0:
						entity = new EntityHorse(world);
						break;
					case 1:
						entity = new EntityWolf(world);
						break;
					case 2:
						entity = new EntityOcelot(world);
						break;
					}
				} else {
					int entityType = world.rand.nextInt(5);
					switch(entityType) {
					case 0:
						entity = new EntitySheep(world);
						break;
					case 1:
						if(Math.random() < 0.01)
							entity = new EntityMooshroom(world);
						else entity = new EntityCow(world);
						break;
					case 2:
						entity = new EntityPig(world);
						break;
					case 3:
						entity = new EntityChicken(world);
						break;
					case 4:
						entity = new EntityRabbit(world);
						break;
					}
				}
			}else{
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
