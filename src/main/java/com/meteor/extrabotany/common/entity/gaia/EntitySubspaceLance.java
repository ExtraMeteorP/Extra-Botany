package com.meteor.extrabotany.common.entity.gaia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.api.entity.IBossProjectile;
import com.meteor.extrabotany.common.entity.EntityThrowableCopy;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.helper.MathHelper;
import vazkii.botania.common.core.helper.Vector3;

public class EntitySubspaceLance extends EntityThrowableCopy implements IBossProjectile {

	private static final String TAG_ROTATION = "rotation";
	private static final String TAG_DAMAGE = "damage";
	private static final String TAG_LIFE = "life";
	private static final String TAG_PITCH = "pitch";
	private static final String TAG_LIGHTNING_SEED = "lightningSeed";

	private static final DataParameter<Float> ROTATION = EntityDataManager.createKey(EntitySubspaceLance.class,
			DataSerializers.FLOAT);
	private static final DataParameter<Integer> DAMAGE = EntityDataManager.createKey(EntitySubspaceLance.class,
			DataSerializers.VARINT);
	private static final DataParameter<Integer> LIFE = EntityDataManager.createKey(EntitySubspaceLance.class,
			DataSerializers.VARINT);
	private static final DataParameter<Float> PITCH = EntityDataManager.createKey(EntitySubspaceLance.class,
			DataSerializers.FLOAT);
	private static final DataParameter<Float> SEED = EntityDataManager.createKey(EntitySubspaceLance.class,
			DataSerializers.FLOAT);

	private EntitySubspaceLance.Status preStatus;
	private EntitySubspaceLance.Status status;

	public EntitySubspaceLance(World worldIn) {
		super(worldIn);
	}

	public EntitySubspaceLance(World world, EntityLivingBase thrower) {
		super(world, thrower);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		setSize(0F, 0F);
		dataManager.register(ROTATION, 0F);
		dataManager.register(DAMAGE, 0);
		dataManager.register(LIFE, 0);
		dataManager.register(PITCH, 0F);
	}

	@Override
	public boolean isImmuneToExplosions() {
		return true;
	}

	@Override
	protected float getGravityVelocity() {
		return 0.15F;
	}

	@Override
	public void onUpdate() {

		this.preStatus = this.status;
		if (this.status != EntitySubspaceLance.Status.STANDBY) {
			this.setPositionAndUpdate(this.posX, this.posY - 0.15F, this.posZ);
			this.status = getStatus();
		} else {
			this.motionZ = 0;
			this.motionY = 0;
			this.motionX = 0;
		}

		super.onUpdate();

		if (ticksExisted > getLife())
			setDead();

		EntityLivingBase thrower = getThrower();
		if (!world.isRemote && (thrower == null || thrower.isDead)) {
			setDead();
			return;
		}

		AxisAlignedBB axis = new AxisAlignedBB(posX - 1.5F, posY - 2F, posZ - 1.5F, lastTickPosX + 1.5F,
				lastTickPosY + 2F, lastTickPosZ + 1.5F);
		List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, axis);
		for (EntityLivingBase living : entities) {
			if (living == thrower)
				continue;

			if (living.hurtTime == 0 && this.ticksExisted % 35 == 0 && living.getHealth() > 20F) {
				if (!world.isRemote && living.isEntityAlive())
					lightning(living);
			}

		}

