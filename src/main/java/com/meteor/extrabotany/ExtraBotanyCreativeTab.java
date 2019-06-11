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
	
	public static final ExtraBotanyCreativeTab INSTANCE = new ExtraBotanyCreativeTab();
	NonNullList<ItemStack> list;
	
	public ExtraBotanyCreativeTab() {
		super("extrabotany");
		setNoTitle();
		setBackgroundImageName("extrabotany.png");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.kinggarden);
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
		addBlock(ModBlocks.trophy);
		addItem(ModItems.hammermanasteel);
		addItem(ModItems.hammerelementium);
		addItem(ModItems.hammerterrasteel);
		addItem(ModItems.hammerultimate);
		addItem(ModItems.walkingcane);
		addItem(ModItems.manaReader);
		addItem(ModItems.binder);
		addItem(ModItems.magicfinger);
		if(ConfigHandler.ENABLE_SHIELD){
			addItem(ModItems.manasteelshield);
			addItem(ModItems.elementiumshield);
			addItem(ModItems.terrasteelshield);
		}
		addItem(ModItems.flyingboat);
		addItem(ModItems.froststar);
		addItem(ModItems.deathring);
		addItem(ModItems.walljumping);
		addItem(ModItems.wallrunning);
		addItem(ModItems.parkour);
		addItem(ModItems.manadriverring);
		addItem(ModItems.elvenking);
		addItem(ModItems.jingweifeather);
		addItem(ModItems.puredaisypendant);
		addItem(ModItems.supercrown);
		addItem(ModItems.kinggarden);
		addItem(ModItems.bottledflame);
		addItem(ModItems.bottledpixie);
		addItem(ModItems.bottledstar);
		addItem(ModItems.masterhandbag);
		addItem(ModItems.cosmhelm);
		addItem(ModItems.cosmchest);
		addItem(ModItems.cosmleg);
		addItem(ModItems.cosmboot);
		addItem(ModItems.cmhelm);
		addItem(ModItems.cmchest);
		addItem(ModItems.cmleg);
		addItem(ModItems.cmboot);
		addItem(ModItems.gshelm);
		addItem(ModItems.gschest);
		addItem(ModItems.gsleg);
		addItem(ModItems.gsboot);
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
		addBlock(ModBlocks.shadowium);
		addBlock(ModBlocks.photonium);
		addItem(ModItems.friedchicken);
		addItem(ModItems.manadrink);
		addItem(ModItems.gildedmashedpotato);
		addItem(ModItems.candy);
		addItem(ModItems.candybag);
		addItem(ModItems.lens);
		addItem(ModItems.orb);
		addBlock(ModBlocks.batterybox);
		addBlock(ModBlocks.managenerator);
		addBlock(ModBlocks.manaliquefying);
		addBlock(ModBlocks.elfjar);
		addBlock(ModBlocks.cocoondesire);
		addItem(ModItems.rewardbag);
		addItem(ModItems.rewardbag943);
		addItem(ModItems.treasure);
		addItem(ModItems.failnaught);
		addItem(ModItems.camera);
		addItem(ModItems.excaliber);
		addItem(ModItems.relicshield);
		addItem(ModItems.spearsubspace);
		addItem(ModItems.judahoath);
		addItem(ModItems.godcore);
		addItem(ModItems.mastermanaring);
		addItem(ModItems.allforone);
		addItem(ModItems.relics);
		addItem(ModItems.cocktail);
		addItem(ModItems.infinitewine);
		addItem(ModItems.splashgrenade);
		addItem(ModItems.redscarf);
		addItem(ModItems.foxear);
		addItem(ModItems.mask);
	}
	
	private void addItem(Item item) {
		item.getSubItems(this, list);
	}

	private void addBlock(Block block) {
		ItemStack stack = new ItemStack(block);
		block.getSubBlocks(this, list);
	}
	
}
