package com.meteor.extrabotany.common.item.equipment.armor.combatmaid;

import com.meteor.extrabotany.common.lib.LibItemsName;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;
import thaumcraft.api.items.IGoggles;
import thaumcraft.api.items.IRevealer;

@Optional.InterfaceList({
        @Optional.Interface(modid = "thaumcraft", iface = "thaumcraft.api.items.IGoggles", striprefs = true),
        @Optional.Interface(modid = "thaumcraft", iface = "thaumcraft.api.items.IRevealer", striprefs = true)})
public class ItemCombatMaidHelmRevealing extends ItemCombatMaidHelm implements IGoggles, IRevealer {

    public ItemCombatMaidHelmRevealing() {
        super(LibItemsName.CMHELMREVEALING);
    }

    @Override
    public boolean showNodes(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    public boolean showIngamePopups(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }
}
