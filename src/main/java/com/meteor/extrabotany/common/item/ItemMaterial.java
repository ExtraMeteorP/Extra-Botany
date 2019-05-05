package com.meteor.extrabotany.common.item;

import com.meteor.extrabotany.common.block.ModBlocks;
import com.meteor.extrabotany.common.block.tile.TileManaLiquefaction;
import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.entity.gaia.EntityGaiaIII;
import com.meteor.extrabotany.common.entity.gaia.EntityVoidHerrscher;
import com.meteor.extrabotany.common.lib.LibItemsName;
import com.meteor.extrabotany.common.lib.LibMisc;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.item.IPetalApothecary;
import vazkii.botania.api.recipe.IFlowerComponent;
import vazkii.botania.common.block.tile.mana.TilePool;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import javax.annotation.Nonnull;

public class ItemMaterial extends ItemMod implements IFlowerComponent{
	
	public static final String TAG_UUID = "uuid";
	
	final int types = 10;

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
		TileEntity te = world.getTileEntity(pos);
		if(player.getHeldItem(hand).getMetadata() == 4){
			if(te == null)
				return EnumActionResult.PASS;
		
			if(te instanceof TileManaLiquefaction){
				TileManaLiquefaction tee = (TileManaLiquefaction) te;
				if(tee.energy >= 25 && !world.isRemote){
					tee.energy -=25;
					player.inventory.addItemStackToInventory(new ItemStack(ModItems.manadrink));
					player.getHeldItem(hand).shrink(1);
				}	
			}else if(te instanceof TilePool){
				TilePool p = (TilePool) te;
				if(p.getCurrentMana() >= 25000 && !world.isRemote){
					p.recieveMana(-25000);
					player.inventory.addItemStackToInventory(new ItemStack(ModItems.manadrink));
					player.getHeldItem(hand).shrink(1);
				}
			}
		}
		
		if(player.getHeldItem(hand).getMetadata() == 6 && player.isSneaking()){
			if(world.getBlockState(pos).getBlock() == ModBlocks.trophy){
				for(int i = 0; i < 9; i++)
					player.sendMessage(new TextComponentTranslation("extrabotanymisc.noveline" + i).setStyle(new Style().setColor(TextFormatting.WHITE)));
				if(!world.isRemote)
					player.entityDropItem(new ItemStack(ModItems.gaiarecord), 0);
				player.getHeldItem(hand).shrink(1);
				return EnumActionResult.PASS;
			}
			if(ConfigHandler.GAIA_ENABLE){
				return EntityGaiaIII.spawn(player, player.getHeldItem(hand), world, pos, false) ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
			}
		}
		
		if(player.getHeldItem(hand).getMetadata() == 9 && player.isSneaking()){
			if(ConfigHandler.ENABLE_HERRSCHER){
				return EntityVoidHerrscher.spawn(player, player.getHeldItem(hand), world, pos, false) ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
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
	
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		/**
		if(stack.getMetadata() == 7){
			ItemNBTHelper.setString(stack, TAG_UUID, player.getUniqueID().toString());
		}
		**/
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
	
	@Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand){
		if(stack.getMetadata() == 7  && target instanceof EntityPlayer){
			ItemNBTHelper.setString(stack, TAG_UUID, ((EntityPlayer)target).getUniqueID().toString());
		}
        return true;
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
