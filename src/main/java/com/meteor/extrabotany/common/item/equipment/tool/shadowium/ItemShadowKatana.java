package com.meteor.extrabotany.common.item.equipment.tool.shadowium;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import com.google.common.collect.Multimap;
import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.client.render.IModelReg;
import com.meteor.extrabotany.common.core.handler.DarkPixieHandler;
import com.meteor.extrabotany.common.entity.EntityDarkPixie;
import com.meteor.extrabotany.common.lib.LibItemsName;
import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class ItemShadowKatana extends ItemSword implements IModelReg, IManaUsingItem{
	
	private static final int MANA_PER_DAMAGE = 60;

	public ItemShadowKatana() {
		super(BotaniaAPI.elementiumToolMaterial);
		setCreativeTab(ExtraBotany.tabExtraBotany);
		setRegistryName(new ResourceLocation(LibMisc.MOD_ID, LibItemsName.SHADOWKATANA));
		setUnlocalizedName(LibItemsName.SHADOWKATANA);
	}
	
	@Override
    public void onUpdate(ItemStack stack, World world, Entity player, int itemSlot, boolean isSelected){
    	super.onUpdate(stack, world, player, itemSlot, isSelected);
    	if(!world.isRemote && player instanceof EntityPlayer && stack.getItemDamage() > 0 && ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer) player, MANA_PER_DAMAGE * 2, true))
			stack.setItemDamage(stack.getItemDamage() - 1);
    	
    	if(world.isDaytime())
    		ItemNBTHelper.setBoolean(stack, "isnight", false);
    	else
    		ItemNBTHelper.setBoolean(stack, "isnight", true);
    	
    	int RANGE = 8;
    	
    	if(player instanceof EntityPlayer){
    		if(!(((EntityPlayer) player).getHeldItemMainhand().getItem() instanceof ItemShadowKatana))
    			return;
    		
    		List<EntityLivingBase> livings = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(player.getPosition().add(-RANGE, -RANGE, -RANGE), player.getPosition().add(RANGE + 1, RANGE + 1, RANGE + 1)));
	    	if(livings.size() > 1)
	    		for(EntityLivingBase living : livings){
	    			if(living == player)
	    				continue;
		    		if(((EntityPlayer) player).swingProgressInt == 4 && living.isEntityAlive() && ManaItemHandler.requestManaExactForTool(stack, ((EntityPlayer) player), 100, true)){
		    			EntityDarkPixie pixie = DarkPixieHandler.getPixie(((EntityPlayer) player), living, stack);
		    			if(!world.isRemote)
		    				world.spawnEntity(pixie);
		    			break;
		    		}
		    	}
    	}
    }
	
	@Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker){
		super.hitEntity(stack, target, attacker);
		if(!attacker.getEntityWorld().isDaytime())
			attacker.heal(2F);
        return true;
    }
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> attrib = super.getAttributeModifiers(slot, stack);
		UUID uuid = new UUID((getUnlocalizedName() + slot.toString()).hashCode(), 0);
		boolean night = ItemNBTHelper.getBoolean(stack, "isnight", false);
		if (slot == EntityEquipmentSlot.MAINHAND) {
			attrib.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(uuid, "ShadowKatana modifier", night ? 10 : 0, 0));
			attrib.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(uuid, "ShadowKatana modifier", night ? 0.15F : 0, 1));
		}
		return attrib;
	}
	
	@Nonnull
	@Override
	public String getUnlocalizedNameInefficiently(@Nonnull ItemStack par1ItemStack) {
		return super.getUnlocalizedNameInefficiently(par1ItemStack).replaceAll("item\\.", "item." + LibMisc.MOD_ID + ":");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}

	@Override
	public boolean usesMana(ItemStack arg0) {
		return true;
	}

}
