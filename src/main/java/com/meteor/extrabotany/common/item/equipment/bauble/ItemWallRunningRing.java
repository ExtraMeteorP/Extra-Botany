package com.meteor.extrabotany.common.item.equipment.bauble;

import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.common.lib.LibItemsName;

import baubles.api.BaubleType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemWallRunningRing extends ItemBauble{

	public ItemWallRunningRing() {
		super(LibItemsName.BAUBLE_WALLRUNNING);
	}
	
	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase entity) {
		super.onWornTick(stack, entity);
		if(!(entity instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) entity;
		player.fallDistance = 0F;
		if(player.collidedHorizontally){
			if(ExtraBotany.keyUp.isKeyDown())
				player.motionY = Math.max(player.motionY, 0.09F);
		}
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.RING;
	}

}
