package com.meteor.extrabotany.common.block.subtile.generating;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.api.subtile.SubTileGeneratingNature;
import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.lexicon.LexiconData;
import com.meteor.extrabotany.common.lib.LibAdvancements;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.RadiusDescriptor;

public class SubTileTinkle extends SubTileGeneratingNature{
	
	private static final int RANGE = 8;
	private static final String TAG_TIME = "time";
	private int time = 0;
	
	@Override
	public LexiconEntry getEntry() {
		return LexiconData.tinkle;
	}
	
	@Override
	public boolean acceptsRedstone() {
		return true;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if(redstoneSignal > 0)
			return;
		World world = this.supertile.getWorld();
		if(!world.isRemote && world.getTotalWorldTime() % 20L == 0){
            NBTTagCompound tag = this.supertile.getTileData();
            int time = tag.getByte(TAG_TIME);
            int prevTime = time;
        	for(EntityPlayer player : supertile.getWorld().getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(supertile.getPos().add(-RANGE, -RANGE, -RANGE), supertile.getPos().add(RANGE + 1, RANGE + 1, RANGE + 1)))) {
	            double vx = player.posX - player.chasingPosX;
	            double vy = player.posY - player.chasingPosY;
	            double vz = player.posZ - player.chasingPosZ;
	            double vel = Math.sqrt(vx*vx + vy*vy + vz*vz);
	            if(player.isPotionActive(MobEffects.SPEED))
	                vel *= 1.2;
	
	            time += MathHelper.clamp((int) (vel * 10.0), 0, 8);
	
	            final int limit = 10;
	            
	            int buff = ConfigHandler.LP_BELLFLOWER ? isEnabled() ? 15 : 0 : 0;
	            
	            if(time >= limit){
	            	if(mana < getMaxMana())
	            		mana += Math.min(ConfigHandler.EFF_TINKLE + buff, getMaxMana() - mana);
	                ExtraBotanyAPI.unlockAdvancement(player, LibAdvancements.TINKLE_USE);
	                player.addExhaustion(0.02F);
	                time %= limit;
	            }
	
	            if(time != prevTime)
	                tag.setByte(TAG_TIME, (byte) time);
	        }
        }
	}
	
	@Override
	public int getRate(){
		return 8;
	}
	
	@Override
	public boolean willConsume(){
		return true;
	}
	
	@Override
	public void writeToPacketNBT(NBTTagCompound cmp) {
		super.writeToPacketNBT(cmp);

		cmp.setInteger(TAG_TIME, time);
	}

	@Override
	public void readFromPacketNBT(NBTTagCompound cmp) {
		super.readFromPacketNBT(cmp);

		time = cmp.getInteger(TAG_TIME);
	}
	
	@Override
	public int getMaxMana() {
		return 1000;
	}
	
	@Override
	public int getDelayBetweenPassiveGeneration() {
		return 2;
	}
	
	@Override
	public int getColor() {
		return 0xCCFF00;
	}
	
	@Override
	public RadiusDescriptor getRadius() {
		return new RadiusDescriptor.Square(toBlockPos(), RANGE);
	}
	
}
