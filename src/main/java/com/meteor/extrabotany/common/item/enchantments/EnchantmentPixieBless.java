package com.meteor.extrabotany.common.item.enchantments;

import com.meteor.extrabotany.common.entity.EntityPetPixie;
import com.meteor.extrabotany.common.lib.LibMisc;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentPixieBless extends Enchantment {

    public EnchantmentPixieBless() {
        super(Rarity.VERY_RARE, EnumEnchantmentType.ARMOR_CHEST, new EntityEquipmentSlot[]{EntityEquipmentSlot.CHEST});
        this.setName("pixiebless");
        this.setRegistryName(LibMisc.MOD_ID, "pixiebless");
    }

    public void onUserHurt(EntityLivingBase user, Entity attacker, int level) {
        if (user.world.rand.nextInt(12) < level && attacker instanceof EntityLivingBase) {
            EntityPetPixie pet = new EntityPetPixie(user.world);
            pet.setProps(user, 0.8F);
            pet.setPosition(user.posX, user.posY + 1.2F, user.posZ);
            pet.setAttackTarget((EntityLivingBase) attacker);
            user.world.spawnEntity(pet);
        }
    }

    public int getMinEnchantability(int enchantmentLevel) {
        return 5 + (enchantmentLevel - 1) * 8;
    }

    public int getMaxEnchantability(int enchantmentLevel) {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }

    public int getMaxLevel() {
        return 3;
    }

}
