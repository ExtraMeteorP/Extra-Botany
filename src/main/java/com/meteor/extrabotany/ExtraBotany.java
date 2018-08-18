package com.meteor.extrabotany;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.meteor.extrabotany.common.CommonProxy;
import com.meteor.extrabotany.common.core.handler.ConfigHandler;
import com.meteor.extrabotany.common.core.handler.ToolTipHandler;
import com.meteor.extrabotany.common.crafting.ModRecipe;
import com.meteor.extrabotany.common.entity.ModEntities;
import com.meteor.extrabotany.common.item.equipment.shield.ItemShieldCopy;
import com.meteor.extrabotany.common.lexicon.LexiconData;
import com.meteor.extrabotany.common.lib.LibMisc;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = LibMisc.MOD_ID, name = ExtraBotany.NAME, version = ExtraBotany.VERSION, dependencies = "required-after:botania;after:Baubles", updateJSON = ExtraBotany.UPDATE_URL)
public class ExtraBotany{
    public static final String MODID = "extrabotany";
    public static final String NAME = "extrabotany";
    public static final String VERSION = "34";

    public static final Logger logger = LogManager.getLogger(LibMisc.MOD_ID);
    
    public static final List<IAction> LATE_REMOVALS = new LinkedList<>();
    public static final List<IAction> LATE_ADDITIONS = new LinkedList<>();
    
    public static final ExtraBotanyCreativeTab tabExtraBotany = new ExtraBotanyCreativeTab(); 
	public static Set<String> subtilesForCreativeMenu = new LinkedHashSet();
	
	public static final String UPDATE_URL = "https://raw.github.com/ExtraMeteorP/ExtraBotany/master/" + LibMisc.MOD_ID + "_update.json";
	
	@SidedProxy(serverSide = "com.meteor.extrabotany.common.CommonProxy", clientSide = "com.meteor.extrabotany.client.ClientProxy")
	public static CommonProxy proxy;
	
	@Instance(LibMisc.MOD_ID)
	public static ExtraBotany instance;
	
	public static void addSubTileToCreativeMenu(String key) {
		subtilesForCreativeMenu.add(key);
	}
	
