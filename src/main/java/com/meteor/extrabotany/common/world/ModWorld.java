package com.meteor.extrabotany.common.world;

import com.meteor.extrabotany.client.lib.LibResource;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;

public class ModWorld {

    public static VillagerRegistry.VillagerProfession villagerBotanist;

    public static void init() {

        villagerBotanist = new VillagerRegistry.VillagerProfession("botanist", LibResource.BOTANIST, LibResource.BOTANIST_ZOMBIE);
        ForgeRegistries.VILLAGER_PROFESSIONS.register(villagerBotanist);

        VillagerCareer botanistCareer = new VillagerCareer(villagerBotanist, "botanist");
        Trades.addTradeForBotanist(botanistCareer);

        BotanistCreationHandler botanist = new BotanistCreationHandler();
        VillagerRegistry villagerRegistry = VillagerRegistry.instance();
        villagerRegistry.registerVillageCreationHandler(botanist);

        try {
            MapGenStructureIO.registerStructureComponent(ComponentBotanist.class, "extrabotany:botanist");
        } catch (Throwable ignored) {
        }
    }

}
