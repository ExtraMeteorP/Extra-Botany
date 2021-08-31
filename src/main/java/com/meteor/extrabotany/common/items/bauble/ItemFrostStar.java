package com.meteor.extrabotany.common.items.bauble;

import com.meteor.extrabotany.common.handler.DamageHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;

public class ItemFrostStar extends ItemBauble implements IManaUsingItem {

    public ItemFrostStar(Properties props) {
        super(props);
    }

    private static final int RANGE = 6;
    private static final int MANA_PER_DAMAGE = 30;

    @Override
    public void onWornTick(ItemStack stack, LivingEntity entity) {
        super.onWornTick(stack, entity);
        if(entity instanceof PlayerEntity) {
            if (!entity.world.isRemote) {
                for (LivingEntity living : entity.getEntityWorld().getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(entity.getPosition().add(-RANGE, -RANGE, -RANGE), entity.getPosition().add(RANGE + 1, RANGE + 1, RANGE + 1)))) {
                    if (living.isSpectatedByPlayer((ServerPlayerEntity)entity)
                            && living != entity
                            && DamageHandler.INSTANCE.checkPassable(living, entity)
                            && entity.ticksExisted % 20 == 0
                            && ManaItemHandler.instance().requestManaExactForTool(stack, (PlayerEntity) entity, MANA_PER_DAMAGE, true)) {
                        living.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 60, 4));
                    }
                }
            }
        }
    }

    @Override
    public boolean usesMana(ItemStack stack) {
        return true;
    }

}
