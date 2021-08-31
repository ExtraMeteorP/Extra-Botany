package com.meteor.extrabotany.common.blocks.tile;

import com.google.common.base.Predicates;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.mana.IManaReceiver;
import vazkii.botania.api.mana.IThrottledPacket;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import vazkii.botania.api.mana.spark.ISparkEntity;
import vazkii.botania.common.block.tile.TileMod;
import vazkii.botania.common.block.tile.mana.TilePool;

import java.util.List;

public class TileManaBuffer extends TileMod implements IManaReceiver, ISparkAttachable, ITickableTileEntity, IThrottledPacket {

    private static final BlockPos[] POOL_LOCATIONS = { new BlockPos(1, 0, 0), new BlockPos(0, 0, 1),
            new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, -1, 0) };

    public static final int MAX_MANA = 64000000;
    public static final int TRANSFER_SPEED = 1000;

    private static final String TAG_MANA = "mana";

    private int mana;

    private int ticks = 0;
    private boolean sendPacket = false;

    public TileManaBuffer() {
        super(ModTiles.MANA_BUFFER);
    }

    @Override
    public void tick() {
        if (sendPacket && ticks % 10 == 0) {
            VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
            sendPacket = false;
        }

        for (BlockPos o : POOL_LOCATIONS)
            if (world.getTileEntity(pos.add(o)) instanceof TilePool) {
                TilePool p = (TilePool) world.getTileEntity(pos.add(o));
                int manaToGet = Math.min(TRANSFER_SPEED, p.getCurrentMana());
                int space = Math.max(0, MAX_MANA - getCurrentMana());
                int current = Math.min(space, manaToGet);
                p.receiveMana(-current);
                receiveMana(current);
            } else if (world.getTileEntity(pos.add(o)) instanceof TileManaBuffer) {
                TileManaBuffer p = (TileManaBuffer) world.getTileEntity(pos.add(o));
                int manaToGet = Math.min(TRANSFER_SPEED, p.getCurrentMana());
                int space = Math.max(0, MAX_MANA - getCurrentMana());
                int current = Math.min(space, manaToGet);
                p.receiveMana(-current);
                receiveMana(current);
            }

        if (world.getTileEntity(pos.add(0, 1, 0)) instanceof TilePool) {
            TilePool p = (TilePool) world.getTileEntity(pos.add(0, 1, 0));
            int manaToGet = Math.min(TRANSFER_SPEED, getCurrentMana());
            int space = Math.max(0, p.manaCap - p.getCurrentMana());
            int current = Math.min(space, manaToGet);
            p.receiveMana(current);
            receiveMana(-current);
        } else if (world.getTileEntity(pos.add(0, 1, 0)) instanceof TileManaBuffer) {
            TileManaBuffer p = (TileManaBuffer) world.getTileEntity(pos.add(0, 1, 0));
            int manaToGet = Math.min(TRANSFER_SPEED, getCurrentMana());
            int space = Math.max(0, p.MAX_MANA - p.getCurrentMana());
            int current = Math.min(space, manaToGet);
            p.receiveMana(current);
            receiveMana(-current);
        }

        ticks++;
    }

    @Override
    public void writePacketNBT(CompoundNBT cmp) {
        cmp.putInt(TAG_MANA, mana);
    }

    @Override
    public void readPacketNBT(CompoundNBT cmp) {
        mana = cmp.getInt(TAG_MANA);
    }

    @Override
    public boolean canAttachSpark(ItemStack stack) {
        return true;
    }

    @Override
    public void attachSpark(ISparkEntity entity) {

    }

    @Override
    public int getAvailableSpaceForMana() {
        int space = Math.max(0, MAX_MANA - getCurrentMana());
        if (space > 0) {
            return space;
        } else {
            return 0;
        }
    }

    @Override
    public ISparkEntity getAttachedSpark() {
        List<Entity> sparks = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos.up(), pos.up().add(1, 1, 1)), Predicates.instanceOf(ISparkEntity.class));
        if (sparks.size() == 1) {
            Entity e = sparks.get(0);
            return (ISparkEntity) e;
        }

        return null;
    }

    @Override
    public boolean areIncomingTranfersDone() {
        return false;
    }

    @Override
    public boolean isFull() {
        return getCurrentMana() >= MAX_MANA;
    }

    @Override
    public void receiveMana(int mana) {
        int old = this.mana;
        this.mana = Math.max(0, Math.min(getCurrentMana() + mana, MAX_MANA));
        if (old != this.mana) {
            markDirty();
            markDispatchable();
        }
    }

    @Override
    public boolean canReceiveManaFromBursts() {
        return true;
    }

    @Override
    public int getCurrentMana() {
        return mana;
    }

    @Override
    public void markDispatchable() {
        sendPacket = true;
    }
}
