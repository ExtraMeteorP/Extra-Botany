package com.meteor.extrabotany.common.handler;

import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.multiplayer.ClientAdvancementManager;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import top.theillusivec4.curios.api.CuriosApi;
import vazkii.botania.common.core.handler.EquipmentHandler;
import vazkii.botania.common.core.helper.PlayerHelper;

import java.util.Map;

@Mod.EventBusSubscriber
public final class AdvancementHandler {

    public static final AdvancementHandler INSTANCE = new AdvancementHandler();

    public void grantAdvancement(ServerPlayerEntity player, String id){
        PlayerHelper.grantCriterion(player, new ResourceLocation(LibMisc.MOD_ID, "main/" + id), "code_triggered");
    }

    public static boolean checkAdvancement(PlayerEntity player, String modid, String advancement){
        ResourceLocation id = ResourceLocation.tryCreate(modid + ":main/" + advancement);
        if(id != null) {
            if (player instanceof ServerPlayerEntity) {
                AdvancementManager manager = player.getServer().getAdvancementManager();
                PlayerAdvancements advancements = ((ServerPlayerEntity) player).getAdvancements();
                Advancement adv = manager.getAdvancement(id);
                if(adv != null)
                    return advancements.getProgress(adv).isDone();
            }
        }
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public static Advancement getSideAdvancement(String modid, String advancement){
        ResourceLocation id = ResourceLocation.tryCreate(modid + ":main/" + advancement);
        if(id != null){
            ClientPlayNetHandler netHandler = Minecraft.getInstance().player.connection;
            ClientAdvancementManager manager = netHandler.getAdvancementManager();
            Advancement adv = manager.getAdvancementList().getAdvancement(id);
            return adv;
        }
        return null;
    }

    public static boolean hasDone(String modid, String advancement) {
        ResourceLocation id = ResourceLocation.tryCreate(modid + ":main/" + advancement);
        if (id != null) {
            ClientPlayNetHandler conn = Minecraft.getInstance().getConnection();
            if (conn != null) {
                ClientAdvancementManager cm = conn.getAdvancementManager();
                Advancement adv = cm.getAdvancementList().getAdvancement(id);
                if (adv != null) {
                    Map<Advancement, AdvancementProgress> progressMap = ObfuscationReflectionHelper.getPrivateValue(ClientAdvancementManager.class, cm, "field_192803_d");
                    AdvancementProgress progress = progressMap.get(adv);
                    return progress != null && progress.isDone();
                }
            }
        }
        return false;
    }

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent event) {
        if (event.isCancelable() && !event.getPlayer().isCreative()) {
            if (event.getItemStack().getItem() instanceof IAdvancementRequirement) {
                IAdvancementRequirement r = (IAdvancementRequirement) event.getItemStack().getItem();
                if (!checkAdvancement(event.getPlayer(), LibMisc.MOD_ID, r.getAdvancementName()))
                    event.setCanceled(true);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() instanceof IAdvancementRequirement) {
            IAdvancementRequirement r = (IAdvancementRequirement) event.getItemStack().getItem();
            ClientPlayerEntity playerSP = Minecraft.getInstance().player;
            if (playerSP != null) {
                if (!hasDone(LibMisc.MOD_ID, r.getAdvancementName()))
                    event.getToolTip().add(new TranslationTextComponent("extrabotanymisc.description",
                            new TranslationTextComponent("extrabotany." + r.getAdvancementName() + ".title")).mergeStyle(TextFormatting.RED));
            }
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {

        if (event.getEntity() instanceof PlayerEntity && !event.getEntityLiving().world.isRemote) {
            final PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (player.isCreative()) {
                return;
            }

            CuriosApi.getCuriosHelper().getEquippedCurios(player).ifPresent((c) ->{
                for(int i = 0; i < c.getSlots(); i++){
                    final ItemStack stack = c.getStackInSlot(i);
                    if (stack.getItem() instanceof IAdvancementRequirement) {
                        IAdvancementRequirement r = (IAdvancementRequirement) stack.getItem();
                        if (!checkAdvancement(player, LibMisc.MOD_ID, r.getAdvancementName())) {
                            c.setStackInSlot(i, ItemStack.EMPTY);
                            player.dropItem(stack, false);
                        }
                    }
                }
            });

            for (final EquipmentSlotType slot : EquipmentSlotType.values()) {
                final ItemStack stack = player.getItemStackFromSlot(slot);
                if (stack.getItem() instanceof IAdvancementRequirement) {
                    IAdvancementRequirement r = (IAdvancementRequirement) stack.getItem();
                    if (!checkAdvancement(player, LibMisc.MOD_ID, r.getAdvancementName())) {
                        player.setItemStackToSlot(slot, ItemStack.EMPTY);
                        player.dropItem(stack, false);
                    }
                }
            }
        }

    }

}
