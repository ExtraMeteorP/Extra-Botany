package com.meteor.extrabotany.common.core.handler;

import java.rmi.registry.Registry;

import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.common.entity.EntityFlyingBoat;
import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = LibMisc.MOD_ID)
public class KeyListener {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        EntityPlayer p = Minecraft.getMinecraft().player;

        Entity riding = p.getRidingEntity();
        if (!(riding instanceof EntityFlyingBoat)) {
            return;
        }
        EntityFlyingBoat steerable = (EntityFlyingBoat) riding;
        steerable.updateInputs(ExtraBotany.keyLeft.isKeyDown(), ExtraBotany.keyRight.isKeyDown(), ExtraBotany.keyForward.isKeyDown(), ExtraBotany.keyBackward.isKeyDown(), ExtraBotany.keyUp.isKeyDown());
    }
}
