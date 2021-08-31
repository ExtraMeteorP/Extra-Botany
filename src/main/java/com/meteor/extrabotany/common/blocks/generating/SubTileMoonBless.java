package com.meteor.extrabotany.common.blocks.generating;

import com.meteor.extrabotany.common.blocks.ModSubtiles;

public class SubTileMoonBless extends SubTileSunBless{

    public SubTileMoonBless() {
        super(ModSubtiles.MOONBLESS);
    }

    @Override
    public int getColor() {
        return 0xFFFF00;
    }

    @Override
    public int getValueForPassiveGeneration() {
        return 1;
    }

    @Override
    public boolean canGeneratePassively() {
        return !this.getWorld().isDaytime() && this.ticksExisted % 4 == 0;
    }

}
