package com.meteor.extrabotany.client.core.handler;

import com.meteor.extrabotany.client.render.IModelReg;
import com.meteor.extrabotany.common.block.subtile.functional.*;
import com.meteor.extrabotany.common.block.subtile.generating.*;
import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.lib.LibBlocksName;
import com.meteor.extrabotany.common.lib.LibMisc;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IRegistryDelegate;
import vazkii.botania.api.BotaniaAPIClient;
import vazkii.botania.client.model.SpecialFlowerModel;

import java.util.Locale;
import java.util.Map;
import java.util.function.IntFunction;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = LibMisc.MOD_ID)
public class ModelHandler {
	
	public static boolean isClient = false;
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void registerModels(ModelRegistryEvent evt) {
		ModelLoaderRegistry.registerLoader(SpecialFlowerModel.Loader.INSTANCE);
		OBJLoader.INSTANCE.addDomain(LibMisc.MOD_ID.toLowerCase(Locale.ROOT));

		registerSubtiles();

		for(Block block : Block.REGISTRY) {
			if(block instanceof IModelReg)
				((IModelReg) block).registerModels();
		}

		for(Item item : Item.REGISTRY) {
			if(item instanceof IModelReg)
				((IModelReg) item).registerModels();
		}
		isClient = true;
	}

	private static void registerSubtiles() {
		if(ConfigHandler.ENABLE_BE)
			BotaniaAPIClient.registerSubtileModel(SubTileBloodyEnchantress.class, new ModelResourceLocation("botania:" + LibBlocksName.SUBTILE_BLOODYENCHANTRESS));
		if(ConfigHandler.ENABLE_SB)
			BotaniaAPIClient.registerSubtileModel(SubTileSunBless.class, new ModelResourceLocation("botania:" + LibBlocksName.SUBTILE_SUNBLESS));
		if(ConfigHandler.ENABLE_MB)
			BotaniaAPIClient.registerSubtileModel(SubTileMoonBless.class, new ModelResourceLocation("botania:" + LibBlocksName.SUBTILE_MOONBLESS));
		if(ConfigHandler.ENABLE_OV)
			BotaniaAPIClient.registerSubtileModel(SubTileOmniViolet.class, new ModelResourceLocation("botania:" + LibBlocksName.SUBTILE_OMINIVIOLET));
		if(ConfigHandler.ENABLE_SS)
			BotaniaAPIClient.registerSubtileModel(SubTileStonesia.class, new ModelResourceLocation("botania:" + LibBlocksName.SUBTILE_STONESIA));
		if(ConfigHandler.ENABLE_TK)
			BotaniaAPIClient.registerSubtileModel(SubTileTinkle.class, new ModelResourceLocation("botania:" + LibBlocksName.SUBTILE_TINKLE));
		if(ConfigHandler.ENABLE_BF)
			BotaniaAPIClient.registerSubtileModel(SubTileBellFlower.class, new ModelResourceLocation("botania:" + LibBlocksName.SUBTILE_BELLFLOWER));
		if(ConfigHandler.ENABLE_AF)
			BotaniaAPIClient.registerSubtileModel(SubTileAnnoyingFlower.class, new ModelResourceLocation("botania:" + LibBlocksName.SUBTILE_ANNOYINGFLOWER));
		if(ConfigHandler.ENABLE_SL)
			BotaniaAPIClient.registerSubtileModel(SubTileStardustLotus.class, new ModelResourceLocation("botania:" + LibBlocksName.SUBTILE_STARDUSTLOTUS));
		if(ConfigHandler.ENABLE_ML)
			BotaniaAPIClient.registerSubtileModel(SubTileManalinkium.class, new ModelResourceLocation("botania:" + LibBlocksName.SUBTILE_MANALINKIUM));
		if(ConfigHandler.ENABLE_RL)
			BotaniaAPIClient.registerSubtileModel(SubTileReikarLily.class, new ModelResourceLocation("botania:" + LibBlocksName.SUBTILE_REIKARLILY));
		if(ConfigHandler.ENABLE_EO)
			BotaniaAPIClient.registerSubtileModel(SubTileEnchantedOrchid.class, new ModelResourceLocation("botania:" + LibBlocksName.SUBTILE_ENCHANTEDORCHID));
		if(ConfigHandler.ENABLE_MT)
			BotaniaAPIClient.registerSubtileModel(SubTileMirrortunia.class, new ModelResourceLocation("botania:" + LibBlocksName.SUBTILE_MIRROWTUNIA));
		if(ConfigHandler.ENABLE_EW)
			BotaniaAPIClient.registerSubtileModel(SubTileEdelweiss.class, new ModelResourceLocation("botania:" + LibBlocksName.SUBTILE_EDELWEISS));
		if(ConfigHandler.ENABLE_GO)
			BotaniaAPIClient.registerSubtileModel(SubTileGeminiOrchid.class, new ModelResourceLocation("botania:" + LibBlocksName.SUBTILE_GEMINIORCHID));
	}
	
	private static final Map<IRegistryDelegate<Block>, IStateMapper> customStateMappers = ReflectionHelper.getPrivateValue(ModelLoader.class, null, "customStateMappers");
	private static final DefaultStateMapper fallbackMapper = new DefaultStateMapper();
	
	private static ModelResourceLocation getMrlForState(IBlockState state) {
		return customStateMappers
				.getOrDefault(state.getBlock().delegate, fallbackMapper)
				.putStateModelLocations(state.getBlock())
				.get(state);
	}
	
	public static void registerBlockToState(Block b, int meta, IBlockState state) {
		ModelLoader.setCustomModelResourceLocation(
				Item.getItemFromBlock(b),
				meta,
				getMrlForState(state)
				);
	}

	public static void registerBlockToState(Block b, int maxExclusive) {
		for(int i = 0; i < maxExclusive; i++)
			registerBlockToState(b, i, b.getStateFromMeta(i));
	}
	
	public static void registerItemMetas(Item item, int maxExclusive, IntFunction<String> metaToName) {
		for (int i = 0; i < maxExclusive; i++) {
			ModelLoader.setCustomModelResourceLocation(
					item, i,
					new ModelResourceLocation(LibMisc.MOD_ID + ":" + metaToName.apply(i), "inventory")
					);
		}
	}


}
