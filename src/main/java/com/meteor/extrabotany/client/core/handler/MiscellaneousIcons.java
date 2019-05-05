package com.meteor.extrabotany.client.core.handler;

import com.meteor.extrabotany.common.item.equipment.tool.ItemKingGarden;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.client.core.helper.IconHelper;

public class MiscellaneousIcons {

    public static final MiscellaneousIcons INSTANCE = new MiscellaneousIcons();

    public TextureAtlasSprite[] kingGardenWeaponIcons;
    public TextureAtlasSprite[] swordDomainIcons;
    public TextureAtlasSprite[] godcoreIcons;
    public TextureAtlasSprite puredaisyPendantIcon;
    public TextureAtlasSprite volantoroIcon;
    public TextureAtlasSprite[] judahIcons;
    public TextureAtlasSprite[] judahIcons2;
    public TextureAtlasSprite[] spearIcons;
    public TextureAtlasSprite foxearIcon;

    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Pre evt) {
        kingGardenWeaponIcons = new TextureAtlasSprite[ItemKingGarden.WEAPON_TYPES];
        for (int i = 0; i < ItemKingGarden.WEAPON_TYPES; i++)
            kingGardenWeaponIcons[i] = IconHelper.forName(evt.getMap(), "flower_weapon_" + i, "items");
        swordDomainIcons = new TextureAtlasSprite[8];
        for (int i = 0; i < 8; i++)
            swordDomainIcons[i] = IconHelper.forName(evt.getMap(), "sworddomain_" + i, "items");
        puredaisyPendantIcon = IconHelper.forName(evt.getMap(), "puredaisypendant", "items");

        godcoreIcons = new TextureAtlasSprite[3];
        for (int i = 0; i < 3; i++)
            godcoreIcons[i] = IconHelper.forName(evt.getMap(), "godcore_" + i, "items");

        volantoroIcon = IconHelper.forName(evt.getMap(), "volantoro", "items");

        judahIcons = new TextureAtlasSprite[4];
        for (int i = 0; i < 4; i++)
            judahIcons[i] = IconHelper.forName(evt.getMap(), "judahoath_" + i, "items");

        judahIcons2 = new TextureAtlasSprite[4];
        for (int i = 0; i < 4; i++)
            judahIcons2[i] = IconHelper.forName(evt.getMap(), "judah_" + i, "items");

        spearIcons = new TextureAtlasSprite[4];
        for (int i = 0; i < 4; i++)
            spearIcons[i] = IconHelper.forName(evt.getMap(), "spear_" + i, "items");
        foxearIcon = IconHelper.forName(evt.getMap(), "foxear", "items");
    }

}
