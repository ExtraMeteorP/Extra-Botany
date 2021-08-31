package com.meteor.extrabotany.common.blocks;

import com.meteor.extrabotany.common.blocks.functional.SubTileAnnoyingFlower;
import com.meteor.extrabotany.common.blocks.generating.*;
import com.meteor.extrabotany.common.libs.LibBlockNames;
import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import vazkii.botania.common.block.BlockFloatingSpecialFlower;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

public class ModSubtiles {

    private static final AbstractBlock.Properties FLOWER_PROPS = AbstractBlock.Properties.from(Blocks.POPPY);
    private static final AbstractBlock.Properties FLOATING_PROPS = ModBlocks.FLOATING_PROPS;

    public static final Block bellflower = new BlockSpecialFlower(Effects.SPEED, 360, FLOWER_PROPS, SubTileBellFlower::new);
    public static final Block bellflowerFloating = new BlockFloatingSpecialFlower(FLOATING_PROPS, SubTileBellFlower::new);

    public static final Block edelweiss = new BlockSpecialFlower(Effects.SLOWNESS, 80, FLOWER_PROPS, SubTileEdelweiss::new);
    public static final Block edelweissFloating = new BlockFloatingSpecialFlower(FLOATING_PROPS, SubTileEdelweiss::new);

    public static final Block sunbless = new BlockSpecialFlower(Effects.LUCK, 1600, FLOWER_PROPS, SubTileSunBless::new);
    public static final Block sunblessFloating = new BlockFloatingSpecialFlower(FLOATING_PROPS, SubTileSunBless::new);

    public static final Block moonbless = new BlockSpecialFlower(Effects.UNLUCK, 1600, FLOWER_PROPS, SubTileMoonBless::new);
    public static final Block moonblessFloating = new BlockFloatingSpecialFlower(FLOATING_PROPS, SubTileMoonBless::new);

    public static final Block geminiorchid = new BlockSpecialFlower(Effects.GLOWING, 1600, FLOWER_PROPS, SubTileGeminiOrchid::new);
    public static final Block geminiorchidFloating = new BlockFloatingSpecialFlower(FLOATING_PROPS, SubTileGeminiOrchid::new);

    public static final Block tinkleflower = new BlockSpecialFlower(Effects.HASTE, 360, FLOWER_PROPS, SubTileTinkleFlower::new);
    public static final Block tinkleflowerFloating = new BlockFloatingSpecialFlower(FLOATING_PROPS, SubTileTinkleFlower::new);

    public static final Block omniviolet = new BlockSpecialFlower(Effects.REGENERATION, 360, FLOWER_PROPS, SubTileOmniViolet::new);
    public static final Block omnivioletFloating = new BlockFloatingSpecialFlower(FLOATING_PROPS, SubTileOmniViolet::new);

    public static final Block reikarlily = new BlockSpecialFlower(Effects.JUMP_BOOST, 1600, FLOWER_PROPS, SubTileReikarLily::new);
    public static final Block reikarlilyFloating = new BlockFloatingSpecialFlower(FLOATING_PROPS, SubTileReikarLily::new);

    public static final Block annoyingflower = new BlockSpecialFlower(Effects.HUNGER, 360, FLOWER_PROPS, SubTileAnnoyingFlower::new);
    public static final Block annoyingflowerFloating = new BlockFloatingSpecialFlower(FLOATING_PROPS, SubTileAnnoyingFlower::new);

    public static final Block bloodyenchantress = new BlockSpecialFlower(Effects.WITHER, 360, FLOWER_PROPS, SubTileBloodyEnchantress::new);
    public static final Block bloodyenchantressFloating = new BlockFloatingSpecialFlower(FLOATING_PROPS, SubTileBloodyEnchantress::new);

    public static final TileEntityType<SubTileBellFlower> BELL_FLOWER = TileEntityType.Builder.create(SubTileBellFlower::new, bellflower, bellflowerFloating).build(null);
    public static final TileEntityType<SubTileEdelweiss> EDELWEISS = TileEntityType.Builder.create(SubTileEdelweiss::new, edelweiss, edelweissFloating).build(null);
    public static final TileEntityType<SubTileSunBless> SUNBLESS = TileEntityType.Builder.create(SubTileSunBless::new, sunbless, sunblessFloating).build(null);
    public static final TileEntityType<SubTileMoonBless> MOONBLESS = TileEntityType.Builder.create(SubTileMoonBless::new, moonbless, moonblessFloating).build(null);
    public static final TileEntityType<SubTileGeminiOrchid> GEMINIORCHID = TileEntityType.Builder.create(SubTileGeminiOrchid::new, geminiorchid, geminiorchidFloating).build(null);
    public static final TileEntityType<SubTileTinkleFlower> TINKLEFLOWER = TileEntityType.Builder.create(SubTileTinkleFlower::new, tinkleflower, tinkleflowerFloating).build(null);
    public static final TileEntityType<SubTileOmniViolet> OMNIVIOLET = TileEntityType.Builder.create(SubTileOmniViolet::new, omniviolet, omnivioletFloating).build(null);
    public static final TileEntityType<SubTileReikarLily> REIKARLILY = TileEntityType.Builder.create(SubTileReikarLily::new, reikarlily, reikarlilyFloating).build(null);
    public static final TileEntityType<SubTileAnnoyingFlower> ANNOYING_FLOWER = TileEntityType.Builder.create(SubTileAnnoyingFlower::new, annoyingflower, annoyingflowerFloating).build(null);
    public static final TileEntityType<SubTileBloodyEnchantress> BLOODY_ENCHANTRESS = TileEntityType.Builder.create(SubTileBloodyEnchantress::new, bloodyenchantress, bloodyenchantressFloating).build(null);

