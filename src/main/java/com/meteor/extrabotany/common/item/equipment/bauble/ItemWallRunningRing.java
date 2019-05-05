package com.meteor.extrabotany.common.item.equipment.bauble;

import baubles.api.BaubleType;
import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.common.lib.LibItemsName;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWallRunningRing extends WallJumpingShim{

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
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void clientWornTick(ItemStack stack, EntityLivingBase player) {
		if(player instanceof EntityPlayerSP && player == Minecraft.getMinecraft().player) {
			if(player.collidedHorizontally){
				if(ExtraBotany.keyUp.isKeyDown())
					player.motionY = Math.max(player.motionY, 0.11F);
			}
		}
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.RING;
	}

}
