package com.meteor.extrabotany.common.item.enchantments;

import com.meteor.extrabotany.common.lib.LibMisc;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)
public class ModEnchantments {

    public static final Enchantment pixiebless = new EnchantmentPixieBless();
    public static final Enchantment instantheal = new EnchantmentInstantHeal();

    @SubscribeEvent
    public static void registerEnchantments(RegistryEvent.Register<Enchantment> evt) {
        IForgeRegistry<Enchantment> r = evt.getRegistry();
        //r.register(pixiebless);
        //r.register(instantheal);
    }

}
