package com.meteor.extrabotany.common.item.relic;

import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.api.item.WeightCategory;
import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.lib.LibItemsName;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTreasure extends ItemModRelic{

	public ItemTreasure() {
		super(LibItemsName.REWARD_TREASURE);
	}
	
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if(world.isRemote)
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		if(isRightPlayer(player, stack)){
			world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 0.5F, 0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));
			player.addItemStackToInventory(new ItemStack(ModItems.rewardbag, 32));
			player.addItemStackToInventory(new ItemStack(ModItems.rewardbag, 18, 1));
			player.addItemStackToInventory(new ItemStack(ModItems.rewardbag, 12, 2));
			player.addItemStackToInventory(new ItemStack(ModItems.rewardbag, 12, 3));
			player.addItemStackToInventory(new ItemStack(ModItems.material, 1, 3));
			stack.shrink(1);
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
	
	void addStringToTooltip(String s, List<String> tooltip) {
		tooltip.add(s.replaceAll("&", "\u00a7"));
	}

}
