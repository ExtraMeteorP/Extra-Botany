package com.meteor.extrabotany.common.item.equipment.bauble;

import baubles.api.BaubleType;
import com.meteor.extrabotany.client.core.handler.MiscellaneousIcons;
import com.meteor.extrabotany.common.lib.LibItemsName;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import vazkii.botania.api.item.ICosmeticBauble;
import vazkii.botania.client.core.helper.IconHelper;

public class ItemFoxEar extends ItemBauble implements ICosmeticBauble{

	public ItemFoxEar() {
		super(LibItemsName.BAUBLE_FOXEAR);
	}

	@Override
	public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {

		if (type == RenderType.HEAD) {
			TextureAtlasSprite icon = MiscellaneousIcons.INSTANCE.foxearIcon;
			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

			boolean flying = player.capabilities.isFlying;

			float rz = 120F;
			float rx = 20F + (float) ((Math.sin((double) (player.ticksExisted + partialTicks) * (flying ? 0.23F : 0.2F)) + 0.5F) * (flying ? 8F : 5F));
			float ry = 0F;
			float h = 0.5F;
			float i = 0.15F;
			float s = 1F;
			float x = 0.04F;

			GlStateManager.pushMatrix();
			GlStateManager.rotate(90F, 0F, 1F, 0F);
			GlStateManager.scale(0.6F, 0.6F, 0.6F);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GlStateManager.color(1F, 1F, 1F, 1F);

			int light = 15728880;
			int lightmapX = light % 65536;
			int lightmapY = light / 65536;

			float lbx = OpenGlHelper.lastBrightnessX;
			float lby = OpenGlHelper.lastBrightnessY;

			rz = 170F;
			h = -0.55F;
			rx = 20F;
			ry = -(float) ((Math.sin((double) (player.ticksExisted + partialTicks) * (0.28F)) + 0.6F) * (10F));
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapX, lightmapY);
			
			// account for padding in the texture
			float mul = 32F / 20F;
			s *= mul;

			float f = icon.getMinU();
			float f1 = icon.getMaxU();
			float f2 = icon.getMinV();
			float f3 = icon.getMaxV();
			float sr = 1F / s;

			Helper.rotateIfSneaking(player);

			GlStateManager.translate(0F, h, i);

			GlStateManager.pushMatrix();
			GlStateManager.translate(-x, 0F, 0F);
			GlStateManager.rotate(rz, 0F, 0F, 1F);
			GlStateManager.rotate(rx, 1F, 0F, 0F);
			GlStateManager.rotate(ry, 0F, 1F, 0F);
			GlStateManager.scale(s, s, s);
			IconHelper.renderIconIn3D(Tessellator.getInstance(), f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 1F / 32F);
			GlStateManager.scale(sr, sr, sr);
			GlStateManager.rotate(-ry, 0F, 1F, 0F);
			GlStateManager.rotate(-rx, 1F, 0F, 0F);
			GlStateManager.rotate(-rz, 0F, 0F, 1F);
			GlStateManager.popMatrix();

			GlStateManager.pushMatrix();
			GlStateManager.translate(x, 0F, 0F);
			GlStateManager.scale(-1F, 1F, 1F);
			GlStateManager.rotate(rz, 0F, 0F, 1F);
			GlStateManager.rotate(rx, 1F, 0F, 0F);
			GlStateManager.rotate(ry, 0F, 1F, 0F);
			GlStateManager.scale(s, s, s);
			IconHelper.renderIconIn3D(Tessellator.getInstance(), f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 1F / 32F);
			GlStateManager.scale(sr, sr, sr);
			GlStateManager.rotate(-ry, 1F, 0F, 0F);
			GlStateManager.rotate(-rx, 1F, 0F, 0F);
			GlStateManager.rotate(-rz, 0F, 0F, 1F);
			GlStateManager.popMatrix();

			GlStateManager.color(1F, 1F, 1F);
			GlStateManager.popMatrix();

			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbx, lby);
		}
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.CHARM;
	}

}
