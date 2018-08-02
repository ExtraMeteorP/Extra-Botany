package com.meteor.extrabotany.common.block.subtile.generating;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.meteor.extrabotany.common.block.ModBlocks;
import com.meteor.extrabotany.common.block.tile.TilePedestal;
import com.meteor.extrabotany.common.core.handler.ConfigHandler;
import com.meteor.extrabotany.common.crafting.recipe.RecipeStonesia;
import com.meteor.extrabotany.common.lexicon.LexiconData;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileGenerating;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class SubTileStonesia extends SubTileGenerating{
	
	private static final String TAG_BURN_TIME = "burnTime";
	private static final String TAG_COOLDOWN = "cooldown";
	private static final String TAG_CATALYSIS = "catalysis";

	private static final BlockPos[] OFFSETS = { new BlockPos(0, 0, 1), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(-1, 0, 1), new BlockPos(-1, 0, -1), new BlockPos(1, 0, 1), new BlockPos(1, 0, -1) };

	int burnTime, cooldown;
	float catalysis = 0;

	@Override
	public void onUpdate() {
		super.onUpdate();
		if(redstoneSignal > 0)
			return;

		if(cooldown > 0) {
			cooldown--;
			for(int i = 0; i < 3; i++)
				Botania.proxy.wispFX(supertile.getPos().getX() + 0.5 + Math.random() * 0.2 - 0.1, supertile.getPos().getY() + 0.5 + Math.random() * 0.2 - 0.1, supertile.getPos().getZ() + 0.5 + Math.random() * 0.2 - 0.1, 0.1F, 0.1F, 0.1F, (float) Math.random() / 6, (float) -Math.random() / 30);
		}
		
		for(BlockPos o : OFFSETS)
			if(supertile.getWorld().getTileEntity(supertile.getPos().add(o)) instanceof TilePedestal){
				TilePedestal te = (TilePedestal) supertile.getWorld().getTileEntity(supertile.getPos().add(o));
				ItemStack i = te.getItem();
				if(i != null){
					if(i.isItemEqual(new ItemStack(Blocks.COAL_BLOCK)))
						catalysis = Math.max(-5, catalysis);
					else if(i.isItemEqual(new ItemStack(Blocks.IRON_BLOCK)))
						catalysis = Math.max(10, catalysis);
					else if(i.isItemEqual(new ItemStack(Blocks.GOLD_BLOCK)))
						catalysis = Math.max(15, catalysis);
					else if(i.isItemEqual(new ItemStack(Blocks.DIAMOND_BLOCK)))
						catalysis = Math.max(25, catalysis);
					else if(i.isItemEqual(new ItemStack(Blocks.LAPIS_BLOCK)))
						catalysis = Math.max(10, catalysis);
					else if(i.isItemEqual(new ItemStack(Blocks.EMERALD_BLOCK)))
						catalysis = Math.max(18, catalysis);
					else if(i.isItemEqual(new ItemStack(Blocks.REDSTONE_BLOCK)))
						catalysis = Math.max(8, catalysis);
					else if(i.isItemEqual(new ItemStack(ModBlocks.orichalcosblock)))
						catalysis = Math.max(35, catalysis);
				}
			}

		if(burnTime == 0) {
			if(mana < getMaxMana() && !supertile.getWorld().isRemote) {
				List<BlockPos> offsets = Arrays.asList(OFFSETS);
				Collections.shuffle(offsets);
				for(BlockPos offset : offsets) {
					BlockPos pos = supertile.getPos().add(offset);
					Block block = supertile.getWorld().getBlockState(pos).getBlock();
					int output = RecipeStonesia.getOutput(new ItemStack(block));
					if(block != null && output != 0){
											
						if(cooldown == 0){
							burnTime += output * (1+catalysis/100);
							supertile.getWorld().setBlockToAir(pos);
							cooldown = getCooldown();
						}

						sync();
						playSound();
						break;
					}
				}
			}
		} else {
			if(supertile.getWorld().rand.nextInt(8) == 0)
				doBurnParticles();
			burnTime--;
		}
	}
	
	public void doBurnParticles() {
		Botania.proxy.wispFX(supertile.getPos().getX() + 0.55 + Math.random() * 0.2 - 0.1, supertile.getPos().getY() + 0.55 + Math.random() * 0.2 - 0.1, supertile.getPos().getZ() + 0.5, 0.05F, 0.05F, 0.7F, (float) Math.random() / 6, (float) -Math.random() / 60);
	}

	public void playSound() {
		supertile.getWorld().playSound(null, supertile.getPos(), SoundEvents.ENTITY_GENERIC_DRINK, SoundCategory.BLOCKS, 0.01F, 0.5F + (float) Math.random() * 0.5F);
	}

	@Override
	public RadiusDescriptor getRadius() {
		return new RadiusDescriptor.Square(toBlockPos(), 1);
	}

	@Override
	public int getMaxMana() {
		return 800;
	}

	@Override
	public int getColor() {
		return 0x778899;
	}

	@Override
	public LexiconEntry getEntry() {
		return LexiconData.stonesia;
	}

	@Override
	public void writeToPacketNBT(NBTTagCompound cmp) {
		super.writeToPacketNBT(cmp);

		cmp.setInteger(TAG_BURN_TIME, burnTime);
		cmp.setInteger(TAG_COOLDOWN, cooldown);
		cmp.setFloat(TAG_CATALYSIS, catalysis);
	}

	@Override
	public void readFromPacketNBT(NBTTagCompound cmp) {
		super.readFromPacketNBT(cmp);

		burnTime = cmp.getInteger(TAG_BURN_TIME);
		cooldown = cmp.getInteger(TAG_COOLDOWN);
		catalysis = cmp.getFloat(TAG_CATALYSIS);
	}

	@Override
	public void populateDropStackNBTs(List<ItemStack> drops) {
		super.populateDropStackNBTs(drops);
		int cooldown = this.cooldown;
		if(burnTime > 0)
			cooldown = getCooldown();

		if(cooldown > 0)
			ItemNBTHelper.setInt(drops.get(0), TAG_COOLDOWN, getCooldown());
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack) {
		super.onBlockPlacedBy(world, pos, state, entity, stack);
		cooldown = ItemNBTHelper.getInt(stack, TAG_COOLDOWN, 0);
	}

	@Override
	public boolean canGeneratePassively() {
		return burnTime > 0;
	}

	@Override
	public int getDelayBetweenPassiveGeneration() {
		return 1;
	}
	
	@Override
	public int getValueForPassiveGeneration() {
		return ConfigHandler.EFF_STONESIA;
	}

	public int getCooldown() {
		return 40;
	}

}
