package com.meteor.extrabotany.common.entity.judah;

import java.util.List;
import java.util.UUID;

import com.google.common.base.Optional;
import com.meteor.extrabotany.common.entity.EntityThrowableCopy;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import vazkii.botania.common.Botania;

public class EntityJudahOath extends EntityThrowableCopy{
	
	private static final String TAG_DAMAGE = "damage";
	private static final String TAG_ROTATION = "rotation";
	private static final String TAG_PLAYERLIST = "playerlist";
	
	private static final DataParameter<Integer> DAMAGE = EntityDataManager.createKey(EntityJudahOath.class, DataSerializers.VARINT);
	private static final DataParameter<Float> ROTATION = EntityDataManager.createKey(EntityJudahOath.class, DataSerializers.FLOAT);
	private static final DataParameter<Integer> TYPE = EntityDataManager.<Integer>createKey(EntityJudahOath.class, DataSerializers.VARINT);
	private static final DataParameter<Optional<UUID>> UUID = EntityDataManager.createKey(EntityJudahOath.class, DataSerializers.OPTIONAL_UNIQUE_ID);
	
	private EntityJudahOath.Status preStatus;
	private EntityJudahOath.Status status;
	private float range = 5F;
	private int fakecount = 0;
	private int count = 0;
	private int standby = 0;
	
	public EntityJudahOath(World worldIn) {
		super(worldIn);
	}
	
	public EntityJudahOath(World worldIn, EntityLivingBase thrower) {
		super(worldIn, thrower);
	}
	
	@Override
    public float getGravityVelocity(){
        return 0.15F;
    }
	
	@Override
	protected void entityInit() {
		setSize(0F, 0F);
		dataManager.register(DAMAGE, 0);
		dataManager.register(ROTATION, 0F);
        dataManager.register(TYPE, Integer.valueOf(EntityJudahOath.Type.JUDAH.ordinal()));
        dataManager.register(UUID, Optional.absent());
	}
	
	@Override
	public void onUpdate() {
		this.preStatus = this.status;
		if(this.status != EntityJudahOath.Status.STANDBY){
			this.motionY*=0.6F;
			this.status = getStatus();
		}else{
			this.motionZ=0;
			this.motionY=0;
			this.motionX=0;
		}
		super.onUpdate();
		
		EntityLivingBase thrower = getThrower();
		if(!world.isRemote && (thrower == null || !(thrower instanceof EntityPlayer) || thrower.isDead)) {
			setDead();
			return;
		}
		EntityPlayer player = (EntityPlayer) thrower;
		
		if(this.preStatus == EntityJudahOath.Status.INAIR && this.status == EntityJudahOath.Status.STANDBY)
			world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, posX, posY, posZ, 1D, 0D, 0D);
		
		if(this.status == EntityJudahOath.Status.STANDBY){
			this.standby++;
			
			if(this.range <=13F)
				this.range += 0.5F;
			
			if(this.ticksExisted % 4 == 0 && this.fakecount < 13){
				EntityJudahSpear spear = new EntityJudahSpear(player, this.world);
				spear.setUUID(getUniqueID());
				spear.setRotation(180F);
				spear.setPosition(this.posX, this.posY, this.posZ);
				spear.setDamage(getDamage());
				spear.setFake(true);
				spear.setType(EntityJudahSpear.Type.byId(getType().ordinal()));
				if(!this.world.isRemote)
					this.world.spawnEntity(spear);	
				this.fakecount+=1;
			}
			
			
			AxisAlignedBB axis = new AxisAlignedBB(posX-this.range+2.5F, posY-this.range+2.5F, posZ-this.range+2.5F, lastTickPosX+this.range-2.5F, lastTickPosY+this.range-2.5F, lastTickPosZ+this.range-2.5F);
			List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, axis);
			double tx = posX;
			double ty = posY + 10;
			double tz = posZ;
				
