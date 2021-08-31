package com.meteor.extrabotany.common;

import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

import static com.meteor.extrabotany.common.items.ModItems.*;
import static com.meteor.extrabotany.common.blocks.ModSubtiles.*;
import static com.meteor.extrabotany.common.blocks.ModBlocks.*;

public final class ExtraBotanyGroup extends ItemGroup {

    NonNullList<ItemStack> list;

    public ExtraBotanyGroup() {
        super(LibMisc.MOD_ID);
        setNoTitle();
        setBackgroundImageName("extrabotany.png");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModItems.pylon);
    }

    @Override
    public boolean hasSearchBar() {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void fill(NonNullList<ItemStack> list) {
        this.list = list;
        addFlower(annoyingflower, annoyingflowerFloating);
        addFlower(bellflower, bellflowerFloating);
        addFlower(edelweiss, edelweissFloating);
        addFlower(geminiorchid, geminiorchidFloating);
        addFlower(sunbless, sunblessFloating);
        addFlower(moonbless, moonblessFloating);
        addFlower(omniviolet, omnivioletFloating);
        addFlower(reikarlily, reikarlilyFloating);
        addFlower(tinkleflower, tinkleflowerFloating);

        addBlock(powerframe);
        addBlock(manabuffer);

        addItem(lensmana);
        addItem(lenspotion);
        addItem(lenspush);
        addItem(lenssmelt);
        addItem(lenssupercondutor);
        addItem(lenstrace);

        addItem(gildedpotato);
        addItem(gildedmashedpotato);
        addItem(nightmarefuel);
        addItem(spiritfuel);
        addItem(spirit);
        addItem(shadowium);
        addItem(photonium);
        addItem(aerialite);
        addItem(orichalcos);
        addItem(heromedal);
        addItem(universalpetal);
        addItem(elementrune);
        addItem(sinrune);
        addItem(thechaos);
        addItem(theorigin);
        addItem(theend);
        addItem(theuniverse);

        addItem(aerostone);
        addItem(earthstone);
        addItem(aquastone);
        addItem(ignisstone);
        addItem(thecommunity);
        addItem(peaceamulet);
        addItem(powerglove);
        addItem(froststar);
        addItem(deathring);
        addItem(manadrivering);
        addItem(natureorb);
        addItem(jingweifeather);

        addItem(walkingcane);
        addItem(shadowkatana);
        addItem(influxwaver);
        addItem(starwrath);
        addItem(trueshadowkatana);
        addItem(trueterrablade);
        addItem(challengeticket);
        addItem(sagesmanaring);
        addItem(excaliber);
        addItem(failnaught);
        addItem(camera);
        addItem(coregod);
        addItem(motor);
        addItem(motoraccessory);
        addItem(cosmiccarkey);
        addItem(cosmiccarkeyaccessory);
        addItem(gemofconquest);
        addItem(flamescionweapon);
        addItem(firstfractal);
        addItem(buddhistrelics);

        addItem(armor_miku_helm);
        addItem(armor_miku_chest);
        addItem(armor_miku_legs);
        addItem(armor_miku_boots);
        addItem(armor_goblinslayer_helm);
        addItem(armor_goblinslayer_chest);
        addItem(armor_goblinslayer_legs);
        addItem(armor_goblinslayer_boots);
        addItem(armor_shadowwarrior_helm);
        addItem(armor_shadowwarrior_chest);
        addItem(armor_shadowwarrior_legs);
        addItem(armor_shadowwarrior_boots);
        addItem(armor_shootingguardian_helm);
        addItem(armor_shootingguardian_chest);
        addItem(armor_shootingguardian_legs);
        addItem(armor_shootingguardian_boots);
        addItem(armor_maid_helm);
        addItem(armor_maid_chest);
        addItem(armor_maid_legs);
        addItem(armor_maid_boots);

        addItem(foxear);
        addItem(foxmask);
        addItem(pylon);
        addItem(blackglasses);
        addItem(thuglife);
        addItem(redscarf);
        addItem(mask);
        addItem(supercrown);

        addItem(rewardbaga);
        addItem(rewardbagb);
        addItem(rewardbagc);
        addItem(rewardbagd);

        addItem(emptybottle);
        addItem(manadrink);
        addItem(cocktail);
        addItem(splashgrenade);
        addItem(infinitewine);
    }

    private void addFlower(Block flower, Block floating){
        addItem(new ItemBlockSpecialFlower(flower, defaultBuilder()));
        addItem(new ItemBlockSpecialFlower(floating, defaultBuilder()));
    }

    private void addBlock(Block block){
        new BlockItem(block, defaultBuilder()).fillItemGroup(this, list);
    };

    private void addItem(Item item) {
        item.fillItemGroup(this, list);
    }
}
