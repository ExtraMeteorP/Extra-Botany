package com.meteor.extrabotany.common.item.equipment.bauble;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.meteor.extrabotany.common.lib.LibItemsName;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import vazkii.botania.api.mana.IManaGivingItem;
import vazkii.botania.common.item.ItemBaubleBox;

public class ItemAFORing extends ItemBaubleRelic implements IManaGivingItem{
	
	public ItemAFORing(String name) {
		super(name);
	}

	public ItemAFORing() {
		this(LibItemsName.BAUBLE_ALLFORONE);
	}
	
	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase entity) {
		super.onWornTick(stack, entity);
		if(!(entity instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) entity;
		for(int i = 0; i < player.inventory.getSizeInventory(); i++){
			ItemStack box = player.inventory.getStackInSlot(i);
			if(box.getItem() instanceof ItemBaubleBox && player.inventory.hasItemStack(box)){
				IItemHandler newInv = box.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
				for(int s = 0; s < getSize(newInv); s++){
					if(newInv.getStackInSlot(s).getItem() instanceof IBauble && !(newInv.getStackInSlot(s).getItem() instanceof ItemAFORing)){
						((IBauble)newInv.getStackInSlot(s).getItem()).onWornTick(newInv.getStackInSlot(s), player);
					}
				}
				break;
			}
		}
	}
	
	public int getSize(IItemHandler inv){
		return inv.getSlots();
	}
	
	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.RING;
	}

}
