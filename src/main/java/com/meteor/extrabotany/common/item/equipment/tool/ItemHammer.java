package com.meteor.extrabotany.common.item.equipment.tool;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.ExtraBotanyCreativeTab;
import com.meteor.extrabotany.api.item.IHammer;
import com.meteor.extrabotany.client.render.IModelReg;
import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;

public class ItemHammer extends ItemPickaxe implements IModelReg, IHammer, IManaUsingItem{
	
	private static final int MANA_PER_DAMAGE = 60;

	public ItemHammer(String name, ToolMaterial material) {
		super(material);
		setCreativeTab(ExtraBotanyCreativeTab.INSTANCE);
		setRegistryName(new ResourceLocation(LibMisc.MOD_ID, name));
		setUnlocalizedName(name);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}

	@Nonnull
	@Override
	public String getUnlocalizedNameInefficiently(@Nonnull ItemStack par1ItemStack) {
		return super.getUnlocalizedNameInefficiently(par1ItemStack).replaceAll("item\\.", "item." + LibMisc.MOD_ID + ":");
	}
	
	public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5) {
		if(!world.isRemote && player instanceof EntityPlayer && stack.getItemDamage() > 0 && ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer) player, MANA_PER_DAMAGE * 2, true))
			stack.setItemDamage(stack.getItemDamage() - 1);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}

	@Override
	public boolean isHammer() {
		return true;
	}
	
	@SubscribeEvent
	public void onMining(PlayerEvent.BreakSpeed event){
		if(event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof IHammer){
			event.setNewSpeed(event.getOriginalSpeed() * 1.25F);
		}
	}
	
	@Override
	public boolean usesMana(ItemStack stack) {
		return true;
	}
}
