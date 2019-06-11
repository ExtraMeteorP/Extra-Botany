package com.meteor.extrabotany.common.brew.potion;

import java.util.List;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.common.lib.LibPotionsName;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;

public class PotionTemptation extends PotionMod{

	public PotionTemptation() {
		super(LibPotionsName.TEMPTATION, false, 0X00000, 7);
	}
	
	@Override
	public void performEffect(@Nonnull EntityLivingBase living, int amplified) {
		float range = 7 + 2 * amplified;
		AxisAlignedBB axis = new AxisAlignedBB(living.posX - range, living.posY - range, living.posZ - range, living.posX + range, living.posY + range, living.posZ + range);
		List<EntityLiving> entities = living.world.getEntitiesWithinAABB(EntityLiving.class, axis);
		for(EntityLiving entity : entities) {
			if(entity == living)
				continue;
			entity.getNavigator().tryMoveToXYZ(living.posX, living.posY, living.posZ, 1.2F);
		}
	}

}
