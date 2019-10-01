package com.meteor.extrabotany.common.block.subtile.generating;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.brew.ModPotions;
import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.lexicon.LexiconData;
import com.meteor.extrabotany.common.lib.LibAdvancements;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileGenerating;

public class SubTileBloodyEnchantress extends SubTileGenerating {
	
	private static final String TAG_BURN_TIME = "burnTime";
	private static final int RANGE = 1;
	private static final int START_BURN_EVENT = 0;

	private int burnTime = 0;

	@Override
	public void onUpdate() {
		super.onUpdate();
		if(redstoneSignal > 0)
			return;

		if(burnTime > 0)
			burnTime--;

		if(getWorld().isRemote) {
			if(burnTime > 0 && supertile.getWorld().rand.nextInt(10) == 0) {
				Vec3d offset = getWorld().getBlockState(getPos()).getOffset(getWorld(), getPos()).addVector(0.4, 0.7, 0.4);
				supertile.getWorld().spawnParticle(EnumParticleTypes.FLAME, supertile.getPos().getX() + offset.x + Math.random() * 0.2, supertile.getPos().getY() + offset.y, supertile.getPos().getZ() + offset.z + Math.random() * 0.2, 0.0D, 0.0D, 0.0D);
			}
			return;
		}
		
		int ampall = 0;
		for(EntityLivingBase living : supertile.getWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(supertile.getPos().add(-RANGE, -RANGE, -RANGE), supertile.getPos().add(RANGE + 1, RANGE + 1, RANGE + 1)))){
			if(living.isEntityAlive()){
				int amp = living.isPotionActive(ModPotions.bloodtemptation) ? living.getActivePotionEffect(ModPotions.bloodtemptation).getAmplifier() : 0;
				ampall += amp;
			}
		}
		if(ampall > 35)
			return;

		if(linkedCollector != null) {
			if(burnTime == 0) {
				if(mana < getMaxMana()) {
					for(EntityLivingBase living : supertile.getWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(supertile.getPos().add(-RANGE, -RANGE, -RANGE), supertile.getPos().add(RANGE + 1, RANGE + 1, RANGE + 1)))) {
						if(living.isEntityAlive()){
							int amp = living.isPotionActive(ModPotions.bloodtemptation) ? living.getActivePotionEffect(ModPotions.bloodtemptation).getAmplifier() : 0;
							if(amp > 4 && Math.random() > 0.5F)
								continue;
							if(amp < 10){
								mana += ConfigHandler.EFF_BLOODYENCHANTRESS * 12F * (1F - 0.05F * amp - 0.02F * ampall);
							}else
								break;
							ExtraBotanyAPI.addPotionEffect(living, ModPotions.bloodtemptation, 100, 10, true);
							if(living instanceof EntityPlayer){
								ExtraBotanyAPI.unlockAdvancement((EntityPlayer)living, LibAdvancements.BLOODYENCHANTRESS_USE);
								ExtraBotanyAPI.dealTrueDamage(living, living, 3F);
							}else{
								living.setHealth(living.getHealth() - 3F);
							}
							living.attackEntityFrom(DamageSource.MAGIC, 0.01F);
							burnTime+=ConfigHandler.BLOOD_BURNTIME;
							return;
						}
					}	
				}
			}
		}
	}

	@Override
	public boolean receiveClientEvent(int event, int param) {
		if(event == START_BURN_EVENT) {
			Entity e = getWorld().getEntityByID(param);
			if(e != null) {
				e.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, e.posX, e.posY + 0.1, e.posZ, 0.0D, 0.0D, 0.0D);
				e.world.spawnParticle(EnumParticleTypes.FLAME, e.posX, e.posY, e.posZ, 0.0D, 0.0D, 0.0D);
			}
			return true;
		} else {
			return super.receiveClientEvent(event, param);
		}
	}

	@Override
	public int getMaxMana() {
		return 800;
	}

	@Override
	public int getValueForPassiveGeneration() {
		return ConfigHandler.EFF_BLOODYENCHANTRESS;
	}

	@Override
	public int getColor() {
		return 0x8B0000;
	}

	@Override
	public RadiusDescriptor getRadius() {
		return new RadiusDescriptor.Square(toBlockPos(), RANGE);
	}

	@Override
	public LexiconEntry getEntry() {
		return LexiconData.bloodyenchantress;
	}

	@Override
	public void writeToPacketNBT(NBTTagCompound cmp) {
		super.writeToPacketNBT(cmp);

		cmp.setInteger(TAG_BURN_TIME, burnTime);
	}

	@Override
	public void readFromPacketNBT(NBTTagCompound cmp) {
		super.readFromPacketNBT(cmp);

		burnTime = cmp.getInteger(TAG_BURN_TIME);
	}

	@Override
	public int getDelayBetweenPassiveGeneration() {
		return 2;
	}
}
