package com.meteor.extrabotany.common.core.version;

import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = LibMisc.MOD_ID)
public class VersionChecker {
	private VersionChecker() {}

	public static volatile boolean doneChecking = false;
	public static volatile String onlineVersion = "";
	private static boolean triedToWarnPlayer = false;

	public static volatile boolean startedDownload = false;
	public static volatile boolean downloadedFile = false;

	public static void init() {
		new ThreadVersionChecker();
	}

	@SubscribeEvent
	public static void onTick(ClientTickEvent event) {
		if(event.phase == Phase.END && Minecraft.getMinecraft().player != null && !triedToWarnPlayer && doneChecking) {
			if(!onlineVersion.isEmpty()) {
				EntityPlayer player = Minecraft.getMinecraft().player;
				int onlineBuild = Integer.parseInt(onlineVersion.split("-")[1]);
				int clientBuild = LibMisc.BUILD;
				if(onlineBuild > clientBuild) {
					player.sendMessage(new TextComponentTranslation("extrabotany.versioning.flavour").setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)));
					player.sendMessage(new TextComponentTranslation("extrabotany.versioning.outdated", clientBuild, onlineBuild));
				}
			}

			triedToWarnPlayer = true;
		}
	}
}
