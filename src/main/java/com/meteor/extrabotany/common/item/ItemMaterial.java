package com.meteor.extrabotany.common.item;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.common.block.tile.TileManaLiquefaction;
import com.meteor.extrabotany.common.lib.LibItemsName;
import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.item.IPetalApothecary;
import vazkii.botania.api.recipe.IFlowerComponent;
import vazkii.botania.common.block.tile.mana.TilePool;

public class ItemMaterial extends ItemMod implements IFlowerComponent{
	
	final int types = 5;

	public ItemMaterial() {
		super(LibItemsName.MATERIAL);
		setHasSubtypes(true);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Nonnull
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return "item." + LibItemsName.MANA_RESOURCE_NAMES[Math.min(types - 1, par1ItemStack.getItemDamage())];
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if(player.getHeldItem(hand).getMetadata() == 4){
			TileEntity te = world.getTileEntity(pos);
			if(te == null)
				return EnumActionResult.PASS;
		
			if(te instanceof TileManaLiquefaction){
				TileManaLiquefaction tee = (TileManaLiquefaction) te;
				if(tee.energy >= 100 && !world.isRemote){
					tee.energy -=100;
					player.inventory.addItemStackToInventory(new ItemStack(ModItems.manadrink));
					player.getHeldItem(hand).shrink(1);
				}	
			}else if(te instanceof TilePool){
				TilePool p = (TilePool) te;
				if(p.getCurrentMana() >= 100000 && !world.isRemote){
					p.recieveMana(-100000);
					player.inventory.addItemStackToInventory(new ItemStack(ModItems.manadrink));
					player.getHeldItem(hand).shrink(1);
				}
			}
		}
		
		return EnumActionResult.PASS;
	}
	
	@Override
	public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> stacks) {
		if(isInCreativeTab(tab)) {
			for(int i = 0; i < types; i++) {
				stacks.add(new ItemStack(this, 1, i));
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		for (int i = 0; i < LibItemsName.MANA_RESOURCE_NAMES.length; i++) {
			if (!"UNUSED".equals(LibItemsName.MANA_RESOURCE_NAMES[i])) {
				ModelLoader.setCustomModelResourceLocation(
					this, i,
					new ModelResourceLocation(LibMisc.MOD_ID + ":" + LibItemsName.MANA_RESOURCE_NAMES[i], "inventory")
				);
			}
		}
	}
	
	@Override
	public boolean canFit(ItemStack arg0, IPetalApothecary arg1) {
		return true;
	}

	@Override
	public int getParticleColor(ItemStack arg0) {
		return 0x9b0000;
	}

}
