package com.meteor.extrabotany.common.network.flamescion;

import com.meteor.extrabotany.common.capability.CapabilityHandler;
import com.meteor.extrabotany.common.capability.IFlamescion;
import com.meteor.extrabotany.common.entities.EntityFlamescionUlt;
import com.meteor.extrabotany.common.handler.FlamescionHandler;
import com.meteor.extrabotany.common.potions.ModPotions;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class FlamescionQPack {

    public FlamescionQPack(PacketBuffer buffer) {

    }

    public FlamescionQPack() {

    }

    public void toBytes(PacketBuffer buf) {

    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
                ServerPlayerEntity player = ctx.get().getSender();
                LazyOptional<IFlamescion> cap = player.getCapability(CapabilityHandler.FLAMESCION_CAPABILITY);
                cap.ifPresent((c) -> {
                    c.setEnergy(FlamescionHandler.MAX_FLAMESCION_ENERGY);
                    c.setOverloaded(true);
                });
                EntityFlamescionUlt ult = new EntityFlamescionUlt(player.world, player);
                Vector3d lookVec = player.getLookVec().normalize().scale(5D);
                Vector3d spawnPoint = player.getPositionVec().add(lookVec.x, 0.25D, lookVec.z);
                ult.setPosition(spawnPoint.x, spawnPoint.y, spawnPoint.z);
                player.world.addEntity(ult);
                player.addPotionEffect(new EffectInstance(ModPotions.timelock, 40));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
