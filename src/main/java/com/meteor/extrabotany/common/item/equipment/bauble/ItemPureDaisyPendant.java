package com.meteor.extrabotany.common.item.equipment.bauble;

import baubles.api.BaubleType;
import com.meteor.extrabotany.client.core.handler.MiscellaneousIcons;
import com.meteor.extrabotany.common.lib.LibItemsName;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.item.ICosmeticBauble;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.api.recipe.RecipePureDaisy;
import vazkii.botania.api.subtile.SubTileEntity;
import vazkii.botania.client.core.helper.IconHelper;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import java.util.List;

public class ItemPureDaisyPendant extends ItemBauble implements IManaUsingItem, ICosmeticBauble {

    public static final String TAG_USE = "usecount";
    public static final String TAG_CD = "cooldown";
    private ItemStack renderStack;

    public ItemPureDaisyPendant() {
        super(LibItemsName.BAUBLE_PUREDAISYPENDANT);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isAirBlock(pos)) {
            world.profiler.startSection("findRecipe");
            RecipePureDaisy recipe = findRecipe(world, pos);
            world.profiler.endSection();

            ItemStack stack = player.getHeldItem(hand);

            if (recipe != null && getCD(stack) == 0) {
                if (recipe.set(world, pos, new SubTileEntity()) && ManaItemHandler.requestManaExactForTool(player.getHeldItem(hand), player, 50, true))

                    world.addBlockEvent(pos, recipe.getOutputState().getBlock(), 1, 1);
                world.playEvent(2001, pos, Block.getStateId(recipe.getOutputState()));
                setCount(stack, getCount(stack) + 1);
                if (getCount(stack) % 64 == 0)
                    setCD(stack, 3000);
            }
        }
        return EnumActionResult.PASS;
    }

    private RecipePureDaisy findRecipe(World world, BlockPos coords) {
        IBlockState state = world.getBlockState(coords);

        for (RecipePureDaisy recipe : BotaniaAPI.pureDaisyRecipes) {
            if (recipe.matches(world, coords, new SubTileEntity(), state)) {
                return recipe;
            }
        }

        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addHiddenTooltip(ItemStack stack, World world, List<String> stacks, ITooltipFlag flags) {
        addStringToTooltip("Cooldown:" + getCD(stack) + "ticks", stacks);
    }

    @Override
    public void onWornTick(ItemStack stack, EntityLivingBase player) {
        super.onWornTick(stack, player);
        if (getCD(stack) > 0)
            setCD(stack, getCD(stack) - 1);
        if (player.getEntityWorld().isDaytime() && player.getEntityWorld().getWorldTime() % 120 == 0)
            player.heal(1F);
    }

    private boolean isOreDict(ItemStack stack, String entry) {
        if (stack.isEmpty())
            return false;

        for (ItemStack ostack : OreDictionary.getOres(entry, false)) {
            if (OreDictionary.itemMatches(ostack, stack, false)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public BaubleType getBaubleType(ItemStack arg0) {
        return BaubleType.AMULET;
    }

    @Override
    public boolean usesMana(ItemStack arg0) {
        return true;
    }

    public void setCD(ItemStack stack, int i) {
        ItemNBTHelper.setInt(stack, TAG_CD, i);
    }

    public int getCD(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, TAG_CD, 0);
    }

    public void setCount(ItemStack stack, int i) {
        ItemNBTHelper.setInt(stack, TAG_USE, i);
    }

    public int getCount(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, TAG_USE, 0);
    }

    @Override
    public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        renderStack = stack;
        if (type == RenderType.BODY) {
            Helper.rotateIfSneaking(player);
            boolean armor = !player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).isEmpty();
            GlStateManager.rotate(180F, 1F, 0F, 0F);
            GlStateManager.translate(-0.3F, -0.57F, armor ? 0.2F : 0.15F);
            GlStateManager.scale(0.6F, 0.6F, 0.6F);

            TextureAtlasSprite gemIcon = MiscellaneousIcons.INSTANCE.puredaisyPendantIcon;
            float f = gemIcon.getMinU();
            float f1 = gemIcon.getMaxU();
            float f2 = gemIcon.getMinV();
            float f3 = gemIcon.getMaxV();
            IconHelper.renderIconIn3D(Tessellator.getInstance(), f1, f2, f, f3, gemIcon.getIconWidth(), gemIcon.getIconHeight(), 1F / 32F);
        }
    }

}
