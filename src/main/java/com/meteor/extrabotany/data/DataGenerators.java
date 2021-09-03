package com.meteor.extrabotany.data;

import com.meteor.extrabotany.common.libs.LibMisc;
import com.meteor.extrabotany.data.recipes.BrewProvider;
import com.meteor.extrabotany.data.recipes.ManaInfusionProvider;
import com.meteor.extrabotany.data.recipes.RecipeProvider;
import com.meteor.extrabotany.data.recipes.RuneRecipeProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent evt) {
        ExistingFileHelper helper = evt.getExistingFileHelper();
        if (evt.includeServer()) {
            evt.getGenerator().addProvider(new RecipeProvider(evt.getGenerator()));
            evt.getGenerator().addProvider(new BrewProvider(evt.getGenerator()));
            evt.getGenerator().addProvider(new ManaInfusionProvider(evt.getGenerator()));
            evt.getGenerator().addProvider(new RuneRecipeProvider(evt.getGenerator()));
        }
        if (evt.includeClient()) {
            evt.getGenerator().addProvider(new BlockstateProvider(evt.getGenerator(), helper));
            evt.getGenerator().addProvider(new ItemModelProvider(evt.getGenerator(), helper));
        }
    }
}
