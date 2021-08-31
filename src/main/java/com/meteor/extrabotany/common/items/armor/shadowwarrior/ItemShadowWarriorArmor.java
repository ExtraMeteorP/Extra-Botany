package com.meteor.extrabotany.common.items.armor.shadowwarrior;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.client.model.armor.ModelShadowWarriorArmor;
import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.items.armor.miku.ItemMikuArmor;
import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
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

public class ItemShadowWarriorArmor extends ItemMikuArmor {

    public static final String TAG_NIGHT = "isnight";

    public ItemShadowWarriorArmor(EquipmentSlotType type, Properties props) {
        super(type, ExtraBotanyAPI.INSTANCE.getShadowWarriorArmorMaterial(), props);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        super.onArmorTick(stack, world, player);
        if(hasArmorSet(player) && !world.isDaytime()) {
            ItemNBTHelper.setBoolean(stack, TAG_NIGHT, true);
            player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 400, 1));
        }else
            ItemNBTHelper.setBoolean(stack, TAG_NIGHT, false);
    }

    @Nonnull
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(@Nonnull EquipmentSlotType slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> ret = super.getAttributeModifiers(slot, stack);
        UUID uuid = new UUID(Registry.ITEM.getKey(this).hashCode() + slot.toString().hashCode(), 0);
        boolean night = ItemNBTHelper.getBoolean(stack, TAG_NIGHT, false);
        if (slot == getEquipmentSlot()) {
            ret = HashMultimap.create(ret);
            ret.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "ShadowWarrior modifier health" + type, night ? 0.25F : 0,  AttributeModifier.Operation.MULTIPLY_BASE));
            ret.put(Attributes.ATTACK_SPEED, new AttributeModifier(uuid, "ShadowWarrior modifier attack speed" + type, night ? 0.125F : 0, AttributeModifier.Operation.MULTIPLY_BASE));
            ret.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "ShadowWarrior modifier attack damage" + type, night ? 0.05F : 0, AttributeModifier.Operation.MULTIPLY_BASE));
        }
        return ret;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public BipedModel<?> provideArmorModelForSlot(EquipmentSlotType slot) {
        return new ModelShadowWarriorArmor(slot);
    }

    @Override
    public String getArmorTextureAfterInk(ItemStack stack, EquipmentSlotType slot) {
        return LibMisc.MOD_ID + ":textures/model/armor_shadowwarrior.png";
    }

    private static final LazyValue<ItemStack[]> armorSet = new LazyValue<>(() -> new ItemStack[] {
            new ItemStack(ModItems.armor_shadowwarrior_helm),
            new ItemStack(ModItems.armor_shadowwarrior_chest),
            new ItemStack(ModItems.armor_shadowwarrior_legs),
            new ItemStack(ModItems.armor_shadowwarrior_boots)
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
                return stack.getItem() == ModItems.armor_shadowwarrior_helm;
            case CHEST:
                return stack.getItem() == ModItems.armor_shadowwarrior_chest;
            case LEGS:
                return stack.getItem() == ModItems.armor_shadowwarrior_legs;
            case FEET:
                return stack.getItem() == ModItems.armor_shadowwarrior_boots;
        }

        return false;
    }

    @Override
    public IFormattableTextComponent getArmorSetName() {
        return new TranslationTextComponent("extrabotany.armorset.shadowwarrior.name");
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addArmorSetDescription(ItemStack stack, List<ITextComponent> list) {
        list.add(new TranslationTextComponent("extrabotany.armorset.shadowwarrior.desc0").mergeStyle(TextFormatting.GRAY));
        list.add(new TranslationTextComponent("extrabotany.armorset.shadowwarrior.desc1").mergeStyle(TextFormatting.GRAY));
    }

}
