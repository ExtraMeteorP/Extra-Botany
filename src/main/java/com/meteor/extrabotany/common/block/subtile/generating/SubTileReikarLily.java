package com.meteor.extrabotany.common.block.subtile.generating;

import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.lexicon.LexiconData;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileGenerating;

public class SubTileReikarLily extends SubTileGenerating{
	
	private static final String TAG_BURN_TIME = "burnTime";
	private static final String TAG_COOLDOWN = "cooldown";
	private static final String TAG_CD = "cd";
	private static final int RANGE = 3;

	int burnTime, cooldown, cd;

	@Override
	public void onUpdate() {
		super.onUpdate();
		if(redstoneSignal > 0)
			return;
		
		BlockPos pos = this.supertile.getPos();
		
		if(getWorld().isRaining() && getWorld().canSeeSky(pos) && cd == 0){
			int baseY = ConfigHandler.RL_BASEY;
			if(getWorld().rand.nextInt((int)(3000 * baseY / pos.getY())) == 1){
				EntityLightningBolt bolt = new EntityLightningBolt(getWorld(), pos.getX(), pos.getY(), pos.getZ(), true);
				getWorld().spawnEntity(bolt);
				cd += ConfigHandler.RL_CD;
			}
		}
		
		for(EntityLightningBolt bolt : supertile.getWorld().getEntitiesWithinAABB(EntityLightningBolt.class, new AxisAlignedBB(supertile.getPos().add(-RANGE, -RANGE, -RANGE), supertile.getPos().add(RANGE + 1, RANGE + 1, RANGE + 1)))) {
			if(bolt.isEntityAlive()){
				if(cooldown == 0){
					burnTime += ConfigHandler.RL_BURNTIME;
					if(mana < getMaxMana())
						mana += Math.min(ConfigHandler.RL_BASEGEN, getMaxMana() - mana);
					cooldown = getCooldown();
					bolt.setDead();
				}
			}
		}
		
		if(cooldown > 0)
			cooldown--;
		if(cd > 0)
			cd--;
		if(burnTime > 0)
			burnTime--;
	
	}
	
	@Override
	public int getMaxMana() {
		return 100000;
	}

	@Override
	public int getColor() {
		return 0x0000CD;
	}

	@Override
	public LexiconEntry getEntry() {
		return LexiconData.reikarlily;
	}

	@Override
	public void writeToPacketNBT(NBTTagCompound cmp) {
		super.writeToPacketNBT(cmp);

		cmp.setInteger(TAG_BURN_TIME, burnTime);
		cmp.setInteger(TAG_COOLDOWN, cooldown);
		cmp.setInteger(TAG_CD, cd);
	}

	@Override
	public void readFromPacketNBT(NBTTagCompound cmp) {
		super.readFromPacketNBT(cmp);

		burnTime = cmp.getInteger(TAG_BURN_TIME);
		cooldown = cmp.getInteger(TAG_COOLDOWN);
		cd = cmp.getInteger(TAG_CD);
	}
	
	@Override
	public boolean canGeneratePassively() {
		return burnTime > 0;
	}
	
	@Override
	public int getDelayBetweenPassiveGeneration() {
		return 1;
	}
	
	@Override
	public int getValueForPassiveGeneration() {
		return ConfigHandler.RL_EFF;
	}

	public int getCooldown() {
		return ConfigHandler.RL_COOLDOWN;
	}
	
	@Override
	public RadiusDescriptor getRadius() {
		return new RadiusDescriptor.Square(toBlockPos(), RANGE);
	}
	
}
