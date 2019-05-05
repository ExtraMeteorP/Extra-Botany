package com.meteor.extrabotany.common.core.command;

import com.meteor.extrabotany.common.lib.LibMisc;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatEvent {

    @SubscribeEvent
    public void onMessageSent(ClientChatReceivedEvent event) {
        String text = event.getMessage().getFormattedText();
        for (int i = 0; i < LibMisc.EMOJI.length; i++)
            text = text.replace("%e" + String.valueOf(i + 1), LibMisc.EMOJI[i]);
        text = text.replace("����", "����");
        event.setMessage(new TextComponentTranslation(text));
    }

}
