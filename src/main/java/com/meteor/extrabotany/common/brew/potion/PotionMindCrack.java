package com.meteor.extrabotany.common.brew.potion;

import com.meteor.extrabotany.common.brew.ModPotions;
import com.meteor.extrabotany.common.lib.LibPotionsName;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.IMob;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionMindCrack extends PotionMod {

    public PotionMindCrack() {
        super(LibPotionsName.MINDCRACK, true, 0X8B008B, 0);
        MinecraftForge.EVENT_BUS.register(this);
        setBeneficial();
    }

    @SubscribeEvent
    public void onMobUpdate(LivingUpdateEvent event) {
        if (!(event.getEntityLiving() instanceof IMob))
            return;
        if (event.getEntityLiving() instanceof EntityLiving) {
            EntityLiving mob = (EntityLiving) event.getEntityLiving();

            if (mob.isNonBoss() && mob.isPotionActive(ModPotions.mindcrack)) {
                if (mob.getActivePotionEffect(ModPotions.mindcrack).getDuration() > 5)
                    mob.setNoAI(true);
                else
                    mob.setNoAI(false);
            }
        }
    }

}
