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
import vazkii.botania.common.network.PacketHandler;
import vazkii.botania.common.network.PacketJump;

import java.util.UUID;

public class ItemWallJumpingRing extends WallJumpingShim{
	
	public ItemWallJumpingRing(String name) {
		super(name);
	}

	public ItemWallJumpingRing() {
		this(LibItemsName.BAUBLE_WALLJUMPING);
	}
	
	private static int timesJumped;
	private static boolean jumpDown;
	
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
			EntityPlayerSP playerSp = (EntityPlayerSP) player;
			UUID uuid = playerSp.getUniqueID();
			
			if(player.collidedHorizontally){
				if(ExtraBotany.keyDown.isKeyDown())
					player.motionY = 0F;
			}

			if(playerSp.onGround)
				timesJumped = 0;		
			else {
				if(playerSp.movementInput.jump && playerSp.collidedHorizontally) {
					if(!jumpDown && timesJumped < getMaxAllowedJumps()) {
						playerSp.jump();
						PacketHandler.sendToServer(new PacketJump());
						timesJumped++;
					}
					jumpDown = true;
				} else jumpDown = false;
			}
		}
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.RING;
	}
}
