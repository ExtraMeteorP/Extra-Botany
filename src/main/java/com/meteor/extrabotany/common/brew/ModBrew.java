package com.meteor.extrabotany.common.brew;

import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.common.brew.BrewMod;

public class ModBrew {

	public static Brew revolution;
	public static Brew shell;
	public static Brew oneforall;
	public static Brew deadpool;
	public static Brew floating;

	public static void init() {
		revolution = new BrewMod("revolution", 0x483D8B, 10000, new PotionEffect(MobEffects.UNLUCK, 1800, 2),
				new PotionEffect(MobEffects.HASTE, 1800, 2));
		shell = new BrewMod("shell", 0x006400, 10000, new PotionEffect(MobEffects.SLOWNESS, 1200, 2),
				new PotionEffect(MobEffects.RESISTANCE, 1200, 2));
		oneforall = new BrewMod("oneforall", 0xFFD700, 30000, new PotionEffect(MobEffects.ABSORPTION, 900, 0),
				new PotionEffect(MobEffects.FIRE_RESISTANCE, 900, 0), new PotionEffect(MobEffects.HASTE, 900, 0),
				new PotionEffect(MobEffects.JUMP_BOOST, 900, 0), new PotionEffect(MobEffects.LUCK, 900, 0),
				new PotionEffect(MobEffects.REGENERATION, 900, 0), new PotionEffect(MobEffects.SPEED, 900, 0),
				new PotionEffect(MobEffects.STRENGTH, 900, 0));
		deadpool = new BrewMod("deadpool", 0xFF4500, 20000, new PotionEffect(MobEffects.WITHER, 300, 1),
				new PotionEffect(MobEffects.POISON, 300, 1), new PotionEffect(MobEffects.GLOWING, 1800, 2),
				new PotionEffect(MobEffects.STRENGTH, 1800, 2));
		floating = new BrewMod("floating", 0x00CED1, 6000, new PotionEffect(MobEffects.LEVITATION, 160, 2));
	}

}
