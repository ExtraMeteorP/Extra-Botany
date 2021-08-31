package com.meteor.extrabotany.common.network;

import com.meteor.extrabotany.common.items.relic.ItemBuddhistrelics;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class BuddhistChangePack {

    public BuddhistChangePack(PacketBuffer buffer) {

    }

    public BuddhistChangePack() {

    }

    public void toBytes(PacketBuffer buf) {

    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
                ServerPlayerEntity player = ctx.get().getSender();
                if(!ItemBuddhistrelics.relicShift(player.getHeldItemMainhand()).isEmpty())
                    ctx.get().enqueueWork(() -> player.setHeldItem(Hand.MAIN_HAND, ItemBuddhistrelics.relicShift(player.getHeldItemMainhand())));
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
