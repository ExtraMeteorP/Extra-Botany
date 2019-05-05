package com.meteor.extrabotany.client.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.opengl.GL11;
import vazkii.botania.api.item.IBaubleRender;
import vazkii.botania.api.item.IBaubleRender.RenderType;
import vazkii.botania.common.item.ItemBaubleBox;

import javax.annotation.Nonnull;

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
