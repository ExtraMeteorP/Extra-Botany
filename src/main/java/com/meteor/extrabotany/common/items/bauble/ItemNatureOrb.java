package com.meteor.extrabotany.common.items.bauble;

import com.meteor.extrabotany.common.entities.ego.EntityEGO;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.network.play.server.SRemoveEntityEffectPacket;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

public class ItemNatureOrb extends ItemBauble{

    public static final String TAG_XP = "xp";
    public static final int MAX_XP = 500000;

    public ItemNatureOrb(Properties props) {
        super(props);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
        super.addInformation(stack, world, tooltip, flags);
        tooltip.add(new TranslationTextComponent("extrabotany.natureorb", getXP(stack), getMaxXP(stack)).mergeStyle(TextFormatting.GRAY));
        tooltip.add(new TranslationTextComponent("extrabotany.natureorbeffect1").mergeStyle(getXP(stack) >= 100000 ? TextFormatting.AQUA : TextFormatting.GRAY));
        tooltip.add(new TranslationTextComponent("extrabotany.natureorbeffect2").mergeStyle(getXP(stack) >= 300000 ? TextFormatting.DARK_RED : TextFormatting.GRAY));
        tooltip.add(new TranslationTextComponent("extrabotany.natureorbeffect3").mergeStyle(getXP(stack) >= 400000 ? TextFormatting.DARK_GREEN : TextFormatting.GRAY));
    }

    @Nonnull
    @Override
    public ActionResultType onItemUse(ItemUseContext ctx) {
        ItemStack stack = ctx.getItem();
        return EntityEGO.spawn(ctx.getPlayer(), stack, ctx.getWorld(), ctx.getPos()) ? ActionResultType.SUCCESS : ActionResultType.FAIL;
    }

    @Override
    public void fillItemGroup(@Nonnull ItemGroup tab, @Nonnull NonNullList<ItemStack> stacks) {
        if (isInGroup(tab)) {
            stacks.add(new ItemStack(this));

            ItemStack full = new ItemStack(this);
            setXP(full, getMaxXP(full));
            stacks.add(full);
        }
    }

    @Override
    public void onWornTick(ItemStack stack, LivingEntity entity) {
        super.onWornTick(stack, entity);
        if(entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if(!player.world.isRemote) {
                if (getXP(stack) > 100000 && player.ticksExisted % 5 == 0)
                    ManaItemHandler.instance().dispatchManaExact(stack, player, 5, true);
                if (getXP(stack) > 200000 && player.ticksExisted % 5 == 0)
                    ManaItemHandler.instance().dispatchManaExact(stack, player, 5, true);
                if (getXP(stack) > 300000 && player.ticksExisted % 5 == 0) {
                    ManaItemHandler.instance().dispatchManaExact(stack, player, 5, true);
                    if (player.ticksExisted % 60 == 0)
                        player.heal(1F);
                }
                if (getXP(stack) > 400000) {
                    if (player.ticksExisted % 40 == 0) {
                        clearPotions(stack, player);
                    }
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

    public boolean addXP(ItemStack stack, int xp){
        if(getXP(stack) >= getMaxXP(stack))
            return false;
        setXP(stack, Math.min(Math.max(getXP(stack) + xp, 0), getMaxXP(stack)));
        return true;
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
                .filter(effect -> effect.getPotion().getEffectType() == EffectType.HARMFUL
                        && effect.getCurativeItems().stream().anyMatch(e -> e.isItemEqual(new ItemStack(Items.MILK_BUCKET))))
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
