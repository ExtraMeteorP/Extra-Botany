package com.meteor.extrabotany.common.blocks;

import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.libs.LibBlockNames;
import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class ModBlocks {

    public static final Block powerframe = new BlockPowerFrame(AbstractBlock.Properties.from(Blocks.SPAWNER));
    public static final Block manabuffer = new BlockManaBuffer(AbstractBlock.Properties.from(Blocks.STONE));
    public static final Block dimensioncatalyst = new BlockDimensionCatalyst(AbstractBlock.Properties.from(Blocks.STONE));

    public static void registerBlocks(RegistryEvent.Register<Block> evt) {
        IForgeRegistry<Block> r = evt.getRegistry();
        register(r, LibBlockNames.POWER_FRAME, powerframe);
        register(r, LibBlockNames.MANA_BUFFER, manabuffer);
        register(r, LibBlockNames.DIMENSION_CATALYST, dimensioncatalyst);
    }

    public static void registerItemBlocks(RegistryEvent.Register<Item> evt) {
        IForgeRegistry<Item> r = evt.getRegistry();
        Item.Properties props = ModItems.defaultBuilder();
        register(r, Registry.BLOCK.getKey(powerframe), new BlockItem(powerframe, props));
        register(r, Registry.BLOCK.getKey(manabuffer), new BlockItem(manabuffer, props));
        register(r, Registry.BLOCK.getKey(dimensioncatalyst), new BlockItem(dimensioncatalyst, props));
    }

    public static <V extends IForgeRegistryEntry<V>> void register(IForgeRegistry<V> reg, ResourceLocation name, IForgeRegistryEntry<V> thing) {
        reg.register(thing.setRegistryName(name));
    }

    public static <V extends IForgeRegistryEntry<V>> void register(IForgeRegistry<V> reg, String name, IForgeRegistryEntry<V> thing) {
        register(reg, prefix(name), thing);
    }

    public static ResourceLocation prefix(String path) {
        return new ResourceLocation(LibMisc.MOD_ID, path);
    }

}
