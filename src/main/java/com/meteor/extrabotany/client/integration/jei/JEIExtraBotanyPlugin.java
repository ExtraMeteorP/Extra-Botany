package com.meteor.extrabotany.client.integration.jei;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.client.integration.jei.pedestal.HammerRecipeCategory;
import com.meteor.extrabotany.client.integration.jei.pedestal.HammerRecipeWrapper;
import com.meteor.extrabotany.common.block.ModBlocks;
import com.meteor.extrabotany.common.crafting.recipe.RecipePedestal;
import com.meteor.extrabotany.common.item.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;

@JEIPlugin
public class JEIExtraBotanyPlugin implements IModPlugin{

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(
				new HammerRecipeCategory(registry.getJeiHelpers().getGuiHelper())
			);
	}

	public static boolean doesOreExist(String key) {
		return OreDictionary.doesOreNameExist(key)
				&& OreDictionary.getOres(key).stream()
				.anyMatch(s -> s.getItem() instanceof ItemBlock);
	}

	@Override
	public void register(@Nonnull IModRegistry registry) {
		registry.handleRecipes(RecipePedestal.class, HammerRecipeWrapper::new, HammerRecipeCategory.UID);
		registry.addRecipes(ExtraBotanyAPI.pedestalRecipes, HammerRecipeCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.pedestal), HammerRecipeCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModItems.hammerelementium), HammerRecipeCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModItems.hammermanasteel), HammerRecipeCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModItems.hammerterrasteel), HammerRecipeCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModItems.hammerultimate), HammerRecipeCategory.UID);
	
	}
	
}
