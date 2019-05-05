package com.meteor.extrabotany.common.block.tile;

import com.meteor.extrabotany.common.core.config.ConfigHandler;

public class TileQuantumManaBuffer extends TileManaBuffer {

    @Override
    public int getMaxMana() {
        return 64000000 * 16;
    }

    @Override
    public int getTransferSpeed() {
        return ConfigHandler.MB_SPEED * 32;
    }

}
