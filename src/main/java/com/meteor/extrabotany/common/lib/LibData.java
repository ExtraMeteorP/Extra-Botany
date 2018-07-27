package com.meteor.extrabotany.common.lib;

import com.meteor.extrabotany.common.core.handler.ConfigHandler;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class LibData {
    public static int getBookBurnTime(ItemStack stack){
    	if (stack == null){
            return 0;
        }
        else{	
        	Item item = stack.getItem();
        	if (item == Items.BOOK) return ConfigHandler.BOOK_BURNTIME;
        	if (item == Items.WRITTEN_BOOK) return ConfigHandler.WRITTENBOOK_BURNTIME;
        }
    	return 0;	
    }
    
    public static int getOreBurnTime(Block block){
    	if(block == Blocks.STONE) return 20;
    	if(block == Blocks.COBBLESTONE) return 10;
    	if(block == Blocks.COAL_ORE) return 380;
    	if(block == Blocks.IRON_ORE) return 440;
    	if(block == Blocks.DIAMOND_ORE) return 900;
    	if(block == Blocks.REDSTONE_ORE) return 400;
    	if(block == Blocks.LAPIS_ORE) return 360;
    	if(block == Blocks.GOLD_ORE) return 640;
    	if(block == Blocks.EMERALD_ORE) return 800;
    	return 0;
    }
}
