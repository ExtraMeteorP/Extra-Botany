package com.meteor.extrabotany.common.core;

import com.meteor.extrabotany.ExtraBotany;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.function.Predicate;

public abstract class EquipmentHandler {

    public static EquipmentHandler instance;

    public static void init() {
        if (ExtraBotany.curiosLoaded) {
            instance = new CurioIntegration();
            FMLJavaModLoadingContext.get().getModEventBus().addListener(CurioIntegration::sendImc);
        } else {
            InventoryEquipmentHandler handler = new InventoryEquipmentHandler();
            instance = handler;
        }
    }

    public static LazyOptional<IItemHandlerModifiable> getAllWorn(LivingEntity living) {
        return instance.getAllWornItems(living);
    }

    public static ItemStack findOrEmpty(Item item, LivingEntity living) {
        return instance.findItem(item, living);
    }

    public static ItemStack findOrEmpty(Predicate<ItemStack> pred, LivingEntity living) {
        return instance.findItem(pred, living);
    }

    public static ICapabilityProvider initBaubleCap(ItemStack stack) {
        if (instance != null)
        {
            return instance.initCap(stack);
        }
        return null;
    }

    protected abstract LazyOptional<IItemHandlerModifiable> getAllWornItems(LivingEntity living);

    protected abstract ItemStack findItem(Item item, LivingEntity living);

    protected abstract ItemStack findItem(Predicate<ItemStack> pred, LivingEntity living);

    protected abstract ICapabilityProvider initCap(ItemStack stack);

    static class InventoryEquipmentHandler extends EquipmentHandler {

        @Override
        protected LazyOptional<IItemHandlerModifiable> getAllWornItems(LivingEntity living) {
            return LazyOptional.empty();
        }

        @Override
        protected ItemStack findItem(Item item, LivingEntity living) {
            return ItemStack.EMPTY;
        }

        @Override
        protected ItemStack findItem(Predicate<ItemStack> pred, LivingEntity living) {
            return ItemStack.EMPTY;
        }

        @Override
        protected ICapabilityProvider initCap(ItemStack stack) {
            return null;
        }
    }

}
