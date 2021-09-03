package com.meteor.extrabotany.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.meteor.extrabotany.common.items.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.crafting.ModRecipeTypes;

import javax.annotation.Nullable;
import java.util.function.Consumer;

import static com.meteor.extrabotany.common.items.ModItems.prefix;

public class RuneRecipeProvider extends RecipeProvider {

    public RuneRecipeProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    public String getName() {
        return "ExtraBotany runic altar recipes";
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        consumer.accept(new FinishedRecipe(idFor("firstfractal"), new ItemStack(ModItems.firstfractal), 500000
                , Ingredient.fromItems(ModItems.trueshadowkatana), Ingredient.fromItems(ModItems.trueterrablade)
                , Ingredient.fromItems(ModItems.influxwaver), Ingredient.fromItems(ModItems.starwrath)
                , Ingredient.fromItems(ModItems.excaliber), Ingredient.fromItems(Items.WOODEN_SWORD)
                , Ingredient.fromItems(Items.DIAMOND_SWORD), Ingredient.fromItems(Items.NETHERITE_SWORD)
                , Ingredient.fromItems(vazkii.botania.common.item.ModItems.manasteelSword), Ingredient.fromItems(vazkii.botania.common.item.ModItems.elementiumSword)
                , Ingredient.fromItems(ModItems.gildedmashedpotato), Ingredient.fromItems(ModItems.theuniverse)));

        consumer.accept(new FinishedRecipe(idFor("gildedpotato"), new ItemStack(ModItems.gildedpotato, 4), 2000
                , Ingredient.fromItems(Items.POTATO), Ingredient.fromItems(Items.POTATO)
                , Ingredient.fromItems(Items.POTATO), Ingredient.fromItems(Items.POTATO)
                , Ingredient.fromTag(Tags.Items.INGOTS_GOLD)));
    }

    private static ResourceLocation idFor(String s) {
        return prefix("runic_altar/" + s);
    }

    private static class FinishedRecipe implements IFinishedRecipe {
        private final ResourceLocation id;
        private final ItemStack output;
        private final int mana;
        private final Ingredient[] inputs;

        private FinishedRecipe(ResourceLocation id, ItemStack output, int mana, Ingredient... inputs) {
            this.id = id;
            this.output = output;
            this.mana = mana;
            this.inputs = inputs;
        }

        @Override
        public void serialize(JsonObject json) {
            json.add("output", ItemNBTHelper.serializeStack(output));
            JsonArray ingredients = new JsonArray();
            for (Ingredient ingr : inputs) {
                ingredients.add(ingr.serialize());
            }
            json.addProperty("mana", mana);
            json.add("ingredients", ingredients);
        }

        @Override
        public ResourceLocation getID() {
            return id;
        }

        @Override
        public IRecipeSerializer<?> getSerializer() {
            return ModRecipeTypes.RUNE_SERIALIZER;
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
