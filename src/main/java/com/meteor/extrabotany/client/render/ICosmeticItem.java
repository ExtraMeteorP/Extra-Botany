package com.meteor.extrabotany.client.render;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.item.IBaubleRender.RenderType;

public interface ICosmeticItem {

    public void onItemRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks);

}
