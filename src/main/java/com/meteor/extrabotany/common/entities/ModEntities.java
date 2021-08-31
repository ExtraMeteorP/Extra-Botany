package com.meteor.extrabotany.common.entities;

import com.meteor.extrabotany.common.entities.ego.EntityEGO;
import com.meteor.extrabotany.common.entities.ego.EntityEGOLandmine;
import com.meteor.extrabotany.common.entities.ego.EntityEGOMinion;
import com.meteor.extrabotany.common.entities.mountable.EntityMotor;
import com.meteor.extrabotany.common.entities.mountable.EntityUfo;
import com.meteor.extrabotany.common.entities.projectile.*;
import com.meteor.extrabotany.common.libs.LibEntityNames;
import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class ModEntities {

    public static final EntityType<EntityMotor> MOTOR = EntityType.Builder.<EntityMotor>create(
            EntityMotor::new, EntityClassification.MISC)
            .size(1.675F, 1.875F)
            .setUpdateInterval(10)
            .setTrackingRange(64)
            .setShouldReceiveVelocityUpdates(true)
            .build("");

    public static final EntityType<EntityKeyOfTruth> KEY_OF_TRUTH = EntityType.Builder.<EntityKeyOfTruth>create(
            EntityKeyOfTruth::new, EntityClassification.MISC)
            .size(0.1F, 0.1F)
            .setUpdateInterval(10)
            .setTrackingRange(64)
            .setShouldReceiveVelocityUpdates(true)
            .build("");

    public static final EntityType<EntitySlash> SLASH = EntityType.Builder.<EntitySlash>create(
            EntitySlash::new, EntityClassification.MISC)
            .size(0.1F, 0.1F)
            .setUpdateInterval(10)
            .setTrackingRange(64)
            .setShouldReceiveVelocityUpdates(true)
            .build("");

    public static final EntityType<EntityUfo> UFO = EntityType.Builder.<EntityUfo>create(
            EntityUfo::new, EntityClassification.MISC)
            .size(1.5F, 1.5F)
            .setUpdateInterval(10)
            .setTrackingRange(64)
            .setShouldReceiveVelocityUpdates(true)
            .build("");

    public static final EntityType<EntityPhantomSword> PHANTOMSWORD = EntityType.Builder.<EntityPhantomSword>create(
            EntityPhantomSword::new, EntityClassification.MISC)
            .size(0.1F, 0.1F)
            .setUpdateInterval(10)
            .setTrackingRange(64)
            .setShouldReceiveVelocityUpdates(true)
            .build("");

    public static final EntityType<EntityFlamescionSlash> FLAMESCIONSLASH = EntityType.Builder.<EntityFlamescionSlash>create(
            EntityFlamescionSlash::new, EntityClassification.MISC)
            .size(0.1F, 0.1F)
            .setUpdateInterval(10)
            .setTrackingRange(64)
            .setShouldReceiveVelocityUpdates(true)
            .build("");

    public static final EntityType<EntityStrengthenSlash> SRENGTHENSLASH = EntityType.Builder.<EntityStrengthenSlash>create(
            EntityStrengthenSlash::new, EntityClassification.MISC)
            .size(0.1F, 0.1F)
            .setUpdateInterval(10)
            .setTrackingRange(64)
            .setShouldReceiveVelocityUpdates(true)
            .build("");

    public static final EntityType<EntityFlamescionUlt> ULT = EntityType.Builder.<EntityFlamescionUlt>create(
            EntityFlamescionUlt::new, EntityClassification.MISC)
            .size(0.1F, 0.1F)
            .setUpdateInterval(10)
            .setTrackingRange(64)
            .setShouldReceiveVelocityUpdates(true)
            .build("");

    public static final EntityType<EntityFlamescionVoid> VOID = EntityType.Builder.<EntityFlamescionVoid>create(
            EntityFlamescionVoid::new, EntityClassification.MISC)
            .size(0.1F, 0.1F)
            .setUpdateInterval(10)
            .setTrackingRange(64)
            .setShouldReceiveVelocityUpdates(true)
            .build("");

    public static final EntityType<EntityFlamescionSword> SWORD = EntityType.Builder.<EntityFlamescionSword>create(
            EntityFlamescionSword::new, EntityClassification.MISC)
            .size(0.1F, 0.1F)
            .setUpdateInterval(10)
            .setTrackingRange(64)
            .setShouldReceiveVelocityUpdates(true)
            .build("");

    public static final EntityType<EntityMagicArrow> MAGICARROW = EntityType.Builder.<EntityMagicArrow>create(
            EntityMagicArrow::new, EntityClassification.MISC)
            .size(0.05F, 0.05F)
            .setUpdateInterval(10)
            .setTrackingRange(64)
            .setShouldReceiveVelocityUpdates(true)
            .build("");

    public static final EntityType<EntitySplashGrenade> SPLASHGRENADE = EntityType.Builder.<EntitySplashGrenade>create(
            EntitySplashGrenade::new, EntityClassification.MISC)
            .size(0.05F, 0.05F)
            .setUpdateInterval(10)
            .setTrackingRange(64)
            .setShouldReceiveVelocityUpdates(true)
            .build("");

    public static final EntityType<EntityInfluxWaverProjectile> INFLUXWAVER = EntityType.Builder.<EntityInfluxWaverProjectile>create(
            EntityInfluxWaverProjectile::new, EntityClassification.MISC)
            .size(0.1F, 0.1F)
            .setUpdateInterval(10)
            .setTrackingRange(64)
            .setShouldReceiveVelocityUpdates(true)
            .build("");

    public static final EntityType<EntityTrueTerrabladeProjectile> TRUETERRABLADE = EntityType.Builder.<EntityTrueTerrabladeProjectile>create(
            EntityTrueTerrabladeProjectile::new, EntityClassification.MISC)
            .size(0.1F, 0.1F)
            .setUpdateInterval(10)
            .setTrackingRange(64)
            .setShouldReceiveVelocityUpdates(true)
            .build("");

    public static final EntityType<EntityTrueShadowKatanaProjectile> TRUESHADOWKATANA = EntityType.Builder.<EntityTrueShadowKatanaProjectile>create(
            EntityTrueShadowKatanaProjectile::new, EntityClassification.MISC)
            .size(0.1F, 0.1F)
            .setUpdateInterval(10)
            .setTrackingRange(64)
            .setShouldReceiveVelocityUpdates(true)
            .build("");

    public static final EntityType<EntityAuraFire> AURAFIRE = EntityType.Builder.<EntityAuraFire>create(
            EntityAuraFire::new, EntityClassification.MISC)
            .size(0.1F, 0.1F)
            .setUpdateInterval(10)
            .setTrackingRange(64)
            .setShouldReceiveVelocityUpdates(true)
            .build("");

    public static final EntityType<EntityEGO> EGO = EntityType.Builder.<EntityEGO>create(
            EntityEGO::new, EntityClassification.CREATURE)
            .size(0.6F, 1.8F)
            .setUpdateInterval(2)
            .setTrackingRange(128)
            .setShouldReceiveVelocityUpdates(true)
            .build("");

    public static final EntityType<EntityEGOMinion> EGOMINION = EntityType.Builder.<EntityEGOMinion>create(
            EntityEGOMinion::new, EntityClassification.CREATURE)
            .size(0.6F, 1.8F)
            .setUpdateInterval(2)
            .setTrackingRange(128)
            .setShouldReceiveVelocityUpdates(true)
            .build("");

    public static final EntityType<EntityEGOLandmine> EGOLANDMINE = EntityType.Builder.<EntityEGOLandmine>create(
            EntityEGOLandmine::new, EntityClassification.MISC)
            .size(3F, 0.1F)
            .setUpdateInterval(2)
            .setTrackingRange(128)
            .setShouldReceiveVelocityUpdates(true)
            .build("");

    public static void registerEntities(RegistryEvent.Register<EntityType<?>> evt) {
        IForgeRegistry<EntityType<?>> r = evt.getRegistry();
        register(r, LibEntityNames.KEYOFTRUTH, KEY_OF_TRUTH);
        register(r, LibEntityNames.MOTOR, MOTOR);
        register(r, LibEntityNames.SLASH, SLASH);
        register(r, LibEntityNames.UFO, UFO);
        register(r, LibEntityNames.PHANTONSWORD, PHANTOMSWORD);
        register(r, LibEntityNames.FLAMESCIONSLASH, FLAMESCIONSLASH);
        register(r, LibEntityNames.STRENGTHENSLASH, SRENGTHENSLASH);
        register(r, LibEntityNames.FLAMESCIONULT, ULT);
        register(r, LibEntityNames.FLAMESCIONSWORD, SWORD);
        register(r, LibEntityNames.VOID, VOID);
        register(r, LibEntityNames.MAGICARROW, MAGICARROW);
        register(r, LibEntityNames.SPLASHGRENADE, SPLASHGRENADE);

        register(r, LibEntityNames.EGO, EGO);
        register(r, LibEntityNames.EGOMINION, EGOMINION);
        register(r, LibEntityNames.EGOLANDMINE, EGOLANDMINE);

        register(r, LibEntityNames.AURAFIRE, AURAFIRE);
        register(r, LibEntityNames.INFLUXWAVER_PROJECTILE, INFLUXWAVER);
        register(r, LibEntityNames.TRUETERRABLADE_PROJECTILE,TRUETERRABLADE);
        register(r, LibEntityNames.TRUESHADOWKATANA_PROJECTILE, TRUESHADOWKATANA);
    }

    public static <V extends IForgeRegistryEntry<V>> void register(IForgeRegistry<V> reg, ResourceLocation name, IForgeRegistryEntry<V> thing) {
        reg.register(thing.setRegistryName(name));
    }

    public static <V extends IForgeRegistryEntry<V>> void register(IForgeRegistry<V> reg, String name, IForgeRegistryEntry<V> thing) {
        register(reg, new ResourceLocation(LibMisc.MOD_ID, name), thing);
    }

}
