package com.meteor.extrabotany.common.items.lens;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import vazkii.botania.api.internal.IManaBurst;
import vazkii.botania.api.mana.IManaBlock;
import vazkii.botania.common.core.handler.ConfigHandler;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.lens.ItemLens;
import vazkii.botania.common.item.lens.Lens;

public class LensSmelt extends Lens {

    @Override
    public boolean collideBurst(IManaBurst burst, RayTraceResult rtr, boolean isManaBlock, boolean dead, ItemStack stack) {
        ThrowableEntity entity = burst.entity();
        World world = entity.world;

        if (world.isRemote || rtr.getType() != RayTraceResult.Type.BLOCK) {
            return false;
        }

        BlockPos collidePos = ((BlockRayTraceResult) rtr).getPos();
        BlockState state = world.getBlockState(collidePos);
        Block block = state.getBlock();

        ItemStack composite = ((ItemLens) stack.getItem()).getCompositeLens(stack);
        boolean warp = !composite.isEmpty() && composite.getItem() == ModItems.lensWarp;

        int harvestLevel = ConfigHandler.COMMON.harvestLevelBore.get();
        TileEntity tile = world.getTileEntity(collidePos);

        float hardness = state.getBlockHardness(world, collidePos);
        int neededHarvestLevel = block.getHarvestLevel(state);
        int mana = burst.getMana();

        BlockPos source = burst.getBurstSourceBlockPos();
        if (!source.equals(collidePos)
                && !(tile instanceof IManaBlock)
                && neededHarvestLevel <= harvestLevel
                && hardness != -1 && hardness < 50F
                && (burst.isFake() || mana >= 24)) {
            if (!burst.hasAlreadyCollidedAt(collidePos)) {
                if (!burst.isFake()) {

                    IRecipe<?> irecipe = world.getRecipeManager().getRecipe(IRecipeType.SMELTING, new Inventory(new ItemStack(block)),world).orElse(null);

                    if(!irecipe.getRecipeOutput().isEmpty()) {

                        world.removeBlock(collidePos, false);
                        if (ConfigHandler.COMMON.blockBreakParticles.get()) {
                            world.playEvent(2001, collidePos, Block.getStateId(state));
                        }

                        boolean offBounds = source.getY() < 0;
                        boolean doWarp = warp && !offBounds;
                        BlockPos dropCoord = doWarp ? source : collidePos;
                        Block.spawnAsEntity(world, dropCoord, irecipe.getRecipeOutput().copy());

                        burst.setMana(mana - 40);
                    }
                }
            }

            dead = false;
        }

        return dead;
    }

}
