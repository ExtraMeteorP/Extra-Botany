package com.meteor.extrabotany.common.items.bauble;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.meteor.extrabotany.common.core.EquipmentHandler;
import com.meteor.extrabotany.common.items.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ItemStack;

public class ItemTheCommunity extends ItemBauble{

    public ItemTheCommunity(Properties props) {
        super(props);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getEquippedAttributeModifiers(ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributes = HashMultimap.create();
        attributes.put(Attributes.ARMOR, new AttributeModifier(getBaubleUUID(stack), "Earth Stone", 4, AttributeModifier.Operation.ADDITION));
        attributes.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(getBaubleUUID(stack), "Ignis Stone", 0.10F, AttributeModifier.Operation.MULTIPLY_TOTAL));
        attributes.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(getBaubleUUID(stack), "Aero Stone 1", 0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL));
        attributes.put(Attributes.FLYING_SPEED, new AttributeModifier(getBaubleUUID(stack), "Aero Stone 2", 0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL));
        return attributes;
    }

    @Override
    public boolean canEquip(ItemStack stack, LivingEntity entity) {
        return EquipmentHandler.findOrEmpty(this, entity).isEmpty()
                && EquipmentHandler.findOrEmpty(ModItems.aerostone, entity).isEmpty()
                && EquipmentHandler.findOrEmpty(ModItems.earthstone, entity).isEmpty()
                && EquipmentHandler.findOrEmpty(ModItems.aquastone, entity).isEmpty()
                && EquipmentHandler.findOrEmpty(ModItems.ignisstone, entity).isEmpty();
    }

}
