package com.meteor.extrabotany.common.item.relic;

import com.google.common.collect.Multimap;
import com.meteor.extrabotany.ExtraBotanyCreativeTab;
import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.client.render.IModelReg;
import com.meteor.extrabotany.common.core.network.ExtraBotanyNetwork;
import com.meteor.extrabotany.common.core.network.PacketLeftClick;
import com.meteor.extrabotany.common.entity.gaia.EntityVoidHerrscher;
import com.meteor.extrabotany.common.lib.LibItemsName;
import com.meteor.extrabotany.common.lib.LibMisc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.internal.IManaBurst;
import vazkii.botania.api.item.IRelic;
import vazkii.botania.api.mana.BurstProperties;
import vazkii.botania.api.mana.ILensEffect;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.advancements.RelicBindTrigger;
import vazkii.botania.common.core.handler.ModSounds;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.core.helper.Vector3;
import vazkii.botania.common.entity.EntityManaBurst;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.equipment.tool.ToolCommons;
import vazkii.botania.common.item.relic.ItemRelic;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public class ItemExcaliber extends ItemSword implements IRelic, ILensEffect, IModelReg, IManaUsingItem{
	
	private static final String TAG_ATTACKER_USERNAME = "attackerUsername";
	private static final String TAG_HOME_ID = "homeID";
	private static final String TAG_SOULBIND_UUID = "soulbindUUID";
	public static ToolMaterial toolMaterial = EnumHelper.addToolMaterial("B_EXCALIBER", 3, -1, 6.2F, 6F, 40);
	private static final int MANA_PER_DAMAGE = 100;
	
	public ItemExcaliber(){
		this(LibItemsName.EXCALIBER);
	}
	
	public ItemExcaliber(String name) {
		super(toolMaterial);
		setCreativeTab(ExtraBotanyCreativeTab.INSTANCE);
		setRegistryName(new ResourceLocation(LibMisc.MOD_ID, name));
		setUnlocalizedName(name);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void leftClick(PlayerInteractEvent.LeftClickEmpty evt) {
		if (!evt.getItemStack().isEmpty()
				&& evt.getItemStack().getItem() == this) {
			ExtraBotanyNetwork.sendToServer(new PacketLeftClick());
		}
	}

	@SubscribeEvent
	public void attackEntity(AttackEntityEvent evt) {
		if (!evt.getEntityPlayer().world.isRemote) {
			trySpawnBurst(evt.getEntityPlayer());
		}
	}

	public void trySpawnBurst(EntityPlayer player) {
		if (!player.getHeldItemMainhand().isEmpty()
				&& player.getHeldItemMainhand().getItem() == this && player.getCooledAttackStrength(0) == 1) {
			EntityManaBurst burst = getBurst(player, player.getHeldItemMainhand());
			player.world.spawnEntity(burst);
			ToolCommons.damageItem(player.getHeldItemMainhand(), 1, player, MANA_PER_DAMAGE);
			player.world.playSound(null, player.posX, player.posY, player.posZ, ModSounds.terraBlade, SoundCategory.PLAYERS, 0.4F, 1.4F);
		}
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if(!world.isRemote && entity instanceof EntityPlayer){
			updateRelic(stack, (EntityPlayer) entity);
			//PotionEffect haste = ((EntityLivingBase) entity).getActivePotionEffect(MobEffects.HASTE);
			//float check = haste == null ? 0.16666667F : haste.getAmplifier() == 1 ? 0.5F : 0.4F;
			//if(((EntityPlayer)entity).swingProgress == check)
				//trySpawnBurst((EntityPlayer) entity);
		}
		if(!world.isRemote && entity instanceof EntityPlayer && stack.getItemDamage() > 0 && ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer) entity, getManaPerDamage() * 2, true))
			stack.setItemDamage(stack.getItemDamage() - 1);
	}
	
    @Override
    public boolean isEnchantable(ItemStack stack){
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

	private static void addStringToTooltip(String s, List<String> tooltip) {
		tooltip.add(s.replaceAll("&", "\u00a7"));
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
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> attrib = super.getAttributeModifiers(slot, stack);
		UUID uuid = new UUID((getUnlocalizedName() + slot.toString()).hashCode(), 0);
		if (slot == slot.MAINHAND) {
			attrib.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(uuid, "Weapon modifier", 5, 0));
			attrib.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(uuid, "Weapon modifier" , 0.3, 1));
		}
		return attrib;
	}
	
	public static EntityManaBurst getBurst(EntityPlayer player, ItemStack stack) {
		EntityManaBurst burst = new EntityManaBurst(player, EnumHand.MAIN_HAND);

		float motionModifier = 9F;
		burst.setColor(0xFFFF20);
		burst.setMana(MANA_PER_DAMAGE);
		burst.setStartingMana(MANA_PER_DAMAGE);
		burst.setMinManaLoss(40);
		burst.setManaLossPerTick(4F);
		burst.setGravity(0F);
		burst.setMotion(burst.motionX * motionModifier, burst.motionY * motionModifier, burst.motionZ * motionModifier);
		
		ItemStack lens = stack.copy();
		ItemNBTHelper.setString(lens, TAG_ATTACKER_USERNAME, player.getName());
		burst.setSourceLens(lens);
		return burst;
	}
	@Override
	public void apply(ItemStack stack, BurstProperties props) {
		// NO-OP
	}

	@Override
	public void updateBurst(IManaBurst burst, ItemStack stack) {
		EntityThrowable entity = (EntityThrowable) burst;
		AxisAlignedBB axis = new AxisAlignedBB(entity.posX, entity.posY, entity.posZ, entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).grow(1);
		String attacker = ItemNBTHelper.getString(burst.getSourceLens(), TAG_ATTACKER_USERNAME, "");
		
		if(burst.getColor() == 0XFFAF00 || burst.getColor() == 0XFFD700){
			AxisAlignedBB axis1 = new AxisAlignedBB(entity.posX -2.5F, entity.posY -2.5F, entity.posZ -2.5F, entity.lastTickPosX + 2.5F, entity.lastTickPosY + 2.5F, entity.lastTickPosZ + 2.5F);
			if(burst.getColor() == 0XFFD700)
				axis1.grow(1.5F);
			List<EntityLivingBase> entities = entity.world.getEntitiesWithinAABB(EntityLivingBase.class, axis1);
			for(EntityLivingBase living : entities) {
				if((living instanceof EntityPlayer 
						&& (living.getName().equals(attacker) || FMLCommonHandler.instance().getMinecraftServerInstance() != null 
						&& !FMLCommonHandler.instance().getMinecraftServerInstance().isPVPEnabled())) && burst.getColor() == 0XFFAF00)
					continue;
				if(burst.getColor() == 0XFFD700 && living instanceof EntityVoidHerrscher)
					continue;
				if(entity.ticksExisted % 3 == 0)
					ExtraBotanyAPI.dealTrueDamage(living, burst.getColor() == 0XFFD700 ? 1.8F : 2.2F);
				if(living.hurtTime == 0)
					living.attackEntityFrom(ItemRelic.damageSource(), burst.getColor() == 0XFFD700 ? 7F : 8F);
			}
			return;
		}
		
		int homeID = ItemNBTHelper.getInt(stack, TAG_HOME_ID, -1);
		if(homeID == -1) {
			AxisAlignedBB axis1 = new AxisAlignedBB(entity.posX -5F, entity.posY -5F, entity.posZ -5F, entity.lastTickPosX + 5F, entity.lastTickPosY + 5F, entity.lastTickPosZ + 5F);
			List<EntityLivingBase> entities = entity.world.getEntitiesWithinAABB(EntityLivingBase.class, axis1);
			for(EntityLivingBase living : entities) {
				if(living instanceof EntityPlayer || !(living instanceof IMob) || living.hurtTime != 0)
					continue;
				homeID = living.getEntityId();
				ItemNBTHelper.setInt(stack, TAG_HOME_ID, homeID);
				break;
			}
		}
	
		List<EntityLivingBase> entities = entity.world.getEntitiesWithinAABB(EntityLivingBase.class, axis);
		if(homeID != -1) {
			Entity home = entity.world.getEntityByID(homeID);
			if(home != null) {
				Vector3 vecEntity = Vector3.fromEntityCenter(home);
				Vector3 vecThis = Vector3.fromEntityCenter(entity);
				Vector3 vecMotion = vecEntity.subtract(vecThis);
				Vector3 vecCurrentMotion = new Vector3(entity.motionX, entity.motionY, entity.motionZ);
				vecMotion.normalize().multiply(vecCurrentMotion.mag());
				burst.setMotion(vecMotion.x, vecMotion.y, vecMotion.z);
			}
		}
		for(EntityLivingBase living : entities) {
			if(living instanceof EntityPlayer && (living.getName().equals(attacker) || FMLCommonHandler.instance().getMinecraftServerInstance() != null && !FMLCommonHandler.instance().getMinecraftServerInstance().isPVPEnabled()))
				continue;

			if(living.isEntityAlive()) {
				int cost = MANA_PER_DAMAGE / 3;
				int mana = burst.getMana();
				if(mana >= cost) {
					burst.setMana(mana - cost);
					float damage = BotaniaAPI.terrasteelToolMaterial.getAttackDamage() + 3F;
					if(!burst.isFake() && !entity.world.isRemote) {
						EntityPlayer player = living.world.getPlayerEntityByName(attacker);
						living.attackEntityFrom(player == null ? ItemRelic.damageSource() : DamageSource.causePlayerDamage(player), damage);
						ExtraBotanyAPI.dealTrueDamage(living, 3F);
						entity.setDead();
						break;
					}
				}
			}
		}
	}
	
	@Override
	public boolean doParticles(IManaBurst burst, ItemStack stack) {
		return true;
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
	public boolean collideBurst(IManaBurst burst, RayTraceResult arg1, boolean arg2, boolean dead, ItemStack arg4) {
		EntityThrowable entity = (EntityThrowable) burst;
		if(burst.getColor() == 0XFFAF00)
			entity.getEntityWorld().spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, entity.posX, entity.posY, entity.posZ, 1D, 0D, 0D);
		return dead;
	}
	
	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, @Nonnull ItemStack par2ItemStack) {
		return par2ItemStack.getItem() == ModItems.manaResource && par2ItemStack.getItemDamage() == 4 ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
	}

}
