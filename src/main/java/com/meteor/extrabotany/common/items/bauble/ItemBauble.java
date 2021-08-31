package com.meteor.extrabotany.common.items.bauble;

import com.meteor.extrabotany.common.core.EquipmentHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import javax.annotation.Nullable;

public class ItemBauble extends vazkii.botania.common.item.equipment.bauble.ItemBauble {

    public ItemBauble(Properties props) {
        super(props);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return EquipmentHandler.initBaubleCap(stack);
    }

}
