package com.meteor.extrabotany.common.item.bonus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.common.item.ItemMod;
import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.lib.LibItemsName;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

public class ItemRewardBagSpecial extends ItemMod {

	public ItemRewardBagSpecial() {
		super(LibItemsName.SPECIALBAG);
	}

	public static final String TAG_POOL = "exbot:pool";
	public static final ItemStack[] stacks = new ItemStack[] { new ItemStack(ModItems.silenteternity),
			new ItemStack(ModItems.lens, 1, 6), new ItemStack(ModItems.lens, 1, 6), new ItemStack(ModItems.lens, 1, 6),
			new ItemStack(ModItems.lens, 1, 6), new ItemStack(ModItems.material, 1, 3),
			new ItemStack(ModItems.material, 1, 3), new ItemStack(ModItems.material, 1, 3),
			new ItemStack(ModItems.material, 1, 3), new ItemStack(ModItems.material, 1, 3),
			new ItemStack(ModItems.material, 1, 3), new ItemStack(ModItems.material, 1, 3),
			new ItemStack(ModItems.material, 1, 3), new ItemStack(ModItems.material, 1, 3),
			new ItemStack(ModItems.material, 1, 3), new ItemStack(ModItems.rewardbag, 4, 3),
			new ItemStack(ModItems.rewardbag, 4, 3), new ItemStack(ModItems.rewardbag, 4, 3),
			new ItemStack(ModItems.rewardbag, 4, 3), new ItemStack(ModItems.rewardbag, 4, 3),
			new ItemStack(ModItems.rewardbag, 4, 2), new ItemStack(ModItems.rewardbag, 4, 2),
			new ItemStack(ModItems.rewardbag, 4, 2), new ItemStack(ModItems.rewardbag, 4, 2),
			new ItemStack(ModItems.rewardbag, 4, 2), new ItemStack(ModItems.rewardbag, 4, 2),
			new ItemStack(ModItems.rewardbag, 4, 2), new ItemStack(ModItems.rewardbag, 4, 2),
			new ItemStack(ModItems.rewardbag, 4, 2), new ItemStack(ModItems.rewardbag, 4, 2),
			new ItemStack(ModItems.rewardbag, 6, 1), new ItemStack(ModItems.rewardbag, 6, 1),
			new ItemStack(ModItems.rewardbag, 6, 1), new ItemStack(ModItems.rewardbag, 6, 1),
			new ItemStack(ModItems.rewardbag, 6, 1), new ItemStack(ModItems.rewardbag, 6, 1),
			new ItemStack(ModItems.rewardbag, 6, 1), new ItemStack(ModItems.rewardbag, 6, 1),
			new ItemStack(ModItems.rewardbag, 6, 1), new ItemStack(ModItems.rewardbag, 6, 1),
			new ItemStack(ModItems.rewardbag, 6, 1), new ItemStack(ModItems.rewardbag, 6, 1),
			new ItemStack(ModItems.rewardbag, 6, 1), new ItemStack(ModItems.rewardbag, 6, 1),
			new ItemStack(ModItems.rewardbag, 6, 1), new ItemStack(ModItems.rewardbag943, 3),
			new ItemStack(ModItems.rewardbag943, 3), new ItemStack(ModItems.rewardbag943, 3),
			new ItemStack(ModItems.rewardbag943, 3), new ItemStack(ModItems.rewardbag943, 3),
			new ItemStack(ModItems.rewardbag, 4, 3), new ItemStack(ModItems.rewardbag, 4, 3),
			new ItemStack(ModItems.rewardbag, 4, 3), new ItemStack(ModItems.rewardbag, 4, 3),
			new ItemStack(ModItems.rewardbag, 4, 3), new ItemStack(ModItems.rewardbag, 4, 2),
			new ItemStack(ModItems.rewardbag, 4, 2), new ItemStack(ModItems.rewardbag, 4, 2),
			new ItemStack(ModItems.rewardbag, 4, 2), new ItemStack(ModItems.rewardbag, 4, 2),
			new ItemStack(ModItems.rewardbag, 4, 2), new ItemStack(ModItems.rewardbag, 4, 2),
			new ItemStack(ModItems.rewardbag, 4, 2), new ItemStack(ModItems.rewardbag, 4, 2),
			new ItemStack(ModItems.rewardbag, 4, 2), new ItemStack(ModItems.rewardbag, 6, 1),
			new ItemStack(ModItems.rewardbag, 6, 1), new ItemStack(ModItems.rewardbag, 6, 1),
			new ItemStack(ModItems.rewardbag, 6, 1), new ItemStack(ModItems.rewardbag, 6, 1),
			new ItemStack(ModItems.rewardbag, 6, 1), new ItemStack(ModItems.rewardbag, 6, 1),
			new ItemStack(ModItems.rewardbag, 6, 1), new ItemStack(ModItems.rewardbag, 6, 1),
			new ItemStack(ModItems.rewardbag, 6, 1), new ItemStack(ModItems.rewardbag, 6, 1),
			new ItemStack(ModItems.rewardbag, 6, 1), new ItemStack(ModItems.rewardbag, 6, 1),
			new ItemStack(ModItems.rewardbag, 6, 1), new ItemStack(ModItems.rewardbag, 6, 1),
			new ItemStack(ModItems.rewardbag943, 3), new ItemStack(ModItems.rewardbag943, 3),
			new ItemStack(ModItems.rewardbag943, 3), new ItemStack(ModItems.rewardbag943, 3),
			new ItemStack(ModItems.rewardbag943, 3), new ItemStack(Items.DIAMOND, 4), new ItemStack(Items.DIAMOND, 4),
			new ItemStack(Items.DIAMOND, 4), new ItemStack(Items.DIAMOND, 4), new ItemStack(Items.DIAMOND, 4),
			new ItemStack(Items.DIAMOND, 4), new ItemStack(Items.DIAMOND, 4), new ItemStack(Items.DIAMOND, 4),
			new ItemStack(Items.DIAMOND, 4), new ItemStack(Items.DIAMOND, 4), new ItemStack(Items.DIAMOND, 4),
			new ItemStack(Items.DIAMOND, 4), new ItemStack(Items.DIAMOND, 4), new ItemStack(Items.DIAMOND, 4),
			new ItemStack(Items.DIAMOND, 4) };

	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		NBTTagCompound nbt = player.getEntityData().getCompoundTag(player.PERSISTED_NBT_TAG);
		if (!nbt.hasKey(TAG_POOL))
			initPool(player);
		if (nbt.hasKey(TAG_POOL) && !world.isRemote) {
			ItemStack reward = draw(player).copy();
			player.entityDropItem(reward, 0).setNoPickupDelay();
			stack.shrink(1);
			world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT,
					SoundCategory.PLAYERS, 0.5F, 0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));
		}
		return ActionResult.newResult(EnumActionResult.PASS, stack);
	}

	private void initPool(EntityPlayer player) {
		NBTTagCompound nbt = player.getEntityData().getCompoundTag(player.PERSISTED_NBT_TAG);
		NBTTagCompound nbt_ = new NBTTagCompound();
		ItemStackHandler handler = new ItemStackHandler(100);
		List<ItemStack> list = Arrays.asList(stacks);
		Collections.shuffle(list, player.world.rand);
		for (int i = 0; i < list.size(); i++) {
			handler.setStackInSlot(i, list.get(i));
		}
		nbt_ = handler.serializeNBT();
		nbt.setTag(TAG_POOL, nbt_);
	}

	private ItemStack draw(EntityPlayer player) {
		NBTTagCompound nbt = player.getEntityData().getCompoundTag(player.PERSISTED_NBT_TAG);
		NBTTagCompound nbt_ = nbt.getCompoundTag(TAG_POOL);
		ItemStackHandler handler = new ItemStackHandler();
		handler.deserializeNBT(nbt_);
		ItemStack stack = ItemStack.EMPTY;
		int rnd = player.world.rand.nextInt(handler.getSlots());
		int times = 0;
		boolean flag = false;
		while (stack.isEmpty()) {
			times++;
			if (times > 100) {
				flag = true;
				break;
			}
			rnd++;
			if (rnd >= handler.getSlots())
				rnd = 0;
			if (!handler.getStackInSlot(rnd).isEmpty()) {
				stack = handler.getStackInSlot(rnd).copy();
				handler.setStackInSlot(rnd, ItemStack.EMPTY);
				break;
			}
		}
		NBTTagCompound newpool = handler.serializeNBT();
		nbt.setTag(TAG_POOL, newpool);
		if (flag)
			return new ItemStack(Items.EMERALD, 2);
		return stack;
	}

}
