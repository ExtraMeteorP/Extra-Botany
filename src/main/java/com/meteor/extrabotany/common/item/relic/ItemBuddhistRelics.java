package com.meteor.extrabotany.common.item.relic;

import java.util.UUID;

import javax.annotation.Nonnull;

import com.google.common.collect.Multimap;
import com.meteor.extrabotany.api.item.IAdvancementRequired;
import com.meteor.extrabotany.common.brew.ModBrew;
import com.meteor.extrabotany.common.core.network.ExtraBotanyNetwork;
import com.meteor.extrabotany.common.core.network.PacketLeftClickCopy;
import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.lib.LibAdvancements;
import com.meteor.extrabotany.common.lib.LibItemsName;
import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.core.handler.ModSounds;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.entity.EntityManaBurst;

public class ItemBuddhistRelics extends ItemModRelic implements IManaUsingItem, IAdvancementRequired {

	private static final String TAG_MODE = "mode";
	private static final int MODE = 5;
	private static final int MANA_PER_SECONDS = 4;

	public ItemBuddhistRelics() {
		super(LibItemsName.BUDDHISTRELICS);
		addPropertyOverride(new ResourceLocation(LibMisc.MOD_ID, "mode"), (stack, world, entity) -> getMode(stack));
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> attrib = super.getAttributeModifiers(slot, stack);
		UUID uuid = new UUID((getUnlocalizedName() + slot.toString()).hashCode(), 0);
		if (slot == slot.MAINHAND) {
			attrib.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
					new AttributeModifier(uuid, "Weapon modifier", getMode(stack) == 4 ? 15 : 0, 0));
			attrib.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
					new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", -2.6, 0));
			attrib.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
					new AttributeModifier(uuid, "Weapon modifier", getMode(stack) == 4 ? 0.3 : 0, 1));
		}
		return attrib;
	}

	@SubscribeEvent
	public void leftClick(PlayerInteractEvent.LeftClickEmpty evt) {
		if (!evt.getItemStack().isEmpty() && evt.getItemStack().getItem() == this) {
			ExtraBotanyNetwork.sendToServer(new PacketLeftClickCopy());
		}
	}

	@SubscribeEvent
	public void attackEntity(AttackEntityEvent evt) {
		if (!evt.getEntityPlayer().world.isRemote) {
			trySpawnBurst(evt.getEntityPlayer());
		}
	}

	public void trySpawnBurst(EntityPlayer player) {
		if (!player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() == this
				&& getMode(player.getHeldItemMainhand()) == 4 && player.getCooledAttackStrength(0) == 1) {
			EntityManaBurst burst = ItemExcaliber.getBurst(player, new ItemStack(ModItems.excaliber));
			player.world.spawnEntity(burst);
			player.world.playSound(null, player.posX, player.posY, player.posZ, ModSounds.terraBlade,
					SoundCategory.PLAYERS, 0.4F, 1.4F);
		}
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (!world.isRemote && entity instanceof EntityPlayer) {
			updateRelic(stack, (EntityPlayer) entity);
			if (!(ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer) entity, MANA_PER_SECONDS, true))
					&& getMode(stack) != 0)
				setMode(stack, 0);
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		int range = 20;
		if (!player.isSneaking()) {
			if (getMode(stack) > 0 && getMode(stack) < 4) {
				player.setActiveHand(hand);
				if (getMode(stack) == 2)
					return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
				return ActionResult.newResult(EnumActionResult.PASS, stack);
			} else if (getMode(stack) == 5) {
				ItemCamera camera = new ItemCamera();
				camera.onItemRightClick(world, player, hand);
			}
		} else
			switchMode(stack);
		return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
	}

	@Nonnull
	@Override
	public ItemStack onItemUseFinish(@Nonnull ItemStack stack, World world, EntityLivingBase player) {
		if (getMode(stack) == 2) {
			if (!world.isRemote) {
				for (PotionEffect effect : ModBrew.oneforall.getPotionEffects(stack)) {
					PotionEffect newEffect = new PotionEffect(effect.getPotion(),
							(int) ((float) effect.getDuration() * 2), effect.getAmplifier() + 1, true, true);
					if (effect.getPotion().isInstant())
						effect.getPotion().affectEntity(player, player, player, newEffect.getAmplifier(), 1F);
					else
						player.addPotionEffect(newEffect);
				}

				if (world.rand.nextBoolean())
					world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_BURP,
							SoundCategory.PLAYERS, 1F, 1F);
			}
		}

		return stack;
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase living, int count) {
		super.onUsingTick(stack, living, count);
		if (!(living instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) living;
		if (ManaItemHandler.requestManaExact(stack, player, 500, true) && getMode(stack) == 1) {
			if (count % 5 == 0)
				player.getFoodStats().addStats(1, 1F);
		}
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entity, int timeLeft) {
		if (!(entity instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) entity;

		if (getMode(stack) == 3) {
			ItemFailnaught failnaught = new ItemFailnaught();
			failnaught.onPlayerStoppedUsing(stack, world, entity, timeLeft);
		}
	}

	@Nonnull
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		if (getMode(stack) == 2)
			return EnumAction.DRINK;
		else if (getMode(stack) == 1)
			return EnumAction.EAT;
		else if (getMode(stack) == 3)
			return EnumAction.BOW;
		else
			return EnumAction.NONE;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return getMode(stack) == 2 ? 18 : 72000;
	}

	public void switchMode(ItemStack stack) {
		if (getMode(stack) < MODE)
			setMode(stack, getMode(stack) + 1);
		else
			setMode(stack, 0);
	}

	public int getMode(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_MODE, 0);
	}

	public void setMode(ItemStack stack, int mode) {
		ItemNBTHelper.setInt(stack, TAG_MODE, mode);
	}

	@Override
	public boolean usesMana(ItemStack arg0) {
		return true;
	}

	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return true;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment) {
		return enchantment.type == enchantment.type.WEAPON;
	}

	@Override
	public String getAdvancementName(ItemStack stack) {
		return LibAdvancements.GAIA_DEFEAT;
	}

}
