package com.meteor.extrabotany.common.item.equipment.bauble;

import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.lib.LibItemsName;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.api.item.ICosmeticBauble;

public class ItemSuperCrown extends ItemBauble implements ICosmeticBauble{
	
	private ItemStack renderStack;

	public ItemSuperCrown() {
		super(LibItemsName.BAUBLE_SUPERCROWN);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onEntityDamaged(LivingHurtEvent event) {
		if(!(event.getEntityLiving() instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) event.getEntityLiving();
		if(BaublesApi.isBaubleEquipped(player, ModItems.supercrown) != -1 && hasArmorSet(player))
			event.setAmount(Math.max(0, event.getAmount() - 2));
	}
	
	public boolean hasArmorSet(EntityPlayer player) {
		return hasArmorSetItem(player, 0) && hasArmorSetItem(player, 1) && hasArmorSetItem(player, 2) && hasArmorSetItem(player, 3);
	}
	
	public boolean hasArmorSetItem(EntityPlayer player, int i) {
		if(player == null || player.inventory == null || player.inventory.armorInventory == null)
			return false;
		
		ItemStack stack = player.inventory.armorInventory.get(3 - i);
		if(stack.isEmpty())
			return false;

		switch(i) {
		case 0: return stack.getItem() == ModItems.cosmhelm || stack.getItem() == ModItems.coshelmrevealing || stack.getItem() == ModItems.cmhelm || stack.getItem() == ModItems.cmhelmrevealing;
		case 1: return stack.getItem() == ModItems.cosmchest || stack.getItem() == ModItems.cmchest || stack.getItem() == ModItems.cmchestdarkened;
		case 2: return stack.getItem() == ModItems.cosmleg || stack.getItem() == ModItems.cmleg;
		case 3: return stack.getItem() == ModItems.cosmboot || stack.getItem() == ModItems.cmboot;
		}

		return false;
	}

	@Override
	public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

		renderStack = stack;
		if (type == RenderType.HEAD) {
			Helper.translateToHeadLevel(player);
			Helper.translateToFace();
			Helper.defaultTransforms();
			scale(1.4F);
			GlStateManager.translate(0F, 0.65F, 0.3F);
			renderItem();
		}
	}
	
	public void scale(float f) {
		GlStateManager.scale(f, f, f);
	}

	public void renderItem() {
		GlStateManager.pushMatrix();
		Minecraft.getMinecraft().getRenderItem().renderItem(renderStack, ItemCameraTransforms.TransformType.NONE);
		GlStateManager.popMatrix();
	}

	@Override
	public BaubleType getBaubleType(ItemStack stack) {
		return BaubleType.HEAD;
	}

}
