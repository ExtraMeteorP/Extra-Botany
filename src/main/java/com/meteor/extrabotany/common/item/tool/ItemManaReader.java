package com.meteor.extrabotany.common.item.tool;

import com.meteor.extrabotany.common.item.ItemMod;
import com.meteor.extrabotany.common.lib.LibItemsName;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import vazkii.botania.common.block.tile.mana.TilePool;

public class ItemManaReader extends ItemMod{

	public ItemManaReader() {
		super(LibItemsName.MANAREADER);
		setMaxStackSize(1);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        TileEntity te = worldIn.getTileEntity(pos);
        if(te instanceof TilePool){
        	TilePool p = (TilePool)te;
        	int cap = p.manaCap;
			int mana = p.getCurrentMana();
			if(!worldIn.isRemote){
				player.sendMessage(new TextComponentTranslation(String.valueOf(mana)));
			}
        }
		
		return EnumActionResult.PASS;
    }

}
