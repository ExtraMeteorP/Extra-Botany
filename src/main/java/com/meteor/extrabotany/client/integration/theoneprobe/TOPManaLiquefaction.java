package com.meteor.extrabotany.client.integration.theoneprobe;

import com.meteor.extrabotany.common.block.tile.TileManaLiquefaction;
import com.meteor.extrabotany.common.lib.Reference;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class TOPManaLiquefaction implements IProbeInfoProvider{
	   
	@Override
	public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data){
		if(world.getTileEntity(data.getPos()) instanceof TileManaLiquefaction){
	    	TileManaLiquefaction tile = (TileManaLiquefaction) world.getTileEntity(data.getPos());
	    	probeInfo.text("Mana:" + tile.getCurrentMana() + "/" + "1000000");
	    	probeInfo.text("Fluided Mana:" + tile.energy + "/" + 16000);
			int redstoneSignal = 0;
			for(EnumFacing dir : EnumFacing.VALUES) {
				int redstoneSide = tile.getWorld().getRedstonePower(data.getPos().offset(dir), dir);
				redstoneSignal = Math.max(redstoneSignal, redstoneSide);
			}
			if(redstoneSignal == 0)
				probeInfo.text("Mode: Fluided Mana to Mana");
			else
				probeInfo.text("Mode: Mana to Fluided Mana");
	    }
	}
		
	@Override
	public String getID(){
		return Reference.MOD_ID + ".manaliquefaction";
	}
}
