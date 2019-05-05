package com.meteor.extrabotany.common.item.equipment.tool;

import com.meteor.extrabotany.common.entity.EntityBottledStar;
import com.meteor.extrabotany.common.item.ItemMod;
import com.meteor.extrabotany.common.lib.LibItemsName;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import vazkii.botania.api.mana.ManaItemHandler;

import javax.annotation.Nonnull;

public class ItemBottledStar extends ItemMod{

	public ItemBottledStar() {
		super(LibItemsName.BOTTLEDSTAR);
		this.setMaxStackSize(1);
	}
	
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		ItemStack stack = playerIn.getHeldItem(hand);
		if(ManaItemHandler.requestManaExactForTool(stack, playerIn, 300, true)){
			EntityBottledStar star = new EntityBottledStar(worldIn, playerIn);
			worldIn.spawnEntity(star);
			playerIn.getCooldownTracker().setCooldown(this, 100);
		}
					
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

}
