package com.meteor.extrabotany.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.gamerforea.eventhelper.util.EventUtils;
import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.crafting.recipe.RecipeOmniviolet;
import com.meteor.extrabotany.common.crafting.recipe.RecipePedestal;
import com.meteor.extrabotany.common.crafting.recipe.RecipeStonesia;
import com.meteor.extrabotany.common.lib.LibAdvancements;
import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.EnumHelper;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.KnowledgeType;
import vazkii.botania.api.lexicon.LexiconCategory;
import vazkii.botania.api.recipe.RecipeManaInfusion;

public class ExtraBotanyAPI {

	public static IBlockState dimensionState;

	public static final KnowledgeType dreamKnowledge;
	public static LexiconCategory dreamCategory;

	public static final List<RecipePedestal> pedestalRecipes = new ArrayList<RecipePedestal>();
	public static final List<RecipeStonesia> stonesiaRecipes = new ArrayList<RecipeStonesia>();
	public static final List<RecipeOmniviolet> omnivioletRecipes = new ArrayList<RecipeOmniviolet>();
	public static final ArmorMaterial orichalcosArmorMaterial = EnumHelper.addArmorMaterial("ORICHALCOS", "orichalcos",
			50, new int[] { 4, 7, 8, 3 }, 50, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 4F);
	public static final ArmorMaterial shadowiumArmorMaterial = EnumHelper.addArmorMaterial("SHADOWIUM", "shadowium", 23,
			new int[] { 3, 7, 6, 3 }, 28, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 1.5F);
	public static final ToolMaterial shadowiumToolMaterial = EnumHelper.addToolMaterial("SHADOWIUM", 3, 880, 6.5F, 2F,
			12);
	public static final ArmorMaterial goblinslayerArmorMaterial = EnumHelper.addArmorMaterial("GOBLINSLAYER", "goblinslayer", 21,
			new int[] { 3, 6, 6, 3 }, 40, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.5F);
	
	public static final ToolMaterial manasteelHammer = EnumHelper.addToolMaterial("MANASTEELHAMMER", 3, 400, 6.8F, 2F, 20);
	
	public static final ToolMaterial elementiumHammer = EnumHelper.addToolMaterial("ELEMENTIUMHAMMER", 3, 900, 6.8F, 2F, 20);
	
	public static final ToolMaterial terrasteelHammer = EnumHelper.addToolMaterial("TERRASTEELHAMMER", 4, 3000, 9.8F, 3F, 26);
	
	public static final ToolMaterial ultimateHammer = EnumHelper.addToolMaterial("ULTIMATEHAMMER", 4, 3000, 10.5F, 3F, 40);


	public static final Set<Block> gaiaBreakBlacklist = new HashSet<>();

	static {
		dreamKnowledge = BotaniaAPI.registerKnowledgeType("dream", TextFormatting.DARK_RED, false);
		blacklistBlockFromGaiaGuardian(Blocks.BEACON);
	}

	public static void blacklistBlockFromGaiaGuardian(Block block) {
		gaiaBreakBlacklist.add(block);
	}

	public static RecipeManaInfusion registerManaDimensionRecipe(ItemStack output, Object input, int mana) {
		RecipeManaInfusion recipe = BotaniaAPI.registerManaInfusionRecipe(output, input, mana);
		recipe.setCatalyst(dimensionState);
		return recipe;
	}

	public static RecipePedestal registerPedestalRecipe(ItemStack output, ItemStack input) {
		RecipePedestal recipe = new RecipePedestal(output, input);
		pedestalRecipes.add(recipe);
		return recipe;
	}

	public static RecipeStonesia registerStonesiaRecipe(int output, Object input) {
		RecipeStonesia recipe = new RecipeStonesia(output, input);
		stonesiaRecipes.add(recipe);
		return recipe;
	}

	public static RecipeOmniviolet registerOmnivioletRecipe(int output, ItemStack input) {
		RecipeOmniviolet recipe = new RecipeOmniviolet(output, input);
		omnivioletRecipes.add(recipe);
		return recipe;
	}

	public static void unlockAdvancement(EntityPlayer player, String name) {
		if (player instanceof EntityPlayerMP) {
			PlayerAdvancements advancements = ((EntityPlayerMP) player).getAdvancements();
			AdvancementManager manager = ((WorldServer) player.getEntityWorld()).getAdvancementManager();
			Advancement advancement = manager
					.getAdvancement(new ResourceLocation(LibMisc.MOD_ID, LibAdvancements.PREFIX + name));
			if (advancement != null)
				advancements.grantCriterion(advancement, "ebt_trigger");
		}
	}

	public static boolean cantAttack(EntityLivingBase attacker, EntityLivingBase target) {
		if (ExtraBotany.isTableclothServer)
			return attacker instanceof EntityPlayer && EventUtils.cantAttack((EntityPlayer) attacker, target);
		else
			return false;
	}

	public static float dealTrueDamage(EntityLivingBase player, EntityLivingBase target, float amount) {
		float result = 0;

		if (target == null)
			return result;
		if (!(target instanceof EntityLivingBase))
			return result;
		if (!target.isEntityAlive())
			return result;
		if (amount < 0)
			return result;
		if (player != null && cantAttack(player, target))
			return result;

		target.attackEntityFrom(DamageSource.MAGIC.setDamageIsAbsolute().setDamageBypassesArmor(), 0.01F);
		float health = target.getHealth();
		if (target instanceof EntityPlayer)
			if (((EntityPlayer) target).isCreative())
				return result;
		
		if(ConfigHandler.ENABLE_TRUEDAMAGE) {
			if(!target.isNonBoss() || target instanceof EntityPlayer && player.isNonBoss())
				amount*=0.2F;
			else
				amount*=1.5F;
			if (health > 0) {
				float postHealth = Math.max(1, health - amount);
				target.setHealth(postHealth);
				if (health <= amount) {
					if (target instanceof EntityPlayer)
						target.onKillCommand();
					else
						target.attackEntityFrom(DamageSource.MAGIC.setDamageIsAbsolute().setDamageBypassesArmor(),
								Integer.MAX_VALUE - 1F);
				}
				result = health - postHealth;
			}
		}else {
			target.attackEntityFrom(DamageSource.causeIndirectMagicDamage(player, target), amount);
		}
		return result;
	}

	public static void addPotionEffect(EntityLivingBase entity, Potion potion, int time, int max, boolean multi) {
		if (!entity.isPotionActive(potion))
			entity.addPotionEffect(new PotionEffect(potion, time, 0));
		else {
			int amp = entity.getActivePotionEffect(potion).getAmplifier();
			int t = multi ? time + 200 * amp : time;
			entity.addPotionEffect(new PotionEffect(potion, t, Math.min(max, amp + 1)));
		}
	}

	public static void addPotionEffect(EntityLivingBase entity, Potion potion, int max) {
		addPotionEffect(entity, potion, 100, max, false);
	}

}
