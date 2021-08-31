package com.meteor.extrabotany.common.items;

import com.meteor.extrabotany.api.items.BonusHelper;
import com.meteor.extrabotany.api.items.WeightCategory;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ItemRewardBag extends Item {

    List<WeightCategory> categoryList = new ArrayList<>();

    public ItemRewardBag(Properties prop, List<WeightCategory> categoryList) {
        super(prop);
        this.categoryList = categoryList;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
        super.addInformation(stack, world, tooltip, flags);
        DecimalFormat df = new DecimalFormat("0.00%");
        int sum = BonusHelper.sum(categoryList);
        for(WeightCategory category : categoryList){
            String percentage = df.format((float) category.getWeight() / sum);
            String stackname = new TranslationTextComponent(category.getCategory().getTranslationKey()).getString();
            int count = category.getCategory().getCount();
            TextFormatting color = (float) category.getWeight() / sum <= 0.01F ? TextFormatting.GOLD : TextFormatting.RESET;
            tooltip.add(new StringTextComponent(String.format("%s x%d %s", stackname, count, percentage)).mergeStyle(color));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand handIn) {
        ItemStack itemstack = player.getHeldItem(handIn);

        ItemStack reward = BonusHelper.rollItem(player, categoryList);

        if(!reward.isEmpty() && !worldIn.isRemote){
            ItemStack stack = reward.copy();
            worldIn.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT,
                    SoundCategory.PLAYERS, 0.5F, 0.4F / (worldIn.rand.nextFloat() * 0.4F + 0.8F));
            player.entityDropItem(stack).setNoPickupDelay();
            if (!player.abilities.isCreativeMode) {
                itemstack.shrink(1);
            }
            return ActionResult.resultSuccess(itemstack);
        }

        return ActionResult.resultFail(itemstack);
    }

}
