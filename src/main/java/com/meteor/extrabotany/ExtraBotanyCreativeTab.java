package com.meteor.extrabotany;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.common.block.ModBlocks;
import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.lib.LibBlocksName;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

public class ExtraBotanyCreativeTab extends CreativeTabs{
	public ExtraBotanyCreativeTab() {
		super("extrabotany");
		setNoTitle();
	}

	NonNullList<ItemStack> list;

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.hammerterrasteel);
	}
	
	@Override
	public boolean hasSearchBar() {
		return true;
	}
	
	@Override
	public void displayAllRelevantItems(@Nonnull NonNullList<ItemStack> list) {
		this.list = list;
		addBlock(ModBlocks.blockspecial);
		addBlock(ModBlocks.pedestal);
		addItem(ModItems.hammermanasteel);
		addItem(ModItems.hammerelementium);
		addItem(ModItems.hammerterrasteel);
		addItem(ModItems.hammerultimate);
		addItem(ModItems.manaReader);
		addItem(ModItems.binder);
		addItem(ModItems.froststar);
		addItem(ModItems.deathring);
		addItem(ModItems.nightmareFuel);
		addItem(ModItems.spiritFuel);
		addItem(ModItems.material);
		addBlock(ModBlocks.orichalcosblock);
		addItem(ModItems.friedchicken);
		addItem(ModItems.gildedmashedpotato);
	}
	
	private void addItem(Item item) {
		item.getSubItems(this, list);
	}

	private void addBlock(Block block) {
		ItemStack stack = new ItemStack(block);
		block.getSubBlocks(this, list);
	}
	
}
