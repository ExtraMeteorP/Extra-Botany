package com.meteor.extrabotany.common.crafting.recipe;

import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.items.brew.ItemBrewBase;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import vazkii.botania.api.brew.IBrewItem;
import vazkii.botania.api.item.IRelic;

public class InfiniteWineChangeRecipe extends SpecialRecipe {

    public static final SpecialRecipeSerializer<InfiniteWineChangeRecipe> SERIALIZER = new SpecialRecipeSerializer<>(InfiniteWineChangeRecipe::new);

    public InfiniteWineChangeRecipe(ResourceLocation idIn) {
        super(idIn);
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        boolean foundInfiniteWine = false;
        boolean foundItem = false;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == ModItems.infinitewine && !foundInfiniteWine) {
                    foundInfiniteWine = true;
                } else if (!foundItem) {
                    if (stack.getItem() == ModItems.cocktail) {
                        foundItem = true;
                    } else {
                        return false;
                    }
                }
            }
        }

        return foundInfiniteWine && foundItem;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        ItemStack item = ItemStack.EMPTY;
        ItemStack cocktail = ItemStack.EMPTY;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == ModItems.infinitewine && item.isEmpty()) {
                    item = stack;
                } else if (cocktail.isEmpty()) {
                    if (stack.getItem() == ModItems.cocktail) {
                        cocktail = stack;
                    }
                }
            }
        }

        IRelic relic = (IRelic) item.getItem();
        ItemStack copy = item.copy();
        relic.bindToUUID(relic.getSoulbindUUID(item), copy);

        IBrewItem brew = (IBrewItem) cocktail.getItem();
        ItemBrewBase.setBrew(copy, brew.getBrew(cocktail));

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
