package com.meteor.extrabotany.common.blocks.generating;

import com.meteor.extrabotany.common.blocks.ModSubtiles;
import net.minecraft.tileentity.TileEntityType;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.TileEntityGeneratingFlower;

public class SubTileSunBless extends TileEntityGeneratingFlower {

    private static final int RANGE = 2;

    public SubTileSunBless(TileEntityType<?> type) {
        super(type);
    }

    public SubTileSunBless() {
        this(ModSubtiles.SUNBLESS);
    }

    @Override
    public int getMaxMana() {
        return 200;
    }

    @Override
    public int getValueForPassiveGeneration() {
        return 1;
    }

    @Override
    public int getColor() {
        return 0xFFA500;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Square(getEffectivePos(), RANGE);
    }

    @Override
    public boolean canGeneratePassively() {
        return this.getWorld().isDaytime() && this.ticksExisted % 2 == 0;
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
