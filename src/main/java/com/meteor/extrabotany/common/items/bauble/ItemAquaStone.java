package com.meteor.extrabotany.common.items.bauble;

import com.meteor.extrabotany.common.core.EquipmentHandler;
import com.meteor.extrabotany.common.items.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vazkii.botania.api.mana.ManaDiscountEvent;

public class ItemAquaStone extends ItemBauble{

    public ItemAquaStone(Properties props) {
        super(props);
        MinecraftForge.EVENT_BUS.addListener(this::manaDiscount);
    }

    @SubscribeEvent
    public void manaDiscount(ManaDiscountEvent event){
        PlayerEntity player = event.getEntityPlayer();
        if(!EquipmentHandler.findOrEmpty(this, player).isEmpty() || !EquipmentHandler.findOrEmpty(ModItems.thecommunity, player).isEmpty()){
            event.setDiscount(event.getDiscount() + 0.1F);
        }
    }

    @Override
    public boolean canEquip(ItemStack stack, LivingEntity entity) {
        return EquipmentHandler.findOrEmpty(this, entity).isEmpty()
                && EquipmentHandler.findOrEmpty(ModItems.thecommunity, entity).isEmpty();
    }

}
