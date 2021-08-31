package com.meteor.extrabotany.common.blocks.generating;

import com.meteor.extrabotany.common.blocks.ModSubtiles;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.TileEntityGeneratingFlower;

public class SubTileBellFlower extends TileEntityGeneratingFlower {

    private static final int RANGE = 2;

    public SubTileBellFlower() {
        super(ModSubtiles.BELL_FLOWER);
    }

    @Override
    public void tickFlower() {
        super.tickFlower();

        int baseGen = 10;
        int baseY = 90;
        int y = this.getEffectivePos().getY();

        if(this.getWorld().canBlockSeeSky(this.getEffectivePos()) && y > baseY){
            int rain = this.getWorld().isRaining() ? 3 : 0;
            int gen = (baseGen + rain) * y / baseY;
            if(this.ticksExisted % 10 == 0)
                addMana(gen);
        }
    }

    @Override
    public int getMaxMana() {
        return 300;
    }

    @Override
    public int getColor() {
        return 0xFFFF99;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Square(getEffectivePos(), RANGE);
    }

    @Override
    public void writeToPacketNBT(CompoundNBT cmp) {
        super.writeToPacketNBT(cmp);

    }

    @Override
    public void readFromPacketNBT(CompoundNBT cmp) {
        super.readFromPacketNBT(cmp);

    }

    @Override
    public boolean isPassiveFlower() {
        return true;
    }

}
