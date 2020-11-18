package com.meteor.extrabotany.common.item.relic;

import javax.annotation.Nonnull;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

import com.meteor.extrabotany.client.core.handler.EventHandlerClient;
import com.meteor.extrabotany.common.core.handler.PlayerStatHandler;
import com.meteor.extrabotany.common.core.network.ExtraBotanyNetwork;
import com.meteor.extrabotany.common.core.network.PacketCursor;
import com.meteor.extrabotany.common.item.ItemMod;
import com.meteor.extrabotany.common.lib.LibItemsName;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemRainbowCursor extends ItemMod{
	
	public ItemRainbowCursor() {
		super(LibItemsName.RAINBOWCURSOR);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		PlayerStatHandler.setCursor(player, !PlayerStatHandler.getCursor(player));
		if (world.isRemote) {
			if(PlayerStatHandler.getCursor(player)) {
				EventHandlerClient.loadCursor();
			}else {
				try {
					Mouse.setNativeCursor(null);
				} catch (LWJGLException e) {
					e.printStackTrace();
				}
			}
		}else {
			stack.shrink(1);
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
	
	@SubscribeEvent
	public void playerLoggedIn(EntityJoinWorldEvent event) {
		if(event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			//ExtraBotanyNetwork.instance.sendToAll(new PacketCursor(PlayerStatHandler.getCursor(player)));
			if(player.world.isRemote) {
				if(PlayerStatHandler.getCursor(player)) {
					EventHandlerClient.loadCursor();
				}
			}
		}
	}



}
