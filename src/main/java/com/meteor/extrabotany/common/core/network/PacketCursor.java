package com.meteor.extrabotany.common.core.network;

import com.meteor.extrabotany.client.core.handler.EventHandlerClient;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetHandlerPlayServer;

public class PacketCursor extends AbstractPacketThreadsafe {
	
	public boolean getCursor = false;
	
	public PacketCursor(boolean getCursor) {
		this.getCursor = getCursor;
	}

	@Override
	public void handleClientSafe(NetHandlerPlayClient netHandler) {
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		if(getCursor) {
			EventHandlerClient.loadCursor();
		}
	}

	@Override
	public void handleServerSafe(NetHandlerPlayServer netHandler) {

	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.getCursor = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(this.getCursor);
	}

}
