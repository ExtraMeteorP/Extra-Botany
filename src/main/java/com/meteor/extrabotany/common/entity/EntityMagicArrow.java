package com.meteor.extrabotany.common.entity;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import vazkii.botania.common.Botania;

public class EntityMagicArrow extends EntityThrowableCopy {

	private static final String TAG_DAMAGE = "damage";
	private static final String TAG_LIFE = "life";
	private static final String TAG_ROTATION = "rotation";
	private static final DataParameter<Integer> DAMAGE = EntityDataManager.createKey(EntityMagicArrow.class,
			DataSerializers.VARINT);
	private static final DataParameter<Integer> LIFE = EntityDataManager.createKey(EntityMagicArrow.class,
			DataSerializers.VARINT);
	private static final DataParameter<Float> ROTATION = EntityDataManager.createKey(EntityMagicArrow.class,
			DataSerializers.FLOAT);

	public EntityMagicArrow(World worldIn) {
		super(worldIn);
	}

	public EntityMagicArrow(World world, EntityLivingBase thrower) {
		super(world, thrower);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		setSize(0F, 0F);
		dataManager.register(DAMAGE, 0);
		dataManager.register(LIFE, 0);
		dataManager.register(ROTATION, 0F);
	}

	@Override
	public boolean isImmuneToExplosions() {
		return true;
	}

	@Override
	protected float getGravityVelocity() {
		return 0F;
	}

	@Override
	public void onUpdate() {

		EntityLivingBase thrower = getThrower();
		if (!world.isRemote && (thrower == null || !(thrower instanceof EntityPlayer) || thrower.isDead)) {
			setDead();
			return;
		}
		EntityPlayer player = (EntityPlayer) thrower;
		if (!world.isRemote) {
			AxisAlignedBB axis = new AxisAlignedBB(posX - 2F, posY - 2F, posZ - 2F, lastTickPosX + 2F,
					lastTickPosY + 2F, lastTickPosZ + 2F);
			List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, axis);
			for (EntityLivingBase living : entities) {
				if (living == thrower)
					continue;
				double attribute = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
				if (living.hurtTime == 0) {
					attackedFrom(living, player, (int) (getDamage() + attribute));
				}

			}
		}

		super.onUpdate();

		Botania.proxy.wispFX(posX, posY, posZ, 0.1F, 0.85F, 0.1F, 0.2F, 0F);

		if (ticksExisted > getLife())
			setDead();
	}

	public static void attackedFrom(EntityLivingBase target, EntityPlayer player, int i) {
		if (player != null)
			target.attackEntityFrom(DamageSource.causePlayerDamage(player), i);
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
		cmp.setInteger(TAG_LIFE, getLife());
		cmp.setInteger(TAG_DAMAGE, getDamage());
		cmp.setFloat(TAG_ROTATION, getRotation());
	}

	@Override
	public void readEntityFromNBT(@Nonnull NBTTagCompound cmp) {
		super.readEntityFromNBT(cmp);
		setLife(cmp.getInteger(TAG_LIFE));
		setDamage(cmp.getInteger(TAG_DAMAGE));
		setRotation(cmp.getFloat(TAG_ROTATION));
	}

	public float getRotation() {
		return dataManager.get(ROTATION);
	}

	public void setRotation(float rot) {
		dataManager.set(ROTATION, rot);
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
