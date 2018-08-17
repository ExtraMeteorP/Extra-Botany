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
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.KnowledgeType;

public class ExtraBotanyAPI {
	
	public static final KnowledgeType dreamKnowledge;
	
	public static final List<RecipePedestal> pedestalRecipes = new ArrayList<RecipePedestal>();
	public static final List<RecipeStonesia> stonesiaRecipes = new ArrayList<RecipeStonesia>();
	public static final List<RecipeOmniviolet> omnivioletRecipes = new ArrayList<RecipeOmniviolet>();
	public static final PropertyEnum<PedestalVariant> PEDESTAL_VARIANT = PropertyEnum.create("variant", PedestalVariant.class);
	public static final PropertyEnum<ManaBufferVariant> BATTERYBOX_VARIANT = PropertyEnum.create("variant", ManaBufferVariant.class);
	
	static {
    	dreamKnowledge = BotaniaAPI.registerKnowledgeType("dream", TextFormatting.DARK_RED, false);
	}
	
	public static RecipePedestal registerPedestalRecipe(ItemStack output, ItemStack input){
		RecipePedestal recipe = new RecipePedestal(output, input);
		pedestalRecipes.add(recipe);
		return recipe;
	};
	
	public static RecipeStonesia registerStonesiaRecipe(int output, ItemStack input){
		RecipeStonesia recipe = new RecipeStonesia(output, input);
		stonesiaRecipes.add(recipe);
		return recipe;
	};
	
	public static RecipeOmniviolet registerOmnivioletRecipe(int output, ItemStack input){
		RecipeOmniviolet recipe = new RecipeOmniviolet(output, input);
		omnivioletRecipes.add(recipe);
		return recipe;
	};
	
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
