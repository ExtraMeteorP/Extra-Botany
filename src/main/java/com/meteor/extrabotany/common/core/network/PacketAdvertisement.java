package com.meteor.extrabotany.common.core.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;

public class PacketAdvertisement extends AbstractPacketThreadsafe {

	public ITextComponent component;

	public PacketAdvertisement(ITextComponent component) {
		this.component = component;
	}

	public PacketAdvertisement() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}

	@Override
	public void handleClientSafe(NetHandlerPlayClient netHandler) {
		Minecraft mc = Minecraft.getMinecraft();
		GameSettings gameSettings = mc.gameSettings;
		if (gameSettings.language.equals("zh_cn"))
			netHandler.handleChat(new SPacketChat(component, ChatType.GAME_INFO));
	}

	@Override
	public void handleServerSafe(NetHandlerPlayServer netHandler) {
	}
}
