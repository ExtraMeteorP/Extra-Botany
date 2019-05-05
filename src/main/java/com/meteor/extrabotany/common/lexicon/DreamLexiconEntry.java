package com.meteor.extrabotany.common.lexicon;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import vazkii.botania.api.lexicon.LexiconCategory;
import vazkii.botania.common.lexicon.BasicLexiconEntry;

public class DreamLexiconEntry extends BasicLexiconEntry {

    public DreamLexiconEntry(String unlocalizedName, LexiconCategory category) {
        super(unlocalizedName, category);
        setKnowledgeType(ExtraBotanyAPI.dreamKnowledge);
    }

}
