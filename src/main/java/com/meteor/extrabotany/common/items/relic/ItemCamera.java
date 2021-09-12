package com.meteor.extrabotany.common.items.relic;

import com.meteor.extrabotany.common.handler.IAdvancementRequirement;
import com.meteor.extrabotany.common.libs.LibAdvancementNames;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.item.relic.ItemRelic;

import javax.annotation.Nonnull;

public class ItemCamera extends ItemRelic implements IManaUsingItem, IAdvancementRequirement {

    public static final int MANA_PER_DAMAGE = 1500;
    public static final int RANGE = 20;
    public static final String TAG_FREEZETIME = "freezeTime";
    public static final String TAG_TIMES = "freezeTimes";

    public ItemCamera(Properties props) {
        super(props);
        MinecraftForge.EVENT_BUS.addListener(this::onLivingUpdate);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event){
        if(event.getEntity() instanceof LivingEntity) {
            if (!(event.getEntityLiving() instanceof PlayerEntity)) {
                if (event.getEntityLiving().getPersistentData().getInt(TAG_FREEZETIME) > 0) {
                    event.getEntityLiving().getPersistentData().putInt(TAG_FREEZETIME,
                            event.getEntityLiving().getPersistentData().getInt(TAG_FREEZETIME) - 1);
                    event.setCanceled(true);
                }
            }
        }
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (isRightPlayer(player, stack) && ManaItemHandler.instance().requestManaExactForTool(stack, player, MANA_PER_DAMAGE, true)
                && !world.isRemote) {
            for (LivingEntity living : player.getEntityWorld().getEntitiesWithinAABB(LivingEntity.class,
                    new AxisAlignedBB(player.getPosition().add(-RANGE, -RANGE, -RANGE),
                            player.getPosition().add(RANGE + 1, RANGE + 1, RANGE + 1)))) {
                if(living == player)
                    continue;
                if (living.isSpectatedByPlayer((ServerPlayerEntity) player)) {
                    living.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 100, 5));
                    int time = 200;
                    if (!living.isNonBoss())
                        time = 40;
                    if (living.getPersistentData().getInt(TAG_TIMES) > 10)
                        time = 0;
                    living.getPersistentData().putInt(TAG_FREEZETIME, time);
                    living.getPersistentData().putInt(TAG_TIMES,
                            living.getPersistentData().getInt(TAG_TIMES) + 1);
                }
            }

            for (Entity e : player.getEntityWorld().getEntitiesWithinAABB(Entity.class,
                    new AxisAlignedBB(player.getPosition().add(-RANGE, -RANGE, -RANGE),
                            player.getPosition().add(RANGE + 1, RANGE + 1, RANGE + 1)))) {

                if (e instanceof ProjectileEntity)
                    e.remove();
            }
            player.getCooldownTracker().setCooldown(stack.getItem(), 200);
        }
        return ActionResult.resultPass(stack);
    }

    @Override
    public boolean usesMana(ItemStack stack) {
        return true;
    }

    @Override
    public String getAdvancementName() {
        return LibAdvancementNames.EGODEFEAT;
    }
}
