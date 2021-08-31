package com.meteor.extrabotany.common.items;

import com.meteor.extrabotany.ExtraBotany;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

public class ItemGemOfConquest extends Item {

    public ItemGemOfConquest() {
        super(new Properties().group(ExtraBotany.itemGroup).rarity(Rarity.EPIC).maxStackSize(1).setNoRepair());
    }

}
