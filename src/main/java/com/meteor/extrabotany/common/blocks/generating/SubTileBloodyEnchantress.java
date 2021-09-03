package com.meteor.extrabotany.common.blocks.generating;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.blocks.ModSubtiles;
import com.meteor.extrabotany.common.handler.AdvancementHandler;
import com.meteor.extrabotany.common.libs.LibAdvancementNames;
import com.meteor.extrabotany.common.potions.ModPotions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.TileEntityGeneratingFlower;

public class SubTileBloodyEnchantress extends TileEntityGeneratingFlower {

    private static final String TAG_BURN_TIME = "burnTime";
    private static final int RANGE = 1;
    private static final int START_BURN_EVENT = 0;

    private int burnTime = 0;

    public SubTileBloodyEnchantress() {
        super(ModSubtiles.BLOODY_ENCHANTRESS);
    }

    @Override
    public void tickFlower() {
        super.tickFlower();

        if(burnTime > 0)
            burnTime--;

        int ampall = 0;
        for(LivingEntity living : getWorld().getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(getEffectivePos().add(-RANGE, -RANGE, -RANGE), getEffectivePos().add(RANGE + 1, RANGE + 1, RANGE + 1)))){
            if(!living.removed){
                int amp = living.isPotionActive(ModPotions.bloodtemptation) ? living.getActivePotionEffect(ModPotions.bloodtemptation).getAmplifier() : 0;
                ampall += amp;
            }
        }
        if(ampall > 35)
            return;

        if(linkedCollector != null) {
            if(burnTime == 0) {
                if(getMana() < getMaxMana()) {
                    for(LivingEntity living : getWorld().getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(getEffectivePos().add(-RANGE, -RANGE, -RANGE), getEffectivePos().add(RANGE + 1, RANGE + 1, RANGE + 1)))){
                        if(!living.removed){
                            int amp = living.isPotionActive(ModPotions.bloodtemptation) ? living.getActivePotionEffect(ModPotions.bloodtemptation).getAmplifier() : 0;
                            if(amp > 4 && Math.random() > 0.5F)
                                continue;
                            if(amp < 10){
                                addMana((int) (22F * 12F * (1F - 0.04F * amp - 0.02F * ampall)));
                            }else
                                break;
                            ExtraBotanyAPI.addPotionEffect(living, ModPotions.bloodtemptation, 100, 10, true);
                            if(living instanceof ServerPlayerEntity){
                                AdvancementHandler.INSTANCE.grantAdvancement((ServerPlayerEntity) living, LibAdvancementNames.BLOODYENCHANTRESSUSE);
                            }
                            living.attackEntityFrom(DamageSource.MAGIC.setDamageIsAbsolute().setDamageBypassesArmor(), 3F);
                            living.attackEntityFrom(DamageSource.MAGIC, 0.01F);
                            burnTime+=20;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void writeToPacketNBT(CompoundNBT cmp) {
        super.writeToPacketNBT(cmp);
        cmp.putInt(TAG_BURN_TIME, burnTime);
    }

    @Override
    public void readFromPacketNBT(CompoundNBT cmp) {
        super.readFromPacketNBT(cmp);
        burnTime = cmp.getInt(TAG_BURN_TIME);
    }

    @Override
    public boolean receiveClientEvent(int event, int param) {
        if(event == START_BURN_EVENT) {
            Entity e = getWorld().getEntityByID(param);
            if(e != null) {
                e.world.addParticle(ParticleTypes.LARGE_SMOKE, e.getPosX(), e.getPosY() + 0.1, e.getPosZ(), 0.0D, 0.0D, 0.0D);
                e.world.addParticle(ParticleTypes.FLAME, e.getPosX(), e.getPosY(), e.getPosZ(), 0.0D, 0.0D, 0.0D);
            }
            return true;
        } else {
            return super.receiveClientEvent(event, param);
        }
    }

    @Override
    public int getMaxMana() {
        return 800;
    }

    @Override
    public int getValueForPassiveGeneration() {
        return 22;
    }

    @Override
    public int getColor() {
        return 0x8B0000;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Square(getEffectivePos(), RANGE);
    }

}
