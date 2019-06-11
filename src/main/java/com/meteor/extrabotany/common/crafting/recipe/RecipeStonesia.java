package com.meteor.extrabotany.common.crafting.recipe;

import java.util.Collections;
import java.util.List;

import com.meteor.extrabotany.api.ExtraBotanyAPI;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeStonesia {
	
	public static void addRecipe(int output, ItemStack input) {
		ExtraBotanyAPI.stonesiaRecipes.add(new RecipeStonesia(output, input));
	}

	public static int getOutput(ItemStack input) {
		for (RecipeStonesia recipe : ExtraBotanyAPI.stonesiaRecipes)
			if (recipe.matches(input))
				return recipe.getOutput();

		return 0;
	}


	public static List<RecipeStonesia> getRecipeList() {
		return Collections.unmodifiableList(ExtraBotanyAPI.stonesiaRecipes);
	}

	private final int output;
	private final Object input;

	public RecipeStonesia(int output, Object input) {
		this.output = output;
		this.input = input;

			if (input instanceof ItemStack)
				input = (ItemStack)input;
			else if(input instanceof String){
					input = (String)input;
			}else
				throw new IllegalArgumentException("Input must be an ItemStack");
	}

	public Object getInput() {
		return input;
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

	private boolean areStacksTheSame(Object stack, ItemStack target) {
		return areStacksTheSame(stack, target, false);
	}

	public static boolean areStacksTheSame(Object stack1, ItemStack stack2, boolean matchSize) {
		if(stack1 instanceof ItemStack){
			if (((ItemStack) stack1).isEmpty() || stack2.isEmpty())
				return false;
	
			if (((ItemStack) stack1).getItem() == stack2.getItem())
				if (((ItemStack) stack1).getItemDamage() == stack2.getItemDamage())
					if (!matchSize || ((ItemStack) stack1).getCount() == stack2.getCount()) {
						if (((ItemStack) stack1).hasTagCompound() && stack2.hasTagCompound())
							return ((ItemStack) stack1).getTagCompound().equals(stack2.getTagCompound());
						return ((ItemStack) stack1).hasTagCompound() == stack2.hasTagCompound();
					}
		}else if(stack1 instanceof String){
			for(ItemStack stack : OreDictionary.getOres((String) stack1))
				if (stack.getItem() == stack2.getItem())
					if (stack.getItemDamage() == stack2.getItemDamage())
						if (!matchSize || stack.getCount() == stack2.getCount()) {
							if (stack.hasTagCompound() && stack2.hasTagCompound())
								return stack.getTagCompound().equals(stack2.getTagCompound());
							return stack.hasTagCompound() == stack2.hasTagCompound();
						}
		}
		return false;
	}

}
