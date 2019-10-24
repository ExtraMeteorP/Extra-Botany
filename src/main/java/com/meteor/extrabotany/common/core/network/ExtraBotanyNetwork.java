package com.meteor.extrabotany.common.core.network;

import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ExtraBotanyNetwork extends NetworkWrapper {

	public static ExtraBotanyNetwork instance = new ExtraBotanyNetwork();

	public ExtraBotanyNetwork() {
		super(LibMisc.MOD_ID);
	}

	public void setup() {
		registerPacketClient(FluidUpdatePacket.class);
		registerPacketClient(SpecialParticlePacket.class);
		registerPacketServer(PacketLeftClick.class);
		registerPacketServer(PacketLeftClickCopy.class);
		registerPacketServer(PacketLeftClickJingwei.class);
		registerPacketServer(PacketLeftClickSpear.class);
	}

	public static void sendPacket(Entity player, Packet<?> packet) {
		if (player instanceof EntityPlayerMP && ((EntityPlayerMP) player).connection != null) {
			((EntityPlayerMP) player).connection.sendPacket(packet);
		}
	}

	public static void sendToAll(AbstractPacket packet) {
		instance.network.sendToAll(packet);
	}

	public static void sendTo(AbstractPacket packet, EntityPlayerMP player) {
		instance.network.sendTo(packet, player);
	}

	public static void sendToAllAround(AbstractPacket packet, NetworkRegistry.TargetPoint point) {
		instance.network.sendToAllAround(packet, point);
	}

	public static void sendToDimension(AbstractPacket packet, int dimensionId) {
		instance.network.sendToDimension(packet, dimensionId);
	}

	public static void sendToServer(AbstractPacket packet) {
		instance.network.sendToServer(packet);
	}

	public static void sendToClients(WorldServer world, BlockPos pos, AbstractPacket packet) {
		Chunk chunk = world.getChunkFromBlockCoords(pos);
		for (EntityPlayer player : world.playerEntities) {
			// only send to relevant players
			if (!(player instanceof EntityPlayerMP)) {
				continue;
			}
			EntityPlayerMP playerMP = (EntityPlayerMP) player;
			if (world.getPlayerChunkMap().isPlayerWatchingChunk(playerMP, chunk.x, chunk.z)) {
				ExtraBotanyNetwork.sendTo(packet, playerMP);
			}
		}
	}

}
