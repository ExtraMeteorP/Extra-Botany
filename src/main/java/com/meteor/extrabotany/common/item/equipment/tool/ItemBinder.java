package com.meteor.extrabotany.common.item.equipment.tool;

import com.meteor.extrabotany.common.item.ItemMod;
import com.meteor.extrabotany.common.lib.LibItemsName;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemBinder extends ItemMod{
	
	private static final String TAG_X = "posx";
	private static final String TAG_Y = "posy";
	private static final String TAG_Z = "posz";
	private static final String TAG_DIM = "dim";

	public ItemBinder() {
		super(LibItemsName.BINDER);
		setMaxStackSize(1);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> stacks, ITooltipFlag flags) {
		String x = I18n.format("extrabotany.bindx");
		String y = I18n.format("extrabotany.bindy");
		String z = I18n.format("extrabotany.bindz");
		String dim = I18n.format("extrabotany.binddim");
		String posx = x + getPosX(stack);
		String posy = y + getPosY(stack);
		String posz = z + getPosZ(stack);
		String adim = dim + getDim(stack);
		stacks.add(posx);
		stacks.add(posy);
		stacks.add(posz);
		stacks.add(adim);
	}
	
	public static void setPosX(ItemStack stack, int x) {
		ItemNBTHelper.setInt(stack, TAG_X, x);
	}

	public int getPosX(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_X, 0);
	}
	
	public static void setPosY(ItemStack stack, int y) {
		ItemNBTHelper.setInt(stack, TAG_Y, y);
	}

	public int getPosY(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_Y, -1);
	}
	
	public static void setPosZ(ItemStack stack, int z) {
		ItemNBTHelper.setInt(stack, TAG_Z, z);
	}

	public int getPosZ(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_Z, 0);
	}
	
	public static void setDim(ItemStack stack, int z) {
		ItemNBTHelper.setInt(stack, TAG_DIM, z);
	}

	public int getDim(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_DIM, 0);
	}
	
	
	@Nonnull
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if(player.isSneaking()){
			setPosX(player.getHeldItem(hand), pos.getX());
			setPosY(player.getHeldItem(hand), pos.getY());
			setPosZ(player.getHeldItem(hand), pos.getZ());
			setDim(player.getHeldItem(hand), player.dimension);
		}
		return EnumActionResult.SUCCESS;
	}

}
