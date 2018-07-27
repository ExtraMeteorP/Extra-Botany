package com.meteor.extrabotany.api;

import java.util.ArrayList;
import java.util.List;

import com.meteor.extrabotany.common.crafting.recipe.RecipePedestal;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.KnowledgeType;

public class ExtraBotanyAPI {
	
	public static final KnowledgeType dreamKnowledge;
	
	public static final List<RecipePedestal> pedestalRecipes = new ArrayList<RecipePedestal>();
	public static final PropertyEnum<PedestalVariant> PEDESTAL_VARIANT = PropertyEnum.create("variant", PedestalVariant.class);
	
	static {
    	dreamKnowledge = BotaniaAPI.registerKnowledgeType("dream", TextFormatting.DARK_RED, false);
	}
	
	public static RecipePedestal registerPedestalRecipe(ItemStack output, ItemStack input){
		RecipePedestal recipe = new RecipePedestal(output, input);
		pedestalRecipes.add(recipe);
		return recipe;
	};
}
