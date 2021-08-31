package com.meteor.extrabotany.common.items.armor.shootingguardian;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.client.model.armor.ModelShootingGuardianArmor;
import com.meteor.extrabotany.client.model.armor.ModelShootingGuardianHelmet;
import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.items.armor.miku.ItemMikuArmor;
import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.LazyValue;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public class ItemShootingGuardianArmor extends ItemMikuArmor {

    public ItemShootingGuardianArmor(EquipmentSlotType type, Properties props) {
        super(type, ExtraBotanyAPI.INSTANCE.getShootingGuardianArmorMaterial(), props);
    }

    @Nonnull
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(@Nonnull EquipmentSlotType slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> ret = super.getAttributeModifiers(slot, stack);
        UUID uuid = new UUID(Registry.ITEM.getKey(this).hashCode() + slot.toString().hashCode(), 0);
        if (slot == getEquipmentSlot()) {
            ret = HashMultimap.create(ret);
            ret.put(Attributes.FLYING_SPEED, new AttributeModifier(uuid, "Shooting Guardian modifier flying speed" + type, 0.10F, AttributeModifier.Operation.MULTIPLY_BASE));
            ret.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "Shooting Guardian modifier speed" + type, 0.10F, AttributeModifier.Operation.MULTIPLY_BASE));
            ret.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "Shooting Guardian modifier attack damage" + type, 0.10F, AttributeModifier.Operation.MULTIPLY_BASE));
            ret.put(Attributes.ATTACK_SPEED, new AttributeModifier(uuid, "Shooting Guardian modifier attack speed" + type, 0.03F, AttributeModifier.Operation.MULTIPLY_BASE));
        }
        return ret;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public BipedModel<?> provideArmorModelForSlot(EquipmentSlotType slot) {
        return new ModelShootingGuardianArmor(slot);
    }

    @Override
    public String getArmorTextureAfterInk(ItemStack stack, EquipmentSlotType slot) {
        return slot == EquipmentSlotType.HEAD ? LibMisc.MOD_ID + ":textures/model/armor_shootingguardian_helmet.png" : LibMisc.MOD_ID + ":textures/model/armor_shootingguardian.png";
    }

    private static final LazyValue<ItemStack[]> armorSet = new LazyValue<>(() -> new ItemStack[] {
            new ItemStack(ModItems.armor_shootingguardian_helm),
            new ItemStack(ModItems.armor_shootingguardian_chest),
            new ItemStack(ModItems.armor_shootingguardian_legs),
            new ItemStack(ModItems.armor_shootingguardian_boots)
    });

    @Override
    public ItemStack[] getArmorSetStacks() {
        return armorSet.getValue();
    }

    @Override
    public boolean hasArmorSetItem(PlayerEntity player, EquipmentSlotType slot) {
        if (player == null) {
            return false;
        }

        ItemStack stack = player.getItemStackFromSlot(slot);
        if (stack.isEmpty()) {
            return false;
        }

        switch (slot) {
            case HEAD:
                return stack.getItem() == ModItems.armor_shootingguardian_helm;
            case CHEST:
                return stack.getItem() == ModItems.armor_shootingguardian_chest;
            case LEGS:
                return stack.getItem() == ModItems.armor_shootingguardian_legs;
            case FEET:
                return stack.getItem() == ModItems.armor_shootingguardian_boots;
        }

        return false;
    }

    @Override
    public IFormattableTextComponent getArmorSetName() {
        return new TranslationTextComponent("extrabotany.armorset.shootingguardian.name");
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addArmorSetDescription(ItemStack stack, List<ITextComponent> list) {
        list.add(new TranslationTextComponent("extrabotany.armorset.shootingguardian.desc0").mergeStyle(TextFormatting.GRAY));
        list.add(new TranslationTextComponent("extrabotany.armorset.shootingguardian.desc1").mergeStyle(TextFormatting.GRAY));
        list.add(new TranslationTextComponent("extrabotany.armorset.shootingguardian.desc2").mergeStyle(TextFormatting.GRAY));
        list.add(new TranslationTextComponent("extrabotany.armorset.shootingguardian.desc3").mergeStyle(TextFormatting.GRAY));
    }

}
