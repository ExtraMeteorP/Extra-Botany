package com.meteor.extrabotany.common.blocks;

import net.minecraft.potion.Effect;
import vazkii.botania.api.subtile.TileEntitySpecialFlower;

import java.util.function.Supplier;

public class BlockSpecialFlower extends vazkii.botania.common.block.BlockSpecialFlower {

    public BlockSpecialFlower(Effect stewEffect, int stewDuration, Properties props, Supplier<? extends TileEntitySpecialFlower> teProvider) {
        super(stewEffect, stewDuration, props, teProvider);
    }

}
