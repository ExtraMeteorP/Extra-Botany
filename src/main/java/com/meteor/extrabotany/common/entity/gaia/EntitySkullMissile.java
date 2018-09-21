package com.meteor.extrabotany.common.entity.gaia;

import java.util.List;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.entity.EntityThrowableCopy;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeaves;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.helper.Vector3;

public class EntitySkullMissile extends EntityThrowableCopy{
	
	public EntitySkullMissile(World world) {
		super(world);
	}

	public EntitySkullMissile(EntityLivingBase thrower) {
		super(thrower.world);
	}
	
	int time = 0;
	double lockX, lockY = -1, lockZ;
	
	private static final String TAG_FIRE = "fire";
	private static final String TAG_EFFECT = "effect";
	private static final String TAG_DAMAGE = "damage";
	private static final String TAG_TRUEDAMAGE = "truedamage";
	private static final String TAG_TIME = "time";
	
	private static final DataParameter<Float> DAMAGE = EntityDataManager.createKey(EntitySkullMissile.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> TRUEDAMAGE = EntityDataManager.createKey(EntitySkullMissile.class, DataSerializers.FLOAT);
	private static final DataParameter<Boolean> EFFECT = EntityDataManager.createKey(EntitySkullMissile.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> FIRE = EntityDataManager.createKey(EntitySkullMissile.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> TARGET = EntityDataManager.createKey(EntitySkullMissile.class, DataSerializers.VARINT);
	
	@Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(DAMAGE, 0F);
		dataManager.register(TRUEDAMAGE, 0F);
		dataManager.register(EFFECT, false);
		dataManager.register(FIRE, false);
		dataManager.register(TARGET, 0);
	}
	
	@Override
    public void writeEntityToNBT(NBTTagCompound cmp){
		super.writeEntityToNBT(cmp);
		cmp.setInteger(TAG_TIME, time);
    	cmp.setFloat(TAG_DAMAGE, getDamage());
    	cmp.setFloat(TAG_TRUEDAMAGE, getTrueDamage());
    	cmp.setBoolean(TAG_EFFECT, getEffect());
    	cmp.setBoolean(TAG_FIRE, getFire());
    }
	
	@Override
	public void readEntityFromNBT(NBTTagCompound cmp){
		super.readEntityFromNBT(cmp);
		time = cmp.getInteger(TAG_TIME);
		setDamage(cmp.getFloat(TAG_DAMAGE));
		setTrueDamage(cmp.getFloat(TAG_TRUEDAMAGE));
		setFire(cmp.getBoolean(TAG_FIRE));
		setEffect(cmp.getBoolean(TAG_EFFECT));
    }
	
	public float getDamage() {
		return dataManager.get(DAMAGE);
	}
	
	public void setDamage(float dam) {
		dataManager.set(DAMAGE, dam);;
	}
	
	public float getTrueDamage() {
		return dataManager.get(TRUEDAMAGE);
	}
	
	public void setTrueDamage(float dam) {
		dataManager.set(TRUEDAMAGE, dam);;
	}
	
	public void setFire(boolean b) {
		dataManager.set(FIRE, b);;
	}
	
	public void setEffect(boolean b) {
		dataManager.set(EFFECT, b);;
	}
	
	public boolean getFire(){
		return dataManager.get(FIRE);
	}
	
	public boolean getEffect(){
		return dataManager.get(EFFECT);
	}
	
	public void setTarget(EntityPlayer e) {
		dataManager.set(TARGET, e == null ? -1 : e.getEntityId());
	}

	public EntityPlayer getTargetEntity() {
		int id = dataManager.get(TARGET);
		Entity e = world.getEntityByID(id);
		if(e != null && e instanceof EntityPlayer)
			return (EntityPlayer) e;

		return null;
	}
	
	@Override
	public void onUpdate() {
		double lastTickPosX = this.lastTickPosX;
		double lastTickPosY = this.lastTickPosY;
		double lastTickPosZ = this.lastTickPosZ;

		super.onUpdate();

		if(!world.isRemote && (!findTarget() || time > 100)) {
			setDead();
			return;
		}

		Vector3 thisVec = Vector3.fromEntityCenter(this);
		Vector3 oldPos = new Vector3(lastTickPosX, lastTickPosY, lastTickPosZ);
		Vector3 diff = thisVec.subtract(oldPos);
		Vector3 step = diff.normalize().multiply(0.05);
		int steps = (int) (diff.mag() / step.mag());
		Vector3 particlePos = oldPos;

		Botania.proxy.setSparkleFXCorrupt(true);
		for(int i = 0; i < steps; i++) {
			Botania.proxy.sparkleFX(particlePos.x, particlePos.y, particlePos.z, 1F, 0F, 1F, 0.8F, 2);
			if(world.rand.nextInt(steps) <= 1)
				Botania.proxy.sparkleFX(particlePos.x + (Math.random() - 0.5) * 0.4, particlePos.y + (Math.random() - 0.5) * 0.4, particlePos.z + (Math.random() - 0.5) * 0.4, 1F, 0F, 1F, 0.8F, 2);

			particlePos = particlePos.add(step);
		}
		Botania.proxy.setSparkleFXCorrupt(false);

		EntityPlayer target = getTargetEntity();
		if(target != null) {
			if(lockY == -1) {
				lockX = target.posX;
				lockY = target.posY;
				lockZ = target.posZ;
			}

			Vector3 targetVec = new Vector3(lockX, lockY, lockZ);
			Vector3 diffVec = targetVec.subtract(thisVec);
			Vector3 motionVec = diffVec.normalize().multiply(0.6);
			motionX = motionVec.x;
			motionY = motionVec.y;
			if(time < 10)
				motionY = Math.abs(motionY);
			motionZ = motionVec.z;

			List<EntityPlayer> targetList = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(posX - 1, posY - 1, posZ - 1, posX + 1, posY + 1, posZ + 1));
			if(targetList.contains(target)) {
				target.attackEntityFrom(DamageSource.GENERIC, getDamage());
				ExtraBotanyAPI.dealTrueDamage(target, getTrueDamage());
				if(getFire())
					target.setFire(5);
				if(getEffect()){
					target.addPotionEffect(new PotionEffect(MobEffects.WITHER, 200, 1));
				}
				float m = 0.35F;
				for(int i = 0; i < 25; i++)
					Botania.proxy.wispFX(posX, posY + 1, posZ, (float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, (float) (Math.random() - 0.5F) * m, (float) (Math.random() - 0.5F) * m, (float) (Math.random() - 0.5F) * m);
				setDead();
			}

		}
		
		time++;

	}
	
	private List<EntityPlayer> getPlayersAround() {
		BlockPos source = this.getPosition();
		float range = 15F;
		return world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range, source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
	}
	
	public boolean findTarget() {
		EntityPlayer target = getTargetEntity();
		if(target != null && target.getHealth() > 0 && !target.isDead && world.loadedEntityList.contains(target))
			return true;
		if(target != null)
			setTarget(null);

		double range = 15;
		AxisAlignedBB bounds = new AxisAlignedBB(posX - range, posY - range, posZ - range, posX + range, posY + range, posZ + range);
		List entities;
		entities = world.getEntitiesWithinAABB(EntityPlayer.class, bounds);

		while(entities.size() > 0) {
			Entity e = (Entity) entities.get(world.rand.nextInt(entities.size()));
			if(!(e instanceof EntityLivingBase) || e.isDead) { // Just in case...
				entities.remove(e);
				continue;
			}

			target = (EntityPlayer) e;
			setTarget(target);
			break;
		}

		return target != null;
	}

	
	@Override
	protected void onImpact(@Nonnull RayTraceResult pos) {
		switch (pos.typeOfHit) {
			case BLOCK: {
				Block block = world.getBlockState(pos.getBlockPos()).getBlock();
				if(block instanceof BlockBush || block instanceof BlockLeaves)
					return;
				
				setDead();
					break;
				}
			case ENTITY: {
				if (pos.entityHit == getTargetEntity())
					setDead();
				break;
			}
			default: {
				setDead();
				break;
			}
		}
	}

}
