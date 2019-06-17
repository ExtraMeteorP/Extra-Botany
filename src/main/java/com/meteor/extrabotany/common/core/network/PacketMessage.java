package com.meteor.extrabotany.common.core.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;

public class PacketMessage extends AbstractPacketThreadsafe {

	public ITextComponent component;

	public PacketMessage(ITextComponent component) {
		this.component = component;
	}

	public PacketMessage() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}

	@Override
	public void handleClientSafe(NetHandlerPlayClient netHandler) {
		netHandler.handleChat(new SPacketChat(component, ChatType.GAME_INFO));
	}

	@Override
	public void handleServerSafe(NetHandlerPlayServer netHandler) {
	}

}
