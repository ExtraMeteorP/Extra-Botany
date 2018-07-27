package com.meteor.extrabotany.common.block;

import com.meteor.extrabotany.common.lib.LibBlocksName;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockOrichalcos extends BlockMod{

	public BlockOrichalcos() {
		super(Material.IRON, LibBlocksName.BLOCKORICHALCOS);
		setHardness(5.5F);
		setSoundType(SoundType.STONE);
	}

}
