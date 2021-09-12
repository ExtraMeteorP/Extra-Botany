package com.meteor.extrabotany.common.items.bauble;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.meteor.extrabotany.common.core.EquipmentHandler;
import com.meteor.extrabotany.common.handler.IAdvancementRequirement;
import com.meteor.extrabotany.common.libs.LibAdvancementNames;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.common.core.handler.PixieHandler;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.equipment.bauble.ItemAuraRing;
import vazkii.botania.common.item.equipment.bauble.ItemMiningRing;
import vazkii.botania.common.item.equipment.bauble.ItemSwapRing;
import vazkii.botania.common.item.equipment.bauble.ItemWaterRing;
import vazkii.botania.common.item.relic.ItemRelicBauble;

import static com.meteor.extrabotany.common.items.ModItems.*;

public class ItemSunRing extends ItemRelicBauble implements IManaUsingItem, IAdvancementRequirement {

    public ItemSunRing(Properties props) {
        super(props);
    }

    @Override
    public boolean canEquip(ItemStack stack, LivingEntity entity) {
        return EquipmentHandler.findOrEmpty(this, entity).isEmpty();
    }

    @Override
    public void onWornTick(ItemStack stack, LivingEntity player) {
        super.onWornTick(stack, player);
        ((ItemMiningRing) ModItems.miningRing).onWornTick(stack, player);
        ((ItemAuraRing) ModItems.auraRingGreater).onWornTick(stack, player);
        ((ItemSwapRing) ModItems.swapRing).onWornTick(stack, player);
        ((ItemWaterRing) ModItems.waterRing).onWornTick(stack, player);
        ((ItemDeathRing) deathring).onWornTick(stack, player);
        ((ItemFrostStar) froststar).onWornTick(stack, player);
        ((ItemManaDriveRing) manadrivering).onWornTick(stack, player);
    }

    @Override
    public void onUnequipped(ItemStack stack, LivingEntity player) {
        super.onUnequipped(stack, player);
        ((ItemMiningRing) ModItems.miningRing).onUnequipped(stack, player);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getEquippedAttributeModifiers(ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributes = HashMultimap.create();
        attributes.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(getBaubleUUID(stack), "Sun Ring", 3.5, AttributeModifier.Operation.ADDITION));
        attributes.put(PixieHandler.PIXIE_SPAWN_CHANCE, new AttributeModifier(getBaubleUUID(stack), "Ring modifier", 0.25, AttributeModifier.Operation.ADDITION));
        return attributes;
    }

    @Override
    public boolean usesMana(ItemStack stack) {
        return true;
    }

    @Override
    public String getAdvancementName() {
        return LibAdvancementNames.EGODEFEAT;
    }
}
