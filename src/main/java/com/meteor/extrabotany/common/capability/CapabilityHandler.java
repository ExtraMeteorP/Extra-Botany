package com.meteor.extrabotany.common.capability;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityHandler {

    @CapabilityInject(IHerrscherEnergy.class)
    public static Capability<IHerrscherEnergy> HERRSCHERENERGY_CAPABILITY;

    @CapabilityInject(IFlamescion.class)
    public static Capability<IFlamescion> FLAMESCION_CAPABILITY;

    public static void register(){
        CapabilityManager.INSTANCE.register(
                IHerrscherEnergy.class,
                new Capability.IStorage<IHerrscherEnergy>() {
                    @Nullable
                    @Override
                    public INBT writeNBT(Capability<IHerrscherEnergy> capability, IHerrscherEnergy instance, Direction side) {
                        return null;
                    }

                    @Override
                    public void readNBT(Capability<IHerrscherEnergy> capability, IHerrscherEnergy instance, Direction side, INBT nbt) {

                    }
                },
                () -> null
        );

        CapabilityManager.INSTANCE.register(
                IFlamescion.class,
                new Capability.IStorage<IFlamescion>() {
                    @Nullable
                    @Override
                    public INBT writeNBT(Capability<IFlamescion> capability, IFlamescion instance, Direction side) {
                        return null;
                    }

                    @Override
                    public void readNBT(Capability<IFlamescion> capability, IFlamescion instance, Direction side, INBT nbt) {

                    }
                },
                () -> null
        );
    }

}
