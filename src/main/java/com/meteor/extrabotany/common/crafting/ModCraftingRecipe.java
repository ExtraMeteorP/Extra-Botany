package com.meteor.extrabotany.common.crafting;

import com.meteor.extrabotany.common.block.ModBlocks;
import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;
import vazkii.botania.common.lib.LibOreDict;

public class ModCraftingRecipe {
	
	public static IRecipe PEDESTAL;
	public static IRecipe TERRASTEEL_HAMMER;
	public static IRecipe MANASTEEL_HAMMER;
	public static IRecipe ELEMENTIUM_HAMMER;
	public static IRecipe MANAREADER;
	public static IRecipe BINDER;
	
	public static void init() {
		
		PEDESTAL = new ShapedOreRecipe(getResource("recipe_pedestal"), new ItemStack(ModBlocks.pedestal, 1), "X#X", " X ", "XXX", '#', new ItemStack(Items.GOLD_NUGGET), 'X', LibOreDict.LIVING_ROCK);
		PEDESTAL.setRegistryName(getResource("recipe_pedestal"));
		
		TERRASTEEL_HAMMER = new ShapedOreRecipe(getResource("recipe_terrasteelhammer"), new ItemStack(ModItems.hammerterrasteel, 1), "XXX", "XXX", " Y ", 'X', LibOreDict.TERRA_STEEL, 'Y', LibOreDict.LIVINGWOOD_TWIG);
		TERRASTEEL_HAMMER.setRegistryName(getResource("recipe_terrasteelhammer"));
		
		ELEMENTIUM_HAMMER = new ShapedOreRecipe(getResource("recipe_elementiumhammer"), new ItemStack(ModItems.hammerelementium, 1), "XXX", "XXX", " Y ", 'X', LibOreDict.ELEMENTIUM, 'Y', LibOreDict.LIVINGWOOD_TWIG);
		ELEMENTIUM_HAMMER.setRegistryName(getResource("recipe_elementiumhammer"));
		
		MANASTEEL_HAMMER = new ShapedOreRecipe(getResource("recipe_manasteelhammer"), new ItemStack(ModItems.hammermanasteel, 1), "XXX", "XXX", " Y ", 'X', LibOreDict.MANA_STEEL, 'Y', LibOreDict.LIVINGWOOD_TWIG);
		MANASTEEL_HAMMER.setRegistryName(getResource("recipe_manasteelhammer"));
		
		MANAREADER = new ShapedOreRecipe(getResource("recipe_manareader"), new ItemStack(ModItems.manaReader, 1), " XY", " ZX", "Z  ", 'X', LibOreDict.MANA_POWDER, 'Y', LibOreDict.MANA_DIAMOND, 'Z', LibOreDict.LIVINGWOOD_TWIG);
		MANAREADER.setRegistryName(getResource("recipe_manareader"));
		
		BINDER = new ShapedOreRecipe(getResource("recipe_binder"), new ItemStack(ModItems.binder, 1), " XY", " ZX", "Z  ", 'X', LibOreDict.MANA_POWDER, 'Y', LibOreDict.DRAGONSTONE, 'Z', LibOreDict.LIVINGWOOD_TWIG);
		BINDER.setRegistryName(getResource("recipe_binder"));
	
	}
	
	@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)
	public static class RegistrationHandlerRecipes {
		@SubscribeEvent
		public static void registerRecipes(final RegistryEvent.Register<IRecipe> event) {
			init();
			final IForgeRegistry<IRecipe> registry = event.getRegistry();
			event.getRegistry().registerAll(
					PEDESTAL,
					TERRASTEEL_HAMMER,
					ELEMENTIUM_HAMMER,
					MANASTEEL_HAMMER,
					MANAREADER
			);
		}
	}
	
	private static ResourceLocation getResource(String inName) {
		return new ResourceLocation(LibMisc.MOD_ID, inName);
	}

}
