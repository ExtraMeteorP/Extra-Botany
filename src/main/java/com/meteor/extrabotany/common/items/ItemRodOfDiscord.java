package com.meteor.extrabotany.common.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import vazkii.botania.api.mana.ManaItemHandler;

public class ItemRodOfDiscord extends Item {

    private static final int MANA_PER_DAMAGE = 2000;

    public ItemRodOfDiscord(Properties properties) {
        super(properties.maxDamage(81));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand handIn) {
        ItemStack itemstack = player.getHeldItem(handIn);
        BlockRayTraceResult rtr = (BlockRayTraceResult) player.pick(64F, 1, false);
        if(rtr.getType() != RayTraceResult.Type.MISS && ManaItemHandler.instance().requestManaExactForTool(itemstack, player, MANA_PER_DAMAGE, true)){
            Vector3d end = rtr.getHitVec();
            player.setPositionAndUpdate(end.x, end.y+1, end.z);
            if(!worldIn.isRemote)
                worldIn.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1F, 3F);
            player.addPotionEffect(new EffectInstance(Effects.NAUSEA, 100));
            if(itemstack.getDamage() > 0){
                float health = Math.max(1F, player.getHealth() - player.getMaxHealth() / 6F);
                player.setHealth(health);
            }
            itemstack.setDamage(itemstack.getMaxDamage()-1);
        }
        return ActionResult.resultPass(itemstack);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
        if(!world.isRemote && stack.getDamage() > 0){
            stack.setDamage(stack.getDamage() - 1);
        }
    }

}
