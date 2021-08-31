package com.meteor.extrabotany.common.blocks.generating;

import com.meteor.extrabotany.common.blocks.ModSubtiles;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.AxisAlignedBB;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.TileEntityGeneratingFlower;
import vazkii.botania.client.fx.WispParticleData;

public class SubTileReikarLily extends TileEntityGeneratingFlower {

    private static final String TAG_BURN_TIME = "burnTime";
    private static final String TAG_COOLDOWN = "cooldown";
    private static final String TAG_CD = "cd";
    private static final int RANGE = 5;
    int burnTime = 0, cooldown = 0, cd = 0;

    public SubTileReikarLily() {
        super(ModSubtiles.REIKARLILY);
    }

    @Override
    public void tickFlower() {
        super.tickFlower();

        if (getWorld().isRaining() && getWorld().canSeeSky(pos) && cd == 0) {
            int baseY = 64;
            if (getWorld().rand.nextInt((int) (4000 * baseY / pos.getY())) == 1) {
                LightningBoltEntity bolt = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, getWorld());
                bolt.setPosition(getEffectivePos().getX(), getEffectivePos().getY(), getEffectivePos().getZ());
                if(!getWorld().isRemote)
                    getWorld().addEntity(bolt);
                cd += getCooldown();
                if (cooldown == 0) {
                    burnTime += 1500;
                    if (getMana() < getMaxMana())
                        addMana(getMaxMana());
                    cooldown = getCooldown();
                }
            }
        }

        for (LightningBoltEntity bolt : getWorld().getEntitiesWithinAABB(LightningBoltEntity.class,
                new AxisAlignedBB(getEffectivePos().add(-RANGE, -RANGE, -RANGE),
                        getEffectivePos().add(RANGE + 1, RANGE + 1, RANGE + 1)))) {
            if (!bolt.removed) {
                if (cooldown == 0) {
                    burnTime += 1500;
                    if (getMana() < getMaxMana())
                        addMana(getMaxMana());
                    cooldown = getCooldown();
                    bolt.remove();
                    break;
                }
            }
        }

        if (cooldown > 0) {
            cooldown--;
            for (int i = 0; i < 3; i++) {
                WispParticleData data = WispParticleData.wisp((float) Math.random() / 6, 0.1F, 0.1F, 0.1F, 1);
                world.addParticle(data, getEffectivePos().getX() + 0.5 + Math.random() * 0.2 - 0.1, getEffectivePos().getY() + 0.5 + Math.random() * 0.2 - 0.1, getEffectivePos().getZ() + 0.5 + Math.random() * 0.2 - 0.1, 0, (float) Math.random() / 30, 0);
            }
        }
        if (cd > 0)
            cd--;
        if (burnTime > 0)
            burnTime--;

    }

    @Override
    public int getMaxMana() {
        return 12000;
    }

    @Override
    public int getColor() {
        return 0x0000CD;
    }

    @Override
    public void writeToPacketNBT(CompoundNBT cmp) {
        super.writeToPacketNBT(cmp);
        cmp.putInt(TAG_BURN_TIME, burnTime);
        cmp.putInt(TAG_COOLDOWN, cooldown);
        cmp.putInt(TAG_CD, cd);
    }

    @Override
    public void readFromPacketNBT(CompoundNBT cmp) {
        super.readFromPacketNBT(cmp);
        burnTime = cmp.getInt(TAG_BURN_TIME);
        cooldown = cmp.getInt(TAG_COOLDOWN);
        cd = cmp.getInt(TAG_CD);
    }

    @Override
    public RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Square(getEffectivePos(), RANGE);
    }

    @Override
    public boolean canGeneratePassively() {
        return burnTime > 0;
    }

    @Override
    public int getValueForPassiveGeneration() {
        return 45;
    }

    public int getCooldown() {
        return 3600;
    }
}
