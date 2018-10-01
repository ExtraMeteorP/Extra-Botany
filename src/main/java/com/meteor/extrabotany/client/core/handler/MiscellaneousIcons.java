package com.meteor.extrabotany.client.core.handler;

import com.meteor.extrabotany.common.item.equipment.tool.ItemKingGarden;
import com.meteor.extrabotany.common.lib.LibItemsName;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.client.core.helper.IconHelper;

public class MiscellaneousIcons {
	
	public static final MiscellaneousIcons INSTANCE = new MiscellaneousIcons();
	
	public TextureAtlasSprite[] kingGardenWeaponIcons;
	public TextureAtlasSprite[] swordDomainIcons;
	public TextureAtlasSprite puredaisyPendantIcon;
	
	@SubscribeEvent
	public void onTextureStitch(TextureStitchEvent.Pre evt) {
		kingGardenWeaponIcons = new TextureAtlasSprite[ItemKingGarden.WEAPON_TYPES];
		for(int i = 0; i < ItemKingGarden.WEAPON_TYPES; i++)
			kingGardenWeaponIcons[i] = IconHelper.forName(evt.getMap(), "flower_weapon_" + i, "items");
		swordDomainIcons = new TextureAtlasSprite[8];
		for(int i = 0; i < 8; i++)
			swordDomainIcons[i] = IconHelper.forName(evt.getMap(), "sworddomain_" + i, "items");
		puredaisyPendantIcon = IconHelper.forName(evt.getMap(), "puredaisypendant", "items");
	}

}
