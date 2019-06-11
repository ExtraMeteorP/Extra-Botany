package com.meteor.extrabotany.common.core.network;

import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.item.equipment.bauble.ItemJingweiFeather;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;

public class PacketLeftClickJingwei extends AbstractPacketThreadsafe{

	@Override
	public void handleClientSafe(NetHandlerPlayClient netHandler) {}

	@Override
	public void handleServerSafe(NetHandlerPlayServer netHandler) {
		EntityPlayerMP player = netHandler.player;
		player.mcServer.addScheduledTask(() -> ((ItemJingweiFeather) ModItems.jingweifeather).trySpawnFireball(player));
	}

	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}
}
