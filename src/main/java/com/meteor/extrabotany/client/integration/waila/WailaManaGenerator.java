package com.meteor.extrabotany.client.integration.waila;

import com.meteor.extrabotany.common.block.BlockManaGenerator;
import com.meteor.extrabotany.common.block.tile.TileManaGenerator;
import com.meteor.extrabotany.common.core.config.ConfigHandler;
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

public class WailaManaGenerator implements IWailaDataProvider{
	
	public static void register(IWailaRegistrar registrar){
        registrar.registerBodyProvider(new WailaManaGenerator(), BlockManaGenerator.class);
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
		if(accessor.getTileEntity() instanceof TileManaGenerator){
			TileManaGenerator pool = (TileManaGenerator) accessor.getTileEntity();
			currenttip.add("Mana:" + pool.getCurrentMana() + "/" + "1000000");
			currenttip.add("FE:" + pool.energy + "/" + ConfigHandler.MG_MAXENERGY);
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
