package com.meteor.extrabotany.common.item.equipment.armor.combatmaid;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketRemoveEntityEffect;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.api.mana.IManaDiscountArmor;
import vazkii.botania.api.mana.IManaGivingItem;
import vazkii.botania.api.mana.ManaItemHandler;

public class ItemCombatMaidHelm extends ItemCombatMaidArmor implements IManaDiscountArmor, IManaGivingItem{

	public ItemCombatMaidHelm() {
		super(EntityEquipmentSlot.HEAD, "combatmaidhelm");
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		super.onArmorTick(world, player, stack);
		if(hasArmorSet(player)) {
			if(player.shouldHeal() && player.ticksExisted % 40 == 0)
				player.heal(1F);
			ManaItemHandler.dispatchManaExact(stack, player, 1, true);
			if(player.ticksExisted % 40 == 0)
				clearPotions(player);
		}
	}
	
	private void clearPotions(EntityPlayer player) {
		int posXInt = MathHelper.floor(player.getPosition().getX());
		int posZInt = MathHelper.floor(player.getPosition().getZ());

		List<Potion> potionsToRemove = player.getActivePotionEffects().stream()
				.filter(effect -> effect.getPotion().isBadEffect())
				.map(PotionEffect::getPotion)
				.distinct()
				.collect(Collectors.toList());

		potionsToRemove.forEach(potion -> {
			player.removePotionEffect(potion);
			if(!player.getEntityWorld().isRemote)
				((WorldServer) player.getEntityWorld()).getPlayerChunkMap().getEntry(posXInt >> 4, posZInt >> 4).sendPacket(new SPacketRemoveEntityEffect(player.getEntityId(), potion));
		});
	}

	@Override
	public float getDiscount(ItemStack stack, int slot, EntityPlayer player, @Nullable ItemStack tool) {
		return hasArmorSet(player) ? 0.3F : 0F;
	}
	
	@SubscribeEvent
	public void onEntityAttacked(LivingHurtEvent event) {
		Entity attacker = event.getSource().getImmediateSource();
		if(attacker instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) attacker;
			if(hasArmorSet(player)) {
				if(player.getHeldItemMainhand() == ItemStack.EMPTY)
					event.setAmount(event.getAmount() + 10F);
				if(player.shouldHeal())
					player.heal(event.getAmount()/5F);
			}	
		}
	}
}
