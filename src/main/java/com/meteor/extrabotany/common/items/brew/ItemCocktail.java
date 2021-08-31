package com.meteor.extrabotany.common.items.brew;

import com.meteor.extrabotany.common.items.ModItems;

public class ItemCocktail extends ItemBrewBase{

    public ItemCocktail(Properties builder) {
        super(builder, 8, 20, 1.3F, 0, () -> ModItems.emptybottle);
    }

}
