package com.meteor.extrabotany.common.item.equipment.bauble;

import baubles.api.BaubleType;
import com.meteor.extrabotany.common.lib.LibItemsName;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;

import java.util.regex.Pattern;

public class ItemBottledFlame extends ItemBauble {

    private static final Pattern TORCH_PATTERN = Pattern.compile("(?:(?:(?:[A-Z-_.:]|^)torch)|(?:(?:[a-z-_.:]|^)Torch))(?:[A-Z-_.:]|$)");

    public ItemBottledFlame() {
        super(LibItemsName.BAUBLE_BOTTLEDFLAME);
    }

    @Override
    public void onWornTick(ItemStack stack, EntityLivingBase entity) {
        super.onWornTick(stack, entity);
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            World world = entity.world;
            BlockPos pos = entity.getPosition();
            if (world.getLightBrightness(pos) < 0.25F) {
                if (world.getBlockState(pos.add(0, -1, 0)).getBlock() == Blocks.AIR)
                    return;
                if (world.getBlockState(pos.add(0, -1, 0)).getBlock() instanceof BlockFluidClassic || world.getBlockState(pos).getBlock() instanceof BlockFluidClassic)
                    return;
                if (world.getBlockState(pos.add(0, -1, 0)).getMaterial() == Material.WATER || world.getBlockState(pos).getMaterial() == Material.WATER)
                    return;
                if (world.getBlockState(pos.add(0, -1, 0)).getMaterial() == Material.LAVA || world.getBlockState(pos).getMaterial() == Material.LAVA)
                    return;
                for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                    ItemStack stackAt = player.inventory.getStackInSlot(i);
                    if (!stackAt.isEmpty() && TORCH_PATTERN.matcher(stackAt.getItem().getUnlocalizedName()).find()) {
                        ItemStack saveHeldStack = player.getHeldItem(EnumHand.OFF_HAND);
                        player.setHeldItem(EnumHand.OFF_HAND, stackAt);
                        stackAt.getItem().onItemUse(player, world, pos, EnumHand.OFF_HAND, EnumFacing.DOWN, 0F, 0F, 0F);
                        player.setHeldItem(EnumHand.OFF_HAND, saveHeldStack);
                    }
                }
            }
        }
    }

    @Override
    public BaubleType getBaubleType(ItemStack arg0) {
        return BaubleType.CHARM;
    }

}
