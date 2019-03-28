package com.meteor.extrabotany.common.item.lens;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import vazkii.botania.api.internal.IManaBurst;
import vazkii.botania.common.core.helper.Vector3;
import vazkii.botania.common.item.lens.Lens;

public class LensSexpistol extends Lens{
	
	private static final String TAG_HOME_ID = "homeID";
	
	@Override
	public void updateBurst(IManaBurst burst, EntityThrowable entity, ItemStack stack) {	
		AxisAlignedBB axis = new AxisAlignedBB(entity.posX, entity.posY, entity.posZ, entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).grow(3);
		List<EntityLivingBase> entities = entity.world.getEntitiesWithinAABB(EntityLivingBase.class, axis);
		int homeID = entity.getEntityData().getInteger(TAG_HOME_ID);
		for(EntityLivingBase living : entities) {
			entity.getEntityData().setInteger(TAG_HOME_ID, living.getEntityId());
			break;
		}
		
		Entity result = entity.world.getEntityByID(homeID);
		if(result != null && result.getDistance(entity) < 3F && !burst.isFake()){
			Vector3 vecEntity = Vector3.fromEntityCenter(result);
			Vector3 vecThis = Vector3.fromEntityCenter(entity);
			Vector3 vecMotion = vecEntity.subtract(vecThis);
			Vector3 vecCurrentMotion = new Vector3(entity.motionX, entity.motionY, entity.motionZ);
			vecMotion.normalize().multiply(vecCurrentMotion.mag());
			burst.setMotion(vecMotion.x, vecMotion.y, vecMotion.z);
		}
		
	}

}
