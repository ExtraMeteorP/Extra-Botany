package com.meteor.extrabotany.api.subtile;

import com.meteor.extrabotany.api.item.INatureOrb;
import com.meteor.extrabotany.common.block.tile.TilePedestal;

import net.minecraft.nbt.NBTTagCompound;
import vazkii.botania.api.subtile.SubTileFunctional;
import vazkii.botania.common.Botania;

public class SubTileFunctionalNature extends SubTileFunctional{
	
	private static final String TAG_ENABLED = "enabled";
	public boolean enabled = false;
	protected int range = 7;
	
	@Override
	public void writeToPacketNBT(NBTTagCompound cmp) {
		super.writeToPacketNBT(cmp);
		cmp.setBoolean(TAG_ENABLED, enabled);
	}

	@Override
	public void readFromPacketNBT(NBTTagCompound cmp) {
		super.readFromPacketNBT(cmp);
		enabled = cmp.getBoolean(TAG_ENABLED);
	}
	
	@Override
	public boolean acceptsRedstone() {
		return true;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		enabled = false;

		for(int x = -range; x < range; x++)
			for(int z = -range; z < range; z++){
				if(this.supertile.getWorld().getTileEntity(this.getPos().add(x, 0, z)) instanceof TilePedestal){
					TilePedestal te = (TilePedestal) this.supertile.getWorld().getTileEntity(this.getPos().add(x, 0, z));
					if(te.getItem().getItem() instanceof INatureOrb){
						INatureOrb o = (INatureOrb) te.getItem().getItem();
						if(o.getXP(te.getItem()) >= getRate()){
							enabled = true;
							Botania.proxy.sparkleFX(te.getPos().getX() + 0.5F, te.getPos().getY() + 1.8F, te.getPos().getZ() + 0.5F, 0.08F, 1F, 0.08F, 1F, 10);
							if(willConsume() && ticksExisted % 20 == 0)
								o.addXP(te.getItem(), -getRate());
							break;
						}
					}
				}
			}
		
		if(isEnabled())
			Botania.proxy.sparkleFX(getPos().getX() + 0.5F, getPos().getY() + 1.2F, getPos().getZ() + 0.5F, 0.08F, 1F, 0.08F, 1F, 10);

	}
	
	public boolean isEnabled(){
		return enabled && redstoneSignal == 0;
	}
	
	public int getRate(){
		return 0;
	}
	
	public boolean willConsume(){
		return false;
	}

}