	public ExtraBotany() {
		super();
	}

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
    	proxy.preInit(event);
    	ConfigHandler.INSTANCE.loadConfig(event);
    	logger.info("Welcome to the World of the supreme principle of Mana");
    }

    @EventHandler
	public void Init(FMLInitializationEvent event){
    	MinecraftForge.EVENT_BUS.register(ConfigHandler.INSTANCE);
    	if(event.getSide().isClient())
    		MinecraftForge.EVENT_BUS.register(new ToolTipHandler());
    	MinecraftForge.EVENT_BUS.register(ItemShieldCopy.EventHandler.INSTANCE);
    	ModEntities.init();
		ModRecipe.init();
		LexiconData.init();
		proxy.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
    	logger.info("o****************ooooooooooooooo$$$$$$$$$&&&$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$&&&&&&&&&&&&&&ooooooooo");
    	logger.info("****$$**************ooooooooo!$$$$$$$$$$$$&$$$$$$$$$$$&$$$$$$$$$$$$$$$$$$$$$$$$&&&&&&&&&&&*ooooooooo");
    	logger.info("*$****$$***************ooooo$$$$$$$$$$$$$&&$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$&&&&&&&&&oooooooooo");
    	logger.info("o*$*oo*******************o$$$$$$$$$$$$$$$&$$$$$$$$$$$$$&$$$$$$$$$$$$$$$$$$$$$$$$$$&&&&&&&&oooooooooo");
    	logger.info("oo$*$oo*****************$$$$$$$$$$$$$$$$$&$$$$$$$$$$$$$$&$$$$$$$$$$$$$$$$$$$$$$$$$$&&&&&&&!ooooooooo");
    	logger.info("ooo**$****o*o**********$$$$$$$!$$$$$$$$$$&$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$&&&&&&oooooooooo");
    	logger.info("!ooo**$oooo*ooo*******$$$$$$$$$$$o!$$$$$$$$$$$$$$$$$o$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$&&&&$oooooooooo");
    	logger.info("!oooo****ooooo**oo***$$$$$$$$$$$$$$$$$$o!$$$$$$$$$$$o;*$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$&&&$o!oooooooo");
    	logger.info("!ooooo****oooooooooo$$$$$$$$$$$$$$$$$$!;;$$$$$$$$$$$;;;!$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$&&&o!!!oooo!oo");
    	logger.info("!!oooooo*!!o**oooo$$$$$$$$o$$$$$$$$$o;;;;$$$$$$$$$$$;;;!**$$$$$$$$$$$$$$$$$$$$$$$$$$$$$&&!o!!!!!!!!!");
    	logger.info("!!ooooooo****oo$$$$$$$$$$$$$!o$$$$o;;;;*o$$$$$$$$$$$oo;;;;;$$$$$$$$$$$$$$$$$$$$$$$$$$$$$&!!!!!!!!!!!");
    	logger.info("!!ooooo*$$$$$$$$*$$$$$$$$$$$$$$!$;;;;;;;;!$$$$$$$$$$;;;;;;;;!$$$$$$$$$$$$$$$$$$$$$$$$$$$&!!!!!!!!!!!");
    	logger.info("!!ooo!ooo*;;;!!o$$$$$$$$$$$$$$$$;;;;;;;;;;$$$$$$$$$$;;;o!;!o!;!$$$$$$$$$$$$$$$$$$$$$$$$$$*!!!!!!!!!!");
    	logger.info(";!!!!oooo*;..;;!$$$$$$$$$$$$$$$;;;;$&$;;;;;$$;o$$$$$;;;;888888$;o$$$$$$$$$$$$$$$$$$$$$$$$$$!!!!oo!!!");
    	logger.info("....;!!!oo;...o$$$$$$$$$$$$$$$;;*88$o$8**;;!$;;;$$$$;;!$!&8&!!$8$;$$$$$$$$&$$$$$$$$$$$$$$!!*$!!o*!!!");
    	logger.info("........;o;..;*$$$$$$$$$$$$$$;;&8!!*8$.&!;;;o!;;;!$$o;;;8&8..$!!8&;$$$$$$$$$$$$$$$$$$$$$$!!!!!!$*!!!");
    	logger.info(".........o;...o$$$$$$$$$$$$$$;$8!;&8o8.**;;;;;;;;;;*$;;8888888$;!8$o$$$$$$$$$$$$$$$$$$$$$$!!!!ooo!!!");
    	logger.info(".........o!;;.!$$$$$$$$$$$$$o;&8.;88&888&;;;;;;;;;;;;!o8&88888&.;8$;$$$$$$$$$$$$$$$$$$$$$$o!!!!o*!!!");
    	logger.info(".........o!;;.!$$$$$$$$$$$&$;;;&.!8&$$&&$;;;;;;;;;;;;;!o.**$&8;.*!;;$$$$$$$$$$$$$$$$$$$$$$;!ooo*$o*o");
    	logger.info(".........!;...!$$$$$$$$$$$&$;;;;..&;**$$;;;;;;;;;;;;;;;$&$$&8..;;;;$$$$$$$$$$$$$$$$$$$$$$$$;!!!o*!!!");
    	logger.info(".........!;...$$$$$$$$$$$$&&!;;;;;;!*!;;;;;;;;;;;;;;;;;;;;;;;;;;;;;$$$$$$$$$$$$*&$&$$$$$$$$;!!!!*!!!");
    	logger.info("*o!;.....!!...$$$$$$$$$$$$&&$;;;;;!;!;;;;;;;;!!;;;;;;;;;;;;;;;;;;;$$$$$$$$$$$$$;$&$$$$$$$$$$!!;!*!;!");
    	logger.info("$*$$$$*o;!;...$$$$$$$$$$$$$&&;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;o$$$$$$$$$$$$$;&$$$$$$$$$$$!;;!*;;;");
    	logger.info("$oooooooo*;.;$$$$$$$$$$$$$$&&;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;$$$$$$$$$$$$*;o$$$$$$$$$$$$$;;!*;;;");
    	logger.info("*oooooo*$$$$$$$$$$$$$$$$$$$&$;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;$$$$$$$$$$$$$;$$$$$$$$$$$$$$o$!;*;;;");
    	logger.info("*;;;!$$$$$$$$$&$$$$$$$$$$$$$$;;;;;;;;;;;;;;;;o.;;;;;;;;;;;;;;;;$$$$$$$$$$$$$$$$$$$$$&$$$$$$$!;;$o;;;");
    	logger.info("*....$&$$$$$$$$$$$$$$$$$$$$$$*;;;;;;;;o;;;;;;$$*;;;;;;;;;;;;;;$$$$$$&$$$$$$$$$$$$$&&$$$$$$$$;;;;*!;;");
    	logger.info("*....$$$$$$$$$$$$$$$$$$$$$$$$$$;;;;;;;;;;;;;;$***;;;;;;;;;;;;$$$$$$!!!!!!!!!!!!!!!!*$$$$*$$;;;;;*!..");
    	logger.info("*....o$$$$$$$$$$&$$$$$$*!!!!!*$$$;;;;;;;;;;;;****;;;;;;;;;;$o!!!!!!!!!!!!!!!!!!!!!!!!oooo$o!;..;*!..");
    	logger.info("*.....$$$$$$$$$$$$$$$$!!!!!!!!!!!*$*;;;;;;;;;;;;;;;;;;;*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!o!$oooo!;;*;..");
    	logger.info("o.....$&$$$$$$$$$$$$o!!!!!!!!!!!!!!!*!!!!!!o*!;;;;;*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*o!;;;..;o!..");
    	logger.info("o.....!$$$$$$$$$$$$$!!!!!!!!!!!!!!!!!!!!!!!!!!!!*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*!*o;;*****o;.");
    	logger.info("o...;!!$&$$$$$$$$$$!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!oooo*ooo;;**o;o*o;");
    	logger.info("o...;o*$$$$$$$!!!!!!o!!!!!!!!!!!!!!!!!!!!!o!!!!!!!!!!!!!!!!!!!!!!!!!!o*o!!!!!!!!!!!!!!!!!!!!o*;;***o");
    	logger.info("o..!***$$&$$$!!!!!!!!!o!!!!!!!!!!!!!!!!!!!!!*!!!!!!!!!!!!!!!!*o!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!**oo*");
    	logger.info("o...;.!$*$$$$!!!!!!!!!!!!o!!!!!!!!!!!!!!!!!!!!**$o!!!!;!*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!o!o");
    	logger.info("o.....;*$$&$$!!!!!!!!!!!!!!!!o!!!!!!!!!!!!!!!!!ooooooo!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!o!!");
    	logger.info("*;;.;o$$$$$&$!!!!!!!!!!!!!!!!!!!$!!!!!!!!!!!!!!!!$oooo!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!$o!!");
    	logger.info("$**$*!*$$$$$$!!!!!!!!!!!!!!!!!!!!o*$!!!!!!!!!!!!!!!*ooooo!!!!!!!!!!!!!!!!!!!!!!!!!!o**$*!!!!!!!$$***");
	}
	
    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
    	if(Loader.isModLoaded("mtlib") && Loader.isModLoaded("crafttweaker")){
	        try {
	            LATE_REMOVALS.forEach(CraftTweakerAPI::apply);
	            LATE_ADDITIONS.forEach(CraftTweakerAPI::apply);
	        } catch(Exception e) {
	            e.printStackTrace();
	            CraftTweakerAPI.logError("Error while applying actions", e);
	        }
    	}
    }
    
}
