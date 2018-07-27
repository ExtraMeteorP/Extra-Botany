package com.meteor.extrabotany.common.item;

import com.meteor.extrabotany.common.item.equipment.ItemBaubleDeathRing;
import com.meteor.extrabotany.common.item.equipment.ItemBaubleFrostStar;
import com.meteor.extrabotany.common.item.tool.ItemHammer;
import com.meteor.extrabotany.common.item.tool.ItemHammerUltimate;
import com.meteor.extrabotany.common.item.tool.ItemManaReader;
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
	
	public static final Item binder = new ItemBinder();
	
	public static final Item froststar = new ItemBaubleFrostStar();
	public static final Item deathring = new ItemBaubleDeathRing();
	
	public static final Item hammermanasteel = new ItemHammer(LibItemsName.HAMMER_MANASTEEL, BotaniaAPI.manasteelToolMaterial);
	public static final Item hammerelementium = new ItemHammer(LibItemsName.HAMMER_ELEMENTIUM, BotaniaAPI.elementiumToolMaterial);
	public static final Item hammerterrasteel = new ItemHammer(LibItemsName.HAMMER_TERRASTEEL, BotaniaAPI.terrasteelToolMaterial);
	public static final Item hammerultimate = new ItemHammerUltimate();
	
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
		registerOreDictionary();
	}
	
	private static void registerOreDictionary() {
		
	}
}
