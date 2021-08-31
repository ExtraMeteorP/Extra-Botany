package com.meteor.extrabotany.common.handler;

import com.meteor.extrabotany.common.capability.CapabilityHandler;
import com.meteor.extrabotany.common.capability.IFlamescion;
import com.meteor.extrabotany.common.entities.EntityFlamescionSlash;
import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.network.flamescion.FlamescionQPack;
import com.meteor.extrabotany.common.network.flamescion.FlamescionShiftPack;
import com.meteor.extrabotany.common.network.flamescion.FlamescionStateUpdatePack;
import com.meteor.extrabotany.common.network.NetworkHandler;
import com.meteor.extrabotany.common.potions.ModPotions;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

import java.util.concurrent.atomic.AtomicBoolean;

@Mod.EventBusSubscriber
public class FlamescionHandler {

    public static final int MAX_FLAMESCION_ENERGY = 600;

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        PlayerEntity p = Minecraft.getInstance().player;
        if(p == null)
            return;
        if(isFlamescionMode(p)){
            if(!p.getCooldownTracker().hasCooldown(getFlamescionWeapon()))
                if(event.getAction() == GLFW.GLFW_PRESS && event.getKey() == GLFW.GLFW_KEY_LEFT_SHIFT){
                    NetworkHandler.INSTANCE.sendToServer(new FlamescionShiftPack());
                }
            if(event.getAction() == GLFW.GLFW_PRESS && event.getKey() == GLFW.GLFW_KEY_R){
                NetworkHandler.INSTANCE.sendToServer(new FlamescionQPack());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerAttack(LivingAttackEvent event){
        if(event.getSource().getTrueSource() instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();
            if(isFlamescionMode(player)) {
                EntityFlamescionSlash slash = new EntityFlamescionSlash(player.world, player);
                slash.setPosition(event.getEntityLiving().getPosX(), event.getEntityLiving().getPosY()+1F, event.getEntityLiving().getPosZ());
                if(!player.world.isRemote)
                    player.world.addEntity(slash);
                player.addPotionEffect(new EffectInstance(ModPotions.incandescence, 30));
                event.getEntityLiving().addPotionEffect(new EffectInstance(ModPotions.timelock, 30));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        LazyOptional<IFlamescion> cap = event.player.getCapability(CapabilityHandler.FLAMESCION_CAPABILITY);
        PlayerEntity player = event.player;
        if(event.phase == TickEvent.Phase.END) {
            cap.ifPresent((c) -> {
                int energy = c.getEnergy();
                if (isFlamescionMode(player)) {
                    if (energy < MAX_FLAMESCION_ENERGY) {
                        c.setEnergy(Math.min(MAX_FLAMESCION_ENERGY, energy + 2));
                    } else {
                        c.setOverloaded(true);
                    }
                    c.markDirty(true);
                }

                if (c.isOverloaded()) {
                    if (energy > 0) {
                        c.setEnergy(Math.max(0, energy - 3));
                    } else
                        c.setOverloaded(false);
                    c.markDirty(true);
                }

            });
            if (!event.player.world.isRemote)
                sync(event.player);
        }
    }

    public static void sync(PlayerEntity player){
        LazyOptional<IFlamescion> cap = player.getCapability(CapabilityHandler.FLAMESCION_CAPABILITY);
        if(!player.world.isRemote)
            cap.ifPresent((c) -> {
                if(c.isDirty()){
                    NetworkHandler.INSTANCE.send(
                            PacketDistributor.PLAYER.with(
                                    () -> {
                                        return (ServerPlayerEntity) player;
                                    }
                            ),
                            new FlamescionStateUpdatePack(c.getEnergy(), c.isOverloaded()));
                    c.markDirty(false);
                }
            });
    }

    public static boolean isFlamescionMode(PlayerEntity player){
        return !player.isOnGround()
                && player.getHeldItemMainhand() != null
                && player.getHeldItemMainhand().getItem() == getFlamescionWeapon()
                && player.isPotionActive(ModPotions.incandescence)
                && !isOverloaded(player);
    }

    public static boolean isOverloaded(PlayerEntity player){
        LazyOptional<IFlamescion> cap = player.getCapability(CapabilityHandler.FLAMESCION_CAPABILITY);
        AtomicBoolean overloaded = new AtomicBoolean(false);
        cap.ifPresent((c) -> {
            overloaded.set(c.isOverloaded());
        });
        return overloaded.get();
    }

    public static Item getFlamescionWeapon(){
        return ModItems.flamescionweapon;
    }

    public static DamageSource flameSource() {
        return new DamageSource("hoi-flame").setDamageBypassesArmor().setDamageIsAbsolute().setMagicDamage();
    }

}
