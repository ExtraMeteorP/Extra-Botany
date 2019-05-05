package com.meteor.extrabotany.client.integration.waila;

import com.meteor.extrabotany.common.block.BlockManaLiquefaction;
import com.meteor.extrabotany.common.block.tile.TileManaLiquefaction;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class WailaManaLiquefaction implements IWailaDataProvider{
	
	public static void register(IWailaRegistrar registrar){
        registrar.registerBodyProvider(new WailaManaLiquefaction(), BlockManaLiquefaction.class);
    }

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config){
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config){
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config){
		if(accessor.getTileEntity() instanceof TileManaLiquefaction){
			TileManaLiquefaction pool = (TileManaLiquefaction) accessor.getTileEntity();
			int redstoneSignal = 0;
			for(EnumFacing dir : EnumFacing.VALUES) {
				int redstoneSide = pool.getWorld().getRedstonePower(pool.getPos().offset(dir), dir);
				redstoneSignal = Math.max(redstoneSignal, redstoneSide);
			}
			currenttip.add("Mana:" + pool.getCurrentMana() + "/" + "1000000");
			currenttip.add("Fluided Mana:" + pool.energy + "/" + 16000);
			if(redstoneSignal == 0)
				currenttip.add("Mode: Fluided Mana to Mana");
			else
				currenttip.add("Mode: Mana to Fluided Mana");
			return currenttip;
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config){
		return currenttip;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos){
		return tag;
	}
}
