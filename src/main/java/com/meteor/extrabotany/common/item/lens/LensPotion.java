package com.meteor.extrabotany.common.item.lens;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.api.internal.IManaBurst;
import vazkii.botania.api.mana.BurstProperties;
import vazkii.botania.common.item.lens.Lens;

public class LensPotion extends Lens {

	@Override
	public void apply(ItemStack stack, BurstProperties props) {
		props.motionModifier *= 0.9F;
		props.maxMana *= 4;
		props.manaLossPerTick *= 4;
	}

	@Override
	public void updateBurst(IManaBurst burst, EntityThrowable entity, ItemStack stack) {
		if (entity.world.isRemote)
			return;

		AxisAlignedBB axis = new AxisAlignedBB(entity.posX, entity.posY, entity.posZ, entity.lastTickPosX,
				entity.lastTickPosY, entity.lastTickPosZ).grow(1);
		List<EntityLivingBase> entities = entity.world.getEntitiesWithinAABB(EntityLivingBase.class, axis);
		int mana = burst.getMana();
		if (stack.getItem() instanceof ItemLens) {
			ItemLens lens = (ItemLens) stack.getItem();
			Brew brew = lens.getBrew(stack);
			for (EntityLivingBase living : entities) {
				if (mana >= 24) {
					burst.setMana(mana - 24);
					if (!burst.isFake()) {
						if (!entity.world.isRemote) {
							for (PotionEffect effect : brew.getPotionEffects(stack)) {
								PotionEffect newEffect = new PotionEffect(effect.getPotion(), effect.getDuration(),
										effect.getAmplifier(), true, true);
								if (effect.getPotion().isInstant())
									effect.getPotion().affectEntity(living, living, living, newEffect.getAmplifier(),
											1F);
								else
									living.addPotionEffect(newEffect);
							}
						}
					}
				}
			}
		}
	}

}
