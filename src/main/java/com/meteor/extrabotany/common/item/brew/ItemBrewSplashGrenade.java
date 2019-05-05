package com.meteor.extrabotany.common.item.brew;

import com.meteor.extrabotany.common.entity.EntitySplashGrenade;
import com.meteor.extrabotany.common.item.ItemMod;
import com.meteor.extrabotany.common.lib.LibItemsName;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.api.brew.IBrewItem;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemBrewSplashGrenade extends ItemMod implements IBrewItem {

	private static final String TAG_BREW_KEY = "brewKey";

	public ItemBrewSplashGrenade() {
		super(LibItemsName.BREW_SPLASHGRENADE);
		setMaxStackSize(16);
	}
	
	@Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn){
        ItemStack stack = player.getHeldItem(handIn);
        ItemStack itemstack1 = player.capabilities.isCreativeMode ? stack.copy() : stack.splitStack(1);
		EntitySplashGrenade sg = new EntitySplashGrenade(world, player);
		sg.setItem(itemstack1);
		sg.shoot(player, player.rotationPitch, player.rotationYaw, -5.0F, 0.8F, 1.0F);
		if(!world.isRemote)
			world.spawnEntity(sg);

		world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		stack.shrink(1);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if(isInCreativeTab(tab)) {
			for(String s : BotaniaAPI.brewMap.keySet()) {
				ItemStack stack = new ItemStack(this);
				setBrew(stack, s);
				list.add(stack);
			}
		}
	}

	@Nonnull
	@Override
	public String getItemStackDisplayName(@Nonnull ItemStack stack) {
		return String.format(net.minecraft.util.text.translation.I18n.translateToLocal(getUnlocalizedNameInefficiently(stack) + ".name"), net.minecraft.util.text.translation.I18n.translateToLocal(getBrew(stack).getUnlocalizedName(stack)), TextFormatting.BOLD + ""  + TextFormatting.RESET);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flags) {
		Brew brew = getBrew(stack);
		for(PotionEffect effect : brew.getPotionEffects(stack)) {
			TextFormatting format = effect.getPotion().isBadEffect() ? TextFormatting.RED : TextFormatting.GRAY;
			list.add(format + I18n.format(effect.getEffectName()) + (effect.getAmplifier() == 0 ? "" : " " + I18n.format("botania.roman" + (effect.getAmplifier() + 1))) + TextFormatting.GRAY + (effect.getPotion().isInstant() ? "" : " (" + Potion.getPotionDurationString(effect, 0.6F) + ")"));
		}
	}

	@Override
	public Brew getBrew(ItemStack stack) {
		String key = ItemNBTHelper.getString(stack, TAG_BREW_KEY, "");
		return BotaniaAPI.getBrewFromKey(key);
	}

	public static void setBrew(ItemStack stack, Brew brew) {
		setBrew(stack, (brew == null ? BotaniaAPI.fallbackBrew : brew).getKey());
	}

	public static void setBrew(ItemStack stack, String brew) {
		ItemNBTHelper.setString(stack, TAG_BREW_KEY, brew);
	}
}
