package com.meteor.extrabotany.common.blocks.generating;

import com.meteor.extrabotany.common.blocks.ModSubtiles;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.AxisAlignedBB;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.TileEntityGeneratingFlower;

public class SubTileEdelweiss extends TileEntityGeneratingFlower {

    private static final String TAG_BURN_TIME = "burnTime";
    private static final int RANGE = 1;
    private int burnTime = 0;

    public SubTileEdelweiss() {
        super(ModSubtiles.EDELWEISS);
    }

    @Override
    public void tickFlower() {
        super.tickFlower();

        if (burnTime > 0) {
            burnTime--;
        }

        if (linkedCollector != null) {
            if (burnTime == 0) {
                if (getMana() < getMaxMana()) {
                    for (SnowGolemEntity golem : getWorld().getEntitiesWithinAABB(SnowGolemEntity.class, new AxisAlignedBB(getEffectivePos().add(-RANGE, -RANGE, -RANGE), getEffectivePos().add(RANGE + 1, RANGE + 1, RANGE + 1)))) {
                        if(!golem.removed){
                            golem.remove();
                            addMana(1600);
                            burnTime+=5;
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void writeToPacketNBT(CompoundNBT cmp) {
        super.writeToPacketNBT(cmp);

        cmp.putInt(TAG_BURN_TIME, burnTime);
    }

    @Override
    public void readFromPacketNBT(CompoundNBT cmp) {
        super.readFromPacketNBT(cmp);

        burnTime = cmp.getInt(TAG_BURN_TIME);
    }

    @Override
    public RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Square(getEffectivePos(), RANGE);
    }

    @Override
    public int getColor() {
        return 0X4169E1;
    }

}
