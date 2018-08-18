package com.meteor.extrabotany.common.entity;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.api.entity.IEntityWithShield;
import com.meteor.extrabotany.common.core.handler.ConfigHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.handler.ModSounds;

public class EntitySkullMinion extends EntityLiving implements IEntityWithShield{
	
	private static final String TAG_TYPE = "type";
	private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntitySkullMinion.class, DataSerializers.VARINT);
	private static final String TAG_SHIELD = "shield";
	private static final DataParameter<Integer> SHIELD = EntityDataManager.createKey(EntitySkullMinion.class, DataSerializers.VARINT);
	
	private int tpDelay = 200;

	public EntitySkullMinion(World worldIn) {
		super(worldIn);
		setSize(1F, 1F);
	}
	
	@Override
	protected void initEntityAI() {
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, 15 * 1.5F));
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(SHIELD, 0);
		dataManager.register(TYPE, 0);
	}
	
	@Override
	public void readEntityFromNBT(@Nonnull NBTTagCompound var1) {
		setType(var1.getInteger(TAG_TYPE));
		setShield(var1.getInteger(TAG_SHIELD));
	}

	@Override
	public void writeEntityToNBT(@Nonnull NBTTagCompound var1) {
		var1.setInteger(TAG_TYPE, getType());
		var1.setInteger(TAG_SHIELD, getShield());
	}
	
	@Override
	public void onLivingUpdate() {
		motionX = 0;
		motionY = 0;
		motionZ = 0;
		super.onLivingUpdate();
		
		if(world.getDifficulty() == EnumDifficulty.PEACEFUL || getHostAround().isEmpty())
			setDead();
		
		if(ticksExisted > 55 && ticksExisted % 15 == 0)
			spawnMissile();
		
		if(ticksExisted > 400 && ticksExisted % 400 == 0 && ConfigHandler.GAIA_DISARM)
			for(EntityPlayer p : getPlayersAround())
				p.dropItem(true);
		
		for(EntityGaiaIII g : getHostAround())
			if(ticksExisted % 100 == 0 && g.getRankIII())
				g.heal(2F);
		
		if(tpDelay > 0)	
			tpDelay--;

		if(tpDelay == 0 && getHealth() > 0 && !getHostAround().isEmpty()){
			double newx = posX - 1 + world.rand.nextDouble() * 2;
			double newz = posZ - 1 + world.rand.nextDouble() * 2;
			this.setPosition(newx, posY, newz);
			tpDelay = 180;
		}
	}
	
	@Override
	public boolean attackEntityFrom(@Nonnull DamageSource source, float par2) {
		float m = 0.35F;
		for(int i = 0; i < 15; i++)
			Botania.proxy.wispFX(posX, posY + 1, posZ, 0.95F, 0.15F, 0.12F, 0.5F, (float) (Math.random() - 0.5F) * m, (float) (Math.random() - 0.5F) * m, (float) (Math.random() - 0.5F) * m);
		int cap = 15;
		return super.attackEntityFrom(source, Math.min(cap, par2));
	}
	
	private List<EntityGaiaIII> getHostAround() {
		BlockPos source = this.getPosition();
		float range = 15F;
		return world.getEntitiesWithinAABB(EntityGaiaIII.class, new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range, source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
	}
	
	private List<EntityPlayer> getPlayersAround() {
		BlockPos source = this.getPosition();
		float range = 15F;
		return world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range, source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
	}
	
	private void spawnMissile() {
		EntitySkullMissile missile = new EntitySkullMissile(this);
		missile.setPosition(posX + (Math.random() - 0.5 * 0.1), posY + 1.8 + (Math.random() - 0.5 * 0.1), posZ + (Math.random() - 0.5 * 0.1));
		missile.setDamage(4);
		missile.setFire(true);
		playSound(ModSounds.missile, 0.6F, 0.8F + (float) Math.random() * 0.2F);
		world.spawnEntity(missile);
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0);
	}
	
	public int getType() {
		return dataManager.get(TYPE);
	}
	
	public void setType(int t) {
		dataManager.set(TYPE, t);;
	}

	@Override
	public int getShield() {
		return dataManager.get(SHIELD);
	}

	@Override
	public void setShield(int shield) {
		dataManager.set(SHIELD, shield);
	}

}
