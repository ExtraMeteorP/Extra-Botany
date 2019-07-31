package com.meteor.extrabotany.common.block.tile;

import java.util.List;

import com.google.common.base.Predicates;
import com.meteor.extrabotany.common.core.config.ConfigHandler;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import vazkii.botania.api.mana.IManaReceiver;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import vazkii.botania.api.mana.spark.ISparkEntity;
import vazkii.botania.common.block.tile.TileMod;
import vazkii.botania.common.block.tile.mana.TilePool;

public class TileQuantumManaBuffer extends TileMod implements IManaReceiver, ISparkAttachable, ITickable {

	private static final BlockPos[] POOL_LOCATIONS = { new BlockPos(1, 0, 0), new BlockPos(0, 0, 1),
			new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, -1, 0) };

	private static final BlockPos[] POOL_LOCATIONS2 = { new BlockPos(0, 1, 0) };

	private static final String TAG_MANA = "mana";
	private static final String TAG_MANA_CAP = "manaCap";

	int mana;

	public int manaCap = 1024000000;

	@Override
	public boolean canRecieveManaFromBursts() {
		return true;
	}

	@Override
	public boolean isFull() {
		return mana >= manaCap;
	}

	public int getMaxMana() {
		return manaCap;
	}
	
	@Override
	public void recieveMana(int mana) {
		this.mana = Math.min(getMaxMana(), this.mana + mana);
	}

	public static int calculateComparatorLevel(int mana, int max) {
		int val = (int) ((double) mana / (double) max * 15.0);
		if (mana > 0)
			val = Math.max(val, 1);
		return val;
	}

	@Override
	public int getCurrentMana() {
		return mana;
	}

	@Override
	public void update() {

		int speed = 5000;

		for (BlockPos o : POOL_LOCATIONS)// input
			if (world.getTileEntity(pos.add(o)) instanceof TilePool) {
				TilePool p = (TilePool) world.getTileEntity(pos.add(o));
				int manaToGet = Math.min(speed, p.getCurrentMana());
				int space = Math.max(0, getMaxMana() - getCurrentMana());
				int current = Math.min(space, manaToGet);
				p.recieveMana(-current);
				recieveMana(current);	
			} else if (world.getTileEntity(pos.add(o)) instanceof TileManaBuffer) {
				TileManaBuffer p = (TileManaBuffer) world.getTileEntity(pos.add(o));
				int manaToGet = Math.min(speed, p.getCurrentMana());
				int space = Math.max(0, getMaxMana() - getCurrentMana());
				int current = Math.min(space, manaToGet);
				p.recieveMana(-current);
				recieveMana(current);	
			}

		for (BlockPos o : POOL_LOCATIONS2)// output
			if (world.getTileEntity(pos.add(o)) instanceof TilePool) {
				TilePool p = (TilePool) world.getTileEntity(pos.add(0, 1, 0));
				int manaToGet = Math.min(speed, getCurrentMana());
				int space = Math.max(0, p.manaCap - p.getCurrentMana());
				int current = Math.min(space, manaToGet);
				p.recieveMana(current);
				recieveMana(-current);	
			} else if (world.getTileEntity(pos.add(o)) instanceof TileManaBuffer) {
				TileManaBuffer p = (TileManaBuffer) world.getTileEntity(pos.add(o));
				int manaToGet = Math.min(speed, getCurrentMana());
				int space = Math.max(0, p.manaCap - p.getCurrentMana());
				int current = Math.min(space, manaToGet);
				p.recieveMana(current);
				recieveMana(-current);
			}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound cmp) {
		super.writeToNBT(cmp);
		cmp.setInteger(TAG_MANA, mana);
		cmp.setInteger(TAG_MANA_CAP, getMaxMana());
		return cmp;
	}

	@Override
	public void readFromNBT(NBTTagCompound cmp) {
		super.readFromNBT(cmp);
		mana = cmp.getInteger(TAG_MANA);
		manaCap = cmp.getInteger(TAG_MANA_CAP);
	}

	@Override
	public boolean areIncomingTranfersDone() {
		return false;
	}

	@Override
	public void attachSpark(ISparkEntity arg0) {
	}

	@Override
	public boolean canAttachSpark(ItemStack arg0) {
		return true;
	}

	@Override
	public ISparkEntity getAttachedSpark() {
		List sparks = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos.up(), pos.up().add(1, 1, 1)),
				Predicates.instanceOf(ISparkEntity.class));
		if (sparks.size() == 1) {
			Entity e = (Entity) sparks.get(0);
			return (ISparkEntity) e;
		}
		return null;
	}

	@Override
	public int getAvailableSpaceForMana() {
		int space = Math.max(0, getMaxMana() - getCurrentMana());
		if (space > 0)
			return space;
		else
			return 0;
	}

}
