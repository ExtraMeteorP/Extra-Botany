package com.meteor.extrabotany.common.block.fluid;

import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class ManaFluid extends Fluid{

    public ManaFluid(String fluidName) {
        super(fluidName, new ResourceLocation(LibMisc.MOD_ID, "blocks/fluid/" + fluidName + "_still"), new ResourceLocation(LibMisc.MOD_ID, "blocks/fluid/" + fluidName + "_flow"));
    }
    
    @Override
    public int getTemperature(FluidStack stack){
        return 100;
    }

}
