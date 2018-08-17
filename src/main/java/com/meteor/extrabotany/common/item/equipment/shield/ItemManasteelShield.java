package com.meteor.extrabotany.common.item.equipment.shield;

import com.meteor.extrabotany.common.lib.LibItemsName;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;

public class ItemManasteelShield extends ItemShieldCopy implements IManaUsingItem{
	
	public ItemManasteelShield(ToolMaterial tool, String name) {
		super(tool, name);
	}

	public ItemManasteelShield() {
		super(BotaniaAPI.manasteelToolMaterial, LibItemsName.SHIELDMANASTEEL);
	}
	
	@Override
	public boolean usesMana(ItemStack stack) {
		return true;
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World world, Entity par3Entity, int par4, boolean par5) {
		super.onUpdate(par1ItemStack, world, par3Entity, par4, par5);
		if(!world.isRemote && par3Entity.ticksExisted % getRepairSpeed() == 0 && ManaItemHandler.requestManaExact(par1ItemStack, (EntityPlayer)par3Entity, getManaPerDamage(), true)){
			par1ItemStack.setItemDamage(par1ItemStack.getItemDamage() - 1);
		}
	}
	
	public int getRepairSpeed(){
		return 40;
	}
	
	public int getManaPerDamage(){
		return 60;
	}

}
