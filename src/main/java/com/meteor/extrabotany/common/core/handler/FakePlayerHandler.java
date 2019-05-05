package com.meteor.extrabotany.common.core.handler;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;

import java.lang.ref.WeakReference;
import java.util.UUID;

public class FakePlayerHandler {
	
	private static GameProfile gameProfile;
    private static WeakReference<EntityPlayerMP> fakePlayer;

    public FakePlayerHandler(){
        gameProfile = new GameProfile(UUID.fromString("77591457-dbd0-4c11-83d4-b5017158284a"), "[ExtraBotany]");
        fakePlayer = new WeakReference<EntityPlayerMP>(null);
    }

    public static WeakReference<EntityPlayerMP> getFakePlayer(WorldServer server){
        if (fakePlayer.get() == null){
            fakePlayer = new WeakReference<EntityPlayerMP>(FakePlayerFactory.get(server, gameProfile));
        }
        else{
            fakePlayer.get().world = server;
        }
        return fakePlayer;
    }

}
