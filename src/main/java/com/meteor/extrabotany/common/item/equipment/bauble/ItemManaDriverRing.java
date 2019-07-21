package com.meteor.extrabotany.common.item.equipment.bauble;

import com.meteor.extrabotany.api.item.IAdvancementRequired;
import com.meteor.extrabotany.common.lib.LibAdvancements;
import com.meteor.extrabotany.common.lib.LibItemsName;

import baubles.api.BaubleType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.api.subtile.SubTileFunctional;
import vazkii.botania.common.block.tile.TileSpecialFlower;

public class ItemManaDriverRing extends ItemBauble implements IManaUsingItem, IAdvancementRequired{

	public ItemManaDriverRing() {
		super(LibItemsName.BAUBLE_MANADRIVERRING);
	}
	
	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase entity) {
		super.onWornTick(stack, entity);
		if(!(entity instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) entity;
		int range = 7;
		for(int x = -range; x < range; x++)
			for(int y = -range; y < range; y++)
				for(int z = -range; z < range; z++) {
					TileEntity te = player.getEntityWorld().getTileEntity(new BlockPos(player.getPosition().add(x, y, z)));
					if(te instanceof TileSpecialFlower) {
						TileSpecialFlower f = (TileSpecialFlower) te;
						if(f.getSubTile() instanceof SubTileFunctional) {
							SubTileFunctional subt = (SubTileFunctional) f.getSubTile();
							int manaToUse = subt.getMaxMana() - subt.mana;
							if(ManaItemHandler.requestManaExact(stack, player, manaToUse, true))
								subt.addMana(manaToUse);
						}
					}
				}
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.RING;
	}

	@Override
	public boolean usesMana(ItemStack arg0) {
		return true;
	}

	@Override
	public String getAdvancementName(ItemStack stack) {
		return LibAdvancements.MANADRIVERRING;
	}

}
