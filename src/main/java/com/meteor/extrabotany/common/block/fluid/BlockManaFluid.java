package com.meteor.extrabotany.common.block.fluid;

import com.meteor.extrabotany.ExtraBotanyCreativeTab;
import com.meteor.extrabotany.client.render.IModelReg;
import com.meteor.extrabotany.common.lib.Reference;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockManaFluid extends BlockFluidClassic implements IModelReg{
   
	public BlockManaFluid(){
        super(ModFluid.fluidMana, Material.WATER);
        setRegistryName(new ResourceLocation(Reference.MOD_ID, "fluidedmana"));
        setCreativeTab(ExtraBotanyCreativeTab.INSTANCE);
        setUnlocalizedName("fluidedmana");
    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		final String location = Reference.MOD_ID + ":" + "fluid_mana";
        final Item itemFluid = Item.getItemFromBlock(this);
        ModelLoader.setCustomMeshDefinition(itemFluid, new ItemMeshDefinition()
        {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack)
            {
                return new ModelResourceLocation(location, "fluid");
            }
        });
        ModelLoader.setCustomStateMapper(this, new StateMapperBase()
        {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state)
            {
                return new ModelResourceLocation(location, "fluid");
            }
        });
	}
	
}
