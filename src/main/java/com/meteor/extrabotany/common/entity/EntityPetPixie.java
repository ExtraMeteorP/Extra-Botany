package com.meteor.extrabotany.common.entity;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import vazkii.botania.common.Botania;

public class EntityPetPixie extends EntityFlying {

	private EntityLivingBase summoner = null;
	private float damage = 0;
	private PotionEffect effect = null;

	private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntityPetPixie.class,
			DataSerializers.VARINT);

	public EntityPetPixie(World world) {
		super(world);
		setSize(0F, 0F);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(TYPE, 0);
	}

	public void setType(int type) {
		dataManager.set(TYPE, type);
	}

	public int getType() {
		return dataManager.get(TYPE);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0);
	}

	public void setProps(EntityLivingBase summoner, float damage) {
		this.summoner = summoner;
		this.damage = damage;
	}

	public void setApplyPotionEffect(PotionEffect effect) {
		this.effect = effect;
	}

	@Override
	protected void updateAITasks() {
		EntityLivingBase target = getAttackTarget();
		if (target != null) {
			double d0 = target.posX + target.width / 2 - posX;
			double d1 = target.posY + target.height / 2 - posY;
			double d2 = target.posZ + target.width / 2 - posZ;
			double d3 = d0 * d0 + d1 * d1 + d2 * d2;

			float mod = 0.75F;

			motionX += d0 / d3 * mod;
			motionY += d1 / d3 * mod;
			motionZ += d2 / d3 * mod;

			if (Math.sqrt(d3) < 3F) {
				if (summoner != null) {
					if (summoner instanceof EntityPlayer)
						target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) summoner), damage);
					else {
						target.attackEntityFrom(DamageSource.causeMobDamage(summoner), damage);
					}
				} else
					target.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
				if (effect != null && !(target instanceof EntityPlayer))
					target.addPotionEffect(effect);
			}
		}

		renderYawOffset = rotationYaw = -((float) Math.atan2(motionX, motionZ)) * 180.0F / (float) Math.PI;
	}

	@Override
	public boolean attackEntityFrom(@Nonnull DamageSource par1DamageSource, float par2) {
		if (par1DamageSource == DamageSource.IN_WALL)
			return false;
		if (par1DamageSource.getTrueSource() != summoner)
			return super.attackEntityFrom(par1DamageSource, par2 / 5);
		return false;
	}

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();

		if (!world.isRemote && ticksExisted > 1000)
			setDead();

		int RANGE = 24;

		if (summoner != null) {
			if (summoner.getLastAttackedEntity() != null && summoner.getLastAttackedEntity().isEntityAlive())
				this.setAttackTarget(summoner.getLastAttackedEntity());
		}

		List<EntityLivingBase> livings = world.getEntitiesWithinAABB(EntityLivingBase.class,
				new AxisAlignedBB(this.getPosition().add(-RANGE, -RANGE, -RANGE),
						this.getPosition().add(RANGE + 1, RANGE + 1, RANGE + 1)));
		if (livings.size() > 1 && (getAttackTarget() != null && getAttackTarget().isDead || getAttackTarget() == null))
			for (EntityLivingBase living : livings) {
				if (living == summoner)
					continue;
				if (living instanceof IMob)
					this.setAttackTarget(living);
				break;
			}

		if (world.isRemote) {

			Botania.proxy.sparkleFX(posX, posY, posZ, 0.08F, 1F, 0.08F, 3F, 2);
			Botania.proxy.sparkleFX(posX + (Math.random() - 0.5) * 0.25, posY + 0.5 + (Math.random() - 0.5) * 0.25,
					posZ + (Math.random() - 0.5) * 0.25, 0.08F, 1F, 0.08F, 0.01F + (float) Math.random() * 0.25F, 20);
		}

	}

	@Override
	public void setDead() {
		if (world != null && world.isRemote)
			for (int i = 0; i < 12; i++)
				Botania.proxy.sparkleFX(posX + (Math.random() - 0.5) * 0.25, posY + 0.5 + (Math.random() - 0.5) * 0.25,
						posZ + (Math.random() - 0.5) * 0.25, 0.08F, 1F, 0.08F, 1F + (float) Math.random() * 0.25F, 5);
		super.setDead();
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
	public boolean canBeLeashedTo(EntityPlayer player) {
		return false;
	}

}
