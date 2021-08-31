package com.meteor.extrabotany.common.network.flamescion;

import com.meteor.extrabotany.common.entities.mountable.EntityMotor;
import com.meteor.extrabotany.common.handler.FlamescionHandler;
import com.meteor.extrabotany.common.potions.ModPotions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class FlamescionShiftPack {

    public FlamescionShiftPack(PacketBuffer buffer) {

    }

    public FlamescionShiftPack() {

    }

    public void toBytes(PacketBuffer buf) {

    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
                ServerPlayerEntity player = ctx.get().getSender();
                Vector3d lookvec = player.getLookVec().scale(4D);
                Vector3d playervec = player.getPositionVec();
                Vector3d newvec = playervec.add(lookvec);
                player.setPositionAndUpdate(newvec.x, newvec.y, newvec.z);
                boolean flag = false;
                for (LivingEntity living : EntityMotor.getEntitiesAround(new BlockPos(newvec), 8F, player.world)) {
                    if(living == player)
                        continue;
                    boolean hit = living.getBoundingBox().grow(4).rayTrace(playervec.subtract(lookvec), newvec.add(lookvec))
                            .isPresent();
                    if (hit) {
                        living.addPotionEffect(new EffectInstance(ModPotions.timelock, 40));
                        living.hurtResistantTime=0;
                        living.attackEntityFrom(FlamescionHandler.flameSource(), 6);
                        flag = true;
                    }
                }
                if(flag) {
                    player.addPotionEffect(new EffectInstance(ModPotions.incandescence, 80));
                    player.addPotionEffect(new EffectInstance(ModPotions.flamescion, 200));
                }
                player.getCooldownTracker().setCooldown(FlamescionHandler.getFlamescionWeapon(), 20);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
