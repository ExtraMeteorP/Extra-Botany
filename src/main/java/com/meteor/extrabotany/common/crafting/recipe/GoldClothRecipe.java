package com.meteor.extrabotany.common.crafting.recipe;

import com.meteor.extrabotany.common.items.ModItems;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import vazkii.botania.api.item.IRelic;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class GoldClothRecipe extends SpecialRecipe {

    public static final SpecialRecipeSerializer<GoldClothRecipe> SERIALIZER = new SpecialRecipeSerializer<>(GoldClothRecipe::new);

    public GoldClothRecipe(ResourceLocation idIn) {
        super(idIn);
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        boolean foundGoldCloth = false;
        boolean foundItem = false;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == ModItems.goldcloth && !foundGoldCloth) {
                    foundGoldCloth = true;
                } else if (!foundItem) {
                    if (stack.getItem() instanceof IRelic) {
                        foundItem = true;
                    } else {
                        return false;
                    }
                }
            }
        }

        return foundGoldCloth && foundItem;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        ItemStack item = ItemStack.EMPTY;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof IRelic && item.isEmpty()) {
                    item = stack;
                }
            }
        }

        ItemStack copy = item.copy();
        ItemNBTHelper.removeEntry(copy, "soulbindUUID");
        return copy;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width > 1 || height > 1;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }
}
