package com.meteor.extrabotany.common.entity.judah;

import java.util.List;

import com.meteor.extrabotany.api.ExtraBotanyAPI;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import vazkii.botania.common.Botania;

public class EntityJudahSword extends Entity{
	
    private static final String TAG_DAMAGE = "damage";
    private static final String TAG_STARTPOS = "startpos";
    private static final String TAG_ENDPOS = "endpos";
    
    private static final DataParameter<Float> DAMAGE = EntityDataManager.createKey(EntityJudahSword.class, DataSerializers.FLOAT);
    private static final DataParameter<BlockPos> STARTPOS = EntityDataManager.createKey(EntityJudahSword.class, DataSerializers.BLOCK_POS);
    private static final DataParameter<BlockPos> ENDPOS = EntityDataManager.createKey(EntityJudahSword.class, DataSerializers.BLOCK_POS);

	public EntityJudahSword(World world) {
		super(world);
	}
	
	public EntityJudahSword(World world, BlockPos start, BlockPos end) {
		super(world);
		setStartPos(start);
		setEndPos(end);
	}

	@Override
	public void entityInit() {
		this.setSize(0F, 0F);
		dataManager.register(DAMAGE, 0F);
		dataManager.register(STARTPOS, BlockPos.ORIGIN);
		dataManager.register(ENDPOS, BlockPos.ORIGIN);
	}
	
	@Override
    public void onUpdate() {
		float r=1.00F;
		float g=0.7F;
		float b=0.7F;
		Botania.proxy.sparkleFX(posX, posY, posZ, r, g, b, 1.8F, 25);
		
		BlockPos start = getStartPos();
		BlockPos end = getEndPos();
		Vec3d motion = new Vec3d(end.getX()-start.getX(), end.getY()-start.getY(), end.getZ()-start.getZ());
		motion = motion.normalize().scale(0.75D);
		this.posX = this.posX + motion.x;
		this.posZ = this.posZ + motion.z;
		this.posY = this.posY + motion.y;
		
		for (EntityLivingBase entity : getEntitiesCollided()) {
			if(entity instanceof EntityPlayer)
				continue;
			entity.attackEntityFrom(DamageSource.MAGIC.setDamageBypassesArmor().setDamageIsAbsolute(), getDamage()*0.2F);
			entity.hurtResistantTime=0;
			entity.attackEntityFrom(DamageSource.LAVA.setDamageBypassesArmor().setDamageIsAbsolute().setFireDamage(), getDamage()*0.2F);
			entity.setFire(5);
		}
		
		super.onUpdate();
		
		if(this.getPosition().getX() >= end.getX()-1 &&  this.getPosition().getX() <= end.getX()+1 && this.getPosition().getZ() >= end.getZ()-1 &&  this.getPosition().getZ() <= end.getZ()+1) {
			Vec3d oldPosVec = new Vec3d(start.getX(), start.getY(), start.getZ());
			Vec3d newPosVec = new Vec3d(end.getX(), end.getY(), end.getZ());
			
			for (EntityLivingBase entity : getEntitiesAround()) {
				if(entity instanceof EntityPlayer)
					continue;
				RayTraceResult rtr = entity.getEntityBoundingBox().grow(1.8D).calculateIntercept(oldPosVec, newPosVec);
				if (rtr != null) {
					entity.attackEntityFrom(DamageSource.MAGIC.setDamageBypassesArmor().setDamageIsAbsolute(), getDamage()*0.6F);
					entity.hurtResistantTime=0;
					entity.attackEntityFrom(DamageSource.LAVA.setDamageBypassesArmor().setDamageIsAbsolute(), getDamage()*0.3F);
					ExtraBotanyAPI.dealTrueDamage(entity, entity, getDamage()*0.3F);
					entity.setFire(5);
				}
			}
			this.setDead();
		}
	}
	
	public List<EntityLivingBase> getEntitiesCollided() {
		BlockPos source = this.getPosition();
		float range = 0.75F;
		return world.getEntitiesWithinAABB(EntityLivingBase.class,
				new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range,
						source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
	}
	
	public List<EntityLivingBase> getEntitiesAround() {
		BlockPos source = this.getPosition();
		float range = 13F;
		return world.getEntitiesWithinAABB(EntityLivingBase.class,
				new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range,
						source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound cmp) {
		setStartPos(BlockPos.fromLong(cmp.getLong("startpos")));
		setEndPos(BlockPos.fromLong(cmp.getLong("endpos")));
		setDamage(cmp.getFloat("dmg"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound cmp) {
		cmp.setLong("startpos", getStartPos().toLong());
		cmp.setLong("endpos", getEndPos().toLong());
		cmp.setFloat("dmg", getDamage());
	}
	
	public void setStartPos(BlockPos pos) {
		dataManager.set(STARTPOS, pos);
	}
	
	public void setEndPos(BlockPos pos) {
		dataManager.set(ENDPOS, pos);
	}
	
	public void setDamage(float dmg) {
		dataManager.set(DAMAGE, dmg);
	}
	
	public BlockPos getStartPos() {
		return dataManager.get(STARTPOS);
	}
	
	public BlockPos getEndPos() {
		return dataManager.get(ENDPOS);
	}
	
	public float getDamage() {
		return dataManager.get(DAMAGE);
	}

}
