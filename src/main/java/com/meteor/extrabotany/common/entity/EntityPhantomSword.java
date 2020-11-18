package com.meteor.extrabotany.common.entity;

import java.util.List;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.item.ModItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import vazkii.botania.common.Botania;

public class EntityPhantomSword extends EntityThrowableCopy {

	private static final String TAG_VARIETY = "variety";
	private static final String TAG_ROTATION = "rotation";
	private static final String TAG_PITCH = "pitch";
	private static final String TAG_TARGETPOS = "targetpos";
	private static final String TAG_DELAY = "delay";
	private static final String TAG_FAKE = "fake";

	private static final DataParameter<Integer> VARIETY = EntityDataManager.createKey(EntityPhantomSword.class,
			DataSerializers.VARINT);
	private static final DataParameter<Integer> DELAY = EntityDataManager.createKey(EntityPhantomSword.class,
			DataSerializers.VARINT);
	private static final DataParameter<Float> ROTATION = EntityDataManager.createKey(EntityPhantomSword.class,
			DataSerializers.FLOAT);
	private static final DataParameter<Float> PITCH = EntityDataManager.createKey(EntityPhantomSword.class,
			DataSerializers.FLOAT);
	private static final DataParameter<BlockPos> TARGET_POS = EntityDataManager.createKey(EntityPhantomSword.class,
			DataSerializers.BLOCK_POS);
	private static final DataParameter<Boolean> FAKE = EntityDataManager.createKey(EntityPhantomSword.class,
			DataSerializers.BOOLEAN);

	private static final float rgb[][] = { { 0.82F, 0.2F, 0.58F }, { 0F, 0.71F, 0.10F }, { 0.74F, 0.07F, 0.32F },
			{ 0.01F, 0.45F, 0.8F }, { 0.05F, 0.39F, 0.9F }, { 0.38F, 0.34F, 0.42F }, { 0.41F, 0.31F, 0.14F },
			{ 0.92F, 0.92F, 0.21F }, { 0.61F, 0.92F, 0.98F }, { 0.18F, 0.45F, 0.43F } };

	private EntityLivingBase thrower;

	public EntityPhantomSword(World worldIn) {
		super(worldIn);
	}

	public EntityPhantomSword(World world, EntityLivingBase thrower, BlockPos targetpos) {
		super(world, thrower);
		this.thrower = thrower;
		setTargetPos(targetpos);
		setVariety((int) (10 * Math.random()));
		double range = 13D;
		double k = 0.6F * Math.PI * Math.random();
		double j = -Math.PI + 2 * Math.PI * Math.random();
		double x = targetpos.getX() + range * Math.sin(k) * Math.cos(j);
		double y = targetpos.getY() + range * Math.cos(k);
		double z = targetpos.getZ() + range * Math.sin(k) * Math.sin(j);
		this.setPosition(x, y, z);
		faceEntity(targetpos);
		Vec3d vec = new Vec3d(getTargetPos().getX() - posX, getTargetPos().getY() - posY, getTargetPos().getZ() - posZ)
				.normalize();
		this.motionX = vec.x * 1.05F;
		this.motionY = vec.y * 1.05F;
		this.motionZ = vec.z * 1.05F;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		setSize(0F, 0F);
		dataManager.register(VARIETY, 0);
		dataManager.register(DELAY, 0);
		dataManager.register(ROTATION, 0F);
		dataManager.register(PITCH, 0F);
		dataManager.register(TARGET_POS, BlockPos.ORIGIN);
		dataManager.register(FAKE, false);
	}

	@Override
	public boolean isImmuneToExplosions() {
		return true;
	}

