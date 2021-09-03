package com.meteor.extrabotany.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.meteor.extrabotany.common.items.brew.ModBrew;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.common.crafting.ModRecipeTypes;

import javax.annotation.Nullable;
import java.util.function.Consumer;

import static com.meteor.extrabotany.common.items.ModItems.prefix;

public class BrewProvider extends RecipeProvider {

    public BrewProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    public String getName() {
        return "ExtraBotany Brew recipes";
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        consumer.accept(new FinishedRecipe(idFor("allmighty"), ModBrew.allmighty, Ingredient.fromItems(Items.NETHER_WART), Ingredient.fromItems(Items.GOLDEN_CARROT), Ingredient.fromItems(Items.GHAST_TEAR), Ingredient.fromItems(Items.GLOWSTONE_DUST)));
        consumer.accept(new FinishedRecipe(idFor("shell"), ModBrew.shell, Ingredient.fromItems(Items.NETHER_WART), Ingredient.fromItems(Items.GOLDEN_APPLE), Ingredient.fromItems(Items.SCUTE), Ingredient.fromItems(Items.OBSIDIAN)));
        consumer.accept(new FinishedRecipe(idFor("revolution"), ModBrew.revolution, Ingredient.fromItems(Items.NETHER_WART), Ingredient.fromItems(Items.SUGAR), Ingredient.fromItems(Items.IRON_PICKAXE)));
        consumer.accept(new FinishedRecipe(idFor("deadpool"), ModBrew.deadpool, Ingredient.fromItems(Items.NETHER_WART), Ingredient.fromItems(Items.ROTTEN_FLESH), Ingredient.fromItems(Items.BONE), Ingredient.fromItems(Items.BLAZE_POWDER)));
        consumer.accept(new FinishedRecipe(idFor("floating"), ModBrew.floating, Ingredient.fromItems(Items.NETHER_WART), Ingredient.fromItems(Items.SUGAR), Ingredient.fromItems(Items.CHORUS_FRUIT)));
    }

    private static ResourceLocation idFor(String s) {
        return prefix("brew/" + s);
    }

    private static class FinishedRecipe implements IFinishedRecipe {
        private final ResourceLocation id;
        private final Brew brew;
        private final Ingredient[] inputs;

        private FinishedRecipe(ResourceLocation id, Brew brew, Ingredient... inputs) {
            this.id = id;
            this.brew = brew;
            this.inputs = inputs;
        }

        @Override
        public void serialize(JsonObject json) {
            json.addProperty("brew", BotaniaAPI.instance().getBrewRegistry().getKey(brew).toString());
            JsonArray ingredients = new JsonArray();
            for (Ingredient ingr : inputs) {
                ingredients.add(ingr.serialize());
            }
            json.add("ingredients", ingredients);
        }

        @Override
        public ResourceLocation getID() {
            return id;
        }

        @Override
        public IRecipeSerializer<?> getSerializer() {
            return ModRecipeTypes.BREW_SERIALIZER;
        }

        @Nullable
        @Override
        public JsonObject getAdvancementJson() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementID() {
            return null;
        }
    }

}
