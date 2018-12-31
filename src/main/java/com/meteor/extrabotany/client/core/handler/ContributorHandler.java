package com.meteor.extrabotany.client.core.handler;

import java.util.HashMap;
import java.util.UUID;

import com.meteor.extrabotany.client.particle.ParticleCloudPattern;
import com.meteor.extrabotany.common.core.handler.PersistentVariableHandler;
import com.meteor.extrabotany.common.core.network.ExtraBotanyNetwork;
import com.meteor.extrabotany.common.core.network.SpecialParticlePacket;
import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)
public class ContributorHandler {
	
	public static HashMap<UUID, Long> SPECIAL_ENTITIES = new HashMap<>();
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event) {
		if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().player.world != null && !Minecraft.getMinecraft().isGamePaused() && Minecraft.getMinecraft().player.world.getTotalWorldTime() % 11 == 0) {
			BlockPos pos = new BlockPos(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY, Minecraft.getMinecraft().player.posZ);
	        Minecraft.getMinecraft().player.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.add(32, 32, 32), pos.add(-32, -32, -32)),
	        input -> PersistentVariableHandler.contributors.contains(input.getGameProfile().getName())).
	        forEach(living -> Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleCloudPattern(living)));
		}
	}
	 
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) return;
		if (event.player.world.getTotalWorldTime() % 20 == 0) {
			ExtraBotanyNetwork.sendToAllAround(new SpecialParticlePacket(event.player.getUniqueID()), new NetworkRegistry.TargetPoint(event.player.dimension,
                    event.player.posX, event.player.posY, event.player.posZ, 64));
            return;
	    }
	}

}
