package com.meteor.extrabotany.common.item.bonus;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.api.item.Bonus;
import com.meteor.extrabotany.api.item.WeightCategory;
import com.meteor.extrabotany.common.item.ItemMod;
import com.meteor.extrabotany.common.lib.LibItemsName;
import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.ModItems;

public class ItemRewardBag extends ItemMod{
	
	final int types = 4;
	
	private static final String TAG_WEIGHT = "weight";
	private static Random random = new Random();  
	private static List<WeightCategory> categorysA = new ArrayList<WeightCategory>();
	private static List<WeightCategory> categorysB = new ArrayList<WeightCategory>();
	private static List<WeightCategory> categorysC = new ArrayList<WeightCategory>();
	private static List<WeightCategory> categorysD = new ArrayList<WeightCategory>();

	public ItemRewardBag() {
		super(LibItemsName.REWARD_BAG);
		setHasSubtypes(true);
		//A
		for(int i = 0; i < 16; i++)
			Bonus.addItem(new ItemStack(ModItems.petal, 6, i), 1, categorysA);
		//B
		for(int i = 0; i < 4; i++)
			Bonus.addItem(new ItemStack(ModItems.rune, 2, i), 5, categorysB);
		for(int i = 4; i < 8; i++)
			Bonus.addItem(new ItemStack(ModItems.rune, 1, i), 3, categorysB);
		for(int i = 8; i < 16; i++)
			Bonus.addItem(new ItemStack(ModItems.rune, 1, i), 1, categorysB);
		//C
		for(int i = 0; i < 3; i++)
			Bonus.addItem(new ItemStack(ModItems.manaResource, 5, i), 12, categorysC);
		Bonus.addItem(new ItemStack(ModItems.manaResource, 1, 4), 9, categorysC);
		Bonus.addItem(new ItemStack(ModItems.manaResource, 12, 23), 10, categorysC);
		Bonus.addItem(new ItemStack(ModItems.manaResource, 1, 14), 7, categorysC);
		Bonus.addItem(new ItemStack(ModItems.manaResource, 4, 5), 8, categorysC);
		for(int i = 7; i < 10; i++)
			Bonus.addItem(new ItemStack(ModItems.manaResource, 3, i), 11, categorysC);
		Bonus.addItem(new ItemStack(com.meteor.extrabotany.common.item.ModItems.material, 1, 3), 1, categorysC);
		//D
		Bonus.addItem(new ItemStack(Items.IRON_INGOT, 8), 8, categorysD);
		Bonus.addItem(new ItemStack(Items.DIAMOND, 2), 1, categorysD);
		Bonus.addItem(new ItemStack(Items.COAL, 14), 10, categorysD);
		Bonus.addItem(new ItemStack(Items.GOLD_INGOT, 6), 6, categorysD);
		Bonus.addItem(new ItemStack(ModItems.overgrowthSeed, 2), 2, categorysD);
		Bonus.addItem(new ItemStack(ModItems.blackLotus, 3), 2, categorysD);
		Bonus.addItem(new ItemStack(Items.ENDER_PEARL, 4), 6, categorysD);
		Bonus.addItem(new ItemStack(Items.REDSTONE, 16), 6, categorysD);
	}
	
	@Nonnull
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return "item." + LibItemsName.REWARD_BAGS_NAMES[Math.min(types - 1, par1ItemStack.getItemDamage())];
	}
	
	@Override
	public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> stacks) {
		if(isInCreativeTab(tab)) {
			for(int i = 0; i < types; i++) {
				stacks.add(new ItemStack(this, 1, i));
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		for (int i = 0; i < LibItemsName.REWARD_BAGS_NAMES.length; i++) {
			if (!"UNUSED".equals(LibItemsName.REWARD_BAGS_NAMES[i])) {
				ModelLoader.setCustomModelResourceLocation(
					this, i,
					new ModelResourceLocation(LibMisc.MOD_ID + ":" + LibItemsName.REWARD_BAGS_NAMES[i], "inventory")
				);
			}
		}
	}
	
	public List<WeightCategory> getWeightCategory(int meta){
		switch(meta){
			case 0:
				return categorysA;
			case 1:
				return categorysB;
			case 2:
				return categorysC;
			case 3:
				return categorysD;
		}
		return null;
	}
	
	public void setSum(ItemStack stack, int i){
		ItemNBTHelper.setInt(stack, TAG_WEIGHT, i);
	}
	
	public int getSum(ItemStack stack){
		return ItemNBTHelper.getInt(stack, TAG_WEIGHT, 0);
	}
	
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if(world.isRemote)
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		if(rollItem(stack) != null){
			ItemStack newstack = rollItem(stack).copy();
			world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 0.5F, 0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));
			player.inventory.addItemStackToInventory(newstack);
			stack.shrink(1);
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		}
		return ActionResult.newResult(EnumActionResult.PASS, stack);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack, World world, List<String> stacks, ITooltipFlag flags) {
		DecimalFormat df = new DecimalFormat("0.00%");
		int weightSum = 0;
    	for (WeightCategory wc : getWeightCategory(par1ItemStack.getMetadata())) {  
            weightSum += wc.getWeight();  
        }
    	setSum(par1ItemStack, weightSum);
		if(GuiScreen.isShiftKeyDown()){
			addStringToTooltip(I18n.format("extrabotany.bonusbase"), stacks);
			for (WeightCategory wc : getWeightCategory(par1ItemStack.getMetadata())) {
				String num = df.format((float)wc.getWeight()/getSum(par1ItemStack));
				String name = I18n.format(wc.getCategory().getItem().getUnlocalizedNameInefficiently(wc.getCategory()) + ".name");
				addStringToTooltip(name +"x"+ wc.getCategory().getCount() + " " + num, stacks);
			}
		}else
			addStringToTooltip(I18n.format("botaniamisc.shiftinfo"), stacks);
	}
	
	void addStringToTooltip(String s, List<String> tooltip) {
		tooltip.add(s.replaceAll("&", "\u00a7"));
	}
	
	@Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected){
    	super.onUpdate(stack, world, entity, itemSlot, isSelected);
    	setSum(stack, Bonus.sum(getWeightCategory(stack.getMetadata())));
	}
	
	public ItemStack rollItem(ItemStack stack){
        int n = random.nextInt(getSum(stack));
        int m = 0;  
        for (WeightCategory wc : getWeightCategory(stack.getMetadata())) {  
             if (m <= n && n < m + wc.getWeight()) {  
               return wc.getCategory();  
             }  
             m += wc.getWeight();  
        }
		return null;  
	}

}
