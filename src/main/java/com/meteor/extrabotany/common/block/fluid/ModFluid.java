package com.meteor.extrabotany.common.block.fluid;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluid {
	
	public static Fluid fluidMana;
	
	public static void init(){
		fluidMana = new ManaFluid("mana");
    	FluidRegistry.registerFluid(fluidMana);
    	FluidRegistry.addBucketForFluid(fluidMana);
    	fluidMana = FluidRegistry.getFluid("mana");
	}

}
