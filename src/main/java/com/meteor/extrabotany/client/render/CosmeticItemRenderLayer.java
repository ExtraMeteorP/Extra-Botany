package com.meteor.extrabotany.client.render;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import com.meteor.extrabotany.client.model.ModelGreenHat;
import com.meteor.extrabotany.common.brew.ModPotions;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import vazkii.botania.api.item.IBaubleRender;
import vazkii.botania.api.item.IBaubleRender.RenderType;
import vazkii.botania.common.item.ItemBaubleBox;

public class CosmeticItemRenderLayer implements LayerRenderer<EntityPlayer> {

	@Override
	public void doRenderLayer(@Nonnull EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		
		if(player.getActivePotionEffect(MobEffects.INVISIBILITY) != null)
			return;

		dispatchRenders(player, RenderType.BODY, partialTicks);

		float yaw = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * partialTicks;
		float yawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks;
		float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks;

		GlStateManager.pushMatrix();
		GlStateManager.rotate(yawOffset, 0, -1, 0);
		GlStateManager.rotate(yaw - 270, 0, 1, 0);
		GlStateManager.rotate(pitch, 0, 0, 1);
		dispatchRenders(player, RenderType.HEAD, partialTicks);
		greenHats(player, partialTicks);
		GlStateManager.popMatrix();
	}
	
	private void greenHats(EntityPlayer player, float partialTicks) {
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		GlStateManager.pushMatrix();
		int hats = player.isPotionActive(ModPotions.vegetable) ? player.getActivePotionEffect(ModPotions.vegetable).getAmplifier() + 1 : 0;
		//GlStateManager.translate(-0.2F, 0F, 0.375F);
		GlStateManager.pushMatrix();
		GL11.glColor3ub((byte) 255, (byte) 255, (byte) 255); 
		GlStateManager.color(1F, 1F, 1F, 1F);
			/**
			Helper.translateToHeadLevel(player);
			Helper.translateToFace();
			Helper.defaultTransforms();
			GlStateManager.scale(1.4F, 1.6F, 1.4F);
			GlStateManager.translate(0F, 0.35F * i, 0F);
			GlStateManager.translate(-0.01F * i, 0F, 0F);
			TextureAtlasSprite gemIcon = MiscellaneousIcons.INSTANCE.greenhatIcon;
			float f = gemIcon.getMinU();
			float f1 = gemIcon.getMaxU();
			float f2 = gemIcon.getMinV();
			float f3 = gemIcon.getMaxV();
			IconHelper.renderIconIn3D(Tessellator.getInstance(), f1, f2, f, f3, gemIcon.getIconWidth(), gemIcon.getIconHeight(), 1F / 32F);
			**/
		ModelGreenHat hat1 = new ModelGreenHat();
		ModelGreenHat hat2 = new ModelGreenHat();
		ModelGreenHat hat3 = new ModelGreenHat();
		ModelGreenHat hat4 = new ModelGreenHat();
		ModelGreenHat hat5 = new ModelGreenHat();
		ModelGreenHat hat6 = new ModelGreenHat();
		ModelGreenHat hat7 = new ModelGreenHat();
		ModelGreenHat hat8 = new ModelGreenHat();
		ModelGreenHat hat9 = new ModelGreenHat();
		ModelGreenHat hat10 = new ModelGreenHat();
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("extrabotany:textures/model/greenhat.png"));
		GlStateManager.pushMatrix();
		if(hats > 0)
			hat1.render(player, player.limbSwing, player.limbSwingAmount, 1/16F);
		GlStateManager.popMatrix();	
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(0F, -0.35F, 0F);
		if(hats > 1)
			hat2.render(player, player.limbSwing, player.limbSwingAmount, 1/16F);
		GlStateManager.popMatrix();	
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(0F, -0.7F, 0F);
		if(hats > 2)
			hat3.render(player, player.limbSwing, player.limbSwingAmount, 1/16F);
		GlStateManager.popMatrix();	
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(0F, -1.05F, 0F);
		if(hats > 3)
			hat4.render(player, player.limbSwing, player.limbSwingAmount, 1/16F);
		GlStateManager.popMatrix();	
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(0F, -1.4F, 0F);
		if(hats > 4)
			hat5.render(player, player.limbSwing, player.limbSwingAmount, 1/16F);
		GlStateManager.popMatrix();	
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(0F, -1.75F, 0F);
		if(hats > 5)
			hat6.render(player, player.limbSwing, player.limbSwingAmount, 1/16F);
		GlStateManager.popMatrix();	
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(0F, -2.1F, 0F);
		if(hats > 6)
			hat7.render(player, player.limbSwing, player.limbSwingAmount, 1/16F);
		GlStateManager.popMatrix();	
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(0F, -2.45F, 0F);
		if(hats > 7)
			hat8.render(player, player.limbSwing, player.limbSwingAmount, 1/16F);
		GlStateManager.popMatrix();	
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(0F, -2.8F, 0F);
		if(hats > 8)
			hat9.render(player, player.limbSwing, player.limbSwingAmount, 1/16F);
		GlStateManager.popMatrix();	
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(0F, -3.15F, 0F);
		if(hats > 9)
			hat10.render(player, player.limbSwing, player.limbSwingAmount, 1/16F);
		GlStateManager.popMatrix();	
		
		GlStateManager.popMatrix();				
		GlStateManager.popMatrix();
	}

	private void dispatchRenders(EntityPlayer player, RenderType type, float partialTicks) {
		
		for(int i = 0; i < player.inventory.getSizeInventory(); i++){
			ItemStack stack = player.inventory.getStackInSlot(i);
			if(stack.getItem() instanceof ICosmeticItem && player.inventory.hasItemStack(stack) && !player.getHeldItemMainhand().isItemEqual(stack)){
				GlStateManager.pushMatrix();
				GL11.glColor3ub((byte) 255, (byte) 255, (byte) 255); 
				GlStateManager.color(1F, 1F, 1F, 1F);
				((ICosmeticItem)stack.getItem()).onItemRender(stack, player, type, partialTicks);
				GlStateManager.popMatrix();
			}
			
			if(stack.getItem() instanceof ItemBaubleBox && player.inventory.hasItemStack(stack) && player.getName() == "ExtraMeteorP"){
				IItemHandler newInv = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
				for(int s = 0; s < newInv.getSlots(); s++){
					if(newInv.getStackInSlot(s).getItem() instanceof IBaubleRender){
						GlStateManager.pushMatrix();
						GL11.glColor3ub((byte) 255, (byte) 255, (byte) 255); 
						GlStateManager.color(1F, 1F, 1F, 1F);
						((IBaubleRender)newInv.getStackInSlot(s).getItem()).onPlayerBaubleRender(newInv.getStackInSlot(s), player, type, partialTicks);
						GlStateManager.popMatrix();
					}
				}
			}
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
}
