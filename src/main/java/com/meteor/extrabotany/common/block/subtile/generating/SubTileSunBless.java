package com.meteor.extrabotany.common.block.subtile.generating;

import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.lexicon.LexiconData;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileGenerating;

public class SubTileSunBless extends SubTileGenerating{
	
	private static final int RANGE = 2;
	
	@Override
	public int getMaxMana() {
		return 200;
	}

	@Override
	public int getValueForPassiveGeneration() {
		return ConfigHandler.EFF_SUNBLESS;
	}

	@Override
	public int getColor() {
		return 0xFFA500;
	}

	@Override
	public RadiusDescriptor getRadius() {
		return new RadiusDescriptor.Square(toBlockPos(), RANGE);
	}

	@Override
	public LexiconEntry getEntry() {
		return LexiconData.sunbless;
	}

	@Override
	public boolean canGeneratePassively() {
		return this.supertile.getWorld().isDaytime() && this.ticksExisted % 3 == 0;
	}

	@Override
	public int getDelayBetweenPassiveGeneration() {
		return 2;
	}
	
	@Override
	public boolean isPassiveFlower() {
		return true;
	}
	
}
