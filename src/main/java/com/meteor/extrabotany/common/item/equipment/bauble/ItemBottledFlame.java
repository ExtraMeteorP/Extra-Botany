package com.meteor.extrabotany.common.item.equipment.bauble;

import java.util.regex.Pattern;

import com.meteor.extrabotany.common.lib.LibItemsName;

import baubles.api.BaubleType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBottledFlame extends ItemBauble{

	public ItemBottledFlame() {
		super(LibItemsName.BAUBLE_BOTTLEDFLAME);
	}
	
	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase entity) {
		super.onWornTick(stack, entity);
		if(entity instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) entity;
			World world = entity.world;
			BlockPos pos = entity.getPosition();
			if(world.getLightBrightness(pos) < 0.25F){
				for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
					ItemStack stackAt = player.inventory.getStackInSlot(i);
					if(!stackAt.isEmpty() && TORCH_PATTERN.matcher(stackAt.getItem().getUnlocalizedName()).find()) {
						ItemStack saveHeldStack = player.getHeldItem(EnumHand.OFF_HAND);
						player.setHeldItem(EnumHand.OFF_HAND, stackAt);
						stackAt.getItem().onItemUse(player, world, pos, EnumHand.OFF_HAND, EnumFacing.DOWN, 0F,0F,0F);
						player.setHeldItem(EnumHand.OFF_HAND, saveHeldStack);
					}
				}
			}
		}
	}
	
	private static final Pattern TORCH_PATTERN = Pattern.compile("(?:(?:(?:[A-Z-_.:]|^)torch)|(?:(?:[a-z-_.:]|^)Torch))(?:[A-Z-_.:]|$)");

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.CHARM;
	}

}
