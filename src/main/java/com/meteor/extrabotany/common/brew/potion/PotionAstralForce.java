package com.meteor.extrabotany.common.brew.potion;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.common.lib.LibPotionsName;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import vazkii.botania.common.core.helper.Vector3;
import vazkii.botania.common.entity.EntityFallingStar;

public class PotionAstralForce extends PotionMod{

	public PotionAstralForce() {
		super(LibPotionsName.ASTRALFORCE, false, 0XFFD700, 7);
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration % 2 == 0;
	}

	@Override
	public void performEffect(@Nonnull EntityLivingBase living, int amplified) {
		World world = living.world;
		float range = 1.3F + 0.5F * amplified;
		float drange = 2 * range;

			Vector3 posVec = Vector3.fromBlockPos(living.getPosition());
			Vector3 motVec = new Vector3((drange * Math.random() - range) * 18, 24, (drange * Math.random() - range) * 18);
			posVec = posVec.add(motVec);
			motVec = motVec.normalize().negate().multiply(1.5);
	
			EntityFallingStar star = new EntityFallingStar(world, living);
			star.setPosition(living.posX, posVec.y, living.posZ);
			star.motionX = motVec.x;
			star.motionY = motVec.y;
			star.motionZ = motVec.z;
			world.spawnEntity(star);
			
			if(Math.random() < 0.05F * amplified){
				EntityFallingStar bonusStar = new EntityFallingStar(world, living);
				bonusStar.setPosition(living.posX, posVec.y, living.posZ);
				bonusStar.motionX = motVec.x + Math.random() - 0.5;
				bonusStar.motionY = motVec.y + Math.random() - 0.5;
				bonusStar.motionZ = motVec.z + Math.random() - 0.5;
				world.spawnEntity(bonusStar);
			}
		
		
	}

}
