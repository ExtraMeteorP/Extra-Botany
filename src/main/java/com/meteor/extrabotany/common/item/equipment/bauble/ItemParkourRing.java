package com.meteor.extrabotany.common.item.equipment.bauble;

import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.common.lib.LibItemsName;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void clientWornTick(ItemStack stack, EntityLivingBase player) {
		super.clientWornTick(stack, player);
		if(player instanceof EntityPlayerSP && player == Minecraft.getMinecraft().player) {
			if(player.collidedHorizontally){
				if(ExtraBotany.keyUp.isKeyDown())
					player.motionY = Math.max(player.motionY, 0.11F);
			}
		}
	}

	@Override
	public int getMaxAllowedJumps() {
		return 8;
	}

}
