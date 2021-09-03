package com.meteor.extrabotany.common.items.brew;

import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import vazkii.botania.api.brew.Brew;

import java.util.Arrays;
import static com.meteor.extrabotany.common.items.ModItems.*;

public class ModBrew {

    public static final Brew revolution = make(10000, new EffectInstance(Effects.UNLUCK, 1800, 2),
            new EffectInstance(Effects.HASTE, 1800, 2));
    public static final Brew shell = make(10000, new EffectInstance(Effects.SLOWNESS, 1200, 2),
            new EffectInstance(Effects.RESISTANCE, 1200, 2));
    public static final Brew allmighty = make(30000, new EffectInstance(Effects.ABSORPTION, 900, 0),
            new EffectInstance(Effects.FIRE_RESISTANCE, 900, 0), new EffectInstance(Effects.HASTE, 900, 0),
            new EffectInstance(Effects.JUMP_BOOST, 900, 0), new EffectInstance(Effects.LUCK, 900, 0),
            new EffectInstance(Effects.REGENERATION, 900, 0), new EffectInstance(Effects.SPEED, 900, 0),
            new EffectInstance(Effects.STRENGTH, 900, 0));
    public static final Brew deadpool = make(20000, new EffectInstance(Effects.WITHER, 300, 1),
            new EffectInstance(Effects.POISON, 300, 1), new EffectInstance(Effects.GLOWING, 3600, 2),
            new EffectInstance(Effects.STRENGTH, 3600, 2));
    public static final Brew floating = make(2000, new EffectInstance(Effects.LEVITATION, 160, 2));

    public static void registerBrews(RegistryEvent.Register<Brew> evt) {
        IForgeRegistry<Brew> r = evt.getRegistry();
        register(r, "revolution", revolution);
        register(r, "shell", shell);
        register(r, "allmighty", allmighty);
        register(r, "deadpool", deadpool);
        register(r, "floating", floating);
    }

    private static Brew make(int cost, EffectInstance... effects) {
        return new Brew(PotionUtils.getPotionColorFromEffectList(Arrays.asList(effects)), cost, effects);
    }

}
