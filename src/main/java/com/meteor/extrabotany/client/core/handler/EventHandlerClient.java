package com.meteor.extrabotany.client.core.handler;

import com.meteor.extrabotany.common.lib.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandlerClient {
	
	public static final EventHandlerClient INSTANCE = new EventHandlerClient();

	@SubscribeEvent
	public void handleTextureStitchPreEvent(TextureStitchEvent.Pre event) {
		event.getMap().registerSprite(new ResourceLocation(Reference.MOD_ID, "blocks/fluid/" + "mana" + "_still"));
		event.getMap().registerSprite(new ResourceLocation(Reference.MOD_ID, "blocks/fluid/" + "mana" + "_flow"));
	}
	
}
