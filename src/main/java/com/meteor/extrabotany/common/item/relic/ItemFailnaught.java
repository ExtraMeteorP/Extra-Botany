package com.meteor.extrabotany.common.item.relic;

import com.meteor.extrabotany.ExtraBotanyCreativeTab;
import com.meteor.extrabotany.client.render.IModelReg;
import com.meteor.extrabotany.common.entity.EntityMagicArrow;
import com.meteor.extrabotany.common.lib.LibItemsName;
import com.meteor.extrabotany.common.lib.LibMisc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.item.IRelic;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.advancements.RelicBindTrigger;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.relic.ItemRelic;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public class ItemFailnaught extends ItemBow implements IManaUsingItem, IRelic, IModelReg {

    private static final String TAG_SOULBIND_UUID = "soulbindUUID";

    public ItemFailnaught() {
        super();
        setCreativeTab(ExtraBotanyCreativeTab.INSTANCE);
        setRegistryName(new ResourceLocation(LibMisc.MOD_ID, LibItemsName.FAILNAUGHT));
        setUnlocalizedName(LibItemsName.FAILNAUGHT);
        addPropertyOverride(new ResourceLocation("minecraft:pull"), (stack, worldIn, entityIn) -> {
            if (entityIn == null) {
                return 0.0F;
            } else {
                ItemStack itemstack = entityIn.getActiveItemStack();
                return !itemstack.isEmpty() && itemstack.getItem() instanceof ItemFailnaught ? (stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) * chargeVelocityMultiplier() / 20.0F : 0.0F;
            }
        });
    }

    private static void addStringToTooltip(String s, List<String> tooltip) {
        tooltip.add(s.replaceAll("&", "\u00a7"));
    }

    public static DamageSource damageSource() {
        return new DamageSource("botania-relic");
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        player.setActiveHand(hand);
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entity, int timeLeft) {
        if (!(entity instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) entity;
        int i = (int) ((getMaxItemUseDuration(stack) - timeLeft) * chargeVelocityMultiplier());
        if (i < 12)
            return;
        int rank = i / 15;
        if (isRightPlayer(player, stack) && ManaItemHandler.requestManaExactForTool(stack, player, Math.min(480, 220 + rank * 50), true)) {
            EntityMagicArrow arrow = new EntityMagicArrow(world, player);
            arrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 3.0F, 1.0F);
            arrow.setDamage(Math.min(40, 8 + rank * 7));
            arrow.rotationYaw = player.rotationYaw;
            arrow.setRotation(MathHelper.wrapDegrees(-player.rotationYaw + 180));
            int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
            if (j > 0) {
                arrow.setDamage(arrow.getDamage() + j * 2);
            }
            arrow.setLife(Math.min(150, 5 + i * 4));

            if (!world.isRemote)
                world.spawnEntity(arrow);

            world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
        }
    }

    @Override
    public boolean usesMana(ItemStack stack) {
        return true;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 72000;
    }

    float chargeVelocityMultiplier() {
        return 1F;
    }

    @Override
    public int getItemEnchantability() {
        return 10;
    }

    @Nonnull
    @Override
    public String getUnlocalizedNameInefficiently(@Nonnull ItemStack par1ItemStack) {
        return super.getUnlocalizedNameInefficiently(par1ItemStack).replaceAll("item\\.", "item." + LibMisc.MOD_ID + ":");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.onUpdate(stack, world, entity, slot, selected);
        if (!world.isRemote && entity instanceof EntityPlayer)
            updateRelic(stack, (EntityPlayer) entity);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flags) {
        addBindInfo(tooltip, stack);
        addStringToTooltip("DMG:" + String.valueOf(8 + 2 * EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack)), tooltip);
    }

    @SideOnly(Side.CLIENT)
    public void addBindInfo(List<String> list, ItemStack stack) {
        if (GuiScreen.isShiftKeyDown()) {
            if (!hasUUID(stack)) {
                addStringToTooltip(I18n.format("botaniamisc.relicUnbound"), list);
            } else {
                if (!getSoulbindUUID(stack).equals(Minecraft.getMinecraft().player.getUniqueID()))
                    addStringToTooltip(I18n.format("botaniamisc.notYourSagittarius"), list);
                else
                    addStringToTooltip(I18n.format("botaniamisc.relicSoulbound", Minecraft.getMinecraft().player.getName()), list);
            }

        } else addStringToTooltip(I18n.format("botaniamisc.shiftinfo"), list);
    }

    public boolean shouldDamageWrongPlayer() {
        return true;
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, World world) {
        return Integer.MAX_VALUE;
    }

    public void updateRelic(ItemStack stack, EntityPlayer player) {
        if (stack.isEmpty() || !(stack.getItem() instanceof IRelic))
            return;

        boolean rightPlayer = true;

        if (!hasUUID(stack)) {
            bindToUUID(player.getUniqueID(), stack);
            if (player instanceof EntityPlayerMP)
                RelicBindTrigger.INSTANCE.trigger((EntityPlayerMP) player, stack);
        } else if (!getSoulbindUUID(stack).equals(player.getUniqueID())) {
            rightPlayer = false;
        }

        if (!rightPlayer && player.ticksExisted % 10 == 0 && (!(stack.getItem() instanceof ItemRelic) || ((ItemRelic) stack.getItem()).shouldDamageWrongPlayer()))
            player.attackEntityFrom(damageSource(), 2);
    }

    public boolean isRightPlayer(EntityPlayer player, ItemStack stack) {
        return hasUUID(stack) && getSoulbindUUID(stack).equals(player.getUniqueID());
    }

    @Override
    public void bindToUUID(UUID uuid, ItemStack stack) {
        ItemNBTHelper.setString(stack, TAG_SOULBIND_UUID, uuid.toString());
    }

    @Override
    public UUID getSoulbindUUID(ItemStack stack) {
        if (ItemNBTHelper.verifyExistance(stack, TAG_SOULBIND_UUID)) {
            try {
                return UUID.fromString(ItemNBTHelper.getString(stack, TAG_SOULBIND_UUID, ""));
            } catch (IllegalArgumentException ex) { // Bad UUID in tag
                ItemNBTHelper.removeEntry(stack, TAG_SOULBIND_UUID);
            }
        }

        return null;
    }

    @Override
    public boolean hasUUID(ItemStack stack) {
        return getSoulbindUUID(stack) != null;
    }

    @Nonnull
    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return BotaniaAPI.rarityRelic;
    }

}
