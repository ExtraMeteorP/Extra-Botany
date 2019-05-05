package com.meteor.extrabotany.client.integration.theoneprobe;

import com.meteor.extrabotany.common.lib.LibMisc;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import vazkii.botania.common.block.tile.mana.TileSpreader;

public class TOPSpreader implements IProbeInfoProvider{
	   
		@Override
	    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data){
	    	if(world.getTileEntity(data.getPos()) instanceof TileSpreader){
	    		TileSpreader tile = (TileSpreader) world.getTileEntity(data.getPos());
	    		probeInfo.text("Mana:" + tile.getCurrentMana() + "/" + tile.getMaxMana());
	    	}
	    }
		
		@Override
	    public String getID(){
	        return LibMisc.MOD_ID + ".spreader";
	    }
}
