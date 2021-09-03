package com.meteor.extrabotany.common.entities.ego;

import com.meteor.extrabotany.common.core.Helper;
import com.meteor.extrabotany.common.entities.ModEntities;
import com.meteor.extrabotany.common.handler.DamageHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import vazkii.botania.client.fx.WispParticleData;
import vazkii.botania.common.core.handler.ModSounds;

import javax.annotation.Nonnull;
import java.util.List;

public class EntityEGOLandmine extends Entity {

    public EntityEGO summoner;

    private static final String TAG_TYPE = "type";

    private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntityEGOLandmine.class, DataSerializers.VARINT);

    public EntityEGOLandmine(EntityType<EntityEGOLandmine> type, World world) {
        super(type, world);
    }

    public EntityEGOLandmine(World world) {
        super(ModEntities.EGOLANDMINE, world);
    }

    public static void spawnLandmine(int wave, World world, BlockPos source, EntityEGO ego){
        Vector3d vecSource = Helper.PosToVec(source);
        Vector3d unit = new Vector3d(2, 0, 0);
        if(!world.isRemote) {
            switch (wave) {
                case 0: {

                    for (int i = 0; i < 8; i++) {
                        unit = unit.rotateYaw((float) (Math.PI / 4F * i));
                        for (int j = 0; j < 8; j++) {
                            Vector3d end = vecSource.add(unit.mul(j + 1, j + 1, j + 1));
                            int k = j % 4 == 0 ? 2 : 0;
                            EntityEGOLandmine landmine = new EntityEGOLandmine(world);
                            landmine.summoner = ego;
                            landmine.setPosition(end.x, end.y, end.z);
                            landmine.setLandmineType(k);
                            world.addEntity(landmine);
                        }
                    }

                    break;
                }

                case 1:{
                    for(int i = 0; i < 5; i++){

                        for(int j = 0; j < 16; j++){
                            Vector3d u = unit.add(new Vector3d(3, 0, 0).mul(i, 0, 0));
                            u = u.rotateYaw((float) (Math.PI / 8F * j));
                            Vector3d end = vecSource.add(u);
                            int k = i % 3;
                            EntityEGOLandmine landmine = new EntityEGOLandmine(world);
                            landmine.summoner = ego;
                            landmine.setPosition(end.x, end.y, end.z);
                            landmine.setLandmineType(k);
                            world.addEntity(landmine);
                        }

                    }
                    break;
                }

                case 2:{
                    for(int i = 0; i < 72; i++){
                        double p = i * Math.PI / 12F;
                        double r = 1 + 1 * p;
                        double x = r * Math.cos(p);
                        double z = r * Math.sin(p);
                        double y = vecSource.y;
                        int k = i % 5 == 0 ? 2 : 0;
                        EntityEGOLandmine landmine = new EntityEGOLandmine(world);
                        landmine.summoner = ego;
                        landmine.setPosition(vecSource.x + x,y,vecSource.z + z);
                        landmine.setLandmineType(k);
                        world.addEntity(landmine);
                    }
                    break;
                }

                case 3:{
                    for(int i = 0; i < 80; i++){
                        double p = i * Math.PI / 80F;
                        double r = 24 * Math.sin(5F * p);
                        double x = r * Math.cos(p);
                        double z = r * Math.sin(p);
                        double y = vecSource.y;
                        int k = i % 4 == 0 ? 2 : 0;
                        EntityEGOLandmine landmine = new EntityEGOLandmine(world);
                        landmine.summoner = ego;
                        landmine.setPosition(vecSource.x + x,y,vecSource.z + z);
                        landmine.setLandmineType(k);
                        world.addEntity(landmine);
                    }
                    break;
                }

                case 4:{
                    for(int i = 0; i < 8; i++){
                        for(int j = 0; j < 16; j++){
                            Vector3d u = unit.mul(3, 0, 3);
                            u = u.rotateYaw((float) (Math.PI / 8F * j));
                            Vector3d end = vecSource.add(unit.mul(6, 0, 6).rotateYaw((float) (Math.PI/4 * i))).add(u);
                            int k = i % 3;
                            EntityEGOLandmine landmine = new EntityEGOLandmine(world);
                            landmine.summoner = ego;
                            landmine.setPosition(end.x, end.y, end.z);
                            landmine.setLandmineType(k);
                            world.addEntity(landmine);
                        }
                    }
                    break;
                }

                case 5:{
                    for(int i = 0; i < 6; i++){
                        Vector3d mp = vecSource.add(unit.mul(5, 0, 5).rotateYaw((float) (Math.PI * 2 / 6F * i)));
                        EntityEGOLandmine mid = new EntityEGOLandmine(world);
                        mid.summoner = ego;
                        mid.setPosition(mp.x, mp.y, mp.z);
                        mid.setLandmineType(0);
                        world.addEntity(mid);
                        for(int j = 0; j < 16; j++){
                            Vector3d u = unit.mul(2, 0, 2).rotateYaw((float) (Math.PI / 8F * j));
                            Vector3d end = mp.add(u);
                            EntityEGOLandmine landmine = new EntityEGOLandmine(world);
                            landmine.summoner = ego;
                            landmine.setPosition(end.x, end.y, end.z);
                            landmine.setLandmineType(2);
                            world.addEntity(landmine);
                        }
                    }
                    break;
                }

                case 6:{
                    for(int i = 0; i < 72; i++){
                        Vector3d mp = vecSource.add(unit.mul(7, 0, 7).rotateYaw((float) (Math.PI * 2 / 72F * i)));
                        EntityEGOLandmine mid = new EntityEGOLandmine(world);
                        mid.summoner = ego;
                        mid.setPosition(mp.x, mp.y, mp.z);
                        mid.setLandmineType(2);
                        world.addEntity(mid);
                        if(i % 5 == 0){
                            for(int j = 0; j < 12; j++){
                                Vector3d u = unit.mul(4, 0, 4).rotateYaw((float) (Math.PI / 6F * j));
                                Vector3d end = mp.add(u);
                                EntityEGOLandmine landmine = new EntityEGOLandmine(world);
                                landmine.summoner = ego;
                                landmine.setPosition(end.x, end.y, end.z);
                                landmine.setLandmineType(i % 2);
                                world.addEntity(landmine);
                            }
                        }
                    }
                    break;
                }

                case 7:{
                    for(int i = 0; i < 6; i++){
                        for(int l1 = 0; l1 < 11; l1++){
                            Vector3d mp = vecSource.add(unit.mul(l1, 0, l1).rotateYaw((float) (Math.PI * 2 / 6F * i)));
                            EntityEGOLandmine mid = new EntityEGOLandmine(world);
                            mid.summoner = ego;
                            mid.setPosition(mp.x, mp.y, mp.z);
                            mid.setLandmineType(1);
                            world.addEntity(mid);
                            if(l1 == 5){
                                for(int j = 0; j < 6; j++){
                                    for(int l2 = 0; l2 < 7; l2++){
                                        Vector3d end = mp.add(unit.mul(l2*0.6F, 0, l2*0.6F).rotateYaw((float) (Math.PI * 2 / 6F * j + Math.PI / 6F)));
                                        EntityEGOLandmine landmine = new EntityEGOLandmine(world);
                                        landmine.summoner = ego;
                                        landmine.setPosition(end.x, end.y, end.z);
                                        landmine.setLandmineType(j % 3);
                                        world.addEntity(landmine);
                                    }
                                }
                            }
                        }

                    }
                    break;
                }
            }
        }
    }

    @Override
    public void tick() {
        setMotion(Vector3d.ZERO);
        super.tick();

        float range = getWidth() / 2;
        float r = 0F;
        float g = 0F;
        float b = 0F;

        switch (getLandmineType()){
            case 0:
                b = 1F;
                break;
            case 1:
                g = 1F;
                break;
            case 2:
                r = 1F;
                break;
        }

        if(ticksExisted % 2 == 0)
            for (int i = 0; i < 2; i++) {
                WispParticleData data = WispParticleData.wisp(0.4F, r, g, b, (float) 1);
                world.addParticle(data, getPosX() - range + Math.random() * range * 2, getPosY(), getPosZ() - range + Math.random() * range * 2, 0, - -0.015F, 0);
            }

        if (ticksExisted >= 50) {
            world.playSound(null, getPosX(), getPosY(), getPosZ(), ModSounds.gaiaTrap, SoundCategory.NEUTRAL, 0.3F, 1F);

            float m = 0.35F;
            for (int i = 0; i < 4; i++) {
                WispParticleData data = WispParticleData.wisp(0.5F, r, g, b);
                world.addParticle(data, getPosX(), getPosY() + 1, getPosZ(), (float) (Math.random() - 0.5F) * m, (float) (Math.random() - 0.5F) * m, (float) (Math.random() - 0.5F) * m);
            }

            if (!world.isRemote) {
                List<PlayerEntity> players = world.getEntitiesWithinAABB(PlayerEntity.class, getBoundingBox().grow(0, 12, 0));
                for (PlayerEntity player : players) {
                    DamageHandler.INSTANCE.dmg(player, summoner, 5F, DamageHandler.INSTANCE.LIFE_LOSING);
                    player.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, summoner), 10F);
                    player.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 25, 0));
                    EffectInstance wither = new EffectInstance(Effects.WITHER, 120, 2);
                    wither.getCurativeItems().clear();
                    player.addPotionEffect(wither);

                    switch (getLandmineType()){
                        case 1:
                            player.drop(true);
                            break;
                        case 2:
                            DamageHandler.INSTANCE.dmg(player, summoner, 10F, DamageHandler.INSTANCE.LIFE_LOSING);
                            break;
                    }
                }
            }

            remove();
        }
    }

    @Override
    protected void registerData() {
        dataManager.register(TYPE, 0);
    }

    @Override
    protected void readAdditional(@Nonnull CompoundNBT var1) {
        setLandmineType(var1.getInt(TAG_TYPE));
    }

    @Override
    protected void writeAdditional(@Nonnull CompoundNBT var1) {
        var1.putInt(TAG_TYPE, getLandmineType());
    }

    public int getLandmineType(){
        return dataManager.get(TYPE);
    }

    public void setLandmineType(int i){
        dataManager.set(TYPE, i);
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
