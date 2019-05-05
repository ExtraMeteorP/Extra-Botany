package com.meteor.extrabotany.common.item.equipment.bauble;

import baubles.api.BaubleType;
import com.meteor.extrabotany.common.lib.LibItemsName;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.item.ICosmeticBauble;

public class ItemRedScarf extends ItemBauble implements ICosmeticBauble{

	public ItemRedScarf() {
		super(LibItemsName.BAUBLE_REDSCARF);
	}

	private ItemStack renderStack;
	
	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		super.onWornTick(stack, player);
		
	}
	
	@Override
	public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

		renderStack = stack;
		if (type == RenderType.BODY) {
			Helper.rotateIfSneaking(player);
			boolean armor = !player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).isEmpty();
			GlStateManager.rotate(180F, 1F, 0F, 0F);
			GlStateManager.translate(0F, -0.22F, armor ? 0.2F : 0.15F);
			GlStateManager.scale(0.35F, 0.6F, 0.35F);
			renderItem();
		}
	}

	public void renderItem() {
		GlStateManager.pushMatrix();
		Minecraft.getMinecraft().getRenderItem().renderItem(renderStack, ItemCameraTransforms.TransformType.NONE);
		GlStateManager.popMatrix();
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.BODY;
	}

}