		List<EntitySubspaceLance> lances = world.getEntitiesWithinAABB(EntitySubspaceLance.class, new AxisAlignedBB(
				posX - 15F, posY - 15F, posZ - 15F, lastTickPosX + 15F, lastTickPosY + 15F, lastTickPosZ + 15F));
		for (EntitySubspaceLance lance : lances) {
			float distance = MathHelper.pointDistanceSpace(this.posX, this.posY, this.posZ, lance.posX, lance.posY,
					lance.posZ);
			Vec3d oldPosVec = new Vec3d(this.posX, this.posY + height / 2 + 1.5F, this.posZ);
			Vec3d newPosVec = new Vec3d(lance.posX, lance.posY + height / 2 + 1.5F, lance.posZ);
			for (EntityPlayer player : getPlayersAround()) {
				RayTraceResult rtr = player.getEntityBoundingBox().grow(0.4).calculateIntercept(oldPosVec, newPosVec);
				if (rtr != null) {
					if (this.ticksExisted % 5 == 0 && player.getHealth() > 8) {
						player.attackEntityFrom(DamageSource.GENERIC.setDamageBypassesArmor().setDamageIsAbsolute(), 1.2F);
						player.attackEntityFrom(DamageSource.LIGHTNING_BOLT.setDamageBypassesArmor().setDamageIsAbsolute(), 1.2F);
						ExtraBotanyAPI.dealTrueDamage(this.getThrower(), player, 0.4F);
						player.addPotionEffect(new PotionEffect(MobEffects.UNLUCK, 100, 1));
					}
				}
			}

			for (int i = 0; i < distance; i++) {
				float x = (float) (this.posX + (lance.posX - this.posX) * i / distance) + 0.5F;
				float y = (float) (this.posY + (lance.posY - this.posY) * i / distance) + 2.2F;
				float z = (float) (this.posZ + (lance.posZ - this.posZ) * i / distance) + 0.5F;
				Botania.proxy.sparkleFX(x, y, z, 0.1F, 0F, 1F, 1.2F, 5);
			}
		}

	}

	public void lightning(EntityLivingBase entity) {
		double range = 8;
		List<EntityLivingBase> alreadyTargetedEntities = new ArrayList<>();
		int dmg = 4;

		Predicate<Entity> selector = e -> e instanceof EntityLivingBase && !alreadyTargetedEntities.contains(e);

		Random rand = new Random();
		EntityLivingBase lightningSource = entity;
		if (lightningSource == null)
			return;
		int hops = entity.world.isThundering() ? 10 : 4;
		for (int i = 0; i < hops; i++) {
			List<Entity> entities = entity.world.getEntitiesInAABBexcluding(lightningSource,
					new AxisAlignedBB(lightningSource.posX - range, lightningSource.posY - range,
							lightningSource.posZ - range, lightningSource.posX + range, lightningSource.posY + range,
							lightningSource.posZ + range),
					selector::test);
			if (entities.isEmpty())
				break;

			EntityLivingBase target = (EntityLivingBase) entities.get(rand.nextInt(entities.size()));
			if (!(target instanceof EntityVoidHerrscher)) {
				if (target.getHealth() > 10)
					ExtraBotanyAPI.dealTrueDamage(this.getThrower(), target, dmg + target.getMaxHealth() * 0.1F);
				target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 1));
			}

			Botania.proxy.lightningFX(Vector3.fromEntityCenter(lightningSource), Vector3.fromEntityCenter(target), 1,
					0x0179C4, 0xAADFFF);

			alreadyTargetedEntities.add(target);
			lightningSource = target;
			dmg--;
		}
	}

	public List<EntityPlayer> getPlayersAround() {
		BlockPos source = this.getPosition();
		float range = 24F;
		return world.getEntitiesWithinAABB(EntityPlayer.class,
				new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range,
						source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
	}

	public static void attackedFrom(EntityLivingBase target, EntityLivingBase player, int i) {
		if (player != null && player instanceof EntityPlayer)
			target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) player), i);
		else
			target.attackEntityFrom(DamageSource.GENERIC, i);
	}

	@Override
	protected void onImpact(RayTraceResult pos) {
		EntityLivingBase thrower = getThrower();
		if (pos.entityHit == null || pos.entityHit != thrower) {

		}
	}

	@Override
	public void writeEntityToNBT(@Nonnull NBTTagCompound cmp) {
		super.writeEntityToNBT(cmp);
		cmp.setFloat(TAG_ROTATION, getRotation());
		cmp.setInteger(TAG_LIFE, getLife());
		cmp.setInteger(TAG_DAMAGE, getDamage());
		cmp.setFloat(TAG_PITCH, getPitch());
	}

	@Override
	public void readEntityFromNBT(@Nonnull NBTTagCompound cmp) {
		super.readEntityFromNBT(cmp);
		setRotation(cmp.getFloat(TAG_ROTATION));
		setLife(cmp.getInteger(TAG_LIFE));
		setDamage(cmp.getInteger(TAG_DAMAGE));
		setPitch(cmp.getFloat(TAG_PITCH));
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

	public int getLife() {
		return dataManager.get(LIFE);
	}

	public void setLife(int delay) {
		dataManager.set(LIFE, delay);
	}

	public int getDamage() {
		return dataManager.get(DAMAGE);
	}

	public void setDamage(int delay) {
		dataManager.set(DAMAGE, delay);
	}

	public static enum Status {
		INAIR, STANDBY;
	}

	private EntitySubspaceLance.Status getStatus() {
		if (this.world.getBlockState(this.getPosition().add(0, -1.9F, 0)).getBlock() != Blocks.AIR)
			return EntitySubspaceLance.Status.STANDBY;
		return EntitySubspaceLance.Status.INAIR;
	}

	@Override
	public boolean isBoss(Entity p) {
		return true;
	}

	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public boolean isPushedByWater() {
		return false;
	}

}
