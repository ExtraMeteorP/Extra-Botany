package com.meteor.extrabotany.common.core.handler;

import java.util.Arrays;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.block.ModBlocks;
import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.lib.LibAdvancements;
import com.meteor.extrabotany.common.lib.Reference;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class StatHandler {

	@SubscribeEvent
	public static void getAchievement(AdvancementEvent event) { //achievement or advancement?
		EntityPlayerMP player = ((EntityPlayerMP) event.getEntityPlayer());
		Advancement advancement = event.getAdvancement();

		if (!player.getAdvancements().getProgress(advancement).isDone()) {
			return;
		}

		ResourceLocation id = advancement.getId();

		if (!Reference.MOD_ID.equals(id.getResourceDomain())) {
			return;
		}

		if (LibAdvancements.MANA_DRIVER_RING_ID.equals(id)) {
			player.entityDropItem(new ItemStack(ModItems.manadriverring), 0).setNoPickupDelay();
		}
		else if (LibAdvancements.MAGIC_FINGER_GET_ID.equals(id)) {
			player.entityDropItem(new ItemStack(ModItems.magicfinger), 0).setNoPickupDelay();
		}
		else if (LibAdvancements.JINGWEI_FEATHER_ID.equals(id)) {
			player.entityDropItem(new ItemStack(ModItems.jingweifeather), 0).setNoPickupDelay();
		}
		else if (LibAdvancements.HERRSCHER_DEFEAT_ID.equals(id)) {
			player.entityDropItem(new ItemStack(ModItems.mask, 1, 8), 0).setNoPickupDelay();
		}
		else if (LibAdvancements.ENDGAME_GOAL_ID.equals(id)) {
			player.entityDropItem(new ItemStack(ModItems.mask, 1, 9), 0).setNoPickupDelay();
			player.entityDropItem(new ItemStack(ModItems.gaiarecord), 0).setNoPickupDelay();
			player.entityDropItem(new ItemStack(ModItems.herrscherrecord), 0).setNoPickupDelay();
		}
		else if (LibAdvancements.ALL_STATS_ID.equals(id)) {
			player.entityDropItem(new ItemStack(ModBlocks.trophy), 0).setNoPickupDelay();
		}
	}
	
	@SubscribeEvent
	public static void checkAdvancements(AdvancementEvent event) {
		EntityPlayerMP player = (EntityPlayerMP) event.getEntityPlayer();
		Advancement advancement = event.getAdvancement();

		if (!player.getAdvancements().getProgress(advancement).isDone()) {
			return; //sponge fires event when a CRITERION is granted, leading to an infinite recursion.
		}

		ResourceLocation id = advancement.getId();

		if (!Reference.MOD_ID.equals(id.getResourceDomain())) {
			return;
		}

		int amount = statsAmount(player);
		if (amount == STAT_IDS.length) {
			if (!LibAdvancements.ALL_STATS_ID.equals(id)) {
				ExtraBotanyAPI.unlockAdvancement(player, LibAdvancements.ALL_STATS_ID);
			}
		}
		else if (amount >= 18) {
			if (!LibAdvancements.JINGWEI_FEATHER_ID.equals(id)) {
				ExtraBotanyAPI.unlockAdvancement(player, LibAdvancements.JINGWEI_FEATHER_ID);
			}
		}
		else if (amount >= 12) {
			if (!LibAdvancements.MAGIC_FINGER_GET_ID.equals(id)) {
				ExtraBotanyAPI.unlockAdvancement(player, LibAdvancements.MAGIC_FINGER_GET_ID);
			}
		}
		else if (amount >= 6) {
			if (!LibAdvancements.MANA_DRIVER_RING_ID.equals(id)) {
				ExtraBotanyAPI.unlockAdvancement(player, LibAdvancements.MANA_DRIVER_RING_ID);
			}
		}
	}

	public static final ResourceLocation[] STAT_IDS = new ResourceLocation[] {
		LibAdvancements.ANNOYING_DOG_SUMMON_ID,
		LibAdvancements.BLOODY_ENCHANTRESS_USE_ID,
		LibAdvancements.CAMERA_CRAFT_ID,
		LibAdvancements.FAIL_NAUGHT_CRAFT_ID,
		LibAdvancements.FRAGMENT_FORGE_ID,
		LibAdvancements.GAIA_DEFEAT_ID,
		LibAdvancements.KING_GARDEN_USE_ID,
		LibAdvancements.LANDMINE_ACTIVE_ID,
		LibAdvancements.MANA_BUFFER_CRAFT_ID,
		LibAdvancements.MANA_LINKIUM_USE_ID,
		LibAdvancements.MANA_READER_CRAFT_ID,
		LibAdvancements.MASTER_MANA_RING_FILL_ID,
		LibAdvancements.MASTER_MANA_RING_CRAFT_ID,
		LibAdvancements.MUSIC_ALL_ID,
		LibAdvancements.NATURE_ORB_CRAFT_ID,
		LibAdvancements.NEW_KNOWLEDGE_UNLOCK_ID,
		LibAdvancements.NIGHTMARE_FUEL_EATING_ID,
		LibAdvancements.PEDESTAL_CRAFT_ID,
		LibAdvancements.POS_BINDER_CRAFT_ID,
		LibAdvancements.RELICSHIELD_CRAFT_ID,
		LibAdvancements.STARDUST_LOTUS_TELEPORT_ID,
		LibAdvancements.TINKLE_USE_ID,
		LibAdvancements.ULTIMATE_HAMMER_CRAFT_ID,
		LibAdvancements.ULTIMATE_HAMMER_UPGRADE_ID,
		LibAdvancements.ARMORSET_COMBAT_ID,
		LibAdvancements.ARMORSET_COS_ID,
		LibAdvancements.ARMORSET_SW_ID,
		LibAdvancements.ARMORSET_GS_ID,
		LibAdvancements.INFINITE_WINE_ID,
		LibAdvancements.EXCALIBUR_ID,
		LibAdvancements.BUDDHIST_RELICS_ID,
		LibAdvancements.LUCKY_DRAW_ID,
		LibAdvancements.SPEAR_SUBSPACE_ID,
		LibAdvancements.HERRSCHER_DEFEAT_ID,
		LibAdvancements.ENDGAME_GOAL_ID,
		LibAdvancements.JINGWEI_FEATHER_ID,
		LibAdvancements.ONE_PUNCHMAN_ID,
		LibAdvancements.BOTTLE_SET_ID,
		LibAdvancements.RING_SET_ID,
		LibAdvancements.CORE_GOD_ID,
		LibAdvancements.MANA_DRIVER_RING_ID,
		LibAdvancements.MAGIC_FINGER_GET_ID
	};

	public static int statsAmount(EntityPlayer player){
		return (int) Arrays.stream(STAT_IDS)
			.filter(x -> hasStat(player, x))
			.count();
	}

	public static boolean hasAllStats(EntityPlayer player){
		return Arrays.stream(STAT_IDS)
			.allMatch(x -> hasStat(player, x));
	}

	public static boolean hasStat(EntityPlayer player, ResourceLocation id){
		if (player instanceof EntityPlayerMP) {
			EntityPlayerMP playerMP = ((EntityPlayerMP) player);
			AdvancementManager manager = playerMP.getServerWorld().getAdvancementManager();
			Advancement advancement = manager.getAdvancement(id);

			if (advancement != null) {
				return playerMP.getAdvancements().getProgress(advancement).isDone();
			}
		}

		return false;
	}

	@SideOnly(Side.CLIENT)
	public static Advancement getSideAdvancement(ResourceLocation id) {
		return Minecraft.getMinecraft()
			.player
			.connection
			.getAdvancementManager()
			.getAdvancementList()
			.getAdvancement(id);
	}

	@SideOnly(Side.CLIENT)
	public static boolean hasAdvancement(ResourceLocation id) {
		AdvancementProgress progress =
			Minecraft.getMinecraft()
				.player
				.connection
				.getAdvancementManager()
				.advancementToProgress.get(getSideAdvancement(id));

		return progress != null && progress.isDone();
	}

	@Deprecated
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

	@Deprecated
	public static boolean hasStat(EntityPlayer player, String name){
		return hasStat(player, new ResourceLocation(Reference.MOD_ID, LibAdvancements.PREFIX + name));
	}

	@Deprecated
	@SideOnly(Side.CLIENT)
	public static Advancement getSideAdvancement(String name) {
		return Minecraft.getMinecraft().player.connection.getAdvancementManager().getAdvancementList().getAdvancement(new ResourceLocation(Reference.MOD_ID, LibAdvancements.PREFIX+name));
	}

	@Deprecated
	@SideOnly(Side.CLIENT)
	public static boolean hasAdvancement(String name) {
		Advancement adv = getSideAdvancement(name);
		AdvancementProgress progress = Minecraft.getMinecraft().player.connection.getAdvancementManager().advancementToProgress.get(adv);
		return progress != null && progress.isDone();
	}
}
