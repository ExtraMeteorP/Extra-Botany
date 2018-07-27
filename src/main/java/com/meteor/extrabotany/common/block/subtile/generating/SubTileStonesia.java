package com.meteor.extrabotany.common.block.subtile.generating;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.meteor.extrabotany.common.block.ModBlocks;
import com.meteor.extrabotany.common.block.tile.TilePedestal;
import com.meteor.extrabotany.common.core.handler.ConfigHandler;
import com.meteor.extrabotany.common.lexicon.LexiconData;
import com.meteor.extrabotany.common.lib.LibData;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
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

	private static final BlockPos[] OFFSETS = { new BlockPos(0, 0, 1), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(-1, 0, 1), new BlockPos(-1, 0, -1), new BlockPos(1, 0, 1), new BlockPos(1, 0, -1) };

	int burnTime, cooldown;

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

		if(burnTime == 0) {
			if(mana < getMaxMana() && !supertile.getWorld().isRemote) {
				List<BlockPos> offsets = Arrays.asList(OFFSETS);
				Collections.shuffle(offsets);
				for(BlockPos offset : offsets) {
					BlockPos pos = supertile.getPos().add(offset);
					Block block = supertile.getWorld().getBlockState(pos).getBlock();
					if(block != null && LibData.getOreBurnTime(block) > 0) {
						float catalysis = 0;
						
						for(int x = -1; x < 1; x++){
							for(int z = -1; z < 1; z++){
								BlockPos posi = new BlockPos(supertile.getPos().add(x, 0, z));
								if(supertile.getWorld().getTileEntity(posi) instanceof TilePedestal){
									TilePedestal te = (TilePedestal) supertile.getWorld().getTileEntity(posi);
									Item i = te.getItem().getItem();
									if(i != null){
										if(i == Item.getItemFromBlock(Blocks.COAL_BLOCK))
											catalysis -=2;
										else if(i == Item.getItemFromBlock(Blocks.IRON_BLOCK))
											catalysis +=4;
										else if(i == Item.getItemFromBlock(Blocks.GOLD_BLOCK))
											catalysis +=8;
										else if(i == Item.getItemFromBlock(Blocks.DIAMOND_BLOCK))
											catalysis +=14;
										else if(i == Item.getItemFromBlock(Blocks.LAPIS_BLOCK))
											catalysis +=6;
										else if(i == Item.getItemFromBlock(Blocks.EMERALD_BLOCK))
											catalysis +=10;
										else if(i == Item.getItemFromBlock(Blocks.REDSTONE_BLOCK))
											catalysis +=4;
										else if(i == Item.getItemFromBlock(ModBlocks.orichalcosblock))
											catalysis +=25;
									}
								}
							}
						}
						
						if(cooldown == 0){
							burnTime += (LibData.getOreBurnTime(block) * (1+catalysis/100));
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
	}

	@Override
	public void readFromPacketNBT(NBTTagCompound cmp) {
		super.readFromPacketNBT(cmp);

		burnTime = cmp.getInteger(TAG_BURN_TIME);
		cooldown = cmp.getInteger(TAG_COOLDOWN);
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
