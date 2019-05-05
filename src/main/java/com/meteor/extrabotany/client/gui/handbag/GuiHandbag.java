package com.meteor.extrabotany.client.gui.handbag;

import com.meteor.extrabotany.common.lib.LibMisc;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public class GuiHandbag extends GuiContainer {

	private static final ResourceLocation texture = new ResourceLocation(LibMisc.MOD_ID, "textures/gui/handbag.png");

	public GuiHandbag(EntityPlayer player, InventoryHandbag box) {
		super(new ContainerHandbag(player, box));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		for(int i1 = 0; i1 < 7; ++i1) {
			Slot slot = inventorySlots.inventorySlots.get(i1);
			if(slot.getHasStack() && slot.getSlotStackLimit() == 1)
				drawTexturedModalRect(guiLeft+slot.xPos, guiTop+slot.yPos, 200, 0, 16, 16);
		}
	}
}
