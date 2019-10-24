package com.meteor.extrabotany.common.block.subtile.generating;

import com.meteor.extrabotany.api.subtile.SubTileGeneratingNature;
import com.meteor.extrabotany.common.core.config.ConfigHandler;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.FluidRegistry;
import vazkii.botania.api.subtile.RadiusDescriptor;

public class SubTileGeminiOrchid extends SubTileGeneratingNature{
	
	private static final BlockPos[] OFFSETS = { new BlockPos(0, 0, 1), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(-1, 0, 1), new BlockPos(-1, 0, -1), new BlockPos(1, 0, 1), new BlockPos(1, 0, -1) };
	
	@Override
	public void onUpdate() {
		if(redstoneSignal > 0)
			return;
		super.onUpdate();
		
		int tempMax = 700;
		int tempMin = 700;
		for(int i = 0; i < OFFSETS.length; i++){
			BlockPos pos = this.getPos().add(OFFSETS[i]);
			Block block = this.getWorld().getBlockState(pos).getBlock();
			if(block != null){	
				if(block == Blocks.LAVA){
					tempMax = Math.max(tempMax, FluidRegistry.LAVA.getTemperature());
					tempMin = Math.min(tempMin, FluidRegistry.LAVA.getTemperature());
				}else if(block == Blocks.WATER){
					tempMax = Math.max(tempMax, FluidRegistry.WATER.getTemperature());
					tempMin = Math.min(tempMin, FluidRegistry.WATER.getTemperature());
				}else if(block instanceof BlockFluidClassic){
					tempMax = Math.max(tempMax, ((BlockFluidClassic) block).getTemperature());
					tempMin = Math.min(tempMin, ((BlockFluidClassic) block).getTemperature());
				}
			}
		}
		float buff = isEnabled() ? 1.2F : 1.0F;
		if(mana < getMaxMana() && ticksExisted % 8 == 0)
			mana+= (int)(Math.abs(tempMax - tempMin)/100 * buff * ConfigHandler.EFF_GEMINIORCHID);
		
	}
	
	@Override
	public int getRate(){
		return 6;
	}
	
	@Override
	public boolean willConsume(){
		return true;
	}
	
	@Override
	public int getMaxMana() {
		return 1000;
	}
	
	@Override
	public boolean isPassiveFlower() {
		return true;
	}
	
	@Override
	public RadiusDescriptor getRadius() {
		return new RadiusDescriptor.Square(toBlockPos(), 1);
	}

}
