package com.meteor.extrabotany.client.handler;

import com.mojang.blaze3d.matrix.MatrixStack;
import org.lwjgl.opengl.GL11;
import com.meteor.extrabotany.common.capability.CapabilityHandler;
import com.meteor.extrabotany.common.capability.IHerrscherEnergy;
import com.meteor.extrabotany.common.libs.LibMisc;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;

public class HerrscherGUI extends AbstractGui {
    private final int width;
    private final int height;
    private final Minecraft minecraft;
    private final ResourceLocation HUD = new ResourceLocation(LibMisc.MOD_ID, "textures/gui/hud.png");
    private MatrixStack ms;
    private int offset;

    public HerrscherGUI(MatrixStack ms, int offset) {
        this.offset = offset;
        this.ms = ms;
        this.width = Minecraft.getInstance().getMainWindow().getScaledWidth();
        this.height = Minecraft.getInstance().getMainWindow().getScaledHeight();
        this.minecraft = Minecraft.getInstance();
    }

    public void render() {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(HUD);
        LazyOptional<IHerrscherEnergy> cap = this.minecraft.player.getCapability(CapabilityHandler.HERRSCHERENERGY_CAPABILITY);
        cap.ifPresent((c) -> {
            int energy = c.getEnergy();
            renderBar(energy);
        });
    }

    private void renderBar(int energy) {
        Minecraft mc = Minecraft.getInstance();
        int width = 64;
        int x = mc.getMainWindow().getScaledWidth() / 2 - width / 2;
        int y = mc.getMainWindow().getScaledHeight() - 56 - offset;

        width *= (double) energy / 600D;
        mc.textureManager.bindTexture(HUD);

        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        blit(ms, x, y, 0, 0, 64, 6);
        if(energy < 600)
            blit(ms, x, y, 0, 6, width, 6);
        else
            blit(ms, x, y, 0, 11, 64, 6);
        RenderSystem.disableBlend();
        RenderSystem.color4f(1, 1, 1, 1);
    }


}
