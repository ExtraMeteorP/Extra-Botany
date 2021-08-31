package com.meteor.extrabotany.common.items;

import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.api.items.WeightCategory;
import com.meteor.extrabotany.common.crafting.recipe.*;
import com.meteor.extrabotany.common.items.armor.goblinslayer.ItemGoblinSlayerArmor;
import com.meteor.extrabotany.common.items.armor.maid.ItemMaidArmor;
import com.meteor.extrabotany.common.items.armor.maid.ItemMaidHelm;
import com.meteor.extrabotany.common.items.armor.miku.ItemMikuArmor;
import com.meteor.extrabotany.common.items.armor.shadowwarrior.ItemShadowWarriorArmor;
import com.meteor.extrabotany.common.items.armor.shootingguardian.ItemShootingGuardianArmor;
import com.meteor.extrabotany.common.items.armor.shootingguardian.ItemShootingGuardianHelm;
import com.meteor.extrabotany.common.items.bauble.*;
import com.meteor.extrabotany.common.items.bauble.mount.ItemCosmicCarKeyAccessory;
import com.meteor.extrabotany.common.items.bauble.mount.ItemMotorAccessory;
import com.meteor.extrabotany.common.items.brew.ItemCocktail;
import com.meteor.extrabotany.common.items.brew.ItemInfiniteWine;
import com.meteor.extrabotany.common.items.brew.ItemSplashGrenade;
import com.meteor.extrabotany.common.items.lens.*;
import com.meteor.extrabotany.common.items.relic.*;
import com.meteor.extrabotany.common.libs.LibItemNames;
import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import vazkii.botania.api.BotaniaAPI;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

    public static final Food SPIRITFUEL_PROP = (new Food.Builder()).hunger(4).saturation(0.3F)
            .setAlwaysEdible()
            .effect(new EffectInstance(Effects.INSTANT_HEALTH, 1, 2), 1.0F)
            .effect(new EffectInstance(Effects.NIGHT_VISION, 500), 1.0F)
            .effect(new EffectInstance(Effects.SPEED, 500), 1.0F)
            .effect(new EffectInstance(Effects.LUCK, 500), 1.0F)
            .effect(new EffectInstance(Effects.STRENGTH, 500), 1.0F)
            .build();
    public static final Food NIGHTMAREFUEL_PROP = (new Food.Builder()).hunger(4).saturation(0.3F)
            .setAlwaysEdible()
            .effect(new EffectInstance(Effects.INSTANT_DAMAGE, 1, 2), 1.0F)
            .effect(new EffectInstance(Effects.BLINDNESS, 500), 1.0F)
            .effect(new EffectInstance(Effects.SLOWNESS, 500), 1.0F)
            .effect(new EffectInstance(Effects.UNLUCK, 500), 1.0F)
            .effect(new EffectInstance(Effects.WEAKNESS, 500), 1.0F)
            .build();
    public static final Food GILDEDMASHEDPOTATO_PROP = (new Food.Builder()).hunger(5).saturation(0.2F)
            .setAlwaysEdible()
            .effect(new EffectInstance(Effects.RESISTANCE, 600, 3), 1.0F)
            .effect(new EffectInstance(Effects.SLOWNESS, 600, 3), 1.0F)
            .effect(new EffectInstance(Effects.ABSORPTION, 600, 1), 1.0F)
            .build();
    public static final Food MANADRINK_PROP = (new Food.Builder()).hunger(0).saturation(0F)
            .setAlwaysEdible()
            .effect(new EffectInstance(Effects.RESISTANCE, 1200, 0), 1.0F)
            .effect(new EffectInstance(Effects.SPEED, 1200, 0), 1.0F)
            .effect(new EffectInstance(Effects.JUMP_BOOST, 1200, 0), 1.0F)
            .build();
    public static final Food FRIEDCHICKEN_PROP = (new Food.Builder()).hunger(6).saturation(0.5F).build();

    public static final Item spiritfuel = new Item(defaultBuilder().food(SPIRITFUEL_PROP));
    public static final Item nightmarefuel = new ItemNightmareFuel(defaultBuilder().food(NIGHTMAREFUEL_PROP));
    public static final Item friedchicken = new Item(defaultBuilder().food(FRIEDCHICKEN_PROP));
    public static final Item gildedmashedpotato = new Item(defaultBuilder().food(GILDEDMASHEDPOTATO_PROP));

    public static final Item motor = new ItemMotor();
    public static final Item gemofconquest = new ItemGemOfConquest();
    public static final Item firstfractal = new ItemFirstFractal();
    public static final Item cosmiccarkey = new ItemCosmicCarKey();
    public static final Item flamescionweapon = new ItemFlamescionWeapon();
    public static final Item silverbullet = new ItemSilverBullet(unstackable());
    public static final Item walkingcane = new ItemWalkingCane(unstackable());
    public static final Item shadowkatana = new ItemShadowKatana(unstackable());

    public static final Item peaceamulet = new ItemBauble(unstackable());
    public static final Item aerostone = new ItemAeroStone(unstackable());
    public static final Item earthstone = new ItemEarthStone(unstackable());
    public static final Item aquastone = new ItemAquaStone(unstackable());
    public static final Item ignisstone = new ItemIgnisStone(unstackable());
    public static final Item thecommunity = new ItemTheCommunity(unstackable());
    public static final Item froststar = new ItemFrostStar(unstackable());
    public static final Item deathring = new ItemDeathRing(unstackable());
    public static final Item manadrivering = new ItemManaDriveRing(unstackable());
    public static final Item natureorb = new ItemNatureOrb(unstackable());
    public static final Item powerglove = new ItemPowerGlove(unstackable());
    public static final Item jingweifeather = new ItemJingweiFeather(unstackable());
    public static final Item motoraccessory = new ItemMotorAccessory(unstackable());
    public static final Item cosmiccarkeyaccessory = new ItemCosmicCarKeyAccessory(unstackable());

    public static final Item sagesmanaring = new ItemSagesManaRing(relic());
    public static final Item excaliber = new ItemExcaliber(relic());
    public static final Item failnaught = new ItemFailnaught(relic());
    public static final Item influxwaver = new ItemInfluxWaver(relic());
    public static final Item trueterrablade = new ItemTrueTerrablade(relic());
    public static final Item trueshadowkatana = new ItemTrueShadowKatana(relic());
    public static final Item starwrath = new ItemStarWrath(relic());
    public static final Item buddhistrelics = new ItemBuddhistrelics(relic());
    public static final Item camera = new ItemCamera(relic());
    public static final Item coregod = new ItemCoreGod(relic());

    public static final Item spirit = new Item(defaultBuilder());
    public static final Item orichalcos = new Item(defaultBuilder());
    public static final Item gildedpotato = new Item(defaultBuilder());
    public static final Item heromedal = new Item(defaultBuilder());
    public static final Item shadowium = new Item(defaultBuilder());
    public static final Item goldcloth = new Item(defaultBuilder());
    public static final Item photonium = new Item(defaultBuilder());
    public static final Item emptybottle = new Item(defaultBuilder());
    public static final Item aerialite = new Item(defaultBuilder());
    public static final Item thechaos = new Item(defaultBuilder());
    public static final Item theorigin = new Item(defaultBuilder());
    public static final Item theend = new Item(defaultBuilder());
    public static final Item theuniverse = new Item(defaultBuilder());
    public static final Item universalpetal = new Item(defaultBuilder());
    public static final Item elementrune = new Item(defaultBuilder());
    public static final Item sinrune = new Item(defaultBuilder());

    public static final Item challengeticket = new ItemChallengeTicket(defaultBuilder());

    public static final Item manadrink = new ItemManaDrink(defaultBuilder().food(MANADRINK_PROP));
    public static final Item cocktail = new ItemCocktail(unstackable());
    public static final Item infinitewine = new ItemInfiniteWine(unstackable());
    public static final Item splashgrenade = new ItemSplashGrenade(defaultBuilder().maxStackSize(32));

    public static final Item lenssmelt = new ItemLens(unstackable(), new LensSmelt(), vazkii.botania.common.item.lens.ItemLens.PROP_TOUCH);
    public static final Item lensmana = new ItemLens(unstackable(), new LensMana(), vazkii.botania.common.item.lens.ItemLens.PROP_INTERACTION);
    public static final Item lenstrace = new ItemLens(unstackable(), new LensTrace(), vazkii.botania.common.item.lens.ItemLens.PROP_CONTROL);
    public static final Item lenspush = new ItemLens(unstackable(), new LensPush(), vazkii.botania.common.item.lens.ItemLens.PROP_INTERACTION);
    public static final Item lenspotion = new ItemLens(unstackable(), new LensPotion(), vazkii.botania.common.item.lens.ItemLens.PROP_INTERACTION);
    public static final Item lenssupercondutor = new ItemLens(unstackable(), new LensSuperconductor(), vazkii.botania.common.item.lens.ItemLens.PROP_POWER);

    public static final Item foxear = new ItemBaubleCosmetic(ItemBaubleCosmetic.Variant.FOX_EAR, unstackable());
    public static final Item foxmask = new ItemBaubleCosmetic(ItemBaubleCosmetic.Variant.FOX_MASK, unstackable());
    public static final Item blackglasses = new ItemBaubleCosmetic(ItemBaubleCosmetic.Variant.BLACK_GLASSES, unstackable());
    public static final Item thuglife = new ItemBaubleCosmetic(ItemBaubleCosmetic.Variant.THUG_LIFE, unstackable());
    public static final Item redscarf = new ItemBaubleCosmetic(ItemBaubleCosmetic.Variant.RED_SCARF, unstackable());
    public static final Item supercrown = new ItemBaubleCosmetic(ItemBaubleCosmetic.Variant.SUPER_CROWN, unstackable());
    public static final Item pylon = new ItemBaubleCosmetic(ItemBaubleCosmetic.Variant.PYLON, unstackable());
    public static final Item mask = new ItemBaubleCosmetic(ItemBaubleCosmetic.Variant.MASK, unstackable());

    public static final List<WeightCategory> categoryListA = new ArrayList<>();
    public static final List<WeightCategory> categoryListB = new ArrayList<>();
    public static final List<WeightCategory> categoryListC = new ArrayList<>();
    public static final List<WeightCategory> categoryListD = new ArrayList<>();

    public static final Item treasurebox = new ItemTreasureBox(unstackable());
    public static final Item rewardbaga = new ItemRewardBag(defaultBuilder(), categoryListA);
    public static final Item rewardbagb = new ItemRewardBag(defaultBuilder(), categoryListB);
    public static final Item rewardbagc = new ItemRewardBag(defaultBuilder(), categoryListC);
    public static final Item rewardbagd = new ItemRewardBag(defaultBuilder(), categoryListD);

    public static final Item armor_maid_helm = new ItemMaidHelm(unstackable());
    public static final Item armor_maid_chest = new ItemMaidArmor(EquipmentSlotType.CHEST, unstackable());
    public static final Item armor_maid_legs = new ItemMaidArmor(EquipmentSlotType.LEGS, unstackable());
    public static final Item armor_maid_boots = new ItemMaidArmor(EquipmentSlotType.FEET, unstackable());

    public static final Item armor_miku_helm = new ItemMikuArmor(EquipmentSlotType.HEAD, unstackable());
    public static final Item armor_miku_chest = new ItemMikuArmor(EquipmentSlotType.CHEST, unstackable());
    public static final Item armor_miku_legs = new ItemMikuArmor(EquipmentSlotType.LEGS, unstackable());
    public static final Item armor_miku_boots = new ItemMikuArmor(EquipmentSlotType.FEET, unstackable());

    public static final Item armor_goblinslayer_helm = new ItemGoblinSlayerArmor(EquipmentSlotType.HEAD, unstackable());
    public static final Item armor_goblinslayer_chest = new ItemGoblinSlayerArmor(EquipmentSlotType.CHEST, unstackable());
    public static final Item armor_goblinslayer_legs = new ItemGoblinSlayerArmor(EquipmentSlotType.LEGS, unstackable());
    public static final Item armor_goblinslayer_boots = new ItemGoblinSlayerArmor(EquipmentSlotType.FEET, unstackable());

    public static final Item armor_shadowwarrior_helm = new ItemShadowWarriorArmor(EquipmentSlotType.HEAD, unstackable());
    public static final Item armor_shadowwarrior_chest = new ItemShadowWarriorArmor(EquipmentSlotType.CHEST, unstackable());
    public static final Item armor_shadowwarrior_legs = new ItemShadowWarriorArmor(EquipmentSlotType.LEGS, unstackable());
    public static final Item armor_shadowwarrior_boots = new ItemShadowWarriorArmor(EquipmentSlotType.FEET, unstackable());

    public static final Item armor_shootingguardian_helm = new ItemShootingGuardianHelm(unstackable());
    public static final Item armor_shootingguardian_chest = new ItemShootingGuardianArmor(EquipmentSlotType.CHEST, unstackable());
    public static final Item armor_shootingguardian_legs = new ItemShootingGuardianArmor(EquipmentSlotType.LEGS, unstackable());
    public static final Item armor_shootingguardian_boots = new ItemShootingGuardianArmor(EquipmentSlotType.FEET, unstackable());

    public static Item.Properties defaultBuilder() {
        return new Item.Properties().group(ExtraBotany.itemGroup);
    }

    private static Item.Properties unstackable() {
        return defaultBuilder().maxStackSize(1);
    }

    private static Item.Properties relic(){
        return defaultBuilder().rarity(BotaniaAPI.instance().getRelicRarity()).setNoRepair();
    };

    public static void registerItems(RegistryEvent.Register<Item> evt) {
        IForgeRegistry<Item> r = evt.getRegistry();
        register(r, LibItemNames.SPIRITFUEL, spiritfuel);
        register(r, LibItemNames.NIGHTMAREFUEL, nightmarefuel);
        register(r, LibItemNames.GILDEDMASHEDPOTATO, gildedmashedpotato);
        register(r, LibItemNames.FRIEDCHICKEN, friedchicken);

        register(r, LibItemNames.CHALLENGETICKET, challengeticket);

        register(r, LibItemNames.MOTOR, motor);
        register(r, LibItemNames.GEMOFCONQUEST, gemofconquest);
        register(r, LibItemNames.FIRSTFRACTAL, firstfractal);
        register(r, LibItemNames.COSMICCARKEY, cosmiccarkey);
        register(r, LibItemNames.FLAMESCIONWEAPON, flamescionweapon);
        register(r, LibItemNames.SILVERBULLET, silverbullet);
        register(r, LibItemNames.WALKINGCANE, walkingcane);
        register(r, LibItemNames.SHADOWKATANA, shadowkatana);

        register(r, LibItemNames.AEROSTONE, aerostone);
        register(r, LibItemNames.EARTHSTONE, earthstone);
        register(r, LibItemNames.AQUASTONE, aquastone);
        register(r, LibItemNames.IGNISSTONE, ignisstone);
        register(r, LibItemNames.THECOMMUNITY, thecommunity);
        register(r, LibItemNames.PEACEAMULET, peaceamulet);
        register(r, LibItemNames.FROSTSTAR, froststar);
        register(r, LibItemNames.DEATHRING, deathring);
        register(r, LibItemNames.MANADRIVERRING, manadrivering);
        register(r, LibItemNames.NATUREORB, natureorb);
        register(r, LibItemNames.POWERGLOVE, powerglove);
        register(r, LibItemNames.JINGWEIFEATHER, jingweifeather);
        register(r, LibItemNames.MOTORACCESSORY, motoraccessory);
        register(r, LibItemNames.COSMICCARKEYACCESSORY, cosmiccarkeyaccessory);

        register(r, LibItemNames.EXCALIBER, excaliber);
        register(r, LibItemNames.SAGES_MANA_RING, sagesmanaring);
        register(r, LibItemNames.FAILNAUGHT, failnaught);
        register(r, LibItemNames.INFLUXWAVER, influxwaver);
        register(r, LibItemNames.TRUETERRABLADE, trueterrablade);
        register(r, LibItemNames.TRUESHADOWKATANA, trueshadowkatana);
        register(r, LibItemNames.STARWRATH, starwrath);
        register(r, LibItemNames.CAMERA, camera);
        register(r, LibItemNames.BUDDHISTRELICS, buddhistrelics);
        register(r, LibItemNames.COREGOD, coregod);

        register(r, LibItemNames.MANADRINK, manadrink);
        register(r, LibItemNames.BREW_COCKTAIL, cocktail);
        register(r, LibItemNames.BREW_INFINITEWINE, infinitewine);
        register(r, LibItemNames.BREW_SPLASHGRENADE, splashgrenade);

        register(r, LibItemNames.SPIRIT, spirit);
        register(r, LibItemNames.ORICHALCOS, orichalcos);
        register(r, LibItemNames.PHONTONIUM, photonium);
        register(r, LibItemNames.SHADOWIUM, shadowium);
        register(r, LibItemNames.AERIALITE, aerialite);
        register(r, LibItemNames.GILDEDPOTATO, gildedpotato);
        register(r, LibItemNames.HEROMEDAL, heromedal);
        register(r, LibItemNames.GOLDCLOTH, goldcloth);
        register(r, LibItemNames.EMPTYBOTTLE, emptybottle);
        register(r, LibItemNames.THECHAOS, thechaos);
        register(r, LibItemNames.THEORIGIN, theorigin);
        register(r, LibItemNames.THEEND, theend);
        register(r, LibItemNames.THEUNIVERSE, theuniverse);
        register(r, LibItemNames.UNIVERSALPETAL, universalpetal);
        register(r, LibItemNames.ELEMENTRUNE, elementrune);
        register(r, LibItemNames.SINRUNE, sinrune);

        register(r, LibItemNames.LENS_MANA, lensmana);
        register(r, LibItemNames.LENS_POTION, lenspotion);
        register(r, LibItemNames.LENS_PUSH, lenspush);
        register(r, LibItemNames.LENS_SMELT, lenssmelt);
        register(r, LibItemNames.LENS_SUPERCONDUCTOR, lenssupercondutor);
        register(r, LibItemNames.LENS_TRACE, lenstrace);

        register(r, LibItemNames.FOX_EAR, foxear);
        register(r, LibItemNames.FOX_MASK, foxmask);
        register(r, LibItemNames.PYLON, pylon);
        register(r, LibItemNames.BLACK_GLASSES, blackglasses);
        register(r, LibItemNames.RED_SCARF, redscarf);
        register(r, LibItemNames.THUG_LIFE, thuglife);
        register(r, LibItemNames.SUPER_CROWN, supercrown);
        register(r, LibItemNames.MASK, mask);

        register(r, LibItemNames.ARMOR_MAID_HELM, armor_maid_helm);
        register(r, LibItemNames.ARMOR_MAID_CHEST, armor_maid_chest);
        register(r, LibItemNames.ARMOR_MAID_LEGS, armor_maid_legs);
        register(r, LibItemNames.ARMOR_MAID_BOOTS, armor_maid_boots);

        register(r, LibItemNames.ARMOR_MIKU_HELM, armor_miku_helm);
        register(r, LibItemNames.ARMOR_MIKU_CHEST, armor_miku_chest);
        register(r, LibItemNames.ARMOR_MIKU_LEGS, armor_miku_legs);
        register(r, LibItemNames.ARMOR_MIKU_BOOTS, armor_miku_boots);

        register(r, LibItemNames.ARMOR_GOBLINSLAYER_HELM, armor_goblinslayer_helm);
        register(r, LibItemNames.ARMOR_GOBLINSLAYER_CHEST, armor_goblinslayer_chest);
        register(r, LibItemNames.ARMOR_GOBLINSLAYER_LEGS, armor_goblinslayer_legs);
        register(r, LibItemNames.ARMOR_GOBLINSLAYER_BOOTS, armor_goblinslayer_boots);

        register(r, LibItemNames.ARMOR_SHADOWWARRIOR_HELM, armor_shadowwarrior_helm);
        register(r, LibItemNames.ARMOR_SHADOWWARRIOR_CHEST, armor_shadowwarrior_chest);
        register(r, LibItemNames.ARMOR_SHADOWWARRIOR_LEGS, armor_shadowwarrior_legs);
        register(r, LibItemNames.ARMOR_SHADOWWARRIOR_BOOTS, armor_shadowwarrior_boots);

        register(r, LibItemNames.ARMOR_SHOOTINGGUARDIAN_HELM, armor_shootingguardian_helm);
        register(r, LibItemNames.ARMOR_SHOOTINGGUARDIAN_CHEST, armor_shootingguardian_chest);
        register(r, LibItemNames.ARMOR_SHOOTINGGUARDIAN_LEGS, armor_shootingguardian_legs);
        register(r, LibItemNames.ARMOR_SHOOTINGGUARDIAN_BOOTS, armor_shootingguardian_boots);

        categoryListA.add(new WeightCategory(new ItemStack(universalpetal, 4), 10));
        categoryListA.add(new WeightCategory(new ItemStack(universalpetal, 8), 10));
        categoryListA.add(new WeightCategory(new ItemStack(universalpetal, 6), 30));

        categoryListB.add(new WeightCategory(new ItemStack(elementrune, 2), 80));
        categoryListB.add(new WeightCategory(new ItemStack(sinrune, 1), 20));

        categoryListC.add(new WeightCategory(new ItemStack(vazkii.botania.common.item.ModItems.manaSteel, 4), 15));
        categoryListC.add(new WeightCategory(new ItemStack(vazkii.botania.common.item.ModItems.manaPearl, 4), 15));
        categoryListC.add(new WeightCategory(new ItemStack(vazkii.botania.common.item.ModItems.manaDiamond, 4), 15));
        categoryListC.add(new WeightCategory(new ItemStack(vazkii.botania.common.item.ModItems.elementium, 3), 11));
        categoryListC.add(new WeightCategory(new ItemStack(vazkii.botania.common.item.ModItems.pixieDust, 3), 11));
        categoryListC.add(new WeightCategory(new ItemStack(vazkii.botania.common.item.ModItems.dragonstone, 3), 11));
        categoryListC.add(new WeightCategory(new ItemStack(vazkii.botania.common.item.ModItems.manaPowder, 8), 10));
        categoryListC.add(new WeightCategory(new ItemStack(vazkii.botania.common.item.ModItems.terrasteel, 1), 9));
        categoryListC.add(new WeightCategory(new ItemStack(vazkii.botania.common.item.ModItems.lifeEssence, 4), 8));
        categoryListC.add(new WeightCategory(new ItemStack(vazkii.botania.common.item.ModItems.gaiaIngot, 1), 7));
        categoryListC.add(new WeightCategory(new ItemStack(heromedal, 1), 1));

        categoryListD.add(new WeightCategory(new ItemStack(Items.COAL, 6), 40));
        categoryListD.add(new WeightCategory(new ItemStack(Items.IRON_INGOT, 4), 36));
        categoryListD.add(new WeightCategory(new ItemStack(Items.GOLD_INGOT, 4), 24));
        categoryListD.add(new WeightCategory(new ItemStack(Items.REDSTONE, 8), 22));
        categoryListD.add(new WeightCategory(new ItemStack(Items.ENDER_PEARL, 4), 20));
        categoryListD.add(new WeightCategory(new ItemStack(Items.DIAMOND, 1), 18));
        categoryListD.add(new WeightCategory(new ItemStack(vazkii.botania.common.item.ModItems.blackerLotus, 2), 16));
        categoryListD.add(new WeightCategory(new ItemStack(vazkii.botania.common.item.ModItems.overgrowthSeed, 1), 12));
        categoryListD.add(new WeightCategory(new ItemStack(buddhistrelics), 1));

        register(r, LibItemNames.TREASUREBOX, treasurebox);
        register(r, LibItemNames.REWARDBAGA, rewardbaga);
        register(r, LibItemNames.REWARDBAGB, rewardbagb);
        register(r, LibItemNames.REWARDBAGC, rewardbagc);
        register(r, LibItemNames.REWARDBAGD, rewardbagd);
    }

    public static void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> evt) {
        IForgeRegistry<IRecipeSerializer<?>> r = evt.getRegistry();
        register(r, "goldcloth", GoldClothRecipe.SERIALIZER);
        register(r, "infinitewine_change", InfiniteWineChangeRecipe.SERIALIZER);
        register(r, "holygrenade", HolyGrenadeRecipe.SERIALIZER);
        register(r, "infinitewine", InfiniteWineRecipe.SERIALIZER);
        register(r, "cocktail", CocktailRecipe.SERIALIZER);
        register(r, "lenspotion", LensPotionRecipe.SERIALIZER);
        register(r, "relicupgrade", RelicUpgradeRecipe.SERIALIZER);
        register(r, "relicupgradeshaped", RelicUpgradeShapedRecipe.SERIALIZER);
    }

    public static <V extends IForgeRegistryEntry<V>> void register(IForgeRegistry<V> reg, ResourceLocation name, IForgeRegistryEntry<V> thing) {
        reg.register(thing.setRegistryName(name));
    }

    public static <V extends IForgeRegistryEntry<V>> void register(IForgeRegistry<V> reg, String name, IForgeRegistryEntry<V> thing) {
        register(reg, prefix(name), thing);
    }

    public static ResourceLocation prefix(String path) {
        return new ResourceLocation(LibMisc.MOD_ID, path);
    }
}
