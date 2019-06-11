package com.meteor.extrabotany.common.item.lens;

import java.awt.Color;
import java.util.List;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.client.core.handler.ModelHandler;
import com.meteor.extrabotany.common.item.ItemMod;
import com.meteor.extrabotany.common.lib.LibItemsName;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.api.brew.IBrewItem;
import vazkii.botania.api.internal.IManaBurst;
import vazkii.botania.api.mana.BurstProperties;
import vazkii.botania.api.mana.ICompositableLens;
import vazkii.botania.api.mana.ILens;
import vazkii.botania.api.mana.ILensControl;
import vazkii.botania.api.mana.IManaReceiver;
import vazkii.botania.api.mana.IManaSpreader;
import vazkii.botania.api.mana.ITinyPlanetExcempt;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.lens.Lens;

public class ItemLens extends ItemMod implements ILensControl, ICompositableLens, ITinyPlanetExcempt, IBrewItem{

	public static final int SUBTYPES = 6;

	public static final int PUSH = 0,
			SMELT = 1,
			MANA = 2,
			POTION = 3,
			CLOUD = 4,
			SEXPISTOL = 5;

	private static final int PROP_NONE = 0,
			PROP_POWER = 1,
			PROP_ORIENTATION = 1 << 1,
			PROP_TOUCH = 1 << 2,
			PROP_INTERACTION = 1 << 3,
			PROP_DAMAGE = 1 << 4,
			PROP_CONTROL = 1 << 5;

	private static final int[] props = new int[SUBTYPES];
	private static final Lens[] lenses = new Lens[SUBTYPES];
	private static final Lens fallbackLens = new Lens();

	static {
		setProps(PUSH, PROP_NONE);
		setProps(SMELT, PROP_NONE);
		setProps(MANA, PROP_NONE);
		setProps(POTION, PROP_NONE);
		setProps(CLOUD, PROP_NONE);
		setProps(SEXPISTOL, PROP_CONTROL);

		setLens(PUSH, new LensPush());
		setLens(SMELT, new LensSmelt());
		setLens(MANA, new LensMana());
		setLens(POTION, new LensPotion());
		setLens(CLOUD, new LensCloud());
		setLens(SEXPISTOL, new LensSexpistol());
	}

	private static final String TAG_COLOR = "color";
	private static final String TAG_COMPOSITE_LENS = "compositeLens";
	private static final String TAG_BREW_KEY = "brewKey";
	
	public ItemLens() {
		super(LibItemsName.LENS);
		setMaxStackSize(1);
		setHasSubtypes(true);
	}

