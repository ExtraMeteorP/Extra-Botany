package com.meteor.extrabotany.common.core;

import com.mojang.authlib.GameProfile;

public interface IProxy {

    default void registerHandlers() {}

    default void preloadSkin(GameProfile customSkin) {}

}
