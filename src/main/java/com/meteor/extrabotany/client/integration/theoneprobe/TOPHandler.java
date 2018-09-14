package com.meteor.extrabotany.client.integration.theoneprobe;

import java.util.function.Function;

import javax.annotation.Nullable;

import mcjty.theoneprobe.api.ITheOneProbe;

public class TOPHandler implements Function<ITheOneProbe, Void>{
	
    @Nullable
    public Void apply(ITheOneProbe theOneProbe){
    	theOneProbe.registerProvider(new TOPManaLiquefaction());
    	theOneProbe.registerProvider(new TOPManaGenerator());
    	theOneProbe.registerProvider(new TOPManaBuffer());
    	theOneProbe.registerProvider(new TOPElfJar());
    	
    	theOneProbe.registerProvider(new TOPPool());
    	theOneProbe.registerProvider(new TOPSpreader());
    	theOneProbe.registerProvider(new TOPFlower());
        return null;
    }
    
}
