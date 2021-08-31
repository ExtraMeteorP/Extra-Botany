package com.meteor.extrabotany.common.items;

import com.meteor.extrabotany.common.items.armor.shootingguardian.ItemShootingGuardianArmor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import vazkii.botania.api.mana.BurstProperties;
import vazkii.botania.api.mana.ILens;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.ItemManaGun;

import javax.annotation.Nonnull;

public class ItemSilverBullet extends ItemManaGun {

    public ItemSilverBullet(Properties props) {
        super(props);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, @Nonnull Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        ItemNBTHelper.setBoolean(stack, "usemana", !((ItemShootingGuardianArmor)ModItems.armor_shootingguardian_helm).hasArmorSet(player));
        return super.onItemRightClick(world, player, hand);
    }

    @Nonnull
    @Override
    public BurstProperties getBurstProps(PlayerEntity player, ItemStack stack, boolean request, Hand hand) {
        int maxMana = 240;
        int color = 0x87CEFA;
        int ticksBeforeManaLoss = 80;
        float manaLossPerTick = 3F;
        float motionModifier = 7.5F;
        float gravity = 0F;
        BurstProperties props = new BurstProperties(maxMana, ticksBeforeManaLoss, manaLossPerTick, gravity, motionModifier, color);

        ItemStack lens = getLens(stack);
        if (!lens.isEmpty()) {
            ((ILens) lens.getItem()).apply(lens, props);
        }
        return props;
    }

    @Override
    public boolean usesMana(ItemStack stack) {
        return ItemNBTHelper.getBoolean(stack, "usemana", true);
    }

}
