package com.meteor.extrabotany.common.integration.tinkerconstruct;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import com.meteor.extrabotany.common.core.handler.DarkPixieHandler;
import com.meteor.extrabotany.common.entity.EntityDarkPixie;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;
import vazkii.botania.api.mana.ManaItemHandler;

import java.util.List;

public class Shadow extends AbstractTrait {
    public static final Shadow shadow = new Shadow();

    public Shadow() {
        super("shadow", 0x800080);
    }

    private static boolean drawMana(EntityPlayer ent) {
        IItemHandler handler = ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        for (int i = 0; i < handler.getSlots(); ++i) {
            if (ManaItemHandler.requestManaExactForTool(handler.getStackInSlot(i), ent, 100, true)) {
                return true;
            }
        }

        IBaublesItemHandler ib = BaublesApi.getBaublesHandler(ent);
        for (int i = 0; i < ib.getSlots(); ++i) {
            if (ManaItemHandler.requestManaExactForTool(ib.getStackInSlot(i), ent, 100, true)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {

    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isRemote
                && entity instanceof EntityPlayer
                && ToolHelper.getCurrentDurability(tool) < ToolHelper.getMaxDurability(tool)
                && drawMana((EntityPlayer) entity)) {
            ToolHelper.unbreakTool(tool);
            ToolHelper.healTool(tool, 1, (EntityPlayer) entity);
        }

    }

    @Override
    public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
        if (!entity.getEntityWorld().isRemote
                && entity instanceof EntityPlayer
                && drawMana((EntityPlayer) entity)) {
            --newDamage;
        }
        return super.onToolDamage(tool, damage, newDamage, entity);
    }

    @Override
    public void onHit(ItemStack tool, EntityLivingBase entity, EntityLivingBase target, float damage, boolean isCritical) {
        int RANGE = 8;

        if (entity instanceof EntityPlayer && !entity.getEntityWorld().isDaytime()) {
            EntityPlayer player = (EntityPlayer) entity;

            if (!player.getHeldItemMainhand().isItemEqual(tool))
                return;

            List<EntityLivingBase> livings = entity.getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(player.getPosition().add(-RANGE, -RANGE, -RANGE), player.getPosition().add(RANGE + 1, RANGE + 1, RANGE + 1)));
            if (livings.size() > 1)
                for (EntityLivingBase living : livings) {
                    if (living == player)
                        continue;
                    if (living.isEntityAlive() && ManaItemHandler.requestManaExactForTool(tool, player, 100, true)) {
                        EntityDarkPixie pixie = DarkPixieHandler.getPixie(player, living, tool);
                        if (!entity.getEntityWorld().isRemote)
                            entity.getEntityWorld().spawnEntity(pixie);
                        break;
                    }
                }
        }
    }

}
