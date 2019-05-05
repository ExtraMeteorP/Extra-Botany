package com.meteor.extrabotany.common.item.relic;

import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.client.core.handler.MiscellaneousIcons;
import com.meteor.extrabotany.client.render.ICosmeticItem;
import com.meteor.extrabotany.common.entity.judah.EntityJudahOath;
import com.meteor.extrabotany.common.lib.LibItemsName;
import com.meteor.extrabotany.common.lib.LibMisc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.item.IBaubleRender.Helper;
import vazkii.botania.api.item.IBaubleRender.RenderType;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.client.core.helper.IconHelper;

import javax.annotation.Nonnull;

public class ItemJudahOath extends ItemModRelic implements ICosmeticItem, IManaUsingItem {

    final int types = 2;

    public ItemJudahOath() {
        super(LibItemsName.JUDAHOATH);
        setHasSubtypes(true);
    }

    @Nonnull
    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return "item." + LibItemsName.JUDAHOATHS[Math.min(types - 1, par1ItemStack.getItemDamage())];
    }

    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> stacks) {
        if (isInCreativeTab(tab)) {
            for (int i = 0; i < types; i++) {
                stacks.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (isRightPlayer(player, stack) && ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer) player, 800, true)) {
            player.getCooldownTracker().setCooldown(this, 120);
            EntityJudahOath judah = new EntityJudahOath(world, player);
            judah.shoot(player, player.rotationPitch, player.rotationYaw, 0F, 0.5F, 0F);
            judah.setPosition(player.posX, player.posY + 1, player.posZ);
            ExtraBotany.logger.info(MathHelper.wrapDegrees(-player.rotationYaw + 180));
            judah.setRotation(MathHelper.wrapDegrees(-player.rotationYaw + 180));
            judah.setDamage(7);
            judah.setUUID(player.getUniqueID());
            judah.setType(EntityJudahOath.Type.byId(stack.getMetadata()));
            if (!world.isRemote)
                world.spawnEntity(judah);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onItemRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        if (type == RenderType.BODY) {
            Helper.rotateIfSneaking(player);
            boolean armor = !player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).isEmpty();
            GlStateManager.rotate(180F, 1F, 0F, 0F);
            GlStateManager.translate(-1.0F, -1.23F, armor ? -0.23F : -0.18F);
            GlStateManager.scale(2F, 2F, 2F);
            TextureAtlasSprite gemIcon = MiscellaneousIcons.INSTANCE.judahIcons2[stack.getMetadata()];
            float f = gemIcon.getMinU();
            float f1 = gemIcon.getMaxU();
            float f2 = gemIcon.getMinV();
            float f3 = gemIcon.getMaxV();
            IconHelper.renderIconIn3D(Tessellator.getInstance(), f1, f2, f, f3, gemIcon.getIconWidth(), gemIcon.getIconHeight(), 1F / 32F);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModels() {
        for (int i = 0; i < LibItemsName.JUDAHOATHS.length; i++) {
            ModelLoader.setCustomModelResourceLocation(
                    this, i,
                    new ModelResourceLocation(LibMisc.MOD_ID + ":" + LibItemsName.JUDAHOATHS[i], "inventory")
            );
        }
    }

    @Override
    public boolean usesMana(ItemStack arg0) {
        return true;
    }

}
