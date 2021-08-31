package com.meteor.extrabotany.common.potions;

import com.meteor.extrabotany.common.libs.LibPotionNames;
import net.minecraft.potion.Effect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

import static com.meteor.extrabotany.common.items.ModItems.register;

public class ModPotions {

    public static final Effect incandescence = new PotionIncandescence();
    public static final Effect timelock = new PotionTimeLock();
    public static final Effect flamescion = new PotionFlamescion();
    public static final Effect bloodtemptation = new PotionBloodTempation();

    public static void registerPotions(RegistryEvent.Register<Effect> evt) {
        IForgeRegistry<Effect> r = evt.getRegistry();
        register(r, LibPotionNames.INCANDESCENCE, incandescence);
        register(r, LibPotionNames.TIMELOCK, timelock);
        register(r, LibPotionNames.FLAMESCION, flamescion);
        register(r, LibPotionNames.BLOODTEMPTATION, bloodtemptation);
    }
}
