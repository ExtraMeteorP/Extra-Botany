package com.meteor.extrabotany.common.item.equipment.tool;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.meteor.extrabotany.common.lib.LibItemsName;
import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.item.ISequentialBreaker;
import vazkii.botania.api.mana.IManaGivingItem;
import vazkii.botania.api.mana.IManaItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.core.handler.ModSounds;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

public class ItemHammerUltimate extends ItemHammer implements IManaItem, ISequentialBreaker{
	
	private static final List<Material> MATERIALS = Arrays.asList(Material.ROCK, Material.IRON, Material.ICE, Material.GLASS, Material.PISTON, Material.ANVIL, Material.GRASS, Material.GROUND, Material.SAND, Material.SNOW, Material.CRAFTED_SNOW, Material.CLAY);

	private static final String TAG_ENABLED = "enabled";
	private static final String TAG_MANA = "mana";
	private static final String TAG_REPAIRUPGRADE = "repair";
	private static final String TAG_DAMAGEUPGRADE = "damage";
	private static final String TAG_RANGE = "range";
	
	private static final int MAX_MANA = 50000;
	private static final int MANA_PER_DAMAGE = 80;
	
	public ItemHammerUltimate() {
		super(LibItemsName.HAMMER_ULTIMATE, BotaniaAPI.terrasteelToolMaterial);
		setMaxDamage(3000);
		addPropertyOverride(new ResourceLocation(LibMisc.MOD_ID, "enabled"), (itemStack, world, entityLivingBase) -> isEnabled(itemStack) ? 1 : 0);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack, World world, List<String> stacks, ITooltipFlag flags) {
		String repair = I18n.format("extrabotany.repairupgrade" + getRepair(par1ItemStack));
		String attack = I18n.format("extrabotany.damageupgrade" + getAttack(par1ItemStack));
		String range = I18n.format("extrabotany.rangeupgrade");
		if(getAttack(par1ItemStack)>0)
			stacks.add(attack);
		if(getRepair(par1ItemStack)>0)
			stacks.add(repair);
		if(hasRange(par1ItemStack))
			stacks.add(range);
	}
	
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if(hasRange(stack)){
			setEnabled(stack, !isEnabled(stack));
			if(!world.isRemote)
				world.playSound(null, player.posX, player.posY, player.posZ, ModSounds.terraPickMode, SoundCategory.PLAYERS, 0.5F, 0.4F);
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

	@Nonnull
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float sx, float sy, float sz) {
		return player.isSneaking() ? super.onItemUse(player, world, pos, hand, side, sx, sy, sz)
				: EnumActionResult.PASS;
	}
	
	@Override
	public int getEntityLifespan(ItemStack itemStack, World world) {
		return Integer.MAX_VALUE;
	}
	
	public static void setRepair(ItemStack stack, int mana) {
		ItemNBTHelper.setInt(stack, TAG_REPAIRUPGRADE, mana);
	}

