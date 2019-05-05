package com.meteor.extrabotany.client.render.entity.gaia;

import com.meteor.extrabotany.client.model.ModelVoidHerrscher;
import com.meteor.extrabotany.common.entity.gaia.EntityVoidHerrscher;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class RenderVoidHerrscher extends RenderBiped<EntityVoidHerrscher> {

	private static final ResourceLocation GAIA_TEXTURES = new ResourceLocation("extrabotany:textures/entity/voidherrscher.png");
	private static final ResourceLocation WING_TEXTURES = new ResourceLocation("extrabotany:textures/entity/wing.png");
	private final ModelVoidHerrscher model = new ModelVoidHerrscher(0.0F);
	
	public RenderVoidHerrscher(RenderManager renderManager) {
		super(renderManager, new ModelVoidHerrscher(0.0F), 0F);
	}

	@Override
	public void doRender(@Nonnull EntityVoidHerrscher dopple, double par2, double par4, double par6, float par8, float partialTicks) {
		super.doRender(dopple, par2, par4, par6, par8, partialTicks);
	}

	@Nonnull
	@Override
	protected ResourceLocation getEntityTexture(@Nonnull EntityVoidHerrscher entity) {
		return WING_TEXTURES;
	}
}
