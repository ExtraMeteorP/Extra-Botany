package com.meteor.extrabotany;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.common.block.ModBlocks;
import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.item.ModItems;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ExtraBotanyCreativeTab extends CreativeTabs{
	public ExtraBotanyCreativeTab() {
		super("extrabotany");
		setNoTitle();
		setBackgroundImageName("extrabotany.png");
	}

	NonNullList<ItemStack> list;

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.relicshield);
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
		if(ConfigHandler.ENABLE_SHIELD){
			addItem(ModItems.manasteelshield);
			addItem(ModItems.elementiumshield);
			addItem(ModItems.terrasteelshield);
		}
		addItem(ModItems.flyingboat);
		addItem(ModItems.froststar);
		addItem(ModItems.deathring);
		addItem(ModItems.kinggarden);
		addItem(ModItems.cosmhelm);
		addItem(ModItems.cosmchest);
		addItem(ModItems.cosmleg);
		addItem(ModItems.cosmboot);
		addItem(ModItems.cmhelm);
		addItem(ModItems.cmchest);
		addItem(ModItems.cmleg);
		addItem(ModItems.cmboot);
		addItem(ModItems.swhelm);
		addItem(ModItems.swchest);
		addItem(ModItems.swleg);
		addItem(ModItems.swboot);
		addItem(ModItems.shadowkatana);
		addItem(ModItems.cmchestdarkened);
		if(ExtraBotany.thaumcraftLoaded){
			addItem(ModItems.coshelmrevealing);
			addItem(ModItems.cmhelmrevealing);
		}
		addItem(ModItems.nightmareFuel);
		addItem(ModItems.spiritFuel);
		addItem(ModItems.material);
		addBlock(ModBlocks.orichalcosblock);
		addItem(ModItems.friedchicken);
		addItem(ModItems.manadrink);
		addItem(ModItems.gildedmashedpotato);
		addItem(ModItems.orb);
		addBlock(ModBlocks.batterybox);
		addBlock(ModBlocks.managenerator);
		addBlock(ModBlocks.manaliquefying);
		addBlock(ModBlocks.elfjar);
		addBlock(ModBlocks.cocoondesire);
		addItem(ModItems.rewardbag);
		addItem(ModItems.treasure);
		addItem(ModItems.failnaught);
		addItem(ModItems.camera);
		addItem(ModItems.relicshield);
		addItem(ModItems.mastermanaring);
		addItem(ModItems.cocktail);
		addItem(ModItems.infinitewine);
		addItem(ModItems.splashgrenade);
	}
	
	private void addItem(Item item) {
		item.getSubItems(this, list);
	}

	private void addBlock(Block block) {
		ItemStack stack = new ItemStack(block);
		block.getSubBlocks(this, list);
	}
	
}
