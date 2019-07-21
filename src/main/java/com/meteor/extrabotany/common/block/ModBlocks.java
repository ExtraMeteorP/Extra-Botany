package com.meteor.extrabotany.common.block;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.block.fluid.BlockManaFluid;
import com.meteor.extrabotany.common.block.subtile.functional.SubTileAnnoyingFlower;
import com.meteor.extrabotany.common.block.subtile.functional.SubTileEnchantedOrchid;
import com.meteor.extrabotany.common.block.subtile.functional.SubTileManalinkium;
import com.meteor.extrabotany.common.block.subtile.functional.SubTileMirrortunia;
import com.meteor.extrabotany.common.block.subtile.functional.SubTileNecrofleur;
import com.meteor.extrabotany.common.block.subtile.functional.SubTileStardustLotus;
import com.meteor.extrabotany.common.block.subtile.generating.SubTileBellFlower;
import com.meteor.extrabotany.common.block.subtile.generating.SubTileBloodyEnchantress;
import com.meteor.extrabotany.common.block.subtile.generating.SubTileEdelweiss;
import com.meteor.extrabotany.common.block.subtile.generating.SubTileGeminiOrchid;
import com.meteor.extrabotany.common.block.subtile.generating.SubTileMoonBless;
import com.meteor.extrabotany.common.block.subtile.generating.SubTileOmniViolet;
import com.meteor.extrabotany.common.block.subtile.generating.SubTileReikarLily;
import com.meteor.extrabotany.common.block.subtile.generating.SubTileStonesia;
import com.meteor.extrabotany.common.block.subtile.generating.SubTileSunBless;
import com.meteor.extrabotany.common.block.subtile.generating.SubTileTinkle;
import com.meteor.extrabotany.common.block.tile.TileChargePad;
import com.meteor.extrabotany.common.block.tile.TileCocoonDesire;
import com.meteor.extrabotany.common.block.tile.TileLivingrockBarrel;
import com.meteor.extrabotany.common.block.tile.TileManaBuffer;
import com.meteor.extrabotany.common.block.tile.TileManaGenerator;
import com.meteor.extrabotany.common.block.tile.TileManaLiquefaction;
import com.meteor.extrabotany.common.block.tile.TilePedestal;
import com.meteor.extrabotany.common.block.tile.TileQuantumManaBuffer;
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
import vazkii.botania.api.subtile.SubTileEntity;

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
	public static final Block dimensioncatalyst = new BlockDimensionCatalyst();
	public static final Block photonium = new BlockPhotonium();
	public static final Block shadowium = new BlockShadowium();
	public static final Block chargepad = new BlockChargePad();
	
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
		r.register(chargepad);
		
		ExtraBotanyAPI.dimensionState = dimensioncatalyst.getDefaultState();
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
		r.register(new ItemBlockMod(chargepad).setRegistryName(chargepad.getRegistryName()));
		initTileEntities();
	}
	
	private static void initTileEntities() {
		if(ConfigHandler.ENABLE_BE)
			BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_BLOODYENCHANTRESS, SubTileBloodyEnchantress.class);
		if(ConfigHandler.ENABLE_SB)
			BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_SUNBLESS, SubTileSunBless.class);
		if(ConfigHandler.ENABLE_MB)
			BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_MOONBLESS, SubTileMoonBless.class);
		if(ConfigHandler.ENABLE_OV)
			BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_OMINIVIOLET, SubTileOmniViolet.class);
		if(ConfigHandler.ENABLE_SS)
			BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_STONESIA, SubTileStonesia.class);
		if(ConfigHandler.ENABLE_TK)
			BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_TINKLE, SubTileTinkle.class);
		if(ConfigHandler.ENABLE_BF)
			BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_BELLFLOWER, SubTileBellFlower.class);
		if(ConfigHandler.ENABLE_AF)
			BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_ANNOYINGFLOWER, SubTileAnnoyingFlower.class);
		if(ConfigHandler.ENABLE_SL)
			BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_STARDUSTLOTUS, SubTileStardustLotus.class);
		if(ConfigHandler.ENABLE_ML)
			BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_MANALINKIUM, SubTileManalinkium.class);
		if(ConfigHandler.ENABLE_RL)
			BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_REIKARLILY, SubTileReikarLily.class);
		if(ConfigHandler.ENABLE_EO)
			BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_ENCHANTEDORCHID, SubTileEnchantedOrchid.class);
		if(ConfigHandler.ENABLE_EW)
			BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_EDELWEISS, SubTileEdelweiss.class);
		if(ConfigHandler.ENABLE_MT)
			BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_MIRROWTUNIA, SubTileMirrortunia.class);
		if(ConfigHandler.ENABLE_GO)
			BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_GEMINIORCHID, SubTileGeminiOrchid.class);
		if(ConfigHandler.ENABLE_NF)
			registerSubTileWithMini(LibBlocksName.SUBTILE_NECROFLEUR, SubTileNecrofleur.class);
		registerTile(TilePedestal.class, LibBlocksName.TILE_PEDESTAL);
		registerTile(TileManaBuffer.class, LibBlocksName.TILE_BATTERYBOX);
		registerTile(TileCocoonDesire.class, LibBlocksName.TILE_COCOON);
		registerTile(TileManaGenerator.class, LibBlocksName.TILE_MANAGENERATOR);
		registerTile(TileManaLiquefaction.class, LibBlocksName.TILE_MANALIQUEFYING);
		registerTile(TileLivingrockBarrel.class, LibBlocksName.TILE_ELFJAR);
		registerTile(TileQuantumManaBuffer.class, LibBlocksName.TILE_QUANTUMMANABUFFER);
		registerTile(TileChargePad.class, LibBlocksName.TILE_CHARGEPAD);
	}
	
	private static void registerSubTileWithMini(String key, Class<? extends SubTileEntity> clazz) {
		BotaniaAPI.registerSubTile(key, clazz);

		for(Class innerClazz : clazz.getDeclaredClasses())
			if(innerClazz.getSimpleName().equals("Mini"))
				BotaniaAPI.registerMiniSubTile(key + "Chibi", innerClazz, key);
	}
	
	private static void registerTile(Class<? extends TileEntity> clazz, String key) {
		GameRegistry.registerTileEntity(clazz, LibMisc.MOD_ID + ":" + key);
	}

}
