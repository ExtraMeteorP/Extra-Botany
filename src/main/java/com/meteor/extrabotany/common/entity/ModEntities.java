package com.meteor.extrabotany.common.entity;

import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {
	
	public static void init() {
		int id = 0;
		EntityRegistry.registerModEntity(makeName("flower_weapon"), EntityFlowerWeapon.class, "extrabotany:flowerWeapon", id++, ExtraBotany.instance, 64, 10, true);
	}
	
	private static ResourceLocation makeName(String s) {
		return new ResourceLocation(LibMisc.MOD_ID, s);
	}

}
