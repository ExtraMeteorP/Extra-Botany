package com.meteor.extrabotany.common.brew;

import com.meteor.extrabotany.common.brew.potion.PotionBlackSabbath;
import com.meteor.extrabotany.common.brew.potion.PotionBloodTemptation;
import com.meteor.extrabotany.common.brew.potion.PotionConstantPain;
import com.meteor.extrabotany.common.brew.potion.PotionDivineJustice;
import com.meteor.extrabotany.common.brew.potion.PotionEternity;
import com.meteor.extrabotany.common.brew.potion.PotionHealReverse;
import com.meteor.extrabotany.common.brew.potion.PotionMindCrack;
import com.meteor.extrabotany.common.brew.potion.PotionReflect;
import com.meteor.extrabotany.common.brew.potion.PotionTemptation;
import com.meteor.extrabotany.common.brew.potion.PotionVegetable;
import com.meteor.extrabotany.common.brew.potion.PotionWitchCurse;
import com.meteor.extrabotany.common.lib.Reference;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModPotions {

	public static final Potion mindcrack = new PotionMindCrack();
	public static final Potion eternity = new PotionEternity();
	public static final Potion reflect = new PotionReflect();
	public static final Potion blacksabbath = new PotionBlackSabbath();
	public static final Potion constantpain = new PotionConstantPain();
	public static final Potion divinejustice = new PotionDivineJustice();
	public static final Potion bloodtemptation = new PotionBloodTemptation();
	public static final Potion temptation = new PotionTemptation();
	public static final Potion witchcurse = new PotionWitchCurse();
	public static final Potion vegetable = new PotionVegetable();
	public static final Potion healreverse = new PotionHealReverse();

	@SubscribeEvent
	public static void registerPotions(RegistryEvent.Register<Potion> evt) {
		evt.getRegistry().register(mindcrack);
		evt.getRegistry().register(eternity);
		evt.getRegistry().register(reflect);
		evt.getRegistry().register(constantpain);
		evt.getRegistry().register(divinejustice);
		evt.getRegistry().register(blacksabbath);
		evt.getRegistry().register(bloodtemptation);
		evt.getRegistry().register(temptation);
		evt.getRegistry().register(witchcurse);
		evt.getRegistry().register(vegetable);
		evt.getRegistry().register(healreverse);
	}

}
