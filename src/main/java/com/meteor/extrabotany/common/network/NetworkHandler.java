package com.meteor.extrabotany.common.network;

import com.meteor.extrabotany.common.libs.LibMisc;
import com.meteor.extrabotany.common.network.flamescion.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {

    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessage() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(LibMisc.MOD_ID + ":networking"),
                () -> "1.0",
                (s) -> true,
                (s) -> true
        );
        INSTANCE.registerMessage(
                nextID(),
                HerrscherEnergyUpdatePack.class,
                HerrscherEnergyUpdatePack::toBytes,
                HerrscherEnergyUpdatePack::new,
                HerrscherEnergyUpdatePack::handler
        );

        INSTANCE.registerMessage(
                nextID(),
                HerrscherSkillPack.class,
                HerrscherSkillPack::toBytes,
                HerrscherSkillPack::new,
                HerrscherSkillPack::handler
        );

        INSTANCE.registerMessage(
                nextID(),
                MountableUpdatePack.class,
                MountableUpdatePack::toBytes,
                MountableUpdatePack::new,
                MountableUpdatePack::handler
        );

        INSTANCE.registerMessage(
                nextID(),
                UfoCatchPack.class,
                UfoCatchPack::toBytes,
                UfoCatchPack::new,
                UfoCatchPack::handler
        );

        INSTANCE.registerMessage(
                nextID(),
                FlamescionStateUpdatePack.class,
                FlamescionStateUpdatePack::toBytes,
                FlamescionStateUpdatePack::new,
                FlamescionStateUpdatePack::handler
        );

        INSTANCE.registerMessage(
                nextID(),
                FlamescionShiftPack.class,
                FlamescionShiftPack::toBytes,
                FlamescionShiftPack::new,
                FlamescionShiftPack::handler
        );

        INSTANCE.registerMessage(
                nextID(),
                FlamescionQPack.class,
                FlamescionQPack::toBytes,
                FlamescionQPack::new,
                FlamescionQPack::handler
        );

        INSTANCE.registerMessage(
                nextID(),
                FlamescionStrengthenPack.class,
                FlamescionStrengthenPack::toBytes,
                FlamescionStrengthenPack::new,
                FlamescionStrengthenPack::handler
        );

        INSTANCE.registerMessage(
                nextID(),
                LeftClickPack.class,
                LeftClickPack::toBytes,
                LeftClickPack::new,
                LeftClickPack::handler
        );

        INSTANCE.registerMessage(
                nextID(),
                BuddhistChangePack.class,
                BuddhistChangePack::toBytes,
                BuddhistChangePack::new,
                BuddhistChangePack::handler
        );

        INSTANCE.registerMessage(
                nextID(),
                MountPack.class,
                MountPack::toBytes,
                MountPack::new,
                MountPack::handler
        );
    }

    public static void sendToNearby(World world, BlockPos pos, Object toSend) {
        if (world instanceof ServerWorld) {
            ServerWorld ws = (ServerWorld) world;

            ws.getChunkProvider().chunkManager.getTrackingPlayers(new ChunkPos(pos), false)
                    .filter(p -> p.getDistanceSq(pos.getX(), pos.getY(), pos.getZ()) < 64 * 64)
                        .forEach(p -> INSTANCE.send(PacketDistributor.PLAYER.with(() -> p), toSend));
        }
    }

    public static void sendToNearby(World world, Entity e, Object toSend) {
        sendToNearby(world, new BlockPos(e.getPosX(), e.getPosY(), e.getPosZ()), toSend);
    }

}
