package com.meteor.extrabotany.common.core.network;

import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.item.relic.ItemBuddhistRelics;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;

public class PacketLeftClickCopy extends AbstractPacketThreadsafe{

	@Override
	public void handleClientSafe(NetHandlerPlayClient netHandler) {}

	@Override
	public void handleServerSafe(NetHandlerPlayServer netHandler) {
		EntityPlayerMP player = netHandler.player;
		player.mcServer.addScheduledTask(() -> ((ItemBuddhistRelics) ModItems.relics).trySpawnBurst(player));
	}

	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}
}
