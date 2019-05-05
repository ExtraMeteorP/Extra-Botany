package com.meteor.extrabotany.common.brew.potion;

import com.meteor.extrabotany.common.brew.ModPotions;
import com.meteor.extrabotany.common.lib.LibPotionsName;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionEternity extends PotionMod {

    public PotionEternity() {
        super(LibPotionsName.ETERNITY, false, 0XDAA520, 1);
        MinecraftForge.EVENT_BUS.register(this);
        setBeneficial();
    }

    @SubscribeEvent
    public void onUpdate(LivingUpdateEvent event) {
        if (event.getEntityLiving().isPotionActive(ModPotions.eternity)) {
            event.getEntityLiving().motionY = 0;
            event.getEntityLiving().motionX = 0;
            event.getEntityLiving().motionZ = 0;
        }
    }

    @SubscribeEvent
    public void onDamageTaken(LivingHurtEvent event) {
        if (!(event.getEntityLiving() instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
        if (player.isPotionActive(ModPotions.eternity))
            event.setAmount(0F);
    }

}