	@Override
	public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> stacks) {
		if(isInCreativeTab(tab)) {
			for(int i = 0; i < SUBTYPES; i++)
				stacks.add(new ItemStack(this, 1, i));
		}
	}

	@Nonnull
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item." + LibItemsName.LENSES[Math.min(SUBTYPES - 1, stack.getItemDamage())];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> stacks, ITooltipFlag flags) {
		int storedColor = getStoredColor(stack);
		if(storedColor != -1)
			stacks.add(I18n.format("botaniamisc.color", I18n.format("botania.color" + storedColor)));
		Brew brew = getBrew(stack);
		if(brew != null)
			for(PotionEffect effect : brew.getPotionEffects(stack)) {
				TextFormatting format = effect.getPotion().isBadEffect() ? TextFormatting.RED : TextFormatting.GRAY;
				stacks.add(format + I18n.format(effect.getEffectName()) + (effect.getAmplifier() == 0 ? "" : " " + I18n.format("botania.roman" + (effect.getAmplifier() + 1))) + TextFormatting.GRAY + (effect.getPotion().isInstant() ? "" : " (" + Potion.getPotionDurationString(effect, 1) + ")"));
			}
	}


	private String getItemShortTermName(ItemStack stack) {
		return I18n.format(stack.getUnlocalizedName().replaceAll("item.", "item.botania:") + ".short");
	}

	@Nonnull
	@Override
	public String getItemStackDisplayName(@Nonnull ItemStack stack) {
		ItemStack compositeLens = getCompositeLens(stack);
		if(compositeLens.isEmpty())
			return super.getItemStackDisplayName(stack);
		return String.format(I18n.format("item.botania:compositeLens.name", getItemShortTermName(stack), getItemShortTermName(compositeLens)));
	}

	@Override
	public void apply(ItemStack stack, BurstProperties props) {
		int storedColor = getStoredColor(stack);
		if(storedColor != -1)
			props.color = getLensColor(stack);

		getLens(stack.getItemDamage()).apply(stack, props);

		ItemStack compositeLens = getCompositeLens(stack);
		if(!compositeLens.isEmpty() && compositeLens.getItem() instanceof ILens)
			((ILens) compositeLens.getItem()).apply(compositeLens, props);
	}

	@Override
	public boolean collideBurst(IManaBurst burst, RayTraceResult pos, boolean isManaBlock, boolean dead, ItemStack stack) {
		EntityThrowable entity = (EntityThrowable) burst;

		dead = getLens(stack.getItemDamage()).collideBurst(burst, entity, pos, isManaBlock, dead, stack);

		ItemStack compositeLens = getCompositeLens(stack);
		if(!compositeLens.isEmpty() && compositeLens.getItem() instanceof ILens)
			dead = ((ILens) compositeLens.getItem()).collideBurst(burst, pos, isManaBlock, dead, compositeLens);

		return dead;
	}

	@Override
	public void updateBurst(IManaBurst burst, ItemStack stack) {
		EntityThrowable entity = (EntityThrowable) burst;
		int storedColor = getStoredColor(stack);

		if(storedColor == 16 && entity.world.isRemote)
			burst.setColor(getLensColor(stack));

		getLens(stack.getItemDamage()).updateBurst(burst, entity, stack);

		ItemStack compositeLens = getCompositeLens(stack);
		if(!compositeLens.isEmpty() && compositeLens.getItem() instanceof ILens)
			((ILens) compositeLens.getItem()).updateBurst(burst, compositeLens);
	}

	@Override
	public int getLensColor(ItemStack stack) {
		int storedColor = getStoredColor(stack);

		if(storedColor == -1)
			return 0xFFFFFF;

		if(storedColor == 16)
			return Color.HSBtoRGB(Botania.proxy.getWorldElapsedTicks() * 2 % 360 / 360F, 1F, 1F);

		return EnumDyeColor.byMetadata(storedColor).getColorValue();
	}

	public static int getStoredColor(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_COLOR, -1);
	}

	public static void setLensColor(ItemStack stack, int color) {
		ItemNBTHelper.setInt(stack, TAG_COLOR, color);
	}

	@Override
	public boolean doParticles(IManaBurst burst, ItemStack stack) {
		return true;
	}

	public static void setProps(int lens, int props_) {
		props[lens] = props_;
	}

	public static void setLens(int index, Lens lens) {
		lenses[index] = lens;
	}

	public static boolean isBlacklisted(ItemStack lens1, ItemStack lens2) {
		ICompositableLens item1 = (ICompositableLens) lens1.getItem();
		ICompositableLens item2 = (ICompositableLens) lens2.getItem();
		return (item1.getProps(lens1) & item2.getProps(lens2)) != 0;
	}

	public static Lens getLens(int index) {

		if(index < 0 || index >= lenses.length)
			return fallbackLens;

		Lens lens = lenses[index];
		return lens == null ? fallbackLens : lens;
	}

	@Override
	public boolean canCombineLenses(ItemStack sourceLens, ItemStack compositeLens) {
		ICompositableLens sourceItem = (ICompositableLens) sourceLens.getItem();
		ICompositableLens compositeItem = (ICompositableLens) compositeLens.getItem();
		if(sourceItem == compositeItem && sourceLens.getItemDamage() == compositeLens.getItemDamage())
			return false;

		if(!sourceItem.isCombinable(sourceLens) || !compositeItem.isCombinable(compositeLens))
			return false;

		if(isBlacklisted(sourceLens, compositeLens))
			return false;

		return true;
	}

	@Override
	public ItemStack getCompositeLens(ItemStack stack) {
		NBTTagCompound cmp = ItemNBTHelper.getCompound(stack, TAG_COMPOSITE_LENS, true);
		if(cmp == null)
			return ItemStack.EMPTY;
		else return new ItemStack(cmp);
	}

	@Override
	public ItemStack setCompositeLens(ItemStack sourceLens, ItemStack compositeLens) {
		if(!compositeLens.isEmpty()) {
			NBTTagCompound cmp = compositeLens.writeToNBT(new NBTTagCompound());
			ItemNBTHelper.setCompound(sourceLens, TAG_COMPOSITE_LENS, cmp);
		}
		return sourceLens;
	}

	@Override
	public int getManaToTransfer(IManaBurst burst, EntityThrowable entity, ItemStack stack, IManaReceiver receiver) {
		return getLens(stack.getItemDamage()).getManaToTransfer(burst, entity, stack, receiver);
	}
	
	@Override
	public boolean shouldPull(ItemStack stack) {
		return true;
	}

	@Override
	public boolean isControlLens(ItemStack stack) {
		return getProps(stack) != 0;
	}

	@Override
	public boolean allowBurstShooting(ItemStack stack, IManaSpreader spreader, boolean redstone) {
		return getLens(stack.getItemDamage()).allowBurstShooting(stack, spreader, redstone);
	}

	@Override
	public void onControlledSpreaderTick(ItemStack stack, IManaSpreader spreader, boolean redstone) {
		getLens(stack.getItemDamage()).onControlledSpreaderTick(stack, spreader, redstone);
	}

	@Override
	public void onControlledSpreaderPulse(ItemStack stack, IManaSpreader spreader, boolean redstone) {
		getLens(stack.getItemDamage()).onControlledSpreaderPulse(stack, spreader, redstone);
	}

	@Override
	public int getProps(ItemStack stack) {
		return props[stack.getItemDamage()];
	}

	@Override
	public boolean isCombinable(ItemStack stack) {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		ModelHandler.registerItemMetas(this, LibItemsName.LENSES.length, i -> LibItemsName.LENSES[i]);
	}

	@Override
	public Brew getBrew(ItemStack stack) {
		String key = ItemNBTHelper.getString(stack, TAG_BREW_KEY, "");
		return BotaniaAPI.getBrewFromKey(key);
	}

	public static void setBrew(ItemStack stack, Brew brew) {
		setBrew(stack, (brew == null ? BotaniaAPI.fallbackBrew : brew).getKey());
	}

	public static void setBrew(ItemStack stack, String brew) {
		ItemNBTHelper.setString(stack, TAG_BREW_KEY, brew);
	}

}
