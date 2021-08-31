package com.meteor.extrabotany.common.handler;

import com.meteor.extrabotany.common.capability.CapabilityHandler;
import com.meteor.extrabotany.common.capability.IHerrscherEnergy;
import com.meteor.extrabotany.common.core.EquipmentHandler;
import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.network.HerrscherEnergyUpdatePack;
import com.meteor.extrabotany.common.network.HerrscherSkillPack;
import com.meteor.extrabotany.common.network.NetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber
public class HerrscherHandler {

    @SubscribeEvent
    public static void onHerrscherAttacked(LivingAttackEvent event){
        if(event.getSource().getImmediateSource() != null)
            if(event.getEntityLiving() instanceof PlayerEntity){
                PlayerEntity player = (PlayerEntity) event.getEntityLiving();
                if(isHerrscherOfThunder(player)) {
                    LazyOptional<IHerrscherEnergy> cap = player.getCapability(CapabilityHandler.HERRSCHERENERGY_CAPABILITY);
                    cap.ifPresent((c) -> {
                        if(c.getEnergy() >= 200 || player.isCreative()) {
                            if (!player.isSwingInProgress && player.getHeldItemMainhand().getItem() instanceof SwordItem) {
                                player.swing(Hand.MAIN_HAND, true);
                                player.attackTargetEntityWithCurrentItem(event.getSource().getImmediateSource());
                                c.setEnergy(c.getEnergy() - 200);
                                c.markDirty(true);
                                sync(player);
                                event.setCanceled(true);
                            }
                        }
                    });
                }
            }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onPlayerLeftClick(PlayerInteractEvent.LeftClickEmpty event){
        PlayerEntity player = event.getPlayer();
        Minecraft mc = Minecraft.getInstance();
        RayTraceResult pos = mc.objectMouseOver;
        if(isHerrscherOfThunder(player)) {
            if (!event.getItemStack().isEmpty() && event.getItemStack().getItem() instanceof SwordItem) {
                LazyOptional<IHerrscherEnergy> cap = player.getCapability(CapabilityHandler.HERRSCHERENERGY_CAPABILITY);
                cap.ifPresent((c) -> {
                    int energy = c.getEnergy();
                    if(pos != null) {
                        BlockPos p = new BlockPos(pos.getHitVec());
                        if ((energy == 600 || player.isCreative()) && mc.gameSettings.keyBindSprint.isKeyDown()) {
                            c.setEnergy(0);
                            c.markDirty(true);
                            NetworkHandler.INSTANCE.sendToServer(new HerrscherSkillPack(p.add(0, 2, 0)));
                        }
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        if(!isHerrscherOfThunder(event.player))
            return;
        LazyOptional<IHerrscherEnergy> cap = event.player.getCapability(CapabilityHandler.HERRSCHERENERGY_CAPABILITY);
        cap.ifPresent((c) -> {
            int energy = c.getEnergy();
            if(energy < 600) {
                c.setEnergy(Math.min(600, energy + 2));
                c.markDirty(true);
            }
        });
        if(!event.player.world.isRemote)
            sync(event.player);
    }

    public static void sync(PlayerEntity player){
        LazyOptional<IHerrscherEnergy> cap = player.getCapability(CapabilityHandler.HERRSCHERENERGY_CAPABILITY);
        if(!player.world.isRemote)
            cap.ifPresent((c) -> {
                if(c.isDirty()){
                    NetworkHandler.INSTANCE.send(
                            PacketDistributor.PLAYER.with(
                                    () -> {
                                        return (ServerPlayerEntity) player;
                                    }
                            ),
                            new HerrscherEnergyUpdatePack(c.getEnergy()));
                    c.markDirty(false);
                }
            });
    }

    public static DamageSource damageSource() {
        return new DamageSource("hoi-thunder").setDamageBypassesArmor().setDamageIsAbsolute().setMagicDamage();
    }

    public static DamageSource iceSource() {
        return new DamageSource("hoi-ice").setDamageBypassesArmor().setDamageIsAbsolute().setMagicDamage();
    }

    public static void thunderAttack(Entity target, PlayerEntity player, float dmg){
        /**
         * 伤害结算
         */
        target.attackEntityFrom(damageSource(), dmg);
    }

    public static void iceAttack(Entity target, PlayerEntity player, float dmg){
        /**
         * 伤害结算
         */
        target.attackEntityFrom(iceSource(), dmg);
    }

    public static boolean isHerrscherOfThunder(PlayerEntity player){
        return !EquipmentHandler.findOrEmpty(ModItems.gemofconquest, player).isEmpty();
    }

}
