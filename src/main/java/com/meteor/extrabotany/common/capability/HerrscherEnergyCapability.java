package com.meteor.extrabotany.common.capability;

import net.minecraft.nbt.CompoundNBT;

public class HerrscherEnergyCapability implements IHerrscherEnergy{

    private int energy;
    private boolean isDirty;

    public HerrscherEnergyCapability(int energy){
        this.energy = energy;
        this.isDirty = false;
    }

    @Override
    public int getEnergy() {
        return this.energy;
    }

    @Override
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    @Override
    public void markDirty(boolean dirty) {
        this.isDirty = dirty;
    }

    @Override
    public boolean isDirty(){
        return this.isDirty;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putInt("energy", this.energy);
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.energy = nbt.getInt("energy");
    }
}
