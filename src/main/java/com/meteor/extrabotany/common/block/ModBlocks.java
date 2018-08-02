package com.meteor.extrabotany.common.block;

import com.meteor.extrabotany.common.block.subtile.functional.SubTileAnnoyingFlower;
import com.meteor.extrabotany.common.block.subtile.functional.SubTileEnchantedOrchid;
import com.meteor.extrabotany.common.block.subtile.functional.SubTileManalinkium;
import com.meteor.extrabotany.common.block.subtile.functional.SubTileStardustLotus;
import com.meteor.extrabotany.common.block.subtile.generating.SubTileBellFlower;
import com.meteor.extrabotany.common.block.subtile.generating.SubTileBloodyEnchantress;
import com.meteor.extrabotany.common.block.subtile.generating.SubTileMoonBless;
import com.meteor.extrabotany.common.block.subtile.generating.SubTileOmniViolet;
import com.meteor.extrabotany.common.block.subtile.generating.SubTileReikarLily;
import com.meteor.extrabotany.common.block.subtile.generating.SubTileStonesia;
import com.meteor.extrabotany.common.block.subtile.generating.SubTileSunBless;
import com.meteor.extrabotany.common.block.subtile.generating.SubTileTinkle;
import com.meteor.extrabotany.common.block.tile.TileManaBarrel;
import com.meteor.extrabotany.common.block.tile.TilePedestal;
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
	public static final Block batterybox = new BlockManaBarrel();
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> evt) {
		IForgeRegistry<Block> r = evt.getRegistry();
		r.register(pedestal);
		r.register(orichalcosblock);
		r.register(batterybox);
	}
	
	@SubscribeEvent
	public static void registerItemBlocks(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();
		r.register(new ItemBlockMod(pedestal).setRegistryName(pedestal.getRegistryName()));
		r.register(new ItemBlockMod(orichalcosblock).setRegistryName(orichalcosblock.getRegistryName()));
		r.register(new ItemBlockMod(batterybox).setRegistryName(batterybox.getRegistryName()));
		initTileEntities();
	}
	
	private static void initTileEntities() {
		BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_BLOODYENCHANTRESS, SubTileBloodyEnchantress.class);
		BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_SUNBLESS, SubTileSunBless.class);
		BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_MOONBLESS, SubTileMoonBless.class);
		BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_OMINIVIOLET, SubTileOmniViolet.class);
		BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_STONESIA, SubTileStonesia.class);
		BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_TINKLE, SubTileTinkle.class);
		BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_BELLFLOWER, SubTileBellFlower.class);
		BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_ANNOYINGFLOWER, SubTileAnnoyingFlower.class);
		BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_STARDUSTLOTUS, SubTileStardustLotus.class);
		BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_MANALINKIUM, SubTileManalinkium.class);
		BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_REIKARLILY, SubTileReikarLily.class);
		BotaniaAPI.registerSubTile(LibBlocksName.SUBTILE_ENCHANTEDORCHID, SubTileEnchantedOrchid.class);
		registerTile(TilePedestal.class, LibBlocksName.TILE_PEDESTAL);
		registerTile(TileManaBarrel.class, LibBlocksName.TILE_BATTERYBOX);
	}
	
	private static void registerTile(Class<? extends TileEntity> clazz, String key) {
		GameRegistry.registerTileEntity(clazz, LibMisc.MOD_ID + ":" + key);
	}

}
