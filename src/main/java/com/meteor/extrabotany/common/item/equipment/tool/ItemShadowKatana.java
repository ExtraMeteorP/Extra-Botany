package com.meteor.extrabotany.common.item.equipment.tool;

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
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class ItemShadowKatana extends ItemSword implements IModelReg{

	public ItemShadowKatana() {
		super(BotaniaAPI.elementiumToolMaterial);
		setCreativeTab(ExtraBotany.tabExtraBotany);
		setRegistryName(new ResourceLocation(LibMisc.MOD_ID, LibItemsName.SHADOWKATANA));
		setUnlocalizedName(LibItemsName.SHADOWKATANA);
	}
	
	@Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected){
    	super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
    	
    	if(worldIn.isDaytime())
    		ItemNBTHelper.setBoolean(stack, "isnight", false);
    	else
    		ItemNBTHelper.setBoolean(stack, "isnight", true);
    	
    	int RANGE = 8;
    	
    	if(entityIn instanceof EntityPlayer){
    		EntityPlayer player = (EntityPlayer) entityIn;
    		if(!(player.getHeldItemMainhand().getItem() instanceof ItemShadowKatana))
    			return;
    		
    		List<EntityLivingBase> livings = worldIn.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(entityIn.getPosition().add(-RANGE, -RANGE, -RANGE), entityIn.getPosition().add(RANGE + 1, RANGE + 1, RANGE + 1)));
	    	if(livings.size() > 1)
	    		for(EntityLivingBase living : livings){
	    			if(living == player)
	    				continue;
		    		if(player.swingProgressInt == 4 && living.isEntityAlive() && ManaItemHandler.requestManaExactForTool(stack, player, 100, true)){
		    			EntityDarkPixie pixie = DarkPixieHandler.getPixie(player, living, stack);
		    			if(!worldIn.isRemote)
		    				worldIn.spawnEntity(pixie);
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

}
