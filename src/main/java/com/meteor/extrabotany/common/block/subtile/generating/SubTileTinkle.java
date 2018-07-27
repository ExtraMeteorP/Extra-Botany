package com.meteor.extrabotany.common.block.subtile.generating;

import com.meteor.extrabotany.common.block.tile.TilePedestal;
import com.meteor.extrabotany.common.core.handler.ConfigHandler;
import com.meteor.extrabotany.common.lexicon.LexiconData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.SubTileGenerating;

public class SubTileTinkle extends SubTileGenerating{
	
	private static final int RANGE = 9;
	private static final String TAG_TIME = "time";
	private int time = 0;
	
	@Override
	public LexiconEntry getEntry() {
		return LexiconData.tinkle;
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
	            
	            boolean hasDiamond = false;
	            
	            for(int x = -4; x < 4; x++){
					for(int z = -4; z < 4; z++){
						BlockPos posi = new BlockPos(supertile.getPos().add(x, 0, z));
						if(supertile.getWorld().getTileEntity(posi) instanceof TilePedestal){
							TilePedestal te = (TilePedestal) supertile.getWorld().getTileEntity(posi);
							Item i = te.getItem().getItem();
							if(i != null){
								if(i == Items.DIAMOND){
									hasDiamond = true;
								}
							}
						}
					}
	            }
	            
	            int buff = ConfigHandler.LP_BELLFLOWER ? hasDiamond ? 15 : 0 : 0;
	            
	            if(time >= limit){
	                mana += ConfigHandler.EFF_TINKLE + buff;
	                time %= limit;
	            }
	
	            if(time != prevTime)
	                tag.setByte(TAG_TIME, (byte) time);
	        }
        }
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
	
}
