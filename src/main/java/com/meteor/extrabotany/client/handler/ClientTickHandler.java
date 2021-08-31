package com.meteor.extrabotany.client.handler;

import net.minecraftforge.event.TickEvent;

public final class ClientTickHandler {

    public static int ticksInGame = 0;

    public static void clientTickEnd(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            ticksInGame++;
        }
    }
}
