package com.github.yuitosaito.enigma;

import cpw.mods.fml.client.config.DummyConfigElement;
import cpw.mods.fml.client.config.GuiConfigEntries;
import cpw.mods.fml.client.config.IConfigElement;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.gui.GuiKeyBindingList;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import java.util.List;


public class EnigmaModConfigCore {
    public static final String MODE = "Mode";
    public static final String GUI = "Gui";
    public static Configuration cfg;
    public static boolean isNeutral = false;
    public static boolean isDisplayGui = false;
    public static int position = 8;
    public static int xGap = 0;
    public static int yGap = 0;

    public static void loadConfig(FMLPreInitializationEvent event) {
        cfg = new Configuration(event.getSuggestedConfigurationFile(), EnigmaMOD.MOD_VERSION, true);
        initConfig();
        syncConfig();
    }

    private static void initConfig() {
        cfg.addCustomCategoryComment(MODE, "A settings of Enigma Mode.");
        cfg.setCategoryLanguageKey(MODE, "config.enigma.category.mode");
        cfg.addCustomCategoryComment(GUI, "A settings of Enigma Mode Gui.");
        cfg.setCategoryLanguageKey(GUI, "config.enigma.category.gui");
    }

    public static void syncConfig() {
        isNeutral = cfg.getBoolean("enabledNeutralMode", MODE, false, "If true, you will not be able to send ciphertext.", "config.enigma.prop.enabledNeutralMode");
        isDisplayGui = cfg.getBoolean("isDisplayGui",GUI,false,"If true, the startup status of Enigma is displayed on the screen.","config.enigma.prop.isDisplayGui");
        position = cfg.getInt("textPosition",GUI,8,0,8,"0 to 8, 0 is upper left, 1 is upper center...","config.enigma.prop.textPosition");
        xGap = cfg.getInt("xGap",GUI,0,Integer.MIN_VALUE,Integer.MAX_VALUE,"Specifies the distance from the x coordinate position.","config.enigma.prop.xGap");
        yGap = cfg.getInt("yGap",GUI,0,Integer.MIN_VALUE,Integer.MAX_VALUE,"Specifies the distance from the y coordinate position.","config.enigma.prop.yGap");
        cfg.save();
    }

    @SuppressWarnings("rawtypes")
    public static List<IConfigElement> getElements(){
        List<IConfigElement> l = (new ConfigElement<>(EnigmaModConfigCore.cfg.getCategory(EnigmaModConfigCore.MODE))).getChildElements();
        DummyConfigElement dce = new DummyConfigElement.DummyCategoryElement("Gui","config.enigma.category.gui",(new ConfigElement<>(EnigmaModConfigCore.cfg.getCategory(EnigmaModConfigCore.GUI))).getChildElements());
        l.add(dce);
        return l;
    }
}