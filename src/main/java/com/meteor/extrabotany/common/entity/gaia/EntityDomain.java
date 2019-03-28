package com.meteor.extrabotany.common.entity.gaia;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityDomain extends Entity{

	public EntityDomain(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void entityInit() {
		
	}
	
	@Override
	public void onUpdate(){
		if(getHostAround().isEmpty())
			setDead();
	}
	
	private List<EntitySwordDomain> getHostAround() {
		BlockPos source = this.getPosition();
		float range = 15F;
		return world.getEntitiesWithinAABB(EntitySwordDomain.class, new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range, source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
	}


	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		
	}

}
