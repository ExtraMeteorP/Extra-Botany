package com.meteor.extrabotany.common.entity;

import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {
	
	public static void init() {
		int id = 0;
		EntityRegistry.registerModEntity(makeName("flower_weapon"), EntityFlowerWeapon.class, "extrabotany:flowerWeapon", id++, ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("gaiaIII"), EntityGaiaIII.class, "extrabotany:gaiaIII", id++, ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("skullmissile"), EntitySkullMissile.class, "extrabotany:skullmissile", id++, ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("skulllandmine"), EntitySkullLandmine.class, "extrabotany:skulllandmine", id++, ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("skullminion"), EntitySkullMinion.class, "extrabotany:skullminion", id++, ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("magicarrow"), EntityMagicArrow.class, "extrabotany:magicarrow", id++, ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("splashgrenade"), EntitySplashGrenade.class, "extrabotany:splashgrenade", id++, ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("fb"), EntityFlyingBoat.class, "extrabotany:fb", id++, ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("darkpixie"), EntityDarkPixie.class, "extrabotany:darkpixie", id++, ExtraBotany.instance, 64, 10, true);
	}
	
	private static ResourceLocation makeName(String s) {
		return new ResourceLocation(LibMisc.MOD_ID, s);
	}

}
