package com.meteor.extrabotany.client.handler;

import com.meteor.extrabotany.common.items.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.DyeColor;
import net.minecraft.util.math.MathHelper;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.api.brew.IBrewItem;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.common.brew.ModBrews;

public final class ColorHandler {

    public static void init() {
        BlockColors blocks = Minecraft.getInstance().getBlockColors();

        ItemColors items = Minecraft.getInstance().getItemColors();

        items.register((s, t) -> {
            if (t != 1) {
                return -1;
            }

            Brew brew = ((IBrewItem) s.getItem()).getBrew(s);
            if (brew == ModBrews.fallbackBrew) {
                return 0x989898;
            }

            int color = brew.getColor(s);
            double speed = 0.1D;
            int add = (int) (Math.sin(ClientTickHandler.ticksInGame * speed) * 24);

            int r = Math.max(0, Math.min(255, (color >> 16 & 0xFF) + add));
            int g = Math.max(0, Math.min(255, (color >> 8 & 0xFF) + add));
            int b = Math.max(0, Math.min(255, (color & 0xFF) + add));

            return r << 16 | g << 8 | b;
        }, ModItems.infinitewine, ModItems.splashgrenade, ModItems.cocktail);

        items.register((s, t) -> {
            if (t != 0) {
                return -1;
            }

            return MathHelper.hsvToRGB(ClientTickHandler.ticksInGame * 2 % 360 / 360F, 0.25F, 1F);
        }, ModItems.universalpetal);

    }
}
