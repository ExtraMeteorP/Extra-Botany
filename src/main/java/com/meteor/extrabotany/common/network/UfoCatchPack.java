package com.meteor.extrabotany.common.network;

import com.meteor.extrabotany.common.entities.mountable.EntityUfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class UfoCatchPack {

    private int id;

    public UfoCatchPack(PacketBuffer buffer) {
        id = buffer.readInt();
    }

    public UfoCatchPack(int id) {
        this.id = id;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeInt(id);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
                ServerPlayerEntity player = ctx.get().getSender();
                Entity riding = player.getRidingEntity();
                if (riding != null && riding instanceof EntityUfo) {
                    EntityUfo motor = (EntityUfo) riding;
                    motor.setCatchedID(id);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
