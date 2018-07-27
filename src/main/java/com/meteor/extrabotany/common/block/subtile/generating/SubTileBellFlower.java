package com.meteor.extrabotany.common.block.subtile.generating;

import com.meteor.extrabotany.common.block.tile.TilePedestal;
import com.meteor.extrabotany.common.core.handler.ConfigHandler;
import com.meteor.extrabotany.common.lexicon.LexiconData;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileGenerating;

public class SubTileBellFlower extends SubTileGenerating{
	
	private static final int RANGE = 2;
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if(redstoneSignal > 0)
			return;
		
		int baseGen = ConfigHandler.EFF_BELLFLOWER;
		int baseY = ConfigHandler.BASEY;
		int y = this.supertile.getPos().getY();
		
		boolean hasDiamond = false;
        
        for(int x = -4; x < 4; x++){
			for(int z = -4; z < 4; z++){
				BlockPos posi = new BlockPos(supertile.getPos().add(x, 0, z));
				if(supertile.getWorld().getTileEntity(posi) instanceof TilePedestal){
					TilePedestal te = (TilePedestal) supertile.getWorld().getTileEntity(posi);
					Item i = te.getItem().getItem();
					if(i != null){
						if(i == Items.DIAMOND){
							hasDiamond = true;
						}
					}
				}
			}
        }
        
        int buff = ConfigHandler.LP_BELLFLOWER ? hasDiamond ? 2 : 0 : 0;
        
		if(this.getWorld().canBlockSeeSky(this.supertile.getPos()) && y > baseY){
			int rain = ConfigHandler.LP_BELLFLOWER ? this.getWorld().isRaining() ? 2 : 0 : 0;
			int gen = (baseGen + rain + buff) * y/baseY;
			if(this.ticksExisted % 10 == 0)
				mana += gen;
		}
		
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
