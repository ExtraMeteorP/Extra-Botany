package com.meteor.extrabotany.common.entity;

import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.common.entity.gaia.EntityDomain;
import com.meteor.extrabotany.common.entity.gaia.EntityGaiaIII;
import com.meteor.extrabotany.common.entity.gaia.EntitySkullLandmine;
import com.meteor.extrabotany.common.entity.gaia.EntitySkullMinion;
import com.meteor.extrabotany.common.entity.gaia.EntitySkullMissile;
import com.meteor.extrabotany.common.entity.gaia.EntitySubspaceLance;
import com.meteor.extrabotany.common.entity.gaia.EntitySwordDomain;
import com.meteor.extrabotany.common.entity.gaia.EntityVoid;
import com.meteor.extrabotany.common.entity.gaia.EntityVoidHerrscher;
import com.meteor.extrabotany.common.entity.judah.EntityJudahOath;
import com.meteor.extrabotany.common.entity.judah.EntityJudahSpear;
import com.meteor.extrabotany.common.entity.judah.EntityJudahSword;
import com.meteor.extrabotany.common.lib.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {

	public static void init() {
		int id = 0;
		EntityRegistry.registerModEntity(makeName("flower_weapon"), EntityFlowerWeapon.class,
				"extrabotany:flowerWeapon", id++, ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("gaiaIII"), EntityGaiaIII.class, "extrabotany:gaiaIII", id++,
				ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("skullmissile"), EntitySkullMissile.class, "extrabotany:skullmissile",
				id++, ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("skulllandmine"), EntitySkullLandmine.class,
				"extrabotany:skulllandmine", id++, ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("skullminion"), EntitySkullMinion.class, "extrabotany:skullminion",
				id++, ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("magicarrow"), EntityMagicArrow.class, "extrabotany:magicarrow", id++,
				ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("splashgrenade"), EntitySplashGrenade.class,
				"extrabotany:splashgrenade", id++, ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("fb"), EntityFlyingBoat.class, "extrabotany:fb", id++,
				ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("darkpixie"), EntityDarkPixie.class, "extrabotany:darkpixie", id++,
				ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("sworddomain"), EntitySwordDomain.class, "extrabotany:sworddomain",
				id++, ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("domain"), EntityDomain.class, "extrabotany:domain", id++,
				ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("subspace"), EntitySubspace.class, "extrabotany:subspace", id++,
				ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("subspacespear"), EntitySubspaceSpear.class,
				"extrabotany:subspacespear", id++, ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("judahspear"), EntityJudahSpear.class, "extrabotany:judahspear", id++,
				ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("judahoath"), EntityJudahOath.class, "extrabotany:judahoath", id++,
				ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("petpixie"), EntityPetPixie.class, "extrabotany:petpixie", id++,
				ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("bottledstar"), EntityBottledStar.class, "extrabotany:bottledstar",
				id++, ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("void"), EntityVoid.class, "extrabotany:void", id++,
				ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("voidherrscher"), EntityVoidHerrscher.class,
				"extrabotany:voidherrscher", id++, ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("subspacelance"), EntitySubspaceLance.class,
				"extrabotany:subspacelance", id++, ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("aurafire"), EntityAuraFire.class, "extrabotany:aurafire", id++,
				ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("judahsword"), EntityJudahSword.class, "extrabotany:judahsword", id++,
				ExtraBotany.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("phantomsword"), EntityPhantomSword.class, "extrabotany:phantomsword", id++,
				ExtraBotany.instance, 64, 10, true);
	}

	private static ResourceLocation makeName(String s) {
		return new ResourceLocation(Reference.MOD_ID, s);
	}

}
