package com.meteor.extrabotany.common.item.equipment.bauble;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.meteor.extrabotany.common.brew.ModPotions;
import com.meteor.extrabotany.common.lib.LibItemsName;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import net.minecraft.enchantment.EnchantmentFrostWalker;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.api.mana.IManaItem;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class ItemSilentEternity extends ItemBaubleRelic implements IManaItem{
	
	protected static final int MAX_MANA = 666;
	
	private static final String TAG_MANA = "mana";
	private static final String TAG_X = "posx";
	private static final String TAG_Y = "posy";
	private static final String TAG_Z = "posz";
	private static final String TAG_STOPTICKS = "stopticks";
	
	public ItemSilentEternity() {
		super(LibItemsName.SILENTETERNITY);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onPlayerHeal(LivingHealEvent event) {
		if(!(event.getEntityLiving() instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) event.getEntityLiving();
		if(BaublesApi.isBaubleEquipped(player, this) != -1)
			event.setCanceled(true);
	}
	
	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase entity) {
		super.onWornTick(stack, entity);
		if(!(entity instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) entity;
		addMana(stack, 666);
		if(!entity.world.isRemote) {
			boolean lastOnGround = entity.onGround;
			entity.onGround = true;
			EnchantmentFrostWalker.freezeNearby(entity, entity.world, new BlockPos(entity), 4);
			ItemFrostStar.freezeLava(entity, entity.world, new BlockPos(entity), 4);
			entity.onGround = lastOnGround;
			if(getX(stack) == player.posX && getY(stack) == player.posY && getZ(stack) == player.posZ) {
				setStopticks(stack, getStopticks(stack)+1);
				if(getStopticks(stack) > 15) {
					player.setHealth(Math.min(player.getMaxHealth(), player.getHealth()+0.4F));
					player.addPotionEffect(new PotionEffect(ModPotions.eternity, 10));
				}
			}else
				setStopticks(stack, 0);
			setX(stack,player.lastTickPosX);
			setY(stack,player.lastTickPosY);
			setZ(stack,player.lastTickPosZ);
		}
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.TRINKET;
	}
	
	public static void setMana(ItemStack stack, int mana) {
		ItemNBTHelper.setInt(stack, TAG_MANA, mana);
	}
	
	public double getX(ItemStack stack) {
		return ItemNBTHelper.getDouble(stack, TAG_X, 0);
	}
	
	public double getY(ItemStack stack) {
		return ItemNBTHelper.getDouble(stack, TAG_Y, 0);
	}
	
	public double getZ(ItemStack stack) {
		return ItemNBTHelper.getDouble(stack, TAG_Z, 0);
	}
	
	public void setX(ItemStack stack, double d) {
		ItemNBTHelper.setDouble(stack, TAG_X, d);
	}
	
	public void setY(ItemStack stack, double d) {
		ItemNBTHelper.setDouble(stack, TAG_Y, d);
	}
	
	public void setZ(ItemStack stack, double d) {
		ItemNBTHelper.setDouble(stack, TAG_Z, d);
	}
	
	public int getStopticks(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_STOPTICKS, 0);
	}
	
	public void setStopticks(ItemStack stack, int i) {
		ItemNBTHelper.setInt(stack, TAG_STOPTICKS, i);
	}

	@Override
	public int getMana(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_MANA, 0);
	}

	@Override
	public int getMaxMana(ItemStack stack) {
		return MAX_MANA;
	}

	@Override
	public void addMana(ItemStack stack, int mana) {
		setMana(stack, Math.min(getMana(stack) + mana, getMaxMana(stack)));
	}

	@Override
	public boolean canReceiveManaFromPool(ItemStack stack, TileEntity pool) {
		return false;
	}

	@Override
	public boolean canReceiveManaFromItem(ItemStack stack, ItemStack otherStack) {
		return false;
	}

	@Override
	public boolean canExportManaToPool(ItemStack stack, TileEntity pool) {
		return false;
	}

	@Override
	public boolean canExportManaToItem(ItemStack stack, ItemStack otherStack) {
		return true;
	}

	@Override
	public boolean isNoExport(ItemStack stack) {
		return false;
	}
	
	@Override
	public void onEquippedOrLoadedIntoWorld(ItemStack stack, EntityLivingBase player) {
		if(!player.world.isRemote) {
			Multimap<String, AttributeModifier> attributes = HashMultimap.create();
			fillModifiers(attributes, stack);
			player.getAttributeMap().applyAttributeModifiers(attributes);
		}
	}

	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase player) {
		if(!player.world.isRemote) {
			Multimap<String, AttributeModifier> attributes = HashMultimap.create();
			fillModifiers(attributes, stack);
			player.getAttributeMap().removeAttributeModifiers(attributes);
		}
	}

	void fillModifiers(Multimap<String, AttributeModifier> attributes, ItemStack stack){
		if(stack.isEmpty())
			return;
		attributes.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), new AttributeModifier(getBaubleUUID(stack), "SILENTETERNITY", 1, 0).setSaved(false));
	}

}
