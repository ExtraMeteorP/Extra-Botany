package com.meteor.extrabotany.client.integration.waila;

import com.meteor.extrabotany.common.block.BlockManaBuffer;
import com.meteor.extrabotany.common.block.tile.TileManaBuffer;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class WailaManaBuffer implements IWailaDataProvider{
	
	public static void register(IWailaRegistrar registrar){
        registrar.registerBodyProvider(new WailaManaBuffer(), BlockManaBuffer.class);
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
		if(accessor.getTileEntity() instanceof TileManaBuffer){
			TileManaBuffer pool = (TileManaBuffer) accessor.getTileEntity();
			currenttip.add("Mana:" + pool.getCurrentMana() + "/" + pool.getMaxMana());
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
