package com.meteor.extrabotany.data.recipes;

import com.google.gson.JsonObject;
import com.meteor.extrabotany.common.items.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import vazkii.botania.api.recipe.StateIngredient;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.crafting.ModRecipeTypes;
import vazkii.botania.common.crafting.StateIngredientHelper;

import javax.annotation.Nullable;
import java.util.function.Consumer;

import static com.meteor.extrabotany.common.items.ModItems.prefix;

public class ManaInfusionProvider extends RecipeProvider {

    public ManaInfusionProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    public String getName() {
        return "ExtraBotany mana pool recipes";
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        consumer.accept(FinishedRecipe.dimension(id("enderpearl"), new ItemStack(Items.ENDER_PEARL), ingr(Items.DIAMOND), 20000));
        consumer.accept(FinishedRecipe.dimension(id("shulker_shell"), new ItemStack(Items.SHULKER_SHELL), ingr(Items.DIAMOND_HORSE_ARMOR), 20000));
        consumer.accept(FinishedRecipe.dimension(id("chorus_fruit"), new ItemStack(Items.CHORUS_FRUIT), ingr(Items.APPLE), 500));
        consumer.accept(FinishedRecipe.dimension(id("end_stone"), new ItemStack(Items.END_STONE), ingr(Items.STONE), 500));
        consumer.accept(FinishedRecipe.dimension(id("nether_rack"), new ItemStack(Items.NETHERRACK), ingr(Items.COBBLESTONE), 500));
        consumer.accept(FinishedRecipe.dimension(id("soul_sand"), new ItemStack(Items.SOUL_SAND), ingr(Items.SAND), 500));
        consumer.accept(FinishedRecipe.dimension(id("quartz_ore"), new ItemStack(Items.NETHER_QUARTZ_ORE), ingr(Items.IRON_ORE), 2000));
        consumer.accept(FinishedRecipe.dimension(id("blaze_rod"), new ItemStack(Items.BLAZE_ROD, 2), ingr(Items.BLAZE_ROD), 20000));
        consumer.accept(FinishedRecipe.dimension(id("totem_of_undying"), new ItemStack(Items.TOTEM_OF_UNDYING), ingr(Items.NETHER_STAR), 50000));
        consumer.accept(FinishedRecipe.dimension(id("elytra"), new ItemStack(Items.ELYTRA), ingr(ModItems.theorigin), 50000));
    }

    private static ResourceLocation id(String s) {
        return prefix("mana_infusion/" + s);
    }

    private static Ingredient ingr(IItemProvider i) {
        return Ingredient.fromItems(i);
    }

    private static class FinishedRecipe implements IFinishedRecipe {
        private static final StateIngredient CONJURATION = StateIngredientHelper.of(ModBlocks.conjurationCatalyst);
        private static final StateIngredient ALCHEMY = StateIngredientHelper.of(ModBlocks.alchemyCatalyst);
        private static final StateIngredient DIMENSION = StateIngredientHelper.of(com.meteor.extrabotany.common.blocks.ModBlocks.dimensioncatalyst);

        private final ResourceLocation id;
        private final Ingredient input;
        private final ItemStack output;
        private final int mana;
        private final String group;
        @Nullable
        private final StateIngredient catalyst;

        public static FinishedRecipe conjuration(ResourceLocation id, ItemStack output, Ingredient input, int mana) {
            return new FinishedRecipe(id, output, input, mana, "", CONJURATION);
        }

        public static FinishedRecipe dimension(ResourceLocation id, ItemStack output, Ingredient input, int mana) {
            return new FinishedRecipe(id, output, input, mana, "", DIMENSION);
        }

        public static FinishedRecipe alchemy(ResourceLocation id, ItemStack output, Ingredient input, int mana) {
            return alchemy(id, output, input, mana, "");
        }

        public static FinishedRecipe alchemy(ResourceLocation id, ItemStack output, Ingredient input, int mana, String group) {
            return new FinishedRecipe(id, output, input, mana, group, ALCHEMY);
        }

        public FinishedRecipe(ResourceLocation id, ItemStack output, Ingredient input, int mana) {
            this(id, output, input, mana, "");
        }

        public FinishedRecipe(ResourceLocation id, ItemStack output, Ingredient input, int mana, String group) {
            this(id, output, input, mana, group, null);
        }

        public FinishedRecipe(ResourceLocation id, ItemStack output, Ingredient input, int mana, String group, @Nullable StateIngredient catalyst) {
            this.id = id;
            this.input = input;
            this.output = output;
            this.mana = mana;
            this.group = group;
            this.catalyst = catalyst;
        }

        @Override
        public void serialize(JsonObject json) {
            json.add("input", input.serialize());
            json.add("output", ItemNBTHelper.serializeStack(output));
            json.addProperty("mana", mana);
            if (!group.isEmpty()) {
                json.addProperty("group", group);
            }
            if (catalyst != null) {
                json.add("catalyst", catalyst.serialize());
            }
        }

        @Override
        public ResourceLocation getID() {
            return id;
        }

        @Override
        public IRecipeSerializer<?> getSerializer() {
            return ModRecipeTypes.MANA_INFUSION_SERIALIZER;
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
