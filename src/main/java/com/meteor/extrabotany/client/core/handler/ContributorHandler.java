package com.meteor.extrabotany.client.core.handler;

import com.meteor.extrabotany.client.particle.ParticleCloudPattern;
import com.meteor.extrabotany.common.core.handler.PersistentVariableHandler;
import com.meteor.extrabotany.common.lib.LibMisc;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)
public class ContributorHandler {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().player.world != null && !Minecraft.getMinecraft().isGamePaused() && Minecraft.getMinecraft().player.world.getTotalWorldTime() % 11 == 0) {
            BlockPos pos = new BlockPos(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY, Minecraft.getMinecraft().player.posZ);
            Minecraft.getMinecraft().player.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.add(32, 32, 32), pos.add(-32, -32, -32)),
                    input -> PersistentVariableHandler.contributors.contains(input.getGameProfile().getName()) || PersistentVariableHandler.contributorsuuid.contains(input.getUniqueID())).
                    forEach(living -> Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleCloudPattern(living)));
        }
    }

}
