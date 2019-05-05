package com.meteor.extrabotany.common.item.enchantments;

import com.meteor.extrabotany.common.lib.LibMisc;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public class EnchantmentInstantHeal extends Enchantment{

	public EnchantmentInstantHeal() {
		super(Rarity.VERY_RARE, EnumEnchantmentType.ARMOR_HEAD, new EntityEquipmentSlot[]{EntityEquipmentSlot.HEAD});
		this.setName("instantheal");
		this.setRegistryName(LibMisc.MOD_ID, "instantheal");
	}
	
	public void onUserHurt(EntityLivingBase user, Entity attacker, int level){
		if(!(user instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) user;
		boolean cd = false;
		player.getArmorInventoryList().forEach(stack -> {
			if(stack.getItem() instanceof ItemArmor)
				if(((ItemArmor)stack.getItem()).getEquipmentSlot() == EntityEquipmentSlot.HEAD){
					if(!player.getCooldownTracker().hasCooldown(stack.getItem())){
						if(player.getHealth() <= player.getMaxHealth() * 0.2F){
							player.heal(10F + 5F * level);
							player.getCooldownTracker().setCooldown(stack.getItem(), 1800 - level * 200);
						}
					}					
				}
		});
	}
	
    public int getMinEnchantability(int enchantmentLevel){
        return 5 + (enchantmentLevel - 1) * 8;
    }

    public int getMaxEnchantability(int enchantmentLevel){
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }

    public int getMaxLevel(){
        return 3;
    }

}
