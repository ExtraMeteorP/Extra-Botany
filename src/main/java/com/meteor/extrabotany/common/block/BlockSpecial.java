package com.meteor.extrabotany.common.block;

import java.util.Arrays;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.common.lib.LibBlocksName;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.common.block.BlockSpecialFlower;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

public class BlockSpecial extends BlockSpecialFlower{

	static {
		ExtraBotany.subtilesForCreativeMenu.addAll(Arrays.asList(new String[] {	
				LibBlocksName.SUBTILE_BLOODYENCHANTRESS,
				LibBlocksName.SUBTILE_SUNBLESS,
				LibBlocksName.SUBTILE_MOONBLESS,
				LibBlocksName.SUBTILE_OMINIVIOLET,
				LibBlocksName.SUBTILE_STONESIA,
				LibBlocksName.SUBTILE_TINKLE,
				LibBlocksName.SUBTILE_BELLFLOWER,
				LibBlocksName.SUBTILE_ANNOYINGFLOWER,
				LibBlocksName.SUBTILE_STARDUSTLOTUS,
				LibBlocksName.SUBTILE_MANALINKIUM
		}));
	}
	
	@Override
	public void getSubBlocks(CreativeTabs tab, @Nonnull NonNullList<ItemStack> stacks) {
		for(String s : ExtraBotany.subtilesForCreativeMenu) {
			stacks.add(ItemBlockSpecialFlower.ofType(s));
			if(BotaniaAPI.miniFlowers.containsKey(s))
				stacks.add(ItemBlockSpecialFlower.ofType(BotaniaAPI.miniFlowers.get(s)));
		}
	}
	
}
