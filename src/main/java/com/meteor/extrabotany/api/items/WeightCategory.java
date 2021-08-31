package com.meteor.extrabotany.api.items;

import net.minecraft.item.ItemStack;

public class WeightCategory {

    private ItemStack category;
    private Integer weight;


    public WeightCategory() {
        super();
    }

    public WeightCategory(ItemStack category, Integer weight) {
        super();
        this.setCategory(category);
        this.setWeight(weight);
    }


    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public ItemStack getCategory() {
        return category;
    }

    public void setCategory(ItemStack category) {
        this.category = category;
    }

}
