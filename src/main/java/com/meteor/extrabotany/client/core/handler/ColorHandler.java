package com.meteor.extrabotany.client.core.handler;

import com.meteor.extrabotany.common.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.registries.IRegistryDelegate;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.api.brew.IBrewItem;
import vazkii.botania.client.core.handler.ClientTickHandler;

import java.awt.*;
import java.util.Map;

public final class ColorHandler {
	
	public static void init() {
		BlockColors blocks = Minecraft.getMinecraft().getBlockColors();
		Map<IRegistryDelegate<Block>, IBlockColor> map = ReflectionHelper.getPrivateValue(BlockColors.class, blocks, "blockColorMap");
		ItemColors items = Minecraft.getMinecraft().getItemColors();
		items.registerItemColorHandler((s, t) -> {
			if(t != 1)
				return -1;

			Brew brew = ((IBrewItem) s.getItem()).getBrew(s);
			if(brew == BotaniaAPI.fallbackBrew)
				return 0x989898;

			Color color = new Color(brew.getColor(s));
			double speed = 0.2;
			int add = (int) (Math.sin(ClientTickHandler.ticksInGame * speed) * 24);

			int r = Math.max(0, Math.min(255, color.getRed() + add));
			int g = Math.max(0, Math.min(255, color.getGreen() + add));
			int b = Math.max(0, Math.min(255, color.getBlue() + add));

			return r << 16 | g << 8 | b;
		}, ModItems.cocktail, ModItems.infinitewine, ModItems.splashgrenade);
	}
	
	private ColorHandler() {}

}
