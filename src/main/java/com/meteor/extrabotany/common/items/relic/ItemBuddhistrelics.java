package com.meteor.extrabotany.common.items.relic;

import com.meteor.extrabotany.common.items.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.ItemStackHandler;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.relic.ItemRelic;

public class ItemBuddhistrelics extends ItemRelic {

    public static final String TAG_MORPHING = "buddhist:morphing";
    public static final String TAG_DATA = "buddhist:data";
    public static final String TAG_HASDATA = "buddhist:hasdata";
    public static final int MANA_PER_DAMAGE = 4;

    public ItemBuddhistrelics(Properties props) {
        super(props);
        MinecraftForge.EVENT_BUS.addListener(this::onItemUpdate);
    }

    @SubscribeEvent
    public void onItemUpdate(LivingEvent.LivingUpdateEvent event){
        if (event.getEntity() instanceof PlayerEntity && !event.getEntityLiving().world.isRemote) {
            final PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            for(int i = 0; i < player.inventory.getInventoryStackLimit(); i++){
                final ItemStack stack = player.inventory.getStackInSlot(i);
                if(ItemNBTHelper.getBoolean(stack, TAG_MORPHING, false)){
                    if(ManaItemHandler.instance().requestManaExact(stack, player, MANA_PER_DAMAGE, false)){
                        ManaItemHandler.instance().requestManaExact(stack, player, MANA_PER_DAMAGE, true);
                    }else{
                        ItemStack budd = expired(stack);
                        if(!budd.isEmpty()){
                            player.inventory.setInventorySlotContents(i, budd);
                        }
                    }
                }
            }
        }
    }

    public static void relicInit(ItemStack stack){
        if(!ItemNBTHelper.getBoolean(stack, TAG_HASDATA, false)){
            ItemStackHandler handler = new ItemStackHandler(5);
            handler.setStackInSlot(0, new ItemStack(ModItems.excaliber));
            handler.setStackInSlot(1, new ItemStack(ModItems.infinitewine));
            handler.setStackInSlot(2, new ItemStack(ModItems.failnaught));
            handler.setStackInSlot(3, new ItemStack(vazkii.botania.common.item.ModItems.infiniteFruit));
            handler.setStackInSlot(4, new ItemStack(vazkii.botania.common.item.ModItems.kingKey));
            CompoundNBT data = handler.serializeNBT();
            ItemNBTHelper.setCompound(stack, TAG_DATA, data);
            ItemNBTHelper.setBoolean(stack, TAG_HASDATA, true);
        }
    }

    public static ItemStack expired(ItemStack morphstack){
        if(ItemNBTHelper.getBoolean(morphstack, TAG_MORPHING, false)){

            CompoundNBT data = ItemNBTHelper.getCompound(morphstack, TAG_DATA, false);
            ItemStackHandler handler = new ItemStackHandler(5);
            handler.deserializeNBT(data);
            int id = 0;
            for(int i = 0; i < 5; i++){
                ItemStack stack = handler.getStackInSlot(i).copy();
                if(morphstack.getItem() == stack.getItem()){
                    id = i;
                    break;
                }
            }

            ItemStack budd = new ItemStack(ModItems.buddhistrelics);
            ItemStack copy = morphstack.copy();
            copy.getOrCreateTag().remove(TAG_DATA);
            handler.setStackInSlot(id, copy);
            ItemNBTHelper.setCompound(budd, TAG_DATA, handler.serializeNBT());
            ItemNBTHelper.setBoolean(budd, TAG_HASDATA, true);
            return budd.copy();
        }
        return ItemStack.EMPTY;
    }

    public static ItemStack relicShift(ItemStack heldstack){
        if(heldstack.getItem() == ModItems.buddhistrelics){
            relicInit(heldstack);
            CompoundNBT data = ItemNBTHelper.getCompound(heldstack, TAG_DATA, false);
            ItemStackHandler handler = new ItemStackHandler(5);
            handler.deserializeNBT(data);
            ItemStack stack = handler.getStackInSlot(0).copy();
            ItemNBTHelper.setBoolean(stack, TAG_MORPHING, true);
            ItemNBTHelper.setCompound(stack, TAG_DATA, data);
            return stack.copy();
        }else if(ItemNBTHelper.getBoolean(heldstack, TAG_MORPHING, false)){
            CompoundNBT data = ItemNBTHelper.getCompound(heldstack, TAG_DATA, false);
            ItemStackHandler handler = new ItemStackHandler(5);
            handler.deserializeNBT(data);
            int id = 0;
            for(int i = 0; i < 5; i++){
                ItemStack stack = handler.getStackInSlot(i).copy();
                if(heldstack.getItem() == stack.getItem()){
                    id = i;
                    break;
                }
            }
            if(id == 4){
                ItemStack budd = new ItemStack(ModItems.buddhistrelics);
                ItemStack copy = heldstack.copy();
                copy.getOrCreateTag().remove(TAG_DATA);
                handler.setStackInSlot(4, copy);
                ItemNBTHelper.setCompound(budd, TAG_DATA, handler.serializeNBT());
                ItemNBTHelper.setBoolean(budd, TAG_HASDATA, true);
                return budd.copy();
            }
            ItemStack morph = handler.getStackInSlot(id+1);
            ItemStack copy = heldstack.copy();
            copy.getOrCreateTag().remove(TAG_DATA);
            handler.setStackInSlot(id, copy);
            ItemNBTHelper.setBoolean(morph, TAG_MORPHING, true);
            ItemNBTHelper.setCompound(morph, TAG_DATA, handler.serializeNBT());
            return morph.copy();
        }
        return ItemStack.EMPTY;
    }

}
