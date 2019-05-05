package com.meteor.extrabotany.common.core.handler;

import com.meteor.extrabotany.common.lib.LibMisc;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)
public class ModSounds {

    public static final SoundEvent annoyingflower = makeSoundEvent("annoyingflower");
    public static final SoundEvent gaiaMusic3 = makeSoundEvent("music.gaia3");
    public static final SoundEvent spearsubspace = makeSoundEvent("spearsubspace");
    public static final SoundEvent herrscherMusic = makeSoundEvent("music.herrscher");

    private ModSounds() {
    }

    private static SoundEvent makeSoundEvent(String name) {
        ResourceLocation loc = new ResourceLocation(LibMisc.MOD_ID, name);
        return new SoundEvent(loc).setRegistryName(loc);
    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> evt) {
        IForgeRegistry<SoundEvent> r = evt.getRegistry();
        r.register(annoyingflower);
        r.register(gaiaMusic3);
        r.register(spearsubspace);
        r.register(herrscherMusic);
    }

}
