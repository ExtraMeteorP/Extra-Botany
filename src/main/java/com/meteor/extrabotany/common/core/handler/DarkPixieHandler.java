package com.meteor.extrabotany.common.core.handler;

import baubles.api.BaublesApi;
import com.meteor.extrabotany.api.item.IDarkElfSpawner;
import com.meteor.extrabotany.common.entity.EntityDarkPixie;
import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.item.equipment.armor.shadowwarrior.ItemShadowWarriorHelm;
import com.meteor.extrabotany.common.lib.LibMisc;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.IItemHandler;
import vazkii.botania.common.core.helper.PlayerHelper;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)
public final class DarkPixieHandler {

    private static final Potion[] potions = {
            MobEffects.BLINDNESS,
            MobEffects.WITHER,
            MobEffects.SLOWNESS,
            MobEffects.WEAKNESS,
            MobEffects.HUNGER
    };

    private DarkPixieHandler() {
    }

    @SubscribeEvent
    public static void onDamageTaken(LivingHurtEvent event) {
        if (!event.getEntityLiving().world.isRemote && event.getEntityLiving() instanceof EntityPlayer && event.getSource().getTrueSource() instanceof EntityLivingBase) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            ItemStack stack = PlayerHelper.getFirstHeldItemClass(player, IDarkElfSpawner.class);

            float chance = getChance(stack);
            for (ItemStack element : player.inventory.armorInventory)
                chance += getChance(element);

            IItemHandler baubles = BaublesApi.getBaublesHandler(player);
            for (int i = 0; i < baubles.getSlots(); i++)
                chance += getChance(baubles.getStackInSlot(i));

            if (Math.random() < chance) {
                EntityDarkPixie pixie = getPixie(player, (EntityLivingBase) event.getSource().getTrueSource(), stack);
                player.world.spawnEntity(pixie);
            }
        }
    }

    public static EntityDarkPixie getPixie(EntityPlayer player, EntityLivingBase target, ItemStack stack) {
        EntityDarkPixie pixie = new EntityDarkPixie(player.world);
        pixie.setPosition(player.posX, player.posY + 3, player.posZ);

        if (((ItemShadowWarriorHelm) ModItems.swhelm).hasArmorSet(player)) {
            pixie.setApplyPotionEffect(new PotionEffect(potions[player.getEntityWorld().rand.nextInt(potions.length)], 40, 0));
        }

        float dmg = 2;
        if (!stack.isEmpty() && stack.getItem() == ModItems.shadowkatana)
            dmg += 2;

        if (!pixie.getEntityWorld().isDaytime())
            dmg *= 2;
        pixie.setProps(target, player, 0, dmg);
        pixie.onInitialSpawn(player.world.getDifficultyForLocation(new BlockPos(pixie)), null);
        return pixie;
    }

    private static float getChance(ItemStack stack) {
        if (stack.isEmpty() || !(stack.getItem() instanceof IDarkElfSpawner))
            return 0F;
        else return ((IDarkElfSpawner) stack.getItem()).getSpawnChance(stack);
    }
}
