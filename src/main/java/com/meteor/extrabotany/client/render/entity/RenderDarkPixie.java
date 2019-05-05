package com.meteor.extrabotany.client.render.entity;

import com.meteor.extrabotany.client.ClientProxy;
import com.meteor.extrabotany.client.lib.LibResource;
import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.entity.EntityDarkPixie;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import vazkii.botania.api.internal.ShaderCallback;
import vazkii.botania.client.core.helper.ShaderHelper;
import vazkii.botania.client.model.ModelPixie;

import javax.annotation.Nonnull;

public class RenderDarkPixie extends RenderLiving<EntityDarkPixie> {

	final ShaderCallback callback = shader -> {
		// Frag Uniforms
		int disfigurationUniform = ARBShaderObjects.glGetUniformLocationARB(shader, "disfiguration");
		ARBShaderObjects.glUniform1fARB(disfigurationUniform, 0.025F);

		// Vert Uniforms
		int grainIntensityUniform = ARBShaderObjects.glGetUniformLocationARB(shader, "grainIntensity");
		ARBShaderObjects.glUniform1fARB(grainIntensityUniform, 0.05F);
	};

	public RenderDarkPixie(RenderManager renderManager) {
		super(renderManager, new ModelPixie(), 0.25F);
		//setRenderPassModel(new ModelPixie());
		shadowSize = 0.0F;
	}

	@Nonnull
	@Override
	protected ResourceLocation getEntityTexture(@Nonnull EntityDarkPixie entity) {
		if(ClientProxy.halloween && ConfigHandler.ENABLE_FEATURES)
			return new ResourceLocation(LibResource.DARKPIXIE_PUMPKIN);
		return new ResourceLocation(LibResource.DARKPIXIE);
	}

	@Override
	public void doRender(@Nonnull EntityDarkPixie pixie, double par2, double par4, double par6, float par8, float par9) {
		if(pixie.getType() == 1)
			ShaderHelper.useShader(ShaderHelper.doppleganger, callback);
		super.doRender(pixie, par2, par4, par6, par8, par9);
		if(pixie.getType() == 1)
			ShaderHelper.releaseShader();
	}

	protected int setPixieBrightness(EntityDarkPixie par1EntityPixie, int par2, float par3) {
		if (par2 != 0)
			return -1;
		else {
			bindTexture(getEntityTexture(par1EntityPixie));
			float f1 = 1.0F;
			GlStateManager.enableBlend();
			GlStateManager.disableAlpha();
			GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_ONE);

			if (par1EntityPixie.isInvisible())
				GlStateManager.depthMask(false);
			else
				GlStateManager.depthMask(true);

			char c0 = 61680;
			int j = c0 % 65536;
			int k = c0 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.color(1.0F, 1.0F, 1.0F, f1);
			return 1;
		}
	}
}
