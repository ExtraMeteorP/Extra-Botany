package com.meteor.extrabotany;

import com.meteor.extrabotany.client.ClientProxy;
import com.meteor.extrabotany.common.ExtraBotanyGroup;
import com.meteor.extrabotany.common.ServerProxy;
import com.meteor.extrabotany.common.blocks.ModBlocks;
import com.meteor.extrabotany.common.blocks.ModSubtiles;
import com.meteor.extrabotany.common.blocks.tile.ModTiles;
import com.meteor.extrabotany.common.capability.CapabilityHandler;
import com.meteor.extrabotany.common.core.ConfigHandler;
import com.meteor.extrabotany.common.core.EquipmentHandler;
import com.meteor.extrabotany.common.core.IProxy;
import com.meteor.extrabotany.common.core.ModSounds;
import com.meteor.extrabotany.common.entities.ModEntities;
import com.meteor.extrabotany.common.entities.ego.EntityEGO;
import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.libs.LibMisc;
import com.meteor.extrabotany.common.potions.ModPotions;
import com.meteor.extrabotany.data.DataGenerators;
import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.potion.Effect;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(LibMisc.MOD_ID)
public class ExtraBotany {

    public static ItemGroup itemGroup = new ExtraBotanyGroup();

    public static IProxy proxy;

    public static boolean curiosLoaded = false;

    @OnlyIn(Dist.CLIENT)
    public static KeyBinding keyForward;
    @OnlyIn(Dist.CLIENT)
    public static KeyBinding keyBackward;
    @OnlyIn(Dist.CLIENT)
    public static KeyBinding keyLeft;
    @OnlyIn(Dist.CLIENT)
    public static KeyBinding keyRight;
    @OnlyIn(Dist.CLIENT)
    public static KeyBinding keyUp;
    @OnlyIn(Dist.CLIENT)
    public static KeyBinding keyFlight;

    public ExtraBotany() {
        proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);
        proxy.registerHandlers();

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigHandler.CLIENT_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.COMMON_SPEC);

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::commonSetup);
        modBus.addGenericListener(EntityType.class, ModEntities::registerEntities);
        modBus.addGenericListener(SoundEvent.class, ModSounds::registerSounds);
        modBus.addGenericListener(IRecipeSerializer.class, ModItems::registerRecipeSerializers);
        modBus.addGenericListener(Item.class, ModItems::registerItems);
        modBus.addGenericListener(Block.class, ModBlocks::registerBlocks);
        modBus.addGenericListener(Item.class, ModBlocks::registerItemBlocks);
        modBus.addGenericListener(Block.class, ModSubtiles::registerBlocks);
        modBus.addGenericListener(Item.class, ModSubtiles::registerItemBlocks);
        modBus.addGenericListener(TileEntityType.class, ModTiles::registerTiles);
        modBus.addGenericListener(TileEntityType.class, ModSubtiles::registerTEs);
        modBus.addGenericListener(Effect.class, ModPotions::registerPotions);
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        curiosLoaded = ModList.get().isLoaded("curios");
        CapabilityHandler.register();
        EquipmentHandler.init();

        event.enqueueWork(() -> {
            GlobalEntityTypeAttributes.put(ModEntities.EGO, MobEntity.func_233666_p_()
                    .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.55)
                    .createMutableAttribute(Attributes.MAX_HEALTH, EntityEGO.MAX_HP)
                    .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 1.0)
                    .createMutableAttribute(Attributes.ARMOR, 20)
                    .createMutableAttribute(Attributes.FOLLOW_RANGE, 35)
                    .createMutableAttribute(Attributes.ATTACK_DAMAGE, 8)
                    .create());

            GlobalEntityTypeAttributes.put(ModEntities.EGOMINION, MobEntity.func_233666_p_()
                    .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5)
                    .createMutableAttribute(Attributes.MAX_HEALTH, 60)
                    .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 1.0)
                    .createMutableAttribute(Attributes.ARMOR, 15)
                    .createMutableAttribute(Attributes.FOLLOW_RANGE, 35)
                    .createMutableAttribute(Attributes.ATTACK_DAMAGE, 7)
                    .create());
        });
    }

}
