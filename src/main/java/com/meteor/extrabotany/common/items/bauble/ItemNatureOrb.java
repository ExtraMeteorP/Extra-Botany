package com.meteor.extrabotany.common.items.bauble;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.server.SRemoveEntityEffectPacket;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.server.ServerWorld;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import java.util.List;
import java.util.stream.Collectors;

public class ItemNatureOrb extends ItemBauble{

    public static final String TAG_XP = "xp";
    public static final int MAX_XP = 500000;

    public ItemNatureOrb(Properties props) {
        super(props);
    }

    @Override
    public void onWornTick(ItemStack stack, LivingEntity entity) {
        super.onWornTick(stack, entity);
        if(entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;

            if(getXP(stack) > 100000)
                ManaItemHandler.instance().dispatchManaExact(stack, player, 1, true);
            if(getXP(stack) > 200000)
                ManaItemHandler.instance().dispatchManaExact(stack, player, 1, true);
            if(getXP(stack) > 300000){
                ManaItemHandler.instance().dispatchManaExact(stack, player, 1, true);
                if(player.ticksExisted % 60 == 0)
                    player.heal(1F);
            }
            if(getXP(stack) > 400000){
                if(player.ticksExisted % 40 == 0){
                    clearPotions(stack, player);
                }
            }
        }
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1.0 - (float) getXP(stack) / (float) getMaxXP(stack);
    }

    public void addXP(ItemStack stack, int xp){
        setXP(stack, Math.min(Math.max(getXP(stack) + xp, 0), getMaxXP(stack)));
    }

    public void setXP(ItemStack stack, int xp) {
        ItemNBTHelper.setInt(stack, TAG_XP, xp);
    }

    public int getXP(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, TAG_XP, 0);
    }


    public int getMaxXP(ItemStack stack) {
        return MAX_XP;
    }

    public void clearPotions(ItemStack stack, PlayerEntity player) {
        List<Effect> potionsToRemove = player.getActivePotionEffects().stream()
                .filter(effect -> effect.getPotion().getEffectType() == EffectType.HARMFUL)
                .filter(effect -> effect.getPotion().getCurativeItems().contains(new ItemStack(Items.MILK_BUCKET)))
                .map(EffectInstance::getPotion)
                .distinct()
                .collect(Collectors.toList());

        potionsToRemove.forEach(potion -> {
            player.removePotionEffect(potion);
            addXP(stack, -50);
            ((ServerWorld) player.world).getChunkProvider().sendToTrackingAndSelf(player,
                    new SRemoveEntityEffectPacket(player.getEntityId(), potion));
        });
    }

}
