package com.meteor.extrabotany.common.block.fluid;

import com.meteor.extrabotany.common.block.ModBlocks;
import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModFluid {
	
	public static Fluid fluidMana;
	
	public static void init(){
		fluidMana = new ManaFluid("fluidedmana");
    	FluidRegistry.registerFluid(fluidMana);
    	FluidRegistry.addBucketForFluid(fluidMana);
    	fluidMana = FluidRegistry.getFluid("fluidedmana");
	}
	
}
