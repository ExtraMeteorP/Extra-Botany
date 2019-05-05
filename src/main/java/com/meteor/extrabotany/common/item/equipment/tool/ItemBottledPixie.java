package com.meteor.extrabotany.common.item.equipment.tool;

import com.meteor.extrabotany.common.entity.EntityPetPixie;
import com.meteor.extrabotany.common.item.ItemMod;
import com.meteor.extrabotany.common.lib.LibItemsName;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.botania.api.mana.ManaItemHandler;

import javax.annotation.Nonnull;

public class ItemBottledPixie extends ItemMod{

	public ItemBottledPixie() {
		super(LibItemsName.BOTTLEDPIXIE);
		this.setMaxStackSize(1);
	}
	
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		ItemStack stack = playerIn.getHeldItem(hand);
		if(ManaItemHandler.requestManaExactForTool(stack, playerIn, 300, true)){
			EntityPetPixie p = new EntityPetPixie(worldIn);
			p.setPosition(playerIn.posX, playerIn.posY+1.5F, playerIn.posZ);
			p.setProps(playerIn, 3.5F);
			p.onInitialSpawn(playerIn.world.getDifficultyForLocation(new BlockPos(p)), null);
			if(!worldIn.isRemote)
				worldIn.spawnEntity(p);
			playerIn.getCooldownTracker().setCooldown(this, 240);
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
}
