package com.meteor.extrabotany.common.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IFlamescion extends INBTSerializable<CompoundNBT> {

    int getEnergy();

    void setEnergy(int energy);

    boolean isOverloaded();

    void setOverloaded(boolean overloaded);

    void markDirty(boolean dirty);

    boolean isDirty();

}
