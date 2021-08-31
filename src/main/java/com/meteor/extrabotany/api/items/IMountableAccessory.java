package com.meteor.extrabotany.api.items;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public interface IMountableAccessory {

    public Entity getMountableEntity(World world);

}
