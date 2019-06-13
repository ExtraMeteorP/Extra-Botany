package com.meteor.extrabotany.common.item.equipment.bauble;

import com.meteor.extrabotany.api.item.IAdvancementRequired;
import com.meteor.extrabotany.common.core.network.ExtraBotanyNetwork;
import com.meteor.extrabotany.common.core.network.PacketLeftClickJingwei;
import com.meteor.extrabotany.common.entity.EntityAuraFire;
import com.meteor.extrabotany.common.lib.LibAdvancements;
import com.meteor.extrabotany.common.lib.LibItemsName;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.api.mana.ManaItemHandler;

public class ItemJingweiFeather extends ItemBauble implements IAdvancementRequired{

	public ItemJingweiFeather() {
		super(LibItemsName.BAUBLE_FEATHER);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void leftClick(PlayerInteractEvent.LeftClickEmpty evt) {
		if (evt.getItemStack().isEmpty()) {
			ExtraBotanyNetwork.sendToServer(new PacketLeftClickJingwei());
		}
	}
	
	@SubscribeEvent
	public void attackEntity(AttackEntityEvent evt) {
		if (!evt.getEntityPlayer().world.isRemote) {
			trySpawnFireball(evt.getEntityPlayer());
		}
	}
	
	public void trySpawnFireball(EntityPlayer player) {
		if (player.getHeldItemMainhand().isEmpty()
				&& BaublesApi.isBaubleEquipped(player, this) != -1 && player.getCooledAttackStrength(0) == 1) {
			EntityAuraFire aura = new EntityAuraFire(player.world, player);
			aura.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 0.8F, 1.0F);
			if(!player.world.isRemote)
				player.world.spawnEntity(aura);
		}
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.TRINKET;
	}

	@Override
	public String getAdvancementName(ItemStack stack) {
		return LibAdvancements.JINGWEIFEATHER;
	}

}
