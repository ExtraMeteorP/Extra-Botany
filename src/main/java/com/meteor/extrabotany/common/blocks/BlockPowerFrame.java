package com.meteor.extrabotany.common.blocks;

import com.meteor.extrabotany.common.blocks.tile.TilePowerFrame;
import com.meteor.extrabotany.common.items.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.mana.IManaItem;
import vazkii.botania.common.block.BlockMod;
import vazkii.botania.common.block.tile.TileSimpleInventory;
import vazkii.botania.common.core.helper.InventoryHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockPowerFrame extends BlockMod implements ITileEntityProvider {

    public BlockPowerFrame(Properties builder) {
        super(builder);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (world.isRemote) {
            return ActionResultType.SUCCESS;
        }

        TilePowerFrame frame = (TilePowerFrame) world.getTileEntity(pos);
        ItemStack stack = player.getHeldItem(hand);

        if (player.isSneaking()) {
            InventoryHelper.withdrawFromInventory(frame, player);
            VanillaPacketDispatcher.dispatchTEToNearbyPlayers(frame);
            return ActionResultType.SUCCESS;
        }else if(!stack.isEmpty() && stack.getItem() instanceof IManaItem || stack.getItem() == ModItems.natureorb){
            boolean result = frame.addItem(player, stack, hand);
            VanillaPacketDispatcher.dispatchTEToNearbyPlayers(frame);
            return result ? ActionResultType.SUCCESS : ActionResultType.PASS;
        }


        return ActionResultType.PASS;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new TilePowerFrame();
    }

    @Override
    public void onReplaced(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof TileSimpleInventory) {
                net.minecraft.inventory.InventoryHelper.dropInventoryItems(world, pos, ((TileSimpleInventory) te).getItemHandler());
            }
            super.onReplaced(state, world, pos, newState, isMoving);
        }
    }

}
