package com.meteor.extrabotany.client.particle;

import org.lwjgl.opengl.GL11;

import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.client.core.helper.ShaderHelper;

@SideOnly(Side.CLIENT)
public class ParticleCloudPattern extends Particle{
	
	private final Entity entity;
	private ResourceLocation texture = new ResourceLocation(LibMisc.MOD_ID, "textures/particle/cloudpattern.png");
	
	public ParticleCloudPattern(Entity entity) {
		super(entity.world, entity.posX + entity.world.rand.nextDouble() - 0.5, entity.posY + 0.8F + entity.world.rand.nextDouble()*0.25F, entity.posZ + entity.world.rand.nextDouble() - 0.5);
	    this.entity = entity;
	    this.particleMaxAge = 40;
	    this.multipleParticleScaleBy(1.35F);
	}
	
	 @Override
	 @SideOnly(Side.CLIENT)
	 public void renderParticle(BufferBuilder buffer, Entity e, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
	     if (e instanceof EntityPlayer && Minecraft.getMinecraft().player.getUniqueID().equals(entity.getUniqueID()) && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 && this.entity.getPosition().add(0, 1, 0).distanceSq(posX, posY, posZ) < 3)
	    	 return;
		 GlStateManager.pushMatrix();
	     float f10 = 0.1f * this.particleScale;
	     float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)p_180434_3_ - interpPosX);
	     float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)p_180434_3_ - interpPosY);
	     float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)p_180434_3_ - interpPosZ);
	        Tessellator.getInstance().draw();
	        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
	        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
	        buffer.pos((double)(f11 - p_180434_4_ * f10 - p_180434_7_ * f10), (double)(f12 - p_180434_5_ * f10), (double)(f13 - p_180434_6_ * f10 - p_180434_8_ * f10)).tex(1.0, 1.0).endVertex();
	        buffer.pos((double)(f11 - p_180434_4_ * f10 + p_180434_7_ * f10), (double)(f12 + p_180434_5_ * f10), (double)(f13 - p_180434_6_ * f10 + p_180434_8_ * f10)).tex(1.0, 0.0).endVertex();
	        buffer.pos((double)(f11 + p_180434_4_ * f10 + p_180434_7_ * f10), (double)(f12 + p_180434_5_ * f10), (double)(f13 + p_180434_6_ * f10 + p_180434_8_ * f10)).tex(0.0, 0.0).endVertex();
	        buffer.pos((double)(f11 + p_180434_4_ * f10 - p_180434_7_ * f10), (double)(f12 - p_180434_5_ * f10), (double)(f13 + p_180434_6_ * f10 - p_180434_8_ * f10)).tex(0.0, 1.0).endVertex();
	        Tessellator.getInstance().draw();
	        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
	        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
	        GlStateManager.popMatrix();
	        
	 }
	 
	 
	 
	 @Override
	 public void onUpdate(){
		 this.prevPosX = this.posX;
		 this.prevPosY = this.posY;
		 this.prevPosZ = this.posZ;
		 this.motionY += 0.004D;
		 this.move(this.motionX, this.motionY, this.motionZ);
		 this.motionX *= 0.8500000238418579D;
		 this.motionY *= 0.8500000238418579D;
		 this.motionZ *= 0.8500000238418579D;
		float s = this.particleMaxAge-- > 20 ? 1.03F:0.95F;
		this.multipleParticleScaleBy(s);

		 if (this.particleMaxAge-- <= 0){
			 this.setExpired();
		 }
	 }

}
