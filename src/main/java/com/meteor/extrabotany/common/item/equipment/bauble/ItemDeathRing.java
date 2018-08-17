package com.meteor.extrabotany.common.item.equipment.bauble;

import com.meteor.extrabotany.common.lib.LibItemsName;

import baubles.api.BaubleType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import vazkii.botania.api.mana.ManaItemHandler;

public class ItemDeathRing extends ItemBauble{

	public ItemDeathRing() {
		super(LibItemsName.BAUBLE_DEATHRING);
	}
	
	private static final int RANGE = 5;
	
	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase entity) {
		super.onWornTick(stack, entity);
		if(!entity.world.isRemote) {
			for(EntityLivingBase living : entity.getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(entity.getPosition().add(-RANGE, -RANGE, -RANGE), entity.getPosition().add(RANGE + 1, RANGE + 1, RANGE + 1)))){
				if(living.isSpectatedByPlayer((EntityPlayerMP) entity) && living != entity && entity.getEntityWorld().getWorldTime() % 20 == 0 && ManaItemHandler.requestManaExact(stack, (EntityPlayer)entity, 50, true)){
					living.addPotionEffect(new PotionEffect(MobEffects.WITHER, 60, 1));
					living.addPotionEffect(new PotionEffect(MobEffects.UNLUCK, 60, 1));
				}
			}
		}
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.RING;
	}

}