	@Override
	public void onUpdate() {

		if (getDelay() > 0) {
			setDelay(getDelay() - 1);
			return;
		}

		EntityLivingBase thrower = getThrower();

		if (this.ticksExisted >= 26)
			setDead();
		
		if(getFake()) {
			motionX = 0;
			motionY = 0;
			motionZ = 0;
			return;
		}
		
		if (!getFake() && !world.isRemote && (thrower == null || !(thrower instanceof EntityPlayer) || thrower.isDead)) {
			setDead();
			return;
		}
		
		super.onUpdate();
		
		EntityPlayer player = (EntityPlayer) thrower;
		
		Botania.proxy.wispFX(posX, posY, posZ, rgb[getVariety()][0], rgb[getVariety()][1], rgb[getVariety()][2], 0.25F,
				0F);
		
		if(!world.isRemote && !getFake() && this.ticksExisted % 6 == 0) {
			EntityPhantomSword illusion = new EntityPhantomSword(world);
			illusion.thrower = this.thrower;
			illusion.setFake(true);
			illusion.setRotation(this.getRotation());
			illusion.setPitch(this.getPitch());
			illusion.setPosition(posX, posY, posZ);
			illusion.setVariety(getVariety());
			world.spawnEntity(illusion);
		}

		if (!world.isRemote) {
			double speed = player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
			float dmg = !player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() == ModItems.firstfractal ? (float) (player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * Math.min(1 + speed, 2F)) : 10F;
			float pP = 0.65F;
			float mP = 0.3F;
			float tP = Math.max(0F, 1F - pP - mP);
			AxisAlignedBB axis = new AxisAlignedBB(posX, posY, posZ, lastTickPosX, lastTickPosY, lastTickPosZ).grow(2);
			List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, axis);
			for (EntityLivingBase living : entities) {
				if (living == thrower)
					continue;
				if(living instanceof EntityAnimal)
					continue;
				if (living.hurtResistantTime <= 5) {
					ExtraBotanyAPI.dealTrueDamage(player, living, dmg * tP);
					living.attackEntityFrom(DamageSource.MAGIC, dmg * mP);
					attackedFrom(living, player, dmg * pP);
				}
			}
		}

	}

	public static void attackedFrom(EntityLivingBase target, EntityPlayer player, float i) {
		if (player != null)
			target.attackEntityFrom(DamageSource.causePlayerDamage(player), i);
		else
			target.attackEntityFrom(DamageSource.GENERIC, i);
	}

	@Override
	protected void onImpact(RayTraceResult result) {

	}

	public void faceEntity(BlockPos target) {
		double d0 = target.getX() - this.posX;
		double d2 = target.getZ() - this.posZ;
		double d1 = target.getY() - this.posY;

		double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);
		float f = (float) (MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
		float f1 = (float) (-(MathHelper.atan2(d1, d3) * (180D / Math.PI)));
		this.rotationPitch = this.updateRotation(this.rotationPitch, f1, 360F);
		this.rotationYaw = this.updateRotation(this.rotationYaw, f, 360F);

		setPitch(-this.rotationPitch);
		setRotation(MathHelper.wrapDegrees(-this.rotationYaw + 180));
	}

	private float updateRotation(float angle, float targetAngle, float maxIncrease) {
		float f = MathHelper.wrapDegrees(targetAngle - angle);

		if (f > maxIncrease) {
			f = maxIncrease;
		}

		if (f < -maxIncrease) {
			f = -maxIncrease;
		}

		return angle + f;
	}

	@Override
	public void writeEntityToNBT(@Nonnull NBTTagCompound cmp) {
		super.writeEntityToNBT(cmp);
		cmp.setInteger(TAG_VARIETY, getVariety());
		cmp.setInteger(TAG_DELAY, getDelay());
		cmp.setFloat(TAG_ROTATION, getRotation());
		cmp.setFloat(TAG_PITCH, getPitch());
		cmp.setLong(TAG_TARGETPOS, getTargetPos().toLong());
		cmp.setBoolean(TAG_FAKE, getFake());
	}

	@Override
	public void readEntityFromNBT(@Nonnull NBTTagCompound cmp) {
		super.readEntityFromNBT(cmp);
		setVariety(cmp.getInteger(TAG_VARIETY));
		setDelay(cmp.getInteger(TAG_DELAY));
		setRotation(cmp.getFloat(TAG_ROTATION));
		setPitch(cmp.getFloat(TAG_PITCH));
		setTargetPos(BlockPos.fromLong(cmp.getLong(TAG_TARGETPOS)));
		setFake(cmp.getBoolean(TAG_FAKE));
	}

	public int getVariety() {
		return dataManager.get(VARIETY);
	}

	public void setVariety(int var) {
		dataManager.set(VARIETY, var);
	}

	public int getDelay() {
		return dataManager.get(DELAY);
	}

	public void setDelay(int var) {
		dataManager.set(DELAY, var);
	}

	public float getRotation() {
		return dataManager.get(ROTATION);
	}

	public void setRotation(float rot) {
		dataManager.set(ROTATION, rot);
	}

	public float getPitch() {
		return dataManager.get(PITCH);
	}

	public void setPitch(float rot) {
		dataManager.set(PITCH, rot);
	}
	
	public boolean getFake() {
		return dataManager.get(FAKE);
	}

	public void setFake(boolean rot) {
		dataManager.set(FAKE, rot);
	}


	public BlockPos getTargetPos() {
		return dataManager.get(TARGET_POS);
	}

	public void setTargetPos(BlockPos pos) {
		dataManager.set(TARGET_POS, pos);
	}

}
