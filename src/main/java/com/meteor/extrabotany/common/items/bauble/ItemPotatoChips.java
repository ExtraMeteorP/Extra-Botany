package com.meteor.extrabotany.common.items.bauble;

import com.meteor.extrabotany.common.core.EquipmentHandler;
import com.meteor.extrabotany.common.network.NetworkHandler;
import com.meteor.extrabotany.common.network.PotatoChipsPack;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.BossInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vazkii.botania.api.mana.ManaItemHandler;

public class ItemPotatoChips extends ItemBauble{

    public static final int MANA_PER_DAMAGE = 3000;

    public ItemPotatoChips(Properties props) {
        super(props);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerDeath);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onPlayerDeath(LivingDeathEvent event){
        if(!event.isCanceled() && event.getEntityLiving() instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if(!EquipmentHandler.findOrEmpty(this, player).isEmpty()
                    && !player.getCooldownTracker().hasCooldown(this)
                    && ManaItemHandler.instance().requestManaExactForTool(new ItemStack(this), player, MANA_PER_DAMAGE, true)){

                if(!player.world.isRemote){
                    player.world.playSound(player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ITEM_TOTEM_USE, player.getSoundCategory(), 1.0F, 1.0F, false);
                }

                event.setCanceled(true);
                player.setHealth(5.0F);
                player.clearActivePotions();
                player.addPotionEffect(new EffectInstance(Effects.REGENERATION, 900, 1));
                player.addPotionEffect(new EffectInstance(Effects.ABSORPTION, 100, 1));
                player.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 800, 0));
                int ticks = 600;
                if(event.getSource().getTrueSource() != null && !event.getSource().getTrueSource().isNonBoss())
                    ticks = 12000;
                player.getCooldownTracker().setCooldown(this, ticks);
                if(player instanceof ServerPlayerEntity)
                    NetworkHandler.sendTo((ServerPlayerEntity) player, new PotatoChipsPack());
            }
        }
    }

    @Override
    public boolean canEquip(ItemStack stack, LivingEntity entity) {
        return EquipmentHandler.findOrEmpty(this, entity).isEmpty();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void doRender(BipedModel<?> bipedModel, ItemStack stack, LivingEntity player, MatrixStack ms, IRenderTypeBuffer buffers, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ms.push();
        bipedModel.bipedHead.translateRotate(ms);
        ms.translate(0, -0.3, -0.3);
        ms.scale(0.6F, -0.6F, -0.6F);
        renderItem(stack, ms, buffers, light);
        ms.pop();
    }

    public static void renderItem(ItemStack stack, MatrixStack ms, IRenderTypeBuffer buffers, int light) {
        Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.NONE, light, OverlayTexture.NO_OVERLAY, ms, buffers);
    }

}
