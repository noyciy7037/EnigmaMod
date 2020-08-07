package com.github.yuitosaito.enigma;

import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


public class EnigmaModGuiFactory implements IModGuiFactory {
    @Override
    public void initialize(Minecraft minecraftInstance) {
    }


    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return EnigmaModConfigGui.class;
    }


    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }


    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }


    public static class EnigmaModConfigGui extends GuiConfig {
        public EnigmaModConfigGui(GuiScreen parent) {
            super(parent, EnigmaModConfigCore.getElements(), EnigmaMOD.MOD_ID, false, false, EnigmaMOD.MOD_NAME);
        }
    }
}