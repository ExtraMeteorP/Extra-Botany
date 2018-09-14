package com.meteor.extrabotany.common.crafting;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.core.config.ConfigHandler;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModRecipe {
	
	public static void init() {
		ModPetalRecipe.init();
		ModManaInfusionRecipe.init();
		ModCraftingRecipe.init();
		ModRuneRecipe.init();
		ModPedestalRecipe.init();
		ModBrewRecipe.init();
		initStonesiaRecipe();
		initOmnivioletRecipe();
	}
	
	public static void initStonesiaRecipe(){
		addStonesiaRecipe(10, "stone");
		addStonesiaRecipe(5, "cobblestone");
		addStonesiaRecipe(290, "oreCoal");
		addStonesiaRecipe(350, "oreIron");
		addStonesiaRecipe(730, "oreDiamond");
		addStonesiaRecipe(310, "oreRedstone");
		addStonesiaRecipe(270, "oreLapis");
		addStonesiaRecipe(550, "oreGold");
		addStonesiaRecipe(670, "oreEmerald");
		addStonesiaRecipe(300, "oreCopper");
		addStonesiaRecipe(300, "oreTin");
		addStonesiaRecipe(380, "oreAluminum");
		addStonesiaRecipe(380, "oreLead");
		addStonesiaRecipe(450, "oreAmber");
		addStonesiaRecipe(450, "oreCinnabar");
		addStonesiaRecipe(450, "oreCertusQuartz");
		addStonesiaRecipe(1200, "oreMithril");
		addStonesiaRecipe(450, "oreNickel");
		addStonesiaRecipe(550, "oreSilver");
		addStonesiaRecipe(290, "oreSulfur");
		addStonesiaRecipe(550, "oreUranium");
		addStonesiaRecipe(320, "oreZinc");
		addStonesiaRecipe(320, "oreQuartz");
		addStonesiaRecipe(520, "oreCobalt");
		addStonesiaRecipe(520, "oreArdite");
		addStonesiaRecipe(500, "oreTungsten");
		addStonesiaRecipe(400, "oreOsmium");		
	}
	
	public static void addStonesiaRecipe(int i, Object o){
		ExtraBotanyAPI.registerStonesiaRecipe(i, o);
	}
	
	public static void initOmnivioletRecipe(){
		ExtraBotanyAPI.registerOmnivioletRecipe(ConfigHandler.BOOK_BURNTIME, new ItemStack(Items.BOOK));
		ExtraBotanyAPI.registerOmnivioletRecipe(ConfigHandler.WRITTENBOOK_BURNTIME, new ItemStack(Items.WRITTEN_BOOK));
	}

}
