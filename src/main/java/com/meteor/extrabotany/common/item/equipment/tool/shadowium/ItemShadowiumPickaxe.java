package com.meteor.extrabotany.common.item.equipment.tool.shadowium;

import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.client.render.IModelReg;
import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.lib.LibItemsName;
import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.item.ISortableTool;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.client.core.handler.ItemsRemainingRenderHandler;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

public class ItemShadowiumPickaxe extends ItemPickaxe implements IManaUsingItem, ISortableTool, IModelReg {

	private static final Pattern TORCH_PATTERN = Pattern.compile("(?:(?:(?:[A-Z-_.:]|^)torch)|(?:(?:[a-z-_.:]|^)Torch))(?:[A-Z-_.:]|$)");

	private static final int MANA_PER_DAMAGE = 60;

	public ItemShadowiumPickaxe() {
		this(ExtraBotanyAPI.shadowiumToolMaterial, LibItemsName.SHADOWIUMPICKAXE);
	}

	public ItemShadowiumPickaxe(ToolMaterial mat, String name) {
		super(mat);
		setCreativeTab(ExtraBotany.tabExtraBotany);
		setRegistryName(new ResourceLocation(LibMisc.MOD_ID, name));
		setUnlocalizedName(name);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void miningSpeed(PlayerEvent.BreakSpeed event) {
		if(!event.getEntityPlayer().getEntityWorld().isDaytime()){
			if(event.getEntityPlayer().getHeldItemMainhand().getItem().getRegistryName().toString().indexOf("shadowium") != -1)
				event.setNewSpeed(event.getNewSpeed()*3.5F);
		}
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
		for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
			ItemStack stackAt = player.inventory.getStackInSlot(i);
			if(!stackAt.isEmpty() && TORCH_PATTERN.matcher(stackAt.getItem().getUnlocalizedName()).find()) {
				ItemStack saveHeldStack = player.getHeldItem(hand);
				player.setHeldItem(hand, stackAt);
				EnumActionResult did = stackAt.getItem().onItemUse(player, world, pos, hand, side, sx, sy, sz);
				player.setHeldItem(hand, saveHeldStack);

				ItemsRemainingRenderHandler.set(player, new ItemStack(Blocks.TORCH), TORCH_PATTERN);
				return did;
			}
		}

		return EnumActionResult.PASS;
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
		return ToolType.PICK;
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
