package com.meteor.extrabotany.common.block.subtile.generating;

import com.meteor.extrabotany.api.subtile.SubTileGeneratingNature;
import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.lexicon.LexiconData;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.RadiusDescriptor;

public class SubTileBellFlower extends SubTileGeneratingNature{
	
	private static final int RANGE = 2;
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if(redstoneSignal > 0)
			return;
		
		int baseGen = ConfigHandler.EFF_BELLFLOWER;
		int baseY = ConfigHandler.BASEY;
		int y = this.supertile.getPos().getY();
        
        int buff = ConfigHandler.LP_BELLFLOWER ? isEnabled() ? 2 : 0 : 0;
        
		if(this.getWorld().canBlockSeeSky(this.supertile.getPos()) && y > baseY){
			int rain = ConfigHandler.LP_BELLFLOWER ? this.getWorld().isRaining() ? 2 : 0 : 0;
			int gen = (baseGen + rain + buff) * y/baseY;
			if(this.ticksExisted % 8 == 0)
				mana += gen;
		}
		
	}
	
	@Override
	public int getRate(){
		return 2;
	}
	
	@Override
	public boolean willConsume(){
		return true;
	}
	
	@Override
	public int getMaxMana() {
		return 300;
	}

	@Override
	public int getColor() {
		return 0xFFFF99;
	}

	@Override
	public RadiusDescriptor getRadius() {
		return new RadiusDescriptor.Square(toBlockPos(), RANGE);
	}

	@Override
	public LexiconEntry getEntry() {
		return LexiconData.bellflower;
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
