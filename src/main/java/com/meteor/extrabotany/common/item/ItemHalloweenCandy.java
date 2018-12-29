package com.meteor.extrabotany.common.item;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.client.ClientProxy;
import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.lib.LibItemsName;
import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemHalloweenCandy extends ItemFoodMod{

	final int types = 3;
	
	public ItemHalloweenCandy() {
		super(2, 0.4F, false, LibItemsName.CANDY);
		setHasSubtypes(true);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn){
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }
	
	@Override
	public void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player){
		super.onFoodEaten(stack, worldIn, player);
		if(player.shouldHeal())
			player.heal(4F);
		switch(stack.getMetadata()){
			case 0:
				player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 200, 1));
				break;
			case 1:
				player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 200, 1));
				break;
			case 2:
				player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 200, 1));
				break;		
		}
	}
	
	@Nonnull
	@Override
	@SideOnly(Side.CLIENT)
	public String getUnlocalizedName(ItemStack stack) {
		String name = LibItemsName.CANDY;
		if(ClientProxy.christmas)
			name = LibItemsName.CANDY + "chris";
		return "item." + name + stack.getMetadata();
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
		for (int i = 0; i < types; i++) {
				ModelLoader.setCustomModelResourceLocation(
					this, i,
					new ModelResourceLocation(LibMisc.MOD_ID + ":" + LibItemsName.CANDY + i, "inventory")
				);
		}
	}
	
	@Override
    public int getMaxItemUseDuration(ItemStack stack){
        return 14;
    }
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onSpawn(LivingSpawnEvent.SpecialSpawn event) {
		if(!ConfigHandler.ENABLE_CANDYBAGDROP)
			return;
		if(event.getEntityLiving() instanceof EntityZombie || event.getEntityLiving() instanceof EntitySkeleton) {
			if(Math.random() < ConfigHandler.CANDYBAG_DROPCHANCE)
				if(event.getEntityLiving().getHeldItemOffhand() == ItemStack.EMPTY){
					event.getEntityLiving().setHeldItem(EnumHand.OFF_HAND, new ItemStack(ModItems.candybag));
					((EntityMob)event.getEntityLiving()).setDropChance(EntityEquipmentSlot.OFFHAND, 1F);
				}
		}
	}

}
