package com.meteor.extrabotany.common.blocks.tile;

import com.meteor.extrabotany.common.libs.LibBlockNames;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

import static com.meteor.extrabotany.common.blocks.ModBlocks.*;

public class ModTiles {

    public static final TileEntityType<TilePowerFrame> POWER_FRAME = TileEntityType.Builder.create(TilePowerFrame::new, powerframe).build(null);
    public static final TileEntityType<TileManaBuffer> MANA_BUFFER = TileEntityType.Builder.create(TileManaBuffer::new, manabuffer).build(null);

    public static void registerTiles(RegistryEvent.Register<TileEntityType<?>> evt) {
        IForgeRegistry<TileEntityType<?>> r = evt.getRegistry();
        register(r, LibBlockNames.POWER_FRAME, POWER_FRAME);
        register(r, LibBlockNames.MANA_BUFFER, MANA_BUFFER);
    }

}
