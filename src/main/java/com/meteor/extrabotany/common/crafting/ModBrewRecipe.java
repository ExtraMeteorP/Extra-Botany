package com.meteor.extrabotany.common.crafting;

import com.meteor.extrabotany.common.brew.ModBrew;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeBrew;

public class ModBrewRecipe {

    public static RecipeBrew oneforall;
    public static RecipeBrew deadpool;
    public static RecipeBrew revolution;
    public static RecipeBrew shell;
    public static RecipeBrew floating;

    public static void init() {
        oneforall = BotaniaAPI.registerBrewRecipe(ModBrew.oneforall, new ItemStack(Items.NETHER_WART), new ItemStack(Items.GOLDEN_CARROT), new ItemStack(Items.GHAST_TEAR), new ItemStack(Items.GLOWSTONE_DUST));
        deadpool = BotaniaAPI.registerBrewRecipe(ModBrew.deadpool, new ItemStack(Items.NETHER_WART), new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.BONE), new ItemStack(Items.BLAZE_POWDER));
        revolution = BotaniaAPI.registerBrewRecipe(ModBrew.revolution, new ItemStack(Items.NETHER_WART), new ItemStack(Items.IRON_PICKAXE), new ItemStack(Items.SUGAR));
        shell = BotaniaAPI.registerBrewRecipe(ModBrew.shell, new ItemStack(Items.NETHER_WART), new ItemStack(Items.GOLDEN_APPLE), new ItemStack(Items.BUCKET), new ItemStack(Blocks.OBSIDIAN));
        floating = BotaniaAPI.registerBrewRecipe(ModBrew.floating, new ItemStack(Items.NETHER_WART), new ItemStack(Items.CHORUS_FRUIT), new ItemStack(Items.SUGAR));
    }

}
