package com.meteor.extrabotany.client;

import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.client.handler.*;
import com.meteor.extrabotany.client.renderer.entity.*;
import com.meteor.extrabotany.common.blocks.ModBlocks;
import com.meteor.extrabotany.common.core.IProxy;
import com.meteor.extrabotany.common.entities.ModEntities;
import com.meteor.extrabotany.common.items.brew.ItemBrewBase;
import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import vazkii.botania.common.block.decor.BlockFloatingFlower;
import vazkii.botania.common.block.decor.BlockModMushroom;

import java.util.Map;

import static com.meteor.extrabotany.common.items.ModItems.*;

public class ClientProxy implements IProxy {

    public void registerModels(ModelRegistryEvent evt) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.MOTOR, RenderMotor::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.KEY_OF_TRUTH, RenderKeyOfTruth::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.SLASH, RenderSlash::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.UFO, RenderUfo::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.PHANTOMSWORD, RenderPhantomSword::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.FLAMESCIONSLASH, RenderFlamescionSlash::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.SRENGTHENSLASH, RenderStrengthenSlash::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.ULT, RenderFlamescionUlt::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.SWORD, RenderFlamescionSword::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.VOID, RenderFlamescionVoid::new);

        RenderingRegistry.registerEntityRenderingHandler(ModEntities.EGO, RenderEGO::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.EGOMINION, RenderEGO::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.EGOLANDMINE, RenderEGOLandmine::new);

        RenderingRegistry.registerEntityRenderingHandler(ModEntities.MAGICARROW, RenderDummy::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.AURAFIRE, RenderDummy::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.INFLUXWAVER, RenderProjectileBase::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.TRUETERRABLADE, RenderProjectileBase::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.TRUESHADOWKATANA, RenderProjectileBase::new);

        RenderingRegistry.registerEntityRenderingHandler(ModEntities.SPLASHGRENADE, renderManager -> new SpriteRenderer<>(renderManager, Minecraft.getInstance().getItemRenderer()));
    }

    public void onClientSetUpEvent(FMLClientSetupEvent event) {
        Minecraft mc = Minecraft.getInstance();
        GameSettings gameSettings = mc.gameSettings;
        ExtraBotany.keyForward = gameSettings.keyBindForward;
        ExtraBotany.keyBackward = gameSettings.keyBindBack;
        ExtraBotany.keyLeft = gameSettings.keyBindLeft;
        ExtraBotany.keyRight = gameSettings.keyBindRight;
        ExtraBotany.keyUp = gameSettings.keyBindJump;
        ExtraBotany.keyFlight = gameSettings.keyBindSprint;

        registerRenderTypes();
        event.enqueueWork(ClientProxy::registerPropertyGetters);
    }

    @Override
    public void registerHandlers() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::onClientSetUpEvent);
        modBus.addListener(this::loadComplete);
        modBus.addListener(this::registerModels);
        modBus.addListener(MiscellaneousIcons.INSTANCE::onModelRegister);
        modBus.addListener(MiscellaneousIcons.INSTANCE::onModelBake);
        modBus.addListener(ModelHandler::registerModels);

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(HUDHandler::onOverlayRender);
        forgeBus.addListener(ClientTickHandler::clientTickEnd);
    }

    private static void registerRenderTypes() {
        RenderTypeLookup.setRenderLayer(ModBlocks.powerframe, RenderType.getCutout());
        Registry.BLOCK.stream().filter(b -> Registry.BLOCK.getKey(b).getNamespace().equals(LibMisc.MOD_ID))
                .forEach(b -> {
                    if (b instanceof BlockFloatingFlower || b instanceof FlowerBlock
                            || b instanceof TallFlowerBlock || b instanceof BlockModMushroom) {
                        RenderTypeLookup.setRenderLayer(b, RenderType.getCutout());
                    }
                });
    }

    private void loadComplete(FMLLoadCompleteEvent event) {
        DeferredWorkQueue.runLater(() -> {
            initAuxiliaryRender();
            ColorHandler.init();
        });
    }

    private static void registerPropertyGetter(IItemProvider item, ResourceLocation id, IItemPropertyGetter propGetter) {
        ItemModelsProperties.registerProperty(item.asItem(), id, propGetter);
    }

    private static void registerPropertyGetters() {
        IItemPropertyGetter brewGetter = (stack, world, entity) -> {
            ItemBrewBase item = ((ItemBrewBase) stack.getItem());
            return item.getSwigs() - item.getSwigsLeft(stack);
        };
        registerPropertyGetter(cocktail, prefix("swigs_taken"), brewGetter);
        registerPropertyGetter(infinitewine, prefix("swigs_taken"), brewGetter);
    }

    private void initAuxiliaryRender() {
        Map<String, PlayerRenderer> skinMap = Minecraft.getInstance().getRenderManager().getSkinMap();
        PlayerRenderer render;
        render = skinMap.get("default");

        render.addLayer(new LayerHerrscher(render));
        render.addLayer(new LayerFlamescion(render));

        render = skinMap.get("slim");

        render.addLayer(new LayerHerrscher(render));
        render.addLayer(new LayerFlamescion(render));
    }

}