    private static ResourceLocation floating(ResourceLocation orig) {
        return new ResourceLocation(orig.getNamespace(), "floating_" + orig.getPath());
    }

    private static ResourceLocation chibi(ResourceLocation orig) {
        return new ResourceLocation(orig.getNamespace(), orig.getPath() + "_chibi");
    }

    private static ResourceLocation getId(Block b) {
        return Registry.BLOCK.getKey(b);
    }

    public static void registerBlocks(RegistryEvent.Register<Block> evt) {
        IForgeRegistry<Block> r = evt.getRegistry();

        registerPair(r, LibBlockNames.GENERATING_BELLFLOWER, bellflower, bellflowerFloating);
        registerPair(r, LibBlockNames.GENERATING_EDELWEISS, edelweiss, edelweissFloating);
        registerPair(r, LibBlockNames.GENERATING_SUNBLESS, sunbless, sunblessFloating);
        registerPair(r, LibBlockNames.GENERATING_MOONBLESS, moonbless, moonblessFloating);
        registerPair(r, LibBlockNames.GENERATING_GEMINIORCHID, geminiorchid, geminiorchidFloating);
        registerPair(r, LibBlockNames.GENERATING_TINKLEFLOWER, tinkleflower, tinkleflowerFloating);
        registerPair(r, LibBlockNames.GENERATING_OMNIVIOLET, omniviolet, omnivioletFloating);
        registerPair(r, LibBlockNames.GENERATING_REIKARLILY, reikarlily, reikarlilyFloating);
        registerPair(r, LibBlockNames.FUNCTIONAL_ANNOYINGFLOWER, annoyingflower, annoyingflowerFloating);
        registerPair(r, LibBlockNames.GENERATING_BLOODYENCHANTRESS, bloodyenchantress, bloodyenchantressFloating);
    }

    public static void registerPair(IForgeRegistry<Block> r, ResourceLocation name, Block flower, Block floating){
        register(r, name, flower);
        register(r, floating(name), floating);
    }

    public static void registerItemBlocks(RegistryEvent.Register<Item> evt) {
        IForgeRegistry<Item> r = evt.getRegistry();

        registerPairItemBlocks(r, bellflower, bellflowerFloating);
        registerPairItemBlocks(r, edelweiss, edelweissFloating);
        registerPairItemBlocks(r, sunbless, sunblessFloating);
        registerPairItemBlocks(r, moonbless, moonblessFloating);
        registerPairItemBlocks(r, geminiorchid, geminiorchidFloating);
        registerPairItemBlocks(r, tinkleflower, tinkleflowerFloating);
        registerPairItemBlocks(r, omniviolet, omnivioletFloating);
        registerPairItemBlocks(r, reikarlily, reikarlilyFloating);
        registerPairItemBlocks(r, annoyingflower, annoyingflowerFloating);
        registerPairItemBlocks(r, bloodyenchantress, bloodyenchantressFloating);
    }

    public static void registerPairItemBlocks(IForgeRegistry<Item> r, Block flower, Block floating){
        Item.Properties props = ModItems.defaultBuilder();
        register(r, getId((flower)), new ItemBlockSpecialFlower(flower, props));
        register(r, getId(floating), new ItemBlockSpecialFlower(floating, props));
    }

    public static void registerTEs(RegistryEvent.Register<TileEntityType<?>> evt) {
        IForgeRegistry<TileEntityType<?>> r = evt.getRegistry();
        register(r, getId(bellflower), BELL_FLOWER);
        register(r, getId(edelweiss), EDELWEISS);
        register(r, getId(sunbless), SUNBLESS);
        register(r, getId(moonbless), MOONBLESS);
        register(r, getId(geminiorchid), GEMINIORCHID);
        register(r, getId(tinkleflower), TINKLEFLOWER);
        register(r, getId(omniviolet), OMNIVIOLET);
        register(r, getId(reikarlily), REIKARLILY);
        register(r, getId(annoyingflower), ANNOYING_FLOWER);
        register(r, getId(bloodyenchantress), BLOODY_ENCHANTRESS);
    }

    public static <V extends IForgeRegistryEntry<V>> void register(IForgeRegistry<V> reg, ResourceLocation name, IForgeRegistryEntry<V> thing) {
        reg.register(thing.setRegistryName(name));
    }

    public static <V extends IForgeRegistryEntry<V>> void register(IForgeRegistry<V> reg, String name, IForgeRegistryEntry<V> thing) {
        register(reg, new ResourceLocation(LibMisc.MOD_ID, name), thing);
    }
}
