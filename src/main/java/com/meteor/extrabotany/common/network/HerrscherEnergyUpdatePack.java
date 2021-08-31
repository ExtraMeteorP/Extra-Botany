package com.meteor.extrabotany.common.network;

import com.meteor.extrabotany.common.capability.CapabilityHandler;
import com.meteor.extrabotany.common.capability.IHerrscherEnergy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class HerrscherEnergyUpdatePack {

    private int energy;

    public HerrscherEnergyUpdatePack(PacketBuffer buffer) {
        energy = buffer.readInt();
    }

    public HerrscherEnergyUpdatePack(int energy) {
        this.energy = energy;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeInt(this.energy);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
                ClientPlayerEntity player = Minecraft.getInstance().player;
                LazyOptional<IHerrscherEnergy> cap = player.getCapability(CapabilityHandler.HERRSCHERENERGY_CAPABILITY);
                cap.ifPresent((c) -> {
                    c.setEnergy(this.energy);
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
