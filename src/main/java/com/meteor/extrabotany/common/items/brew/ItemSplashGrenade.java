package com.meteor.extrabotany.common.items.brew;

import com.google.common.collect.Lists;
import com.meteor.extrabotany.common.entities.EntitySplashGrenade;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.util.*;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.api.brew.IBrewItem;
import vazkii.botania.common.brew.ModBrews;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

public class ItemSplashGrenade extends Item implements IBrewItem {

    private static final String TAG_BREW_KEY = "brewKey";

    public ItemSplashGrenade(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        EntitySplashGrenade sg = new EntitySplashGrenade(worldIn, playerIn);
        sg.setItem(itemstack);
        sg.setPosition(playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ());
        sg.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, -5.0F, 0.8F, 1.0F);
        if (!worldIn.isRemote) {
            worldIn.addEntity(sg);
        }
        worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(),
                SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.PLAYERS, 0.5F,0.8F);

        if (!playerIn.abilities.isCreativeMode) {
            itemstack.shrink(1);
        }

        return ActionResult.resultPass(itemstack);
    }

    @Override
    public void fillItemGroup(ItemGroup tab, NonNullList<ItemStack> list) {
        if (isInGroup(tab)) {
            for (Brew brew : BotaniaAPI.instance().getBrewRegistry()) {
                if (brew == ModBrews.fallbackBrew) {
                    continue;
                }
                ItemStack stack = new ItemStack(this);
                setBrew(stack, brew);
                list.add(stack);
            }
        }
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName(@Nonnull ItemStack stack) {
        return new TranslationTextComponent(getTranslationKey(), new TranslationTextComponent(getBrew(stack).getTranslationKey(stack)));
    }

    // [VanillaCopy] PotionUtils.addPotionTooltip, with custom effect list
    @OnlyIn(Dist.CLIENT)
    public static void addPotionTooltip(List<EffectInstance> list, List<ITextComponent> lores, float durationFactor) {
        List<Pair<Attribute, AttributeModifier>> list1 = Lists.newArrayList();
        if (list.isEmpty()) {
            lores.add((new TranslationTextComponent("effect.none")).mergeStyle(TextFormatting.GRAY));
        } else {
            for (EffectInstance effectinstance : list) {
                IFormattableTextComponent iformattabletextcomponent = new TranslationTextComponent(effectinstance.getEffectName());
                Effect effect = effectinstance.getPotion();
                Map<Attribute, AttributeModifier> map = effect.getAttributeModifierMap();
                if (!map.isEmpty()) {
                    for (Map.Entry<Attribute, AttributeModifier> entry : map.entrySet()) {
                        AttributeModifier attributemodifier = entry.getValue();
                        AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), effect.getAttributeModifierAmount(effectinstance.getAmplifier(), attributemodifier), attributemodifier.getOperation());
                        list1.add(new Pair<>(entry.getKey(), attributemodifier1));
                    }
                }

                if (effectinstance.getAmplifier() > 0) {
                    iformattabletextcomponent = new TranslationTextComponent("potion.withAmplifier", iformattabletextcomponent, new TranslationTextComponent("potion.potency." + effectinstance.getAmplifier()));
                }

                if (effectinstance.getDuration() > 20) {
                    iformattabletextcomponent = new TranslationTextComponent("potion.withDuration", iformattabletextcomponent, EffectUtils.getPotionDurationString(new EffectInstance(effectinstance.getPotion(), (int) (effectinstance.getDuration() * 0.6F)), durationFactor));
                }

                lores.add(iformattabletextcomponent.mergeStyle(effect.getEffectType().getColor()));
            }
        }

        if (!list1.isEmpty()) {
            lores.add(StringTextComponent.EMPTY);
            lores.add((new TranslationTextComponent("potion.whenDrank")).mergeStyle(TextFormatting.DARK_PURPLE));

            for (Pair<Attribute, AttributeModifier> pair : list1) {
                AttributeModifier attributemodifier2 = pair.getSecond();
                double d0 = attributemodifier2.getAmount();
                double d1;
                if (attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
                    d1 = attributemodifier2.getAmount();
                } else {
                    d1 = attributemodifier2.getAmount() * 100.0D;
                }

                if (d0 > 0.0D) {
                    lores.add((new TranslationTextComponent("attribute.modifier.plus." + attributemodifier2.getOperation().getId(), ItemStack.DECIMALFORMAT.format(d1), new TranslationTextComponent(pair.getFirst().getAttributeName()))).mergeStyle(TextFormatting.BLUE));
                } else if (d0 < 0.0D) {
                    d1 = d1 * -1.0D;
                    lores.add((new TranslationTextComponent("attribute.modifier.take." + attributemodifier2.getOperation().getId(), ItemStack.DECIMALFORMAT.format(d1), new TranslationTextComponent(pair.getFirst().getAttributeName()))).mergeStyle(TextFormatting.RED));
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flags) {
        addPotionTooltip(getBrew(stack).getPotionEffects(stack), list, 1);
    }

    @Override
    public Brew getBrew(ItemStack stack) {
        String key = ItemNBTHelper.getString(stack, TAG_BREW_KEY, "");
        return BotaniaAPI.instance().getBrewRegistry().getOrDefault(ResourceLocation.tryCreate(key));
    }

    public static void setBrew(ItemStack stack, @Nullable Brew brew) {
        ResourceLocation id;
        if (brew != null) {
            id = BotaniaAPI.instance().getBrewRegistry().getKey(brew);
        } else {
            id = prefix("fallback");
        }
        setBrew(stack, id);
    }

    public static void setBrew(ItemStack stack, ResourceLocation brew) {
        ItemNBTHelper.setString(stack, TAG_BREW_KEY, brew.toString());
    }

    @Nonnull
    public static String getSubtype(ItemStack stack) {
        return stack.hasTag() ? ItemNBTHelper.getString(stack, TAG_BREW_KEY, "none") : "none";
    }

}
