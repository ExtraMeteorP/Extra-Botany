package com.meteor.extrabotany.common.capability;

import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber()
public class CapabilityEventHandler {

    @SubscribeEvent
    public static void onAttachCapabilityEvent(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(LibMisc.MOD_ID, "thunderenergy"), new HerrscherEnergyProvider());
            event.addCapability(new ResourceLocation(LibMisc.MOD_ID, "flamescion"), new FlamescionProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) {
            LazyOptional<IHerrscherEnergy> oldSpeedCap = event.getOriginal().getCapability(CapabilityHandler.HERRSCHERENERGY_CAPABILITY);
            LazyOptional<IHerrscherEnergy> newSpeedCap = event.getPlayer().getCapability(CapabilityHandler.HERRSCHERENERGY_CAPABILITY);
            if (oldSpeedCap.isPresent() && newSpeedCap.isPresent()) {
                newSpeedCap.ifPresent((newCap) -> {
                    oldSpeedCap.ifPresent((oldCap) -> {
                        newCap.deserializeNBT(oldCap.serializeNBT());
                    });
                });
            }

            LazyOptional<IFlamescion> oldFlamescionCap = event.getOriginal().getCapability(CapabilityHandler.FLAMESCION_CAPABILITY);
            LazyOptional<IFlamescion> newFlamescionCap = event.getPlayer().getCapability(CapabilityHandler.FLAMESCION_CAPABILITY);
            if (oldFlamescionCap.isPresent() && newFlamescionCap.isPresent()) {
                newFlamescionCap.ifPresent((newCap) -> {
                    oldFlamescionCap.ifPresent((oldCap) -> {
                        newCap.deserializeNBT(oldCap.serializeNBT());
                    });
                });
            }
        }
    }

}
