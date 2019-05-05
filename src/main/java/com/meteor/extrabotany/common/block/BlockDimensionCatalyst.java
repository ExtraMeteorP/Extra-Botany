package com.meteor.extrabotany.common.block;

import com.meteor.extrabotany.common.lib.LibBlocksName;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockDimensionCatalyst extends BlockMod {

    public BlockDimensionCatalyst() {
        super(Material.IRON, LibBlocksName.BLOCKDIMENSIONCATALYST);
        setHardness(2.0F);
        setResistance(10.0F);
        setSoundType(SoundType.STONE);
    }

}
