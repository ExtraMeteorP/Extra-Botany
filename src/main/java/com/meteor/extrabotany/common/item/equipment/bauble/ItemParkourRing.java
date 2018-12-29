package com.meteor.extrabotany.common.item.equipment.bauble;

import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.lib.LibItemsName;

import baubles.api.BaublesApi;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemParkourRing extends ItemWallJumpingRing{

	public ItemParkourRing() {
		super(LibItemsName.BAUBLE_PARKOUR);
	}
	
	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase entity) {
		super.onWornTick(stack, entity);
		if(!(entity instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) entity;
		if(BaublesApi.isBaubleEquipped(player, ModItems.wallrunning) != -1 || BaublesApi.isBaubleEquipped(player, ModItems.parkour) != -1){
			player.fallDistance = 0F;
			if(player.collidedHorizontally){
				if(ExtraBotany.keyUp.isKeyDown())
					player.motionY = Math.max(player.motionY, 0.09F);
			}
		}
	}

	@Override
	public int getMaxAllowedJumps() {
		return 8;
	}

}
