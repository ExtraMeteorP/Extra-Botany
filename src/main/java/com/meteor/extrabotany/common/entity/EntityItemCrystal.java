package com.meteor.extrabotany.common.entity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.botania.common.block.tile.mana.TilePool;

public class EntityItemCrystal extends EntityItem{
	
	private boolean canAbsorb;
	private static final DataParameter<Boolean> ABSORB = EntityDataManager.<Boolean>createKey(EntityItemCrystal.class, DataSerializers.BOOLEAN);
	
	public EntityItemCrystal(World worldIn, double x, double y, double z, ItemStack stack) {
		super(worldIn, x, y, z, stack);
	}
	
	@Override
    protected void entityInit(){
    	super.entityInit();
        dataManager.register(ABSORB, false);
    }
	
	@Override
	public void onUpdate(){
		super.onUpdate();
		if(this.getEntityWorld().getTileEntity(this.getPosition().add(0, -1, 0)) instanceof TilePool){
			TilePool pool = (TilePool) this.getEntityWorld().getTileEntity(this.getPosition().add(0, -1, 0));
			if(pool.getCurrentMana() < pool.manaCap && getAbsorb() && !world.isRemote){
				pool.recieveMana(100000);
				this.getItem().shrink(1);
			}
		}
	}
	
	@Override
    public void writeEntityToNBT(NBTTagCompound cmp){
		super.writeEntityToNBT(cmp);
		cmp.setBoolean("absorb", getAbsorb());
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound cmp){
		super.readEntityFromNBT(cmp);
		setAbsorb(cmp.getBoolean("absorb"));
	}
	
	public Boolean getAbsorb() {
		return dataManager.get(ABSORB);
	}
	
	public void setAbsorb(Boolean eff) {
		dataManager.set(ABSORB, eff);;
	}

}
