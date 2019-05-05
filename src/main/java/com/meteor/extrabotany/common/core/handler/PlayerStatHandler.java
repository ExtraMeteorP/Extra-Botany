package com.meteor.extrabotany.common.core.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class PlayerStatHandler {

    private static final String TAG_EXTRABOTANY = "extrabotany_data";
    private static final String TAG_GAIADEFEAT = "gaiadefeat";
    private static final String TAG_VOIDHERRSCHERDEFEAT = "voidherrscher";
    private static final String TAG_TRUEDAMAGE = "truedamagetaken";

    public static NBTTagCompound getPlayerPersistentNBT(EntityPlayer player) {
        NBTTagCompound nbt = player.getEntityData().getCompoundTag("PlayerPersisted");
        if (!nbt.hasKey(TAG_EXTRABOTANY)) {
            nbt.setTag(TAG_EXTRABOTANY, new NBTTagCompound());
            ((NBTTagCompound) nbt.getTag(TAG_EXTRABOTANY)).setInteger(TAG_GAIADEFEAT, 0);
            ((NBTTagCompound) nbt.getTag(TAG_EXTRABOTANY)).setInteger(TAG_VOIDHERRSCHERDEFEAT, 0);
            ((NBTTagCompound) nbt.getTag(TAG_EXTRABOTANY)).setFloat(TAG_TRUEDAMAGE, 0);
            player.getEntityData().setTag("PlayerPersisted", nbt);
        }
        return nbt;
    }

    public static int getGaiaDefeat(EntityPlayer player) {
        NBTTagCompound nbt = getPlayerPersistentNBT(player);
        if (nbt.hasKey(TAG_EXTRABOTANY)) {
            return ((NBTTagCompound) nbt.getTag(TAG_EXTRABOTANY)).getInteger(TAG_GAIADEFEAT);
        }
        return 0;
    }

    public static int getVoidHerrscherDefeat(EntityPlayer player) {
        NBTTagCompound nbt = getPlayerPersistentNBT(player);
        if (nbt.hasKey(TAG_EXTRABOTANY)) {
            return ((NBTTagCompound) nbt.getTag(TAG_EXTRABOTANY)).getInteger(TAG_VOIDHERRSCHERDEFEAT);
        }
        return 0;
    }

    public static void setGaiaDefeat(EntityPlayer player, int d) {
        NBTTagCompound nbt = getPlayerPersistentNBT(player);
        if (nbt.hasKey(TAG_EXTRABOTANY)) {
            ((NBTTagCompound) nbt.getTag(TAG_EXTRABOTANY)).setInteger(TAG_GAIADEFEAT, d);
            ;
        }
    }

    public static void setHerrscherDefeat(EntityPlayer player, int d) {
        NBTTagCompound nbt = getPlayerPersistentNBT(player);
        if (nbt.hasKey(TAG_EXTRABOTANY)) {
            ((NBTTagCompound) nbt.getTag(TAG_EXTRABOTANY)).setInteger(TAG_VOIDHERRSCHERDEFEAT, d);
            ;
        }
    }

    public static float getTrueDamageTaken(EntityPlayer player) {
        NBTTagCompound nbt = getPlayerPersistentNBT(player);
        if (nbt.hasKey(TAG_EXTRABOTANY)) {
            return ((NBTTagCompound) nbt.getTag(TAG_EXTRABOTANY)).getFloat(TAG_TRUEDAMAGE);
        }
        return 0;
    }

    public static void setTrueDamageTaken(EntityPlayer player, float d) {
        NBTTagCompound nbt = getPlayerPersistentNBT(player);
        if (nbt.hasKey(TAG_EXTRABOTANY)) {
            ((NBTTagCompound) nbt.getTag(TAG_EXTRABOTANY)).setFloat(TAG_TRUEDAMAGE, d);
            ;
        }
    }

}
