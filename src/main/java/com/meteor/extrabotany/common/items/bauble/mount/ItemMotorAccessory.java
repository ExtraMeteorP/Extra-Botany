package com.meteor.extrabotany.common.items.bauble.mount;

import com.meteor.extrabotany.api.items.IMountableAccessory;
import com.meteor.extrabotany.common.entities.mountable.EntityMotor;
import com.meteor.extrabotany.common.items.bauble.ItemBauble;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import vazkii.botania.common.item.relic.ItemRelicBauble;

public class ItemMotorAccessory extends ItemRelicBauble implements IMountableAccessory {

    public ItemMotorAccessory(Properties props) {
        super(props);
    }

    @Override
    public Entity getMountableEntity(World world) {
        EntityMotor motor = new EntityMotor(world);
        motor.setMotion(0, 0, 0);
        motor.setMountable(true);
        return motor;
    }
}
