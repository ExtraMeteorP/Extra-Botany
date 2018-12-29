package com.meteor.extrabotany.common.integration.tinkerconstruct;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Mind extends AbstractTrait{
	
	public static final Mind mind = new Mind();

	public Mind() {
		super("mind", 0XFFDC2D);
	}
	
	@Override
	public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
		event.setNewSpeed(event.getNewSpeed()*1.5F);
	}

}
