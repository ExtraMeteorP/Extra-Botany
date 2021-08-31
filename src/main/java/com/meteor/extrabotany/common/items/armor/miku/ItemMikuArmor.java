package com.meteor.extrabotany.common.items.armor.miku;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.client.model.armor.ModelMikuArmor;
import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.LazyValue;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import vazkii.botania.api.item.IPhantomInkable;
import vazkii.botania.api.mana.IManaDiscountArmor;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.client.core.handler.TooltipHandler;
import vazkii.botania.client.lib.LibResources;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class ItemMikuArmor extends ArmorItem implements IManaUsingItem, IPhantomInkable, IManaDiscountArmor {

    private static final int MANA_PER_DAMAGE = 70;

    private static final String TAG_PHANTOM_INK = "phantomInk";

    private final LazyValue<BipedModel<?>> model;
    public final EquipmentSlotType type;

    public ItemMikuArmor(EquipmentSlotType type, Properties props) {
        this(type, ExtraBotanyAPI.INSTANCE.getMikuArmorMaterial(), props);
    }

    public ItemMikuArmor(EquipmentSlotType type, IArmorMaterial mat, Properties props) {
        super(mat, type, props);
        this.type = type;
        this.model = DistExecutor.unsafeRunForDist(() -> () -> new LazyValue<>(() -> this.provideArmorModelForSlot(type)),
                () -> () -> null);
    }

    @Override
    public float getDiscount(ItemStack stack, int slot, PlayerEntity player, @Nullable ItemStack tool) {
        return hasArmorSet(player) ? 0.15F : 0F;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flags) {
        TooltipHandler.addOnShift(list, () -> addInformationAfterShift(stack, world, list, flags));
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformationAfterShift(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flags) {
        PlayerEntity player = Minecraft.getInstance().player;
        list.add(getArmorSetTitle(player));
        addArmorSetDescription(stack, list);
        ItemStack[] stacks = getArmorSetStacks();
        for (ItemStack armor : stacks) {
            IFormattableTextComponent cmp = new StringTextComponent(" - ").append(armor.getDisplayName());
            EquipmentSlotType slot = ((ArmorItem) armor.getItem()).getEquipmentSlot();
            cmp.mergeStyle(hasArmorSetItem(player, slot) ? TextFormatting.GREEN : TextFormatting.GRAY);
            list.add(cmp);
        }
        if (hasPhantomInk(stack)) {
            list.add(new TranslationTextComponent("botaniamisc.hasPhantomInk").mergeStyle(TextFormatting.GRAY));
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity player, int slot, boolean selected) {
        if (player instanceof PlayerEntity) {
            onArmorTick(stack, world, (PlayerEntity) player);
        }
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (!world.isRemote && stack.getDamage() > 0 && ManaItemHandler.instance().requestManaExact(stack, player, MANA_PER_DAMAGE * 2, true)) {
            stack.setDamage(stack.getDamage() - 1);
        }
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return ToolCommons.damageItemIfPossible(stack, amount, entity, MANA_PER_DAMAGE);
    }

    @Nonnull
    @Override
    public final String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return hasPhantomInk(stack) ? LibResources.MODEL_INVISIBLE_ARMOR : getArmorTextureAfterInk(stack, slot);
    }

    public String getArmorTextureAfterInk(ItemStack stack, EquipmentSlotType slot) {
        return LibMisc.MOD_ID + ":textures/model/armor_miku.png";
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("unchecked")
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A original) {
        return (A) model.getValue();
    }

    @OnlyIn(Dist.CLIENT)
    public BipedModel<?> provideArmorModelForSlot(EquipmentSlotType slot) {
        return new ModelMikuArmor(slot);
    }

    @Override
    public boolean usesMana(ItemStack stack) {
        return true;
    }

    private static final LazyValue<ItemStack[]> armorSet = new LazyValue<>(() -> new ItemStack[] {
            new ItemStack(ModItems.armor_miku_helm),
            new ItemStack(ModItems.armor_miku_chest),
            new ItemStack(ModItems.armor_miku_legs),
            new ItemStack(ModItems.armor_miku_boots)
    });

    public ItemStack[] getArmorSetStacks() {
        return armorSet.getValue();
    }

    public boolean hasArmorSet(PlayerEntity player) {
        return hasArmorSetItem(player, EquipmentSlotType.HEAD) && hasArmorSetItem(player, EquipmentSlotType.CHEST) && hasArmorSetItem(player, EquipmentSlotType.LEGS) && hasArmorSetItem(player, EquipmentSlotType.FEET);
    }

    public boolean hasArmorSetItem(PlayerEntity player, EquipmentSlotType slot) {
        if (player == null || player.inventory == null || player.inventory.armorInventory == null) {
            return false;
        }

        ItemStack stack = player.getItemStackFromSlot(slot);
        if (stack.isEmpty()) {
            return false;
        }

        switch (slot) {
            case HEAD:
                return stack.getItem() == ModItems.armor_miku_helm;
            case CHEST:
                return stack.getItem() == ModItems.armor_miku_chest;
            case LEGS:
                return stack.getItem() == ModItems.armor_miku_legs;
            case FEET:
                return stack.getItem() == ModItems.armor_miku_boots;
        }

        return false;
    }

    private int getSetPiecesEquipped(PlayerEntity player) {
        int pieces = 0;
        for (EquipmentSlotType slot : EquipmentSlotType.values()) {
            if (slot.getSlotType() == EquipmentSlotType.Group.ARMOR && hasArmorSetItem(player, slot)) {
                pieces++;
            }
        }

        return pieces;
    }

    public IFormattableTextComponent getArmorSetName() {
        return new TranslationTextComponent("extrabotany.armorset.miku.name");
    }

    private ITextComponent getArmorSetTitle(PlayerEntity player) {
        ITextComponent end = getArmorSetName()
                .appendString(" (" + getSetPiecesEquipped(player) + "/" + getArmorSetStacks().length + ")")
                .mergeStyle(TextFormatting.GRAY);
        return new TranslationTextComponent("botaniamisc.armorset")
                .appendString(" ")
                .append(end);
    }

    @OnlyIn(Dist.CLIENT)
    public void addArmorSetDescription(ItemStack stack, List<ITextComponent> list) {
        list.add(new TranslationTextComponent("extrabotany.armorset.miku.desc").mergeStyle(TextFormatting.GRAY));
    }

    @Override
    public boolean hasPhantomInk(ItemStack stack) {
        return ItemNBTHelper.getBoolean(stack, TAG_PHANTOM_INK, false);
    }

    @Override
    public void setPhantomInk(ItemStack stack, boolean ink) {
        ItemNBTHelper.setBoolean(stack, TAG_PHANTOM_INK, ink);
    }

}
