package com.meteor.extrabotany.common.item.record;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.ExtraBotanyCreativeTab;
import com.meteor.extrabotany.client.render.IModelReg;
import com.meteor.extrabotany.common.lib.Reference;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemModRecord extends ItemRecord implements IModelReg {

	private final String file;

	public ItemModRecord(String record, SoundEvent sound, String name) {
		super("extrabotany:" + record, sound);
		setCreativeTab(ExtraBotanyCreativeTab.INSTANCE);
		setRegistryName(new ResourceLocation(Reference.MOD_ID, name));
		setUnlocalizedName(name);
		file = "extrabotany:music." + record;
	}

	@Override
	public String getUnlocalizedNameInefficiently(@Nonnull ItemStack par1ItemStack) {
		return super.getUnlocalizedNameInefficiently(par1ItemStack).replaceAll("item\\.",
				"item." + Reference.MOD_ID + ":");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
}
