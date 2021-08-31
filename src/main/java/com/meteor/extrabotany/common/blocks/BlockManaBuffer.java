package com.meteor.extrabotany.common.blocks;

import com.meteor.extrabotany.common.blocks.tile.TileManaBuffer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import vazkii.botania.common.block.BlockMod;

import javax.annotation.Nullable;

public class BlockManaBuffer extends BlockMod implements ITileEntityProvider {

    public BlockManaBuffer(Properties builder) {
        super(builder);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new TileManaBuffer();
    }
}
