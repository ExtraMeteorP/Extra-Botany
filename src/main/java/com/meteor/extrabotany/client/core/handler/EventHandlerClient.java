package com.meteor.extrabotany.client.core.handler;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;

import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.lib.Reference;

import baubles.api.BaublesApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventHandlerClient {

	private static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, "textures/gui/cursor.png");
	public static final EventHandlerClient INSTANCE = new EventHandlerClient();

	@SubscribeEvent
	public void handleTextureStitchPreEvent(TextureStitchEvent.Pre event) {
		event.getMap().registerSprite(new ResourceLocation(Reference.MOD_ID, "blocks/fluid/" + "mana" + "_still"));
		event.getMap().registerSprite(new ResourceLocation(Reference.MOD_ID, "blocks/fluid/" + "mana" + "_flow"));
	}

	public static void loadCursor(BufferedImage img) throws LWJGLException {
		int w = img.getWidth();
		int h = img.getHeight();
		int[] rgbData = new int[w * h];
		for (int i = 0; i < rgbData.length; i++) {
			int x = i % w;
			int y = h - 1 - i / w;
			rgbData[i] = img.getRGB(x, y);
		}
		IntBuffer buffer = BufferUtils.createIntBuffer(w * h);
		buffer.put(rgbData);
		buffer.rewind();
		Cursor cursor = new Cursor(w, h, 2, h - 2, 1, buffer, null);
		Mouse.setNativeCursor(cursor);
	}
	
	public static void loadCursor() {
		try {
			BufferedImage image = ImageIO.read(new FileInputStream("mods/cursor.png"));
			loadCursor(image);
		} catch (IOException | org.lwjgl.LWJGLException e) {
			e.printStackTrace();
			try {
				Files.write(Paths.get("", new String[] { "exbot-error.txt" }), e.getMessage().getBytes(),
						new java.nio.file.OpenOption[0]);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
