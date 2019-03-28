package com.meteor.extrabotany.common.item.equipment.shield;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.ExtraBotanyCreativeTab;
import com.meteor.extrabotany.client.render.IModelReg;
import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemShieldCopy extends ItemShield implements IModelReg{
	
	private ToolMaterial material;
	private String name;

	public ItemShieldCopy(ToolMaterial material, String name) {
		setCreativeTab(ExtraBotanyCreativeTab.INSTANCE);
		setRegistryName(new ResourceLocation(LibMisc.MOD_ID, name));
		setUnlocalizedName(name);
		this.name = name;
		this.material = material;
		this.setMaxDamage(material.getMaxUses() * 2);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack){
		return I18n.translateToLocal("item.extrabotany:" + name + ".name");
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
	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		playerIn.setActiveHand(hand);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BLOCK;
	}

	@Override
	public boolean isShield(ItemStack stack, EntityLivingBase entity) {
		return true;
	}

	public int getShieldBlockingCooldown(ItemStack stack, EntityLivingBase attacked, float damage, DamageSource source) {
		return 0;
	}

	public void onAttackBlocked(ItemStack stack, EntityLivingBase attacked, float damage, DamageSource source) {
		if(!attacked.world.isRemote && source.getTrueSource() instanceof EntityLivingBase) {
			EntityLivingBase attacker = (EntityLivingBase) source.getTrueSource();
			ItemStack attackerItem = attacker.getHeldItemMainhand();
			if(!attackerItem.isEmpty() && attackerItem.getItem().canDisableShield(attackerItem, stack, attacked, attacker)) {
				float attackStrength = attacker instanceof EntityPlayer ? ((EntityPlayer)attacker).getCooledAttackStrength(0.5F) : 1.0F;
				float criticalChance = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(attacker) * 0.05F;
				if(attacker.isSprinting() && attackStrength > 0.9F) {
					criticalChance += 0.75F;
				}
				if (attacked.world.rand.nextFloat() < criticalChance) {
					if(attacked instanceof EntityPlayer) {
						((EntityPlayer)attacked).getCooldownTracker().setCooldown(this, 100);
						attacked.stopActiveHand();
					}
					//Shield break sound effect
					attacked.world.setEntityState(attacked, (byte)30);
				}
			}
		}
	}

	public float getBlockedDamage(ItemStack stack, EntityLivingBase attacked, float damage, DamageSource source) {
		//float multiplier = 0.4F - Math.min(this.material.getAttackDamage() / 3.0F, 1.0F) * 0.4F;
		//return Math.min(damage * multiplier, 8.0F);
		return 0.0F;
	}

	public float getDefenderKnockbackMultiplier(ItemStack stack, EntityLivingBase attacked, float damage, DamageSource source) {
		//Uses durability as "weight"
		return 0.6F - Math.min(this.material.getMaxUses() / 2500.0F, 1.0F) * 0.6F;
	}

	public float getAttackerKnockbackMultiplier(ItemStack stack, EntityLivingBase attacked, float damage, DamageSource source) {
		return 0.6F;
	}

	public boolean canBlockDamageSource(ItemStack stack, EntityLivingBase attacked, EnumHand hand, DamageSource source) {
		if (attacked.isHandActive() && attacked.getActiveHand() == hand && !source.isUnblockable() && attacked.isActiveItemStackBlocking()) {
			Vec3d vec3d = source.getDamageLocation();
			if (vec3d != null) {
				Vec3d vec3d1 = attacked.getLook(1.0F);
				Vec3d vec3d2 = vec3d.subtractReverse(new Vec3d(attacked.posX, attacked.posY, attacked.posZ)).normalize();
				vec3d2 = new Vec3d(vec3d2.x, 0.0D, vec3d2.z);
				if (vec3d2.dotProduct(vec3d1) < 0.0D) {
					return true;
				}
			}
		}
		return false;
	}

	public static enum EventHandler {
		INSTANCE;

		private boolean ignoreEvent = false;

		@SubscribeEvent
		public void onLivingAttacked(LivingAttackEvent event) {
			if(this.ignoreEvent) {
				return;
			}
			this.ignoreEvent = true;
			EntityLivingBase attacked = event.getEntityLiving();
			DamageSource source = event.getSource();
			for(EnumHand hand : EnumHand.values()) {
				ItemStack stack = attacked.getHeldItem(hand);
				if(!stack.isEmpty() && stack.getItem() instanceof ItemShieldCopy) {
					ItemShieldCopy shield = (ItemShieldCopy) stack.getItem();

					if(shield.canBlockDamageSource(stack, attacked, hand, source)) {
						//Cancel event
						if(!attacked.world.isRemote) {
							event.setCanceled(true);
						}

						if(!attacked.world.isRemote) {
							//Apply damage with multiplier
							float defenderKbMultiplier = shield.getDefenderKnockbackMultiplier(stack, attacked, event.getAmount(), source);
							float newDamage = shield.getBlockedDamage(stack, attacked, event.getAmount(), source);
							if(newDamage > 0.0F) {
								double prevMotionX = attacked.motionX;
								double prevMotionY = attacked.motionY;
								double prevMotionZ = attacked.motionZ;
								DamageSource newSource;
								//getDamageLocation() == null so that vanilla shield blocking does not happen
								if(source instanceof EntityDamageSourceIndirect) {
									newSource = new EntityDamageSourceIndirect(source.damageType, source.getImmediateSource(), source.getTrueSource()) {
										@Override
										public Vec3d getDamageLocation() {
											return null;
										}
									};
								} else if(source instanceof EntityDamageSource) {
									newSource = new EntityDamageSource(source.damageType, source.getTrueSource()) {
										@Override
										public Vec3d getDamageLocation() {
											return null;
										}
									};
								} else {
									newSource = new DamageSource(source.damageType) {
										@Override
										public Vec3d getDamageLocation() {
											return null;
										}
									};
								}
								if(source.isDamageAbsolute()) {
									newSource.setDamageIsAbsolute();
								}
								if(source.isUnblockable()) {
									newSource.setDamageBypassesArmor();
								}
								if(source.isFireDamage()) {
									newSource.setFireDamage();
								}
								if(source.isMagicDamage()) {
									newSource.setMagicDamage();
								}
								if(source.isDifficultyScaled()) {
									newSource.setDifficultyScaled();
								}
								if(source.isExplosion()) {
									newSource.setExplosion();
								}
								if(source.isProjectile()) {
									newSource.setProjectile();
								}
								attacked.attackEntityFrom(newSource, newDamage);
								attacked.motionX = prevMotionX;
								attacked.motionY = prevMotionY;
								attacked.motionZ = prevMotionZ;
							}
							if(source.getTrueSource() != null) {
								//Knock back defender
								double prevMotionY = attacked.motionY;
								attacked.knockBack(source.getTrueSource(), defenderKbMultiplier, source.getTrueSource().posX - attacked.posX, source.getTrueSource().posZ - attacked.posZ);
								attacked.motionY = prevMotionY;
								attacked.velocityChanged = true;
							}
							//Shield block sound effect
							attacked.world.setEntityState(attacked, (byte)29);
						}

						//Knock back attacker
						if(!attacked.world.isRemote) {
							if (source.getTrueSource() == source.getImmediateSource() && source.getTrueSource() instanceof EntityLivingBase) {
								float attackerKbMultiplier = shield.getAttackerKnockbackMultiplier(stack, attacked, event.getAmount(), source);
								if(attackerKbMultiplier > 0.0F) {
									((EntityLivingBase)source.getTrueSource()).knockBack(attacked, attackerKbMultiplier, attacked.posX - source.getTrueSource().posX, attacked.posZ - source.getTrueSource().posZ);
								}
							}
						}

						if(attacked instanceof EntityPlayer) {
							int cooldown = shield.getShieldBlockingCooldown(stack, (EntityPlayer)attacked, event.getAmount(), source);
							if(cooldown > 0) {
								((EntityPlayer)attacked).getCooldownTracker().setCooldown(shield, cooldown);
								attacked.stopActiveHand();
							}
						}

						shield.onAttackBlocked(stack, attacked, event.getAmount(), source);

						if(!attacked.world.isRemote) {
							//Damage item
							int itemDamage = 1 + MathHelper.floor(event.getAmount());
							stack.damageItem(itemDamage, attacked);
							//Shield broke
							if (stack.getCount() <= 0) {
								EnumHand enumhand = attacked.getActiveHand();
								if(attacked instanceof EntityPlayer)
									net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem((EntityPlayer)attacked, stack, enumhand);
								if (enumhand == EnumHand.MAIN_HAND)
									attacked.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
								else
									attacked.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, ItemStack.EMPTY);
								//Shield break sound effect
								attacked.world.setEntityState(attacked, (byte)30);
							}
						}

						break;
					}
				}
			}
			this.ignoreEvent = false;
		}
	}
}
