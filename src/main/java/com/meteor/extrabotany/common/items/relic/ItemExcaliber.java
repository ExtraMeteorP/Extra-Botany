package com.meteor.extrabotany.common.items.relic;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.meteor.extrabotany.common.handler.DamageHandler;
import com.meteor.extrabotany.common.handler.IAdvancementRequirement;
import com.meteor.extrabotany.common.libs.LibAdvancementNames;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.internal.IManaBurst;
import vazkii.botania.api.mana.BurstProperties;
import vazkii.botania.api.mana.ILensEffect;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.common.core.handler.ModSounds;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.core.helper.Vector3;
import vazkii.botania.common.entity.EntityManaBurst;
import vazkii.botania.common.item.equipment.tool.ToolCommons;
import vazkii.botania.common.item.relic.ItemRelic;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public class ItemExcaliber extends ItemSwordRelic implements IManaUsingItem, ILensEffect, IAdvancementRequirement {

    private static final String TAG_ATTACKER_USERNAME = "attackerUsername";
    private static final String TAG_HOME_ID = "homeID";
    private static final int MANA_PER_DAMAGE = 160;

    public ItemExcaliber(Properties prop) {
        super(ItemTier.NETHERITE, 8, -2F, prop);
    }

    @Override
    public void onLeftClick(PlayerEntity player, Entity target) {
        if (!player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() == this
                && player.getCooledAttackStrength(0) == 1) {
            EntityManaBurst burst = getBurst(player, player.getHeldItemMainhand());
            player.world.addEntity(burst);
            ToolCommons.damageItemIfPossible(player.getHeldItemMainhand(), 1, player, MANA_PER_DAMAGE);
            player.world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), ModSounds.terraBlade,
                    SoundCategory.PLAYERS, 0.4F, 1.4F);
        }
    }

    @Nonnull
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(@Nonnull EquipmentSlotType slot) {
        Multimap<Attribute, AttributeModifier> ret = super.getAttributeModifiers(slot);
        if (slot == EquipmentSlotType.MAINHAND) {
            ret = HashMultimap.create(ret);
            ret.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("995829fa-94c0-41bd-b046-0468c509a488"), "Excaliber modifier", 0.3D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        return ret;
    }

    public static EntityManaBurst getBurst(PlayerEntity player, ItemStack stack) {
        EntityManaBurst burst = new EntityManaBurst(player);

        float motionModifier = 9F;
        burst.setColor(0xFFFF20);
        burst.setMana(MANA_PER_DAMAGE);
        burst.setStartingMana(MANA_PER_DAMAGE);
        burst.setMinManaLoss(40);
        burst.setManaLossPerTick(4F);
        burst.setGravity(0F);
        burst.setMotion(burst.getMotion().x * motionModifier, burst.getMotion().y * motionModifier, burst.getMotion().z * motionModifier);

        ItemStack lens = stack.copy();
        ItemNBTHelper.setString(lens, TAG_ATTACKER_USERNAME, player.getName().getString());
        burst.setSourceLens(lens);
        return burst;
    }

    @Override
    public void apply(ItemStack stack, BurstProperties props) {

    }

    @Override
    public boolean collideBurst(IManaBurst burst, RayTraceResult pos, boolean isManaBlock, boolean dead, ItemStack stack) {
        return dead;
    }

    @Override
    public void updateBurst(IManaBurst burst, ItemStack stack) {
        ThrowableEntity entity = (ThrowableEntity) burst;
        AxisAlignedBB axis = new AxisAlignedBB(entity.getPosX(), entity.getPosY(), entity.getPosZ(), entity.lastTickPosX,
                entity.lastTickPosY, entity.lastTickPosZ).grow(1);
        String attacker = ItemNBTHelper.getString(burst.getSourceLens(), TAG_ATTACKER_USERNAME, "");


        int homeID = ItemNBTHelper.getInt(stack, TAG_HOME_ID, -1);
        if (homeID == -1) {
            AxisAlignedBB axis1 = new AxisAlignedBB(entity.getPosX() - 5F, entity.getPosY() - 5F, entity.getPosZ() - 5F,
                    entity.lastTickPosX + 5F, entity.lastTickPosY + 5F, entity.lastTickPosZ + 5F);
            List<LivingEntity> entities = entity.world.getEntitiesWithinAABB(LivingEntity.class, axis1);
            for (LivingEntity living : entities) {
                if (living instanceof PlayerEntity || !(living instanceof IMob) || living.hurtTime != 0)
                    continue;
                homeID = living.getEntityId();
                ItemNBTHelper.setInt(stack, TAG_HOME_ID, homeID);
                break;
            }
        }

        List<LivingEntity> entities = entity.world.getEntitiesWithinAABB(LivingEntity.class, axis);
        if (homeID != -1) {
            Entity home = entity.world.getEntityByID(homeID);
            if (home != null) {
                Vector3 vecEntity = Vector3.fromEntityCenter(home);
                Vector3 vecThis = Vector3.fromEntityCenter(entity);
                Vector3 vecMotion = vecEntity.subtract(vecThis);
                Vector3 vecCurrentMotion = new Vector3(entity.getMotion().x, entity.getMotion().y, entity.getMotion().z);
                vecMotion.normalize().multiply(vecCurrentMotion.mag());
                entity.setMotion(vecMotion.x, vecMotion.y, vecMotion.z);
            }
        }

        for (LivingEntity living : entities) {
            if (living instanceof PlayerEntity && (living.getName().getString().equals(attacker)))
                continue;

            if (!living.removed) {
                int cost = MANA_PER_DAMAGE / 3;
                int mana = burst.getMana();
                if (mana >= cost) {
                    burst.setMana(mana - cost);
                    float damage = BotaniaAPI.instance().getTerrasteelItemTier().getAttackDamage() + 3F;
                    if (!burst.isFake() && !entity.world.isRemote) {
                        PlayerEntity player = living.world.getPlayerByUuid(getSoulbindUUID(stack));
                        DamageHandler.INSTANCE.dmg(living, player, damage, DamageHandler.INSTANCE.NETURAL_PIERCING);
                        entity.remove();
                        break;
                    }
                }
            }
        }
    }

    @Override
    public boolean doParticles(IManaBurst burst, ItemStack stack) {
        return true;
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
