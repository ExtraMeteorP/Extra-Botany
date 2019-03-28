package com.meteor.extrabotany.common.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import vazkii.botania.common.core.helper.Vector3;
import vazkii.botania.common.entity.EntityFallingStar;

public class EntityBottledStar extends EntityThrowableCopy{

	public EntityBottledStar(World worldIn) {
		super(worldIn);
	}
	
	public EntityBottledStar(World world, EntityLivingBase thrower) {
		super(world, thrower);
	}
	
	@Override
	public void onUpdate(){
		this.motionX=this.motionY=this.motionZ=0;
		super.onUpdate();
		if(this.ticksExisted > 120)
			setDead();
		if(this.getThrower() != null){
			World world = this.getThrower().world;
			float range = 1.3F + 0.5F;
			float drange = 2 * range;
	
				Vector3 posVec = Vector3.fromBlockPos(this.getPosition());
				Vector3 motVec = new Vector3((drange * Math.random() - range) * 18, 24, (drange * Math.random() - range) * 18);
				posVec = posVec.add(motVec);
				motVec = motVec.normalize().negate().multiply(1.5);
		
				EntityFallingStar star = new EntityFallingStar(world, this.getThrower());
				star.setPosition(this.posX, posVec.y, this.posZ);
				star.motionX = motVec.x;
				star.motionY = motVec.y;
				star.motionZ = motVec.z;
				world.spawnEntity(star);
				
				if(Math.random() < 0.05F){
					EntityFallingStar bonusStar = new EntityFallingStar(world, this.getThrower());
					bonusStar.setPosition(this.posX, posVec.y, this.posZ);
					bonusStar.motionX = motVec.x + Math.random() - 0.5;
					bonusStar.motionY = motVec.y + Math.random() - 0.5;
					bonusStar.motionZ = motVec.z + Math.random() - 0.5;
					world.spawnEntity(bonusStar);
				}
		}
	}

	@Override
	protected void entityInit() {
		
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		
	}

}
