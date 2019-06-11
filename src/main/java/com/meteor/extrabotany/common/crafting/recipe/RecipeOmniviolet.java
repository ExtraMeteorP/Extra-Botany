package com.meteor.extrabotany.common.crafting.recipe;

import java.util.Collections;
import java.util.List;

import com.meteor.extrabotany.api.ExtraBotanyAPI;

import net.minecraft.item.ItemStack;

public class RecipeOmniviolet {
	
	public static void addRecipe(int output, ItemStack input) {
		ExtraBotanyAPI.omnivioletRecipes.add(new RecipeOmniviolet(output, input));
	}

	public static int getOutput(ItemStack input) {
		for (RecipeOmniviolet recipe : ExtraBotanyAPI.omnivioletRecipes)
			if (recipe.matches(input))
				return recipe.getOutput();

		return 0;
	}


	public static List<RecipeOmniviolet> getRecipeList() {
		return Collections.unmodifiableList(ExtraBotanyAPI.omnivioletRecipes);
	}

	private final int output;
	private final ItemStack input;

	public RecipeOmniviolet(int output, ItemStack input) {
		this.output = output;
		this.input = input.copy();

			if (input instanceof ItemStack)
				input = input.copy();

			else
				throw new IllegalArgumentException("Input must be an ItemStack");
	}

	public ItemStack getInput() {
		return input.copy();
	}

	public int getOutput() {
		return output;
	}

	public boolean matches(ItemStack stacks) {
		if (!stacks.isEmpty())
			if (areStacksTheSame(getInput(), stacks)) {
				return true;
			}
		return false;
	}

	private boolean areStacksTheSame(ItemStack stack, ItemStack target) {
		return areStacksTheSame(stack, target, false);
	}

	public static boolean areStacksTheSame(ItemStack stack1, ItemStack stack2, boolean matchSize) {
		if (stack1.isEmpty() || stack2.isEmpty())
			return false;

		if (stack1.getItem() == stack2.getItem())
			if (stack1.getItemDamage() == stack2.getItemDamage())
				if (!matchSize || stack1.getCount() == stack2.getCount()) {
					if (stack1.hasTagCompound() && stack2.hasTagCompound())
						return stack1.getTagCompound().equals(stack2.getTagCompound());
					return stack1.hasTagCompound() == stack2.hasTagCompound();
				}
		return false;
	}

}
