package com.meteor.extrabotany.common.network;

import com.meteor.extrabotany.common.entities.mountable.EntityMotor;
import com.meteor.extrabotany.common.entities.mountable.EntityMountable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MountableUpdatePack {

    private boolean ctrlInputDown;
    private boolean upInputDown;

    public MountableUpdatePack(PacketBuffer buffer) {
        ctrlInputDown = buffer.readBoolean();
        upInputDown = buffer.readBoolean();
    }

    public MountableUpdatePack(boolean ctrlInputDown, boolean upInputDown) {
        this.ctrlInputDown = ctrlInputDown;
        this.upInputDown = upInputDown;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBoolean(this.ctrlInputDown);
        buf.writeBoolean(this.upInputDown);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
                ServerPlayerEntity player = ctx.get().getSender();
                Entity riding = player.getRidingEntity();
                if (riding != null && riding instanceof EntityMountable) {
                    EntityMountable motor = (EntityMountable) riding;
                    motor.updateInput(ctrlInputDown, upInputDown);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
