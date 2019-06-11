package com.meteor.extrabotany.common.item.equipment.tool;

import java.util.UUID;

import javax.annotation.Nonnull;

import com.google.common.collect.Multimap;
import com.meteor.extrabotany.common.item.ItemMod;
import com.meteor.extrabotany.common.lib.LibItemsName;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import vazkii.botania.api.mana.ManaItemHandler;

public class ItemWalkingCane extends ItemMod{

	public ItemWalkingCane() {
		super(LibItemsName.WALKINGCANE);
		setMaxStackSize(1);
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> attrib = super.getAttributeModifiers(slot, stack);
		UUID uuid = new UUID((getUnlocalizedName() + slot.toString()).hashCode(), 0);
		if (slot == EntityEquipmentSlot.MAINHAND || slot == EntityEquipmentSlot.OFFHAND) {
			attrib.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(uuid, "walkingcane modifier ", 0.25F, 1));
		}
		return attrib;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase player, int timeLeft) {
		int time = this.getMaxItemUseDuration(stack) - timeLeft;
	    if(ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer) player, 40, true)) {
	    	if(player instanceof EntityPlayer) {
	    		((EntityPlayer) player).addExhaustion(0.2F);
	    	}
		    player.setSprinting(true);
	
		    float increase = (float) (0.07F * time + 0.67F);
		    if(increase > 0.9f) {
		    	increase = 0.9f;
		    }
		    player.motionY += increase;
		    ((EntityPlayer) player).getCooldownTracker().setCooldown(this, 120);
	
		    float speed = 0.12F * time + 1.1F;
		    if(speed > 1.825f) {
		    	speed = 1.825f;
		   	}
		   	player.motionX = (double) (-MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI) * speed);
		   	player.motionZ = (double) (MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI) * MathHelper
		   			.cos(player.rotationPitch / 180.0F * (float) Math.PI) * speed);
	    }

	    super.onPlayerStoppedUsing(stack, world, player, timeLeft);
	}
	
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		ItemStack itemStackIn = playerIn.getHeldItem(hand);
		playerIn.setActiveHand(hand);
		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
	}
	
	@Nonnull
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.NONE;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 200;
	}


}
