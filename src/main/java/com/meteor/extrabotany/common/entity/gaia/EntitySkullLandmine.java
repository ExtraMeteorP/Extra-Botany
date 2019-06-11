package com.meteor.extrabotany.common.entity.gaia;

import java.util.List;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.brew.ModPotions;
import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.core.handler.StatHandler;
import com.meteor.extrabotany.common.lib.LibAdvancements;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.handler.ModSounds;

public class EntitySkullLandmine extends Entity{
	
	public EntityLiving summoner;
	
	private static final String TAG_TYPE = "type";
	private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntitySkullLandmine.class, DataSerializers.VARINT);

	public EntitySkullLandmine(World world) {
		super(world);
		setSize(0.5F, 0.5F);
	}

	@Override
	public void onUpdate() {
		motionX = 0;
		motionY = 0;
		motionZ = 0;
		super.onUpdate();

		float range = 2.5F;

		float r = 0.2F;
		float g = 0F;
		float b = 0.2F;

		//Botania.proxy.wispFX(world, posX, posY, posZ, r, g, b, 0.6F, -0.2F, 1);
		for(int i = 0; i < 5; i++)
			Botania.proxy.wispFX(posX - range + Math.random() * range * 2, posY, posZ - range + Math.random() * range * 2, r, g, b, 0.4F, -0.015F, 1);

		if(ticksExisted >= 55) {
			world.playSound(null, posX, posY, posZ, ModSounds.gaiaTrap, SoundCategory.NEUTRAL, 0.3F, 1F);

			float m = 0.35F;
			g = 0.4F;
			if(getType() == 0)
				b = 1F;
			if(getType() == 1)
				r = 1F;
			if(getType() == 2)
				g = 1F;
			for(int i = 0; i < 20 * ConfigHandler.PARTICLE; i++)
				Botania.proxy.wispFX(posX, posY + 1, posZ, r, g, b, 0.5F, (float) (Math.random() - 0.5F) * m, (float) (Math.random() - 0.5F) * m, (float) (Math.random() - 0.5F) * m);

			if(!world.isRemote) {
				List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(posX - range, posY - range, posZ - range, posX + range, posY + range, posZ + range));
				for(EntityPlayer player : players) {
					float amplifier = StatHandler.hasStat(player, LibAdvancements.GAIA_DEFEAT) ? 1.0F : 0.7F;
					player.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, summoner), 6 * amplifier);
					for(int i = 0; i < 3; i++)
						ExtraBotanyAPI.addPotionEffect(player, ModPotions.witchcurse, 20);
					if(getType() == 1){
						ExtraBotanyAPI.dealTrueDamage(this.summoner, player, (player.getMaxHealth() * 0.35F + 8) * amplifier);
						ExtraBotanyAPI.unlockAdvancement(player, LibAdvancements.LANDMINE_ACTIVE);
					}
					if(getType() == 2 && ConfigHandler.GAIA_DISARM)
						player.dropItem(true);
					player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 25, 0));
					player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 50, 3));
					PotionEffect wither = new PotionEffect(MobEffects.WITHER, 150, 1);
					wither.getCurativeItems().clear();
					player.addPotionEffect(wither);
				}
			}

			setDead();
		}
	}
	
	public int getType() {
		return dataManager.get(TYPE);
	}
	
	public void setType(int t) {
		dataManager.set(TYPE, t);;
	}

	@Override
	protected void entityInit() {
		dataManager.register(TYPE, 0);
	}

	@Override
	protected void readEntityFromNBT(@Nonnull NBTTagCompound var1) {
		setType(var1.getInteger(TAG_TYPE));
	}

	@Override
	protected void writeEntityToNBT(@Nonnull NBTTagCompound var1) {
		var1.setInteger(TAG_TYPE, getType());
	}

}
