package com.meteor.extrabotany.common.blocks.generating;

import com.meteor.extrabotany.common.blocks.ModSubtiles;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.TileEntityGeneratingFlower;
import vazkii.botania.common.core.handler.ModSounds;

public class SubTileOmniViolet extends TileEntityGeneratingFlower {

    private static final String TAG_BURN_TIME = "burnTime";
    private static final int FUEL_CAP = 32000;
    private static final int RANGE = 2;
    int burnTime = 0;

    public SubTileOmniViolet() {
        super(ModSubtiles.OMNIVIOLET);
    }

    private float getPower(net.minecraft.world.World world, net.minecraft.util.math.BlockPos pos) {
        return world.getBlockState(pos).getEnchantPowerBonus(world, pos);
    }
    
    private float getPower(){
        float power = 0F;
        for(int k = -1; k <= 1; ++k) {
            for(int l = -1; l <= 1; ++l) {
                if ((k != 0 || l != 0) && getWorld().isAirBlock(getEffectivePos().add(l, 0, k)) && getWorld().isAirBlock(getEffectivePos().add(l, 1, k))) {
                    power += getPower(getWorld(), getEffectivePos().add(l * 2, 0, k * 2));
                    power += getPower(getWorld(), getEffectivePos().add(l * 2, 1, k * 2));

                    if (l != 0 && k != 0) {
                        power += getPower(getWorld(), getEffectivePos().add(l * 2, 0, k));
                        power += getPower(getWorld(), getEffectivePos().add(l * 2, 1, k));
                        power += getPower(getWorld(), getEffectivePos().add(l, 0, k * 2));
                        power += getPower(getWorld(), getEffectivePos().add(l, 1, k * 2));
                    }
                }
            }
        }
        return power;
    }

    @Override
    public void tickFlower() {
        super.tickFlower();

        float buff = 1 + getPower() * 0.05F;

        if (burnTime > 0) {
            burnTime--;
            addMana((int) (8 * Math.min(7F, buff)));
        }

        if (linkedCollector != null) {
            if (burnTime == 0) {
                if (getMana() < getMaxMana()) {
                    int slowdown = getSlowdownFactor();

                    for (ItemEntity item : getWorld().getEntitiesWithinAABB(ItemEntity.class,
                            new AxisAlignedBB(getEffectivePos().add(-RANGE, -RANGE, -RANGE),
                                    getEffectivePos().add(RANGE + 1, RANGE + 1, RANGE + 1)))) {
                        if (item.ticksExisted >= 59 + slowdown && !item.removed) {
                            ItemStack stack = item.getItem();
                            if (stack.isEmpty() || stack.getItem().hasContainerItem(stack))
                                continue;

                            int burnTime = stack.getItem() == Items.BOOK ? 50 : stack.getItem() == Items.WRITTEN_BOOK ? 65 : 0;
                            if (burnTime > 0 && stack.getCount() > 0) {
                                this.burnTime = Math.min(FUEL_CAP, burnTime);

                                stack.shrink(1);
                                getWorld().playSound(null, getEffectivePos(), ModSounds.endoflame,
                                        SoundCategory.BLOCKS, 0.2F, 1F);
                                sync();

                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getMaxMana() {
        return 1500;
    }

    @Override
    public int getValueForPassiveGeneration() {
        return 8;
    }

    @Override
    public int getColor() {
        return 0xEE82EE;
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
    public RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Square(getEffectivePos(), RANGE);
    }

    @Override
    public boolean canGeneratePassively() {
        return burnTime > 0;
    }

}
