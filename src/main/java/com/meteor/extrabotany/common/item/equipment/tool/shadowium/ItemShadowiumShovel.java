package com.meteor.extrabotany.common.item.equipment.tool.shadowium;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.client.render.IModelReg;
import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.lib.LibItemsName;
import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.item.ISortableTool;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

public class ItemShadowiumShovel extends ItemSpade implements IManaUsingItem, ISortableTool, IModelReg {
	
	private static final int MANA_PER_DAMAGE = 60;

	public ItemShadowiumShovel() {
		this(ExtraBotanyAPI.shadowiumToolMaterial, LibItemsName.SHADOWIUMSHOVEL);
	}

	public ItemShadowiumShovel(ToolMaterial mat, String name) {
		super(mat);
		setCreativeTab(ExtraBotany.tabExtraBotany);
		setRegistryName(new ResourceLocation(LibMisc.MOD_ID, name));
		setUnlocalizedName(name);
	}

	@Nonnull
	@Override
	public String getUnlocalizedNameInefficiently(@Nonnull ItemStack par1ItemStack) {
		return super.getUnlocalizedNameInefficiently(par1ItemStack).replaceAll("item\\.", "item." + LibMisc.MOD_ID + ":");
	}

	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, @Nonnull EntityLivingBase par3EntityLivingBase) {
		ToolCommons.damageItem(par1ItemStack, 1, par3EntityLivingBase, getManaPerDmg());
		return true;
	}

	@Override
	public boolean onBlockDestroyed(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull IBlockState state, @Nonnull BlockPos pos, @Nonnull EntityLivingBase entity) {
		if(state.getBlockHardness(world, pos) != 0F)
			ToolCommons.damageItem(stack, 1, entity, getManaPerDmg());

		return true;
	}

	@Nonnull
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float sx, float sy, float sz) {
		ItemStack stack = player.getHeldItem(hand);

		if(!player.canPlayerEdit(pos, side, stack))
			return EnumActionResult.PASS;
		else {
			UseHoeEvent event = new UseHoeEvent(player, stack, world, pos);
			if(MinecraftForge.EVENT_BUS.post(event))
				return EnumActionResult.FAIL;

			if(event.getResult() == Result.ALLOW) {
				ToolCommons.damageItem(stack, 1, player, MANA_PER_DAMAGE);
				return EnumActionResult.SUCCESS;
			}

			Block block = world.getBlockState(pos).getBlock();

			if(side != EnumFacing.DOWN && world.getBlockState(pos.up()).getBlock().isAir(world.getBlockState(pos.up()), world, pos.up()) && (block == Blocks.GRASS || block == Blocks.DIRT || block == Blocks.GRASS_PATH)) {
				Block block1 = Blocks.GRASS_PATH;
				if(block == block1)
					block1 = Blocks.FARMLAND;
				
				world.playSound(null, pos, block1.getSoundType().getStepSound(), SoundCategory.BLOCKS, (block1.getSoundType().getVolume() + 1.0F) / 2.0F, block1.getSoundType().getPitch() * 0.8F);

				if (world.isRemote)
					return EnumActionResult.SUCCESS;
				else {
					world.setBlockState(pos, block1.getDefaultState());
					ToolCommons.damageItem(stack, 1, player, MANA_PER_DAMAGE);
					return EnumActionResult.SUCCESS;
				}
			}

			return EnumActionResult.PASS;
		}
	}

	public int getManaPerDmg() {
		return MANA_PER_DAMAGE;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5) {
		if(!world.isRemote && player instanceof EntityPlayer && stack.getItemDamage() > 0 && ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer) player, MANA_PER_DAMAGE * 2, true))
			stack.setItemDamage(stack.getItemDamage() - 1);
	}

	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, @Nonnull ItemStack par2ItemStack) {
		return par2ItemStack.getItem() == ModItems.material && par2ItemStack.getItemDamage() == 5 ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
	}

	@Override
	public boolean usesMana(ItemStack stack) {
		return true;
	}

	@Override
	public ToolType getSortingType(ItemStack stack) {
		return ToolType.SHOVEL;
	}

	@Override
	public int getSortingPriority(ItemStack stack) {
		return ToolCommons.getToolPriority(stack);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
}
