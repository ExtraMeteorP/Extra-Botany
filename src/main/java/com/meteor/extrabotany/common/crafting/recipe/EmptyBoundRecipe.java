package com.meteor.extrabotany.common.crafting.recipe;

import java.util.UUID;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.common.item.ModItems;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;
import vazkii.botania.api.item.IRelic;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.crafting.recipe.HelmRevealingRecipe;

public class EmptyBoundRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

	@Override
	public boolean isDynamic() {
		return true;
	}

	@Override
	public boolean matches(@Nonnull InventoryCrafting var1, @Nonnull World var2) {
		boolean foundRelic = false;
		boolean foundItem = false;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() instanceof IRelic && !foundRelic)
					foundRelic = true;
				else if(!foundItem) {
					if(stack.getItem() == ModItems.material && stack.getMetadata() == 7)
						foundItem = true;
					else return false;
				}
			}
		}

		return foundRelic && foundItem;
	}

	@Nonnull
	@Override
	public ItemStack getCraftingResult(@Nonnull InventoryCrafting var1) {
		ItemStack item = ItemStack.EMPTY;
		ItemStack cloth = ItemStack.EMPTY;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty() && stack.getItem() instanceof IRelic)
				item = stack;
			if(!stack.isEmpty() && stack.getItem() == ModItems.material && stack.getMetadata() == 7)
				cloth = stack;
		}
		
		if(item.isEmpty())
			return ItemStack.EMPTY;

		ItemStack copy = item.copy();
		if(!ItemNBTHelper.getString(cloth, "uuid", "").isEmpty())
			ItemNBTHelper.setString(copy, "soulbindUUID", ItemNBTHelper.getString(cloth, "uuid", ""));
		else ItemNBTHelper.setString(copy, "soulbindUUID", "");
		
		//Copy Enchantments
		NBTTagList enchList = ItemNBTHelper.getList(item, "ench", 10, true);
		if(enchList != null)
			ItemNBTHelper.setList(copy, "ench", enchList);
		HelmRevealingRecipe.copyTCData(item, copy);
		
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
