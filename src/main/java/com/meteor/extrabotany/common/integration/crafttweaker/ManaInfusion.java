package com.meteor.extrabotany.common.integration.crafttweaker;

import com.blamejared.mtlib.helpers.LogHelper;
import com.blamejared.mtlib.utils.BaseListAddition;
import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.lib.Reference;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeManaInfusion;

import static com.blamejared.mtlib.helpers.InputHelper.toObject;
import static com.blamejared.mtlib.helpers.InputHelper.toStack;

@ZenClass("mods.extrabotany.ManaInfusion")
@ModOnly(Reference.MOD_ID)
@ZenRegister
public class ManaInfusion {
    protected static final String name = "ExtraBotany Mana Infusion";

    @ZenMethod
    public static void addDimension(IItemStack output, IIngredient input, int mana) {
        RecipeManaInfusion recipe = new RecipeManaInfusion(toStack(output), toObject(input), mana);
        recipe.setCatalyst(ExtraBotanyAPI.dimensionState);
        ExtraBotany.LATE_ADDITIONS.add(new Add(recipe));
    }

    private static class Add extends BaseListAddition<RecipeManaInfusion> {

        public Add(RecipeManaInfusion recipe) {
            super(ManaInfusion.name, BotaniaAPI.manaInfusionRecipes);
            recipes.add(recipe);
        }

        @Override
        public String getRecipeInfo(RecipeManaInfusion recipe) {
            return LogHelper.getStackDescription(recipe.getOutput());
        }
    }

    // remove: use mods.botania.ManaInfusion.remove(IIngreduent output);
}