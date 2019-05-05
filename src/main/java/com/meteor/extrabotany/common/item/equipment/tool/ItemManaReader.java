package com.meteor.extrabotany.common.item.equipment.tool;

import com.meteor.extrabotany.common.block.tile.TileManaBuffer;
import com.meteor.extrabotany.common.block.tile.TileManaGenerator;
import com.meteor.extrabotany.common.block.tile.TileManaLiquefaction;
import com.meteor.extrabotany.common.item.ItemMod;
import com.meteor.extrabotany.common.lib.LibItemsName;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import vazkii.botania.api.subtile.SubTileEntity;
import vazkii.botania.api.subtile.SubTileFunctional;
import vazkii.botania.api.subtile.SubTileGenerating;
import vazkii.botania.common.block.tile.TileSpecialFlower;
import vazkii.botania.common.block.tile.mana.TilePool;
import vazkii.botania.common.block.tile.mana.TileSpreader;

public class ItemManaReader extends ItemMod {

    public ItemManaReader() {
        super(LibItemsName.MANAREADER);
        setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TilePool) {
            TilePool p = (TilePool) te;
            int mana = p.getCurrentMana();
            if (!worldIn.isRemote)
                sendMessage(player, mana);
        } else if (te instanceof TileManaBuffer) {
            TileManaBuffer p = (TileManaBuffer) te;
            int mana = p.getCurrentMana();
            if (!worldIn.isRemote)
                sendMessage(player, mana);
        } else if (te instanceof TileManaLiquefaction) {
            TileManaLiquefaction p = (TileManaLiquefaction) te;
            int mana = p.getCurrentMana();
            if (!worldIn.isRemote) {
                sendMessage(player, mana);
                player.sendMessage(new TextComponentTranslation("Fluided Mana:" + String.valueOf(p.energy) + "mb"));
            }
        } else if (te instanceof TileManaGenerator) {
            TileManaGenerator p = (TileManaGenerator) te;
            int mana = p.getCurrentMana();
            if (!worldIn.isRemote) {
                sendMessage(player, mana);
                player.sendMessage(new TextComponentTranslation("FE:" + String.valueOf(p.energy) + "rf"));
            }
        } else if (te instanceof TileSpreader) {
            TileSpreader p = (TileSpreader) te;
            int mana = p.getCurrentMana();
            if (!worldIn.isRemote)
                sendMessage(player, mana);
        } else if (te instanceof TileSpecialFlower) {
            TileSpecialFlower f = (TileSpecialFlower) te;
            SubTileEntity se = f.getSubTile();
            NBTTagCompound nbt = f.getUpdateTag();
            NBTTagCompound n = nbt.getCompoundTag("subTileCmp");
            if (se instanceof SubTileGenerating) {
                SubTileGenerating sg = (SubTileGenerating) se;
                int mana = n.getInteger("mana");
                if (!worldIn.isRemote)
                    player.sendMessage(new TextComponentTranslation(String.valueOf(mana) + "/" + String.valueOf(sg.getMaxMana())));
            } else if (se instanceof SubTileFunctional) {
                SubTileFunctional sg = (SubTileFunctional) se;
                int mana = n.getInteger("mana");
                if (!worldIn.isRemote)
                    player.sendMessage(new TextComponentTranslation(String.valueOf(mana) + "/" + String.valueOf(sg.getMaxMana())));
            }
        }
        return EnumActionResult.PASS;
    }

    public void sendMessage(EntityPlayer player, int mana) {
        player.sendMessage(new TextComponentTranslation("Mana:" + String.valueOf(mana)));
    }


}
