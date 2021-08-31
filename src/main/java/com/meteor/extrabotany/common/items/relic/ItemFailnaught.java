package com.meteor.extrabotany.common.items.relic;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.entities.projectile.EntityMagicArrow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.item.IRelic;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.advancements.RelicBindTrigger;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.equipment.tool.ToolCommons;
import vazkii.botania.common.item.relic.ItemRelic;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class ItemFailnaught extends BowItem implements IRelic, IManaUsingItem {

    private static final String TAG_SOULBIND_UUID = "soulbindUUID";
    private static final int MANA_PER_DAMAGE = 160;

    public ItemFailnaught(Properties builder) {
        super(builder);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, PlayerEntity playerIn, @Nonnull Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        return ActionResult.resultSuccess(itemstack);
    }

    @Override
    public void onPlayerStoppedUsing(@Nonnull ItemStack stack, @Nonnull World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityLiving;
            int i = (int) ((getUseDuration(stack) - timeLeft) * 1F);
            if (i < 8)
                return;
            int rank = (i - 8) / 5;
            if (isRightPlayer(player, stack)
                    && ManaItemHandler.instance().requestManaExactForTool(stack, player, Math.min(800, 350 + rank * 20), true)) {
                EntityMagicArrow arrow = new EntityMagicArrow(worldIn, player);
                arrow.setPosition(player.getPosX(), player.getPosY(), player.getPosZ());
                arrow.func_234612_a_(player, player.rotationPitch, player.rotationYaw, 0.0F, 3.0F, 1.0F);
                arrow.setDamage((int) Math.min(80, ExtraBotanyAPI.INSTANCE.calcDamage(8 + rank * 2, player)));
                arrow.rotationYaw = player.rotationYaw;
                int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                if (j > 0) {
                    arrow.setDamage(arrow.getDamage() + j * 2);
                }
                arrow.setLife(Math.min(150, 5 + i * 4));

                if (!worldIn.isRemote)
                    worldIn.addEntity(arrow);

                worldIn.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT,
                        SoundCategory.NEUTRAL, 1.0F, 0.5F);
            }
        }
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return ToolCommons.damageItemIfPossible(stack, amount, entity, MANA_PER_DAMAGE);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isRemote && entity instanceof PlayerEntity) {
            updateRelic(stack, (PlayerEntity) entity);
            if(stack.getDamage() > 0 && ManaItemHandler.instance().requestManaExactForTool(stack, (PlayerEntity) entity, MANA_PER_DAMAGE * 2, true))
                stack.setDamage(stack.getDamage() - 1);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(final ItemStack stack, @Nullable World world, final List<ITextComponent> tooltip, ITooltipFlag flags) {
        if (!hasUUID(stack)) {
            tooltip.add(new TranslationTextComponent("botaniamisc.relicUnbound"));
        } else {
            if (!getSoulbindUUID(stack).equals(Minecraft.getInstance().player.getUniqueID())) {
                tooltip.add(new TranslationTextComponent("botaniamisc.notYourSagittarius"));
            } else {
                tooltip.add(new TranslationTextComponent("botaniamisc.relicSoulbound", Minecraft.getInstance().player.getName()));
            }
        }
    }

    public boolean shouldDamageWrongPlayer() {
        return true;
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, World world) {
        return Integer.MAX_VALUE;
    }

    public void updateRelic(ItemStack stack, PlayerEntity player) {
        if (stack.isEmpty() || !(stack.getItem() instanceof IRelic)) {
            return;
        }

        boolean rightPlayer = true;

        if (!hasUUID(stack)) {
            bindToUUID(player.getUniqueID(), stack);
            if (player instanceof ServerPlayerEntity) {
                RelicBindTrigger.INSTANCE.trigger((ServerPlayerEntity) player, stack);
            }
        } else if (!getSoulbindUUID(stack).equals(player.getUniqueID())) {
            rightPlayer = false;
        }

        if (!rightPlayer && player.ticksExisted % 10 == 0 && (!(stack.getItem() instanceof ItemRelic) || ((ItemRelic) stack.getItem()).shouldDamageWrongPlayer())) {
            player.attackEntityFrom(damageSource(), 2);
        }
    }

    public boolean isRightPlayer(PlayerEntity player, ItemStack stack) {
        return hasUUID(stack) && getSoulbindUUID(stack).equals(player.getUniqueID());
    }

    public static DamageSource damageSource() {
        return new DamageSource("botania-relic");
    }

    @Override
    public void bindToUUID(UUID uuid, ItemStack stack) {
        ItemNBTHelper.setString(stack, TAG_SOULBIND_UUID, uuid.toString());
    }

    @Override
    public UUID getSoulbindUUID(ItemStack stack) {
        if (ItemNBTHelper.verifyExistance(stack, TAG_SOULBIND_UUID)) {
            try {
                return UUID.fromString(ItemNBTHelper.getString(stack, TAG_SOULBIND_UUID, ""));
            } catch (IllegalArgumentException ex) { // Bad UUID in tag
                ItemNBTHelper.removeEntry(stack, TAG_SOULBIND_UUID);
            }
        }

        return null;
    }

    @Override
    public boolean hasUUID(ItemStack stack) {
        return getSoulbindUUID(stack) != null;
    }

    @Override
    public boolean usesMana(ItemStack stack) {
        return true;
    }

}
