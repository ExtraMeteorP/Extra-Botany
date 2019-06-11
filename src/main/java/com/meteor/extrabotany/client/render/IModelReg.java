package com.meteor.extrabotany.client.render;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModelReg {
	
	@SideOnly(Side.CLIENT)
	void registerModels();
}
