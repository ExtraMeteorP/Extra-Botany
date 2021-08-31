package com.meteor.extrabotany.api.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public interface IItemWithLeftClick {

    public void onLeftClick(PlayerEntity living, Entity target);

}
