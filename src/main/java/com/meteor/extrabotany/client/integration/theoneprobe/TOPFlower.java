package com.meteor.extrabotany.client.integration.theoneprobe;

import com.meteor.extrabotany.common.lib.LibMisc;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import vazkii.botania.api.subtile.SubTileEntity;
import vazkii.botania.api.subtile.SubTileFunctional;
import vazkii.botania.api.subtile.SubTileGenerating;
import vazkii.botania.common.block.tile.TileSpecialFlower;

public class TOPFlower implements IProbeInfoProvider {

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        if (world.getTileEntity(data.getPos()) instanceof TileSpecialFlower) {
            TileSpecialFlower f = (TileSpecialFlower) world.getTileEntity(data.getPos());
            SubTileEntity se = f.getSubTile();
            NBTTagCompound nbt = f.getUpdateTag();
            NBTTagCompound n = nbt.getCompoundTag("subTileCmp");
            if (se instanceof SubTileGenerating) {
                SubTileGenerating sg = (SubTileGenerating) se;
                int mana = n.getInteger("mana");
                probeInfo.text("Mana:" + mana + "/" + sg.getMaxMana());
            } else if (se instanceof SubTileFunctional) {
                SubTileFunctional sg = (SubTileFunctional) se;
                int mana = n.getInteger("mana");
                probeInfo.text("Mana:" + mana + "/" + sg.getMaxMana());
            }
        }
    }

    @Override
    public String getID() {
        return LibMisc.MOD_ID + ".flower";
    }
}
