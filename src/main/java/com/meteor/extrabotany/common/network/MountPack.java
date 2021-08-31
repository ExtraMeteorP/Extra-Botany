package com.meteor.extrabotany.common.network;

import com.meteor.extrabotany.api.items.IMountableAccessory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MountPack {

    private ItemStack stack;

    public MountPack(PacketBuffer buffer) {
        stack = buffer.readItemStack();
    }

    public MountPack(ItemStack stack) {
        this.stack = stack;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeItemStack(stack);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
                ServerPlayerEntity player = ctx.get().getSender();
                if(stack.getItem() instanceof IMountableAccessory){
                    IMountableAccessory mountable = (IMountableAccessory) stack.getItem();
                    Entity mount = mountable.getMountableEntity(player.world);
                    mount.setPosition(player.getPosX(), player.getPosY()+0.5F, player.getPosZ());
                    mount.rotationYaw = player.rotationYaw;
                    if(player.world.addEntity(mount)){
                        player.startRiding(mount);
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
