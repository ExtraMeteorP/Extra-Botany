package com.meteor.extrabotany.common.item.equipment.bauble;

import com.meteor.extrabotany.common.lib.LibItemsName;
import net.minecraftforge.items.IItemHandler;

public class ItemElvenKingRing extends ItemAFORing{
	
	public ItemElvenKingRing() {
		super(LibItemsName.BAUBLE_ELVENKING);
	}
	
	@Override
	public int getSize(IItemHandler inv){
		return 3;
	}

}
