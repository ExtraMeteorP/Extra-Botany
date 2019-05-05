package com.meteor.extrabotany.common.item;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.lib.LibAdvancements;
import com.meteor.extrabotany.common.lib.LibItemsName;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ItemNightmareFuel extends ItemFoodMod {

    public ItemNightmareFuel() {
        super(0, 0, false, LibItemsName.NIGHTMAREFUEL);
    }

    public void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        player.inventory.addItemStackToInventory(new ItemStack(ModItems.spiritFuel));
        if (!player.isPotionActive(MobEffects.ABSORPTION)) {
            player.attackEntityFrom(DamageSource.MAGIC, 6F);
            player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 500, 1));
            player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 500, 1));
            player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 500, 1));
            player.addPotionEffect(new PotionEffect(MobEffects.UNLUCK, 500, 1));
        }
        ExtraBotanyAPI.unlockAdvancement(player, LibAdvancements.NIGHTMAREFUEL_EATING);
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack) {
        return 1000;
    }

}
