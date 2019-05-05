package com.meteor.extrabotany.common.item;

import com.meteor.extrabotany.common.core.config.ConfigHandler;
import com.meteor.extrabotany.common.item.bonus.ItemCandyBag;
import com.meteor.extrabotany.common.item.bonus.ItemRewardBag;
import com.meteor.extrabotany.common.item.bonus.ItemRewardBag943;
import com.meteor.extrabotany.common.item.brew.ItemBrewCocktail;
import com.meteor.extrabotany.common.item.brew.ItemBrewInfiniteWine;
import com.meteor.extrabotany.common.item.brew.ItemBrewSplashGrenade;
import com.meteor.extrabotany.common.item.equipment.armor.combatmaid.*;
import com.meteor.extrabotany.common.item.equipment.armor.cosmeticmaid.*;
import com.meteor.extrabotany.common.item.equipment.armor.goblinslayer.ItemGoblinSlayerBoots;
import com.meteor.extrabotany.common.item.equipment.armor.goblinslayer.ItemGoblinSlayerChest;
import com.meteor.extrabotany.common.item.equipment.armor.goblinslayer.ItemGoblinSlayerHelm;
import com.meteor.extrabotany.common.item.equipment.armor.goblinslayer.ItemGoblinSlayerLegs;
import com.meteor.extrabotany.common.item.equipment.armor.shadowwarrior.ItemShadowWarriorBoots;
import com.meteor.extrabotany.common.item.equipment.armor.shadowwarrior.ItemShadowWarriorChest;
import com.meteor.extrabotany.common.item.equipment.armor.shadowwarrior.ItemShadowWarriorHelm;
import com.meteor.extrabotany.common.item.equipment.armor.shadowwarrior.ItemShadowWarriorLegs;
import com.meteor.extrabotany.common.item.equipment.bauble.*;
import com.meteor.extrabotany.common.item.equipment.shield.ItemElementiumShield;
import com.meteor.extrabotany.common.item.equipment.shield.ItemManasteelShield;
import com.meteor.extrabotany.common.item.equipment.shield.ItemTerrasteelShield;
import com.meteor.extrabotany.common.item.equipment.tool.*;
import com.meteor.extrabotany.common.item.lens.ItemLens;
import com.meteor.extrabotany.common.item.record.ItemRecordA;
import com.meteor.extrabotany.common.item.record.ItemRecordB;
import com.meteor.extrabotany.common.item.relic.*;
import com.meteor.extrabotany.common.lib.LibItemsName;
import com.meteor.extrabotany.common.lib.LibMisc;
import com.meteor.extrabotany.common.lib.LibOreDicts;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
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
    public static final Item puredaisypendant = new ItemPureDaisyPendant();
    public static final Item supercrown = new ItemSuperCrown();
    public static final Item redscarf = new ItemRedScarf();
    public static final Item godcore = new ItemCoreGod();
    public static final Item wallrunning = new ItemWallRunningRing();
    public static final Item walljumping = new ItemWallJumpingRing();
    public static final Item parkour = new ItemParkourRing();
    public static final Item allforone = new ItemAFORing();
    public static final Item elvenking = new ItemElvenKingRing();
    public static final Item foxear = new ItemFoxEar();

    public static final Item hammermanasteel = new ItemHammer(LibItemsName.HAMMER_MANASTEEL, BotaniaAPI.manasteelToolMaterial);
    public static final Item hammerelementium = new ItemHammer(LibItemsName.HAMMER_ELEMENTIUM, BotaniaAPI.elementiumToolMaterial);
    public static final Item hammerterrasteel = new ItemHammer(LibItemsName.HAMMER_TERRASTEEL, BotaniaAPI.terrasteelToolMaterial);
    public static final Item hammerultimate = new ItemHammerUltimate();

    public static final Item kinggarden = new ItemKingGarden();
    public static final Item camera = new ItemCamera();
    public static final Item orb = new ItemNatureOrb();
    public static final Item failnaught = new ItemFailnaught();
    public static final Item pocketwatch = new ItemPocketWatchMoon();
    public static final Item excaliber = new ItemExcaliber();
    public static final Item judahoath = new ItemJudahOath();

    public static final Item splashgrenade = new ItemBrewSplashGrenade();
    public static final Item cocktail = new ItemBrewCocktail();
    public static final Item infinitewine = new ItemBrewInfiniteWine();

    public static final Item treasure = new ItemTreasure();
    public static final Item gaiarecord = new ItemRecordA();
    public static final Item herrscherrecord = new ItemRecordB();

    public static final Item manasteelshield = new ItemManasteelShield();
    public static final Item terrasteelshield = new ItemTerrasteelShield();
    public static final Item elementiumshield = new ItemElementiumShield();
    public static final Item relicshield = new ItemAchilleshield();
    public static final Item relics = new ItemBuddhistRelics();
    public static final Item walkingcane = new ItemWalkingCane();

    public static final Item cmhelm = new ItemCombatMaidHelm();
    public static final Item cmchest = new ItemCombatMaidChest();
    public static final Item cmleg = new ItemCombatMaidLegs();
    public static final Item cmboot = new ItemCombatMaidBoots();
    public static final Item cmhelmrevealing = new ItemCombatMaidHelmRevealing();
    public static final Item cmchestdarkened = new ItemCombatMaidChestDarkened();

    public static final Item cosmhelm = new ItemCosmeticMaidHelm();
    public static final Item cosmchest = new ItemCosmeticMaidChest();
    public static final Item cosmleg = new ItemCosmeticMaidLegs();
    public static final Item cosmboot = new ItemCosmeticMaidBoots();
    public static final Item coshelmrevealing = new ItemCosmeticMaidHelmRevealing();

    public static final Item swhelm = new ItemShadowWarriorHelm();
    public static final Item swchest = new ItemShadowWarriorChest();
    public static final Item swleg = new ItemShadowWarriorLegs();
    public static final Item swboot = new ItemShadowWarriorBoots();
    public static final Item shadowkatana = new ItemShadowKatana();

    public static final Item gshelm = new ItemGoblinSlayerHelm();
    public static final Item gschest = new ItemGoblinSlayerChest();
    public static final Item gsleg = new ItemGoblinSlayerLegs();
    public static final Item gsboot = new ItemGoblinSlayerBoots();

    public static final Item flyingboat = new ItemFlyingBoat();
    public static final Item masterhandbag = new ItemMasterHandbag();
    public static final Item spearsubspace = new ItemSpearSubspace();

    public static final Item candy = new ItemHalloweenCandy();

    public static final Item rewardbag = new ItemRewardBag();
    public static final Item candybag = new ItemCandyBag();
    public static final Item rewardbag943 = new ItemRewardBag943();

    public static final Item lens = new ItemLens();
    public static final Item bottledflame = new ItemBottledFlame();
    public static final Item bottledstar = new ItemBottledStar();
    public static final Item bottledpixie = new ItemBottledPixie();

    public static final Item mask = new ItemCosmetic();
    //public static final Item gamewinner = new ItemGameWinner();

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
        r.register(relics);
        r.register(treasure);
        r.register(gaiarecord);
        if (ConfigHandler.ENABLE_SHIELD) {
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
        r.register(coshelmrevealing);
        r.register(cmhelmrevealing);
        r.register(swboot);
        r.register(swchest);
        r.register(swhelm);
        r.register(swleg);
        r.register(cmchestdarkened);
        r.register(shadowkatana);
        r.register(excaliber);
        r.register(puredaisypendant);
        r.register(supercrown);
        r.register(redscarf);
        r.register(masterhandbag);
        r.register(spearsubspace);
        r.register(godcore);
        r.register(candy);
        r.register(rewardbag);
        r.register(candybag);
        r.register(judahoath);
        r.register(wallrunning);
        r.register(walljumping);
        r.register(parkour);
        r.register(walkingcane);
        r.register(allforone);
        r.register(elvenking);
        r.register(rewardbag943);
        r.register(gshelm);
        r.register(gschest);
        r.register(gsleg);
        r.register(gsboot);
        r.register(foxear);
        r.register(lens);
        r.register(bottledflame);
        r.register(bottledstar);
        r.register(bottledpixie);
        r.register(mask);
        //r.register(gamewinner);
        r.register(herrscherrecord);
        registerOreDictionary();
        //A
        if (ConfigHandler.REWARDBAG1.length > 0)
            for (int i = 0; i < ConfigHandler.REWARDBAG1.length; i++)
                ItemRewardBag.parseItems(ConfigHandler.REWARDBAG1[i], ItemRewardBag.categoryA);
        //B
        if (ConfigHandler.REWARDBAG2.length > 0)
            for (int i = 0; i < ConfigHandler.REWARDBAG2.length; i++)
                ItemRewardBag.parseItems(ConfigHandler.REWARDBAG2[i], ItemRewardBag.categoryB);
        //C
        if (ConfigHandler.REWARDBAG3.length > 0)
            for (int i = 0; i < ConfigHandler.REWARDBAG3.length; i++)
                ItemRewardBag.parseItems(ConfigHandler.REWARDBAG3[i], ItemRewardBag.categoryC);
        //D
        if (ConfigHandler.REWARDBAG4.length > 0)
            for (int i = 0; i < ConfigHandler.REWARDBAG4.length; i++)
                ItemRewardBag.parseItems(ConfigHandler.REWARDBAG4[i], ItemRewardBag.categoryD);

        if (ConfigHandler.REWARDBAG5.length > 0)
            for (int i = 0; i < ConfigHandler.REWARDBAG5.length; i++)
                ItemRewardBag.parseItems(ConfigHandler.REWARDBAG5[i], ItemRewardBag943.categorysE);

        if (ConfigHandler.REWARDBAG6.length > 0)
            for (int i = 0; i < ConfigHandler.REWARDBAG6.length; i++)
                ItemRewardBag.parseItems(ConfigHandler.REWARDBAG6[i], ItemCandyBag.category);
    }

    private static void registerOreDictionary() {
        OreDictionary.registerOre(LibOreDicts.PHOTONIUM, new ItemStack(material, 1, 8));
        OreDictionary.registerOre(LibOreDicts.SHADOWIUM, new ItemStack(material, 1, 5));
        OreDictionary.registerOre(LibOreDicts.ORICHALCOS, new ItemStack(material, 1, 1));
        OreDictionary.registerOre(LibOreDicts.GODWEAVE, new ItemStack(material, 1, 7));
        for (int i = 0; i < 2; i++)
            OreDictionary.registerOre(LibOreDicts.COREGOD, new ItemStack(godcore, 1, i));
    }
}
