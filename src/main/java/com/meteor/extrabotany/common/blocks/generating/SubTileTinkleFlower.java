package com.meteor.extrabotany.common.blocks.generating;

import com.meteor.extrabotany.common.blocks.ModSubtiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.TileEntityGeneratingFlower;

public class SubTileTinkleFlower extends TileEntityGeneratingFlower {

    private static final int RANGE = 8;
    private static final String TAG_TIME = "time";
    private int time = 0;

    public SubTileTinkleFlower() {
        super(ModSubtiles.TINKLEFLOWER);
    }

    @Override
    public void tickFlower() {
        super.tickFlower();

        if(!world.isRemote && world.getGameTime() % 20L == 0){
            CompoundNBT tag = getTileData();
            int time = tag.getByte(TAG_TIME);
            int prevTime = time;
            for(PlayerEntity player : getWorld().getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB(getEffectivePos().add(-RANGE, -RANGE, -RANGE), getEffectivePos().add(RANGE + 1, RANGE + 1, RANGE + 1)))) {
                double vx = player.getPosX() - player.chasingPosX;
                double vy = player.getPosY() - player.chasingPosY;
                double vz = player.getPosZ() - player.chasingPosZ;
                double vel = Math.sqrt(vx*vx + vy*vy + vz*vz);
                if(player.isPotionActive(Effects.SPEED))
                    vel *= 1.2;

                time += MathHelper.clamp((int) (vel * 10.0), 0, 8);

                final int limit = 10;

                if(time >= limit){
                    if(getMana() < getMaxMana())
                        addMana(30);

                    player.addExhaustion(0.02F);
                    time %= limit;
                }

                if(time != prevTime)
                    tag.putByte(TAG_TIME, (byte) time);
            }
        }

    }

    @Override
    public int getMaxMana() {
        return 1000;
    }

    @Override
    public int getColor() {
        return 0xCCFF00;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Square(getEffectivePos(), RANGE);
    }

    @Override
    public void writeToPacketNBT(CompoundNBT cmp) {
        super.writeToPacketNBT(cmp);
        cmp.putInt(TAG_TIME, time);
    }

    @Override
    public void readFromPacketNBT(CompoundNBT cmp) {
        super.readFromPacketNBT(cmp);
        time = cmp.getInt(TAG_TIME);
    }

}
