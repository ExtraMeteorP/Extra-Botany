package com.meteor.extrabotany.common.network.flamescion;

import com.meteor.extrabotany.common.capability.CapabilityHandler;
import com.meteor.extrabotany.common.capability.IFlamescion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class FlamescionStateUpdatePack {

    private int energy;
    private boolean overloaded;

    public FlamescionStateUpdatePack(PacketBuffer buffer) {
        energy = buffer.readInt();
        overloaded = buffer.readBoolean();
    }

    public FlamescionStateUpdatePack(int energy, boolean overloaded) {
        this.energy = energy;
        this.overloaded = overloaded;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeInt(this.energy);
        buf.writeBoolean(this.overloaded);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
                ClientPlayerEntity player = Minecraft.getInstance().player;
                LazyOptional<IFlamescion> cap = player.getCapability(CapabilityHandler.FLAMESCION_CAPABILITY);
                cap.ifPresent((c) -> {
                    c.setEnergy(this.energy);
                    c.setOverloaded(this.overloaded);
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
