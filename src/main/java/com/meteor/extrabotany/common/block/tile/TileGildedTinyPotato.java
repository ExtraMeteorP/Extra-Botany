package com.meteor.extrabotany.common.block.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.common.block.tile.TileMod;

public class TileGildedTinyPotato extends TileMod implements ITickable {

    private static final AxisAlignedBB AABB = new AxisAlignedBB(0.375, 0, 0.375, 0.625, 0.375, 0.625);

    private static final String TAG_HUNGER = "hunger";
    private static final String TAG_LOVE = "love";
    private static final String TAG_ISTOUCHED = "istouched";
    private static final String TAG_NAME = "name";

    public int hunger, love = 0;
    public int touchTicks = 0;
    public String name = "";

    public int jumpTicks = 0;
    public int hungerTicks = 0;

    @Override
    public void update() {
        if (world.rand.nextInt(100) == 0 && hunger > 0) {
            jump();
            hunger = Math.max(0, hunger - 1);
        }
        if (hunger == 0)
            hungerTicks++;

        if (world.rand.nextInt(100) == 0 && hungerTicks > 6000)
            love = Math.max(0, love - 1);

        if (jumpTicks > 0)
            jumpTicks--;

        if (touchTicks > 0)
            touchTicks--;
    }

    public void eat(ItemStack stack) {
        if (stack.getItem() instanceof ItemFood && hunger < 100) {
            ItemFood food = (ItemFood) stack.getItem();
            int recover = Math.min(food.getHealAmount(stack) * 2, 100 - hunger);
            hunger += recover;
            love = Math.min(100, love + (int) (recover / 4));
            stack.shrink(1);
            world.spawnParticle(EnumParticleTypes.HEART, pos.getX() + AABB.minX + Math.random() * (AABB.maxX - AABB.minX), pos.getY() + AABB.maxY, pos.getZ() + AABB.minZ + Math.random() * (AABB.maxZ - AABB.minZ), 0, 0, 0);
        }
    }

    public void play(ItemStack stack, EntityPlayer player) {
        if (love > 80 && touchTicks == 0) {

        }
    }

    public void touch() {
        jump();
        if (touchTicks == 0) {
            touchTicks = 24000;
            love = Math.min(100, love + 5);
        }
    }

    public void jump() {
        if (jumpTicks == 0)
            jumpTicks = 20;
        if (Math.random() < 0.25F)
            hunger = Math.max(0, hunger - 1);
    }

    @Override
    public void markDirty() {
        super.markDirty();
        VanillaPacketDispatcher.dispatchTEToNearbyPlayers(world, pos);
    }

    @Override
    public void writePacketNBT(NBTTagCompound cmp) {
        super.writePacketNBT(cmp);
        cmp.setInteger(TAG_HUNGER, hunger);
        cmp.setInteger(TAG_LOVE, love);
        cmp.setInteger(TAG_ISTOUCHED, touchTicks);
        cmp.setString(TAG_NAME, name);
    }

    @Override
    public void readPacketNBT(NBTTagCompound cmp) {
        super.readPacketNBT(cmp);
        hunger = cmp.getInteger(TAG_HUNGER);
        love = cmp.getInteger(TAG_LOVE);
        touchTicks = cmp.getInteger(TAG_ISTOUCHED);
        name = cmp.getString(TAG_NAME);
    }

}
