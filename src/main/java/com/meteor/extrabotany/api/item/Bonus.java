package com.meteor.extrabotany.api.item;

import net.minecraft.item.ItemStack;

import java.util.List;

public class Bonus {
	
	public static int sum(List<WeightCategory> categorys){
		int weightSum = 0;
    	for (WeightCategory wc : categorys) {  
            weightSum += wc.getWeight();  
        }
    	return weightSum;
	}
	
	public static void addItem(ItemStack stack, int weight, List<WeightCategory> categorys){
		WeightCategory w = new WeightCategory(stack, weight);
		categorys.add(w);
	}

}
