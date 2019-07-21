package com.meteor.extrabotany.common.core.network;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.PacketBuffer;

public class SpecialParticlePacket extends AbstractPacketThreadsafe {

	public UUID uuid;

	public SpecialParticlePacket(UUID uuid) {
		this.uuid = uuid;
	}

	public SpecialParticlePacket() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		PacketBuffer buffer = new PacketBuffer(buf);
		uuid = buffer.readUniqueId();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		PacketBuffer buffer = new PacketBuffer(buf);
		buffer.writeUniqueId(uuid);
	}

	@Override
	public void handleClientSafe(NetHandlerPlayClient netHandler) {

	}

	@Override
	public void handleServerSafe(NetHandlerPlayServer netHandler) {
		// clientside only
		throw new UnsupportedOperationException("Serverside only");
	}
}
