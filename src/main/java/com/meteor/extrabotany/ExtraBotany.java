package com.meteor.extrabotany;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.meteor.extrabotany.client.core.handler.GuiHandler;
import com.meteor.extrabotany.common.CommonProxy;
import com.meteor.extrabotany.common.core.handler.Meme;
import com.meteor.extrabotany.common.core.network.ExtraBotanyNetwork;
import com.meteor.extrabotany.common.lib.LibMisc;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = LibMisc.MOD_ID, name = ExtraBotany.NAME, version = ExtraBotany.VERSION, dependencies = "required-after:botania@[r1.10-357,);"
		+ "after:baubles@[1.5.2,);" + "after:waila;" + "after:theoneprobe;" + "after:thaumcraft@[6.1.BETA25,);"
		+ "after:tconstruct;" + "after:mtlib;" + "after:crafttweaker;"
		+ "before:armoryexpansion", updateJSON = ExtraBotany.UPDATE_URL, guiFactory = "com.meteor.extrabotany.common.core.config.ConfigGui")
public class ExtraBotany {
	public static final String MODID = "extrabotany";
	public static final String NAME = "extrabotany";
	public static final String VERSION = "58";

	public static final Logger logger = LogManager.getLogger(LibMisc.MOD_ID);

	public static final List<IAction> LATE_REMOVALS = new LinkedList<>();
	public static final List<IAction> LATE_ADDITIONS = new LinkedList<>();
	public static Set<String> subtilesForCreativeMenu = new LinkedHashSet();

	public static final String UPDATE_URL = "https://raw.github.com/ExtraMeteorP/ExtraBotany/master/" + LibMisc.MOD_ID
			+ "_update.json";

	@SidedProxy(serverSide = "com.meteor.extrabotany.common.CommonProxy", clientSide = "com.meteor.extrabotany.client.ClientProxy")
	public static CommonProxy proxy;

	@Instance(LibMisc.MOD_ID)
	public static ExtraBotany instance;

	public static boolean thaumcraftLoaded = false;
	public static boolean naturalpledgeLoaded = false;
	public static boolean zh_cn = false;

	public static boolean isTableclothServer = false;

	static {
		try {
			Class.forName("com.gamerforea.eventhelper.EventHelperMod");
			isTableclothServer = true;
		} catch (ClassNotFoundException e) {
		}
	}

	public static void addSubTileToCreativeMenu(String key) {
		subtilesForCreativeMenu.add(key);
	}

	public ExtraBotany() {
		super();
	}

	@SideOnly(Side.CLIENT)
	public static KeyBinding keyForward;
	@SideOnly(Side.CLIENT)
	public static KeyBinding keyBackward;
	@SideOnly(Side.CLIENT)
	public static KeyBinding keyLeft;
	@SideOnly(Side.CLIENT)
	public static KeyBinding keyRight;
	@SideOnly(Side.CLIENT)
	public static KeyBinding keyUp;
	@SideOnly(Side.CLIENT)
	public static KeyBinding keyDown;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
		ExtraBotanyNetwork.instance.setup();
		thaumcraftLoaded = Loader.isModLoaded("thaumcraft");
		naturalpledgeLoaded = Loader.isModLoaded("botanicaladdons");
		if (event.getSide().isClient()) {
			Minecraft mc = Minecraft.getMinecraft();
			GameSettings gameSettings = mc.gameSettings;
			keyForward = Minecraft.getMinecraft().gameSettings.keyBindForward;
			keyBackward = Minecraft.getMinecraft().gameSettings.keyBindBack;
			keyLeft = Minecraft.getMinecraft().gameSettings.keyBindLeft;
			keyRight = Minecraft.getMinecraft().gameSettings.keyBindRight;
			keyUp = Minecraft.getMinecraft().gameSettings.keyBindJump;
			keyDown = Minecraft.getMinecraft().gameSettings.keyBindSneak;
			if (gameSettings.language.equals("zh_cn"))
				zh_cn = true;
		}
		logger.info("Welcome to the World of the supreme principle of Mana");
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {
		proxy.init(event);
		NetworkRegistry.INSTANCE.registerGuiHandler(ExtraBotany.instance, new GuiHandler());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		if (Loader.isModLoaded("mtlib") && Loader.isModLoaded("crafttweaker")) {
			try {
				LATE_REMOVALS.forEach(CraftTweakerAPI::apply);
				LATE_ADDITIONS.forEach(CraftTweakerAPI::apply);
			} catch (Exception e) {
				e.printStackTrace();
				CraftTweakerAPI.logError("Error while applying actions", e);
			}
		}
		Meme.init();
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		proxy.serverStarting(event);
	}

}
