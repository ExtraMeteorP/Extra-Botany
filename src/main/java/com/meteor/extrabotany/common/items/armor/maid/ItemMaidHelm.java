package com.meteor.extrabotany.common.items.armor.maid;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.server.SRemoveEntityEffectPacket;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vazkii.botania.api.mana.ManaItemHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemMaidHelm extends ItemMaidArmor{

    public List<DamageSource> source = new ArrayList();

    public ItemMaidHelm(Properties props) {
        super(EquipmentSlotType.HEAD, props);
        MinecraftForge.EVENT_BUS.addListener(this::onEntityAttacked);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerAttacked);
        source.add(DamageSource.ANVIL);
        source.add(DamageSource.CACTUS);
        source.add(DamageSource.DROWN);
        source.add(DamageSource.FALL);
        source.add(DamageSource.FALLING_BLOCK);
        source.add(DamageSource.IN_FIRE);
        source.add(DamageSource.LAVA);
        source.add(DamageSource.ON_FIRE);
        source.add(DamageSource.LIGHTNING_BOLT);
        source.add(DamageSource.FLY_INTO_WALL);
        source.add(DamageSource.HOT_FLOOR);
        source.add(DamageSource.SWEET_BERRY_BUSH);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        super.onArmorTick(stack, world, player);
        if(hasArmorSet(player)) {
            ManaItemHandler.instance().dispatchManaExact(stack, player, 1, true);
            if (player.shouldHeal() && player.ticksExisted % 40 == 0
                    && ManaItemHandler.instance().requestManaExactForTool(stack, player, 20, true))
                player.heal(1F);
            if (player.ticksExisted % 40 == 0)
                clearPotions(stack, player);
        }
    }

    @SubscribeEvent
    public void onEntityAttacked(LivingHurtEvent event) {
        Entity attacker = event.getSource().getImmediateSource();
        LivingEntity target = event.getEntityLiving();
        if (attacker instanceof PlayerEntity && target != null && target != attacker) {
            PlayerEntity player = (PlayerEntity) attacker;
            if (hasArmorSet(player)) {
                if (player.getHeldItemMainhand().isEmpty() && player.getHeldItemOffhand().isEmpty()
                        && ManaItemHandler.instance().requestManaExactForTool(new ItemStack(this), player, 200, true))
                    event.setAmount(event.getAmount() + 8F);
                if (player.shouldHeal()
                        && ManaItemHandler.instance().requestManaExactForTool(new ItemStack(this), player, 80, true))
                    player.heal(event.getAmount() / 10F);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onPlayerAttacked(LivingHurtEvent event) {
        Entity target = event.getEntityLiving();
        if (target instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) target;
            if (hasArmorSet(player)) {
                if (source.contains(event.getSource()))
                    event.setAmount(0F);
                if(source == DamageSource.MAGIC)
                    event.setAmount(event.getAmount() * 0.75F);
            }
        }
    }

    public void clearPotions(ItemStack stack, PlayerEntity player) {
        List<Effect> potionsToRemove = player.getActivePotionEffects().stream()
                .filter(effect -> effect.getPotion().getEffectType() == EffectType.HARMFUL)
                .filter(effect -> effect.getPotion().getCurativeItems().contains(new ItemStack(Items.MILK_BUCKET)))
                .map(EffectInstance::getPotion)
                .distinct()
                .collect(Collectors.toList());

        potionsToRemove.forEach(potion -> {
            if(ManaItemHandler.instance().requestManaExactForTool(stack, player, 100, true)) {
                player.removePotionEffect(potion);
                ((ServerWorld) player.world).getChunkProvider().sendToTrackingAndSelf(player,
                        new SRemoveEntityEffectPacket(player.getEntityId(), potion));
            }
        });
    }

}
