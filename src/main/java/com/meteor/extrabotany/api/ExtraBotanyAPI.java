package com.meteor.extrabotany.api;

import java.util.ArrayList;
import java.util.List;

import com.meteor.extrabotany.common.crafting.recipe.RecipeOmniviolet;
import com.meteor.extrabotany.common.crafting.recipe.RecipePedestal;
import com.meteor.extrabotany.common.crafting.recipe.RecipeStonesia;
import com.meteor.extrabotany.common.lib.LibAdvancements;
import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.EnumHelper;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.KnowledgeType;

public class ExtraBotanyAPI {
	
	public static final KnowledgeType dreamKnowledge;
	
	public static final List<RecipePedestal> pedestalRecipes = new ArrayList<RecipePedestal>();
	public static final List<RecipeStonesia> stonesiaRecipes = new ArrayList<RecipeStonesia>();
	public static final List<RecipeOmniviolet> omnivioletRecipes = new ArrayList<RecipeOmniviolet>();
	public static final ArmorMaterial orichalcosArmorMaterial = EnumHelper.addArmorMaterial("ORICHALCOS", "orichalcos", 50,
			new int[] { 4, 7, 9, 4 }, 40, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 4F);
	public static final ArmorMaterial shadowiumArmorMaterial = EnumHelper.addArmorMaterial("SHADOWIUM", "shadowium", 12,
			new int[] { 3, 6, 5, 2 }, 40, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 3F);
	public static final ToolMaterial shadowiumToolMaterial = EnumHelper.addToolMaterial("SHADOWIUM", 3, 800, 6.5F, 2F, 12);
	
	static {
    	dreamKnowledge = BotaniaAPI.registerKnowledgeType("dream", TextFormatting.DARK_RED, false);
	}
	
	public static RecipePedestal registerPedestalRecipe(ItemStack output, ItemStack input){
		RecipePedestal recipe = new RecipePedestal(output, input);
		pedestalRecipes.add(recipe);
		return recipe;
	}

	public static RecipeStonesia registerStonesiaRecipe(int output, Object input){
		RecipeStonesia recipe = new RecipeStonesia(output, input);
		stonesiaRecipes.add(recipe);
		return recipe;
	}
	
	public static RecipeOmniviolet registerOmnivioletRecipe(int output, ItemStack input){
		RecipeOmniviolet recipe = new RecipeOmniviolet(output, input);
		omnivioletRecipes.add(recipe);
		return recipe;
	}
	
	public static void unlockAdvancement(EntityPlayer player, String name){
		if(player instanceof EntityPlayerMP){
			PlayerAdvancements advancements = ((EntityPlayerMP)player).getAdvancements();
			AdvancementManager manager = ((WorldServer)player.getEntityWorld()).getAdvancementManager();
			Advancement advancement = manager.getAdvancement(new ResourceLocation(LibMisc.MOD_ID, LibAdvancements.PREFIX+name));
			if(advancement!=null)
				advancements.grantCriterion(advancement, "ebt_trigger");		
		}
	}
	
	public static void dealTrueDamage(EntityLivingBase living, float amount){
		if(living.getHealth() > 0)
			if(living.getHealth() <= amount){
				living.setHealth(1F);
				living.attackEntityFrom(DamageSource.MAGIC, Integer.MAX_VALUE);
			}else living.setHealth(living.getHealth() - amount);
	}
	
}
