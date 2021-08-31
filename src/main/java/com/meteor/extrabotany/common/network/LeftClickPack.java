package com.meteor.extrabotany.common.network;

import com.meteor.extrabotany.api.items.IItemWithLeftClick;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class LeftClickPack {

    private ItemStack stack;

    public LeftClickPack(PacketBuffer buffer) {
        stack = buffer.readItemStack();
    }

    public LeftClickPack(ItemStack stack) {
        this.stack = stack;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeItemStack(stack);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
                ServerPlayerEntity player = ctx.get().getSender();
                if(stack.getItem() instanceof IItemWithLeftClick){
                    IItemWithLeftClick item = (IItemWithLeftClick) stack.getItem();
                    ctx.get().enqueueWork(() -> item.onLeftClick(player, null));
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
