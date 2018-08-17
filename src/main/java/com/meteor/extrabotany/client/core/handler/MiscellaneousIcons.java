package com.meteor.extrabotany.client.core.handler;

import com.meteor.extrabotany.common.item.equipment.tool.ItemKingGarden;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.client.core.helper.IconHelper;

public class MiscellaneousIcons {
	
	public static final MiscellaneousIcons INSTANCE = new MiscellaneousIcons();
	
	public TextureAtlasSprite[] kingGardenWeaponIcons;
	
	@SubscribeEvent
	public void onTextureStitch(TextureStitchEvent.Pre evt) {
		kingGardenWeaponIcons = new TextureAtlasSprite[ItemKingGarden.WEAPON_TYPES];
		for(int i = 0; i < ItemKingGarden.WEAPON_TYPES; i++)
			kingGardenWeaponIcons[i] = IconHelper.forName(evt.getMap(), "flower_weapon_" + i, "items");
	}

}
