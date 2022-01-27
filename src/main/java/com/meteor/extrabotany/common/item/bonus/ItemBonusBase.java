package com.meteor.extrabotany.common.item.bonus;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import javax.annotation.Nonnull;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.api.item.Bonus;
import com.meteor.extrabotany.api.item.WeightCategory;
import com.meteor.extrabotany.common.item.ItemMod;
import com.meteor.extrabotany.common.lib.LibAdvancements;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class ItemBonusBase extends ItemMod {

	private static final String TAG_WEIGHT = "weight";
	private static Random random = new Random();

	public ItemBonusBase(String name) {
		super(name);
	}

	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (world.isRemote)
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		if (rollItem(stack, player) != null) {
			ItemStack newstack = rollItem(stack, player).copy();
			world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT,
					SoundCategory.PLAYERS, 0.5F, 0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));
			onBonusOpen(world, player, stack, newstack);
			player.entityDropItem(newstack, 0).setNoPickupDelay();
			stack.shrink(1);
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		}
		return ActionResult.newResult(EnumActionResult.PASS, stack);
	}

	public void onBonusOpen(World world, EntityPlayer player, ItemStack stackold, ItemStack stacknew) {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack, World world, List<String> stacks, ITooltipFlag flags) {
		DecimalFormat df = new DecimalFormat("0.00%");
		int weightSum = 0;
		for (WeightCategory wc : getWeightCategory(par1ItemStack)) {
			weightSum += wc.getWeight();
		}
		setSum(par1ItemStack, weightSum);
		if (GuiScreen.isShiftKeyDown()) {
			addStringToTooltip(I18n.format("extrabotany.bonusbase"), stacks);
			for (WeightCategory wc : getWeightCategory(par1ItemStack)) {
				String num = df.format((float) wc.getWeight() / getSum(par1ItemStack));
				String name = I18n
						.format(wc.getCategory().getItem().getUnlocalizedNameInefficiently(wc.getCategory()) + ".name");
				TextFormatting text = (float) wc.getWeight() / getSum(par1ItemStack) < 0.01F ? TextFormatting.GOLD
						: TextFormatting.RESET;
				addStringToTooltip(text + name + " x" + wc.getCategory().getCount() + " " + num, stacks);
			}
		} else
			addStringToTooltip(I18n.format("botaniamisc.shiftinfo"), stacks);
	}

	void addStringToTooltip(String s, List<String> tooltip) {
		tooltip.add(s.replaceAll("&", "\u00a7"));
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, world, entity, itemSlot, isSelected);
		setSum(stack, Bonus.sum(getWeightCategory(stack)));
	}

	public ItemStack rollItem(ItemStack stack, EntityPlayer player) {
		return rollItem(stack, player, getWeightCategory(stack));
	}

	public ItemStack rollItem(ItemStack stack, EntityPlayer player, List<WeightCategory> weightcategory) {
		int weightSum = 0;
		for (WeightCategory wc : weightcategory) {
			weightSum += wc.getWeight();
		}
		int n = random.nextInt(weightSum);
		int m = 0;
		for (WeightCategory wc : weightcategory) {
			if (m <= n && n < m + wc.getWeight()) {
				if (wc.getWeight() <= 1) {
					ExtraBotanyAPI.unlockAdvancement(player, LibAdvancements.LUCKY_DRAW_ID);
				}
				return wc.getCategory();
			}
			m += wc.getWeight();
		}
		return null;
	}

	public List<WeightCategory> getWeightCategory(ItemStack stack) {
		return null;
	}

	public void setSum(ItemStack stack, int i) {
		ItemNBTHelper.setInt(stack, TAG_WEIGHT, i);
	}

	public int getSum(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_WEIGHT, 0);
	}

}
