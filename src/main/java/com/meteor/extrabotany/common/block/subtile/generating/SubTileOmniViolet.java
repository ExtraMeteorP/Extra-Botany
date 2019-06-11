package com.meteor.extrabotany.common.block.subtile.generating;

import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.crafting.recipe.RecipeOmniviolet;
import com.meteor.extrabotany.common.lexicon.LexiconData;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.ForgeHooks;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileGenerating;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.core.handler.ModSounds;

public class SubTileOmniViolet extends SubTileGenerating{
	private static final String TAG_BURN_TIME = "burnTime";
	private static final int FUEL_CAP = 32000;
	private static final int RANGE = 2;
	int burnTime = 0;
	
	@Override
	public LexiconEntry getEntry() {
		return LexiconData.omniviolet;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if(redstoneSignal > 0)
			return;
		
		float buff = ConfigHandler.LP_OMNIVIOLET ? 1+bookCases() * 0.05F : 1;
		
		if(burnTime > 0){
			burnTime--;
			mana+=ConfigHandler.EFF_OMNIVIOLET * Math.min(5, buff);
		}
		
		if(linkedCollector != null) {
			if(burnTime == 0) {
				if(mana < getMaxMana()) {
					int slowdown = getSlowdownFactor();

					for(EntityItem item : supertile.getWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(supertile.getPos().add(-RANGE, -RANGE, -RANGE), supertile.getPos().add(RANGE + 1, RANGE + 1, RANGE + 1)))) {
						if(item.ticksExisted >= 59 + slowdown && !item.isDead) {
							ItemStack stack = item.getItem();
							if(stack.isEmpty() || stack.getItem().hasContainerItem(stack))
								continue;
							
							int output = RecipeOmniviolet.getOutput(stack);
							int burnTime = stack.getItem() == Item.getItemFromBlock(ModBlocks.spreader) ? 0 : output;
							if(burnTime > 0 && stack.getCount() > 0) {
								this.burnTime = Math.min(FUEL_CAP, burnTime);
								
								stack.shrink(1);
								supertile.getWorld().playSound(null, supertile.getPos(), ModSounds.endoflame, SoundCategory.BLOCKS, 0.2F, 1F);
								sync();

								return;
							}
						}
					}
				}
			}
		}
	}
	public float bookCases(){
	    float temp = 0F;
	    for (int j = -1; j <= 1; j++)
	    {
	      for (int k = -1; k <= 1; k++)
	      {
	        if (((j != 0) || (k != 0)) && (supertile.getWorld().isAirBlock(new BlockPos(supertile.getPos().getX() + k, supertile.getPos().getY(), supertile.getPos().getZ() + j))) && 
	        		(supertile.getWorld().isAirBlock(new BlockPos(supertile.getPos().getX() + k, supertile.getPos().getY() + 1, supertile.getPos().getZ() + j)))) {
	          temp += ForgeHooks.getEnchantPower(supertile.getWorld(), new BlockPos(supertile.getPos().getX() + k * 2, supertile.getPos().getY(), supertile.getPos().getZ() + j * 2));
	          temp += ForgeHooks.getEnchantPower(supertile.getWorld(), new BlockPos(supertile.getPos().getX() + k * 2, supertile.getPos().getY() + 1, supertile.getPos().getZ() + j * 2));

	          if ((k != 0) && (j != 0))
	          {
	            temp += ForgeHooks.getEnchantPower(supertile.getWorld(), new BlockPos(supertile.getPos().getX() + k * 2, supertile.getPos().getY(), supertile.getPos().getZ() + j));
	            temp += ForgeHooks.getEnchantPower(supertile.getWorld(), new BlockPos(supertile.getPos().getX() + k * 2, supertile.getPos().getY() + 1, supertile.getPos().getZ() + j));
	            temp += ForgeHooks.getEnchantPower(supertile.getWorld(), new BlockPos(supertile.getPos().getX() + k, supertile.getPos().getY(), supertile.getPos().getZ() + j * 2));
	            temp += ForgeHooks.getEnchantPower(supertile.getWorld(), new BlockPos(supertile.getPos().getX() + k, supertile.getPos().getY() + 1, supertile.getPos().getZ() + j * 2));
	          }
	        }
	      }
	    }
	    return temp;
	}
	
	@Override
	public int getMaxMana() {
		return 1500;
	}

	@Override
	public int getValueForPassiveGeneration() {
		return ConfigHandler.EFF_OMNIVIOLET;
	}

	@Override
	public int getColor() {
		return 0xEE82EE;
	}

	@Override
	public void writeToPacketNBT(NBTTagCompound cmp) {
		super.writeToPacketNBT(cmp);
		cmp.setInteger(TAG_BURN_TIME, burnTime);
	}

	@Override
	public void readFromPacketNBT(NBTTagCompound cmp) {
		super.readFromPacketNBT(cmp);
		burnTime = cmp.getInteger(TAG_BURN_TIME);
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
	public RadiusDescriptor getRadius() {
		return new RadiusDescriptor.Square(toBlockPos(), RANGE);
	}
}
