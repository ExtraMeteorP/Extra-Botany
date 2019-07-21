package com.meteor.extrabotany.common.entity.judah;

import java.util.List;
import java.util.UUID;

import com.google.common.base.Optional;
import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.brew.ModPotions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import vazkii.botania.common.Botania;

public class EntityJudahSpear extends Entity{
	
    private static final String TAG_DAMAGE = "damage";
    private static final String TAG_FAKE = "fake";
    private static final String TAG_FLAG = "flag";
    private static final String TAG_ROTATION = "rotation";
    private static final String TAG_PLAYERLIST = "playerlist";

    private static final DataParameter<Float> DAMAGE = EntityDataManager.createKey(EntityJudahSpear.class, DataSerializers.FLOAT);
    private static final DataParameter<Boolean> FAKE = EntityDataManager.createKey(EntityJudahSpear.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> FLAG = EntityDataManager.createKey(EntityJudahSpear.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> TYPE = EntityDataManager.<Integer>createKey(EntityJudahSpear.class, DataSerializers.VARINT);
    private static final DataParameter<Float> ROTATION = EntityDataManager.createKey(EntityJudahSpear.class, DataSerializers.FLOAT);
    private static final DataParameter<Optional<UUID>> UUID = EntityDataManager.createKey(EntityJudahSpear.class, DataSerializers.OPTIONAL_UNIQUE_ID);

    private static EntityPlayer thrower;
	
	public EntityJudahSpear(World worldIn) {
        super(worldIn);
    }

    public EntityJudahSpear(EntityPlayer thrower, World worldIn) {
        super(worldIn);
        this.thrower = thrower;
    }

    @Override
    protected void entityInit() {
        dataManager.register(ROTATION, 0F);
        dataManager.register(FAKE, false);
        dataManager.register(FLAG, false);
        dataManager.register(DAMAGE, 0F);
        dataManager.register(TYPE, Integer.valueOf(EntityJudahSpear.Type.JUDAH.ordinal()));
        dataManager.register(UUID, Optional.absent());
    }

    @Override
    public void onUpdate() {
        float r = 0F;
        float g = 0F;
        float b = 0F;
        switch (getType().metadata) {
            case 0:
                r = 0.85F;
                g = 0.6F;
                b = 0.02F;
                break;
            case 1:
                r = 0.01F;
                g = 0.6F;
                b = 0.75F;
                break;
            case 2:
                break;
            case 3:
                break;
        }
        Botania.proxy.sparkleFX(posX, posY, posZ, r, g, b, 1F, 1);
        super.onUpdate();
        this.motionX = 0;
        this.motionZ = 0;
        this.motionY = 0;
        if (getFake())
            this.posY += 0.75F;
        else
            this.posY -= 0.95F;

        if (!world.isRemote && !getFake()) {
            AxisAlignedBB axis = new AxisAlignedBB(posX - 1.3F, posY - 6F, posZ - 1.3F, lastTickPosX + 1.3F, lastTickPosY + 5F, lastTickPosZ + 1.3F);
            List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, axis);
            for (EntityLivingBase living : entities) {
                if (living.getUniqueID() == getUUID() || FMLCommonHandler.instance().getMinecraftServerInstance() != null && !FMLCommonHandler.instance().getMinecraftServerInstance().isPVPEnabled() && living instanceof EntityPlayer)
                    continue;

                if (getFlag() == false) {
                    for (int i = 0; i < 4; i++)
                        Botania.proxy.wispFX(living.posX, living.posY + 0.5F, living.posZ, r, g, b, 0.45F, (float) (Math.random() - 0.5F) * 0.5F, (float) (Math.random() - 0.5F) * 0.5F, (float) (Math.random() - 0.5F) * 0.5F);
                    if (ExtraBotany.isTableclothServer && thrower != null)
                        living.attackEntityFrom(DamageSource.causePlayerDamage(thrower), getDamage() * 1.25F);
                    else
                        living.attackEntityFrom(DamageSource.LIGHTNING_BOLT, getDamage() * 1.25F);
                    ExtraBotanyAPI.addPotionEffect(living, ModPotions.divinejustice, 4);
                    ExtraBotanyAPI.dealTrueDamage(thrower, living, getDamage() * 0.35F);
                    setFlag(true);
                }

            }
        }

        if (this.ticksExisted > 100)
            this.setDead();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound cmp) {
        cmp.setString("Type", this.getType().getName());
        cmp.setFloat(TAG_DAMAGE, getDamage());
        cmp.setBoolean(TAG_FAKE, getFake());
        cmp.setBoolean(TAG_FLAG, getFlag());
        cmp.setFloat(TAG_ROTATION, getRotation());
        cmp.setUniqueId(TAG_PLAYERLIST, getUUID());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound cmp) {
        if (cmp.hasKey("Type", 8)) {
            this.setType(EntityJudahSpear.Type.getTypeFromString(cmp.getString("Type")));
        }
        setDamage(cmp.getFloat(TAG_DAMAGE));
        setFake(cmp.getBoolean(TAG_FAKE));
        setFlag(cmp.getBoolean(TAG_FLAG));
        setRotation(cmp.getFloat(TAG_ROTATION));
        setUUID(cmp.getUniqueId(TAG_PLAYERLIST));
    }

    public UUID getUUID() {
        return (UUID) ((Optional) this.dataManager.get(UUID)).orNull();
    }

    public void setUUID(UUID u) {
        dataManager.set(UUID, Optional.fromNullable(u));
    }

    public EntityJudahSpear.Type getType() {
        return EntityJudahSpear.Type.byId(((Integer) this.dataManager.get(TYPE)).intValue());
    }

    public void setType(EntityJudahSpear.Type raftType) {
        this.dataManager.set(TYPE, Integer.valueOf(raftType.ordinal()));
    }

    public float getDamage() {
        return dataManager.get(DAMAGE);
    }

    public void setDamage(float delay) {
        dataManager.set(DAMAGE, delay);
    }

    public boolean getFake() {
        return dataManager.get(FAKE);
    }

    public void setFake(boolean delay) {
        dataManager.set(FAKE, delay);
    }

    public boolean getFlag() {
        return dataManager.get(FLAG);
    }

    public void setFlag(boolean delay) {
        dataManager.set(FLAG, delay);
    }

    public float getRotation() {
        return dataManager.get(ROTATION);
    }

    public void setRotation(float rot) {
        dataManager.set(ROTATION, rot);
    }

    public static enum Type {
        JUDAH(0, "judah"),
        KIRA(1, "kira"),
        SAKURA(2, "sakura");

        private final String name;
        private final int metadata;

        private Type(int metadataIn, String nameIn) {
            this.name = nameIn;
            this.metadata = metadataIn;
        }

        public static EntityJudahSpear.Type byId(int id) {
            if (id < 0 || id >= values().length) {
                id = 0;
            }

            return values()[id];
        }

        public static EntityJudahSpear.Type getTypeFromString(String nameIn) {
            for (int i = 0; i < values().length; ++i) {
                if (values()[i].getName().equals(nameIn)) {
                    return values()[i];
                }
            }

            return values()[0];
        }

        public String getName() {
            return this.name;
        }

        public int getMetadata() {
            return this.metadata;
        }

        public String toString() {
            return this.name;
        }
    }

}
