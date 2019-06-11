package com.meteor.extrabotany.common.core.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class PlayerStatHandler {
	
	public static NBTTagCompound getPlayerPersistentNBT(EntityPlayer player) {
        NBTTagCompound nbt = player.getEntityData().getCompoundTag("PlayerPersisted");
        if (!nbt.hasKey("extrabotany_data")) {
            nbt.setTag("extrabotany_data", new NBTTagCompound());
            ((NBTTagCompound) nbt.getTag("extrabotany_data")).setInteger("greenhatTicks", 0);
            ((NBTTagCompound) nbt.getTag("extrabotany_data")).setInteger("greenhats", 0);
            player.getEntityData().setTag("PlayerPersisted", nbt);
        }
        return nbt;
    }
	
	public static int getGreenHats(EntityPlayer player){
		NBTTagCompound nbt = getPlayerPersistentNBT(player);
		if (nbt.hasKey("extrabotany_data")) {
            return ((NBTTagCompound) nbt.getTag("extrabotany_data")).getInteger("greenhats");
        }
		return 0;
	}
	
	public static int getGreenHatTicks(EntityPlayer player){
		NBTTagCompound nbt = getPlayerPersistentNBT(player);
		if (nbt.hasKey("extrabotany_data")) {
            return ((NBTTagCompound) nbt.getTag("extrabotany_data")).getInteger("greenhatTicks");
        }
		return 0;
	}
	
	public static void setGreenHats(EntityPlayer player, int d){
		NBTTagCompound nbt = getPlayerPersistentNBT(player);
		if (nbt.hasKey("extrabotany_data")) {
			((NBTTagCompound) nbt.getTag("extrabotany_data")).setInteger("greenhats", d);;
        }
	}
	
	public static void setGreenHatTicks(EntityPlayer player, int d){
		NBTTagCompound nbt = getPlayerPersistentNBT(player);
		if (nbt.hasKey("extrabotany_data")) {
			((NBTTagCompound) nbt.getTag("extrabotany_data")).setInteger("greenhatTicks", d);;
        }
	}

}
