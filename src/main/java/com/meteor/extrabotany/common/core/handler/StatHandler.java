package com.meteor.extrabotany.common.core.handler;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.block.ModBlocks;
import com.meteor.extrabotany.common.lib.LibAdvancements;
import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)
public class StatHandler {
	
	@SubscribeEvent
	public static void getAchievement(AdvancementEvent event) {
		if(!(event.getEntityLiving() instanceof EntityPlayer))
			return;
		if(hasAllStats(event.getEntityPlayer())){
			event.getEntityPlayer().entityDropItem(new ItemStack(ModBlocks.trophy), 0);
			ExtraBotanyAPI.unlockAdvancement(event.getEntityPlayer(), LibAdvancements.ALLSTATS);
		}
	}
	
	public static String[] stats = new String[]{
		LibAdvancements.ANNOYINGDOG_SUMMON,
		LibAdvancements.BLOODYENCHANTRESS_USE,
		LibAdvancements.CAMERA_CRAFT,
		LibAdvancements.FAILNAUGHT_CRAFT,
		LibAdvancements.FRAGMENT_FORGE,
		LibAdvancements.GAIA_DEFEAT,
		LibAdvancements.KINGGARDEN_USE,
		LibAdvancements.LANDMINE_ACTIVE,
		LibAdvancements.MANABUFFER_CRAFT,
		LibAdvancements.MANALINKIUM_USE,
		LibAdvancements.MANAREADER_CRAFT,
		LibAdvancements.MASTERMANARING_FILL,
		LibAdvancements.MUSIC_ALL,
		LibAdvancements.NATUREORB_CRAFT,
		LibAdvancements.NEWKNOWLEDGE_UNLOCK,
		LibAdvancements.NIGHTMAREFUEL_EATING,
		LibAdvancements.PEDESTAL_CRAFT,
		LibAdvancements.POSBINDER_CRAFT,
		LibAdvancements.RELICSHIELD_CRAFT,
		LibAdvancements.STARDUSTLOTUS_TELEPORT,
		LibAdvancements.TINKLE_USE,
		LibAdvancements.ULTIMATEHAMMER_CRAFT,
		LibAdvancements.ULTIMATEHAMMER_UPGRADE,
		LibAdvancements.ARMORSET_COMBAT,
		LibAdvancements.ARMORSET_COS,
		LibAdvancements.ARMORSET_SW,
		LibAdvancements.INFINITEWINE,
		LibAdvancements.EXCALIBER,
		LibAdvancements.BUDDHISTRELICS
	};
	
	public static boolean hasAllStats(EntityPlayer player){
		for(int i = 0; i < stats.length; i++)
			if(!hasStat(player, stats[i]))
				return false;
		return true;
	}
	
	public static boolean hasStat(EntityPlayer player, String name){
		if(player instanceof EntityPlayerMP){
			AdvancementManager manager = ((EntityPlayerMP)player).getServerWorld().getAdvancementManager();
			Advancement advancement = manager.getAdvancement(new ResourceLocation(LibMisc.MOD_ID, LibAdvancements.PREFIX+name));
			if(advancement != null)
				return ((EntityPlayerMP)player).getAdvancements().getProgress(advancement).isDone();
		}
		return false;
	}

}
