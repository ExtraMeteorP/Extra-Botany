package com.meteor.extrabotany.api;

import com.meteor.extrabotany.common.items.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ExtraBotanyAPI {

    public static ExtraBotanyAPI INSTANCE = new ExtraBotanyAPI();

    private enum ArmorMaterial implements IArmorMaterial {
        MIKU("miku", 5, new int[] { 2, 4, 5, 1 }, 22, () -> SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> ModItems.manadrink, 0),
        MAID("maid", 40, new int[] { 4, 7, 9, 4 }, 32, () -> SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, () -> ModItems.goldcloth, 3),
        GOBLINSLAYER("goblinslayer", 27, new int[] { 3, 5, 7, 2 }, 30, () -> SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> ModItems.photonium, 1),
        SHADOWWARRIOR("shadowwarrior", 24, new int[] { 2, 5, 6, 2 }, 26, () -> SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> ModItems.shadowium, 1),
        SHOOTINGGUARDIAN("shootingguardian", 34, new int[] { 3, 7, 8, 4 }, 34, () -> SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> ModItems.orichalcos, 2),
        SILENTSAGES("silentsages", 50, new int[] { 4, 8, 9, 5 }, 40, () -> SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> ModItems.orichalcos, 3);

        private final String name;
        private final int durabilityMultiplier;
        private final int[] damageReduction;
        private final int enchantability;
        private final Supplier<SoundEvent> equipSound;
        private final Supplier<Item> repairItem;
        private final float toughness;
        private static final int[] MAX_DAMAGE_ARRAY = new int[] { 13, 15, 16, 11 };

        ArmorMaterial(String name, int durabilityMultiplier, int[] damageReduction, int enchantability, Supplier<SoundEvent> equipSound, Supplier<Item> repairItem, float toughness) {
            this.name = name;
            this.durabilityMultiplier = durabilityMultiplier;
            this.damageReduction = damageReduction;
            this.enchantability = enchantability;
            this.equipSound = equipSound;
            this.repairItem = repairItem;
            this.toughness = toughness;
        }

        @Override
        public int getDurability(EquipmentSlotType slot) {
            return durabilityMultiplier * MAX_DAMAGE_ARRAY[slot.getIndex()];
        }

        @Override
        public int getDamageReductionAmount(EquipmentSlotType slot) {
            return damageReduction[slot.getIndex()];
        }

        @Override
        public int getEnchantability() {
            return enchantability;
        }

        @Nonnull
        @Override
        public SoundEvent getSoundEvent() {
            return equipSound.get();
        }

        @Nonnull
        @Override
        public Ingredient getRepairMaterial() {
            return Ingredient.fromItems(repairItem.get());
        }

        @Nonnull
        @Override
        public String getName() {
            return name;
        }

        @Override
        public float getToughness() {
            return toughness;
        }

        @Override
        public float getKnockbackResistance() {
            return 0;
        }
    }

    public IArmorMaterial getMaidArmorMaterial() {
        return ArmorMaterial.MAID;
    }

    public IArmorMaterial getMikuArmorMaterial() {
        return ArmorMaterial.MIKU;
    }

    public IArmorMaterial getGoblinSlayerArmorMaterial() {
        return ArmorMaterial.GOBLINSLAYER;
    }

    public IArmorMaterial getShadowWarriorArmorMaterial() {
        return ArmorMaterial.SHADOWWARRIOR;
    }

    public IArmorMaterial getShootingGuardianArmorMaterial() {
        return ArmorMaterial.SHOOTINGGUARDIAN;
    }

    public IArmorMaterial getSilentSagesArmorMaterial() {
        return ArmorMaterial.SILENTSAGES;
    }

    public static void addPotionEffect(LivingEntity entity, Effect potion, int time, int max, boolean multi) {
        if (!entity.isPotionActive(potion))
            entity.addPotionEffect(new EffectInstance(potion, time, 0));
        else {
            int amp = entity.getActivePotionEffect(potion).getAmplifier();
            int t = multi ? time + 200 * amp : time;
            entity.addPotionEffect(new EffectInstance(potion, t, Math.min(max, amp + 1)));
        }
    }

    public static void addPotionEffect(LivingEntity entity, Effect potion, int max) {
        addPotionEffect(entity, potion, 100, max, false);
    }

    public static float calcDamage(float orig, PlayerEntity player){
        if(player == null)
            return orig;
        double value = player.getAttributeValue(Attributes.ATTACK_DAMAGE);
        return (float) (orig + value);
    }
    
}
