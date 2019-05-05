package com.meteor.extrabotany.common.crafting.recipe.brew;

import com.meteor.extrabotany.common.item.ModItems;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.GameRegistry;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import javax.annotation.Nonnull;

public class HelmRevealingRecipe extends ShapelessRecipes {
	@GameRegistry.ObjectHolder("thaumcraft:goggles")
	private static Item goggles = null;

	private final Item botaniaHelm;

	public HelmRevealingRecipe(Item output, Item botaniaHelm) {
		super("extrabotany:helm_revealing", new ItemStack(output),
				NonNullList.from(Ingredient.EMPTY, Ingredient.fromItem(botaniaHelm), Ingredient.fromItem(goggles)));
		this.botaniaHelm = botaniaHelm;
	}

	@Nonnull
	@Override
	public ItemStack getCraftingResult(@Nonnull InventoryCrafting var1) {
		ItemStack helm = ItemStack.EMPTY;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty() && stack.getItem() == botaniaHelm)
				helm = stack;
		}

		if(helm.isEmpty())
			return ItemStack.EMPTY;

		ItemStack helmCopy = helm.copy();
		Item helmItem = helmCopy.getItem();

		ItemStack newHelm;

		if(helmItem == ModItems.cmhelm)
			newHelm = new ItemStack(ModItems.cmhelmrevealing);
		else if(helmItem == ModItems.cosmhelm)
			newHelm = new ItemStack(ModItems.coshelmrevealing);
		else return ItemStack.EMPTY;

		//Copy Enchantments
		NBTTagList enchList = ItemNBTHelper.getList(helmCopy, "ench", 10, true);
		if(enchList != null)
			ItemNBTHelper.setList(newHelm, "ench", enchList);
		copyTCData(helmCopy, newHelm);

		return newHelm;
	}

	@Override
	public boolean isDynamic() {
		return true;
	}

	private static final String TAG_RUNIC = "TC.RUNIC";
	private static final String TAG_WARP = "TC.WARP";
	private static final String TAG_INFUSION_ENCH = "infench";

	/**
	 * Copies Thaumcraft's infusion enchantments, runic shielding,
	 * and warping (which is sometimes added as a side effect of infusion enchanting)
	 */
	public static void copyTCData(ItemStack source, ItemStack destination) {
		byte runicShielding = ItemNBTHelper.getByte(source, TAG_RUNIC, (byte) 0);
		if(runicShielding != 0)
			ItemNBTHelper.setByte(destination, TAG_RUNIC, runicShielding);

		byte warp = ItemNBTHelper.getByte(source, TAG_WARP, (byte) 0);
		if(warp != 0)
			ItemNBTHelper.setByte(destination, TAG_WARP, warp);

		NBTTagList infEnchList = ItemNBTHelper.getList(source, TAG_INFUSION_ENCH, 10, true);
		if(infEnchList != null)
			ItemNBTHelper.setList(destination, TAG_INFUSION_ENCH, infEnchList);
	}
}
