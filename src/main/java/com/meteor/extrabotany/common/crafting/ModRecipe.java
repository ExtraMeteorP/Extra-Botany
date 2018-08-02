package com.meteor.extrabotany.common.crafting;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.core.handler.ConfigHandler;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModRecipe {
	
	public static void init() {
		ModPetalRecipe.init();
		ModManaInfusionRecipe.init();
		ModCraftingRecipe.init();
		ModRuneRecipe.init();
		ModPedestalRecipe.init();
		initStonesiaRecipe();
		initOmnivioletRecipe();
	}
	
	public static void initStonesiaRecipe(){
		ExtraBotanyAPI.registerStonesiaRecipe(10, new ItemStack(Blocks.STONE));
		ExtraBotanyAPI.registerStonesiaRecipe(5, new ItemStack(Blocks.COBBLESTONE));
		ExtraBotanyAPI.registerStonesiaRecipe(330, new ItemStack(Blocks.COAL_ORE));
		ExtraBotanyAPI.registerStonesiaRecipe(390, new ItemStack(Blocks.IRON_ORE));
		ExtraBotanyAPI.registerStonesiaRecipe(800, new ItemStack(Blocks.DIAMOND_ORE));
		ExtraBotanyAPI.registerStonesiaRecipe(350, new ItemStack(Blocks.REDSTONE_ORE));
		ExtraBotanyAPI.registerStonesiaRecipe(310, new ItemStack(Blocks.LAPIS_ORE));
		ExtraBotanyAPI.registerStonesiaRecipe(600, new ItemStack(Blocks.GOLD_ORE));
		ExtraBotanyAPI.registerStonesiaRecipe(700, new ItemStack(Blocks.EMERALD_ORE));
	}
	
	public static void initOmnivioletRecipe(){
		ExtraBotanyAPI.registerOmnivioletRecipe(ConfigHandler.BOOK_BURNTIME, new ItemStack(Items.BOOK));
		ExtraBotanyAPI.registerOmnivioletRecipe(ConfigHandler.WRITTENBOOK_BURNTIME, new ItemStack(Items.WRITTEN_BOOK));
	}

}
