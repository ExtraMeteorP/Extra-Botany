package com.meteor.extrabotany.common.network;

import com.meteor.extrabotany.common.entities.EntitySlash;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class HerrscherSkillPack {

    private BlockPos pos;

    public HerrscherSkillPack(PacketBuffer buffer) {
        this.pos = BlockPos.fromLong(buffer.readLong());
    }

    public HerrscherSkillPack(BlockPos pos) {
        this.pos = pos;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeLong(this.pos.toLong());
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
                ServerPlayerEntity player = ctx.get().getSender();
                EntitySlash slash = new EntitySlash(player.world, player);
                slash.setPosition(this.pos.getX(), this.pos.getY(), this.pos.getZ());
                player.world.addEntity(slash);
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
