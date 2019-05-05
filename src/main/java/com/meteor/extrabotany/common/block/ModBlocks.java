package com.meteor.extrabotany.common.block;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.block.fluid.BlockManaFluid;
import com.meteor.extrabotany.common.block.subtile.functional.*;
import com.meteor.extrabotany.common.block.subtile.generating.*;
import com.meteor.extrabotany.common.block.tile.*;
import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.item.block.ItemBlockMod;
import com.meteor.extrabotany.common.lib.LibBlocksName;
import com.meteor.extrabotany.common.lib.LibMisc;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import vazkii.botania.api.BotaniaAPI;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)
public class ModBlocks {

    public static final Block pedestal = new BlockPedestal();
    public static final Block orichalcosblock = new BlockOrichalcos();
    public static final Block blockspecial = new BlockSpecial();
    public static final Block batterybox = new BlockManaBuffer();
    public static final Block cocoondesire = new BlockCocoonDesire();
    public static final Block managenerator = new BlockManaGenerator();
    public static final Block manaliquefying = new BlockManaLiquefaction();
    public static final Block manafluid = new BlockManaFluid();
    public static final Block elfjar = new BlockLivingrockBarrel();
    public static final Block trophy = new BlockTrophy();
    public static final Block quantummanabuffer = new BlockQuantumManaBuffer();
    public static final Block lightsource = new BlockLightSource();
    public static final Block dimensioncatalyst = new BlockDimensionCatalyst();
    public static final Block photonium = new BlockPhotonium();
    public static final Block shadowium = new BlockShadowium();

    //public static final Block gildedtinypotato = new BlockGildedTinyPotato();
    //public static final Block infinitycube = new BlockInfinityCube();

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> evt) {
        IForgeRegistry<Block> r = evt.getRegistry();
        r.register(pedestal);
        r.register(orichalcosblock);
        r.register(batterybox);
        r.register(cocoondesire);
        r.register(managenerator);
        r.register(manaliquefying);
        r.register(manafluid);
        r.register(elfjar);
        r.register(trophy);
        r.register(quantummanabuffer);
        r.register(shadowium);
        r.register(photonium);
        r.register(dimensioncatalyst);

        ExtraBotanyAPI.dimensionState = dimensioncatalyst.getDefaultState();

        //r.register(gildedtinypotato);
        r.register(lightsource);
        //r.register(infinitycube);
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> evt) {
        IForgeRegistry<Item> r = evt.getRegistry();
        r.register(new ItemBlockMod(pedestal).setRegistryName(pedestal.getRegistryName()));
        r.register(new ItemBlockMod(orichalcosblock).setRegistryName(orichalcosblock.getRegistryName()));
        r.register(new ItemBlockMod(batterybox).setRegistryName(batterybox.getRegistryName()));
        r.register(new ItemBlockMod(cocoondesire).setRegistryName(cocoondesire.getRegistryName()));
        r.register(new ItemBlockMod(managenerator).setRegistryName(managenerator.getRegistryName()));
        r.register(new ItemBlockMod(manaliquefying).setRegistryName(manaliquefying.getRegistryName()));
        r.register(new ItemBlockMod(manafluid).setRegistryName(manafluid.getRegistryName()));
        r.register(new ItemBlockMod(elfjar).setRegistryName(elfjar.getRegistryName()));
        r.register(new ItemBlockMod(trophy).setRegistryName(trophy.getRegistryName()));
        r.register(new ItemBlockMod(quantummanabuffer).setRegistryName(quantummanabuffer.getRegistryName()));
        r.register(new ItemBlockMod(dimensioncatalyst).setRegistryName(dimensioncatalyst.getRegistryName()));
        r.register(new ItemBlockMod(shadowium).setRegistryName(shadowium.getRegistryName()));
        r.register(new ItemBlockMod(photonium).setRegistryName(photonium.getRegistryName()));
        //r.register(new ItemBlockMod(gildedtinypotato).setRegistryName(gildedtinypotato.getRegistryName()));
        r.register(new ItemBlockMod(lightsource).setRegistryName(lightsource.getRegistryName()));
        //r.register(new ItemBlockMod(infinitycube).setRegistryName(infinitycube.getRegistryName()));
        initTileEntities();
    }

    private static void initTileEntities() {
        if (ConfigHandler.ENABLE_BE)
            BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_BLOODYENCHANTRESS, SubTileBloodyEnchantress.class);
        if (ConfigHandler.ENABLE_SB)
            BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_SUNBLESS, SubTileSunBless.class);
        if (ConfigHandler.ENABLE_MB)
            BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_MOONBLESS, SubTileMoonBless.class);
        if (ConfigHandler.ENABLE_OV)
            BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_OMINIVIOLET, SubTileOmniViolet.class);
        if (ConfigHandler.ENABLE_SS)
            BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_STONESIA, SubTileStonesia.class);
        if (ConfigHandler.ENABLE_TK)
            BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_TINKLE, SubTileTinkle.class);
        if (ConfigHandler.ENABLE_BF)
            BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_BELLFLOWER, SubTileBellFlower.class);
        if (ConfigHandler.ENABLE_AF)
            BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_ANNOYINGFLOWER, SubTileAnnoyingFlower.class);
        if (ConfigHandler.ENABLE_SL)
            BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_STARDUSTLOTUS, SubTileStardustLotus.class);
        if (ConfigHandler.ENABLE_ML)
            BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_MANALINKIUM, SubTileManalinkium.class);
        if (ConfigHandler.ENABLE_RL)
            BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_REIKARLILY, SubTileReikarLily.class);
        if (ConfigHandler.ENABLE_EO)
            BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_ENCHANTEDORCHID, SubTileEnchantedOrchid.class);
        if (ConfigHandler.ENABLE_EW)
            BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_EDELWEISS, SubTileEdelweiss.class);
        if (ConfigHandler.ENABLE_MT)
            BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_MIRROWTUNIA, SubTileMirrortunia.class);
        if (ConfigHandler.ENABLE_GO)
            BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_GEMINIORCHID, SubTileGeminiOrchid.class);
        registerTile(TilePedestal.class, LibBlocksName.TILE_PEDESTAL);
        registerTile(TileManaBuffer.class, LibBlocksName.TILE_BATTERYBOX);
        registerTile(TileCocoonDesire.class, LibBlocksName.TILE_COCOON);
        registerTile(TileManaGenerator.class, LibBlocksName.TILE_MANAGENERATOR);
        registerTile(TileManaLiquefaction.class, LibBlocksName.TILE_MANALIQUEFYING);
        registerTile(TileLivingrockBarrel.class, LibBlocksName.TILE_ELFJAR);
        registerTile(TileQuantumManaBuffer.class, LibBlocksName.TILE_QUANTUMMANABUFFER);

        //registerTile(TileGildedTinyPotato.class, LibBlocksName.TILE_GILDEDTINYPOTATO);
        //registerTile(TileInfinityCube.class, LibBlocksName.TILE_INFINITYCUBE);
    }

    private static void registerTile(Class<? extends TileEntity> clazz, String key) {
        GameRegistry.registerTileEntity(clazz, LibMisc.MOD_ID + ":" + key);
    }

}
