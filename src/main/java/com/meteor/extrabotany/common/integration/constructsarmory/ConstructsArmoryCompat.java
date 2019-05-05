package com.meteor.extrabotany.common.integration.constructsarmory;

import c4.conarm.common.armor.traits.ArmorTraits;
import c4.conarm.lib.materials.ArmorMaterials;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import com.meteor.extrabotany.common.integration.tinkerconstruct.TConstructCompat;
import slimeknights.tconstruct.library.TinkerRegistry;

public class ConstructsArmoryCompat {
	
	public ConstructsArmoryCompat(){
		
    }
	
	public static void init() {
		ArmorMaterials.addArmorTrait(TConstructCompat.orichalcos, ArmorTraits.featherweight, ArmorTraits.lightweight);
		ArmorMaterials.addArmorTrait(TConstructCompat.orichalcos, ArmorTraits.subterranean, ArmorTraits.petravidity);
		ArmorMaterials.addArmorTrait(TConstructCompat.orichalcos, ArmorTraits.vengeful, ArmorTraits.prideful);
	     
	    TinkerRegistry.addMaterialStats(TConstructCompat.orichalcos,
	    		new CoreMaterialStats(25F, 25),
                new PlatesMaterialStats(1.0F, 15, 4),
                new TrimMaterialStats(4.5F));
	     
	    TinkerRegistry.addMaterialStats(TConstructCompat.material,
                new CoreMaterialStats(13F, 16),
                new PlatesMaterialStats(0.95F, 5, 1),
                new TrimMaterialStats(3.5F));
    }

}
