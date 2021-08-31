package com.meteor.extrabotany.common.crafting.recipe;

import com.google.gson.JsonObject;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;
import vazkii.botania.api.item.IRelic;

import javax.annotation.Nonnull;

public class RelicUpgradeRecipe implements ICraftingRecipe {

    private final ShapelessRecipe compose;

    public RelicUpgradeRecipe(ShapelessRecipe compose) {
        this.compose = compose;
    }

    @Override
    public boolean matches(@Nonnull CraftingInventory inv, @Nonnull World world) {
        return compose.matches(inv, world);
    }


    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull CraftingInventory inv) {
        ItemStack out = compose.getCraftingResult(inv);
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() instanceof IRelic) {
                EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(stack), out);
                IRelic relic = (IRelic) stack.getItem();
                relic.bindToUUID(relic.getSoulbindUUID(stack), out);
                break;
            }
        }
        return out;
    }

    @Override
    public boolean canFit(int width, int height) {
        return compose.canFit(width, height);
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() {
        return compose.getRecipeOutput();
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return compose.getIngredients();
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return compose.getId();
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public static final IRecipeSerializer<RelicUpgradeRecipe> SERIALIZER = new RelicUpgradeRecipe.Serializer();

    private static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<RelicUpgradeRecipe> {
        @Override
        public RelicUpgradeRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
            return new RelicUpgradeRecipe(IRecipeSerializer.CRAFTING_SHAPELESS.read(recipeId, json));
        }

        @Override
        public RelicUpgradeRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull PacketBuffer buffer) {
            return new RelicUpgradeRecipe(IRecipeSerializer.CRAFTING_SHAPELESS.read(recipeId, buffer));
        }

        @Override
        public void write(@Nonnull PacketBuffer buffer, @Nonnull RelicUpgradeRecipe recipe) {
            IRecipeSerializer.CRAFTING_SHAPELESS.write(buffer, recipe.compose);
        }
    };
}
