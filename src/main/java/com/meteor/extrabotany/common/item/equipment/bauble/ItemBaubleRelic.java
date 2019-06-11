package com.meteor.extrabotany.common.item.equipment.bauble;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.meteor.extrabotany.api.item.IAdvancementReward;
import com.meteor.extrabotany.common.entity.EntityItemUnbreakable;
import com.meteor.extrabotany.common.lib.LibAdvancements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.item.IRelic;
import vazkii.botania.common.advancements.RelicBindTrigger;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.relic.ItemRelic;

public abstract class ItemBaubleRelic extends ItemBauble implements IRelic, IAdvancementReward{
	
	private static final String TAG_SOULBIND_UUID = "soulbindUUID";

	public ItemBaubleRelic(String name) {
		super(name);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if(!world.isRemote && entity instanceof EntityPlayer)
			updateRelic(stack, (EntityPlayer) entity);
	}
	
	@Override
    @Nullable
    public Entity createEntity(World world, Entity location, ItemStack itemstack){
        return new EntityItemUnbreakable(world, location.posX, location.posY, location.posZ, itemstack);
    }

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flags) {
		addBindInfo(tooltip, stack);
	}

	@SideOnly(Side.CLIENT)
	public void addBindInfo(List<String> list, ItemStack stack) {
		if(GuiScreen.isShiftKeyDown()) {
			if(!hasUUID(stack)) {
				addStringToTooltip(I18n.format("botaniamisc.relicUnbound"), list);
			} else {
				if(!getSoulbindUUID(stack).equals(Minecraft.getMinecraft().player.getUniqueID()))
					addStringToTooltip(I18n.format("botaniamisc.notYourSagittarius"), list);
				else addStringToTooltip(I18n.format("botaniamisc.relicSoulbound", Minecraft.getMinecraft().player.getName()), list);
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
		if(stack.isEmpty() || !(stack.getItem() instanceof IRelic))
			return;

		boolean rightPlayer = true;

		if(!hasUUID(stack)) {
			bindToUUID(player.getUniqueID(), stack);
			if(player instanceof EntityPlayerMP)
				RelicBindTrigger.INSTANCE.trigger((EntityPlayerMP) player, stack);
		} else if (!getSoulbindUUID(stack).equals(player.getUniqueID())) {
			rightPlayer = false;
		}

		if(!rightPlayer && player.ticksExisted % 10 == 0 && (!(stack.getItem() instanceof ItemRelic) || ((ItemRelic) stack.getItem()).shouldDamageWrongPlayer()))
			player.attackEntityFrom(damageSource(), 2);
	}

	public boolean isRightPlayer(EntityPlayer player, ItemStack stack) {
		return hasUUID(stack) && getSoulbindUUID(stack).equals(player.getUniqueID());
	}

	public static DamageSource damageSource() {
		return new DamageSource("botania-relic");
	}

	@Override
	public void bindToUUID(UUID uuid, ItemStack stack) {
		ItemNBTHelper.setString(stack, TAG_SOULBIND_UUID, uuid.toString());
	}

	@Override
	public UUID getSoulbindUUID(ItemStack stack) {
		if(ItemNBTHelper.verifyExistance(stack, TAG_SOULBIND_UUID)) {
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
	
	@Override
	public String getAdvancementName(ItemStack stack) {
		return LibAdvancements.GAIA_DEFEAT;
	}

}
