package com.meteor.extrabotany.data.recipes;

import com.meteor.extrabotany.common.blocks.ModBlocks;
import com.meteor.extrabotany.common.crafting.recipe.RelicUpgradeRecipe;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.lib.ModTags;
import vazkii.botania.data.recipes.WrapperResult;

import java.util.function.Consumer;
import static com.meteor.extrabotany.common.items.ModItems.*;

public class RecipeProvider extends net.minecraft.data.RecipeProvider{

    public RecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        registerMain(consumer);
    }

    private void registerMain(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.powerframe)
                .key('M', ModTags.Items.INGOTS_MANASTEEL)
                .key('P', ModItems.pixieDust)
                .key('L', ModTags.Items.LIVINGROCK)
                .patternLine("MMM")
                .patternLine("PLP")
                .patternLine("MMM")
                .addCriterion("has_item", hasItem(ModItems.pixieDust))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.manabuffer)
                .key('K', vazkii.botania.common.block.ModBlocks.fabulousPool)
                .key('M', ModItems.lensNormal)
                .key('G', ModItems.gaiaIngot)
                .patternLine("KMK")
                .patternLine("KGK")
                .patternLine("KMK")
                .addCriterion("has_item", hasItem(ModItems.gaiaIngot))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(lenspotion)
                .addIngredient(ModItems.lensNormal)
                .addIngredient(ModTags.Items.RUNES_SPRING)
                .addIngredient(ModItems.manaPowder)
                .addIngredient(ModTags.Items.GEMS_DRAGONSTONE)
                .addIngredient(ModItems.enderAirBottle)
                .addCriterion("has_item", hasItem(ModItems.lensNormal))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(lenstrace)
                .addIngredient(ModItems.lensNormal)
                .addIngredient(ModTags.Items.RUNES_GREED)
                .addIngredient(ModItems.manaPowder)
                .addCriterion("has_item", hasItem(ModItems.lensNormal))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(lenspush)
                .addIngredient(ModItems.lensNormal)
                .addIngredient(ModTags.Items.RUNES_EARTH)
                .addIngredient(ModItems.manaPowder)
                .addCriterion("has_item", hasItem(ModItems.lensNormal))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(lenssmelt)
                .addIngredient(ModItems.lensNormal)
                .addIngredient(ModTags.Items.RUNES_FIRE)
                .addIngredient(ModItems.manaPowder)
                .addCriterion("has_item", hasItem(ModItems.lensNormal))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(lensmana)
                .addIngredient(ModItems.lensNormal)
                .addIngredient(ModTags.Items.RUNES_MANA)
                .addIngredient(ModItems.manaPowder)
                .addCriterion("has_item", hasItem(ModItems.lensNormal))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(motor)
                .key('A', aerialite)
                .key('P', photonium)
                .key('H', heromedal)
                .patternLine("PAP")
                .patternLine("PHP")
                .patternLine("PAP")
                .addCriterion("has_item", hasItem(heromedal))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(cosmiccarkey)
                .key('M', ModTags.Items.INGOTS_MANASTEEL)
                .key('T', theorigin)
                .key('E', ModItems.enderAirBottle)
                .key('P', Items.ENDER_EYE)
                .patternLine("EPE")
                .patternLine("MTM")
                .patternLine("MMM")
                .addCriterion("has_item", hasItem(theorigin))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(motoraccessory)
                .addIngredient(motor)
                .addCriterion("has_item", hasItem(motor))
                .build(WrapperResult.ofType(RelicUpgradeRecipe.SERIALIZER, consumer), prefix("motor_to_accessory"));

        ShapelessRecipeBuilder.shapelessRecipe(motor)
                .addIngredient(motoraccessory)
                .addCriterion("has_item", hasItem(motor))
                .build(WrapperResult.ofType(RelicUpgradeRecipe.SERIALIZER, consumer), prefix("accessory_to_motor"));

        ShapelessRecipeBuilder.shapelessRecipe(cosmiccarkeyaccessory)
                .addIngredient(cosmiccarkey)
                .addCriterion("has_item", hasItem(cosmiccarkey))
                .build(consumer, prefix("cosmiccarkey_to_accessory"));

        ShapelessRecipeBuilder.shapelessRecipe(cosmiccarkey)
                .addIngredient(cosmiccarkeyaccessory)
                .addCriterion("has_item", hasItem(cosmiccarkey))
                .build(consumer, prefix("accessory_to_cosmiccarkey"));

        ShapelessRecipeBuilder.shapelessRecipe(manadrivering)
                .addIngredient(ModItems.manaRing)
                .addIngredient(ModTags.Items.RUNES_MANA)
                .addCriterion("has_item", hasItem(ModItems.manaRing))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(jingweifeather)
                .key('R', ModItems.redQuartz)
                .key('P', ModItems.pixieDust)
                .key('F', Items.FEATHER)
                .key('I', ModTags.Items.RUNES_FIRE)
                .patternLine("RPR")
                .patternLine("IFI")
                .patternLine("RPR")
                .addCriterion("has_item", hasItem(ModItems.pixieDust))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(walkingcane)
                .key('R', ModTags.Items.LIVINGROCK)
                .key('W', ModItems.livingwoodTwig)
                .key('G', Tags.Items.INGOTS_GOLD)
                .patternLine(" RG")
                .patternLine(" WR")
                .patternLine("W  ")
                .addCriterion("has_item", hasItem(ModItems.livingwoodTwig))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(shadowkatana)
                .key('W', ModItems.livingwoodTwig)
                .key('S', shadowium)
                .patternLine("S")
                .patternLine("S")
                .patternLine("W")
                .addCriterion("has_item", hasItem(shadowium))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(coregod)
                .key('G', ModItems.sunnyQuartz)
                .key('H', heromedal)
                .key('F', ModItems.flightTiara)
                .patternLine("GHG")
                .patternLine("GFG")
                .patternLine("GGG")
                .addCriterion("has_item", hasItem(heromedal))
                .build(consumer);

        Item[] items = { ModItems.lavenderQuartz, ModItems.manaQuartz, ModItems.blazeQuartz};
        for (int i = 0; i < items.length; i++) {
            int tiaraType = i + 1;
            ShapelessRecipeBuilder.shapelessRecipe(coregod)
                    .addIngredient(coregod)
                    .addIngredient(items[i])
                    .setGroup("extrabotany:coregod_wings")
                    .addCriterion("has_item", hasItem(coregod))
                    .build(WrapperResult.ofType(RelicUpgradeRecipe.SERIALIZER, WrapperResult.transformJson(consumer, json -> json.getAsJsonObject("result").addProperty("nbt", "{variant:" + tiaraType + "}")
                    )), "extrabotany:coregod_" + tiaraType);
        }
    }
}
