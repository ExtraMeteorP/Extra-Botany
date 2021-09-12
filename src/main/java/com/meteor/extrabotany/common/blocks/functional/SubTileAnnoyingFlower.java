package com.meteor.extrabotany.common.blocks.functional;

import com.meteor.extrabotany.common.blocks.ModSubtiles;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import vazkii.botania.api.item.IPetalApothecary;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.TileEntityFunctionalFlower;
import vazkii.botania.common.block.tile.TileAltar;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SubTileAnnoyingFlower extends TileEntityFunctionalFlower {

    private static final int COST = 300;
    private static final int RANGE = 3;
    private static final String TAG_TIME = "times";
    int times = 0;

    public SubTileAnnoyingFlower() {
        super(ModSubtiles.ANNOYING_FLOWER);
    }

    @Override
    public void tickFlower() {
        super.tickFlower();
        if(redstoneSignal > 0)
            return;

        boolean hasWater = false;
        for(int x = -RANGE; x <= RANGE; x++){
            for(int z = -RANGE; z <= RANGE; z++){
                BlockPos posi = getEffectivePos().add(x, 0, z);
                if(getWorld().getTileEntity(posi) instanceof TileAltar){
                    TileAltar te = (TileAltar) getWorld().getTileEntity(posi);
                    hasWater = te.getFluid() == IPetalApothecary.State.WATER;
                    if(hasWater)
                        break;
                }
            }
        }

        for(ItemEntity item : getWorld().getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(getEffectivePos().add(-RANGE, -RANGE, -RANGE), getEffectivePos().add(RANGE + 1, RANGE + 1, RANGE + 1)))){
            if(item.getItem().getItem() == Items.COOKED_CHICKEN && item.getItem().getCount() > 0){
                item.getItem().shrink(1);
                times += 3;
            }
        }

        int cd = times > 0 ? (900 * 2/5) : 900;

        if(redstoneSignal == 0 && ticksExisted % cd == 0 && getMana() >= COST && hasWater && !this.getWorld().isRemote) {
            Random rand = getWorld().rand;
            ItemStack stack;
            do {
                LootContext ctx = new LootContext.Builder((ServerWorld) world).build(LootParameterSets.EMPTY);
                List<ItemStack> stacks = ((ServerWorld) world).getServer().getLootTableManager()
                        .getLootTableFromLocation(LootTables.GAMEPLAY_FISHING).generate(ctx);
                if(times > 0){
                    stacks = ((ServerWorld) world).getServer().getLootTableManager()
                            .getLootTableFromLocation(LootTables.GAMEPLAY_FISHING_TREASURE).generate(ctx);
                }
                if (stacks.isEmpty()) {
                    return;
                } else {
                    Collections.shuffle(stacks);
                    stack = stacks.get(0);
                }
            } while (stack.isEmpty());

            int bound = RANGE * 2 + 1;
            ItemEntity entity = new ItemEntity(getWorld(), getEffectivePos().getX() - RANGE + rand.nextInt(bound) , getEffectivePos().getY() + 2, getEffectivePos().getZ() - RANGE + rand.nextInt(bound), stack);
            entity.setMotion(Vector3d.ZERO);

            if(!getWorld().isRemote)
                getWorld().addEntity(entity);
            addMana(-COST);
            sync();
        }
    }

    @Override
    public void writeToPacketNBT(CompoundNBT cmp) {
        super.writeToPacketNBT(cmp);
        cmp.putInt(TAG_TIME, times);
    }

    @Override
    public void readFromPacketNBT(CompoundNBT cmp) {
        super.readFromPacketNBT(cmp);
        times = cmp.getInt(TAG_TIME);
    }

    @Override
    public int getColor() {
        return 0x000000;
    }

    @Override
    public int getMaxMana() {
        return 1000;
    }

    @Override
    public boolean acceptsRedstone() {
        return true;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Square(getEffectivePos(), RANGE);
    }

}
