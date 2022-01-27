package com.meteor.extrabotany.api.item;

import com.meteor.extrabotany.common.lib.LibAdvancements;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public interface IAdvancementRequired {
	@Deprecated
	default String getAdvancementName(ItemStack stack) {
		return getRequiredAdvancementId(stack)
			.getResourcePath()
			.substring(LibAdvancements.PREFIX.length());
	}

	ResourceLocation getRequiredAdvancementId(ItemStack stack);
}
