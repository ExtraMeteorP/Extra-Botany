package com.meteor.extrabotany.common.item.equipment.bauble;

import baubles.api.BaubleType;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.meteor.extrabotany.common.core.handler.PlayerStatHandler;
import com.meteor.extrabotany.common.lib.LibItemsName;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import java.util.List;

public class ItemGameWinner extends ItemBaubleRelic{
	
	public static final String TAG_TRUEDAMAGETAKEN = "truedamagetaken";
	public static final String TAG_GAIADEFEAT = "gaiadefeat";
	public static final String TAG_VOIDHERRSCHERDEFEAT = "voidherrscherdefeat";

	public ItemGameWinner() {
		super(LibItemsName.BAUBLE_GAMEWINNER);
	}
	
	@Override
	public void updateRelic(ItemStack stack, EntityPlayer player) {
		super.updateRelic(stack, player);
		if(this.getSoulbindUUID(stack) != null){
			if(player.world.getPlayerEntityByUUID(this.getSoulbindUUID(stack)) != null){
				EntityPlayer trueplayer = player.world.getPlayerEntityByUUID(this.getSoulbindUUID(stack));
				ItemNBTHelper.setInt(stack, TAG_GAIADEFEAT, PlayerStatHandler.getGaiaDefeat(trueplayer));
				ItemNBTHelper.setInt(stack, TAG_VOIDHERRSCHERDEFEAT, PlayerStatHandler.getVoidHerrscherDefeat(trueplayer));
				ItemNBTHelper.setFloat(stack, TAG_TRUEDAMAGETAKEN, PlayerStatHandler.getTrueDamageTaken(trueplayer));
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void addBindInfo(List<String> list, ItemStack stack) {
		super.addBindInfo(list, stack);
		addStringToTooltip(I18n.format("extrabotany.gaiadefeat") + ItemNBTHelper.getInt(stack, TAG_GAIADEFEAT, 0), list);
		addStringToTooltip(I18n.format("extrabotany.voidherrscherdefeat") + ItemNBTHelper.getInt(stack, TAG_VOIDHERRSCHERDEFEAT, 0), list);
		addStringToTooltip(I18n.format("extrabotany.truedamagetaken") + ItemNBTHelper.getFloat(stack, TAG_TRUEDAMAGETAKEN, 0), list);
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.CHARM;
	}
	
	@Override
	public boolean shouldDamageWrongPlayer() {
		return false;
	}
	
	@Override
	public void onEquippedOrLoadedIntoWorld(ItemStack stack, EntityLivingBase player) {
		if(!player.world.isRemote) {
			Multimap<String, AttributeModifier> attributes = HashMultimap.create();
			fillModifiers(attributes, stack);
			player.getAttributeMap().applyAttributeModifiers(attributes);
		}
	}

	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase player) {
		if(!player.world.isRemote) {
			Multimap<String, AttributeModifier> attributes = HashMultimap.create();
			fillModifiers(attributes, stack);
			player.getAttributeMap().removeAttributeModifiers(attributes);
		}
	}

	void fillModifiers(Multimap<String, AttributeModifier> attributes, ItemStack stack){
		if(stack.isEmpty())
			return;
		attributes.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(getBaubleUUID(stack), "GameWinner", 0.05F * Math.min(10, ItemNBTHelper.getInt(stack, TAG_GAIADEFEAT, 0)), 1).setSaved(false));
		attributes.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(getBaubleUUID(stack), "GameWinner", 0.03F * Math.min(10, ItemNBTHelper.getInt(stack, TAG_VOIDHERRSCHERDEFEAT, 0)), 1).setSaved(false));
		attributes.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(getBaubleUUID(stack), "GameWinner", 0.10F * Math.min(10, ItemNBTHelper.getInt(stack, TAG_VOIDHERRSCHERDEFEAT, 0)), 1).setSaved(false));
		attributes.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(getBaubleUUID(stack), "GameWinner", 0.03F * Math.min(20, ItemNBTHelper.getFloat(stack, TAG_TRUEDAMAGETAKEN, 0)/500), 1).setSaved(false));
	}

}
