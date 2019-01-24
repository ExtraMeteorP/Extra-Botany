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
		addStonesiaRecipe(305, "oreCoal");
		addStonesiaRecipe(370, "oreIron");
		addStonesiaRecipe(750, "oreDiamond");
		addStonesiaRecipe(330, "oreRedstone");
		addStonesiaRecipe(290, "oreLapis");
		addStonesiaRecipe(580, "oreGold");
		addStonesiaRecipe(690, "oreEmerald");
		addStonesiaRecipe(320, "oreCopper");
		addStonesiaRecipe(320, "oreTin");
		addStonesiaRecipe(390, "oreAluminum");
		addStonesiaRecipe(390, "oreLead");
		addStonesiaRecipe(470, "oreAmber");
		addStonesiaRecipe(470, "oreCinnabar");
		addStonesiaRecipe(470, "oreCertusQuartz");
		addStonesiaRecipe(1300, "oreMithril");
		addStonesiaRecipe(480, "oreNickel");
		addStonesiaRecipe(580, "oreSilver");
		addStonesiaRecipe(290, "oreSulfur");
		addStonesiaRecipe(580, "oreUranium");
		addStonesiaRecipe(340, "oreZinc");
		addStonesiaRecipe(340, "oreQuartz");
		addStonesiaRecipe(540, "oreCobalt");
		addStonesiaRecipe(540, "oreArdite");
		addStonesiaRecipe(580, "oreTungsten");
		addStonesiaRecipe(450, "oreOsmium");		
	}
	
	public static void addStonesiaRecipe(int i, Object o){
		ExtraBotanyAPI.registerStonesiaRecipe(i, o);
	}
	
	public static void initOmnivioletRecipe(){
		ExtraBotanyAPI.registerOmnivioletRecipe(ConfigHandler.BOOK_BURNTIME, new ItemStack(Items.BOOK));
		ExtraBotanyAPI.registerOmnivioletRecipe(ConfigHandler.WRITTENBOOK_BURNTIME, new ItemStack(Items.WRITTEN_BOOK));
	}

}
