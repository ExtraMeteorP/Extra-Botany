package com.meteor.extrabotany.common.item.relic;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Multimap;
import com.meteor.extrabotany.ExtraBotanyCreativeTab;
import com.meteor.extrabotany.api.item.IAdvancementRequired;
import com.meteor.extrabotany.client.render.IModelReg;
import com.meteor.extrabotany.common.core.network.ExtraBotanyNetwork;
import com.meteor.extrabotany.common.core.network.PacketLeftClickFractal;
import com.meteor.extrabotany.common.entity.EntityItemUnbreakable;
import com.meteor.extrabotany.common.entity.EntityPhantomSword;
import com.meteor.extrabotany.common.lib.LibAdvancements;
import com.meteor.extrabotany.common.lib.LibItemsName;
import com.meteor.extrabotany.common.lib.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.item.IRelic;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.advancements.RelicBindTrigger;
import vazkii.botania.common.core.handler.ModSounds;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.equipment.tool.ToolCommons;
import vazkii.botania.common.item.relic.ItemRelic;

public class ItemFirstFractal extends ItemSword implements IRelic, IModelReg, IManaUsingItem, IAdvancementRequired{

	public static ToolMaterial toolMaterial = EnumHelper.addToolMaterial("B_FIRSTFRACTAL", 3, -1, 5.8F, 8F, 20);
	private static final int MANA_PER_DAMAGE = 160;
	private static final String TAG_SOULBIND_UUID = "soulbindUUID";

	public ItemFirstFractal() {
		this(LibItemsName.FIRSTFRACTAL);
	}

	public ItemFirstFractal(String name) {
		super(toolMaterial);
		setCreativeTab(ExtraBotanyCreativeTab.INSTANCE);
		setRegistryName(new ResourceLocation(Reference.MOD_ID, name));
		setUnlocalizedName(name);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void attackEntity(AttackEntityEvent evt) {
		if (!evt.getEntityPlayer().world.isRemote) {
			trySpawnPhantomSword(evt.getEntityPlayer(), evt.getTarget());
		}
	}
	
	@SubscribeEvent
	public void leftClick(PlayerInteractEvent.LeftClickEmpty evt) {
		if (!evt.getItemStack().isEmpty() && evt.getItemStack().getItem() == this) {
			ExtraBotanyNetwork.sendToServer(new PacketLeftClickFractal());
		}
	}
	
	@SubscribeEvent
	public void leftClick(PlayerInteractEvent.LeftClickBlock evt) {
		if (evt.getWorld().isRemote) {
			return;
		}

		if (!evt.getItemStack().isEmpty() && evt.getItemStack().getItem() == this) {
			trySpawnPhantomSword(evt.getEntityPlayer(), null);
		}
	}

	public void trySpawnPhantomSword(EntityPlayer player, Entity target) {
		if (!player.world.isRemote && !player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() == this
				&& player.getCooledAttackStrength(0) == 1) {
			for(int i = 0; i < 3; i++) {
				EntityPhantomSword sword = new EntityPhantomSword(player.world, player, target == null ? ToolCommons.raytraceFromEntity(player.world, player, false, 80F).getBlockPos().add(0, 1, 0) : target.getPosition().add(0, 1, 0));
				sword.setDelay(5 + 5 * i);
				player.world.spawnEntity(sword);
				ToolCommons.damageItem(player.getHeldItemMainhand(), 1, player, MANA_PER_DAMAGE);
			}
			EntityPhantomSword sword2 = new EntityPhantomSword(player.world, player, target == null ? ToolCommons.raytraceFromEntity(player.world, player, false, 80F).getBlockPos().add(0, 1, 0) : target.getPosition().add(0, 1, 0));
			sword2.setVariety(9);
			player.world.spawnEntity(sword2);
			player.world.playSound(null, player.posX, player.posY, player.posZ, ModSounds.terraBlade,
					SoundCategory.PLAYERS, 0.4F, 1.4F);
		}
	}
	
	@Override
	@Nullable
	public Entity createEntity(World world, Entity location, ItemStack itemstack) {
		return new EntityItemUnbreakable(world, location.posX, location.posY, location.posZ, itemstack);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (!world.isRemote && entity instanceof EntityPlayer) {
			updateRelic(stack, (EntityPlayer) entity);
		}
		if (!world.isRemote && entity instanceof EntityPlayer && stack.getItemDamage() > 0
				&& ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer) entity, getManaPerDamage() * 2, true))
			stack.setItemDamage(stack.getItemDamage() - 1);
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return true;
	}

	public int getManaPerDamage() {
		return MANA_PER_DAMAGE;
	}

	@Override
	public boolean usesMana(ItemStack stack) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flags) {
		addBindInfo(tooltip, stack);
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
					addStringToTooltip(
							I18n.format("botaniamisc.relicSoulbound", Minecraft.getMinecraft().player.getName()), list);
			}

		} else
			addStringToTooltip(I18n.format("botaniamisc.shiftinfo"), list);
	}

	public boolean shouldDamageWrongPlayer() {
		return true;
	}

	@Override
	public int getEntityLifespan(ItemStack itemStack, World world) {
		return Integer.MAX_VALUE;
	}

	private static void addStringToTooltip(String s, List<String> tooltip) {
		tooltip.add(s.replaceAll("&", "\u00a7"));
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

		if (!rightPlayer && player.ticksExisted % 10 == 0
				&& (!(stack.getItem() instanceof ItemRelic) || ((ItemRelic) stack.getItem()).shouldDamageWrongPlayer()))
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

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> attrib = super.getAttributeModifiers(slot, stack);
		UUID uuid = new UUID((getUnlocalizedName() + slot.toString()).hashCode(), 0);
		if (slot == slot.MAINHAND) {
			attrib.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
					new AttributeModifier(uuid, "Weapon modifier", 0.3, 1));
			attrib.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
					new AttributeModifier(uuid, "Weapon modifier", 0.15, 1));
			attrib.put(EntityPlayer.REACH_DISTANCE.getName(),
					new AttributeModifier(uuid, "Weapon modifier", 5, 0));
		}
		return attrib;
	}
	
	@Nonnull
	@Override
	public String getUnlocalizedNameInefficiently(@Nonnull ItemStack par1ItemStack) {
		return super.getUnlocalizedNameInefficiently(par1ItemStack).replaceAll("item\\.",
				"item." + Reference.MOD_ID + ":");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
	
	@Override
	public String getAdvancementName(ItemStack stack) {
		return LibAdvancements.HERRSCHER_DEFEAT;
	}


}