	public int getRepair(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_REPAIRUPGRADE, 0);
	}
	
	public static void setAttack(ItemStack stack, int mana) {
		ItemNBTHelper.setInt(stack, TAG_DAMAGEUPGRADE, mana);
	}

	public int getAttack(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_DAMAGEUPGRADE, 0);
	}
	
	@Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker){
		if(getAttack(stack) > 0 && ManaItemHandler.requestManaExact(stack, (EntityPlayer)attacker, (80 * getAttack(stack) - getRepair(stack) * 15), true)){
			if(getAttack(stack) > 1)
				target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 1));
			if(getAttack(stack) > 3)
				target.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100, 1));
			if(getAttack(stack) > 5)
				target.addPotionEffect(new PotionEffect(MobEffects.UNLUCK, 100, 2));
			if(getAttack(stack) > 7)
				target.addPotionEffect(new PotionEffect(MobEffects.WITHER, 100, 2));
			if(getAttack(stack) > 9)
				attacker.heal(4F);
			target.attackEntityFrom(DamageSource.MAGIC, 2F * getAttack(stack));
		}
        return true;
    }
	
	public static void setMana(ItemStack stack, int mana) {
		ItemNBTHelper.setInt(stack, TAG_MANA, mana);
	}

	@Override
	public int getMana(ItemStack stack) {
		return getMana_(stack);
	}

	public static int getMana_(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_MANA, 0);
	}
	
	@Override
	public int getMaxMana(ItemStack stack) {
		return MAX_MANA;
	}
	public static boolean hasRange(ItemStack stack) {
		return ItemNBTHelper.getBoolean(stack, TAG_RANGE, false);
	}

	public void setRange(ItemStack stack, boolean enabled) {
		ItemNBTHelper.setBoolean(stack, TAG_RANGE, enabled);
	}
	
	public static boolean isEnabled(ItemStack stack) {
		return ItemNBTHelper.getBoolean(stack, TAG_ENABLED, false);
	}

	void setEnabled(ItemStack stack, boolean enabled) {
		ItemNBTHelper.setBoolean(stack, TAG_ENABLED, enabled);
	}

	@Override
	public void addMana(ItemStack stack, int mana) {
		setMana(stack, Math.min(getMana(stack) + mana, MAX_MANA));
	}
	
	@Override
	public boolean canReceiveManaFromPool(ItemStack stack, TileEntity pool) {
		return true;
	}

	@Override
	public boolean canReceiveManaFromItem(ItemStack stack, ItemStack otherStack) {
		return !(otherStack.getItem() instanceof IManaGivingItem);
	}

	@Override
	public boolean canExportManaToPool(ItemStack stack, TileEntity pool) {
		return false;
	}

	@Override
	public boolean canExportManaToItem(ItemStack stack, ItemStack otherStack) {
		return false;
	}

	@Override
	public boolean isNoExport(ItemStack stack) {
		return true;
	}


	@Override
	public void onUpdate(ItemStack par1ItemStack, World world, Entity par3Entity, int par4, boolean par5) {
		if(!world.isRemote && par3Entity.ticksExisted % (40 - getRepair(par1ItemStack) * 10) == 0 && ManaItemHandler.requestManaExact(par1ItemStack, (EntityPlayer)par3Entity, (getManaPerDamage() - getRepair(par1ItemStack) * 5), true) && par1ItemStack.getItemDamage() > 0){
			par1ItemStack.setItemDamage(par1ItemStack.getItemDamage() - 1);
		}
	}
	
	public static int getManaPerDamage(){
		return MANA_PER_DAMAGE;
	}

	public Set<String> getToolClasses(ItemStack stack) {
		return ImmutableSet.of("pickaxe", "spade", "axe");
	}
	
	@Override
	public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {
		RayTraceResult raycast = ToolCommons.raytraceFromEntity(player.world, player, true, 10);
		if(!player.world.isRemote && raycast != null) {
			breakOtherBlock(player, stack, pos, pos, raycast.sideHit);
		}

		return false;
	}

	@Override
	public void breakOtherBlock(EntityPlayer player, ItemStack stack, BlockPos pos, BlockPos originPos, EnumFacing side) {
		if(!isEnabled(stack))
			return;
		
		World world = player.world;
		Material mat = world.getBlockState(pos).getMaterial();
		if(!MATERIALS.contains(mat))
			return;

		if(world.isAirBlock(pos))
			return;

		int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
		boolean silk = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0;
		boolean doX = side.getFrontOffsetX() == 0;
		boolean doY = side.getFrontOffsetY() == 0;
		boolean doZ = side.getFrontOffsetZ() == 0;

		int level = 2;
		int range = level - 1;
		int rangeY = Math.max(1, range);

		Vec3i beginDiff = new Vec3i(doX ? -range : 0, doY ? -1 : 0, doZ ? -range : 0);
		Vec3i endDiff = new Vec3i(doX ? range : 0, doY ? rangeY * 2 - 1 : 0, doZ ? range : 0);
		ToolCommons.removeBlocksInIteration(player, stack, world, pos, beginDiff, endDiff, state -> MATERIALS.contains(state.getMaterial()), silk);
		stack.damageItem(8, player);

	}
	
	private static HashSet<Block> effectiveAgainst = Sets.newHashSet(new Block[] {
			Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, 
			Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER, 
			Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE,Blocks.ACTIVATOR_RAIL, 
			Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, 
			Blocks.DIAMOND_ORE, Blocks.DOUBLE_STONE_SLAB, Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK, 
			Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, 
			Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, 
			Blocks.PACKED_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, 
			Blocks.STONE, Blocks.STONE_SLAB, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE, Blocks.CLAY, 
			Blocks.DIRT, Blocks.FARMLAND, Blocks.GRASS, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, 
			Blocks.SNOW, Blocks.SNOW_LAYER, Blocks.SOUL_SAND, Blocks.GRASS_PATH}
	);

	@Override
	public boolean canHarvestBlock(IBlockState blockIn) {
			return effectiveAgainst.contains(blockIn) ? true : super.canHarvestBlock(blockIn);
	}

	@Override
	public boolean disposeOfTrashBlocks(ItemStack arg0) {
		return false;
	}

}
