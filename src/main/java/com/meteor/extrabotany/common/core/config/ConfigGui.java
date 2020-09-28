package com.meteor.extrabotany.common.core.config;

import java.util.Set;

import com.meteor.extrabotany.common.lib.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ConfigGui implements IModGuiFactory{

    @Override
    public void initialize(Minecraft minecraftInstance){

    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories(){
        return null;
    }

    @Override
    public boolean hasConfigGui(){
        return true;
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen){
        return new GuiConfig(parentScreen, new ConfigElement(ConfigHandler.CONFIG.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), Reference.MOD_ID, false, false, Reference.MOD_ID, GuiConfig.getAbridgedConfigPath(ConfigHandler.CONFIG.toString()));
    }

}
