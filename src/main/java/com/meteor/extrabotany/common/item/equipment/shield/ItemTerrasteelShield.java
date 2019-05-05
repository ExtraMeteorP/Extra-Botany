package com.meteor.extrabotany.common.item.equipment.shield;

import com.meteor.extrabotany.common.lib.LibItemsName;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import vazkii.botania.api.BotaniaAPI;

public class ItemTerrasteelShield extends ItemManasteelShield{
	
	public ItemTerrasteelShield() {
		super(BotaniaAPI.terrasteelToolMaterial, LibItemsName.SHIELDTERRASTEEL);
	}
	
	@Override
	public void onAttackBlocked(ItemStack stack, EntityLivingBase attacked, float damage, DamageSource source) {
		if(source.getImmediateSource() != null) {
			if(attacked instanceof EntityPlayer) {
				source.getImmediateSource().attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)attacked), damage/2);
			} else {
				source.getImmediateSource().attackEntityFrom(DamageSource.causeMobDamage(attacked), damage/2);
			}
			source.getImmediateSource().setFire(5);
		}
		super.onAttackBlocked(stack, attacked, damage, source);
	}
	
	@Override
	public int getRepairSpeed(){
		return 20;
	}
	
	@Override
	public float getAttackerKnockbackMultiplier(ItemStack stack, EntityLivingBase attacked, float damage, DamageSource source) {
		return 1F;
	}

}
