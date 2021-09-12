package com.meteor.extrabotany.common.items.relic;

import com.meteor.extrabotany.api.items.IItemWithLeftClick;
import com.meteor.extrabotany.common.network.LeftClickPack;
import com.meteor.extrabotany.common.network.NetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import vazkii.botania.api.item.IRelic;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.advancements.RelicBindTrigger;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.relic.ItemRelic;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ItemSwordRelic extends SwordItem implements IRelic, IItemWithLeftClick {

    private static final String TAG_SOULBIND_UUID = "soulbindUUID";
    private static final int MANA_PER_DAMAGE = 120;

    public ItemSwordRelic(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        MinecraftForge.EVENT_BUS.addListener(this::leftClick);
        MinecraftForge.EVENT_BUS.addListener(this::leftClickBlock);
        MinecraftForge.EVENT_BUS.addListener(this::attackEntity);
    }

    public void attackEntity(AttackEntityEvent evt) {
        if (!evt.getPlayer().world.isRemote) {
            onLeftClick(evt.getPlayer(), evt.getTarget());
        }
    }

    public void leftClick(PlayerInteractEvent.LeftClickEmpty evt) {
        if (!evt.getItemStack().isEmpty() && evt.getItemStack().getItem() == this) {
            NetworkHandler.INSTANCE.sendToServer(new LeftClickPack(evt.getItemStack()));
        }
    }

    public void leftClickBlock(PlayerInteractEvent.LeftClickBlock evt) {
        if (evt.getPlayer().world.isRemote && !evt.getItemStack().isEmpty() && evt.getItemStack().getItem() == this) {
            NetworkHandler.INSTANCE.sendToServer(new LeftClickPack(evt.getItemStack()));
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isRemote && entity instanceof PlayerEntity) {
            updateRelic(stack, (PlayerEntity) entity);
        }
    }

    @Override
    public void onLeftClick(PlayerEntity living, Entity target) {

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

        if (!player.world.isRemote && stack.getDamage() > 0 && ManaItemHandler.instance().requestManaExact(stack, player, MANA_PER_DAMAGE * 2, true)) {
            stack.setDamage(stack.getDamage() - 1);
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

    public static BlockRayTraceResult raytraceFromEntity(Entity e, double distance, boolean fluids) {
        return (BlockRayTraceResult) e.pick(distance, 1, fluids);
    }

}
