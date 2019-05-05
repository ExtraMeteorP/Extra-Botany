package com.meteor.extrabotany.common.item.equipment.shield;

import com.meteor.extrabotany.common.lib.LibItemsName;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.item.IPixieSpawner;

public class ItemElementiumShield extends ItemManasteelShield implements IPixieSpawner{
	
	public ItemElementiumShield() {
		super(BotaniaAPI.elementiumToolMaterial, LibItemsName.SHIELDELEMENTIUM);
	}
	
	@Override
	public void onAttackBlocked(ItemStack stack, EntityLivingBase attacked, float damage, DamageSource source) {
		if(source.getImmediateSource() != null) {
			source.getImmediateSource().setFire(5);
		}
		super.onAttackBlocked(stack, attacked, damage, source);
	}

	@Override
	public float getPixieChance(ItemStack arg0) {
		return 0.2F;
	}

}
