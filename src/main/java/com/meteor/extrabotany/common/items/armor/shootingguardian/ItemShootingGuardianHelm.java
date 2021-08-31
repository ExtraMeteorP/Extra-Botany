package com.meteor.extrabotany.common.items.armor.shootingguardian;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ItemShootingGuardianHelm extends ItemShootingGuardianArmor{

    public ItemShootingGuardianHelm(Properties props) {
        super(EquipmentSlotType.HEAD, props);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerAttack);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerUseBow);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerHeal);
    }

    @SubscribeEvent
    public void onPlayerAttack(LivingHurtEvent event){
        if(event.getSource().getTrueSource() instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();
            if (hasArmorSet(player)) {
                event.getSource().setDamageBypassesArmor();
                player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + event.getAmount() * 0.2F));
            }
        }
    }

    @SubscribeEvent
    public void onPlayerUseBow(LivingEntityUseItemEvent event){
        if(event.getEntityLiving() instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if(event.getItem().getItem() instanceof BowItem && hasArmorSet(player)){
                event.setDuration(event.getDuration() - 1);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerHeal(LivingHealEvent event){
        if(event.getEntityLiving() instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if(hasArmorSet(player))
                event.setAmount(event.getAmount() * 0.2F);
        }
    }

}
