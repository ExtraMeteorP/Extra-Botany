package com.meteor.extrabotany.common.blocks.tile;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.mana.IManaItem;
import vazkii.botania.common.block.tile.TileSimpleInventory;
import vazkii.botania.common.block.tile.mana.TilePool;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TilePowerFrame extends TileSimpleInventory implements ITickableTileEntity {

    public static final int TRANSFER_SPEED = 1000;

    public TilePowerFrame() {
        super(ModTiles.POWER_FRAME);
    }

    @Override
    protected Inventory createItemHandler() {
        return new Inventory(1) {
            @Override
            public int getInventoryStackLimit() {
                return 1;
            }

            @Override
            public boolean isItemValidForSlot(int slot, ItemStack stack) {
                return stack.getItem() instanceof IManaItem;
            }
        };
    }

    public boolean addItem(@Nullable PlayerEntity player, ItemStack stack, @Nullable Hand hand) {
        if(!(stack.getItem() instanceof IManaItem))
            return false;

        boolean did = false;

        if (getItemHandler().getStackInSlot(0).isEmpty()) {
            did = true;
            ItemStack stackToAdd = stack.copy();
            stackToAdd.setCount(1);
            getItemHandler().setInventorySlotContents(0, stackToAdd);

            if (player == null || !player.abilities.isCreativeMode) {
                stack.shrink(1);
            }
        }

        if (did) {
            VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
        }

        return true;
    }

    @Override
    public void tick() {

        int redstoneSignal = 0;
        for(Direction dir : Direction.values()) {
            int redstoneSide = this.getWorld().getRedstonePower(this.getPos().offset(dir), dir);
            redstoneSignal = Math.max(redstoneSignal, redstoneSide);
        }

        ItemStack stack = getItemHandler().getStackInSlot(0);
        if(!stack.isEmpty()) {
            if(stack.getItem() instanceof IManaItem) {
                IManaItem item = (IManaItem) stack.getItem();
                if (world.getTileEntity(pos.add(0, 1, 0)) instanceof TilePool) {
                    TilePool p = (TilePool) world.getTileEntity(pos.add(0, 1, 0));

                    if(redstoneSignal == 0) {
                        int manaToGet = Math.min(TRANSFER_SPEED, p.getCurrentMana());
                        int space = Math.max(0, item.getMaxMana(stack) - item.getMana(stack));
                        int current = Math.min(space, manaToGet);
                        p.receiveMana(-current);
                        item.addMana(stack, current);
                    }else{
                        int manaToGet = Math.min(TRANSFER_SPEED, item.getMana(stack));
                        int space = Math.max(0, p.manaCap - p.getCurrentMana());
                        int current = Math.min(space, manaToGet);
                        p.receiveMana(current);
                        item.addMana(stack, -current);
                    }
                }
            }
        }
    }

    @Nonnull
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

}
