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
	
	private static final int MANA_PER_DAMAGE = 60;
	
	public ItemManasteelShield(ToolMaterial tool, String name) {
		super(tool, name);
	}

	public ItemManasteelShield() {
		this(BotaniaAPI.manasteelToolMaterial, LibItemsName.SHIELDMANASTEEL);
	}
	
	@Override
	public boolean usesMana(ItemStack stack) {
		return true;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5) {
		if(!world.isRemote && player instanceof EntityPlayer && stack.getItemDamage() > 0 && ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer) player, MANA_PER_DAMAGE * 2, true))
			stack.setItemDamage(stack.getItemDamage() - 1);
	}
	
	public int getRepairSpeed(){
		return 40;
	}
	
	public int getManaPerDmg() {
		return MANA_PER_DAMAGE;
	}

}
