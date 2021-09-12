package com.meteor.extrabotany.common.network;

import com.meteor.extrabotany.common.items.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PotatoChipsPack {

    public PotatoChipsPack(PacketBuffer buffer) {

    }

    public PotatoChipsPack() {

    }

    public void toBytes(PacketBuffer buf) {

    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
                ClientPlayerEntity player = Minecraft.getInstance().player;
                Minecraft.getInstance().gameRenderer.displayItemActivation(new ItemStack(ModItems.potatochips));
            }
        });
        ctx.get().setPacketHandled(true);
    }
    
}
