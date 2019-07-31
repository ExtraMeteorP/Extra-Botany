package com.meteor.extrabotany.common.item.relic;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Multimap;
import com.meteor.extrabotany.api.item.IAdvancementRequired;
import com.meteor.extrabotany.common.entity.EntityItemUnbreakable;
import com.meteor.extrabotany.common.item.equipment.shield.ItemManasteelShield;
import com.meteor.extrabotany.common.lib.LibAdvancements;
import com.meteor.extrabotany.common.lib.LibItemsName;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.item.IPixieSpawner;
import vazkii.botania.api.item.IRelic;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.advancements.RelicBindTrigger;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.equipment.tool.ItemThunderSword;
import vazkii.botania.common.item.relic.ItemRelic;

public class ItemAchilleshield extends ItemManasteelShield implements IRelic, IPixieSpawner, IAdvancementRequired {

	private static final String TAG_SOULBIND_UUID = "soulbindUUID";
	private static final String TAG_LIGHTNING_SEED = "lightningSeed";
	private static final String TAG_MODE = "mode";

	public ItemAchilleshield() {
		super(BotaniaAPI.terrasteelToolMaterial, LibItemsName.ACHILLESHIELD);
		this.addPropertyOverride(new ResourceLocation("release"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				return entityIn != null && isReleased(stack) ? 1.0F : 0.0F;
			}
		});
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (playerIn.isSneaking() && playerIn.getAbsorptionAmount() >= 12F) {
			setReleased(playerIn.getHeldItem(hand), !isReleased(playerIn.getHeldItem(hand)));
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
		}
		if (!isReleased(playerIn.getHeldItem(hand)))
			playerIn.setActiveHand(hand);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase entity, @Nonnull EntityLivingBase attacker) {
		ItemThunderSword sword = new ItemThunderSword();
		if (isReleased(stack)) {
			sword.hitEntity(stack, entity, attacker);
		} else
			attacker.setAbsorptionAmount(Math.min(20, attacker.getAbsorptionAmount() + 2F));
		return super.hitEntity(stack, entity, attacker);
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> attrib = super.getAttributeModifiers(slot, stack);
		UUID uuid = new UUID((getUnlocalizedName() + slot.toString()).hashCode(), 0);
		if (slot == slot.MAINHAND) {
			attrib.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
					new AttributeModifier(uuid, "Weapon modifier", isReleased(stack) ? 15 : 6, 0));
			attrib.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
					new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", -2.6, 0));
			attrib.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
					new AttributeModifier(uuid, "Tool modifier", isReleased(stack) ? 0.4F : 0, 1));
		}
		return attrib;
	}

	public boolean isReleased(ItemStack stack) {
		return ItemNBTHelper.getBoolean(stack, TAG_MODE, false);
	}

	public void setReleased(ItemStack stack, boolean mode) {
		ItemNBTHelper.setBoolean(stack, TAG_MODE, mode);
	}

	@Override
	@Nullable
	public Entity createEntity(World world, Entity location, ItemStack itemstack) {
		return new EntityItemUnbreakable(world, location.posX, location.posY, location.posZ, itemstack);
	}

	public void onEnemyRammed(ItemStack stack, EntityLivingBase user, EntityLivingBase enemy, Vec3d rammingDir) {
		int knockback = EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, stack);
		int power = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, stack);
		enemy.knockBack(user, 1.5F * (1 + knockback), -rammingDir.x, -rammingDir.z);
		if (user instanceof EntityPlayer) {
			enemy.attackEntityFrom(DamageSource.MAGIC, 13.0F + 1.5F * power);
		}
	}

	@Override
	public void onAttackBlocked(ItemStack stack, EntityLivingBase attacked, float damage, DamageSource source) {
		if (source.getImmediateSource() != null) {
			if (attacked instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) attacked;
				if(ManaItemHandler.requestManaExactForTool(stack, player, 500, true))
					source.getImmediateSource().attackEntityFrom(DamageSource.MAGIC, damage);
			}
		}

		if (source instanceof EntityDamageSourceIndirect) {
			EntityDamageSourceIndirect indirectSource = (EntityDamageSourceIndirect) source;
			if (indirectSource.getImmediateSource() != null && indirectSource.getTrueSource() != null
					&& indirectSource.getImmediateSource() instanceof IProjectile) {
				Vec3d dir = indirectSource.getImmediateSource().getPositionEyes(1)
						.subtract(indirectSource.getTrueSource().getPositionEyes(1)).normalize();
				((IProjectile) indirectSource.getImmediateSource()).shoot(dir.x, dir.y, dir.z, 15, 2);
				if (indirectSource.getImmediateSource() instanceof EntityArrow) {
					((EntityArrow) indirectSource.getImmediateSource()).shootingEntity = attacked;
				}
			}
		}
		super.onAttackBlocked(stack, attacked, damage, source);
	}

	@Override
	public int getRepairSpeed() {
		return 5;
	}

	@Override
	public float getAttackerKnockbackMultiplier(ItemStack stack, EntityLivingBase attacked, float damage,
			DamageSource source) {
		return 1.75F;
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
		super.onUsingTick(stack, player, count);
		if (isReleased(stack))
			player.stopActiveHand();
		if ((player.motionX > 0 || player.motionZ > 0) && player.isHandActive()) {
			Vec3d moveDir = new Vec3d(player.motionX, player.motionY, player.motionZ).normalize();
			List<EntityLivingBase> targets = player.world.getEntitiesWithinAABB(EntityLivingBase.class,
					player.getEntityBoundingBox().grow(1), e -> e != player);
			for (EntityLivingBase target : targets) {
				this.onEnemyRammed(stack, player, target, moveDir);
			}
		}
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		super.onUpdate(stack, world, entity, slot, selected);
		if (!world.isRemote && entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (isReleased(stack) && player.ticksExisted % 10 == 0)
				if (player.getAbsorptionAmount() > 0F)
					player.setAbsorptionAmount(player.getAbsorptionAmount() - 1F);
				else
					setReleased(stack, false);
			updateRelic(stack, player);
		}
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
	public float getPixieChance(ItemStack arg0) {
		return 1F;
	}

	@Override
	public String getAdvancementName(ItemStack stack) {
		return LibAdvancements.GAIA_DEFEAT;
	}

}
