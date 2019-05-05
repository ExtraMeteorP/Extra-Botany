package com.meteor.extrabotany.common.item.equipment.tool;

import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.client.lib.LibGui;
import com.meteor.extrabotany.common.item.ItemMod;
import com.meteor.extrabotany.common.lib.LibItemsName;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemMasterHandbag extends ItemMod{
	
	private static final String TAG_ITEMS = "InvItems";

	public ItemMasterHandbag() {
		super(LibItemsName.MASTERHANDBAG);
		this.setMaxStackSize(1);
	}
	
	@Nonnull
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound oldCapNbt) {
		return new InvProvider();
	}

	private static class InvProvider implements ICapabilitySerializable<NBTBase> {

		private final IItemHandler inv = new ItemStackHandler(24) {
			@Nonnull
			@Override
			public ItemStack insertItem(int slot, @Nonnull ItemStack toInsert, boolean simulate) {
				return super.insertItem(slot, toInsert, simulate);
			}
		};

		@Override
		public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
			return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
		}

		@Override
		public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
			if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inv);
			else return null;
		}

		@Override
		public NBTBase serializeNBT() {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(inv, null);
		}

		@Override
		public void deserializeNBT(NBTBase nbt) {
			CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(inv, null, nbt);
		}
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if(stack.getTagCompound() != null && stack.getTagCompound().hasKey(TAG_ITEMS)) {
			NBTTagList oldData = stack.getTagCompound().getTagList(TAG_ITEMS, Constants.NBT.TAG_COMPOUND);
			IItemHandler newInv = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

			CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(newInv, null, oldData);

			stack.getTagCompound().removeTag(TAG_ITEMS);

			if(stack.getTagCompound().getSize() == 0)
				stack.setTagCompound(null);
		}
	}

	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		player.openGui(ExtraBotany.instance, LibGui.MASTERHANDBAG, world, hand == EnumHand.OFF_HAND ? 1 : 0, 0, 0);
		return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}

}
