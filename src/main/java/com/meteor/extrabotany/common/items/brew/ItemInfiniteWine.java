package com.meteor.extrabotany.common.items.brew;

import com.meteor.extrabotany.common.items.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.botania.api.item.IRelic;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.advancements.RelicBindTrigger;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.relic.ItemRelic;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ItemInfiniteWine extends ItemBrewBase implements IRelic, IManaUsingItem {

    private static final String TAG_SOULBIND_UUID = "soulbindUUID";
    private static final int MANA_PER_DAMAGE = 12000;

    public ItemInfiniteWine(Properties builder) {
        super(builder, 12, 18, 1.5F, 1, ()-> ModItems.emptybottle);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isRemote && entity instanceof PlayerEntity) {
            updateRelic(stack, (PlayerEntity) entity);

            if(world.getGameTime() % 8000 == 0
                    && getSwigsLeft(stack) < 12
                    && ManaItemHandler.instance().requestManaExactForTool(stack, (PlayerEntity) entity, MANA_PER_DAMAGE, true))
                setSwigsLeft(stack, getSwigsLeft(stack) + 1);
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
        super.addInformation(stack, world, tooltip, flags);
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
