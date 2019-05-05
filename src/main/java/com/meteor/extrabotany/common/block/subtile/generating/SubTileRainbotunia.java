package com.meteor.extrabotany.common.block.subtile.generating;

import vazkii.botania.api.subtile.SubTileGenerating;

public class SubTileRainbotunia extends SubTileGenerating {

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (redstoneSignal > 0)
            return;

    }

    @Override
    public int getMaxMana() {
        return 50000;
    }

}
