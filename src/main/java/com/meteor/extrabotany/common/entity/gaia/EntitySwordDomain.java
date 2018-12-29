package com.meteor.extrabotany.common.entity.gaia;

import java.util.List;
import java.util.UUID;

import com.google.common.base.Optional;
import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.core.config.ConfigHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.botania.common.Botania;

public class EntitySwordDomain extends Entity{
	
	private static final String TAG_SOURCE = "source";
	private static final String TAG_SOURCEX = "sourcex";
	private static final String TAG_SOURCEY = "sourcey";
	private static final String TAG_SOURCEZ = "sourcez";
	private static final String TAG_COUNT = "count";
	private static final String TAG_PLAYERLIST = "playerlist";
	
	private static final DataParameter<BlockPos> SOURCE = EntityDataManager.createKey(EntitySwordDomain.class, DataSerializers.BLOCK_POS);
	private static final String TAG_TYPE = "type";
	private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntitySwordDomain.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> COUNT = EntityDataManager.createKey(EntitySwordDomain.class, DataSerializers.VARINT);
	private static final DataParameter<Optional<UUID>> UUID = EntityDataManager.createKey(EntitySwordDomain.class, DataSerializers.OPTIONAL_UNIQUE_ID);
	
	private static byte[] list;
	
	public EntitySwordDomain(World world) {
		super(world);
	}

	@Override
	protected void entityInit() {
		dataManager.register(SOURCE, null);	
		dataManager.register(TYPE, 0);
		dataManager.register(COUNT, 0);
		dataManager.register(UUID, Optional.absent());
	}
	
	@Override
	public void onUpdate(){
		super.onUpdate();
		if(getHostAround().isEmpty())
			setDead();
		
		this.setPosition(posX, Math.max(posY - 0.01F, getCount()), posZ);
		
		float m = 0.45F;
			
		EntityPlayer player = world.getPlayerEntityByUUID(getUUID());
		if (player != null && this.ticksExisted > 70){
			if(player.getPosition().getX() < getSource().getX() - 2.5F || player.getPosition().getX() > getSource().getX() + 2.5F 
					|| player.getPosition().getY() < getSource().getY() - 2.5F || player.getPosition().getY() > getSource().getY() + 2.5F 
					|| player.getPosition().getZ() < getSource().getZ() - 2.5F || player.getPosition().getZ() > getSource().getZ() + 2.5F)
				player.setPosition(getSource().getX() + 0.5, getSource().getY(), getSource().getZ() + 0.5);
			
			for(int i = 0; i < (int)(3 * ConfigHandler.PARTICLE); i++)
				Botania.proxy.wispFX(getSource().getX(), getSource().getY() + 3, getSource().getZ(), 1F, 0.9F, 0F, 0.25F, (float) (Math.random() - 0.5F) * m, (float) (Math.random() - 0.5F) * m, (float) (Math.random() - 0.5F) * m);
			
			if(this.ticksExisted % 50 == 0)
				player.attackEntityFrom(DamageSource.MAGIC, 2F);
			
			if(this.ticksExisted == 200)
				ExtraBotanyAPI.dealTrueDamage(player, 2F);

		}
		
		if(this.ticksExisted > 201){
			for(int i = 0; i < 5  * ConfigHandler.PARTICLE; i++)
				Botania.proxy.wispFX(posX, posY, posZ, (float)Math.random(), (float)Math.random(), (float)Math.random(), 0.25F, (float) (Math.random() - 0.5F) * m, (float) (Math.random() - 0.5F) * m, (float) (Math.random() - 0.5F) * m);
			setDead();
		}
	}
	
	private List<EntityGaiaIII> getHostAround() {
		BlockPos source = this.getPosition();
		float range = 15F;
		return world.getEntitiesWithinAABB(EntityGaiaIII.class, new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range, source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound cmp) {
		setType(cmp.getInteger(TAG_TYPE));
		setSource(new BlockPos(cmp.getInteger(TAG_SOURCEX), cmp.getInteger(TAG_SOURCEY), cmp.getInteger(TAG_SOURCEZ)));
		setCount(cmp.getInteger(TAG_COUNT));
		setUUID(cmp.getUniqueId(TAG_PLAYERLIST));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound cmp) {
		cmp.setInteger(TAG_SOURCEX, getSource().getX());
		cmp.setInteger(TAG_SOURCEY, getSource().getY());
		cmp.setInteger(TAG_SOURCEZ, getSource().getZ());
		cmp.setInteger(TAG_TYPE, getType());
		cmp.setInteger(TAG_COUNT, getCount());
		cmp.setUniqueId(TAG_PLAYERLIST, getUUID());
	}
	
	public UUID getUUID(){
		return (UUID)((Optional)this.dataManager.get(UUID)).orNull();
	}
	
	public void setUUID(UUID u){
		dataManager.set(UUID, Optional.fromNullable(u));
	}
	
	public BlockPos getSource(){
		return dataManager.get(SOURCE);
	}
	
	public void setSource(BlockPos pos){
		dataManager.set(SOURCE, pos);
	}
	
	public int getType() {
		return dataManager.get(TYPE);
	}
	
	public void setType(int t) {
		dataManager.set(TYPE, t);;
	}
	
	public int getCount() {
		return dataManager.get(COUNT);
	}
	
	public void setCount(int t) {
		dataManager.set(COUNT, t);;
	}

}
