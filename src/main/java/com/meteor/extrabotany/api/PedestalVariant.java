package com.meteor.extrabotany.api;

import java.util.Locale;

import net.minecraft.util.IStringSerializable;

public enum PedestalVariant implements IStringSerializable {
	DEFAULT,
	FOREST,
	PLAINS,
	MOUNTAIN,
	FUNGAL,
	SWAMP,
	DESERT,
	TAIGA,
	MESA,
	MOSSY;
	
	@Override
	public String getName() {
		return name().toLowerCase(Locale.ROOT);
	}

}
