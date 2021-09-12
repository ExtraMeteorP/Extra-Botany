package com.meteor.extrabotany.client.handler;

import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import vazkii.botania.mixin.AccessorModelBakery;

import java.util.Set;

import static com.meteor.extrabotany.common.items.ModItems.prefix;

public class MiscellaneousIcons {

    public static final MiscellaneousIcons INSTANCE = new MiscellaneousIcons();

    public final RenderMaterial dimensionCatalystOverlay = mainAtlas("block/dimensioncatalyst");

    public final IBakedModel[] firstFractalWeaponModels = new IBakedModel[10];
    public final IBakedModel[] strengthenSlashModel = new IBakedModel[1];
    public final IBakedModel[] flamescionringModel = new IBakedModel[1];
    public final IBakedModel[] influxwaverprojectileModel = new IBakedModel[1];
    public final IBakedModel[] trueterrabladeprojectileModel = new IBakedModel[1];
    public final IBakedModel[] trueshadowkatanaprojectileModel = new IBakedModel[1];
    public final IBakedModel[] coregodWingsModel = new IBakedModel[4];
    public final IBakedModel[] coregodModel = new IBakedModel[1];
    public final IBakedModel[] butterflyprojectileModel = new IBakedModel[1];

    public void onModelRegister(ModelRegistryEvent evt) {
        Set<RenderMaterial> materials = AccessorModelBakery.getMaterials();
        materials.add(dimensionCatalystOverlay);
        for (int i = 0; i < 10; i++) {
            ModelLoader.addSpecialModel(new ResourceLocation(LibMisc.MOD_ID,"icon/sworddomain_" + i));
        }
        ModelLoader.addSpecialModel(new ResourceLocation(LibMisc.MOD_ID,"icon/strengthenslash"));
        ModelLoader.addSpecialModel(new ResourceLocation(LibMisc.MOD_ID,"icon/flamescionring"));
        ModelLoader.addSpecialModel(new ResourceLocation(LibMisc.MOD_ID,"icon/influxwaverprojectile"));
        ModelLoader.addSpecialModel(new ResourceLocation(LibMisc.MOD_ID,"icon/trueterrabladeprojectile"));
        ModelLoader.addSpecialModel(new ResourceLocation(LibMisc.MOD_ID,"icon/trueshadowkatanaprojectile"));
        for (int i = 0; i < 4; i++) {
            ModelLoader.addSpecialModel(new ResourceLocation(LibMisc.MOD_ID,"icon/wing_" + i));
        }
        ModelLoader.addSpecialModel(new ResourceLocation(LibMisc.MOD_ID,"icon/wing_coregod"));
        ModelLoader.addSpecialModel(new ResourceLocation(LibMisc.MOD_ID,"icon/butterflyprojectile"));
    }

    public void onModelBake(ModelBakeEvent evt) {
        for(int i = 0; i < firstFractalWeaponModels.length; i++){
            firstFractalWeaponModels[i] = evt.getModelRegistry().get(new ResourceLocation(LibMisc.MOD_ID, "icon/sworddomain_" + i));
        }
        strengthenSlashModel[0] = evt.getModelRegistry().get(new ResourceLocation(LibMisc.MOD_ID, "icon/strengthenslash"));
        flamescionringModel[0] = evt.getModelRegistry().get(new ResourceLocation(LibMisc.MOD_ID, "icon/flamescionring"));
        influxwaverprojectileModel[0] = evt.getModelRegistry().get(new ResourceLocation(LibMisc.MOD_ID, "icon/influxwaverprojectile"));
        trueterrabladeprojectileModel[0] = evt.getModelRegistry().get(new ResourceLocation(LibMisc.MOD_ID, "icon/trueterrabladeprojectile"));
        trueshadowkatanaprojectileModel[0] = evt.getModelRegistry().get(new ResourceLocation(LibMisc.MOD_ID, "icon/trueshadowkatanaprojectile"));
        for(int i = 0; i < coregodWingsModel.length; i++){
            coregodWingsModel[i] = evt.getModelRegistry().get(new ResourceLocation(LibMisc.MOD_ID, "icon/wing_" + i));
        }
        coregodModel[0] = evt.getModelRegistry().get(new ResourceLocation(LibMisc.MOD_ID, "icon/wing_coregod"));
        butterflyprojectileModel[0] = evt.getModelRegistry().get(new ResourceLocation(LibMisc.MOD_ID, "icon/butterflyprojectile"));
    }

    private static RenderMaterial mainAtlas(String name) {
        return new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, prefix(name));
    }

}
