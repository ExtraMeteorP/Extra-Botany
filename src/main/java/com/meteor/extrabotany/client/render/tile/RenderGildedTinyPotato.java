package com.meteor.extrabotany.client.render.tile;

/**
public class RenderGildedTinyPotato extends TileEntitySpecialRenderer<TileGildedTinyPotato>{
	
	private static final ResourceLocation texture = new ResourceLocation(LibResources.MODEL_TINY_POTATO);
	private static final ModelTinyPotato model = new ModelTinyPotato();
	
	@Override
	public void render(@Nonnull TileGildedTinyPotato potato, double x, double y, double z, float partialTicks, int destroyStage, float unused) {
		if(!potato.getWorld().isBlockLoaded(potato.getPos(), false)
				|| potato.getWorld().getBlockState(potato.getPos()).getBlock() != ModBlocks.gildedtinypotato)
			return;

		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.translate(x, y, z);

		Minecraft mc = Minecraft.getMinecraft();
		mc.renderEngine.bindTexture(texture);
		String name = potato.name.toLowerCase().trim();

		GlStateManager.translate(0.5F, 1.5F, 0.5F);
		GlStateManager.scale(1F, -1F, -1F);
		int meta = potato.getWorld() == null ? 3 : potato.getBlockMetadata();
		float rotY = meta * 90F - 180F;
		GlStateManager.rotate(rotY, 0F, 1F, 0F);

		float jump = potato.jumpTicks;
		if (jump > 0)
			jump -= partialTicks;

		float up = (float) -Math.abs(Math.sin(jump / 10 * Math.PI)) * 0.2F;
		float rotZ = (float) Math.sin(jump / 10 * Math.PI) * 2;

		GlStateManager.translate(0F, up, 0F);
		GlStateManager.rotate(rotZ, 0F, 0F, 1F);

		GlStateManager.pushMatrix();
		
		model.render();

		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

		GlStateManager.pushMatrix();
		GlStateManager.rotate(180F, 0, 0, 1);
		GlStateManager.translate(0F, -1F, 0F);
		float s = 1F / 3.5F;
		GlStateManager.scale(s, s, s);

		EnumFacing potatoFacing = potato.getWorld().getBlockState(potato.getPos()).getValue(BotaniaStateProps.CARDINALS);

		GlStateManager.popMatrix();

		if (!name.isEmpty()) {

			float scale = 1F / 4F;
			GlStateManager.translate(0F, 1F, 0F);
			GlStateManager.scale(scale, scale, scale);
		}
		GlStateManager.popMatrix();

		MinecraftForge.EVENT_BUS.post(new TinyPotatoRenderEvent(potato, potato.name, x, y, z, partialTicks, destroyStage));

		GlStateManager.rotate(-rotZ, 0F, 0F, 1F);
		GlStateManager.rotate(-rotY, 0F, 1F, 0F);
		GlStateManager.color(1F, 1F, 1F);
		GlStateManager.scale(1F, -1F, -1F);

		RayTraceResult pos = mc.objectMouseOver;
		if (!name.isEmpty() && pos != null && pos.getBlockPos() != null && potato.getPos().equals(pos.getBlockPos())) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(0F, -0.6F, 0F);
			GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
			float f = 1.6F;
			float f1 = 0.016666668F * f;
			GlStateManager.scale(-f1, -f1, f1);
			GlStateManager.disableLighting();
			GlStateManager.translate(0.0F, 0F / f1, 0.0F);
			GlStateManager.depthMask(false);
			GlStateManager.enableBlend();
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder worldrenderer = tessellator.getBuffer();
			GlStateManager.disableTexture2D();
			worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
			int i = mc.fontRenderer.getStringWidth(potato.name) / 2;
			worldrenderer.pos(-i - 1, -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
			worldrenderer.pos(-i - 1, 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
			worldrenderer.pos(i + 1, 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
			worldrenderer.pos(i + 1, -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
			tessellator.draw();
			GlStateManager.enableTexture2D();
			GlStateManager.depthMask(true);
			mc.fontRenderer.drawString(potato.name, -mc.fontRenderer.getStringWidth(potato.name) / 2, 0, 0xFFFFFF);

			GlStateManager.enableLighting();
			GlStateManager.disableBlend();
			GlStateManager.color(1F, 1F, 1F, 1F);
			GlStateManager.scale(1F / -f1, 1F / -f1, 1F / f1);
			GlStateManager.popMatrix();
		}

		GlStateManager.popMatrix();
	}

}
**/