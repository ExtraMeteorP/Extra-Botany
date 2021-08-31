package com.meteor.extrabotany.common.handler;

import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.api.items.IMountableAccessory;
import com.meteor.extrabotany.common.entities.mountable.EntityMountable;
import com.meteor.extrabotany.common.entities.mountable.EntityUfo;
import com.meteor.extrabotany.common.items.relic.ItemBuddhistrelics;
import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.network.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.TickEvent;
import org.lwjgl.glfw.GLFW;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vazkii.botania.common.core.handler.EquipmentHandler;

import java.util.List;

@Mod.EventBusSubscriber
public class KeyInputHandler {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void tick(TickEvent.ClientTickEvent e) {
        Minecraft mc = Minecraft.getInstance();
        if (e.phase == TickEvent.Phase.END && mc.player != null && mc.player.getCooledAttackStrength(0) == 1
                && mc.gameSettings.keyBindAttack.isKeyDown()) {
            if(!EquipmentHandler.findOrEmpty(ModItems.powerglove, mc.player).isEmpty()) {
                if (mc.objectMouseOver.getType() == RayTraceResult.Type.ENTITY) {
                    EntityRayTraceResult result = (EntityRayTraceResult) mc.objectMouseOver;
                    Entity entity = result.getEntity();
                    mc.playerController.attackEntity(mc.player, entity);
                }else if(mc.objectMouseOver.getType() == RayTraceResult.Type.BLOCK){
                    BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)mc.objectMouseOver;
                    BlockPos blockpos = blockraytraceresult.getPos();
                    if (!mc.player.world.isAirBlock(blockpos)) {
                        mc.playerController.clickBlock(blockpos, blockraytraceresult.getFace());
                    }
                }else if(mc.objectMouseOver.getType() == RayTraceResult.Type.MISS){
                    mc.player.resetCooldown();
                    net.minecraftforge.common.ForgeHooks.onEmptyLeftClick(mc.player);
                    mc.player.swingArm(Hand.MAIN_HAND);
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        PlayerEntity p = Minecraft.getInstance().player;
        if(p == null)
            return;
        if(event.getAction() == GLFW.GLFW_PRESS && event.getKey() == GLFW.GLFW_KEY_LEFT_CONTROL) {
            if (!ItemBuddhistrelics.relicShift(p.getHeldItemMainhand()).isEmpty()) {
                NetworkHandler.INSTANCE.sendToServer(new BuddhistChangePack());
            }
        }
        Entity riding = p.getRidingEntity();
        if(riding == null){
            if(event.getAction() == GLFW.GLFW_PRESS && event.getKey() == GLFW.GLFW_KEY_R) {
                ItemStack mountable = EquipmentHandler.findOrEmpty((stack) -> stack.getItem() instanceof IMountableAccessory, p);
                if (!mountable.isEmpty()) {
                    NetworkHandler.INSTANCE.sendToServer(new MountPack(mountable));
                    return;
                }
            }
        }
        if (riding instanceof EntityMountable) {
            EntityMountable steerable = (EntityMountable) riding;
            steerable.updateInput(ExtraBotany.keyFlight.isKeyDown(), ExtraBotany.keyUp.isKeyDown());
            NetworkHandler.INSTANCE.sendToServer(new MountableUpdatePack(ExtraBotany.keyFlight.isKeyDown(), ExtraBotany.keyUp.isKeyDown()));
        }
        if (riding instanceof EntityUfo) {
            EntityUfo steerable = (EntityUfo) riding;

            if(event.getAction() == GLFW.GLFW_PRESS && event.getKey() == GLFW.GLFW_KEY_R){

                if(steerable.getCatchedID() != -1){
                    steerable.setCatchedID(-1);
                    NetworkHandler.INSTANCE.sendToServer(new UfoCatchPack(-1));
                    return;
                }else{
                    List<LivingEntity> entities = steerable.getEntitiesBelow();
                    if(entities.size() > 0) {
                        int id = -1;
                        float distance = 16F;
                        for(Entity e : entities){
                            if(e == p)
                                continue;
                            if(e.getDistance(steerable) < distance){
                                distance = e.getDistance(steerable);
                                id = e.getEntityId();
                            }
                        }
                        steerable.setCatchedID(id);
                        NetworkHandler.INSTANCE.sendToServer(new UfoCatchPack(id));
                    }
                }
            }
        }
    }

}
