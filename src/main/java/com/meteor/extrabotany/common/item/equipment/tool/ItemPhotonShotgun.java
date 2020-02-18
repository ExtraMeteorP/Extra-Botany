package com.meteor.extrabotany.common.item.equipment.tool;

import java.util.List;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.item.ItemMod;
import com.meteor.extrabotany.common.lib.LibItemsName;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import vazkii.botania.api.internal.IManaBurst;
import vazkii.botania.api.mana.BurstProperties;
import vazkii.botania.api.mana.ILensEffect;
import vazkii.botania.common.entity.EntityManaBurst;

public class ItemPhotonShotgun extends ItemMod implements ILensEffect{

	public ItemPhotonShotgun() {
		super(LibItemsName.PHOTONSHOTGUN);
		setMaxStackSize(1);
	}
	
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if(!world.isRemote) {
			for(int i = 0; i < 12; i++) {
				EntityManaBurst burst = getBurst(player, player.getHeldItemMainhand());
				burst.shoot(player, (float) (player.rotationPitch-8F+Math.random()*16F), (float) (player.rotationYaw-8F+Math.random()*16F), 0.0F, 3.2F, 0);
				player.world.spawnEntity(burst);
			}
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
	
	public static EntityManaBurst getBurst(EntityPlayer player, ItemStack stack) {
		EntityManaBurst burst = new EntityManaBurst(player, EnumHand.MAIN_HAND);

		float motionModifier = 10F;
		burst.setColor(0xF5F5F5);
		burst.setMana(120);
		burst.setStartingMana(120);
		burst.setMinManaLoss(40);
		burst.setManaLossPerTick(10F);
		burst.setGravity(0F);
		burst.setMotion(burst.motionX * motionModifier, burst.motionY * motionModifier, burst.motionZ * motionModifier);

		ItemStack lens = stack.copy();
		burst.setSourceLens(lens);
		return burst;
	}

	@Override
	public void apply(ItemStack stack, BurstProperties props) {
		// NO-OP
	}

	@Override
	public boolean collideBurst(IManaBurst burst, RayTraceResult arg1, boolean arg2, boolean dead, ItemStack arg4) {
		return dead;
	}
	
	@Override
	public boolean doParticles(IManaBurst burst, ItemStack stack) {
		return true;
	}


	@Override
	public void updateBurst(IManaBurst burst, ItemStack stack) {
		EntityThrowable entity = (EntityThrowable) burst;
		AxisAlignedBB axis1 = new AxisAlignedBB(entity.posX - 0.7F, entity.posY - 0.7F, entity.posZ - 0.7F,
				entity.lastTickPosX + 0.7F, entity.lastTickPosY + 0.7F, entity.lastTickPosZ + 0.7F);
		List<EntityLivingBase> entities = entity.world.getEntitiesWithinAABB(EntityLivingBase.class, axis1);
		for (EntityLivingBase living : entities) {
			if(living instanceof EntityPlayer)
				continue;
			living.attackEntityFrom(DamageSource.GENERIC, 5F);
			ExtraBotanyAPI.dealTrueDamage(living, living, 0.5F);
		}
	}
	

}
