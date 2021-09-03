package com.meteor.extrabotany.common.items.relic;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.common.entities.projectile.EntityPhantomSword;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Rarity;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import java.util.UUID;

public class ItemFirstFractal extends ItemSwordRelic {

    public ItemFirstFractal() {
        super(ItemTier.NETHERITE, 10, -1.6F, new Properties().group(ExtraBotany.itemGroup).rarity(Rarity.EPIC).maxStackSize(1).setNoRepair());
    }

    @Nonnull
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(@Nonnull EquipmentSlotType slot) {
        Multimap<Attribute, AttributeModifier> ret = super.getAttributeModifiers(slot);
        if (slot == EquipmentSlotType.MAINHAND) {
            ret = HashMultimap.create(ret);
            ret.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("995829fa-94c0-41bd-b046-0468c509a488"), "Fractal modifier", 0.3D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        return ret;
    }

    public void attackEntity(LivingEntity player, Entity target){
        BlockPos targetpos = target == null ? raytraceFromEntity(player, 80F, true).getPos().add(0, 1, 0) : new BlockPos(target.getPositionVec()).add(0, 1, 0);
        double range = 13D;
        double j = -Math.PI + 2 * Math.PI * Math.random();
        double k;
        double x,y,z;
        for(int i = 0; i < 3; i++) {
            EntityPhantomSword sword = new EntityPhantomSword(player.world, player, targetpos);
            sword.setDelay(5 + 5 * i);
            k = 0.12F * Math.PI * Math.random() + 0.28F * Math.PI;
            x = targetpos.getX() + range * Math.sin(k) * Math.cos(j);
            y = targetpos.getY() + range * Math.cos(k);
            z = targetpos.getZ() + range * Math.sin(k) * Math.sin(j);
            j+= 2 * Math.PI * Math.random() * 0.08F + 2 * Math.PI * 0.17F;
            sword.setPosition(x, y, z);
            sword.faceTarget(1.05F);
            player.world.addEntity(sword);

        }
        EntityPhantomSword sword2 = new EntityPhantomSword(player.world, player, targetpos);
        k = 0.12F * Math.PI * Math.random() + 0.28F * Math.PI;
        x = targetpos.getX() + range * Math.sin(k) * Math.cos(j);
        y = targetpos.getY() + range * Math.cos(k);
        z = targetpos.getZ() + range * Math.sin(k) * Math.sin(j);
        sword2.setPosition(x, y, z);
        sword2.faceTarget(1.05F);
        sword2.setVariety(9);
        player.world.addEntity(sword2);
    }

    @Override
    public void onLeftClick(PlayerEntity player, Entity target) {
        if (!player.world.isRemote && !player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() == this
                && player.getCooledAttackStrength(0) == 1) {
            attackEntity(player, target);
        }
    }

}
