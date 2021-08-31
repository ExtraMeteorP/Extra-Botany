package com.meteor.extrabotany.common.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IHerrscherEnergy extends INBTSerializable<CompoundNBT> {

    int getEnergy();

    void setEnergy(int energy);

    void markDirty(boolean dirty);

    boolean isDirty();
}
