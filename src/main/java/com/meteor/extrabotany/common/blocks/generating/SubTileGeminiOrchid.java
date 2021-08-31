package com.meteor.extrabotany.common.blocks.generating;

import com.meteor.extrabotany.common.blocks.ModSubtiles;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.IFluidBlock;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.TileEntityGeneratingFlower;

public class SubTileGeminiOrchid extends TileEntityGeneratingFlower {

    private static final BlockPos[] OFFSETS = { new BlockPos(0, 0, 1), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(-1, 0, 1), new BlockPos(-1, 0, -1), new BlockPos(1, 0, 1), new BlockPos(1, 0, -1) };
    private static final int RANGE = 1;

    public SubTileGeminiOrchid() {
        super(ModSubtiles.GEMINIORCHID);
    }

    @Override
    public void tickFlower() {
        super.tickFlower();

        int tempMax = 700;
        int tempMin = 700;

        for(int i = 0; i < OFFSETS.length; i++){
            BlockPos pos = this.getEffectivePos().add(OFFSETS[i]);
            Block block = this.getWorld().getBlockState(pos).getBlock();

            if(block != null){
                if(block instanceof FlowingFluidBlock){
                    FlowingFluidBlock fluid = (FlowingFluidBlock) block;
                    tempMax = Math.max(tempMax, fluid.getFluid().getAttributes().getTemperature(getWorld(), pos));
                    tempMin = Math.min(tempMin, fluid.getFluid().getAttributes().getTemperature(getWorld(), pos));
                }
                else if(block instanceof IFluidBlock){
                    IFluidBlock fluid = (IFluidBlock) block;
                    tempMax = Math.max(tempMax, fluid.getFluid().getAttributes().getTemperature(getWorld(), pos));
                    tempMin = Math.min(tempMin, fluid.getFluid().getAttributes().getTemperature(getWorld(), pos));
                }
            }
        }

        if(getMana() < getMaxMana() && ticksExisted % 8 == 0)
            addMana((int)(Math.abs(tempMax - tempMin)/100F));
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
        return new RadiusDescriptor.Square(getEffectivePos(), RANGE);
    }

}
