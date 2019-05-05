package com.meteor.extrabotany.common.integration;

import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.integration.constructsarmory.ConstructsArmoryCompat;
import com.meteor.extrabotany.common.integration.tinkerconstruct.TConstructCompat;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class Integration {
	
	public Integration(){}
	
	public static void preInit(){
		if (Loader.isModLoaded("tconstruct"))
			TConstructCompat.preInit();
	}
	
	public static void init(){
		if (Loader.isModLoaded("waila")){
			if(ConfigHandler.ENABLE_WAILAPOOL)
				FMLInterModComms.sendMessage("waila", "register", "com.meteor.extrabotany.client.integration.waila.WailaPool.register");
			if(ConfigHandler.ENABLE_WAILAMANAGEN)
				FMLInterModComms.sendMessage("waila", "register", "com.meteor.extrabotany.client.integration.waila.WailaManaGenerator.register");
			if(ConfigHandler.ENABLE_WAILAMANALIQUE)
				FMLInterModComms.sendMessage("waila", "register", "com.meteor.extrabotany.client.integration.waila.WailaManaLiquefaction.register");
			if(ConfigHandler.ENABLE_WAILAMANABUFFER)
				FMLInterModComms.sendMessage("waila", "register", "com.meteor.extrabotany.client.integration.waila.WailaManaBuffer.register");
		}
		if(ConfigHandler.ENABLE_TOP)
			FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "com.meteor.extrabotany.client.integration.theoneprobe.TOPHandler");
		if(Loader.isModLoaded("conarm") && ConfigHandler.ENABLE_CASUPPORT)
			ConstructsArmoryCompat.init();
	}

}
