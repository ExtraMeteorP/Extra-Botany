package com.meteor.extrabotany.common.crafting;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.item.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.recipe.RecipeManaInfusion;

public class ModManaDimensionRecipe {
	
	public static RecipeManaInfusion enderPearlRecipe;
	public static RecipeManaInfusion chorusRecipe;
	public static RecipeManaInfusion endStoneRecipe;
	public static RecipeManaInfusion shulkerShellRecipe;
	
	public static RecipeManaInfusion netherRackRecipe;
	public static RecipeManaInfusion quartzRecipe;
	public static RecipeManaInfusion soulSandRecipe;
	public static RecipeManaInfusion blazeRodRecipe;
	
	public static RecipeManaInfusion totemRecipe;
	public static RecipeManaInfusion elytraRecipe;
	
	private static int tier1 = 500;
	private static int tier3 = 20000;
	private static int tier4 = 50000;
	
	public static void init() {
		enderPearlRecipe = ExtraBotanyAPI.registerManaDimensionRecipe(new ItemStack(Items.ENDER_PEARL), "gemDiamond", tier3);
		shulkerShellRecipe = ExtraBotanyAPI.registerManaDimensionRecipe(new ItemStack(Items.SHULKER_SHELL), new ItemStack(Items.DIAMOND_HORSE_ARMOR), tier3);
		chorusRecipe = ExtraBotanyAPI.registerManaDimensionRecipe(new ItemStack(Items.CHORUS_FRUIT), new ItemStack(Items.APPLE), tier1);
		endStoneRecipe = ExtraBotanyAPI.registerManaDimensionRecipe(new ItemStack(Blocks.END_STONE), "stone", tier1);
		
		netherRackRecipe = ExtraBotanyAPI.registerManaDimensionRecipe(new ItemStack(Blocks.NETHERRACK), "cobblestone", tier1);
		soulSandRecipe = ExtraBotanyAPI.registerManaDimensionRecipe(new ItemStack(Blocks.SOUL_SAND), "sand", tier1);
		quartzRecipe = ExtraBotanyAPI.registerManaDimensionRecipe(new ItemStack(Blocks.QUARTZ_ORE), "oreIron", tier1);
		blazeRodRecipe = ExtraBotanyAPI.registerManaDimensionRecipe(new ItemStack(Items.BLAZE_ROD, 2), "rodBlaze", tier3);
		
		totemRecipe = ExtraBotanyAPI.registerManaDimensionRecipe(new ItemStack(Items.TOTEM_OF_UNDYING), new ItemStack(Items.NETHER_STAR), tier4);
		elytraRecipe = ExtraBotanyAPI.registerManaDimensionRecipe(new ItemStack(Items.ELYTRA), new ItemStack(ModItems.flyingboat, 1 ,1), tier4);
	}

}
