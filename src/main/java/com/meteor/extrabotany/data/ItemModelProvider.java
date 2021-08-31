package com.meteor.extrabotany.data;

import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Set;
import java.util.stream.Collectors;

import static com.meteor.extrabotany.common.items.ModItems.*;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider{

    public ItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, LibMisc.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Set<Item> items = Registry.ITEM.stream().filter(i -> LibMisc.MOD_ID.equals(Registry.ITEM.getKey(i).getNamespace()))
                .collect(Collectors.toSet());
        registerItemOverrides(items);
    }

    private static String name(Item i) {
        return Registry.ITEM.getKey(i).getPath();
    }

    private static final ResourceLocation GENERATED = new ResourceLocation("item/generated");

    private void registerItemOverrides(Set<Item> items) {

    }

}
