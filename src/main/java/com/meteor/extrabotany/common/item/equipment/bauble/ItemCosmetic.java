package com.meteor.extrabotany.common.item.equipment.bauble;

import baubles.api.BaubleType;
import com.meteor.extrabotany.common.lib.LibItemsName;
import com.meteor.extrabotany.common.lib.LibMisc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.item.ICosmeticBauble;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemCosmetic extends ItemBauble implements ICosmeticBauble {

    final int types = Variants.values().length;
    private ItemStack renderStack;

    public ItemCosmetic() {
        super(LibItemsName.BAUBLE_COSMETIC);
        setHasSubtypes(true);
        renderStack = new ItemStack(this);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addHiddenTooltip(ItemStack stack, World world, List<String> stacks, ITooltipFlag flags) {
        addStringToTooltip(I18n.format(stack.getItemDamage() == 8 || stack.getItemDamage() == 9 ? "Creative Only" : "botaniamisc.cosmeticBauble"), stacks);
        super.addHiddenTooltip(stack, world, stacks, flags);
    }

    @Override
    public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        renderStack = stack;

        if (stack.getItemDamage() >= types || stack.getItemDamage() < 0) return;
        Variants variant = Variants.values()[stack.getItemDamage()];

        if (type == RenderType.HEAD) {
            Helper.translateToHeadLevel(player);
            Helper.translateToFace();
            Helper.defaultTransforms();
            switch (variant) {
                case MASK:
                    scale(1.25F);
                    GlStateManager.translate(0F, 0.025F, 0.01F);
                    renderItem();
                    break;
                case PYLON:
                    scale(0.8F);
                    GlStateManager.translate(0F, 1.2F, 0.3F);
                    renderItem();
                    break;
                case GOGGLE_JUNGLE:
                    scale(1.15F);
                    GlStateManager.translate(0F, -0.065F, 0.045F);
                    renderItem();
                    break;
                case GOGGLE_OCEAN:
                    scale(1.15F);
                    GlStateManager.translate(0F, -0.065F, 0.045F);
                    renderItem();
                    break;
                case GOGGLE_SNOWFIELD:
                    scale(1.15F);
                    GlStateManager.translate(0F, -0.065F, 0.045F);
                    renderItem();
                    break;
                case GOGGLE_STANDARD:
                    scale(1.15F);
                    GlStateManager.translate(0F, -0.065F, 0.045F);
                    renderItem();
                    break;
                case BLACKGLASSES:
                    scale(1.15F);
                    GlStateManager.translate(0F, -0.065F, 0.045F);
                    renderItem();
                    break;
                case QUESTION:
                    scale(0.75F);

                    GlStateManager.pushMatrix();
                    GlStateManager.translate(0F, 1.2F, 0.3F);
                    GlStateManager.rotate(180F, 0F, 1F, 0F);
                    renderItem();
                    GlStateManager.popMatrix();

                    GlStateManager.pushMatrix();
                    GlStateManager.translate(0F, 1.2F, 0.3F);
                    GlStateManager.rotate(180F, 0F, 1F, 0F);
                    GlStateManager.translate(-0.65F, 0F, 0F);
                    GlStateManager.rotate(35F, 0F, 0F, 1F);
                    renderItem();
                    GlStateManager.popMatrix();

                    GlStateManager.pushMatrix();
                    GlStateManager.translate(0F, 1.2F, 0.3F);
                    GlStateManager.rotate(180F, 0F, 1F, 0F);
                    GlStateManager.translate(0.65F, 0F, 0F);
                    GlStateManager.rotate(-35F, 0F, 0F, 1F);
                    renderItem();
                    GlStateManager.popMatrix();

                    break;
                case LEMON:
                    scale(0.50F);
                    GlStateManager.translate(-0.40F, -0.1F, 0F);
                    renderItem();
                    break;
                case THUGLIFE:
                    scale(1.35F);
                    GlStateManager.translate(0F, -0.065F, 0.045F);
                    renderItem();
                    break;
                default:
                    break;
            }
        }
    }

    public void scale(float f) {
        GlStateManager.scale(f, f, f);
    }

    public void renderItem() {
        GlStateManager.pushMatrix();
        Minecraft.getMinecraft().getRenderItem().renderItem(renderStack, ItemCameraTransforms.TransformType.NONE);
        GlStateManager.popMatrix();
    }

    @Nonnull
    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return "item." + LibItemsName.COSMETIC_NAMES[Math.min(types - 1, par1ItemStack.getItemDamage())];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModels() {
        for (int i = 0; i < LibItemsName.COSMETIC_NAMES.length; i++) {
            if (!"UNUSED".equals(LibItemsName.COSMETIC_NAMES[i])) {
                ModelLoader.setCustomModelResourceLocation(
                        this, i,
                        new ModelResourceLocation(LibMisc.MOD_ID + ":" + LibItemsName.COSMETIC_NAMES[i], "inventory")
                );
            }
        }
    }

    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> stacks) {
        if (isInCreativeTab(tab)) {
            for (int i = 0; i < types; i++) {
                stacks.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Override
    public BaubleType getBaubleType(ItemStack stack) {
        return BaubleType.HEAD;
    }

    public enum Variants {
        MASK,
        PYLON,
        GOGGLE_JUNGLE, GOGGLE_OCEAN, GOGGLE_SNOWFIELD, GOGGLE_STANDARD,
        BLACKGLASSES,
        QUESTION,
        LEMON,
        THUGLIFE
    }

}
