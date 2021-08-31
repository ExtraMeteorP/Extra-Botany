package com.meteor.extrabotany.common.items.armor.goblinslayer;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.client.model.armor.ModelGoblinSlayerArmor;
import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.items.armor.miku.ItemMikuArmor;
import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public class ItemGoblinSlayerArmor extends ItemMikuArmor {

    public static final String TAG_DAY = "isday";

    public ItemGoblinSlayerArmor(EquipmentSlotType type, Properties props) {
        super(type, ExtraBotanyAPI.INSTANCE.getGoblinSlayerArmorMaterial(), props);
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return true;
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        super.onArmorTick(stack, world, player);
        if(hasArmorSet(player) && world.isDaytime()) {
            ItemNBTHelper.setBoolean(stack, TAG_DAY, true);
        }else
            ItemNBTHelper.setBoolean(stack, TAG_DAY, false);
    }

    @Nonnull
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(@Nonnull EquipmentSlotType slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> ret = super.getAttributeModifiers(slot, stack);
        UUID uuid = new UUID(Registry.ITEM.getKey(this).hashCode() + slot.toString().hashCode(), 0);
        boolean night = ItemNBTHelper.getBoolean(stack, TAG_DAY, false);
        if (slot == getEquipmentSlot()) {
            ret = HashMultimap.create(ret);
            ret.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "GoblinSlayer modifier " + type, night ? 0.5F : 0,  AttributeModifier.Operation.MULTIPLY_BASE));
            ret.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "GoblinSlayer modifier " + type, night ? 0.04F : 0, AttributeModifier.Operation.MULTIPLY_BASE));
        }
        return ret;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public BipedModel<?> provideArmorModelForSlot(EquipmentSlotType slot) {
        return new ModelGoblinSlayerArmor(slot);
    }

    @Override
    public String getArmorTextureAfterInk(ItemStack stack, EquipmentSlotType slot) {
        return LibMisc.MOD_ID + ":textures/model/armor_goblinslayer.png";
    }

    private static final LazyValue<ItemStack[]> armorSet = new LazyValue<>(() -> new ItemStack[] {
            new ItemStack(ModItems.armor_goblinslayer_helm),
            new ItemStack(ModItems.armor_goblinslayer_chest),
            new ItemStack(ModItems.armor_goblinslayer_legs),
            new ItemStack(ModItems.armor_goblinslayer_boots)
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
                return stack.getItem() == ModItems.armor_goblinslayer_helm;
            case CHEST:
                return stack.getItem() == ModItems.armor_goblinslayer_chest;
            case LEGS:
                return stack.getItem() == ModItems.armor_goblinslayer_legs;
            case FEET:
                return stack.getItem() == ModItems.armor_goblinslayer_boots;
        }

        return false;
    }

    @Override
    public IFormattableTextComponent getArmorSetName() {
        return new TranslationTextComponent("extrabotany.armorset.goblinslayer.name");
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addArmorSetDescription(ItemStack stack, List<ITextComponent> list) {
        list.add(new TranslationTextComponent("extrabotany.armorset.goblinslayer.desc0").mergeStyle(TextFormatting.GRAY));
        list.add(new TranslationTextComponent("extrabotany.armorset.goblinslayer.desc1").mergeStyle(TextFormatting.GRAY));
    }

}
