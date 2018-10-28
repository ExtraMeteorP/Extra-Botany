package com.meteor.extrabotany.common.integration.tinkerconstruct;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;
import vazkii.botania.api.mana.ManaItemHandler;

public class Body extends AbstractTrait{
	
	public static final Body body = new Body();

	public Body() {
		super("body", 0XFF4500);
	}
	
	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (!world.isRemote
				&& entity instanceof EntityPlayer
				&& ToolHelper.getCurrentDurability(tool) < ToolHelper.getMaxDurability(tool)
				&& drawMana((EntityPlayer)entity)) {
			ToolHelper.unbreakTool(tool);
			ToolHelper.healTool(tool, 1, (EntityPlayer)entity);
		}
		
	}
	
	@Override
	public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
		if (!entity.getEntityWorld().isRemote
				&& entity instanceof EntityPlayer
				&& drawMana((EntityPlayer)entity)) {
			--newDamage;
		}
		return super.onToolDamage(tool, damage, newDamage, entity);
	}
	
	private static boolean drawMana(EntityPlayer ent) {
		IItemHandler handler = ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		for (int i=0; i<handler.getSlots(); ++i) {
			if (ManaItemHandler.requestManaExactForTool(handler.getStackInSlot(i), ent, 80, true)) {
				return true;
			}
		}
		
		IBaublesItemHandler ib = BaublesApi.getBaublesHandler(ent);
		for (int i=0; i<ib.getSlots(); ++i) {
			if (ManaItemHandler.requestManaExactForTool(ib.getStackInSlot(i), ent, 80, true)) {
				return true;
			}
		}
		
		return false;
 	}

}
