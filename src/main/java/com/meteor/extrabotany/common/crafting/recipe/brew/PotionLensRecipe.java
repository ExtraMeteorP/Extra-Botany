package com.meteor.extrabotany.common.crafting.recipe.brew;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.item.lens.ItemLens;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.brew.ItemBrewFlask;

public class PotionLensRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

	@Override
	public boolean isDynamic() {
		return true;
	}

	@Override
	public boolean matches(@Nonnull InventoryCrafting var1, @Nonnull World var2) {
		boolean foundLens = false;
		boolean foundItem = false;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() == ModItems.lens && !foundLens)
					foundLens = true;
				else if(!foundItem) {
					if(stack.getItem() instanceof ItemBrewFlask)
						foundItem = true;
					else return false;
				}
			}
		}

		return foundLens && foundItem;
	}

	@Nonnull
	@Override
	public ItemStack getCraftingResult(@Nonnull InventoryCrafting var1) {
		ItemStack item = ItemStack.EMPTY;
		ItemStack lens = ItemStack.EMPTY;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty() && stack.getItem() instanceof ItemBrewFlask && item.isEmpty())
				item = stack;
			if(!stack.isEmpty() && stack.getItem() instanceof ItemLens && item.isEmpty())
				lens = stack;
		}

		Brew brew = BotaniaAPI.getBrewFromKey(ItemNBTHelper.getString(item, "brewKey", ""));
		ItemStack copy = lens.copy();
		ItemLens bb = (ItemLens) copy.getItem();
		bb.setBrew(copy, brew.getKey());
		return copy;
	}

	@Override
	public boolean canFit(int width, int height) {
		return width * height >= 2;
	}

	@Nonnull
	@Override
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}
}
