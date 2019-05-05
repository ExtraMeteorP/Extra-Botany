package com.meteor.extrabotany.common.world;

import net.minecraft.block.Block;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.ModItems;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.ToIntFunction;

public class Trades {

    public static void addTradeForBotanist(VillagerCareer career) {
        career.addTrade(1, new GenericTrade(offer(Items.EMERALD), offer(new ItemStack(ModItems.manaResource, 1, 16), 14, 28)));
        career.addTrade(1, new GenericTrade(offer(Items.EMERALD), offer(ModBlocks.livingrock, 18, 24)));
        career.addTrade(1, new GenericTrade(offer(Items.EMERALD), offer(com.meteor.extrabotany.common.item.ModItems.candybag, 2)));

        career.addTrade(1, new GenericTrade(offer(ModItems.manaResource, 8, 12), offer(Items.EMERALD, 3, 5)));
        career.addTrade(1, new GenericTrade(offer(new ItemStack(ModItems.manaResource, 1, 2)), offer(Items.EMERALD, 4, 7)));
        career.addTrade(1, new GenericTrade(offer(new ItemStack(ModItems.manaResource, 1, 1)), offer(Items.EMERALD, 3, 5)));
        career.addTrade(1, new GenericTrade(offer(new ItemStack(ModItems.blackLotus, 1, 1), 1, 2), offer(Items.EMERALD, 2)));
        career.addTrade(1, new GenericTrade(offer(new ItemStack(ModItems.spark), 6, 9), offer(Items.EMERALD, 2), offer(Items.GOLD_NUGGET, 3, 5)));
        career.addTrade(1, new GenericTrade(offer(new ItemStack(ModItems.manaResource, 1, 4)), offer(Items.EMERALD, 15, 22)));
    }

    public static Offer offer(@Nullable Object obj) {
        return offer(obj, 1);
    }

    public static Offer offer(@Nullable Object obj, int amount) {
        return offer(obj, amount, amount);
    }

    public static Offer offer(@Nullable Object obj, int min, int max) {
        return new Offer(obj, min, max);
    }

    private static class Offer {

        @Nullable
        public final Object obj;
        public final int min, max;

        public Offer(@Nullable Object obj, int min, int max) {
            this.obj = obj;
            this.min = min;
            this.max = max;
        }
    }

    private static class GenericTrade implements EntityVillager.ITradeList {

        private static final BiFunction<ItemStack, Random, ItemStack> DEFAULT_ENCHANTER = (stack, rand) -> stack;
        private static final ToIntFunction<GenericTrade> USE_SETTER = (t) -> 7;

        private final Offer sale;
        private final Offer[] offers;
        private ToIntFunction<GenericTrade> maxUseSetter;
        private BiFunction<ItemStack, Random, ItemStack> enchanter;

        public GenericTrade(Offer sale, Offer... offers) {
            this.sale = sale;
            this.offers = offers;
            this.maxUseSetter = USE_SETTER;
            this.enchanter = DEFAULT_ENCHANTER;
        }

        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random rand) {
            if (offers.length <= 0 || sale.obj == null) {
                return;
            }
            for (Offer offer : offers) {
                if (offer.obj == null) {
                    return;
                }
            }
            ItemStack sellStack = prepareStack(rand, sale);
            ItemStack buyStack1 = prepareStack(rand, offers[0]);
            ItemStack buyStack2 = ItemStack.EMPTY;
            if (offers.length >= 2) {
                buyStack2 = prepareStack(rand, offers[1]);
            }
            if (!sellStack.isEmpty() && !buyStack1.isEmpty()) {
                recipeList.add(new MerchantRecipe(buyStack1, buyStack2, enchanter.apply(sellStack, rand), 0, maxUseSetter.applyAsInt(this)));
            }
        }

        private ItemStack prepareStack(Random rand, Offer offer) throws IllegalArgumentException {
            if (offer.obj instanceof ItemStack) {
                ItemStack stack = (ItemStack) offer.obj;
                stack.setCount(stackSize(rand, offer));
                return stack;
            }
            if (offer.obj instanceof Item) {
                return new ItemStack((Item) offer.obj, stackSize(rand, offer));
            }
            if (offer.obj instanceof Block) {
                return new ItemStack((Block) offer.obj, stackSize(rand, offer));
            }
            return ItemStack.EMPTY;
        }

        GenericTrade setUse(ToIntFunction<GenericTrade> f) {
            this.maxUseSetter = f;
            return this;
        }

        GenericTrade setEnchanter(BiFunction<ItemStack, Random, ItemStack> enchanter) {
            this.enchanter = enchanter;
            return this;
        }

        private int stackSize(Random rand, Offer offer) {
            return MathHelper.getInt(rand, offer.min, offer.max);
        }
    }

}
