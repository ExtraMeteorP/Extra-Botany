package com.meteor.extrabotany.common.lexicon;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.lib.LibAdvancements;
import com.meteor.extrabotany.common.lib.LibMisc;

import net.minecraft.util.ResourceLocation;
import vazkii.botania.api.lexicon.LexiconCategory;
import vazkii.botania.common.lexicon.RelicLexiconEntry;

public class DreamAdvancementEntry extends RelicLexiconEntry{

	public DreamAdvancementEntry(String unlocalizedName, LexiconCategory category, String name) {
		super(unlocalizedName, category, new ResourceLocation(LibMisc.MOD_ID, LibAdvancements.PREFIX+name));
		setKnowledgeType(ExtraBotanyAPI.dreamKnowledge);
	}

}
