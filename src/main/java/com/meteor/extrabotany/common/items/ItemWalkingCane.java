package com.meteor.extrabotany.common.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import java.util.UUID;

public class ItemWalkingCane extends Item {

    public ItemWalkingCane(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(@Nonnull EquipmentSlotType slot) {
        Multimap<Attribute, AttributeModifier> ret = super.getAttributeModifiers(slot);
        if (slot == EquipmentSlotType.MAINHAND) {
            ret = HashMultimap.create(ret);
            ret.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("995829fa-94c0-41bd-b046-0468c509a488"), "Cane modifier", 0.3D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        return ret;
    }

}
