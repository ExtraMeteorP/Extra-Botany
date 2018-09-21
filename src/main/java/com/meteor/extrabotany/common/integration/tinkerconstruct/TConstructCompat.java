package com.meteor.extrabotany.common.integration.tinkerconstruct;

import static slimeknights.tconstruct.library.materials.MaterialTypes.HEAD;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.common.lib.LibMisc;
import com.meteor.extrabotany.common.lib.LibOreDicts;

import net.minecraft.init.Items;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.ArrowShaftMaterialStats;
import slimeknights.tconstruct.library.materials.BowMaterialStats;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;

public class TConstructCompat {
	
	private static final Map<String, Material> materials = new LinkedHashMap<>();
    private static final Map<String, MaterialIntegration> materialIntegrations = new LinkedHashMap<>();
    private static final Map<String, CompletionStage<?>> materialIntegrationStages = new LinkedHashMap<>();

    public static void preInit() {
    	register();
    	preIntegrate(materials, materialIntegrations, materialIntegrationStages);
    }

    // Copied from PlusTiC
    private static void preIntegrate(Map<String, Material> materials,
                                     Map<String, MaterialIntegration> materialIntegrations,
                                     Map<String, CompletionStage<?>> materialIntegrationStages) {
        materials.forEach((k, v) -> {
            if (!materialIntegrations.containsKey(k)) {
                materialIntegrationStages.getOrDefault(k, CompletableFuture.completedFuture(null))
                        .thenRun(() -> {
                            MaterialIntegration mi;
                            if (v.getRepresentativeItem().getItem() == Items.EMERALD) {
                                mi = new MaterialIntegration(v, v.getFluid());
                            } else if (v.getFluid() != null) {
                                mi = new MaterialIntegration(v, v.getFluid(), k).toolforge();
                            } else {
                                mi = new MaterialIntegration(v);
                            }
                            TinkerRegistry.integrate(mi).preInit();
                            materialIntegrations.put(k, mi);
                        });
            }
        });
    }

    private static void register() {
        Material material = new Material(LibMisc.MOD_ID + ":" + LibOreDicts.SHADOWIUM, 0x800080);
        material.addTrait(Shadow.shadow);
        
        material.addTrait(Shadow.shadow, HEAD);
        
        material.addItem(LibOreDicts.SHADOWIUM, 1, Material.VALUE_Ingot);
        material.setCraftable(true);
        ExtraBotany.proxy.setTinkersRenderColor(material, 0x696969);

        int durability = 600;
        float miningSpeed = 1F;
        float meleeDamage = 7F;
        float meleeSpeed = 1F;
        int harvestLevel = 3;
        float drawDelay = Math.max(38.4f - 1.4f * meleeSpeed * miningSpeed, 10);

        TinkerRegistry.addMaterialStats(material,
                new HeadMaterialStats(600, 7.00f, 6.00f, 3),
                new HandleMaterialStats(1.3F, 180), 
                new ExtraMaterialStats(70),
                new BowMaterialStats(0.8F, 1.5F, 7.5F),
                new ArrowShaftMaterialStats(1.2f, 6));
        materials.put(material.identifier, material);
    }   

}
