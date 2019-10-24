package com.meteor.extrabotany.common.item.equipment.armor.goblinslayer;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.lib.LibAdvancements;
import com.meteor.extrabotany.common.lib.LibItemsName;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemGoblinSlayerHelm extends ItemGoblinSlayerArmor{

	public ItemGoblinSlayerHelm() {
		super(EntityEquipmentSlot.HEAD, LibItemsName.GSHELM);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		super.onArmorTick(world, player, stack);
		if(hasArmorSet(player)) {
			ExtraBotanyAPI.unlockAdvancement(player, LibAdvancements.ARMORSET_GS);
			if(world.isDaytime()){
				if(player.shouldHeal() && player.ticksExisted % 25 == 0)
					player.heal(1F);
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerAttacked(LivingHurtEvent event) {
		Entity target = event.getEntityLiving();
		if(target instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) target;
			if(hasArmorSet(player) && player.getEntityWorld().isDaytime()) {
				event.setAmount(event.getAmount() * 0.65F);
			}	
		}
	}
	
	@SubscribeEvent
	public void onEntityAttacked(LivingHurtEvent event) {
		Entity attacker = event.getSource().getImmediateSource();
		if(attacker instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) attacker;
			if(hasArmorSet(player) && player.getEntityWorld().isDaytime()) {
				if(event.getEntityLiving().getCreatureAttribute() != EnumCreatureAttribute.UNDEAD){
					event.setAmount(event.getAmount() * 1.35F);
				}
			}	
		}
	}

}
