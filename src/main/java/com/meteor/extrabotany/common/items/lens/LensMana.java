package com.meteor.extrabotany.common.items.lens;

import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import vazkii.botania.api.internal.IManaBurst;
import vazkii.botania.api.mana.BurstProperties;
import vazkii.botania.api.recipe.IManaInfusionRecipe;
import vazkii.botania.common.crafting.ModRecipeTypes;
import vazkii.botania.common.item.lens.Lens;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LensMana extends Lens {

    @Override
    public void apply(ItemStack stack, BurstProperties props) {
        props.maxMana = 1000;
        props.motionModifier *= 0.5F;
        props.manaLossPerTick *= 2F;
    }

    @Override
    public void updateBurst(IManaBurst burst, ItemStack stack) {
        ThrowableEntity entity = burst.entity();
        if (entity.world.isRemote)
            return;
        int mana = burst.getMana();
        BlockState state = entity.world.getBlockState(burst.getBurstSourceBlockPos().add(0, -1, 0));
        AxisAlignedBB axis = new AxisAlignedBB(entity.getPosX(), entity.getPosY(), entity.getPosZ(), entity.lastTickPosX,
                entity.lastTickPosY, entity.lastTickPosZ).grow(1);
        List<ItemEntity> entities = entity.world.getEntitiesWithinAABB(ItemEntity.class, axis);
        if(!burst.isFake())
            for (ItemEntity items : entities) {
                if (items.cannotPickup())
                    continue;
                ItemStack itemstack = items.getItem();
                IManaInfusionRecipe recipe = getMatchingRecipe(entity.world, itemstack, state);
                if (recipe != null) {
                    int manaToConsume = recipe.getManaToConsume();
                    if (mana >= manaToConsume) {
                        burst.setMana((int) (mana - manaToConsume));
                        itemstack.shrink(1);

                        ItemStack output = recipe.getRecipeOutput().copy();
                        ItemEntity outputItem = new ItemEntity(entity.world, items.getPosX(), items.getPosY()+0.5, items.getPosZ() + 0.5, output);
                        outputItem.setPickupDelay(50);
                        entity.world.addEntity(outputItem);
                    }
                }
            }
    }

    public static List<IManaInfusionRecipe> manaInfusionRecipes(World world) {
        return ModRecipeTypes.getRecipes(world, ModRecipeTypes.MANA_INFUSION_TYPE).values().stream()
                .filter(r -> r instanceof IManaInfusionRecipe)
                .map(r -> (IManaInfusionRecipe) r)
                .collect(Collectors.toList());
    }

    public IManaInfusionRecipe getMatchingRecipe(World world, @Nonnull ItemStack stack, @Nonnull BlockState state) {
        List<IManaInfusionRecipe> matchingNonCatRecipes = new ArrayList<>();
        List<IManaInfusionRecipe> matchingCatRecipes = new ArrayList<>();

        for (IManaInfusionRecipe recipe : manaInfusionRecipes(world)) {
            if (recipe.matches(stack)) {
                if (recipe.getRecipeCatalyst() == null) {
                    matchingNonCatRecipes.add(recipe);
                } else if (recipe.getRecipeCatalyst().test(state)) {
                    matchingCatRecipes.add(recipe);
                }
            }
        }

        // Recipes with matching catalyst take priority above recipes with no catalyst specified
        return !matchingCatRecipes.isEmpty() ? matchingCatRecipes.get(0) : !matchingNonCatRecipes.isEmpty() ? matchingNonCatRecipes.get(0) : null;
    }
}
