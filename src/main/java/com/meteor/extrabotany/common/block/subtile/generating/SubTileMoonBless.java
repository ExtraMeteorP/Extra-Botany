package com.meteor.extrabotany.common.block.subtile.generating;

import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.lexicon.LexiconData;
import vazkii.botania.api.lexicon.LexiconEntry;

public class SubTileMoonBless extends SubTileSunBless{
	
	@Override
	public int getColor() {
		return 0xFFFF00;
	}

	@Override
	public LexiconEntry getEntry() {
		return LexiconData.moonbless;
	}
	
	@Override
	public int getValueForPassiveGeneration() {
		return ConfigHandler.EFF_MOONBLESS;
	}

	@Override
	public boolean canGeneratePassively() {
		return !this.supertile.getWorld().isDaytime() && this.ticksExisted % 4 == 0;
	}
	
}
