package com.meteor.extrabotany.common.blocks.tile;

import com.meteor.extrabotany.client.handler.ClientTickHandler;
import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.items.bauble.ItemNatureOrb;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.LazyValue;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.mana.IManaItem;
import vazkii.botania.client.fx.WispParticleData;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.tile.TileSimpleInventory;
import vazkii.botania.common.block.tile.mana.TilePool;
import vazkii.botania.common.core.helper.Vector3;
import vazkii.botania.common.lib.ModTags;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.PatchouliAPI;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TilePowerFrame extends TileSimpleInventory implements ITickableTileEntity {

    public static final int TRANSFER_SPEED = 1000;

    public TilePowerFrame() {
        super(ModTiles.POWER_FRAME);
    }

    private static final String[][] PATTERN_ADV = new String[][] {
            {
                    "P_____P",
                    "_______",
                    "_______",
                    "_______",
                    "_______",
                    "_______",
                    "P_____P"
            },
            {
                    "M_____M",
                    "_______",
                    "_______",
                    "___0___",
                    "_______",
                    "_______",
                    "M_____M"
            }
    };

    public static final LazyValue<IMultiblock> MULTIBLOCK_ADV = new LazyValue<>(() -> PatchouliAPI.get().makeMultiblock(
            PATTERN_ADV,
            'P', ModBlocks.naturaPylon,
            '0', com.meteor.extrabotany.common.blocks.ModBlocks.powerframe,
            'M', ModBlocks.manaPool));

    public static final BlockPos[] POOL_LOCATIONS = {
            new BlockPos(3, 0, 3), new BlockPos(-3, 0, 3), new BlockPos(3, 0, -3), new BlockPos(-3, 0, -3)
    };

    @Override
    protected Inventory createItemHandler() {
        return new Inventory(1) {
            @Override
            public int getInventoryStackLimit() {
                return 1;
            }

            @Override
            public boolean isItemValidForSlot(int slot, ItemStack stack) {
                return stack.getItem() instanceof IManaItem || stack.getItem() == ModItems.natureorb;
            }
        };
    }

    public boolean addItem(@Nullable PlayerEntity player, ItemStack stack, @Nullable Hand hand) {
        if(!(stack.getItem() instanceof IManaItem) && stack.getItem() != ModItems.natureorb)
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

        boolean transfering = false;
        int ritual = 0;

        if (MULTIBLOCK_ADV.getValue().validate(world, getPos()) != null) {
            ritual = 1;
        }

        int speed = TRANSFER_SPEED * (1 + ritual);

        ItemStack stack = getItemHandler().getStackInSlot(0);
        if (!stack.isEmpty()) {
            if (stack.getItem() instanceof IManaItem) {
                IManaItem item = (IManaItem) stack.getItem();
                if (world.getTileEntity(pos.add(0, 1, 0)) instanceof TilePool) {
                    TilePool p = (TilePool) world.getTileEntity(pos.add(0, 1, 0));

                    if (redstoneSignal == 0) {
                        int manaToGet = Math.min(speed, p.getCurrentMana());
                        int space = Math.max(0, item.getMaxMana(stack) - item.getMana(stack));
                        int current = Math.min(space, manaToGet);
                        if(!world.isRemote) {
                            p.receiveMana(-current);
                            item.addMana(stack, current);
                        }
                        if (current > 0)
                            transfering = true;
                    } else {
                        int manaToGet = Math.min(speed, item.getMana(stack));
                        int space = Math.max(0, p.manaCap - p.getCurrentMana());
                        int current = Math.min(space, manaToGet);
                        if(!world.isRemote) {
                            p.receiveMana(current);
                            item.addMana(stack, -current);
                        }
                        if (current > 0)
                            transfering = true;
                    }

                }
            } else if (stack.getItem() == ModItems.natureorb && ritual > 0) {
                int xp = (int) Math.pow(4, ritual);
                ItemNatureOrb orb = (ItemNatureOrb) stack.getItem();
                if(!world.isRemote)
                    orb.addXP(stack, xp);
                if(orb.getXP(stack) < orb.getMaxXP(stack))
                    transfering = true;

                for (BlockPos offset : POOL_LOCATIONS) {
                    TileEntity tile = world.getTileEntity(pos.add(offset));
                    if (tile instanceof TilePool) {
                        TilePool pool = (TilePool) tile;
                        if (pool.getCurrentMana() >= 10) {
                            pool.receiveMana(-10);
                            orb.addXP(stack, 2);
                        }
                    }
                }
            }
        }


        if(world.isRemote && ritual >= 1 && transfering){
            Vector3 pos = Vector3.fromBlockPos(getPos()).add(new Vector3(0, 0.5, 0));
            for (BlockPos arr : POOL_LOCATIONS) {
                Vector3 pylonPos = new Vector3(getPos().getX() + arr.getX(), getPos().getY() + arr.getY() + 1.2F, getPos().getZ() + arr.getZ());
                double worldTime = ClientTickHandler.ticksInGame;
                worldTime /= 5;

                float rad = 0.75F + (float) Math.random() * 0.05F;
                double xp = pylonPos.x + 0.5 + Math.cos(worldTime) * rad;
                double zp = pylonPos.z + 0.5 + Math.sin(worldTime) * rad;

                Vector3 partPos = new Vector3(xp, pylonPos.y, zp);
                Vector3 mot = pos.subtract(partPos).multiply(0.04);

                float r = (float) Math.random() * 0.3F;
                float g = 0.75F + (float) Math.random() * 0.2F;
                float b = (float) Math.random() * 0.3F;

                WispParticleData data = WispParticleData.wisp(0.25F + (float) Math.random() * 0.1F, r, g, b, 1);
                world.addParticle(data, partPos.x, partPos.y, partPos.z, 0, -(-0.075F - (float) Math.random() * 0.015F), 0);
                WispParticleData data1 = WispParticleData.wisp(0.4F, r, g, b);
                world.addParticle(data1, partPos.x, partPos.y, partPos.z, (float) mot.x, (float) mot.y, (float) mot.z);
            }
        }
    }

    @Nonnull
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

}
