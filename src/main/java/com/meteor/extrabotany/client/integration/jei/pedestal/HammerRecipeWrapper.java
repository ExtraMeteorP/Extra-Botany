package com.meteor.extrabotany.client.integration.jei.pedestal;

import com.google.common.collect.ImmutableList;
import com.meteor.extrabotany.common.crafting.recipe.RecipePedestal;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public class HammerRecipeWrapper implements IRecipeWrapper{

	private final List<List<ItemStack>> input;
	private final ItemStack outputs;

	public HammerRecipeWrapper(RecipePedestal recipe) {
		ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();
		builder.add(ImmutableList.of((ItemStack) recipe.getInput()));
		input = builder.build();
		outputs = recipe.getOutput();
	}

	@Override
	public void getIngredients(@Nonnull IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, input);
		ingredients.setOutput(VanillaTypes.ITEM, outputs);
	}

}
