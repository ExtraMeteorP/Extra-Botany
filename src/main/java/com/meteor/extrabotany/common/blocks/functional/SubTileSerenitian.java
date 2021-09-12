package com.meteor.extrabotany.common.blocks.functional;

import com.meteor.extrabotany.common.blocks.ModSubtiles;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.TileEntityFunctionalFlower;
import vazkii.botania.api.subtile.TileEntityGeneratingFlower;

public class SubTileSerenitian extends TileEntityFunctionalFlower {

    private static final int RANGE = 3;

    public SubTileSerenitian() {
        super(ModSubtiles.SERENITIAN);
    }

    @Override
    public void tickFlower() {
        super.tickFlower();
        if (redstoneSignal > 0)
            return;

        for(int dx = -RANGE; dx <= RANGE; dx++)
            for(int dz = -RANGE; dz <= RANGE; dz++){
                BlockPos pos = getEffectivePos().add(dx, 0, dz);
                TileEntity tile = world.getTileEntity(pos);
                if(tile instanceof TileEntityGeneratingFlower){
                    TileEntityGeneratingFlower flower = (TileEntityGeneratingFlower) tile;
                    if(flower.isPassiveFlower()){
                        flower.passiveDecayTicks = 0;
                    }
                }
            }
    }

    @Override
    public int getColor() {
        return 0x000000;
    }

    @Override
    public int getMaxMana() {
        return 1;
    }

    @Override
    public boolean acceptsRedstone() {
        return true;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Square(getEffectivePos(), RANGE);
    }

}
