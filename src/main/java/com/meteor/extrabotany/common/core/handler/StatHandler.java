package com.meteor.extrabotany.common.core.handler;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.block.ModBlocks;
import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.lib.LibAdvancements;
import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)
public class StatHandler {
	
	@SubscribeEvent
	public static void getAchievement(AdvancementEvent event) {
		if(!(event.getEntityLiving() instanceof EntityPlayer))
			return;
		if(event.getAdvancement() == ((EntityPlayerMP)event.getEntityPlayer()).getServerWorld().getAdvancementManager().getAdvancement(new ResourceLocation(LibMisc.MOD_ID, LibAdvancements.PREFIX+LibAdvancements.ALLSTATS)))
			event.getEntityPlayer().entityDropItem(new ItemStack(ModBlocks.trophy), 0).setNoPickupDelay();		
		if(event.getAdvancement() == ((EntityPlayerMP)event.getEntityPlayer()).getServerWorld().getAdvancementManager().getAdvancement(new ResourceLocation(LibMisc.MOD_ID, LibAdvancements.PREFIX+LibAdvancements.HERRSCHER_DEFEAT)))
			event.getEntityPlayer().entityDropItem(new ItemStack(ModItems.mask,1,8), 0).setNoPickupDelay();
		if(event.getAdvancement() == ((EntityPlayerMP)event.getEntityPlayer()).getServerWorld().getAdvancementManager().getAdvancement(new ResourceLocation(LibMisc.MOD_ID, LibAdvancements.PREFIX+LibAdvancements.ENDGAME_GOAL))){
			event.getEntityPlayer().entityDropItem(new ItemStack(ModItems.mask,1,9), 0).setNoPickupDelay();
			event.getEntityPlayer().entityDropItem(new ItemStack(ModItems.gaiarecord), 0).setNoPickupDelay();
			event.getEntityPlayer().entityDropItem(new ItemStack(ModItems.herrscherrecord), 0).setNoPickupDelay();
		}
		if(event.getAdvancement() == ((EntityPlayerMP)event.getEntityPlayer()).getServerWorld().getAdvancementManager().getAdvancement(new ResourceLocation(LibMisc.MOD_ID, LibAdvancements.PREFIX+LibAdvancements.JINGWEIFEATHER)))
			event.getEntityPlayer().entityDropItem(new ItemStack(ModItems.jingweifeather), 0).setNoPickupDelay();
		if(event.getAdvancement() == ((EntityPlayerMP)event.getEntityPlayer()).getServerWorld().getAdvancementManager().getAdvancement(new ResourceLocation(LibMisc.MOD_ID, LibAdvancements.PREFIX+LibAdvancements.MANADRIVERRING)))
			event.getEntityPlayer().entityDropItem(new ItemStack(ModItems.manadriverring), 0).setNoPickupDelay();
		if(event.getAdvancement() == ((EntityPlayerMP)event.getEntityPlayer()).getServerWorld().getAdvancementManager().getAdvancement(new ResourceLocation(LibMisc.MOD_ID, LibAdvancements.PREFIX+LibAdvancements.MAGICFINGERGET)))
			event.getEntityPlayer().entityDropItem(new ItemStack(ModItems.magicfinger), 0).setNoPickupDelay();
	}
	
	@SubscribeEvent
	public static void checkAdvancements(AdvancementEvent event) {
		if(!(event.getEntityLiving() instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) event.getEntityLiving();
		if(statsAmount(player) >= 18)
			ExtraBotanyAPI.unlockAdvancement(player, LibAdvancements.JINGWEIFEATHER);
		if(statsAmount(player) >= 12)
			ExtraBotanyAPI.unlockAdvancement(player, LibAdvancements.MAGICFINGERGET);
		if(statsAmount(player) >= 6)
			ExtraBotanyAPI.unlockAdvancement(player, LibAdvancements.MANADRIVERRING);
		if(hasAllStats(player))
			ExtraBotanyAPI.unlockAdvancement(player, LibAdvancements.ALLSTATS);
		
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
		LibAdvancements.MASTERMANARING_CRAFT,
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
		LibAdvancements.BUDDHISTRELICS,
		LibAdvancements.LUCKYDRAW,
		LibAdvancements.SPEARSUBSPACE,
		LibAdvancements.ARMORSET_GS,
		LibAdvancements.HERRSCHER_DEFEAT,
		LibAdvancements.ENDGAME_GOAL,
		LibAdvancements.JINGWEIFEATHER,
		LibAdvancements.ONEPUCHMAN,
		LibAdvancements.BOTTLESET,
		LibAdvancements.RINGSET,
		LibAdvancements.COREGOD,
		LibAdvancements.MANADRIVERRING,
		LibAdvancements.MAGICFINGERGET
	};
	
	public static int statsAmount(EntityPlayer player){
		int a = 0;
		for(int i = 0; i < stats.length; i++)
			if(hasStat(player, stats[i]))
				a++;
		return a;
	}
	
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
    
    @SideOnly(Side.CLIENT)
    public static Advancement getSideAdvancement(String name) {
    	return Minecraft.getMinecraft().player.connection.getAdvancementManager().getAdvancementList().getAdvancement(new ResourceLocation(LibMisc.MOD_ID, LibAdvancements.PREFIX+name));
    }
    
    @SideOnly(Side.CLIENT)
    public static boolean hasAdvancement(String name) {
    	Advancement adv = getSideAdvancement(name);
		AdvancementProgress progress = Minecraft.getMinecraft().player.connection.getAdvancementManager().advancementToProgress.get(adv);
		return progress != null && progress.isDone();
    }

}
