package com.meteor.extrabotany.common.integration.crafttweaker;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.lexicon.LexiconPage;
import vazkii.botania.api.recipe.RecipeManaInfusion;
import vazkii.botania.common.lexicon.page.PageManaInfusionRecipe;

import java.util.ArrayList;
import java.util.List;

import static com.blamejared.mtlib.helpers.InputHelper.toObject;
import static com.blamejared.mtlib.helpers.InputHelper.toStack;

// This is a modified code from ModTweaker/ com.blamejared.compat.botania.lexicon.pages.AddPageConjuration and com.blamejared.compat.botania.BotaniaHelper.
// Credit to Copyright (c) 2016 Jaredlll08 for the code for this class.

public class AddPageDimension implements IAction {

    private String name;
    private String entry;
    private int page_number;
    private IItemStack[] outputs;
    private IIngredient[] inputs;
    private int[] mana;


    public AddPageDimension(String name, String entry, int page_number, IItemStack[] outputs, IIngredient[] inputs, int[] mana) {
        this.name = name;
        this.entry = entry;
        this.page_number = page_number;
        this.outputs = outputs;
        this.inputs = inputs;
        this.mana = mana;
    }

    @Override
    public void apply() {
        LexiconEntry lexiconEntry = findEntry(entry);
        if(lexiconEntry == null) {
            CraftTweakerAPI.getLogger().logError("Cannot find lexicon entry " + entry);
            return;
        }
        if(page_number > lexiconEntry.pages.size()) {
            CraftTweakerAPI.getLogger().logError("Page Number " + page_number + " out of bounds for " + entry);
            return;
        }
        if(outputs.length != inputs.length || outputs.length != mana.length) {
            CraftTweakerAPI.getLogger().logError("Length of input and output must match");
            return;
        }
        List<RecipeManaInfusion> recipes = new ArrayList<>();
        for(int i = 0; i < outputs.length; i++) {
            RecipeManaInfusion current_recipe = new RecipeManaInfusion(toStack(outputs[i]), toObject(inputs[i]), mana[i]);
            current_recipe.setCatalyst(ExtraBotanyAPI.dimensionState);
            recipes.add(current_recipe);
        }
        LexiconPage page = new PageManaInfusionRecipe(name, recipes);
        lexiconEntry.pages.add(page_number, page);
    }

    @Override
    public String describe() {
        return "Adding Lexicon Page: " + name;
    }

    private static LexiconEntry findEntry(String name) {
        List<LexiconEntry> entries = BotaniaAPI.getAllEntries();
        for(LexiconEntry entry : entries) {
            if(entry.getUnlocalizedName().equalsIgnoreCase(name))
                return entry;
        }
        return null;
    }
}