			for(EntityLivingBase living:entities){
				if(living.getUniqueID() == getUUID() || living.isDead || FMLCommonHandler.instance().getMinecraftServerInstance() != null && !FMLCommonHandler.instance().getMinecraftServerInstance().isPVPEnabled() && living instanceof EntityPlayer)
					continue;
				living.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20, 9));
				tx = living.posX;
				ty = living.posY + 10;
				tz = living.posZ;
			}
				
			if(this.standby > 20 && this.ticksExisted % 10 == 0 && this.count < 13) {		
				EntityJudahSpear spear = new EntityJudahSpear(this.world);
				spear.setUUID(getUniqueID());
				spear.setRotation(0F);
				spear.setPosition(tx,ty,tz);
				spear.setDamage(getDamage());
				spear.setFake(false);
				spear.setType(EntityJudahSpear.Type.byId(getType().ordinal()));
				if(!this.world.isRemote)
					this.world.spawnEntity(spear);	
				this.count+=1;
			}
			
			for(int i = 0; i < 360; i += 2) {
				float r=0F;
				float g=0F;
				float b=0F;
				switch(getType().metadata){
					case 0:
						r=0.85F;
						g=0.6F;
						b=0.02F;
						break;
					case 1:
						r=0.01F;
						g=0.6F;
						b=0.75F;
						break;
					case 2:
						break;
					case 3:
						break;
				}
				float rad = i * (float) Math.PI / 180F;
				double x = this.posX + 0.5 - Math.cos(rad) * this.range;
				double y = this.posY + 0.2;
				double z = this.posZ + 0.5 - Math.sin(rad) * this.range;
				Botania.proxy.sparkleFX(x, y, z, r , g, b, 1F, 3);
			}
		}
		
		if(this.standby > 140)
			this.setDead();
	}
	
	private EntityJudahOath.Status getStatus(){
		if(this.world.getBlockState(this.getPosition().add(0, -1, 0)).getBlock() != Blocks.AIR)
			return EntityJudahOath.Status.STANDBY;
		return EntityJudahOath.Status.INAIR;
	}
	
    @Override
	public void writeEntityToNBT(NBTTagCompound cmp){
        cmp.setString("Type", this.getType().getName());
        cmp.setInteger(TAG_DAMAGE, getDamage());
        cmp.setFloat(TAG_ROTATION, getRotation());
        cmp.setUniqueId(TAG_PLAYERLIST, getUUID());
    }

    @Override
	public void readEntityFromNBT(NBTTagCompound cmp){
        if (cmp.hasKey("Type", 8)){
            this.setType(EntityJudahOath.Type.getTypeFromString(cmp.getString("Type")));
        }
        setDamage(cmp.getInteger(TAG_DAMAGE));
        setRotation(cmp.getFloat(TAG_ROTATION));
        setUUID(cmp.getUniqueId(TAG_PLAYERLIST));
    }
    
	public UUID getUUID(){
		return (UUID)((Optional)this.dataManager.get(UUID)).orNull();
	}
	
	public void setUUID(UUID u){
		dataManager.set(UUID, Optional.fromNullable(u));
	}
    
    public void setType(EntityJudahOath.Type raftType){
        this.dataManager.set(TYPE, Integer.valueOf(raftType.ordinal()));
    }

    public EntityJudahOath.Type getType(){
        return EntityJudahOath.Type.byId(((Integer)this.dataManager.get(TYPE)).intValue());
    }
    
	public int getDamage() {
		return dataManager.get(DAMAGE);
	}

	public void setDamage(int delay) {
		dataManager.set(DAMAGE, delay);
	}
	
	public float getRotation() {
		return dataManager.get(ROTATION);
	}

	public void setRotation(float rot) {
		dataManager.set(ROTATION, rot);
	}
	
    public static enum Status{
        INAIR,
        STANDBY;
    }
	
    public static enum Type{
    	JUDAH(0, "judah"),
        KIRA(1, "kira"),
        ETERNITY(2, "eternity"),
        SAKURA(2, "sakura");

        private final String name;
        private final int metadata;

        private Type(int metadataIn, String nameIn){
            this.name = nameIn;
            this.metadata = metadataIn;
        }

        public String getName(){
            return this.name;
        }

        public int getMetadata(){
            return this.metadata;
        }

        public String toString(){
            return this.name;
        }

        public static EntityJudahOath.Type byId(int id){
            if (id < 0 || id >= values().length){
                id = 0;
            }

            return values()[id];
        }

        public static EntityJudahOath.Type getTypeFromString(String nameIn){
            for (int i = 0; i < values().length; ++i){
                if (values()[i].getName().equals(nameIn)){
                    return values()[i];
                }
            }

            return values()[0];
        }
    }

	@Override
	protected void onImpact(RayTraceResult result) {
		
	}

}
