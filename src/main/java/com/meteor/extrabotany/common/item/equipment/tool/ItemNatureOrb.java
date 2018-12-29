package com.meteor.extrabotany.common.item.equipment.tool;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import com.meteor.extrabotany.api.item.INatureOrb;
import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.entity.gaia.EntityGaiaIII;
import com.meteor.extrabotany.common.item.equipment.bauble.ItemBauble;
import com.meteor.extrabotany.common.lib.LibItemsName;

import baubles.api.BaubleType;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketRemoveEntityEffect;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.mana.IManaGivingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class ItemNatureOrb extends ItemBauble implements INatureOrb, IManaGivingItem{
	
	public static final String TAG_XP = "xp";
	public static int max = 500000;

	public ItemNatureOrb() {
		super(LibItemsName.NATUREORB);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addHiddenTooltip(ItemStack stack, World world, List<String> stacks, ITooltipFlag flags) {
		String key = vazkii.botania.client.core.helper.RenderHelper.getKeyDisplayString("Baubles Inventory");
		String i = I18n.format("extrabotany.natureorb");
		String ci = i + getXP(stack) + "/" + max;
		String a = I18n.format("extrabotany.natureorbeffect1"+ (getXP(stack) > 100000));
		String b = I18n.format("extrabotany.natureorbeffect2"+ (getXP(stack) > 300000));
		String c = I18n.format("extrabotany.natureorbeffect3"+ (getXP(stack) > 400000));

		if(key != null)
			addStringToTooltip(I18n.format("botania.baubletooltip", key), stacks);

		ItemStack cosmetic = getCosmeticItem(stack);
		if(!cosmetic.isEmpty())
			addStringToTooltip(I18n.format("botaniamisc.hasCosmetic", cosmetic.getDisplayName()), stacks);

		if(hasPhantomInk(stack))
			addStringToTooltip(I18n.format("botaniamisc.hasPhantomInk"), stacks);
		addStringToTooltip(ci, stacks);
		addStringToTooltip(a, stacks);
		addStringToTooltip(b, stacks);
		addStringToTooltip(c, stacks);
	}
	
	@Nonnull
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if(player.isSneaking() && getXP(player.getHeldItem(hand)) >= 100000 && ConfigHandler.GAIA_ENABLE){
			return EntityGaiaIII.spawn(player, player.getHeldItem(hand), worldIn, pos, false) ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
		}
		return EnumActionResult.SUCCESS;
	}
	
	@Override
	public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> list) {
		if(isInCreativeTab(tab)) {
			list.add(new ItemStack(this));
			ItemStack s = new ItemStack(this);
			setXP(s, getMaxXP(s));
			list.add(s);
		}
	}
	
	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase entity) {
		super.onWornTick(stack, entity);
		if(entity instanceof EntityPlayer){
    		EntityPlayer p = (EntityPlayer) entity;
    		
    		if(getXP(stack) > 100000)
    			ManaItemHandler.dispatchManaExact(stack, p, 1, true);
    		if(getXP(stack) > 200000)
    			ManaItemHandler.dispatchManaExact(stack, p, 1, true);
    		if(getXP(stack) > 300000){
    			ManaItemHandler.dispatchManaExact(stack, p, 1, true);
    			if(p.ticksExisted % 60 == 0)
    				p.heal(1F);
    		}
    		if(getXP(stack) > 400000){
    			if(p.ticksExisted % 40 == 0){
    				clearPotions(p);
    				addXP(stack, -10);
    			}
    		}
    	}
	}
    
	public static void clearPotions(EntityPlayer player) {
		int posXInt = MathHelper.floor(player.getPosition().getX());
		int posZInt = MathHelper.floor(player.getPosition().getZ());

		List<Potion> potionsToRemove = player.getActivePotionEffects().stream()
				.filter(effect -> effect.getPotion().isBadEffect())
				.filter(effect -> effect.getPotion().getCurativeItems().contains(Items.MILK_BUCKET))
				.map(PotionEffect::getPotion)
				.distinct()
				.collect(Collectors.toList());

		potionsToRemove.forEach(potion -> {
			player.removePotionEffect(potion);
			if(!player.getEntityWorld().isRemote)
				((WorldServer) player.getEntityWorld()).getPlayerChunkMap().getEntry(posXInt >> 4, posZInt >> 4).sendPacket(new SPacketRemoveEntityEffect(player.getEntityId(), potion));
		});
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return 1.0 - (float) getXP(stack) / (float) getMaxXP(stack);
	}
	
    @Override
	public void addXP(ItemStack stack, int xp){
		setXP(stack, Math.min(Math.max(getXP(stack) + xp, 0), getMaxXP(stack)));
	}
	
    @Override
	public void setXP(ItemStack stack, int xp) {
		ItemNBTHelper.setInt(stack, TAG_XP, xp);
	}

    @Override
	public int getXP(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_XP, 0);
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.CHARM;
	}

	@Override
	public int getMaxXP(ItemStack stack) {
		return max;
	}

	@Override
	public boolean canExportTo(ItemStack stack, ItemStack otherstack) {
		return true;
	}

	@Override
	public boolean canReceiveFrom(ItemStack stack, ItemStack otherstack) {
		return true;
	}

}
