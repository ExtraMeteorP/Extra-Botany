package com.meteor.extrabotany.common.item;

import com.meteor.extrabotany.common.core.handler.ConfigHandler;
import com.meteor.extrabotany.common.item.bonus.ItemRewardBag;
import com.meteor.extrabotany.common.item.brew.ItemBrewCocktail;
import com.meteor.extrabotany.common.item.brew.ItemBrewInfiniteWine;
import com.meteor.extrabotany.common.item.brew.ItemBrewSplashGrenade;
import com.meteor.extrabotany.common.item.equipment.armor.combatmaid.ItemCombatMaidBoots;
import com.meteor.extrabotany.common.item.equipment.armor.combatmaid.ItemCombatMaidChest;
import com.meteor.extrabotany.common.item.equipment.armor.combatmaid.ItemCombatMaidHelm;
import com.meteor.extrabotany.common.item.equipment.armor.combatmaid.ItemCombatMaidLegs;
import com.meteor.extrabotany.common.item.equipment.armor.cosmeticmaid.ItemCosmeticMaidBoots;
import com.meteor.extrabotany.common.item.equipment.armor.cosmeticmaid.ItemCosmeticMaidChest;
import com.meteor.extrabotany.common.item.equipment.armor.cosmeticmaid.ItemCosmeticMaidHelm;
import com.meteor.extrabotany.common.item.equipment.armor.cosmeticmaid.ItemCosmeticMaidLegs;
import com.meteor.extrabotany.common.item.equipment.bauble.ItemDeathRing;
import com.meteor.extrabotany.common.item.equipment.bauble.ItemFrostStar;
import com.meteor.extrabotany.common.item.equipment.shield.ItemElementiumShield;
import com.meteor.extrabotany.common.item.equipment.shield.ItemManasteelShield;
import com.meteor.extrabotany.common.item.equipment.shield.ItemTerrasteelShield;
import com.meteor.extrabotany.common.item.equipment.tool.ItemBinder;
import com.meteor.extrabotany.common.item.equipment.tool.ItemHammer;
import com.meteor.extrabotany.common.item.equipment.tool.ItemHammerUltimate;
import com.meteor.extrabotany.common.item.equipment.tool.ItemKingGarden;
import com.meteor.extrabotany.common.item.equipment.tool.ItemManaReader;
import com.meteor.extrabotany.common.item.equipment.tool.ItemNatureOrb;
import com.meteor.extrabotany.common.item.record.ItemRecordA;
import com.meteor.extrabotany.common.item.relic.ItemAchilleshield;
import com.meteor.extrabotany.common.item.relic.ItemCamera;
import com.meteor.extrabotany.common.item.relic.ItemFailnaught;
import com.meteor.extrabotany.common.item.relic.ItemMasterManaRing;
import com.meteor.extrabotany.common.item.relic.ItemTreasure;
import com.meteor.extrabotany.common.lib.LibItemsName;
import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import vazkii.botania.api.BotaniaAPI;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)
public class ModItems {
	
	public static final Item manaReader = new ItemManaReader();
	public static final Item spiritFuel = new ItemSpiritFuel();
	public static final Item nightmareFuel = new ItemNightmareFuel();
	public static final Item material = new ItemMaterial();
	public static final Item friedchicken = new ItemFriedChicken();
	public static final Item gildedmashedpotato = new ItemGildedMashedPotato();
	public static final Item manadrink = new ItemManaDrink();
	
	public static final Item binder = new ItemBinder();
	
	public static final Item froststar = new ItemFrostStar();
	public static final Item deathring = new ItemDeathRing();
	public static final Item mastermanaring = new ItemMasterManaRing();
	
	public static final Item hammermanasteel = new ItemHammer(LibItemsName.HAMMER_MANASTEEL, BotaniaAPI.manasteelToolMaterial);
	public static final Item hammerelementium = new ItemHammer(LibItemsName.HAMMER_ELEMENTIUM, BotaniaAPI.elementiumToolMaterial);
	public static final Item hammerterrasteel = new ItemHammer(LibItemsName.HAMMER_TERRASTEEL, BotaniaAPI.terrasteelToolMaterial);
	public static final Item hammerultimate = new ItemHammerUltimate();
	
	public static final Item kinggarden = new ItemKingGarden();
	public static final Item camera = new ItemCamera();
	public static final Item orb = new ItemNatureOrb();
	public static final Item failnaught = new ItemFailnaught();
	
	public static final Item splashgrenade = new ItemBrewSplashGrenade();
	public static final Item cocktail = new ItemBrewCocktail();
	public static final Item infinitewine = new ItemBrewInfiniteWine();
	
	public static final Item rewardbag = new ItemRewardBag();
	public static final Item treasure = new ItemTreasure();
	public static final Item gaiarecord = new ItemRecordA();
	
	public static final Item manasteelshield = new ItemManasteelShield();
	public static final Item terrasteelshield = new ItemTerrasteelShield();
	public static final Item elementiumshield = new ItemElementiumShield();
	public static final Item relicshield = new ItemAchilleshield();
	
	public static final Item cmhelm = new ItemCombatMaidHelm();
	public static final Item cmchest = new ItemCombatMaidChest();
	public static final Item cmleg = new ItemCombatMaidLegs();
	public static final Item cmboot = new ItemCombatMaidBoots();
	
	public static final Item cosmhelm = new ItemCosmeticMaidHelm();
	public static final Item cosmchest = new ItemCosmeticMaidChest();
	public static final Item cosmleg = new ItemCosmeticMaidLegs();
	public static final Item cosmboot = new ItemCosmeticMaidBoots();
	
	public static final Item flyingboat = new ItemFlyingBoat();
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();
		r.register(manaReader);
		r.register(spiritFuel);
		r.register(nightmareFuel);
		r.register(material);
		r.register(friedchicken);
		r.register(froststar);
		r.register(deathring);
		r.register(hammermanasteel);
		r.register(hammerelementium);
		r.register(hammerterrasteel);
		r.register(gildedmashedpotato);
		r.register(hammerultimate);
		r.register(binder);
		r.register(kinggarden);
		r.register(camera);
		r.register(orb);
		r.register(failnaught);
		r.register(rewardbag);
		r.register(treasure);
		r.register(gaiarecord);
		if(ConfigHandler.ENABLE_SHIELD){
			r.register(manasteelshield);
			r.register(terrasteelshield);
			r.register(elementiumshield);
		}
		r.register(relicshield);
		r.register(mastermanaring);
		r.register(cmboot);
		r.register(cmchest);
		r.register(cmhelm);
		r.register(cmleg);
		r.register(cosmhelm);
		r.register(cosmchest);
		r.register(cosmleg);
		r.register(cosmboot);
		r.register(manadrink);
		r.register(splashgrenade);
		r.register(cocktail);
		r.register(infinitewine);
		r.register(flyingboat);
		registerOreDictionary();
	}
	
	private static void registerOreDictionary() {
		
	}
}
