package com.meteor.extrabotany.client.core.handler;

import java.util.Locale;
import java.util.Map;

import com.meteor.extrabotany.client.render.IModelReg;
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
import net.minecraftforge.registries.IRegistryDelegate;
import vazkii.botania.api.BotaniaAPIClient;
import vazkii.botania.client.model.SpecialFlowerModel;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = LibMisc.MOD_ID)
public class ModelHandler {
	
	@SubscribeEvent
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
	}

	private static void registerSubtiles() {
		BotaniaAPIClient.registerSubtileModel(SubTileBloodyEnchantress.class, new ModelResourceLocation("botania:" + LibBlocksName.SUBTILE_BLOODYENCHANTRESS));
		BotaniaAPIClient.registerSubtileModel(SubTileSunBless.class, new ModelResourceLocation("botania:" + LibBlocksName.SUBTILE_SUNBLESS));
		BotaniaAPIClient.registerSubtileModel(SubTileMoonBless.class, new ModelResourceLocation("botania:" + LibBlocksName.SUBTILE_MOONBLESS));
		BotaniaAPIClient.registerSubtileModel(SubTileOmniViolet.class, new ModelResourceLocation("botania:" + LibBlocksName.SUBTILE_OMINIVIOLET));
		BotaniaAPIClient.registerSubtileModel(SubTileStonesia.class, new ModelResourceLocation("botania:" + LibBlocksName.SUBTILE_STONESIA));
		BotaniaAPIClient.registerSubtileModel(SubTileTinkle.class, new ModelResourceLocation("botania:" + LibBlocksName.SUBTILE_TINKLE));
		BotaniaAPIClient.registerSubtileModel(SubTileBellFlower.class, new ModelResourceLocation("botania:" + LibBlocksName.SUBTILE_BELLFLOWER));
		BotaniaAPIClient.registerSubtileModel(SubTileAnnoyingFlower.class, new ModelResourceLocation("botania:" + LibBlocksName.SUBTILE_ANNOYINGFLOWER));
		BotaniaAPIClient.registerSubtileModel(SubTileStardustLotus.class, new ModelResourceLocation("botania:" + LibBlocksName.SUBTILE_STARDUSTLOTUS));
		BotaniaAPIClient.registerSubtileModel(SubTileManalinkium.class, new ModelResourceLocation("botania:" + LibBlocksName.SUBTILE_MANALINKIUM));
		BotaniaAPIClient.registerSubtileModel(SubTileReikarLily.class, new ModelResourceLocation("botania:" + LibBlocksName.SUBTILE_REIKARLILY));
		BotaniaAPIClient.registerSubtileModel(SubTileEnchantedOrchid.class, new ModelResourceLocation("botania:" + LibBlocksName.SUBTILE_ENCHANTEDORCHID));
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


}
