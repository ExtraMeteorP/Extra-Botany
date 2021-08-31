package com.meteor.extrabotany.common.handler;

import com.meteor.extrabotany.common.core.EquipmentHandler;
import com.meteor.extrabotany.common.items.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;

import java.util.List;
import java.util.stream.Collectors;

public final class DamageHandler {

    public static final DamageHandler INSTANCE = new DamageHandler();

    public final int NETURAL = 0;
    public final int MAGIC = 1;
    public final int NETURAL_PIERCING = 2;
    public final int MAGIC_PIERCING = 3;
    public final int LIFE_LOSING = 4;

    public boolean checkPassable(Entity target, Entity source){
        if(target == source)
            return false;
        if(source instanceof PlayerEntity){
            PlayerEntity sourcePlayer = (PlayerEntity) source;
            boolean sourceEquipped = !EquipmentHandler.findOrEmpty(ModItems.peaceamulet, sourcePlayer).isEmpty();
            if(target instanceof PlayerEntity){
                PlayerEntity targetPlayer = (PlayerEntity) target;
                return !sourceEquipped && EquipmentHandler.findOrEmpty(ModItems.peaceamulet, targetPlayer).isEmpty();
            }
            if(sourceEquipped && !(target instanceof IMob) && target.isNonBoss())
                return false;
        }

        if(source instanceof IMob){
            if(target instanceof PlayerEntity)
                return true;
            return false;
        }

        return true;
    }

    public List<LivingEntity> getFilteredEntities(List<LivingEntity> entities, Entity source){
        List<LivingEntity> list = entities.stream().filter((living) -> checkPassable(living, source) && !living.removed).collect(Collectors.toList());
        return list;
    }

    public static float calcDamage(float orig, PlayerEntity player){
        if(player == null)
            return orig;
        double value = 0F;
        return (float) (orig + value);
    }

    public boolean dmg(Entity target, Entity source, float amount, int type){
        if(target == null || !checkPassable(target, source))
            return false;
        switch (type){
            case NETURAL: {
                if (source instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) source;
                    DamageSource s = DamageSource.causePlayerDamage(player);
                    return target.attackEntityFrom(s, amount);
                } else if (source instanceof LivingEntity) {
                    LivingEntity living = (LivingEntity) source;
                    DamageSource s = DamageSource.causeMobDamage(living);
                    return target.attackEntityFrom(s, amount);
                } else {
                    return target.attackEntityFrom(DamageSource.GENERIC, amount);
                }
            }
            case MAGIC: {
                DamageSource s = DamageSource.MAGIC;
                return target.attackEntityFrom(s, amount);
            }
            case NETURAL_PIERCING: {
                target.hurtResistantTime=0;
                if (source instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) source;
                    DamageSource s = DamageSource.causePlayerDamage(player).setDamageBypassesArmor().setDamageIsAbsolute();
                    return target.attackEntityFrom(s, amount);
                } else if (source instanceof LivingEntity) {
                    LivingEntity living = (LivingEntity) source;
                    DamageSource s = DamageSource.causeMobDamage(living).setDamageBypassesArmor().setDamageIsAbsolute();
                    return target.attackEntityFrom(s, amount);
                } else {
                    return target.attackEntityFrom(DamageSource.GENERIC, amount);
                }
            }
            case MAGIC_PIERCING: {
                target.hurtResistantTime=0;
                DamageSource s = DamageSource.MAGIC.setDamageBypassesArmor().setDamageIsAbsolute();
                return target.attackEntityFrom(s, amount);
            }
            case LIFE_LOSING:{
                if(!(target instanceof LivingEntity))
                    return false;
                LivingEntity living = (LivingEntity) target;
                float currentHealth = living.getHealth();
                float trueHealth = Math.max(1F, currentHealth - amount);
                living.setHealth(trueHealth);
                return dmg(target, source, 0.01F, NETURAL);
            }
        }
        return false;
    }

}
