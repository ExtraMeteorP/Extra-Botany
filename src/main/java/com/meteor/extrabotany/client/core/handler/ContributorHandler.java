package com.meteor.extrabotany.client.core.handler;

import com.meteor.extrabotany.client.particle.ParticleCloudPattern;
import com.meteor.extrabotany.common.core.handler.PersistentVariableHandler;
import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.lib.LibMisc;

import baubles.api.BaublesApi;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)
public class ContributorHandler {
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event) {
		if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().player.world != null && !Minecraft.getMinecraft().isGamePaused() && Minecraft.getMinecraft().player.world.getTotalWorldTime() % 11 == 0) {
			BlockPos pos = new BlockPos(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY, Minecraft.getMinecraft().player.posZ);
	        Minecraft.getMinecraft().player.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.add(32, 32, 32), pos.add(-32, -32, -32)),
	        input -> BaublesApi.isBaubleEquipped(input, ModItems.mask) == -1 && (PersistentVariableHandler.contributors.contains(input.getGameProfile().getName()) || PersistentVariableHandler.contributorsuuid.contains(input.getUniqueID().toString()))).
	        forEach(living -> Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleCloudPattern(living)));
		}
	}
	
	public static final String TAG_PLAYER_HAS_GIFT = "contributorspawnedgift";

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		NBTTagCompound playerData = event.player.getEntityData();
		NBTTagCompound data = getTagSafe(playerData, EntityPlayer.PERSISTED_NBT_TAG);
		if(!PersistentVariableHandler.contributors.contains(event.player.getGameProfile().getName()))
			return;
		if (!data.getBoolean(TAG_PLAYER_HAS_GIFT)) {
			ItemHandlerHelper.giveItemToPlayer(event.player, new ItemStack(ModItems.material, 1, 10));
			data.setBoolean(TAG_PLAYER_HAS_GIFT, true);
			playerData.setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
		}
	}

	public static NBTTagCompound getTagSafe(NBTTagCompound tag, String key) {
		if (tag == null) {
			return new NBTTagCompound();
		}

		return tag.getCompoundTag(key);
	}

}
