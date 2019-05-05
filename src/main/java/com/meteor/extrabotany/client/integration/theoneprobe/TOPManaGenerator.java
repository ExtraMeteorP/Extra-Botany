package com.meteor.extrabotany.client.integration.theoneprobe;

import com.meteor.extrabotany.common.block.tile.TileManaGenerator;
import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.lib.LibMisc;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class TOPManaGenerator implements IProbeInfoProvider {

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        if (world.getTileEntity(data.getPos()) instanceof TileManaGenerator) {
            TileManaGenerator tile = (TileManaGenerator) world.getTileEntity(data.getPos());
            probeInfo.text("Mana:" + tile.getCurrentMana() + "/" + "1000000");
            probeInfo.text("FE:" + tile.energy + "/" + ConfigHandler.MG_MAXENERGY);
        }
    }

    @Override
    public String getID() {
        return LibMisc.MOD_ID + ".managenerator";
    }
}
