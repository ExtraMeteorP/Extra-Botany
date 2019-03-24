package com.meteor.extrabotany.client.integration.jei.pedestal;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.lib.LibMisc;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class HammerRecipeCategory implements IRecipeCategory {

	public static final String UID = "extrabotany.pedestal";
	private final String localizedName;
	private final IDrawable background;
	private final IDrawable overlay;
	private final ItemStack renderStack = new ItemStack(ModItems.hammermanasteel);
	
	public HammerRecipeCategory(IGuiHelper guiHelper) {
		localizedName = I18n.format("extrabotany.nei.pedestal");
		background = guiHelper.createBlankDrawable(145, 95);
		overlay = guiHelper.createDrawable(new ResourceLocation("botania", "textures/gui/pureDaisyOverlay.png"), 0, 0, 64, 46);
	}

	@Nonnull
	@Override
	public String getUid() {
		return UID;
	}

	@Nonnull
	@Override
	public String getTitle() {
		return localizedName;
	}

	@Nonnull
	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void drawExtras(@Nonnull Minecraft minecraft) {
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		overlay.draw(minecraft, 48, 0);
		GlStateManager.disableBlend();
		GlStateManager.disableAlpha();
	}

	@Override
	public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper, @Nonnull IIngredients ingredients) {
		if(!(recipeWrapper instanceof HammerRecipeWrapper))
			return;

		int index = 0;

		recipeLayout.getItemStacks().init(index, true, 40, 12);
		recipeLayout.getItemStacks().set(index, ingredients.getInputs(ItemStack.class).get(0));

		index++;

		if(ingredients.getInputs(ItemStack.class).size() > 1) {
			// Has catalyst
			recipeLayout.getItemStacks().init(index, true, 20, 12);
			recipeLayout.getItemStacks().set(index, ingredients.getInputs(ItemStack.class).get(1));
			index++;
		}

		recipeLayout.getItemStacks().init(index, true, 70, 12);
		recipeLayout.getItemStacks().set(index, renderStack);
		index++;

		recipeLayout.getItemStacks().init(index, false, 99, 12);
		recipeLayout.getItemStacks().set(index, ingredients.getOutputs(ItemStack.class).get(0));
	}

	@Nonnull
	@Override
	public String getModName() {
		return LibMisc.MOD_ID;
	}
}
