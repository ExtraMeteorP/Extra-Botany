package com.meteor.extrabotany.data.recipes;

import com.meteor.extrabotany.common.blocks.ModBlocks;
import com.meteor.extrabotany.common.crafting.recipe.RelicUpgradeRecipe;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
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

        ShapedRecipeBuilder.shapedRecipe(emptybottle, 3)
                .key('M', vazkii.botania.common.block.ModBlocks.manaGlass)
                .patternLine("M M")
                .patternLine("M M")
                .patternLine(" M ")
                .addCriterion("has_item", hasItem(Items.GLASS))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(challengeticket)
                .addIngredient(thechaos)
                .addIngredient(ModItems.gaiaIngot)
                .addCriterion("has_item", hasItem(ModItems.gaiaIngot))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(gildedmashedpotato)
                .addIngredient(gildedpotato)
                .addIngredient(Items.BOWL)
                .addIngredient(Items.SUGAR)
                .addCriterion("has_item", hasItem(gildedpotato))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(manareader)
                .key('L', ModItems.livingwoodTwig)
                .key('M', ModTags.Items.GEMS_MANA_DIAMOND)
                .key('P', ModItems.manaPowder)
                .patternLine(" PM")
                .patternLine(" LP")
                .patternLine("L  ")
                .addCriterion("has_item", hasItem(ModItems.livingwoodTwig))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(universalpetal, 8)
                .key('L', ModItems.lifeEssence)
                .key('P', ModTags.Items.PETALS)
                .patternLine("PPP")
                .patternLine("PLP")
                .patternLine("PPP")
                .addCriterion("has_item", hasItem(ModItems.lifeEssence))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(elementrune, 8)
                .addIngredient(ModItems.lifeEssence)
                .addIngredient(ModTags.Items.RUNES_AIR)
                .addIngredient(ModTags.Items.RUNES_EARTH)
                .addIngredient(ModTags.Items.RUNES_WATER)
                .addIngredient(ModTags.Items.RUNES_FIRE)
                .addIngredient(ModTags.Items.RUNES_SPRING)
                .addIngredient(ModTags.Items.RUNES_SUMMER)
                .addIngredient(ModTags.Items.RUNES_AUTUMN)
                .addIngredient(ModTags.Items.RUNES_WINTER)
                .addCriterion("has_item", hasItem(ModItems.lifeEssence))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(sinrune, 8)
                .addIngredient(ModItems.lifeEssence)
                .addIngredient(ModTags.Items.RUNES_MANA)
                .addIngredient(ModTags.Items.RUNES_PRIDE)
                .addIngredient(ModTags.Items.RUNES_GLUTTONY)
                .addIngredient(ModTags.Items.RUNES_WRATH)
                .addIngredient(ModTags.Items.RUNES_GREED)
                .addIngredient(ModTags.Items.RUNES_ENVY)
                .addIngredient(ModTags.Items.RUNES_LUST)
                .addIngredient(ModTags.Items.RUNES_SLOTH)
                .addCriterion("has_item", hasItem(ModItems.lifeEssence))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(goldcloth, 4)
                .key('G', ModItems.lifeEssence)
                .key('I', Tags.Items.INGOTS_GOLD)
                .key('M', ModItems.manaweaveCloth)
                .patternLine("GMG")
                .patternLine("MIM")
                .patternLine("GMG")
                .addCriterion("has_item", hasItem(ModItems.lifeEssence))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(rodofdiscord)
                .key('T', ModItems.livingwoodTwig)
                .key('P', ModItems.pixieDust)
                .key('C', thechaos)
                .patternLine(" PC")
                .patternLine(" TP")
                .patternLine("T  ")
                .addCriterion("has_item", hasItem(thechaos))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.dimensioncatalyst)
                .key('L', ModTags.Items.LIVINGROCK)
                .key('E', Items.ENDER_PEARL)
                .key('N', nightmarefuel)
                .key('A', vazkii.botania.common.block.ModBlocks.alchemyCatalyst)
                .patternLine("LEL")
                .patternLine("NAN")
                .patternLine("LNL")
                .addCriterion("has_item", hasItem(nightmarefuel))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(natureorb)
                .key('D', ModTags.Items.GEMS_DRAGONSTONE)
                .key('T', ModTags.Items.INGOTS_TERRASTEEL)
                .key('M', ModItems.manaPearl)
                .patternLine("TDT")
                .patternLine("DMD")
                .patternLine("TDT")
                .addCriterion("has_item", hasItem(ModItems.terrasteel))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(silverbullet)
                .key('P', photonium)
                .key('C', thechaos)
                .key('M', ModItems.manaGun)
                .key('S', ModTags.Items.INGOTS_MANASTEEL)
                .patternLine("PPS")
                .patternLine(" MC")
                .patternLine("  P")
                .addCriterion("has_item", hasItem(photonium))
                .build(consumer);
    }
}
