package com.meteor.extrabotany.client.handler;

import com.meteor.extrabotany.common.entities.mountable.EntityMotor;
import com.meteor.extrabotany.common.handler.FlamescionHandler;
import com.meteor.extrabotany.common.handler.HerrscherHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class HUDHandler {

    public static void onOverlayRender(RenderGameOverlayEvent event){

        int offset = 0;

        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }

        ClientPlayerEntity player = Minecraft.getInstance().player;

        if (player == null ) {
            return;
        }

        if(HerrscherHandler.isHerrscherOfThunder(player)) {
            HerrscherGUI gui = new HerrscherGUI(event.getMatrixStack(), offset);
            gui.render();
            offset+=7;
        }

        Entity riding = player.getRidingEntity();

        if(riding != null){
            if(riding instanceof EntityMotor) {
                MotorGUI motorGui = new MotorGUI(event.getMatrixStack(), offset);
                motorGui.render();
                offset += 7;
            }
        }

        if(riding == null && player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() == FlamescionHandler.getFlamescionWeapon()) {
            FlamescionGUI flamescionGUI = new FlamescionGUI(event.getMatrixStack(), offset);
            flamescionGUI.render();
            offset += 7;
        }
    }

}
